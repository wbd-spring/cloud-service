package com.wbd.oauth.center.service.impl;

import java.util.UUID;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
/**
 * 解决同一username每次登陆access-token都相同的问题
 * 源代码在RedisTokenStore的getAccessToken
 * @author jwh
 *
 */
public class RandomAuthenticationKeyGenerator implements AuthenticationKeyGenerator{

	@Override
	public String extractKey(OAuth2Authentication authentication) {
		return UUID.randomUUID().toString();
	}

}
