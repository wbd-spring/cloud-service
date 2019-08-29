package com.wbd.log.center.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wbd.cloud.model.log.Log;
import com.wbd.cloud.model.log.constants.LogQueue;
import com.wbd.log.center.service.LogService;

/**
 * 从mq 队列中消费日志数据,最后保存到数据库中
 * 
 * @author zgh
 *
 */

@Component
@RabbitListener(queues = LogQueue.LOG_QUEUE) // 监听的队列
public class LogConsumer {

	@Autowired
	private LogService ls;

	private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);

	/**
	 * 当监听到队列中有数据时，所触发的函数调用
	 * 
	 * @param log
	 */
	@RabbitHandler
	public void logHandler(Log log) {

		try {
			ls.save(log);
		} catch (Exception e) {
			logger.error("保存日志失败，日志{},异常：{}", log, e);
		}
	}
}
