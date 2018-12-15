package com.neo.service.hystrix;

import com.neo.service.IHelloFeignService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class HelloFeignHystrixService implements IHelloFeignService {
    @Override
    public String hello(String name) {
        return "sorry hystrix feign  "+name;
    }
}
