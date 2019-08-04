package com.wbd.cloud.model.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解，自定义注解类
 * @author zgh
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

	/**
	 * 日志模块
	 * @see com.wbd.cloud.model.log.constants.LogModule
	 */
	
	String module();
	
	/**
	 * 记录参数
	 * 尽量记录普通参数类型的方法，和能序列化的对象
	 * @return
	 */
	boolean recordParam() default true;
}
