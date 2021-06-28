package az.code.carlada.jobs;

import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.models.VerificationToken;
import az.code.carlada.repositories.VerificationTokenRepo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DisableVerificationTokenJob implements Job {
    VerificationTokenRepo vrRepo;

    public DisableVerificationTokenJob(VerificationTokenRepo vrRepo) {
        this.vrRepo = vrRepo;
    }

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        TimerInfoDTO<VerificationToken> infoDTO = (TimerInfoDTO<VerificationToken>) ctx.getJobDetail().getJobDataMap().get(DisableVerificationTokenJob.class.getSimpleName());
        VerificationToken vToken = infoDTO.getCallbackData();
        vrRepo.delete(vToken);
    }
}
