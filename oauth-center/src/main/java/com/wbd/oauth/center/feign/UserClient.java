package com.wbd.oauth.center.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wbd.cloud.model.user.LoginAppUser;

/**
 * feign接口， 调用user-center微服务
 * @author zgh
 *
 */
@FeignClient("user-center")//表示调用user-center的微服务，
public interface UserClient {

	//调用user-center微服务的控制器
	@GetMapping(value="/users-anon/internal",params = "username")
	LoginAppUser findByUsername(@RequestParam("username") String username);
	
}
