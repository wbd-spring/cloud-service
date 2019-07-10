package com.wbd.user.center.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.wbd.cloud.model.user.WechatUserInfo;

@Mapper
public interface WechatDao {

	/**
	 * 保存微信用户信息
	 * 
	 * @param wechatUserInfo
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into t_wechat(openid,unionid,userId,app,nickname,sex,province,city,country,headimgurl,createTime,updateTime) values(#{openid},#{unionid},#{userId,#{app,#{nickname,#{sex},#{province},#{city},#{country},#{headimgurl},#{createTime},#{updateTime})")
	int saveWechatUserInfo(WechatUserInfo wechatUserInfo);

	/**
	 * 根据openid查询微信信息
	 * 
	 * @param openid
	 * @return
	 */
	@Select("select * from t_wechat t where t.openid=#{openid}")
	WechatUserInfo findByOpenId(String openid);

	/**
	 * 根据unionid查询 微信用户信息列表
	 * 
	 * @param unionid
	 * @return
	 */
	@Select("select *　from t_wechat t where t.unionid=#{unionid}")
	List<WechatUserInfo> findByUnionid(String unionid);

	/**
	 * 更新微信用户信息
	 * 
	 * @param wechatUserInfo
	 * @return
	 */
	int update(WechatUserInfo wechatUserInfo);

}
