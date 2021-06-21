package az.code.carlada.daos;

import az.code.carlada.enums.Status;
import az.code.carlada.enums.TransactionType;
import az.code.carlada.exceptions.EnoughBalanceException;
import az.code.carlada.exceptions.IllegalSignException;
import az.code.carlada.exceptions.ListingNotFound;
import az.code.carlada.exceptions.UserNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Transaction;
import az.code.carlada.repositories.ListingRepo;
import az.code.carlada.repositories.TransactionRepo;
import az.code.carlada.repositories.ProfileRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ProfileDAOImpl implements ProfileDAO {

    ProfileRepo profileRepo;
    TransactionRepo transactionRepo;
    ListingRepo listingRepo;

    public ProfileDAOImpl(ProfileRepo userRepo, TransactionRepo transactionRepo, ListingRepo listingRepo) {
        this.profileRepo = userRepo;
        this.transactionRepo = transactionRepo;
        this.listingRepo = listingRepo;
    }

    @Override
    public Transaction addAmount(String username,Double amount) {
        AppUser appUser = profileRepo.getAppUserByUsername(username);
        if (appUser!=null){
            if (amount<1){
                throw new IllegalSignException("Amount must not be less than 1");
            }
            Double totalAmount = appUser.getAmount()+amount;
            appUser.setAmount(totalAmount);
            Transaction transaction  = createTransaction(null,totalAmount,appUser,TransactionType.INCREASE_BALANCE);
            profileRepo.save(appUser);
           return transaction;
        }
        throw new UserNotFound("User Not Found");
    }

    @Override
    public Transaction payForListingStatus(Long listingId, Status statusType, String username) {
        Double vipStatus = Status.VIP.getStatusAmount();
        Double standardStatus = Status.STANDARD.getStatusAmount();
        Double defaultStatus = Status.FREE.getStatusAmount();
        AppUser appUser = profileRepo.getAppUserByUsername(username);
        Transaction transaction;
        Optional<Listing> listing;
        if (appUser!=null){
            listing = listingRepo.findById(listingId);
            if (listing.isPresent()){
                switch (statusType){
                    case VIP:
                        if (vipStatus<=appUser.getAmount()){
                            appUser.setAmount(appUser.getAmount()-vipStatus);
                            transaction = createTransaction(listingId,vipStatus,appUser,TransactionType.VIP_STATUS_PAYMENT);
                        }
                        else {
                            throw new EnoughBalanceException("Balance is not enough");
                        }
                        break;
                    case FREE:
                        if (defaultStatus<=appUser.getAmount()){
                            appUser.setAmount(appUser.getAmount()-defaultStatus);
                            transaction = createTransaction(listingId,defaultStatus,appUser,TransactionType.NEW);
                        }
                        else {
                            throw new EnoughBalanceException("Balance is not enough");
                        }
                        break;
                    case STANDARD:
                        if (standardStatus<=appUser.getAmount()){
                            appUser.setAmount(appUser.getAmount()-standardStatus);
                            transaction = createTransaction(listingId,standardStatus,appUser,TransactionType.UPDATE_PAYMENT);
                        }
                        else {
                            throw new EnoughBalanceException("Balance is not enough");
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + statusType);
                }
                listing.get().setType(statusType);
                listingRepo.save(listing.get());
                profileRepo.save(appUser);
                return transaction;
            }
            else {
                throw new ListingNotFound("Listing Not Found");
            }
        }
        throw new UserNotFound("User Not Found");
    }

    @Override
    public Transaction createTransaction(Long listingId,Double amount, AppUser appUser, TransactionType transactionType) {
        Transaction transaction = Transaction.builder()
                .listingId(listingId)
                .appUser(appUser)
                .amount(amount)
                .transactionType(transactionType)
                .createdDate(LocalDateTime.now())
                .build();
        transactionRepo.save(transaction);
        return transaction;
    }
}
