package az.code.carlada.jobs;

import az.code.carlada.configs.SchedulerExecutorConfig;
import az.code.carlada.daos.ListingDAO;
import az.code.carlada.daos.SearchDAO;
import az.code.carlada.daos.UserDAO;
import az.code.carlada.enums.Status;
import az.code.carlada.enums.TransactionType;
import az.code.carlada.exceptions.EnoughBalanceException;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Transaction;
import az.code.carlada.repositories.ListingRepo;
import az.code.carlada.repositories.TransactionRepo;
import az.code.carlada.repositories.UserRepo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.List;

public class AutoPaymentJob implements Job {

    ListingDAO listingDAO;
    SchedulerExecutorConfig schConfig;
    SearchDAO searchDAO;
    TransactionRepo transactionRepo;
    UserDAO userDAO;

    public AutoPaymentJob(ListingDAO listingDAO, SchedulerExecutorConfig schConfig, SearchDAO searchDAO, TransactionRepo transactionRepo, UserDAO userDAO) {
        this.listingDAO = listingDAO;
        this.schConfig = schConfig;
        this.searchDAO = searchDAO;
        this.transactionRepo = transactionRepo;
        this.userDAO = userDAO;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Listing> listings = searchDAO.searchAllListingsWithExpiredDate();
        listings.forEach(listing -> {
            AppUser user = listing.getAppUser();
            LocalDateTime duration = listing.getUpdatedAt().plusDays(30);
            LocalDateTime today = LocalDateTime.now();
            boolean isAfterOrNot = today.isAfter(listing.getExpiredAt());
            if (isAfterOrNot) {
                if (user.getAmount() < Status.STANDARD.getStatusAmount()) {
                    listing.setAutoPay(false);
                    listingDAO.saveListing(listing);
                    schConfig.manualDisableJob();
                }
                user.setAmount(user.getAmount() - Status.STANDARD.getStatusAmount());
                userDAO.saveUser(user);
                listing.setUpdatedAt(LocalDateTime.now());
                listing.setExpiredAt(duration);
                listing.setType(Status.STANDARD);
                Transaction transaction = Transaction.builder()
                        .transactionType(TransactionType.UPDATE_PAYMENT)
                        .appUser(user)
                        .createdDate(LocalDateTime.now())
                        .listingId(listing.getId())
                        .amount(Status.STANDARD.getStatusAmount())
                        .build();
                transactionRepo.save(transaction);
            }
            listingDAO.saveListing(listing);
        });
    }
}
