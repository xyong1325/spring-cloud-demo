package com.neo.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class SampleJob extends QuartzJobBean {
   private   static Logger logger = LoggerFactory.getLogger(SampleJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap =   jobExecutionContext.getMergedJobDataMap();
         System.out.println( " 参数--> "+ jobDataMap.values());
         logger.info(" SampleJob 执行任务，任务时间：{ }");
    }
}
