package com.wbd.oauth.center.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.oauth.center.service.impl.RedisClientDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	private RedisClientDetailsService rcds;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@PostMapping
	public void save(@RequestBody BaseClientDetails clientDetails) {
		
		clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
		log.info("保存client信息{}",clientDetails);
	}
	
	/**
	 * 根据clientid获取client信息
	 * @param clientId
	 * @param check
	 * @return
	 */
	private ClientDetails getAndCheckClient(String clientId,boolean check) {
		ClientDetails clientDetails = rcds.loadClientByClientId(clientId);
	 
		return clientDetails;
	}

}
