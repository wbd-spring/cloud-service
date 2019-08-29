package com.wbd.cloud.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 过滤uri 该类uri不需要登录就可以访问， 但是又不允许外网通过网关调用，只允许微服务之间在内网可以调用 为了方便拦截此场景的uri，
 * 我们自己定义了一个规范， uri中有-anno/internal
 * 如登录时更加username查询用户，用户系统提供的查询接口/users-anno/internal肯定不能做登录拦截 而且该接口不能对外网暴露
 * 
 * @author zgh
 *
 */
@Component
public class InternalURIAccessFilter extends ZuulFilter {

	// 当为false表示不拦截， 不执行下面的run方法， 当为ture是，才拦截， 然后执行下面的run方法
	@Override
	public boolean shouldFilter() {
		// 获取当前的请求上下文
		RequestContext requestContext = RequestContext.getCurrentContext();
		// 把当前的请求上下文转换为HttpServletRequest
		HttpServletRequest request = (HttpServletRequest) requestContext;

		return PatternMatchUtils.simpleMatch("*-anno/internal*", request.getRequestURI());
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext requestContext = RequestContext.getCurrentContext();
		requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
		requestContext.setResponseBody(HttpStatus.FORBIDDEN.getReasonPhrase());
		requestContext.setSendZuulResponse(false);// 表示网关不向下执行
		return null;
	}

	// 在请求网关时就可以拦截
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0; // 数字越大， 级别越低
	}
	

}
