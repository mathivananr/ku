package com.ku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ku.common.KUException;
import com.ku.dao.LogDao;
import com.ku.model.AppLog;
import com.ku.service.LogManager;

@Service("logManager")
public class LogManagerImpl extends GenericManagerImpl<AppLog, Long> implements
		LogManager {

	private LogDao logDao;

	@Autowired
	public LogManagerImpl(LogDao logDao) {
		super(logDao);
		this.logDao = logDao;
	}

	public AppLog log(AppLog log) throws KUException {
		return logDao.log(log);
	}
}
