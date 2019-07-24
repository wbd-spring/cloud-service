package com.wbd.oauth.center.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.wbd.oauth.center.service.impl.RandomAuthenticationKeyGenerator;

/**
 * 授权/认证服务器 
 * 1.令牌存储方式，设置保存token机制，一共有5种， 比如内存，redis、数据库， 我们采用数据库存储方式
 * 2.客户端信息来源（一般从数据库， 或者redis，内存中，）
 * 
 * @author jwh
 *
 */
@Configuration
@EnableAuthorizationServer // 开启认证服务器注解
public class AuthenticaitonServerConfig extends AuthorizationServerConfigurerAdapter {

	/**
	 * 认证管理器
	 * 
	 * @see SecurityConfig 的authenticationManagerBean方法
	 */

	@Autowired
	private AuthenticationManager authenticationManager;

	// token采用redis存储
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	private DataSource dataSource;

	// access_tokean使用jwt或者redis,默认是false，采用redis方式， 不采用jwt方式
	@Value("${access_token.store-jwt:false}")
	private boolean storeWithJwt;

	/**
	 * 声明令牌存储 token存储的机制， 可以存储在数据库，redis，或者用jwt方式
	 */

	@Bean
	public TokenStore tokenStore() {
		if (storeWithJwt) {// 采用jwt
			return new JwtTokenStore(accessTokenConverter());
		}
		// 采用redis
		RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
		// 解决同一username每次登陆access-token都相同的问题
		redisTokenStore.setAuthenticationKeyGenerator(new RandomAuthenticationKeyGenerator());

		return redisTokenStore;
	}

	/**
	 * jwt令牌access_token 转换器
	 * 
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {

		return new JwtAccessTokenConverter() {

			// enhance 加强， 增大
			/**
			 * 重写增强token的方法 accessToken 资源令牌 authentication 认证
			 *
			 * return 增强的oauthaccessstoken对象
			 */
			@Override
			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
				// 从认证登录凭证拿到登录凭证
				User user = (User) authentication.getUserAuthentication().getPrincipal();
				Map<String, Object> infoMap = new HashMap<String, Object>();
				infoMap.put("userName", user.getUsername());
				((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(infoMap);
				return super.enhance(accessToken, authentication);
			}
		};
	}

	// 通过configure方法，关联令牌存储于认证管理器
	// 通过认证服务器配置端点来设置认证管理器与token存储方式
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// 配置端点的认证管理器
		endpoints.authenticationManager(authenticationManager);
		// 配置端点的token存储方式
		endpoints.tokenStore(tokenStore());
		if (storeWithJwt) {
			endpoints.accessTokenConverter(accessTokenConverter());
		}
	}

	/**
	 * 允许表单形式的认证
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
	}

	/**
	 * 认证的client来源，我们暂时基于数据库(oauth_client_details这个表)，可以是基于内存， 也可以基于数据库， 也可以基于redis
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	//	clients.inMemory().withClient("system").secret(bCryptPasswordEncoder.encode("system"))
//		.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("app")
//		.accessTokenValiditySeconds(3600);
		clients.jdbc(dataSource);
	}
}
