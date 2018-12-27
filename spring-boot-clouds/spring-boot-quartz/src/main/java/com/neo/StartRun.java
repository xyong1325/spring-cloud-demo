package com.neo;

import com.neo.job.SampleJob;
import com.neo.scheduler.CronScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartRun implements CommandLineRunner {
    private   static Logger logger = LoggerFactory.getLogger(StartRun.class);
    @Autowired
    public CronScheduler jobs;
    @Override
    public void run(String... args) throws Exception {
        jobs.scheduleJobs();
        logger.info("定时任务启动");
    }
}
