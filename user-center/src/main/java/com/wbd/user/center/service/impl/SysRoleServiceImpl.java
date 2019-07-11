package com.wbd.user.center.service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.SysPermission;
import com.wbd.cloud.model.user.SysRole;
import com.wbd.user.center.service.SysRoleService;
@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Override
	public void save(SysRole sysRole) {
		
	}

	@Override
	public void update(SysRole sysRole) {
		
	}

	@Override
	public void deleteRoleById(Long roleId) {
		
	}

	@Override
	public SysRole findById(Long roleId) {
		return null;
	}

	@Override
	public Page<SysRole> findRoles(Map<String, Object> params) {
		return null;
	}

	@Override
	public Set<SysPermission> findPermissionsByRoleId(Long roleId) {
		return null;
	}

	@Override
	public void setPermissionByRoleId(Long roleId, Set<Long> permissionIds) {
		
	}
   
	
}
