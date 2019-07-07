package com.wbd.config.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableConfigServer //配置中心   ,配置中心服务器必须声明@EnableConfigServer注解和引入配置中心服务器所依赖的jar， 而配置中心服务器不需要声明任何注解， 但是需要引入配置中心客户端所依赖的jar
public class ConfigCenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigCenterApplication.class, args);
	}

}
