package com.wbd.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableZuulProxy //声明为网关
@EnableEurekaClient
public class GatewayApplication {

	public static void main(String[] args) {
	
    SpringApplication.run(GatewayApplication.class, args);
	}

}
