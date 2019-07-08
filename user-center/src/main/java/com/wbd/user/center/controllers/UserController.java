package com.wbd.user.center.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.cloud.model.user.AppUser;
import com.wbd.user.center.service.AppUserService;

@RestController
public class UserController {

	@Autowired
	private AppUserService  us;
	
	@GetMapping("/user/test")
	public AppUser test() {
	
		return us.findById(1l);
	}
}
