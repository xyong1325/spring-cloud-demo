package com.neo.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceive {
    @RabbitListener(queues="topic.message")
    public void process1(String str) {
        System.out.println("message:"+str);
    }
}
