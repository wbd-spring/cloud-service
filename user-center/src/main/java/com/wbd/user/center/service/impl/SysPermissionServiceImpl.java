package com.wbd.user.center.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbd.cloud.commons.utils.PageUtil;
import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.SysPermission;
import com.wbd.user.center.dao.RolePermissionDao;
import com.wbd.user.center.dao.SysPermissionDao;
import com.wbd.user.center.service.SysPermissionService;
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Autowired
	private RolePermissionDao rpd;
	
	@Autowired
	private SysPermissionDao spd;
	
	@Override
	public Set<SysPermission> findByRoleIds(Set<Long> roleIds) {
		return rpd.findPermissionsByRoleIds(roleIds);
	}

	@Transactional
	@Override
	public void save(SysPermission sysPermission) {
		SysPermission permission = 	spd.findByPermission(sysPermission.getPermission());
	   if(permission!=null) {
		   throw new IllegalArgumentException("权限标识已经存在，请重新输入");
	   }
	   sysPermission.setCreateTime(new Date());
	   sysPermission.setUpdateTime(sysPermission.getCreateTime());
	   spd.save(sysPermission);
	}

	@Transactional
	@Override
	public void update(SysPermission sysPermission) {
		sysPermission.setUpdateTime(new Date());
		spd.update(sysPermission);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		
		SysPermission sysPermission =spd.findById(id);
		if(sysPermission==null) {
			 throw new IllegalArgumentException("权限标识不已经存在，请重新输入");
		}
		
		spd.delete(id);
		//删除角色与权限管理的中间表
		rpd.deleteRolePermission(null, id);
	}

	@Override
	public Page<SysPermission> findPermissions(Map<String, Object> params) {
	int count=	spd.count(params);
	List<SysPermission> list = Collections.emptyList();
		if(count>0) {
			PageUtil.pageParamConver(params, false);
		}
		list = spd.findData(params);
		
		return new Page<SysPermission>(count,list);
	}

}
