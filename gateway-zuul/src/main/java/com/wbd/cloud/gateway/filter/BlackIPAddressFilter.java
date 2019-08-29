package com.wbd.cloud.gateway.filter;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
/**
 * 黑名单ip拦截
 * @author zgh
 *
 */
public class BlackIPAddressFilter extends ZuulFilter {

	
	private Set<String> blackIPs= new HashSet<String>();
	
	@Override
	public boolean shouldFilter() {
		if(blackIPs.isEmpty()) {
			return false;
		}
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = (HttpServletRequest) requestContext;
		String ip = getIpAddress(request);
		return blackIPs.contains(ip); //判断是否在黑名单列表中
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requsetContext = RequestContext.getCurrentContext();
		requsetContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
		requsetContext.setResponseBody("black_ip");
		requsetContext.setSendZuulResponse(false);
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	//定时任务
	@Scheduled(cron = "${cron.black-ip}")
	public void syncBlackIPList() {
		//TODO  backendclient 未完成
	}
    	
	/**
	 * 获取请求的真实ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
