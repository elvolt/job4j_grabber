package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        Properties config = Config.load("rabbit.properties");
        int interval = Integer.parseInt(config.getProperty("rabbit.interval"));
        try (Connection connection = ConnectionCreator.create(config)) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        public Rabbit() {
            System.out.println(this.hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            String sql = "INSERT INTO rabbit (created_at) VALUES (?)";
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement statement = cn.prepareStatement(sql)) {
                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
