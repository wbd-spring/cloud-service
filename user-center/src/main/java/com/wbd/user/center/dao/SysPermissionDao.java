package com.wbd.user.center.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wbd.cloud.model.user.SysPermission;

/**
 * 权限dao
 * 
 * @author zgh
 *
 */
@Mapper
public interface SysPermissionDao {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_permission(permission,name,createTime,updateTime) values(#{permission},#{name},#{createTime},#{updateTime})")
	int save(SysPermission sysPermission);
	
	@Update("update sys_permission set permission=#{permission},name=#{name},createTime=#{createTime},updateTime=#{updateTime} where id=#{id}")
    int update(SysPermission sysPermission);
	
	@Delete("delete from sys_permission where id=#{id}")
	int delete(Long id);
	
	@Select("select * from sys_permission where id=#{id}")
	SysPermission findById(Long id);
	
	@Select("select * from sys_permission where permission=#{permission}")
	SysPermission findByPermission(String permission);
	
	int count(Map<String,Object> param);
	
	List<SysPermission> findData(Map<String,Object> param);
}
