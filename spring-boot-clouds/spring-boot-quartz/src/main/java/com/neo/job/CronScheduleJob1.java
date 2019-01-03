package com.neo.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class CronScheduleJob1 implements Job {
    private   static Logger logger = LoggerFactory.getLogger(CronScheduleJob1.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info(" CronScheduleJob1 执行任务，任务时间：{ }");
    }
}
