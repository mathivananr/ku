package com.ku.service;

import com.ku.common.KUException;
import com.ku.model.AppLog;

public interface LogManager extends GenericManager<AppLog, Long> {
	
	AppLog log(AppLog log) throws KUException;
	
}
