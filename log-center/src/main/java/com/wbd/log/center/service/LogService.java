package com.wbd.log.center.service;

import java.util.Map;

import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.log.Log;

/**
 * 日志接口  ，保存日志与查询分页日志
 * @author zgh
 *
 */
public interface LogService {
	
	void save(Log log);
	
	Page<Log> findLogs(Map<String,Object> params);

}
