package com.wbd.user.center.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wbd.cloud.model.user.SysRole;

/**
 * 用户角色中间关系表
 * 
 * @author zgh
 *
 */
@Mapper
public interface UserRoleDao {

	/**
	 * 删除中间表一条记录
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	
	int deleteUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

	@Insert("insert into sys_role_user(userId,roleId) values(#{userId},#{roleId})")
	int saveUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

	/**
	 * 根据用户id、获取角色列表
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select r.* from sys_role r,sys_role_user rs where rs.roleId=r.id and rs.userId=#{userId}")
	Set<SysRole> findRolesByUserId(Long userId);
}
