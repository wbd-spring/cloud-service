package com.wbd.log.starter.autoconfigure;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wbd.cloud.model.log.constants.LogQueue;

/**
 * 自定义配置注解
 * @author zgh
 *
 */
@Configuration
public class LogAutoConfiguration {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
	/**
	 * 队列声明
	 * 
	 * 如果日志系统启动，或者mq上已经存在队列logQueue.log_queue
	 * 此处不用声明此队列
	 * ，此处声明只是为了防止日志系统启动前，并且没有队列logQueue.log_queue的情况下丢失信息
	 */
	
	@Bean
	public Queue logQueue() {
		return new Queue(LogQueue.LOG_QUEUE);
	}
	
	@Bean
	public LogMqClient logMqClient() {
		return new LogMqClient(amqpTemplate);
	}
}


