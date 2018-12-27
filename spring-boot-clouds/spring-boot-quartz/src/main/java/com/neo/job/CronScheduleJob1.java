package com.neo.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronScheduleJob1 implements Job {
    private   static Logger logger = LoggerFactory.getLogger(CronScheduleJob1.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info(" CronScheduleJob1 执行任务，任务时间：{ }");
    }
}
