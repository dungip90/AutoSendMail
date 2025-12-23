package demo.autosendmail.quartz;

import demo.autosendmail.mail.MailHistory;
import demo.autosendmail.mail.MailHistoryRepository;
import demo.autosendmail.mail.MailQueue;
import demo.autosendmail.mail.MailQueueRepository;
import demo.autosendmail.mail.MailSenderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;

@Component
public class MailDispatchJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(MailDispatchJob.class);
    private final MailQueueRepository queueRepo;
    private final MailHistoryRepository historyRepo;
    private final MailSenderService mailService;

    public MailDispatchJob(MailQueueRepository queueRepo,
                           MailHistoryRepository historyRepo,
                           MailSenderService mailService) {
        this.queueRepo = queueRepo;
        this.historyRepo = historyRepo;
        this.mailService = mailService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        Instant now = Instant.now();
        List<MailQueue> pending = queueRepo.findReadyToSend(now);
        log.info("MailDispatchJob triggered, found {} mails", pending.size());

        for (MailQueue mq : pending) {
            try {
                // gửi cho nhiều người + cc
                mailService.sendPlainText(
                        mq.getRecipients(),
                        mq.getCcRecipients(),
                        mq.getSubject(),
                        mq.getBody()
                );

                MailHistory h = new MailHistory();
                h.setQueueId(mq.getId());
                h.setRecipients(mq.getRecipients());
                h.setCcRecipients(mq.getCcRecipients());
                h.setSubject(mq.getSubject());
                h.setBody(mq.getBody());
                h.setSentAt(Instant.now());
                h.setStatus("SENT");
                h.setError(null);
                historyRepo.save(h);

                queueRepo.deleteById(mq.getId());
            } catch (Exception ex) {
                log.error("Mail send failed to {}: {}", mq.getRecipients(), ex.getMessage(), ex);

                mq.setAttempts(mq.getAttempts() + 1);
                mq.setStatus("FAILED");
                mq.setLastError(ex.getMessage());
                queueRepo.save(mq);

                MailHistory h = new MailHistory();
                h.setQueueId(mq.getId());
                h.setRecipients(mq.getRecipients());
                h.setCcRecipients(mq.getCcRecipients());
                h.setSubject(mq.getSubject());
                h.setBody(mq.getBody());
                h.setSentAt(Instant.now());
                h.setStatus("FAILED");
                h.setError(ex.getMessage());
                historyRepo.save(h);
            }
        }
    }
}
