package com.wbd.log.starter.autoconfigure;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.wbd.cloud.commons.utils.AppUserUtil;
import com.wbd.cloud.model.log.Log;
import com.wbd.cloud.model.log.LogAnnotation;
import com.wbd.cloud.model.log.constants.LogQueue;
import com.wbd.cloud.model.user.LoginAppUser;

/**
 * aop实现日志
 * @author zgh
 *
 */
@Aspect //定义一个切面
public class LogAop {

	private static final Logger logger = LoggerFactory.getLogger(LogAop.class);
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	/**
	 * 环绕带注解 @LogAnnotation的方法做aop
	 * @param joinPoint
	 * @return
	 * @throws Throwable 
	 */
	@Around(value="@annotation(com.wbd.cloud.model.log.LogAnnotation)")
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Log log = new Log();
		log.setCreateTime(new Date());
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if(loginAppUser!=null) {
			log.setUsername(loginAppUser.getUsername());
		}
		//获取方法签名
		MethodSignature  methodSignature = (MethodSignature) joinPoint.getSignature();
		//获取方法上面 的@LogAnnotation注解， getDeclaredAnnotation通过方法上声明的注解获取具体的注解
		LogAnnotation logAnnotation =methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
		//通过获取方法上的注解， 来获取所声明的模块
		log.setModule(logAnnotation.module());
		
		//是否需要记录方法的参数数据
		
		if(logAnnotation.recordParam()) {//默认需要记录
			
			//通过methodSignature 方法签名获取参数名称
		String[] paramNames = 	methodSignature.getParameterNames();
		System.out.println("所有参数名称="+paramNames);
		if(paramNames!=null && paramNames.length>0) {
		
			//通过joinpoint获取所有的参数值
			Object[] args=	joinPoint.getArgs();
			
			System.out.println("所有参数值="+args);
			Map<String,Object> params = new HashMap<String,Object>();
			for(int i=0;i<paramNames.length;i++) {
				Object value = args[i];
				
				if(value instanceof Serializable) {
					params.put(paramNames[i], value);
				}
			}
			//以json的形式记录参数
			log.setParams(JSONObject.toJSONString(params));
		}
		
		}
		
		
	  //执行原方法
		try {
			Object object = joinPoint.proceed();
			log.setFlag(Boolean.TRUE);
			return object;
		} catch (Throwable e) {//方法执行失败
			log.setFlag(Boolean.FALSE);
			log.setRemark(e.getMessage());//备注记录失败原因
			throw e;
		}finally {
			
			//采用异步将log对象发送到队列
			CompletableFuture.runAsync(()->{
				
				try {
					amqpTemplate.convertAndSend(LogQueue.LOG_QUEUE,log);
				   logger.info("发送日志到队列：{ }",log);
				} catch (AmqpException e) {
					e.printStackTrace();
				}
			});
		}
		
		
	}
	
}
