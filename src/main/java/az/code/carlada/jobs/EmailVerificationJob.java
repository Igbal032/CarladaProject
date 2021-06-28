package az.code.carlada.jobs;

import az.code.carlada.components.MailSenderComponent;
import az.code.carlada.components.ReaderComponent;
import az.code.carlada.components.SchedulerExecutorComponent;
import az.code.carlada.daos.interfaces.VerifyTokenDAO;
import az.code.carlada.dtos.TimerInfoDTO;
import az.code.carlada.dtos.UserDTO;
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
        TimerInfoDTO<UserDTO> infoDTO = (TimerInfoDTO<UserDTO>) ctx.getJobDetail().getJobDataMap().get(EmailVerificationJob.class.getSimpleName());
        UserDTO userDTO = infoDTO.getCallbackData();
        String token = UUID.randomUUID().toString();

        VerificationToken verifyToken = vtDAO.save(VerificationToken.builder()
                .token(token)
                .email(userDTO.getEmail())
                .build());

        senderComponent.sendEmail(userDTO.getEmail(),
                rUtil.property("subject") + " " + userDTO.getFirstname() + " " + userDTO.getLastname(),
                rUtil.property("verify_text") + "  |  " + token);

        schEx.runDisableVerificationToken(verifyToken);
    }
}
