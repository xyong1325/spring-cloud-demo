package com.neo.scheduler;

import com.neo.job.SampleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleScheduler  {


/*    *//**
     * 创建定时任务
     * @return
     *//*
    @Bean
   public JobDetail sampleJobDetail(){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("context","我是参数");
        return JobBuilder.newJob(SampleJob.class).usingJobData(jobDataMap).storeDurably().build();
    }

    *//**
     * 创建任务触发器
     * @return
     *//*
    @Bean
    public Trigger  sampleJobTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever();
        return  TriggerBuilder.newTrigger().forJob(sampleJobDetail()).withSchedule(scheduleBuilder).build();
    }*/
}
