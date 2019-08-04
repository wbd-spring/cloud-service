package com.wbd.user.center.controllers;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.user.SysPermission;
import com.wbd.user.center.service.SysPermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 权限控制器
 * 
 * @author zgh
 *
 */
@Api(tags = "权限接口说明")
@ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "客户请求错误"),
		@ApiResponse(code = 401, message = "未授权"), @ApiResponse(code = 500, message = "服务器异常"), })
@RestController
public class PermissionController {
	@Autowired
	private SysPermissionService sps;

	/**
	 * 管理后台添加权限
	 */
	@ApiOperation(value = "添加权限")
	@ApiImplicitParam(dataType = "SysPermission", name = "sysPermission", required = true, value = "权限实体")
	@PostMapping("/permissions")
	public SysPermission save(@RequestBody SysPermission sysPermission) {

		if (StringUtils.isBlank(sysPermission.getPermission())) {

			throw new IllegalArgumentException("权限标识不能为空");
		}

		if (StringUtils.isBlank(sysPermission.getName())) {

			throw new IllegalArgumentException("权限名称不能为空");
		}

		sps.save(sysPermission);

		return sysPermission;

	}

	/**
	 * 后台管理修改权限
	 */

	@ApiOperation(value = "修改权限")
	@ApiImplicitParam(dataType = "SysPermission", name = "sysPermission", required = true, value = "权限实体")
	@PutMapping("/permissions")
	public SysPermission update(@RequestBody SysPermission sysPermission) {

		if (StringUtils.isBlank(sysPermission.getName())) {
			throw new IllegalArgumentException("权限标识不能为空");
		}

		if (StringUtils.isBlank(sysPermission.getName())) {

			throw new IllegalArgumentException("权限名称不能为空");
		}

		sps.update(sysPermission);

		return sysPermission;
	}

	/**
	 * 删除权限标识
	 */
	@ApiOperation(value = "删除权限标识")
	@ApiImplicitParam(dataType = "Long", name = "id", required = true, value = "权限id")
	@DeleteMapping("/permissions/{id}")
	public void delete(@PathVariable Long id) {
		sps.delete(id);
	}

	/**
	 * 查询所有权限标识
	 */
	@ApiOperation(value = "查询所有权限标识")
	@ApiImplicitParam(name = "params", required = true, value = "权限实体")
	@GetMapping("/permissons")
	public Page<SysPermission> findPermissions(Map<String, Object> params) {
		return sps.findPermissions(params);
	}
}
