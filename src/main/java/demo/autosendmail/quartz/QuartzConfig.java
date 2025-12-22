package demo.autosendmail.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

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
                        .withIntervalInMinutes(1)
                        .repeatForever())
                .build();
    }
}
