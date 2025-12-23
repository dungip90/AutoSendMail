package demo.autosendmail.mail;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "mail_history")
public class MailHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tham chiếu tới mail_queue
    private Long queueId;

    // nhiều người nhận chính
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mail_history_recipients", joinColumns = @JoinColumn(name = "mail_history_id"))
    @Column(name = "recipient")
    private List<String> recipients;

    // nhiều người nhận CC
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mail_history_cc", joinColumns = @JoinColumn(name = "mail_history_id"))
    @Column(name = "cc_recipient")
    private List<String> ccRecipients;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    private Instant sentAt;

    private String status; // SENT hoặc FAILED

    @Column(columnDefinition = "TEXT")
    private String error;

    // ===== Getter/Setter =====
    public Long getId() { return id; }

    public Long getQueueId() { return queueId; }
    public void setQueueId(Long queueId) { this.queueId = queueId; }

    public List<String> getRecipients() { return recipients; }
    public void setRecipients(List<String> recipients) { this.recipients = recipients; }

    public List<String> getCcRecipients() { return ccRecipients; }
    public void setCcRecipients(List<String> ccRecipients) { this.ccRecipients = ccRecipients; }

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
