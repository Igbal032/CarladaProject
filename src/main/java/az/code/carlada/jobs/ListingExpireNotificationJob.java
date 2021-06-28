package az.code.carlada.jobs;

import az.code.carlada.components.MailSenderComponent;
import az.code.carlada.daos.interfaces.ListingDAO;
import az.code.carlada.daos.interfaces.SearchDAO;
import az.code.carlada.components.ReaderComponent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class ListingExpireNotificationJob implements Job {
    SearchDAO searchDAO;
    ListingDAO listingDAO;
    MailSenderComponent senderComponent;
    ReaderComponent rUtil;

    public ListingExpireNotificationJob(SearchDAO searchDAO, ListingDAO listingDAO, MailSenderComponent senderComponent, ReaderComponent rUtil) {
        this.searchDAO = searchDAO;
        this.listingDAO = listingDAO;
        this.senderComponent = senderComponent;
        this.rUtil = rUtil;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        searchDAO.searchAllListingsByExpireDate().stream().parallel()
                .forEach(i -> senderComponent.sendEmail(i.getAppUser().getEmail(),
                        rUtil.property("subject") + " " + i.getAppUser().getFullName(),
                        rUtil.property("expire_text")));
    }
}
