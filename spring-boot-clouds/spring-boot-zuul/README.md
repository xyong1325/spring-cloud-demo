## spring-boot-zuul

1.首先创建一个maven工程。然后添加pom依赖
```xml
   <dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-web</artifactId>
   		</dependency>
   
   		<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   		</dependency>
   
           <!--添加 zuul 依赖-->
   		<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-netflix-zuul</artifactId>
   		</dependency>
```
 2.配置中心 application.properties 添加 配置
```properties

   server.port=8767
   spring.application.name=spring-boot-zuul
   eureka.client.service-url.defaultZone=http://39.108.48.25:8761/eureka/
   zuul.routes.api-a.path=/api-a/*
   zuul.routes.api-a.serviceId=SPRING-BOOT-REST-RIBBON-HYSTRIX
   zuul.routes.api-b.path=/api-b/*
   zuul.routes.api-b.serviceId=SPRING-BOOT-FEIGN-RIBBON-HYSTRIX
```

3.编辑工程启动类SpringBootZuulApplication
   * 添加注解
   * 在类中新增 通过Ioc 注入 RestTemplate
   ```java
   
   @SpringBootApplication
   @EnableEurekaClient
   @EnableDiscoveryClient
   @EnableZuulProxy
   public class SpringBootZuulApplication {
   
   	public static void main(String[] args) {
   		SpringApplication.run(SpringBootZuulApplication.class, args);
   	}
   }
```
4. 自定义 拦截器
```java

@Component
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }
    //表示是否需要执行该filter，true表示执行，false表示不执行
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        System.out.println(token);
        if(token==null){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().setHeader("Content-Type", "text/html;charset=UTF-8");
                ctx.getResponse().getWriter().write("token is empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
 }
```
 

    