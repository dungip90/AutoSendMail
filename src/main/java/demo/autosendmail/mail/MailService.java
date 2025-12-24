//package demo.autosendmail.mail;
//
//
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class MailService {
//
//    private final JavaMailSender mailSender;
//    private final MailQueueRepository queueRepo;
//    private final MailHistoryService historyService;
//    public MailService(JavaMailSender mailSender,
//                       MailQueueRepository queueRepo,
//                       MailHistoryService historyService) {
//        this.mailSender = mailSender;
//        this.queueRepo = queueRepo;
//        this.historyService = historyService;
//    }
//
//    public void processQueue() {
//        List<MailQueue> pendingMails = queueRepo.findByStatus("PENDING");
//        for (MailQueue q : pendingMails) {
//            try {
//                sendMail(q);
//                q.setStatus("SENT");
//                historyService.saveHistory(q, "SENT", null);
//            } catch (Exception e) {
//                q.setStatus("FAILED");
//                q.setLastError(e.getMessage());
//                q.setAttempts(q.getAttempts() + 1);
//                historyService.saveHistory(q, "FAILED", e.getMessage());
//            }
//            queueRepo.save(q);
//        }
//    }
//
//    private void sendMail(MailQueue q) throws Exception {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setSubject(q.getSubject());
//        helper.setText(q.getBody(), true);
//
//        if (q.getRecipients() != null && !q.getRecipients().isEmpty()) {
//            helper.setTo(q.getRecipients().toArray(new String[0]));
//        }
//        if (q.getCcRecipients() != null && !q.getCcRecipients().isEmpty()) {
//            helper.setCc(q.getCcRecipients().toArray(new String[0]));
//        }
//
//        mailSender.send(message);
//    }
//}
