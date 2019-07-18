package com.wbd.oauth.center.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger文档配置
 * 
 * @author zgh
 *
 */
@Configuration
@EnableSwagger2 // 开启swagger2文档注解
public class SwaggerConfig {

	@Bean
	public Docket docket() {

		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("认证中心文档").apiInfo(new ApiInfoBuilder().title("认证中心文档")
						.contact(new Contact("朱光和", "", "285917033@qq.com")).version("1.0").build())
				.select().paths(PathSelectors.any()).build();
	}

}
