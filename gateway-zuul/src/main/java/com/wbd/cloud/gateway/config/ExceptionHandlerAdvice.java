package com.wbd.cloud.gateway.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.fastjson.JSONObject;
import com.netflix.client.ClientException;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * feign接口调用失败出现异常时所处理的类
 * 
 * @author zgh
 *
 */
@Slf4j
@RestControllerAdvice //该注解表示异常捕获，拦截异常并统一处理
public class ExceptionHandlerAdvice {

	@ExceptionHandler({ FeignException.class })
	public Map<String, Object> feignException(FeignException exception, HttpServletResponse response) {
		int httpStatus = exception.status();
		if (httpStatus >= HttpStatus.INTERNAL_SERVER_ERROR.value()) // 500错误
		{
			log.error("feignClient调用异常", exception);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		String msg = exception.getMessage();
		if (!StringUtils.isEmpty(msg)) {

			int index = msg.indexOf("\n");
			if (index > 0) {
				String string = msg.substring(index);

				if (!StringUtils.isEmpty(string)) {
					JSONObject json = JSONObject.parseObject(string.trim());
					data.putAll(json.getInnerMap());
				}
			}
		}

		if (data.isEmpty()) {
			data.put("message", msg);
		}

		data.put("code", httpStatus + "");
		response.setStatus(httpStatus);

		return data;
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> badRequest(IllegalArgumentException e) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("code", HttpStatus.BAD_REQUEST);
		data.put("message", e.getMessage());
		return data;
	}

	@ExceptionHandler({ ClientException.class, Throwable.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> serverException(Throwable e) {
		log.error("服务端出现异常", e);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
		data.put("message", "服务器端出现异常");
		return data;
	}

}
