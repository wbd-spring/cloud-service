package com.wbd.oauth.center.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class Oauth2Controller {

	//
	//他的类型为redis存储类型
	/**
	 * 我们在认证服务器配置中已经注入过TokenStore，他的类型为redis存储类型
	 * @see AuthenticaitonServerConfig.tokenStore()
	 */
	@Autowired
	private TokenStore tokenStore;
	
	/**
	 * 鉴权接口，
	 * 获取用户信息
	 * @param principal
	 * @return
	 */
	@GetMapping("/user-me")
	public Principal principal(Principal principal) {
		log.debug("user-me:{}",principal.getName());
	
		return principal;
	}
	
	/**
	 * 退出接口
	 * 移除accesstoken和refresh_token
	 * @param principal
	 * @param access_token
	 */
	@DeleteMapping(value="/remove_token",params="access_token")
	public void removeToken(Principal principal,String access_token) {
		//获取到access_token对象
		OAuth2AccessToken  accessToken = tokenStore.readAccessToken(access_token);
	   if(accessToken!=null) {
		   //移除access_token
		   tokenStore.removeAccessToken(accessToken);
		   tokenStore.removeRefreshToken(accessToken.getRefreshToken());
	   }
	
	}
}
