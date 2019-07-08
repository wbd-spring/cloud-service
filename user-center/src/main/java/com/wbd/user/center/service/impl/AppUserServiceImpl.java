package com.wbd.user.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbd.cloud.model.user.AppUser;
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

}
