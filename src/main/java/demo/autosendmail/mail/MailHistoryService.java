package demo.autosendmail.mail;

import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class MailHistoryService {

    private final MailHistoryRepository historyRepo;

    public MailHistoryService(MailHistoryRepository historyRepo) {
        this.historyRepo = historyRepo;
    }

    public void saveHistory(MailQueue queue, String status, String error) {
        MailHistory history = new MailHistory();
        history.setQueueId(queue.getId());
        history.setRecipients(queue.getRecipients());
        history.setCcRecipients(queue.getCcRecipients());
        history.setSubject(queue.getSubject());
        history.setBody(queue.getBody());
        history.setSentAt(Instant.now());
        history.setStatus(status);
        history.setError(error);

        historyRepo.save(history);
    }
}

/*
service để lưu lại lịch sử gửi mail ngay sau khi gửi thành công.
Ý tưởng là: sau khi MailService.sendMail() gửi xong, ta tạo một bản ghi MailHistory và lưu vào database
* */