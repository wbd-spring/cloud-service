package com.wbd.log.center.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置， 启用异步
 * 
 * @author zgh
 *
 */
@EnableAsync(proxyTargetClass = true)
@Configuration
public class AsyncTaskExecutorConfig {
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(100);
		taskExecutor.setMaxPoolSize(200);
		return taskExecutor;
	}
}
