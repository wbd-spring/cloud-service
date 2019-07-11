package com.wbd.user.center.service;

import java.util.Map;
import java.util.Set;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.SysPermission;
import com.wbd.cloud.model.user.SysRole;

public interface SysRoleService {

	/**
	 * 保存角色
	 * 
	 * @param sysRole
	 */
	void save(SysRole sysRole);

	/**
	 * 更新角色
	 * 
	 * @param sysRole
	 */
	void update(SysRole sysRole);

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 */
	void deleteRoleById(Long roleId);

	/**
	 * 查询角色
	 * 
	 * @param roleId
	 * @return
	 */
	SysRole findById(Long roleId);

	/**
	 * 根据条件查询出角色分页细信息
	 * 
	 * @param params
	 * @return
	 */
	Page<SysRole> findRoles(Map<String, Object> params);

	/**
	 * 根据roleid查询出对应的权限列表
	 * 
	 * @param roleId
	 * @return
	 */
	Set<SysPermission> findPermissionsByRoleId(Long roleId);

	/**
	 * 根据角色设置对应的权限信息
	 * 
	 * @param roleId
	 * @param permissionIds
	 */
	void setPermissionByRoleId(Long roleId, Set<Long> permissionIds);
}
