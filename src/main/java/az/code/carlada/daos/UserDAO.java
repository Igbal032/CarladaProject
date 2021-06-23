package az.code.carlada.daos;

import az.code.carlada.enums.TransactionType;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Status;
import az.code.carlada.models.Transaction;

public interface UserDAO {
    Transaction addAmount(String usernames, Double amount);
    Transaction payForListingStatus(Long listingId, String statusType, String username);
    Transaction createTransaction(Long listingId, Double amount, AppUser appUser);
    AppUser saveUser(AppUser appUser);
    AppUser getUserByUsername(String username);
    Status getStatusByName(String statusName);
}
