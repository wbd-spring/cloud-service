package com.wbd.user.center.service;

import java.util.Set;

import com.wbd.cloud.model.user.SysPermission;

public interface SysPermissionService {

	/**
	 * 根据rolesId获取到权限列表
	 * @param roleIds
	 * @return
	 */
	Set<SysPermission> findByRoleIds(Set<Long> roleIds);
}
