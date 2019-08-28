package com.wbd.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 文档
 * 
 * @author jwh
 *
 */
@Configuration
@EnableSwagger2 // swagger2文档注解
public class SwaggerConfig {

	@Bean
	public Docket docket() {

		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("网关中心文档").apiInfo(new ApiInfoBuilder().title("用户中心文档")
						.contact(new Contact("朱光和", "", "285917033@qq.com")).version("1.0").build())
				.select().paths(PathSelectors.any()).build();

	}
}
