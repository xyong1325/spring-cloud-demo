## spring-boot-feign-ribbon
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
   
   <!--添加 feign -->
     <dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-openfeign</artifactId>
   		</dependency>
   		
     <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
     </dependency>
   	
```
 2.配置中心 application.properties 添加 配置
```properties
    server.port=8766
    spring.application.name=spring-boot-feign-ribbon-hystrix
    eureka.client.service-url.defaultZone=http://39.108.48.25:8761/eureka/
    feign.hystrix.enabled=true
```

3.编辑工程启动类SpringBootRibbonApplication
   * 添加注解
  
   ```java
   @SpringBootApplication
   @EnableEurekaClient
   @EnableDiscoveryClient
   @EnableFeignClients
   public class SpringBootFeignRibbonApplication {
     
   	public static void main(String[] args) {
   		SpringApplication.run(SpringBootFeignRibbonApplication.class, args);
   	}
   }
```
4.新建service接口 IHelloFeignService、
```java
   //接口
  @FeignClient(value = "SPRING-BOOT-EUREKA-CLIENT")
  public interface IHelloFeignService {
      @RequestMapping(value = "/hi",method = RequestMethod.GET)
      public String  hello(String name);
  }

```
5.新建web层  创建 controller
```java
@RestController
public class HelloRibbonController {

    @Autowired
    IHelloFeignService helloService;
    @RequestMapping("/hii")
    public String home(@RequestParam(value = "name", defaultValue = "xy") String name) {
         return  helloService.hello(name);
    }
}
```

    