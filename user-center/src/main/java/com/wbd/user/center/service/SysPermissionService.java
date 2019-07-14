package com.wbd.user.center.service;

import java.util.Map;
import java.util.Set;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.SysPermission;

public interface SysPermissionService {

	/**
	 * 根据rolesId获取到权限列表
	 * 
	 * @param roleIds
	 * @return
	 */
	Set<SysPermission> findByRoleIds(Set<Long> roleIds);

	/**
	 * 保存权限
	 * 
	 * @param sysPermission
	 */
	void save(SysPermission sysPermission);

	/**
	 * 更新权限
	 * 
	 * @param sysPermission
	 */
	void update(SysPermission sysPermission);

	/**
	 * 删除权限
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 根据条件查询权限信息列表， 包含分页信息
	 * 
	 * @param params
	 * @return
	 */
	Page<SysPermission> findPermissions(Map<String, Object> params);
}
