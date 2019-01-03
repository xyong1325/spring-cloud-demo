package com.neo;

import com.neo.scheduler.CronScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*@Configuration
@EnableScheduling    //启动定时任务
@Component*/
public class JobsListener {

  /*  @Autowired
    public CronScheduler jobs;
    @Scheduled(cron="0 0 12 * * ?")
    public  void starJobs() throws  Exception {
        jobs.scheduleJobs();
    }*/
}
