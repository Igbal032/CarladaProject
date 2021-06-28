package az.code.carlada.jobs;

import az.code.carlada.components.MailSenderComponent;
import az.code.carlada.daos.interfaces.ListingDAO;
import az.code.carlada.components.ReaderComponent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class AutoDisableJob implements Job {
    ListingDAO listingDAO;
    MailSenderComponent senderComponent;
    ReaderComponent rUtil;

    public AutoDisableJob(ListingDAO listingDAO, MailSenderComponent senderComponent, ReaderComponent readerUtil) {
        this.listingDAO = listingDAO;
        this.senderComponent = senderComponent;
        this.rUtil = readerUtil;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        listingDAO.getWaitingExpired().stream().parallel()
                .peek(i -> i.setActive(false))
                .peek(i -> listingDAO.saveListing(i))
                .forEach(i -> senderComponent.sendEmail(i.getAppUser().getEmail(),
                        rUtil.property("subject") + " " + i.getAppUser().getFullName(),
                        rUtil.property("disable_text")));
    }
}
