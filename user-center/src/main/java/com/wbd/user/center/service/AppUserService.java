package com.wbd.user.center.service;

import com.wbd.cloud.model.user.AppUser;

/**
 * 用户业务接口类
 * @author jwh
 *
 */
public interface AppUserService {

	AppUser findById(Long id);
}
