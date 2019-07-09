package com.wbd.user.center.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.wbd.cloud.model.user.AppUser;

@Mapper
public interface AppUserDao {

	/**
	 * 添加用户
	 * 
	 * @param appUser
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyProperty = "id") // 该注解表示插入数据之后，返回id
	@Insert("insert into app_user(username,password,nickname,headImgUrl,phone,sex,enabled,type,createTime,updateTime) values(#{username},#{password},#{nickname},#{headImgUrl},#{phone},#{sex},#{enabled},#{type},#{createTime},#{updateTime})")
	int save(AppUser appUser);

	/**
	 * 根据id查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from app_user where id = #{id}")
	AppUser findById(Long id);

	/**
	 * 根据条件查询用户记录条数
	 * 
	 * @param params
	 * @return
	 */
	int count(Map<String, Object> params);

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	@Select("select * from app_user t where t.username=#{username}")
	AppUser findByUsername(String username);

	/**
	 * 根据条件查询用户列表
	 * 
	 * @param params
	 * @return
	 */
	List<AppUser> findData(Map<String, Object> params);
	
	/**
	 * 更新用户
	 * @param appUser
	 * @return
	 */
	int updateAppUser(AppUser appUser);

}
