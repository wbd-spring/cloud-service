package com.wbd.cloud.model.user.constants;

/**
 *
 * 用户账户类型 登录时，可以用 ：1.用户名，2.手机号码/手机号码+短信验证码 3.微信的openid
 * 
 * @author zgh
 *
 */
public enum CredentialType {

	/** 用户名 **/
	USERNAME,
	/** 手机号码 **/
	PHONE,
	/** 微信openid **/
	WECHAT_OPENID
}
