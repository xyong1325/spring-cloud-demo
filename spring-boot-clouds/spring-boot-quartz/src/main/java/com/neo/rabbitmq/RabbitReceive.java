package com.neo.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="topic.message")
public class RabbitReceive {
     @RabbitHandler
    public void process1(String str) {
        System.out.println("message:"+str);
    }
}
