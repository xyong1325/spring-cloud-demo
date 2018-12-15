## spring-boot-reset-ribbon
1.首先创建一个maven主工程。然后添加pom依赖
```xml
   <dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-web</artifactId>
   	</dependency>
   
   	<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   	</dependency>
   	<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
   	</dependency>
```
 2.配置中心 application.properties 添加 配置
```properties
    server.port=8763
    spring.application.name=spring-boot-rest-ribbon
    eureka.client.service-url.defaultZone=http://39.108.48.25:8761/eureka/
```

3.编辑工程启动类SpringBootRibbonApplication
   * 添加注解
   * 在类中新增 通过Ioc 注入 RestTemplate
   ```java
   @SpringBootApplication
   @EnableEurekaClient
   @EnableDiscoveryClient
   public class SpringBootRibbonApplication {
   
   	@Bean
   	@LoadBalanced
   	public RestTemplate restTemplate() {
   		return new RestTemplate();
   	}
   	public static void main(String[] args) {
   		SpringApplication.run(SpringBootRibbonApplication.class, args);
   	}
   }
```
4.新建service接口 IHelloService、以及service 实现 HelloServiceImpl
```java
   //接口
   public interface IHelloService {
       public String  hello(String  name);
   }


   @Service
   public class HelloServiceImpl implements IHelloService {
       @Autowired
       RestTemplate restTemplate;
       @Override
       public String hello(String name) {
           return restTemplate.getForObject("http://SPRING-BOOT-EUREKA-CLIENT/hi?name="+name,String.class);
       }
   }
```
5.新建web层  创建 controller
```java
@RestController
public class HelloRibbonController {

    @Autowired
    IHelloService helloService;
    @RequestMapping("/hii")
    public String home(@RequestParam(value = "name", defaultValue = "xy") String name) {
         return  helloService.hello(name);
    }

}
```

    