package com.wbd.user.center.controllers;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.cloud.commons.utils.AppUserUtil;
import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.AppUser;
import com.wbd.cloud.model.user.LoginAppUser;
import com.wbd.cloud.model.user.SysRole;
import com.wbd.user.center.service.AppUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
 * 用户中心的所有接口(除了那些放开权限的接口外)先要调用 认证中心的 /user-me?access_token=x 接口， 来获取当前用户信息， 当前用户信息中存储了
 * 用户对应的权限信息，然后用户中心接口请求时拿着 认证中心返回的access_token参数进行接口的请求
 * 因为在认证中心的 资源服务器中就要求请求中必须带accee_token获取请求头 带Authorization BEARER_TYPE
 * 
 * 除了认证中心微服务， 其他的微服务的认证过程都需要来 认证中心进行鉴权，
 * @author zgh
 *
 */
@RestController
@Api(tags = "User接口说明")
@ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "客户请求错误"),
		@ApiResponse(code = 401, message = "未授权"), @ApiResponse(code = 500, message = "服务器异常"), })
public class UserController {

	@Autowired
	private AppUserService us;
   /**
    * 该请求其实是请求认证中心的 http://localhost:8889/user-me 再带上返回的access_token
    * @return
    */
	@ApiOperation(value = "获取当前登录用户的信息")
	@GetMapping("/users/current")
	public LoginAppUser getLoginAppUser() {
		System.out.println("查询用户.....");
		return AppUserUtil.getLoginAppUser();
	}

	@ApiOperation(value = "根据用户名查询用户信息")
	@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String")
	@GetMapping("/users-anon/internal")
	public LoginAppUser findByUsername(String username) {
		return us.findByUsername(username);
	}

	/**
	 * 根据用户id查询用户
	 * 
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "根据用户id查询用户信息", notes = "查询用户信息")
	@GetMapping(value = "/users/{id}")
	public AppUser findAppUserById(@ApiParam(name = "id", value = "用户的唯一编号", required = true) @PathVariable Long id) {

		return us.findById(id);
	}

	/**
	 * 注册用户
	 * 
	 * @param appUser
	 */
	@ApiOperation(value = "注册用户信息", notes = "添加用户信息")
	@ApiImplicitParam(name = "appUser", value = "用户详细实体user", required = true, dataType = "AppUser")
	@PostMapping(value = "/users-anon/register")
	public void register(@RequestBody AppUser appUser) {
		us.addAppUser(appUser);
	}

	/**
	 * 用户查询
	 */
	@ApiOperation(value = "用户查询")
	@ApiImplicitParam(name = "params", value = "用户查询条件", required = true, dataType = "Map", paramType = "query")
	@GetMapping("/users")
	public Page<AppUser> findUsers(@RequestParam Map<String, Object> params) {
		return us.findAppUserByCondition(params);
	}

	/**
	 * 修改用户个人信息
	 */
	@ApiOperation(value = "修改用户信息")
	@ApiImplicitParam(name = "appUser", value = "用户实体user", required = true, dataType = "AppUser")
	@PutMapping("/users/me")
	public AppUser updateMe(@RequestBody AppUser appUser) {

		AppUser user = AppUserUtil.getLoginAppUser();

		appUser.setId(user.getId());

		us.updateAppUser(appUser);

		return appUser;
	}

	/**
	 * 修改密码
	 */
	@ApiOperation(value = "修改密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, dataType = "String"),
			@ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String") })
	@PutMapping("/users/password")
	public void updatePassword(String oldPassword, String newPassword) {
		if (StringUtils.isBlank(oldPassword)) {
			throw new IllegalArgumentException("旧密码不能为空");
		}
		if (StringUtils.isBlank(newPassword)) {
			throw new IllegalArgumentException("新密码不能为空");
		}

		AppUser user = AppUserUtil.getLoginAppUser();
		us.updatePassword(user.getId(), oldPassword, newPassword);
	}

	/***
	 * 管理后台，给用户重置密码
	 */
	@ApiOperation(value = "管理后台，给用户重置密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String") })
	@PutMapping("/users/{id}/password")
	public void restPassword(@PathVariable Long id, String newPassword) {
		us.updatePassword(id, null, newPassword);
	}

	/***
	 * 管理后台，修改用户
	 */
	@ApiOperation(value = "管理后台，修改用户")
	@ApiImplicitParam(name = "appUser", value = "用户实体user", required = true, dataType = "AppUser")
	@PutMapping("/users")
	public void updateAppUser(@RequestBody AppUser appUser) {
		us.updateAppUser(appUser);
	}
	
	/***
	 * 管理后台，给用户分配角色
	 */
	@ApiOperation(value = "管理后台，给用户分配角色")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long")})
	@PostMapping("/users/{id}/roles")
	public void setRoleToUser(@PathVariable Long id,@RequestBody Set<Long> roleIds) {
		us.setRolesToUser(id, roleIds);
	}
	
	/**
	 * 获取用户的角色
	 */
	@ApiOperation(value = "获取用户的角色")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long")})
    @GetMapping("/users/{id}/roles")
	public Set<SysRole> getRolesById(@PathVariable Long id){
		return us.findRoleByUserId(id);
	}
}
