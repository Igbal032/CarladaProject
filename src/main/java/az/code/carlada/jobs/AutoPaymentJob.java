package az.code.carlada.jobs;

import az.code.carlada.components.SchedulerExecutorComponent;
import az.code.carlada.daos.interfaces.ListingDAO;
import az.code.carlada.daos.interfaces.SearchDAO;
import az.code.carlada.daos.interfaces.TransactionDAO;
import az.code.carlada.daos.interfaces.UserDAO;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Status;
import az.code.carlada.models.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

public class AutoPaymentJob implements Job {

    ListingDAO listingDAO;
    SchedulerExecutorComponent schConfig;
    SearchDAO searchDAO;
    TransactionDAO transactionDAO;
    UserDAO userDAO;
    @Value("${app.status.standard}")
    String standard;
    public AutoPaymentJob(ListingDAO listingDAO, SchedulerExecutorComponent schConfig, SearchDAO searchDAO, TransactionDAO transactionDAO, UserDAO userDAO) {
        this.listingDAO = listingDAO;
        this.schConfig = schConfig;
        this.searchDAO = searchDAO;
        this.transactionDAO = transactionDAO;
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
                Status status = transactionDAO.getStatusByName(standard);
                if (user.getAmount() < status.getPrice()) {
                    listing.setAutoPay(false);
                    listingDAO.saveListing(listing);
                    schConfig.autoDisableJob();
                }
                else {
                    user.setAmount(user.getAmount() - status.getPrice());
                    userDAO.saveUser(user);
                    listing.setUpdatedAt(LocalDateTime.now());
                    listing.setExpiredAt(duration);
                    listing.setStatusType(transactionDAO.getStatusByName(standard));
                    Transaction transaction = Transaction.builder()
                            .appUser(user)
                            .createdDate(LocalDateTime.now())
                            .listingId(listing.getId())
                            .amount((double)status.getPrice())
                            .build();
                    transactionDAO.save(transaction);
                }
                listingDAO.saveListing(listing);
            }
        });
    }
}
