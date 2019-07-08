//package com.wbd.user.center.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
///**
// * 全局异常捕获处理器
// * 
// * 如何让我们的异常得到期望的返回格式，这里就需要用到了@ControllerAdvice或者RestControllerAdvice（如果全部异常处理返回json，那么可以使用 @RestControllerAdvice
// * 代替 @ControllerAdvice ，这样在方法上就可以不需要添加 @ResponseBody
// * 
// * @author jwh
// *
// */
//@RestControllerAdvice
//public class ExceptionHandlerAdvice {
//	@ExceptionHandler({IllegalArgumentException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//	public Map<String,Object> badRequestException(IllegalArgumentException exception){
//		Map<String,Object>  data = new HashMap<String,Object>();
//		data.put("code", HttpStatus.BAD_REQUEST.value());
//		data.put("msg", exception.getMessage());
//		return data;
//	}
//	
//}
