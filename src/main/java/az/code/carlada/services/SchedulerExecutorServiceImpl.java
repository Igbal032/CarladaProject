package az.code.carlada.services;

import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.jobs.AutoPaymentJob;
import az.code.carlada.jobs.SubscriptionNotification;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class SchedulerExecutorServiceImpl implements SchedulerExecutorService {
    SchedulerService scheduler;

    public SchedulerExecutorServiceImpl(SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    @Bean
    public void runSubscriptionJob() {
        TimerInfoDTO infoDTO = TimerInfoDTO.builder()
                .runForever(true)
                .repeatIntervalMS(10000)
                .build();
        scheduler.schedule(SubscriptionNotification.class, infoDTO);
    }
    @Bean
    public void runAutoPaymentJob() {
        TimerInfoDTO infoDTO = TimerInfoDTO.builder()
                .runForever(true)
                .repeatIntervalMS(1000*60*60*24)
                .build();
        scheduler.schedule(AutoPaymentJob.class, infoDTO);
    }

}
