package az.code.carlada.jobs;

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

    ListingRepo listingRepo;
    TransactionRepo transactionRepo;
    UserRepo userRepo;

    public AutoPaymentJob(ListingRepo listingRepo, TransactionRepo transactionRepo, UserRepo userRepo) {
        this.listingRepo = listingRepo;
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Checked");
        List<Listing> listings = listingRepo.findAll();
        listings.stream().filter(l->l.getAutoPay()==true).forEach(w->{
            AppUser user = w.getAppUser();
            LocalDateTime duration = w.getUpdatedAt().plusDays(30);
            LocalDateTime today = LocalDateTime.now();
            boolean isAfterOrNot = today.isAfter(w.getExpiredAt());
            if (isAfterOrNot){
                if (user.getAmount()<Status.STANDARD.getStatusAmount()){
                    throw new EnoughBalanceException("Not Enough Money");
                }
                user.setAmount(user.getAmount()-Status.STANDARD.getStatusAmount());
                userRepo.save(user);
                w.setUpdatedAt(LocalDateTime.now());
                w.setExpiredAt(LocalDateTime.now().plusDays(30));
                w.setType(Status.STANDARD);
                Transaction transaction = Transaction.builder()
                        .transactionType(TransactionType.UPDATE_PAYMENT)
                        .appUser(w.getAppUser())
                        .createdDate(LocalDateTime.now())
                        .listingId(w.getId())
                        .amount(Status.STANDARD.getStatusAmount())
                        .build();
                transactionRepo.save(transaction);
            }
            listingRepo.save(w);
        });
        System.out.println(listings.size());
    }
}
