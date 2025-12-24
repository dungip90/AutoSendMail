package demo.autosendmail.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    // đọc giá trị từ application.properties, mặc định 10 nếu không cấu hình
    @Value("${mail.job.interval.minutes:10}")
    private int intervalMinutes;

    @Bean
    public JobDetail mailDispatchJobDetail() {
        return JobBuilder.newJob(MailDispatchJob.class)
                .withIdentity("mailDispatchJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger mailDispatchTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(mailDispatchJobDetail())
                .withIdentity("mailDispatchTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(intervalMinutes) // đảm bảo > 0
                        .repeatForever())
                .build();
    }
}
