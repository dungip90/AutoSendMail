package demo.autosendmail.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface MailQueueRepository extends JpaRepository<MailQueue, Long> {
    @Query("SELECT m FROM MailQueue m " +
            "WHERE m.status = 'PENDING' " +
            "AND (m.scheduledAt IS NULL OR m.scheduledAt <= :now)")
    List<MailQueue> findReadyToSend(Instant now);

    List<MailQueue> findByStatus(String pending);
}