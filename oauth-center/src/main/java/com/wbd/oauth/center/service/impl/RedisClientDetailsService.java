package com.wbd.oauth.center.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 重新该类， 把oauth_client_details表的数据同步到redis中，在redis中用hash存储
 * 
 * @author zgh
 *
 */
@Slf4j
@Service
public class RedisClientDetailsService extends JdbcClientDetailsService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	// 缓存client的redis key，redis存储结构为hash
	private static final String CACHE_CLIENT_EKY = "client_details";

	public RedisClientDetailsService(DataSource dataSource) {
		super(dataSource);

	}
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {

		ClientDetails clientDetails = null;

		String value = (String) stringRedisTemplate.boundHashOps(CACHE_CLIENT_EKY).get(clientId);
		
		if (StringUtils.isBlank(value)) {
			clientDetails = cacheAndGetClient(clientId);

		} else {
			clientDetails = JSONObject.parseObject(value, BaseClientDetails.class);
		}
		return clientDetails;
	}

	/**
	 * 根据clientid，将一条数据存入redis中 读取数据库表oauth_client_details 的一条数据，并把该数据缓存到redis中
	 * 
	 * @param clientId
	 * @return
	 */
	private ClientDetails cacheAndGetClient(String clientId) {
		// 读取数据库表的信息
		ClientDetails clientDetails = super.loadClientByClientId(clientId);

		if (clientDetails != null) {
			stringRedisTemplate.boundHashOps(CACHE_CLIENT_EKY).put(clientId, JSONObject.toJSONString(clientDetails));
			log.info("缓存clientid:{},{}", clientId, clientDetails);
		}

		return clientDetails;

	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {

		super.updateClientDetails(clientDetails);
		cacheAndGetClient(clientDetails.getClientId());
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {

		super.updateClientSecret(clientId, secret);
		cacheAndGetClient(clientId);
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		super.removeClientDetails(clientId);
		removeRedisCache(clientId);
	}

	/**
	 * 删除redis缓存
	 */

	private void removeRedisCache(String clientId) {
		stringRedisTemplate.boundHashOps(CACHE_CLIENT_EKY).delete(clientId);
	}

	/**
	 * 将oauth_client_details表的信息全部刷入redis中
	 */

	public void loadAllClientToCache() {
		if (stringRedisTemplate.hasKey(CACHE_CLIENT_EKY) == Boolean.TRUE) {
			return;
		}
		log.info("将oauth_client_details表的信息全部刷入redis中");

		List<ClientDetails> list = super.listClientDetails();
		if (CollectionUtils.isEmpty(list)) {
			log.info("oauth_client_details表的信息为空，请重新检查");
		}
		list.parallelStream().forEach(client -> {
			stringRedisTemplate.boundHashOps(CACHE_CLIENT_EKY).put(client.getClientId(), JSONObject.toJSONString(client));
		});
	}
}
