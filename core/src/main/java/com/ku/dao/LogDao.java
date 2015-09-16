package com.ku.dao;

import com.ku.common.KUException;
import com.ku.model.AppLog;

public interface LogDao extends GenericDao<AppLog, Long> {
	AppLog log(AppLog log) throws KUException;
}
