-- Bảng mail_queue
CREATE TABLE IF NOT EXISTS mail_queue (
                                          id BIGSERIAL PRIMARY KEY,
                                          subject VARCHAR(255) NOT NULL,
                                          body TEXT NOT NULL,
                                          created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                          scheduled_at TIMESTAMP NULL,
                                          attempts INT NOT NULL DEFAULT 0,
                                          status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                                          last_error TEXT NULL
);

-- Bảng phụ lưu danh sách recipients cho mail_queue
CREATE TABLE IF NOT EXISTS mail_queue_recipients (
                                                     mail_queue_id BIGINT NOT NULL REFERENCES mail_queue(id) ON DELETE CASCADE,
                                                     recipient VARCHAR(255) NOT NULL
);

-- Bảng phụ lưu danh sách CC cho mail_queue
CREATE TABLE IF NOT EXISTS mail_queue_cc (
                                             mail_queue_id BIGINT NOT NULL REFERENCES mail_queue(id) ON DELETE CASCADE,
                                             cc_recipient VARCHAR(255) NOT NULL
);

-- Bảng mail_history
CREATE TABLE IF NOT EXISTS mail_history (
                                            id BIGSERIAL PRIMARY KEY,
                                            queue_id BIGINT NOT NULL REFERENCES mail_queue(id) ON DELETE CASCADE,
                                            subject VARCHAR(255) NOT NULL,
                                            body TEXT NOT NULL,
                                            sent_at TIMESTAMP NOT NULL,
                                            status VARCHAR(50) NOT NULL,
                                            error TEXT NULL
);

-- Bảng phụ lưu danh sách recipients cho mail_history
CREATE TABLE IF NOT EXISTS mail_history_recipients (
                                                       mail_history_id BIGINT NOT NULL REFERENCES mail_history(id) ON DELETE CASCADE,
                                                       recipient VARCHAR(255) NOT NULL
);

-- Bảng phụ lưu danh sách CC cho mail_history
CREATE TABLE IF NOT EXISTS mail_history_cc (
                                               mail_history_id BIGINT NOT NULL REFERENCES mail_history(id) ON DELETE CASCADE,
                                               cc_recipient VARCHAR(255) NOT NULL
);
