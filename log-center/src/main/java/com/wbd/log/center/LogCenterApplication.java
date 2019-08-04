package com.wbd.log.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 日志中心
 * 
 * @author zgh
 *
 */
@EnableEurekaClient
@SpringBootApplication
public class LogCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogCenterApplication.class, args);
	}
}
