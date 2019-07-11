package com.wbd.user.center.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.cloud.commons.utils.AppUserUtil;
import com.wbd.cloud.model.user.AppUser;
import com.wbd.cloud.model.user.LoginAppUser;
import com.wbd.user.center.service.AppUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "User接口说明")
@ApiResponses({ @ApiResponse(code = 200, message = "OK"), 
	            @ApiResponse(code = 400, message = "客户请求错误"),
		        @ApiResponse(code = 401, message = "未授权"), 
		        @ApiResponse(code = 500, message = "服务器异常"), })
public class UserController {

	@Autowired
	private AppUserService us;

	@ApiOperation(value = "获取当前登录用户的信息")
	@GetMapping("/users/current")
	public LoginAppUser getLoginAppUser() {

		return AppUserUtil.getLoginAppUser();
	}

	@ApiOperation(value="根据用户名查询用户信息")
	@ApiImplicitParam(name="username",value="用户名",required=true,dataType="String")
	@GetMapping("/users-anon/internal")
	public LoginAppUser findByUsername(String username) {
		return us.findByUsername(username);
	}
	
	@ApiOperation(value = "查询某个用户信息", notes = "查询用户信息")
	@GetMapping(value = "/users-anon/{userId}")
	public AppUser test(@ApiParam(name = "userId", value = "用户的唯一编号", required = true) @PathVariable Long userId) {

		return us.findById(userId);
	}

	@ApiOperation(value = "添加用户信息", notes = "添加用户信息")
	@ApiImplicitParam(name = "appUser", value = "用户详细实体user", required = true, dataType = "AppUser")
	@PostMapping(value = "/users-anon/register")
	public void add(@RequestBody AppUser appUser) {
		us.addAppUser(appUser);
	}
}
