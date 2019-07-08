package com.wbd.user.center.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wbd.cloud.model.user.constants.UserCenterMQ;

import org.springframework.amqp.core.TopicExchange;
/**
 * rabbitmq配置
 * @author jwh
 *
 */
@Configuration
public class RabbitmqConfig {

	@Bean
	public TopicExchange topicExchange() {
		
		return new TopicExchange(UserCenterMQ.MQ_EXCHANGE_USER);
	}
}
