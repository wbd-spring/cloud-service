package com.wbd.user.center.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wbd.cloud.model.user.SysRole;

@Mapper
public interface SysRoleDao {

	/**
	 * 保存角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_role(code,name,createTime,updateTime) values(#{code},#{name},#{createTime},#{updateTime})")
	int save(SysRole sysRole);

	/**
	 * 更新角色，只能更改名称与更新时间
	 * 
	 * @param sysRole
	 * @return
	 */
	@Update("update sys_role r set r.name=#{name},r.updateTime=#{updateTime} where t.id=#{id}")
	int updateSysRole(SysRole sysRole);

	/**
	 * 根据id查询 角色信息
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from sys_role r where r.id=#{id}")
	SysRole findById(Long id);

	/**
	 * 根据角色code查询角色信息
	 * 
	 * @param code
	 * @return
	 */
	@Select("select * from sys_role r where r.code = #{code}")
	SysRole findByCode(String code);

	/**
	 * 根据id删除角色
	 * 
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_role where id=#{id}")
	int delete(Long id);

	/**
	 * 根据条件查询总记录数
	 * 
	 * @param params
	 * @return
	 */
	int count(Map<String, Object> params);

	/**
	 * 根据条件查询角色列表
	 * 
	 * @param params
	 * @return
	 */
	List<SysRole> findData(Map<String, Object> params);
}
