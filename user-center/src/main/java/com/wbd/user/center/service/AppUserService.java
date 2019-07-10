package com.wbd.user.center.service;

import java.util.Map;
import java.util.Set;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.AppUser;
import com.wbd.cloud.model.user.LoginAppUser;
import com.wbd.cloud.model.user.SysRole;

/**
 * 用户业务接口类
 * 
 * @author jwh
 *
 */
public interface AppUserService {

	/**
	 * 根据用户id获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	AppUser findById(Long id);

	/**
	 * 添加用户
	 * 
	 * @param appUser
	 */
	void addAppUser(AppUser appUser);

	/**
	 * 更新用户
	 * 
	 * @param appUser
	 */
	void updateAppUser(AppUser appUser);

	/**
	 * 根据username查询LoginAppUser信息
	 * 
	 * @param username
	 * @return
	 */
	LoginAppUser findByUsername(String username);

	/**
	 * 根据 条件查询出用户信息， 带分页
	 * 
	 * @param params
	 * @return
	 */
	Page<AppUser> findAppUserByCondition(Map<String, Object> params);

	/**
	 * 根据用户id查询对应的角色列表
	 * 
	 * @param userId
	 * @return
	 */
	Set<SysRole> findRoleByUserId(Long userId);

	/**
	 * 绑定用户的手机号码
	 * 
	 * @param userId
	 * @param phone
	 */
	void bindingPhone(Long userId, String phone);

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 */
	void updatePassword(Long userId, String oldPassword, String newPassword);

	/**
	 * 给某一个用户设置角色
	 * 
	 * @param userId
	 * @param roleIds
	 */
	void setRolesToUser(Long userId, Set<Long> roleIds);
}
