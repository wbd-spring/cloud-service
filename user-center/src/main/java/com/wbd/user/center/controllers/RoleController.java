package com.wbd.user.center.controllers;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.SysPermission;
import com.wbd.cloud.model.user.SysRole;
import com.wbd.user.center.service.SysRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "角色相关接口")
@ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "客户请求错误"),
		@ApiResponse(code = 401, message = "未授权"), @ApiResponse(code = 500, message = "服务器异常"), })
public class RoleController {

	@Autowired
	private SysRoleService roleService;

	/**
	 * 添加角色
	 */
	@ApiOperation(value = "添加角色")
	@ApiImplicitParam(dataType = "SysRole", name = "role", required = true, value = "角色实体")
	@PostMapping("/roles")
	public SysRole save(@RequestBody SysRole role) {

		if (StringUtils.isBlank(role.getCode())) {
			throw new IllegalArgumentException("角色编号不能为空");
		}

		if (StringUtils.isBlank(role.getName())) {
			role.setName(role.getCode());
		}
		roleService.save(role);
		return role;
	}

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 */
	@ApiOperation(value = "根据角色id删除角色相关信息")
	@ApiImplicitParam(dataType = "Long", value = "角色id", name = "roleId", required = true)
	@DeleteMapping("/roles/{roleId}")
	public void deleteRoleById(@PathVariable Long roleId) {
		roleService.deleteRoleById(roleId);
	}

	/**
	 * 管理后台修改角色
	 * 
	 * @param roleId
	 */
	@ApiOperation(value = "管理后台修改角色")
	@ApiImplicitParam(dataType = "SysRole", value = "角色对象", name = "role", required = true)
	@PutMapping("/roles")
	public SysRole updateRole(@RequestBody SysRole role) {
		if (StringUtils.isBlank(role.getCode())) {
			throw new IllegalArgumentException("角色编码不能为空");
		}

		if (StringUtils.isBlank(role.getName())) {
			throw new IllegalArgumentException("角色名称不能为空");
		}

		roleService.update(role);

		return role;
	}

	/**
	 * 管理后台给角色分配权限
	 * 
	 * @param roleId
	 */
	@ApiOperation(value = "管理后台给角色分配权限")
	@ApiImplicitParams({ @ApiImplicitParam(dataType = "Long", value = "角色id", name = "roleId", required = true),
			@ApiImplicitParam(dataType = "Set", value = "权限id集合", name = "permissionIds", required = true)

	})
	@PostMapping("/roles/{roleId}/permission")
	public void setPermissionToRole(@PathVariable Long roleId, Set<Long> permissionIds) {
		roleService.setPermissionByRoleId(roleId, permissionIds);

	}
	/**
	 * 根据角色id查询出对应的权限列表
	 */
	@ApiOperation(value = "根据角色id查询出对应的权限列表")
	@ApiImplicitParams({ @ApiImplicitParam(dataType = "Long", value = "角色id", name = "roleId", required = true)

	})
	@GetMapping("/roles/{roleId}/permission")
	public Set<SysPermission> findPermissionById(@PathVariable Long roleId){
		return roleService.findPermissionsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询角色信息
	 */
	@ApiOperation(value = "根据角色id查询角色信息")
	@ApiImplicitParams({ @ApiImplicitParam(dataType = "Long", value = "角色id", name = "roleId", required = true)

	})
	@GetMapping("/roles/{roleId}")
	public SysRole findById(@PathVariable Long roleId){
		return roleService.findById(roleId);
	}
	
	
	/**
	 * 角色搜索
	 */
	@ApiOperation(value = "角色搜索")
	@ApiImplicitParams({ @ApiImplicitParam(dataType = "Map", value = "角色搜索条件", name = "params", required = true)

	})
	@GetMapping("/roles")
	public Page<SysRole> findRoles(@RequestParam Map<String,Object> params){
		return roleService.findRoles(params);
	}
}
