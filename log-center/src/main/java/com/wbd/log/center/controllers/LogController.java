package com.wbd.log.center.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.log.Log;
import com.wbd.cloud.model.log.constants.LogModule;
import com.wbd.log.center.service.LogService;

@RestController
public class LogController {

	@Autowired
	private LogService ls;
	
	@PostMapping("/logs-anno/internal")
	public void save(@RequestBody Log log) {
		
		ls.save(log);
	}
	
	/**
	 * 日志模块，作废
	 * @return
	 */
	@Deprecated
	@PreAuthorize("hasAuthority('log:query')")
	@GetMapping("/logs-modules")
	public Map<String,String> logModule(){
		return LogModule.MODULES;
	}
	
	
	/**
	 * 日志查询
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('log:query')")
	@GetMapping("/logs")
	public Page<Log> findLogs(@RequestParam Map<String,Object> params){
		return ls.findLogs(params);
	}
	
}
