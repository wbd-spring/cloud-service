package com.wbd.oauth.center.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.oauth.SystemClientInfo;
import com.wbd.oauth.center.service.impl.RedisClientDetailsService;

import ch.qos.logback.core.util.SystemInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * oauth_client_details 页面管理控制器
 * 
 * @author zgh
 *
 */
@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private RedisClientDetailsService rcds;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 保存
	 * 
	 * @param clientDetails
	 */
	@PostMapping
	public void save(@RequestBody BaseClientDetails clientDetails) {
		ClientDetails client = getAndCheckClient(clientDetails.getClientId(), true);
		if (client != null) {
			throw new IllegalArgumentException(clientDetails.getClientId() + "已经存在");
		}
		// 密码加密
		clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));

		rcds.addClientDetails(client);
		log.info("保存client信息 {}", clientDetails);
	}

	/**
	 * 根据clientid获取client信息
	 * 
	 * @param clientId
	 * @param check
	 * @return
	 */
	private ClientDetails getAndCheckClient(String clientId, boolean check) {
		ClientDetails clientDetails = rcds.loadClientByClientId(clientId);
		isSystemClient(clientDetails);
		return clientDetails;
	}

	/**
	 * 更新
	 * 
	 * @param clientDetails
	 */
	@PutMapping
	public void update(@RequestBody BaseClientDetails clientDetails) {
		getAndCheckClient(clientDetails.getClientId(), true);
		rcds.updateClientDetails(clientDetails);
		log.info("修改client信息{ } ", clientDetails);
	}

	/**
	 * 更新secret
	 * 
	 * @param clientId
	 * @param secret
	 */
	@PutMapping(value = "/{clientId}", params = "secret")
	public void updateSecret(@PathVariable String clientId, String secret) {
		getAndCheckClient(clientId, true);
		checkSystemClient(clientId);
		secret = passwordEncoder.encode(secret);
		rcds.updateClientSecret(clientId, secret);
		log.info("修改client密码,{},{}", clientId, secret);

	}

	/**
	 * 假分页查询
	 * 
	 * @return
	 */
	@GetMapping
	public Page<ClientDetails> findClients() {

		List<ClientDetails> list = rcds.listClientDetails();

		list.parallelStream().forEach(client -> {
			isSystemClient(client);
		});

		return new Page<>(list.size(), list);

	}

	/**
	 * 根据clientid查询
	 * 
	 * @param clientId
	 * @return
	 */
	@GetMapping("/{clientId}")
	public ClientDetails getById(@PathVariable String clientId) {
		return getAndCheckClient(clientId, true);
	}

	/**
	 * 删除client信息
	 * 
	 * @param clientId
	 */
	@DeleteMapping("/{clientId}")
	public void delete(@PathVariable String clientId) {
		getAndCheckClient(clientId, true);
		checkSystemClient(clientId);
		rcds.removeClientDetails(clientId);
		log.info("删除client:{}", clientId);
	}

	private void checkSystemClient(String clientId) {
		if (SystemClientInfo.CLIENT_ID.equals(clientId)) {
			throw new IllegalArgumentException("不能操作系统数据");
		}
	}

	/**
	 * 判断是否是我们自己系统内部用的client 在扩展字段里放一个isSytem标注来区分
	 */

	private boolean isSystemClient(ClientDetails clientDetails) {

		BaseClientDetails baseClientDetails = (BaseClientDetails) clientDetails;
		Map<String, Object> map = baseClientDetails.getAdditionalInformation();
		if (map == null) {
			map = new HashMap<>();
			baseClientDetails.setAdditionalInformation(map);
		}
		boolean isSystem = SystemClientInfo.CLIENT_ID.equalsIgnoreCase(baseClientDetails.getClientId());
		baseClientDetails.addAdditionalInformation("isSystem", isSystem);

		return isSystem;
	}
}
