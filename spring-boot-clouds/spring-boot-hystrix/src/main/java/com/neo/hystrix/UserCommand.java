package com.neo.hystrix;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserCommand extends HystrixCommand<String> {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserCommand.class);
    private String userName; ;

    protected UserCommand(String userName) {
         super(Setter.withGroupKey(
                //服务分组
                HystrixCommandGroupKey.Factory.asKey("UserGroup"))
                //线程分组
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("UserPool"))

                //线程池配置
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withKeepAliveTimeMinutes(5)
                        .withMaxQueueSize(10)
                        .withQueueSizeRejectionThreshold(10000))

                //线程池隔离
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))
        );
        this.userName=  userName;
    }

    @Override
    protected String run() throws Exception {
        LOGGER.info("userName=[{}]", userName);
        return "userName --> "+ userName ;
    }
}
