package com.wbd.user.center.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbd.cloud.commons.utils.PhoneUtil;
import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.AppUser;
import com.wbd.cloud.model.user.LoginAppUser;
import com.wbd.cloud.model.user.SysRole;
import com.wbd.cloud.model.user.UserCredential;
import com.wbd.cloud.model.user.constants.CredentialType;
import com.wbd.cloud.model.user.constants.UserType;
import com.wbd.user.center.dao.AppUserDao;
import com.wbd.user.center.dao.UserCredentialsDao;
import com.wbd.user.center.service.AppUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {

	//注入用户dao
	@Autowired
	private AppUserDao appUserDao;
	
	//注入用户登录凭证dao
	@Autowired
	private UserCredentialsDao  ucd;
	
	//注入密码器
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public AppUser findById(Long id) {

		return appUserDao.findById(id);
	}

	/**
	 * 需要添加事务
	 */
	@Transactional
	@Override
	public void addAppUser(AppUser appUser) {

		// 1.验证用户名

		String username = appUser.getUsername();
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("用户名不能为空");
		}
		// 防止用手机号码直接当用户名，手机号要发送短信验证
		if (PhoneUtil.checkPhone(username)) {
			throw new IllegalArgumentException("用户名要包含英文字母");
		}

		if (username.contains("@")) {
			throw new IllegalArgumentException("用户名不能包含@");
		}
		if (username.contains("|")) {
			throw new IllegalArgumentException("用户名不能包含|字符");
		}

		// 2.验证密码
		if (StringUtils.isBlank(appUser.getPassword())) {
			throw new IllegalArgumentException("密码不能为空");
		}

		// 3.nickname ,如果nickname为null，则设置nickname=username
		if (StringUtils.isBlank(appUser.getNickname())) {
			appUser.setNickname(username);
		}
		
		//4.用户类型,如果为null，设置为前端用户
		if(StringUtils.isBlank(appUser.getType())) {
			appUser.setType(UserType.APP.name());
		}
		
		//5.查询用户名是否存在， 去用户登录凭证表(用户表与登录凭证表是1对多的关系)
		UserCredential userCredential = ucd.findByUsername(username);
		if(userCredential!=null) {
			throw new IllegalArgumentException("用户名已经存在，请重新输入");
		}
		
		//设置密码
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		//默认添加的用户是可用状态
		appUser.setEnable(Boolean.TRUE);
		appUser.setCreateTime(new Date());
		appUser.setUpdateTime(appUser.getCreateTime());
		
		//保存用户
		appUserDao.save(appUser);
		
		//保存完用户之后，再向用户登录凭证表插入数据，支持多方式登录
		ucd.save(new UserCredential(username, CredentialType.USERNAME.name(), appUser.getId()));
		
		 
	}

	@Override
	public void updateAppUser(AppUser appUser) {

	}

	@Override
	public LoginAppUser findByUsername(String username) {
		return null;
	}

	@Override
	public Page<AppUser> findAppUserByCondition(Map<String, Object> params) {
		return null;
	}

	@Override
	public Set<SysRole> findRoleByUserId(Long userId) {
		return null;
	}

	@Override
	public void bindingPhone(Long userId, String phone) {

	}

	@Override
	public void updatePassword(Long userId, String oldPassword, String newPassword) {

	}

	@Override
	public void setRolesToUser(Long userId, Set<Long> roleIds) {

	}

}
