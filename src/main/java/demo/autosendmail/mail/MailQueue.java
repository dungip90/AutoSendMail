package demo.autosendmail.mail;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "mail_queue")
public class MailQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nhiều người nhận chính
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mail_queue_recipients", joinColumns = @JoinColumn(name = "mail_queue_id"))
    @Column(name = "recipient")
    private List<String> recipients;

    // nhiều người nhận CC
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mail_queue_cc", joinColumns = @JoinColumn(name = "mail_queue_id"))
    @Column(name = "cc_recipient")
    private List<String> ccRecipients;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    private Instant createdAt = Instant.now();
    private Instant scheduledAt;

    // số lần thử gửi, mặc định 0
    private Integer attempts = 0;

    // trạng thái mặc định
    private String status = "PENDING";

    @Column(columnDefinition = "TEXT")
    private String lastError;

    // ===== Getter/Setter =====
    public Long getId() { return id; }

    public List<String> getRecipients() { return recipients; }
    public void setRecipients(List<String> recipients) { this.recipients = recipients; }

    public List<String> getCcRecipients() { return ccRecipients; }
    public void setCcRecipients(List<String> ccRecipients) { this.ccRecipients = ccRecipients; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(Instant scheduledAt) { this.scheduledAt = scheduledAt; }

    public Integer getAttempts() { return attempts; }
    public void setAttempts(Integer attempts) { this.attempts = attempts; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLastError() { return lastError; }
    public void setLastError(String lastError) { this.lastError = lastError; }
}
