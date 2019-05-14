package com.wbd.config.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableConfigServer //配置中心
public class ConfigCenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigCenterApplication.class, args);
	}

}
