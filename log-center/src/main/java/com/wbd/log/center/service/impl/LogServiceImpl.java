package com.wbd.log.center.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wbd.cloud.commons.utils.PageUtil;
import com.wbd.cloud.model.common.Page;
import com.wbd.cloud.model.log.Log;
import com.wbd.log.center.dao.LogDao;
import com.wbd.log.center.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;

	/**
	 * 保存日志
	 */
	@Async //采用异步的方式发送日志
	@Override
	public void save(Log log) {
		if (log.getCreateTime() == null) {
			log.setCreateTime(new Date());
		}

		if (log.getFlag() == null) {
			log.setFlag(Boolean.TRUE);
		}
		logDao.save(log);

	}

	/**
	 * 分页查询日志
	 */
	@Override
	public Page<Log> findLogs(Map<String, Object> params) {
		
	int count = 	logDao.count(params);
	List<Log> list = Collections.emptyList();
	if(count>0) {
		PageUtil.pageParamConver(params, true);
		list = logDao.findData(params);
	}
		return new Page<Log>(count,list);
	}

}
