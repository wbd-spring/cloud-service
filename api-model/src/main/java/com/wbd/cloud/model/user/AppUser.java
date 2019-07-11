package com.wbd.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用戶 psvm sout
 * 
 * @author zgh
 */
@ApiModel(value = "AppUser", description = "用户信息描述")
public class AppUser implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户唯一标识")
	private Long id;

	@ApiModelProperty("用户名")
	private String username;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("昵称")
	private String nickname;

	@ApiModelProperty("头像")
	private String headImgUrl;

	@ApiModelProperty("手机号码")
	private String phone;
	@ApiModelProperty("性别")
	private Integer sex;

	// 状态
	@ApiModelProperty("用户状态，默认为可用 1=可以，0=不可用")
	private Boolean enabled;

	// @see com.wbd.cloud.model.user.constants.UserType
	@ApiModelProperty("用户类型，有前端用户与后端用户")
	private String type;

	@ApiModelProperty("用户注册时间")
	private Date createTime;

	@ApiModelProperty("用户信息最后更新时间")
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}


	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
