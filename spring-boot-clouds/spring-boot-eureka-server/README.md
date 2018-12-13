## spring-boot-eureka-server
#####_创建服务注册中心_
1.首先创建一个maven主工程。然后添加pom依赖
```xml
   	<dependencies>
   		<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   		</dependency>
   	</dependencies>
```
2. application.properties 添加 配置
```properties
    server.port=8761
    #注册服务IP地址
    eureka.instance.hostname=localhost
    #服务注册地址
    eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
    #因为自己是服务祖册中心，所以不想注册中心发起注册
    eureka.client.register-with-eureka=false
    eureka.client.fetch-registry=false
    #注册服务名称
    spring.application.name=spring-boot-eureka-server
```
3.启动一个服务注册中心 添加 注解@EnableEurekaServer
```java
@SpringBootApplication
@EnableEurekaServer
public class SpringBootEurekaServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootEurekaServelApplication.class, args);
	}
}

```
