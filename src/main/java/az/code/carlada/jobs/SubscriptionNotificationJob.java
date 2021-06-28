package az.code.carlada.jobs;

import az.code.carlada.components.MailSenderComponent;
import az.code.carlada.daos.interfaces.SearchDAO;
import az.code.carlada.daos.interfaces.SubscriptionDAO;
import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.models.Listing;
import az.code.carlada.components.ReaderComponent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionNotificationJob implements Job {
    SearchDAO searchDAO;
    SubscriptionDAO subDAO;
    MailSenderComponent mailSender;
    ReaderComponent rUtil;

    public SubscriptionNotificationJob(SearchDAO searchDAO, SubscriptionDAO subDAO, MailSenderComponent mailSender, ReaderComponent rUtil) {
        this.searchDAO = searchDAO;
        this.subDAO = subDAO;
        this.mailSender = mailSender;
        this.rUtil = rUtil;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TimerInfoDTO<Listing> infoDTO = (TimerInfoDTO<Listing>) context.getJobDetail().getJobDataMap().get(SubscriptionNotificationJob.class.getSimpleName());
        Listing listing = infoDTO.getCallbackData();
        searchDAO.searchAllSubscriptions(listing).stream().parallel()
                .forEach(i -> mailSender.sendEmail(i.getAppUser().getEmail(),
                        rUtil.property("subject") + " " + i.getAppUser().getFullName(),
                        rUtil.property("subtext")));
    }
}
