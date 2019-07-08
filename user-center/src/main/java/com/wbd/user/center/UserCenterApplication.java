package com.wbd.user.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户中心
 * @author zgh
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserCenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}

}
