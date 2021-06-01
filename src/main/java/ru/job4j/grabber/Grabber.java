package ru.job4j.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.html.Post;
import ru.job4j.html.SqlRuParse;
import ru.job4j.quartz.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber implements Grab {
    private static final Properties CFG;

    static {
        CFG = Config.load("app.properties");
    }

    public Store store() {
        return new PsqlStore(CFG);
    }

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(CFG.getProperty("time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    public static class GrabJob implements Job {

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parse parse = (Parse) map.get("parse");
            List<Post> posts = new ArrayList<>();
            posts.addAll(parse.list("https://www.sql.ru/forum/job-offers/1"));
            posts.addAll(parse.list("https://www.sql.ru/forum/job-offers/2"));
            posts.addAll(parse.list("https://www.sql.ru/forum/job-offers/3"));
            posts.addAll(parse.list("https://www.sql.ru/forum/job-offers/4"));
            posts.addAll(parse.list("https://www.sql.ru/forum/job-offers/5"));
            List<Post> javaPosts = posts.stream()
                    .filter(post -> {
                        String header = post.getHeader().toLowerCase();
                        return header.matches(".*\\bjava\\b.*")
                                && !header.matches(".*java script.*");
                    })
                    .collect(Collectors.toList());
            javaPosts.forEach(store::save);
        }
    }

    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        Scheduler scheduler = grab.scheduler();
        Store store = grab.store();
        grab.init(new SqlRuParse(), store, scheduler);
    }
}
