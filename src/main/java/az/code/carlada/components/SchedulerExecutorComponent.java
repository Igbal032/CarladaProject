package az.code.carlada.components;

import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.dtos.UserDTO;
import az.code.carlada.jobs.*;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Listing;
import az.code.carlada.models.VerificationToken;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SchedulerExecutorComponent {
    SchedulerComponent scheduler;

    public SchedulerExecutorComponent(SchedulerComponent scheduler) {
        this.scheduler = scheduler;
    }


    public void runSubscriptionJob(Listing listing) {
        TimerInfoDTO<Listing> infoDTO = new TimerInfoDTO<>();
        infoDTO.setTotalFireCount(1);
        infoDTO.setCallbackData(listing);
        scheduler.schedule(SubscriptionNotificationJob.class, infoDTO);
    }

    public void runEmailVerification(AppUser appUser) {
        TimerInfoDTO<AppUser> infoDTO = new TimerInfoDTO<>();
        infoDTO.setTotalFireCount(1);
        infoDTO.setCallbackData(appUser);
        scheduler.schedule(EmailVerificationJob.class, infoDTO);
    }
    public void runDisableVerificationToken(VerificationToken token) {
        TimerInfoDTO<VerificationToken> infoDTO = new TimerInfoDTO<>();
        infoDTO.setInitialOffsetMS(1000 * 60 * 60 * 12);
        infoDTO.setTotalFireCount(1);
        infoDTO.setCallbackData(token);
        scheduler.schedule(DisableVerificationTokenJob.class, infoDTO);
    }

    @Bean
    public void runAutoPaymentJob() {
        TimerInfoDTO infoDTO = TimerInfoDTO.builder()
                .runForever(true)
                .repeatIntervalMS(1000 * 60 * 60 * 24)
                .build();
        scheduler.schedule(AutoPaymentJob.class, infoDTO);
    }

    @Bean
    public void runListingExpireNotificationJob() {
        TimerInfoDTO infoDTO = TimerInfoDTO.builder()
                .runForever(true)
                .repeatIntervalMS(1000 * 60 * 60 * 24)
                .build();
        scheduler.schedule(ListingExpireNotificationJob.class, infoDTO);
    }
    @Bean
    public void autoDisableJob() {
        TimerInfoDTO infoDTO = TimerInfoDTO.builder()
                .runForever(true)
                .repeatIntervalMS(1000 * 60 * 60 * 24)
                .build();
        scheduler.schedule(AutoDisableJob.class, infoDTO);
    }

}
