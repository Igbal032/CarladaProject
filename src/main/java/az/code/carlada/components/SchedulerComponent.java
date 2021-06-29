package az.code.carlada.components;

import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.utils.TimerUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class SchedulerComponent {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerComponent.class);

    private final Scheduler scheduler;

    public SchedulerComponent(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage());
        }
    }

    public void schedule(Class<? extends Job> jobClass, TimerInfoDTO infoDTO) {
        JobDetail jobDetail = TimerUtil.buildJobDetail(jobClass, infoDTO);
        Trigger trigger = TimerUtil.buildTrigger(jobClass, infoDTO);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage());
        }
    }


    @PreDestroy
    public void preDestroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage());
        }
    }
}
