package com.wbd.user.center.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.wbd.cloud.model.user.AppUser;

@Mapper
public interface AppUserDao {

	/**
	 * 根据id查询用户信息
	 * @param id
	 * @return
	 */
	@Select("select * from app_user where id = #{id}")
	AppUser findById(Long id);
	
	/**
	 * 根据条件查询用户记录条数
	 * @param params
	 * @return
	 */
	int count(Map<String,Object> params);
	
}
