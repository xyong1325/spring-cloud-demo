## spring-boot-config-bus-client
#####_创建服务_
1.首先创建一个maven主工程。然后添加pom依赖
```xml
   <dependencies>
   
        <dependency>
      			<groupId>org.springframework.boot</groupId>
      			<artifactId>spring-boot-starter-web</artifactId>
      	</dependency>
      	
   		<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-config</artifactId>
   		</dependency>
     		
   		<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   		</dependency>
   
   		<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-bus-amqp</artifactId>
   		</dependency>
   
   		<dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-actuator</artifactId>
   		</dependency>
   
   	</dependencies>
```
2. application.yml 添加 配置
```properties
    spring.application.name=spring-boot-config-client-bus  #服务名称
    server.port=8882                                       #服务端口号
```
3.bootstrap.properites 配置
```properties

    spring.cloud.config.name=spring-boot-config-client          #服务器git仓库 文件地址名称  
    spring.cloud.config.label=master                            # git 仓库分支
    spring.cloud.config.profile=dev                             
    spring.cloud.config.discovery.enabled=true                  # 表示开启通过服务名来访问 spring-boot-config-bus-server 
    spring.cloud.config.discovery.serviceId=SPRING-BOOT-CONFIG-BUS-SERVER   # config-server 实例服务名称
    eureka.client.service-url.defaultZone=http://39.108.48.25:8761/eureka/  # 注册中心
    
   # rabbitmq  注册信息 
    spring.rabbitmq.host=39.108.48.25                                 
    spring.rabbitmq.username=neo
    spring.rabbitmq.password=123456
    
    
    spring.cloud.bus.enabled=true
    spring.cloud.bus.trace.enabled=true
    management.endpoints.web.exposure.include=bus-refresh

```
3.启动一个服务中心 添加 注解@EnableEurekaServer
```java
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
@EnableEurekaClient
public class SpringBootConfigBusServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootConfigBusServerApplication.class, args);
	}
}
```

