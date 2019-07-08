package com.wbd.user.center.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 采用redis 开启session共享  
 * @author jwh
 *
 */
@EnableRedisHttpSession
public class SessionConfig {

}
