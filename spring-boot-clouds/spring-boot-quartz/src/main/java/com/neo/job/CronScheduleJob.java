package com.neo.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class CronScheduleJob implements Job {
    private   static Logger logger = LoggerFactory.getLogger(CronScheduleJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info(" CronScheduleJob 执行任务，任务时间：{ }");
    }
}
