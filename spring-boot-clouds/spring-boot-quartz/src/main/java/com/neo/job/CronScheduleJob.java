package com.neo.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class CronScheduleJob implements Job {
    private   static Logger logger = LoggerFactory.getLogger(CronScheduleJob.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        rabbitTemplate.convertAndSend("exchange","topic.message","hello,rabbitmq!");

        logger.info(" CronScheduleJob 执行任务，任务时间：{ 99 }");
    }
}
