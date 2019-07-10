package com.wbd.cloud.commons.utils;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.alibaba.fastjson.JSONObject;
import com.wbd.cloud.model.user.LoginAppUser;

/**
 * 通过SecurityContextHolder获取登录用户的相关信息
 * 
 * @author zgh
 *
 */
public class AppUserUtil {

	/**
	 * 获取登录的LoginAppUser
	 */

	public static LoginAppUser getLoginAppUser() {
		// 通过SecurityContextHolder获取Authentication接口
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof OAuth2Authentication) {
			// 把Authentication转换为OAuth2Authentication
			// OAuth2Authentication实现了Authentication接口
			OAuth2Authentication oauth2Auth = (OAuth2Authentication) authentication;

			authentication = oauth2Auth.getUserAuthentication();

			if (authentication instanceof UsernamePasswordAuthenticationToken) {

				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
				Object principal = authentication.getPrincipal();

				if (principal instanceof LoginAppUser) {
					return (LoginAppUser) principal;
				}

				Map map = (Map) authenticationToken.getDetails();

				map = (Map) map.get("principal");

				return JSONObject.parseObject(JSONObject.toJSONString(map), LoginAppUser.class);
			}

		}
		return null;
	}
}
