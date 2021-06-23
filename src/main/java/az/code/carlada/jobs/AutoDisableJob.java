package az.code.carlada.jobs;

import az.code.carlada.components.MailSenderComponent;
import az.code.carlada.daos.ListingDAO;
import az.code.carlada.exceptions.FileReadException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AutoDisableJob implements Job {
    ListingDAO listingDAO;
    MailSenderComponent senderComponent;
    @Value("classpath:data/notification-message.properties")
    Resource resourceFile;

    public AutoDisableJob(ListingDAO listingDAO, MailSenderComponent senderComponent) {
        this.listingDAO = listingDAO;
        this.senderComponent = senderComponent;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(resourceFile.getFile()));
        } catch (IOException e) {
            throw new FileReadException(e.getMessage());
        }

        listingDAO.getWaitingExpired().stream().parallel()
                .peek(i -> i.setActive(false))
                .peek(i -> listingDAO.saveListing(i))
                .forEach(i -> senderComponent.sendEmail(i.getAppUser().getEmail(),
                prop.getProperty("subject") + " " + i.getAppUser().getFullName(),
                prop.getProperty("disable_text")));
    }
}
