package com.wbd.oauth.center.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 集群中session采用redis存储
 * 
 * @author zgh
 *
 */
@EnableRedisHttpSession
public class SessionConfig {

}
