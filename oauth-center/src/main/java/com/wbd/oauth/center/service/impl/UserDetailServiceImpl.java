package com.wbd.oauth.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wbd.cloud.model.user.LoginAppUser;
import com.wbd.oauth.center.feign.UserClient;
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		LoginAppUser loginAppUser = userClient.findByUsername(username);
		
		return null;
	}

}
