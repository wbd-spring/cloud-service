package com.wbd.user.center.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.wbd.cloud.commons.utils.PageUtil;
import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.SysPermission;
import com.wbd.cloud.model.user.SysRole;
import com.wbd.cloud.model.user.constants.UserCenterMQ;
import com.wbd.user.center.dao.RolePermissionDao;
import com.wbd.user.center.dao.SysRoleDao;
import com.wbd.user.center.dao.UserRoleDao;
import com.wbd.user.center.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	// 注入角色dao
	@Autowired
	private SysRoleDao roleDao;

	// 注入角色与权限中间表的dao 删除角色时候， 需要删除角色对应的权限
	@Autowired
	private RolePermissionDao rolePermissionDao;

	// 注入角色与用户中间表的dao，删除角色时候， 需要删除角色对应的用户
	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Transactional
	@Override
	public void save(SysRole sysRole) {
		SysRole role = roleDao.findByCode(sysRole.getCode());
		if (role != null) {
			throw new IllegalArgumentException("角色编号已存在,请重新输入");
		}
		sysRole.setCreateTime(new Date());
		//sysRole.setUpdateTime(sysRole.getUpdateTime());

		roleDao.save(sysRole);
	}

	@Transactional
	@Override
	public void update(SysRole sysRole) {
		sysRole.setUpdateTime(new Date());
		roleDao.updateSysRole(sysRole);
	}

	@Transactional
	@Override
	public void deleteRoleById(Long roleId) {

		// 1.删除角色表
		roleDao.delete(roleId);

		// 2.删除角色对应的用户中间表
		userRoleDao.deleteUserRole(null, roleId);

		// 3.删除角色对应的权限中间表

		rolePermissionDao.deleteRolePermission(roleId, null);

		// 4.发布role删除的消息
		amqpTemplate.convertAndSend(UserCenterMQ.MQ_EXCHANGE_USER, UserCenterMQ.ROUTING_KEY_ROLE_DELETE, roleId);

	}

	@Override
	public SysRole findById(Long roleId) {
		return roleDao.findById(roleId);
	}

	@Override
	public Page<SysRole> findRoles(Map<String, Object> params) {
		int total = roleDao.count(params);

		List<SysRole> list = Collections.emptyList();

		if (total > 0) {
			PageUtil.pageParamConver(params, false);
			list = roleDao.findData(params);
		}

		return new Page<SysRole>(total, list);
	}

	@Override
	public Set<SysPermission> findPermissionsByRoleId(Long roleId) {
		return rolePermissionDao.findPermissionsByRoleIds(Sets.newHashSet(roleId));
	}

	@Transactional
	@Override
	public void setPermissionByRoleId(Long roleId, Set<Long> permissionIds) {

	  SysRole role = roleDao.findById(roleId);
		
	  if(role==null) {
		  throw new IllegalArgumentException("删除的角色不存在");
	  }
		// 删除之前的角色
		rolePermissionDao.deleteRolePermission(roleId, null);
		permissionIds.forEach(permissionId -> {
			rolePermissionDao.saveRolePermission(roleId, permissionId);
		});
	}

}
