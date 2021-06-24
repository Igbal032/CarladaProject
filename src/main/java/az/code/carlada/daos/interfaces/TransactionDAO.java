package az.code.carlada.daos.interfaces;

import az.code.carlada.models.AppUser;
import az.code.carlada.models.Status;
import az.code.carlada.models.Transaction;

public interface TransactionDAO {
    Transaction addAmount(String usernames, Double amount);

    Transaction payForListingStatus(Long listingId, String statusType, String username);

    Transaction createTransaction(Long listingId, Double amount, AppUser appUser);

    Status getStatusByName(String statusName);

    Transaction save(Transaction transaction);
}
