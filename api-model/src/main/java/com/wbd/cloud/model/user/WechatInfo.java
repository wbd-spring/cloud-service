package com.wbd.cloud.model.user;

import java.io.Serializable;
/**
 * 微信 appid,和secret
 * @author jwh
 *
 */
public class WechatInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appid;
	
	private String secret;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
}
