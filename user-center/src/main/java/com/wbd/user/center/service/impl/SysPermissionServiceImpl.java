package com.wbd.user.center.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbd.cloud.model.user.SysPermission;
import com.wbd.user.center.dao.RolePermissionDao;
import com.wbd.user.center.service.SysPermissionService;
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Autowired
	private RolePermissionDao rpd;
	
	@Override
	public Set<SysPermission> findByRoleIds(Set<Long> roleIds) {
		return rpd.findPermissionsByRoleIds(roleIds);
	}

}
