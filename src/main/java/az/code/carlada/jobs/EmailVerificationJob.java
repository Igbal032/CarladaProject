package az.code.carlada.jobs;

import az.code.carlada.components.MailSenderComponent;
import az.code.carlada.components.ReaderComponent;
import az.code.carlada.components.SchedulerExecutorComponent;
import az.code.carlada.daos.interfaces.VerifyTokenDAO;
import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.dtos.UserDTO;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.VerificationToken;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.UUID;

public class EmailVerificationJob implements Job {
    ReaderComponent rUtil;
    SchedulerExecutorComponent schEx;
    MailSenderComponent senderComponent;
    VerifyTokenDAO vtDAO;

    public EmailVerificationJob(ReaderComponent rUtil, SchedulerExecutorComponent schEx, MailSenderComponent senderComponent, VerifyTokenDAO vtDAO) {
        this.rUtil = rUtil;
        this.schEx = schEx;
        this.senderComponent = senderComponent;
        this.vtDAO = vtDAO;
    }

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        TimerInfoDTO<AppUser> infoDTO = (TimerInfoDTO<AppUser>) ctx.getJobDetail().getJobDataMap().get(EmailVerificationJob.class.getSimpleName());
        AppUser user = infoDTO.getCallbackData();
        String token = UUID.randomUUID().toString();

        VerificationToken verifyToken = vtDAO.save(VerificationToken.builder()
                .token(token)
                .email(user.getEmail())
                .build());

        senderComponent.sendEmail(user.getEmail(),
                rUtil.property("subject") + " " + user.getFullName(),
                rUtil.property("verify_text") + "  |  " + token);

        schEx.runDisableVerificationToken(verifyToken);
    }
}
