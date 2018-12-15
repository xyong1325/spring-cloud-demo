package com.neo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "SPRING-BOOT-EUREKA-CLIENT")
public interface IHelloFeignService {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String  hello(String name);
}
