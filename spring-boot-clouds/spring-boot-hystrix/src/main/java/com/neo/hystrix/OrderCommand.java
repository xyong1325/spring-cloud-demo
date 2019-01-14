package com.neo.hystrix;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OrderCommand extends HystrixCommand<String> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderCommand.class);
    private String orderName ;

    protected OrderCommand(String orderName) {
         super(Setter.withGroupKey(
                //服务分组
                HystrixCommandGroupKey.Factory.asKey("OrderGroup"))
                //线程分组
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("OrderPool"))

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
        this.orderName=  orderName;
    }

    @Override
    protected String run() throws Exception {
        LOGGER.info("orderName=[{}]", orderName);
        return "orderName --> "+ orderName ;
    }
}
