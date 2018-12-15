package com.neo.web;


import com.neo.service.IHelloFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRibbonFeignController {

    @Autowired
    IHelloFeignService helloFeignService;
    @RequestMapping("/hii")
    public String home(@RequestParam(value = "name", defaultValue = "xy") String name) {
         return  helloFeignService.hello(name);
    }

}
