package az.code.carlada.services;

import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.utils.TimerUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class SchedulerService {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
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
