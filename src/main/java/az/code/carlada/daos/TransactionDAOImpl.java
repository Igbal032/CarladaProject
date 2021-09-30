package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.ListingDAO;
import az.code.carlada.daos.interfaces.TransactionDAO;
import az.code.carlada.daos.interfaces.UserDAO;
import az.code.carlada.exceptions.EnoughBalanceException;
import az.code.carlada.exceptions.IllegalSignException;
import az.code.carlada.exceptions.StatusNotFoundException;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Status;
import az.code.carlada.models.Transaction;
import az.code.carlada.repositories.StatusRepo;
import az.code.carlada.repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionDAOImpl implements TransactionDAO {
    UserDAO userDAO;
    StatusRepo statusRepo;
    TransactionRepo transactionRepo;
    ListingDAO listingDAO;
    @Value("${app.status.initial}")
    String initial;
    public TransactionDAOImpl(UserDAO userDAO, StatusRepo statusRepo, TransactionRepo transactionRepo, ListingDAO listingDAO) {
        this.userDAO = userDAO;
        this.statusRepo = statusRepo;
        this.transactionRepo = transactionRepo;
        this.listingDAO = listingDAO;
    }

    @Override
    public Transaction addAmount(String username, Double amount) {
        AppUser appUser = userDAO.getUserByUsername(username);
        if (amount < 1) {
            throw new IllegalSignException("Amount must not be less than 1");
        }
        Double totalAmount = appUser.getAmount() + amount;
        appUser.setAmount(totalAmount);
        Transaction transaction = createTransaction(null, amount, appUser);
        userDAO.saveUser(appUser);
        return transaction;
    }


    @Override
    public Status getStatusByName(String statusName) {
        Status status = statusRepo.getStatusByStatusName(statusName);
        if (status == null)
            throw new StatusNotFoundException("Status does not exsist");
        return status;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction payForListingStatus(Long listingId, String statusType, String username) {
        Status status = getStatusByName(statusType);
        AppUser appUser = userDAO.getUserByUsername(username);
        Transaction transaction;
        Listing listing = listingDAO.getListingById(listingId);
        if (statusType.equals(initial)) {
            transaction = createTransaction(listingId, 0.0, appUser);
        }
        else if (status.getPrice() < appUser.getAmount()) {
            appUser.setAmount(appUser.getAmount() - status.getPrice());
            transaction = createTransaction(listingId, (double) status.getPrice(), appUser);
        } else {
            throw new EnoughBalanceException("Balance is not enough");
        }
        listing.setStatusType(status);
        listingDAO.saveListing(listing);
        userDAO.saveUser(appUser);
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
}
