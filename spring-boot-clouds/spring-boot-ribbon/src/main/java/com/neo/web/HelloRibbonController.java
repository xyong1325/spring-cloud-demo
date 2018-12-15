package com.neo.web;


import com.neo.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRibbonController {

    @Autowired
    IHelloService helloService;
    @RequestMapping("/hii")
    public String home(@RequestParam(value = "name", defaultValue = "xy") String name) {
         return  helloService.hello(name);
    }

}
