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
    ListingDAO listingDAO;

    public UserDAOImpl(UserRepo userRepo, TransactionRepo transactionRepo, ListingDAO listingDAO) {
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
        this.listingDAO = listingDAO;
    }

    @Override
    public Transaction addAmount(String username, Double amount) {
        Optional<AppUser> appUser = userRepo.getAppUserByUsername(username);
        if (appUser.isPresent()) {
            if (amount < 1) {
                throw new IllegalSignException("Amount must not be less than 1");
            }
            Double totalAmount = appUser.get().getAmount() + amount;
            appUser.get().setAmount(totalAmount);
            Transaction transaction = createTransaction(null, totalAmount, appUser.get(), TransactionType.INCREASE_BALANCE);
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
        AppUser appUser = getUserByUsername(username);
        Transaction transaction;
        Listing listing = listingDAO.getListingById(listingId);
        switch (statusType) {
            case VIP:
                if (vipStatus <= appUser.getAmount()) {
                    appUser.setAmount(appUser.getAmount() - vipStatus);
                    transaction = createTransaction(listingId, vipStatus, appUser, TransactionType.VIP_STATUS_PAYMENT);
                } else {
                    throw new EnoughBalanceException("Balance is not enough");
                }
                break;
            case FREE:
                if (defaultStatus <= appUser.getAmount()) {
                    appUser.setAmount(appUser.getAmount() - defaultStatus);
                    transaction = createTransaction(listingId, defaultStatus, appUser, TransactionType.NEW);
                } else {
                    throw new EnoughBalanceException("Balance is not enough");
                }
                break;
            case STANDARD:
                if (standardStatus <= appUser.getAmount()) {
                    appUser.setAmount(appUser.getAmount() - standardStatus);
                    transaction = createTransaction(listingId, standardStatus, appUser, TransactionType.UPDATE_PAYMENT);
                } else {
                    throw new EnoughBalanceException("Balance is not enough");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + statusType);
        }
        listing.setType(statusType);
        listingDAO.saveListing(listing);
        userRepo.save(appUser);
        return transaction;
    }


    @Override
    public Transaction createTransaction(Long listingId, Double amount, AppUser appUser, TransactionType transactionType) {
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
        Optional<AppUser> appUser = userRepo.getAppUserByUsername(username);
        if (appUser.isEmpty())
            throw new UserNotFound("User Not Found");

        return appUser.get();
    }
}
