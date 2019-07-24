package com.wbd.oauth.center.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.wbd.cloud.commons.constants.PermitAllUrl;

/**
 * 资源服务器配置，请求中带access_token或者header里有Authorization的才能通过
 * 注解enableresourceserver帮我们注入了OAuth2AuthenticationProcessingFilter
 * 该filter帮我们从request里面解析出access_token 并且通过defaultTokenService
 * 根据access_token和认证服务器配置里面的tokenstore从redis或者jwt里面解析出用户
 * 注意：认证中心的enableresourceserver和别的微服务里面的enableresourceserver有些不一样
 * 别的微服务是通过UserInfoTokenServices来获取用户的
 * 
 * @author zgh
 *
 */
@Configuration
@EnableResourceServer // 开启资源服务器的注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	//请求中带access_token或者header里有Authorization的,与PermitAllUrl.permitAllUrl()都放行
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(new OAuth2RequestMatcher()).authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl())
				.permitAll().anyRequest().authenticated();
	}

	/**
	 * 请求中带access_token或者header里有Authorization的才能通过
	 * 
	 * @author zgh
	 *
	 */
	private static class OAuth2RequestMatcher implements RequestMatcher {

		@Override
		public boolean matches(HttpServletRequest request) {

			// 请求中带access_token 则返回true
			if (request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) != null) {
				return true;
			}

			// 头部的Authorization值以 Bearer开头
			String auth = request.getHeader("Authorization");
			if (auth != null) {
				return auth.startsWith(OAuth2AccessToken.BEARER_TYPE);
			}
			return false;
		}

	}
}
