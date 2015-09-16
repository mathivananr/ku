package com.ku.dao.hibernate;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ku.common.KUException;
import com.ku.dao.LogDao;
import com.ku.model.AppLog;

@Repository("logDao")
public class LogDaoHibernate extends GenericDaoHibernate<AppLog, Long>
		implements LogDao {

	/**
	 * Constructor to create a Generics-based version using AppLog as the entity
	 */
	public LogDaoHibernate() {
		super(AppLog.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@Transactional
	public AppLog log(AppLog appLog) throws KUException {
		try {
			return (AppLog) getSession().merge(appLog);
		} catch (HibernateException e) {
			throw new KUException(e.getMessage(), e);
		}
	}
}
