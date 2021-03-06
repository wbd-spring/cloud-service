package com.wbd.cloud.model.user;

import java.io.Serializable;

import com.wbd.cloud.model.user.constants.CredentialType;

/**
 *
 * 用户凭证表
 *
 * 用户表与用户凭证表是一对多的关系， 通过 用户表的id与凭证表的userid关联起来
 */
public class UserCredential implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	/**
	 * 用户登录凭证类型
	 * 
	 * @see com.wbd.cloud.model.user.constants.CredentialType
	 */
	private String type;

	private Long userId;

	public String getUsername() {
		return username;
	}

	public String getType() {
		return type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserCredential(String username, String type, Long userId) {
		super();
		this.username = username;
		this.type = type;
		this.userId = userId;
	}

	public UserCredential() {
		super();
	}

}
