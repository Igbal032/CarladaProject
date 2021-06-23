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
import az.code.carlada.repositories.UserRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {

    UserRepo userRepo;
    TransactionRepo transactionRepo;
    ListingRepo listingRepo;

    public UserDAOImpl(UserRepo userRepo, TransactionRepo transactionRepo, ListingRepo listingRepo) {
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
        this.listingRepo = listingRepo;
    }

    @Override
    public Transaction addAmount(String username, Double amount) {
        Optional<AppUser> appUser = userRepo.getAppUserByUsername(username);
        if (appUser!=null){
            if (amount<1){
                throw new IllegalSignException("Amount must not be less than 1");
            }
            Double totalAmount = appUser.get().getAmount()+amount;
            appUser.get().setAmount(totalAmount);
            Transaction transaction  = createTransaction(null,amount,appUser.get(), TransactionType.INCREASE_BALANCE);
            userRepo.save(appUser.get());
            return transaction;
        }
        throw new UserNotFound("User Not Found");
    }

    @Override
    public Transaction payForListingStatus(Long listingId, Status statusType, String username) {
        Double vipStatus = Status.VIP.getStatusAmount();
        Double standardStatus = Status.STANDARD.getStatusAmount();
        Double defaultStatus = Status.FREE.getStatusAmount();
        Optional<AppUser> appUser = userRepo.getAppUserByUsername(username);
        Transaction transaction;
        Optional<Listing> listing;
        if (appUser!=null){
            listing = listingRepo.findById(listingId);
            if (listing.isPresent()){
                switch (statusType){
                    case VIP:
                        if (vipStatus<=appUser.get().getAmount()){
                            appUser.get().setAmount(appUser.get().getAmount()-vipStatus);
                            transaction = createTransaction(listingId,vipStatus,appUser.get(),TransactionType.VIP_STATUS_PAYMENT);
                        }
                        else {
                            throw new EnoughBalanceException("Balance is not enough");
                        }
                        break;
                    case FREE:
                        if (defaultStatus<=appUser.get().getAmount()){
                            appUser.get().setAmount(appUser.get().getAmount()-defaultStatus);
                            transaction = createTransaction(listingId,defaultStatus,appUser.get(),TransactionType.NEW);
                        }
                        else {
                            throw new EnoughBalanceException("Balance is not enough");
                        }
                        break;
                    case STANDARD:
                        if (standardStatus<=appUser.get().getAmount()){
                            appUser.get().setAmount(appUser.get().getAmount()-standardStatus);
                            transaction = createTransaction(listingId,standardStatus,appUser.get(),TransactionType.UPDATE_PAYMENT);
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
                userRepo.save(appUser.get());
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

    @Override
    public AppUser getUserByUsername(String username) {
        if (userRepo.getAppUserByUsername(username).isEmpty())
            throw new UserNotFound("User Not Found");

        return userRepo.getAppUserByUsername(username).get();
    }
}
