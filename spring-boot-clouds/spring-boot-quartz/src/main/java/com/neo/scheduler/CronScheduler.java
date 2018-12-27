package com.neo.scheduler;

import com.neo.job.CronScheduleJob;
import com.neo.job.CronScheduleJob1;
import com.neo.job.SampleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class CronScheduler {


    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

   private  void job1(Scheduler scheduler)  throws Exception{

           JobDetail jobDetail =  JobBuilder.newJob(CronScheduleJob.class).build();
           CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/6 * * * * ?");
           CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();
           scheduler.scheduleJob(jobDetail,cronTrigger);

   }

    private  void job2(Scheduler scheduler)  throws Exception{
        JobDetail jobDetail =  JobBuilder.newJob(CronScheduleJob1.class).build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/12 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);

    }


    public  void scheduleJobs ()  throws Exception{
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        job1(scheduler);
        job2(scheduler);
    }
}
