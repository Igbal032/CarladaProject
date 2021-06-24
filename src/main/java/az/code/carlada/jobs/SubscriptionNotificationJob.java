package az.code.carlada.jobs;

import az.code.carlada.components.MailSenderComponent;
import az.code.carlada.daos.interfaces.SearchDAO;
import az.code.carlada.daos.interfaces.SubscriptionDAO;
import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.exceptions.FileReadException;
import az.code.carlada.models.Listing;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
public class SubscriptionNotificationJob implements Job {
    SearchDAO searchDAO;
    SubscriptionDAO subDAO;
    MailSenderComponent mailSender;
    @Value("classpath:data/notification-message.properties")
    Resource resourceFile;

    public SubscriptionNotificationJob(SearchDAO searchDAO, SubscriptionDAO subDAO, MailSenderComponent mailSender) {
        this.searchDAO = searchDAO;
        this.subDAO = subDAO;
        this.mailSender = mailSender;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TimerInfoDTO<Listing> infoDTO = (TimerInfoDTO<Listing>) context.getJobDetail().getJobDataMap().get(SubscriptionNotificationJob.class.getSimpleName());
        Listing listing = infoDTO.getCallbackData();
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(resourceFile.getFile()));
        } catch (IOException e) {
            throw new FileReadException(e.getMessage());
        }
        searchDAO.searchAllSubscriptions(listing).stream().parallel()
                .forEach(i -> mailSender.sendEmail(i.getAppUser().getEmail(),
                        prop.getProperty("subject") + " " + i.getAppUser().getFullName(),
                        prop.getProperty("subtext")));
    }
}
