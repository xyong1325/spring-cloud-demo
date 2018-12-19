## spring-boot-config-bus-server
#####_创建服务注册中心_
1.首先创建一个maven主工程。然后添加pom依赖
```xml
   	<dependencies>
   	        <dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    		</dependency>
   		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
   	</dependencies>
```
2. application.yml 添加 配置
```yml
    server:
      port: 8889            
    spring:
      application:
        name: spring-boot-config-bus-server
      cloud:
        config:
          server:
            git:
              uri: https://github.com/xyong1325/spring-cloud-demo.git     # 配置git仓库的地址
              search-paths: spring-boot-clouds/config-res                # git仓库地址下的相对地址，可以配置多个，用,分割。
              username:                                                   # git仓库的账号
              password:                                                   # git仓库的密码
          label: master
    eureka:
      client:
        serviceUrl:
          defaultZone: http://39.108.48.25:8761/eureka/
          
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

