package com.wbd.user.center.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.wbd.cloud.commons.utils.PageUtil;
import com.wbd.cloud.commons.utils.PhoneUtil;
import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.AppUser;
import com.wbd.cloud.model.user.LoginAppUser;
import com.wbd.cloud.model.user.SysPermission;
import com.wbd.cloud.model.user.SysRole;
import com.wbd.cloud.model.user.UserCredential;
import com.wbd.cloud.model.user.constants.CredentialType;
import com.wbd.cloud.model.user.constants.UserType;
import com.wbd.user.center.dao.AppUserDao;
import com.wbd.user.center.dao.UserCredentialsDao;
import com.wbd.user.center.dao.UserRoleDao;
import com.wbd.user.center.service.AppUserService;
import com.wbd.user.center.service.SysPermissionService;

@Service
public class AppUserServiceImpl implements AppUserService {

	// 注入用户dao
	@Autowired
	private AppUserDao appUserDao;

	@Autowired
	private SysPermissionService sps;

	// 注入用户登录凭证dao
	@Autowired
	private UserCredentialsDao ucd;

	// 注入用户与角色关系的中京表的dao
	@Autowired
	private UserRoleDao urd;

	// 注入密码器
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

		// 4.用户类型,如果为null，设置为前端用户
		if (StringUtils.isBlank(appUser.getType())) {
			appUser.setType(UserType.APP.name());
		}

		// 5.查询用户名是否存在， 去用户登录凭证表(用户表与登录凭证表是1对多的关系)
		UserCredential userCredential = ucd.findByUsername(username);
		if (userCredential != null) {
			throw new IllegalArgumentException("用户名已经存在，请重新输入");
		}

		// 设置密码
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		// 默认添加的用户是可用状态
		appUser.setEnabled(Boolean.TRUE);
		appUser.setCreateTime(new Date());
		appUser.setUpdateTime(appUser.getCreateTime());

		// 保存用户
		appUserDao.save(appUser);

		// 保存完用户之后，再向用户登录凭证表插入数据，支持多方式登录
		ucd.save(new UserCredential(username, CredentialType.USERNAME.name(), appUser.getId()));

	}

	@Transactional
	@Override
	public void updateAppUser(AppUser appUser) {

		appUser.setUpdateTime(new Date());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		appUserDao.updateAppUser(appUser);

	}

	@Override
	public LoginAppUser findByUsername(String username) {

		AppUser appUser = appUserDao.findByUsername(username);
		if (appUser != null) {

			LoginAppUser loginAppUser = new LoginAppUser();

			// beanutils进行属性值的拷贝
			BeanUtils.copyProperties(appUser, loginAppUser);

			/*** 设置 loginAppUser的 角色集合 **/

			// 1.根据userId获取角色集合

			Set<SysRole> roles = urd.findRolesByUserId(appUser.getId());

			// 2.设置LoginAppUser的角色集合

			loginAppUser.setSysRoles(roles);

			/*** 设置 loginAppUser的 权限集合 **/

			if (!CollectionUtils.isEmpty(roles)) {

				// 通过lamdb表达是获取到 角色id，放入到一个set集合中去
				Set<Long> roleIds = roles.parallelStream().map(SysRole::getId).collect(Collectors.toSet());

				// 调用RolePermissionDao 通过角色集合获取权限集合

				Set<SysPermission> sysPermissions = sps.findByRoleIds(roleIds);

				// 通过lamdb表达式获取 权限url列表

				Set<String> permissions = sysPermissions.parallelStream().map(SysPermission::getPermission)
						.collect(Collectors.toSet());

				// 设置 LoginAppUser的权限

				loginAppUser.setPermissions(permissions);
				
				return  loginAppUser;
			}

		}

		return null;
	}

	@Override
	public Page<AppUser> findAppUserByCondition(Map<String, Object> params) {
		
		int total = appUserDao.count(params);
		List<AppUser> list = Collections.emptyList();
		if(total>0) {
			PageUtil.pageParamConver(params, true);
			list = appUserDao.findData(params);
		}
		
		return new Page<AppUser>(total,list);
	}

	@Override
	public Set<SysRole> findRoleByUserId(Long userId) {
		return urd.findRolesByUserId(userId);
	}

	@Transactional
	@Override
	public void bindingPhone(Long userId, String phone) {

		UserCredential uc = ucd.findByUsername(phone);
	    if(uc!=null) {
	    	throw new IllegalArgumentException("手机号码已经绑定，请换其他手机号码");
	    }
	    
	  AppUser appUser =   appUserDao.findById(userId);
	  appUser.setPhone(phone);
	  updateAppUser(appUser);
	  
	  //像用户登录凭证表插入数据
	  ucd.save(new UserCredential(phone,CredentialType.PHONE.name(),userId));
	
	}

	@Transactional
	@Override
	public void updatePassword(Long userId, String oldPassword, String newPassword) {

		AppUser appUser = appUserDao.findById(userId);

		if (StringUtils.isNotBlank(oldPassword)) {

			if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) {
				throw new IllegalArgumentException("旧密码错误");
			}
		}

		AppUser user = new AppUser();
		user.setId(userId);
		user.setPassword(passwordEncoder.encode(newPassword)); // 密码加密
		appUserDao.updateAppUser(user);

		
	}

	/**
	 * 给用户设置角色 1.先删除老的角色 2.再重新赋值给角色
	 */
	@Transactional
	@Override
	public void setRolesToUser(Long userId, Set<Long> roleIds) {
		AppUser appUser = appUserDao.findById(userId);
		if (appUser == null) {
			throw new IllegalArgumentException("用户不存在");
		}
		
		//先删除用户与角色的中间表， 可以根据userid删除对应的roleid
		
		urd.deleteUserRole(userId, null);
		
		//再重新插入到用户与角色的中间表
		
		if(!CollectionUtils.isEmpty(roleIds)) {
			roleIds.forEach(roleId->{
				//批量插入
				urd.saveUserRole(userId, roleId);
			});
		}

	}

}
