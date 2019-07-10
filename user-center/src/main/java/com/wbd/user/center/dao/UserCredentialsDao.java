package com.wbd.user.center.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.wbd.cloud.model.user.AppUser;
import com.wbd.cloud.model.user.UserCredential;

/**
 * 用户凭证dao
 * 
 * @author jwh
 *
 */
@Mapper
public interface UserCredentialsDao {

	/**
	 * 保存
	 * 
	 * @param userCredential
	 * @return
	 */
	@Insert("insert into user_credentials values(#{username},#{type},#{userId})")
	int save(UserCredential userCredential);

	/**
	 * 查询
	 * 
	 * @param username
	 * @return
	 */
	@Select("select * from user_credentials uc where uc.username=#{username}")
	UserCredential findByUsername(String username);

	/**
	 * 根据用户名查询出appuser对象
	 * 
	 * @param username
	 * @return
	 */
	@Select("select  u.* from app_user u ,user_credentials c where u.id=c.userId and c.username=#{username}")
	AppUser findAppUserByUsername(String username);

}
