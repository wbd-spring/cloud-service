package com.wbd.cloud.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wbd.cloud.model.log.Log;

@FeignClient("log-center")
public interface LogClient {
	
	@PostMapping("/logs-anno/internal")
    void save(@RequestBody Log log);
	
}
