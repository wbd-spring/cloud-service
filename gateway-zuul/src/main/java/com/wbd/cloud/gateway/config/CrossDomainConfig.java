package com.wbd.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * cors跨域请求 跨域资源共享(CORS)
 * 
 * 跨域配置
 * 页面访问和后端接口地址的域名不一致时，会先发起一个options的试探请求
 * 如果不设置跨域，js将无法正确访问接口， 域名一致，不存在这个问题
 * @author zgh
 *
 */
@Configuration
public class CrossDomainConfig {

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	     config.setAllowCredentials(true); //允许cookies跨域
	     config.addAllowedOrigin("*");//允许访问的头信息，*表示所有
	     config.setMaxAge(18000L);//预检查请求的缓存时间(秒),即在在这个时间段内，对应相同的跨域请求不会在检查
	     config.addAllowedMethod("*");//表示允许提交的请求方法
	     source.registerCorsConfiguration("/**", config);
	     return new CorsFilter(source);
	}
	 //两种方式任选其一即可
//  @Bean
//  public WebMvcConfigurer corsConfigurer() {
//      return new WebMvcConfigurer() {
//          @Override
//          public void addCorsMappings(CorsRegistry registry) {
//              registry.addMapping("/**") // 拦截所有权请求
//                      .allowedMethods("*") // 允许提交请求的方法，*表示全部允许
//                      .allowedOrigins("*") // #允许向该服务器提交请求的URI，*表示全部允许
//                      .allowCredentials(true) // 允许cookies跨域
//                      .allowedHeaders("*") // #允许访问的头信息,*表示全部
//                      .maxAge(18000L); // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
//          }
//      };
//  }
}
