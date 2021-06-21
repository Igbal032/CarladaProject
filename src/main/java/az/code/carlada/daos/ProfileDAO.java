package az.code.carlada.daos;

import az.code.carlada.enums.Status;
import az.code.carlada.enums.TransactionType;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Transaction;

public interface ProfileDAO {
    Transaction addAmount(String usernames,Double amount);

    Transaction payForListingStatus(Long listingId,Status statusType,  String username);

    Transaction createTransaction(Long listingId, Double amount, AppUser appUser, TransactionType transactionType);
}
