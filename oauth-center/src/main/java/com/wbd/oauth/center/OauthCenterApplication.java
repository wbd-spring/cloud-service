package com.wbd.oauth.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients //开启feign功能
@EnableEurekaClient  //开启eureka-client
@SpringBootApplication
public class OauthCenterApplication {
	

	public static void main(String[] args) {
		
	SpringApplication.run(OauthCenterApplication.class, args);
	
	}
	
	

}
