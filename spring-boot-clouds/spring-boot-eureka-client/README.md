## spring-boot-eureka-client
#####_创建服务注册中心_
1.首先创建一个maven主工程。然后添加pom依赖
```xml
   	<dependencies>
   		<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   		</dependency>
   	</dependencies>
```
2. application.properties 添加 配置
```properties
    server.port=8762
    spring.application.name=spring-boot-eureka-client
    eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```
3.启动一个服务注册客户端 添加注解@EnableEurekaClient
```java
@EnableEurekaClient
@SpringBootApplication
public class SpringBootEurekaClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootEurekaClientApplication.class, args);
	}
}

```
