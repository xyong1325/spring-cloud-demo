package com.neo.springbootsleuthservicea.web;

import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/hi")
    public String callHome(){
        return restTemplate.getForObject("http://localhost:8769/miya", String.class);
    }
    @RequestMapping("/info")
    public String info(){
        return "i'm spring boot sleuth servic a ";

    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
