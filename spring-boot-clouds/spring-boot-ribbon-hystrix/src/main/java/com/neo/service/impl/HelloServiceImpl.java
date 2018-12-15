package com.neo.service.impl;

import com.neo.service.IHelloService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
@Service
public class HelloServiceImpl implements IHelloService {
    @Autowired
    RestTemplate restTemplate;
    @Override
     @HystrixCommand(fallbackMethod = "error")
    public String hello(String name) {
        return restTemplate.getForObject("http://SPRING-BOOT-EUREKA-CLIENT/hi?name="+name,String.class);
    }

    public String error(String name){
        return "hi,"+name+",sorry,error!";
    }

}
