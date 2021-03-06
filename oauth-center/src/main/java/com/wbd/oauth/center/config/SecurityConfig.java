package com.wbd.oauth.center.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wbd.cloud.commons.constants.PermitAllUrl;

/**
 * 用户校验，当用户过来了之后，我们怎么去验证他的合法性
 * spring security 配置
 * 
 * 1.配置验证用户名与密码的方式 
 * 2.认证管理器 
 * 3.http安全 
 * 4.web资源过滤(可以不配置)
 * 
 * @author jwh
 *
 */
@EnableWebSecurity //spring security 开启注解
@EnableGlobalMethodSecurity(prePostEnabled = true) //prePostEnable=true开启方法类型的权限注解
// @EnableGlobalMethodSecurity已经包含了@Configuration注解， 所以不用额外再添加该注解了
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 自动注入UserDetailsService 的自定义实现类UserDetailServiceImpl，
	// 该类调用feign接口，其实是调用了user-center的接口，通过用户名查询用户是否存在

	@Autowired
	private UserDetailsService userDetailsService;

	// 注入密码解析器，在微服务 用户中心里面我们用的是这个密码器进行加密的， 所以我们在这里（认证中心）用他来解密
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 验证用户名和密码
	 *  验证用户名调用userDetailsService的loadUserByUsername，我们重写了该方法，该方法
	 *  调用了用户中心的接口
	 *  验证密码，采用系统的框架的底层jar进行验证，@see AbstractUserDetailsAuthenticationProvider.additionalAuthenticationChecks()的方法验证密码， 我们传入，密码解析器即可
	 * 方法上的注解@Autowired的意思是，方法的参数值从spring容器中获取
	 * 即参数AuthenticationManagerBuilder是spring中的一个bean
	 * 
	 */
	@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//passwordEncoder解密
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	
	/**
	 * 注入认证管理器
	 * 
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * http安全配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl()).permitAll() // 放开权限的url
				.anyRequest().authenticated().and().httpBasic().and().csrf().disable();
	}

	// web资源的过滤，比如css,js,图片，认证中心没有页面，所以不需要配置
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
}
