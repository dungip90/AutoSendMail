package demo.autosendmail.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;

@Service
public class MailSenderService {

    private final JavaMailSender mailSender;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPlainText(List<String> recipients,
                              List<String> ccRecipients,
                              String subject,
                              String body) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(subject);
        helper.setText(body, true);

        // gửi cho nhiều người nhận chính
        if (recipients != null && !recipients.isEmpty()) {
            helper.setTo(recipients.toArray(new String[0]));
        }

        // gửi cho nhiều người nhận CC
        if (ccRecipients != null && !ccRecipients.isEmpty()) {
            helper.setCc(ccRecipients.toArray(new String[0]));
        }

        mailSender.send(message);
    }
}
