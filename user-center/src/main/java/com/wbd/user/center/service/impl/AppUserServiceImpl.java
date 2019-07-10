package com.wbd.user.center.service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.AppUser;
import com.wbd.cloud.model.user.LoginAppUser;
import com.wbd.cloud.model.user.SysRole;
import com.wbd.user.center.dao.AppUserDao;
import com.wbd.user.center.service.AppUserService;
@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserDao appUserDao;
	
	
	@Override
	public AppUser findById(Long id) {
		
		return appUserDao.findById(id);
	}


	@Override
	public void addAppUser(AppUser appUser) {
		
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
