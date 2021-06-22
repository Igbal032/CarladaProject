package az.code.carlada.services;

import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.jobs.SubscriptionNotification;
import az.code.carlada.models.Listing;
import org.springframework.stereotype.Service;

@Service
public class SchedulerExecutorServiceImpl implements SchedulerExecutorService {
    SchedulerService scheduler;

    public SchedulerExecutorServiceImpl(SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void runSubscriptionJob(Listing listing) {
        TimerInfoDTO infoDTO = TimerInfoDTO.builder()
                .totalFireCount(1)
                .callbackData(listing.getAppUser().getEmail())
                .build();
        scheduler.schedule(SubscriptionNotification.class, infoDTO);
    }
}
