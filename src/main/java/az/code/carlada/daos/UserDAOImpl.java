package az.code.carlada.daos;

import az.code.carlada.enums.TransactionType;
import az.code.carlada.exceptions.EnoughBalanceException;
import az.code.carlada.exceptions.IllegalSignException;
import az.code.carlada.exceptions.StatusNotFoundException;
import az.code.carlada.exceptions.UserNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Status;
import az.code.carlada.models.Transaction;
import az.code.carlada.repositories.StatusRepo;
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
    StatusRepo statusRepo;

    public UserDAOImpl(StatusRepo statusRepo,UserRepo userRepo, TransactionRepo transactionRepo, ListingDAO listingDAO) {
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
        this.listingDAO = listingDAO;
        this.statusRepo = statusRepo;
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
            Transaction transaction  = createTransaction(null,amount,appUser.get());
            userRepo.save(appUser.get());
            return transaction;
        }
        throw new UserNotFound("User Not Found");
    }


    @Override
    public Status getStatusByName(String statusName) {
        Status status = statusRepo.getStatusByStatusName(statusName);
        if (status==null)
            throw new StatusNotFoundException("Status does not exsist");
        return status;
    }

    @Override
    public Transaction payForListingStatus(Long listingId, String statusType, String username) {
        Status status = getStatusByName(statusType);
        AppUser appUser = getUserByUsername(username);
        Transaction transaction;
        Listing listing = listingDAO.getListingById(listingId);
        if (statusType.equals("FREE")){
            return null;
        }
        if (status.getPrice() <= appUser.getAmount()) {
            appUser.setAmount(appUser.getAmount() - status.getPrice());
            transaction = createTransaction(listingId, (double) status.getPrice() , appUser);
        } else {
            throw new EnoughBalanceException("Balance is not enough");
        }
        listing.setStatusType(status);
        listingDAO.saveListing(listing);
        saveUser(appUser);
        return transaction;
    }


    @Override
    public Transaction createTransaction(Long listingId, Double amount, AppUser appUser) {
        Transaction transaction = Transaction.builder()
                .listingId(listingId)
                .appUser(appUser)
                .amount(amount)
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


    public AppUser saveUser(AppUser user){
        return userRepo.save(user);
    }
}
