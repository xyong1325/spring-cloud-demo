package com.neo.service.impl;

import com.neo.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
@Service
public class HelloServiceImpl implements IHelloService {
    @Autowired
    RestTemplate restTemplate;
    @Override
    public String hello(String name) {
        return restTemplate.getForObject("http://SPRING-BOOT-EUREKA-CLIENT/hi?name="+name,String.class);
    }
}
