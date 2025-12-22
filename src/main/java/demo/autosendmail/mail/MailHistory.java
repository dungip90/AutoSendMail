package demo.autosendmail.mail;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "mail_history")
public class MailHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long queueId;
    @Setter
    private String recipient;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;

    private Instant sentAt;
    private String status;
    @Column(columnDefinition = "TEXT")
    private String error;


    public Long getId() { return id; }
    public Long getQueueId() { return queueId; }

    public String getRecipient() { return recipient; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public Instant getSentAt() { return sentAt; }
    public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}

