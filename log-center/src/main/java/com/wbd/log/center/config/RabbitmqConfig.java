package com.wbd.log.center.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wbd.cloud.model.log.constants.LogQueue;

/**
 * rabbitmq配置
 * @author zgh
 *
 */
@Configuration
public class RabbitmqConfig {

	/**
	 * 声明队列
	 */
	@Bean
	public Queue logQueue() {
		
		Queue queue = new Queue(LogQueue.LOG_QUEUE);
		
		return queue;
	}
}
