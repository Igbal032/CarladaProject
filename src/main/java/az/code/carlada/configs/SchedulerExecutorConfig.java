package az.code.carlada.configs;

import az.code.carlada.components.SchedulerComponent;
import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.jobs.AutoDisableJob;
import az.code.carlada.jobs.AutoPaymentJob;
import az.code.carlada.jobs.ListingExpireNotificationJob;
import az.code.carlada.jobs.SubscriptionNotificationJob;
import az.code.carlada.models.Listing;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SchedulerExecutorConfig {
    SchedulerComponent scheduler;

    public SchedulerExecutorConfig(SchedulerComponent scheduler) {
        this.scheduler = scheduler;
    }


    public void runSubscriptionJob(Listing listing) {
        TimerInfoDTO<Listing> infoDTO = new TimerInfoDTO<>();
        infoDTO.setTotalFireCount(1);
        infoDTO.setCallbackData(listing);
        scheduler.schedule(SubscriptionNotificationJob.class, infoDTO);
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
    public void AutoDisableJob() {
        TimerInfoDTO infoDTO = TimerInfoDTO.builder()
                .runForever(true)
                .repeatIntervalMS(1000 * 60 * 60 * 24)
                .build();
        scheduler.schedule(AutoDisableJob.class, infoDTO);
    }
    public void manualDisableJob() {
        TimerInfoDTO infoDTO = TimerInfoDTO.builder()
                .totalFireCount(1)
                .build();
        scheduler.schedule(AutoDisableJob.class, infoDTO);
    }

}
