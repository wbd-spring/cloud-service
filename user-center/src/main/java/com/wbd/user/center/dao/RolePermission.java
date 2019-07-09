package com.wbd.user.center.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wbd.cloud.model.user.SysPermission;

/**
 * 角色与权限中间表
 * @author zgh
 *
 */
@Mapper
public interface RolePermission {

	@Insert("insert into sys_role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
	int saveRolePermission(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);
	
	@Delete("delete from sys_role_permission where roleId=#{roleId} and permissionId=#{permissionId}")
	int deleteRolePermission(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);
	/**
	 * 根据角色ids查询权限列表
	 * @param roleIds
	 * @return
	 */
	Set<SysPermission> findPermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);
}
