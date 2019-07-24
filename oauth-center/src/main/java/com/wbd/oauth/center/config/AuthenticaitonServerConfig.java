package com.wbd.oauth.center.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
/**
 * 授权/认证服务器
 * 1.令牌存储方式，设置保存token机制，一共有5种， 比如内存，redis、数据库， 我们采用数据库存储方式
 * 2.客户端信息来源（一般从数据库， 或者redis，内存中，）
 * @author jwh
 *
 */
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
@Configuration
@EnableAuthorizationServer //开启认证服务器注解
public class AuthenticaitonServerConfig extends AuthorizationServerConfigurerAdapter {

	/**
	 * 认证管理器
	 * @see SecurityConfig 的authenticationManagerBean方法
	 */
	
	@Autowired
	private AuthenticationManager authenticationManager;

	//token采用redis存储
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

     /**
      * token存储的机制， 可以存储在数据库，redis，或者用jwt方式
      */
    
    @Bean
    public TokenStore tokenStore() {
    	
    	RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
    	
    	return redisTokenStore;
    }
}
