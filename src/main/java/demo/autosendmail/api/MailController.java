package demo.autosendmail.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import demo.autosendmail.mail.MailQueue;
import demo.autosendmail.mail.MailQueueRepository;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailQueueRepository queueRepo;

    public MailController(MailQueueRepository queueRepo) {
        this.queueRepo = queueRepo;
    }

    @GetMapping("/queue")
    public List<MailQueue> getQueue() {
        return queueRepo.findAll();
    }

    @PostMapping("/queue")
    public MailQueue addToQueue(
            @RequestParam List<@Email String> recipients,
            @RequestParam(required = false) List<@Email String> ccRecipients,
            @RequestParam @NotBlank String subject,
            @RequestParam @NotBlank String body,
            @RequestParam(required = false) Long scheduleEpochMillis
    ) {
        MailQueue q = new MailQueue();
        q.setRecipients(recipients);
        q.setCcRecipients(ccRecipients);
        q.setSubject(subject);
        q.setBody(body);
        if (scheduleEpochMillis != null) {
            q.setScheduledAt(Instant.ofEpochMilli(scheduleEpochMillis));
        }
        q.setStatus("PENDING");
        return queueRepo.save(q);
    }
}