package com.ku.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ku.dao.LookupDao;
import com.ku.model.AppConfig;
import com.ku.model.MerchantType;
import com.ku.model.Role;

/**
 * Hibernate implementation of LookupDao.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         jgarcia: updated to hibernate 4
 */
@Repository
public class LookupDaoHibernate implements LookupDao {
	private Log log = LogFactory.getLog(LookupDaoHibernate.class);
	private final SessionFactory sessionFactory;

	/**
	 * Initialize LookupDaoHibernate with Hibernate SessionFactory.
	 * 
	 * @param sessionFactory
	 */
	@Autowired
	public LookupDaoHibernate(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Role> getRoles() {
		log.debug("Retrieving all role names...");
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Role.class).list();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<MerchantType> getMerchantTypes() {
		log.debug("Retrieving all merchant types...");
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(MerchantType.class).list();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<AppConfig> getConfigs() {
		log.debug("Retrieving ku configs...");
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(AppConfig.class).list();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAppConfigTypes() {
		log.debug("Retrieving ku config types...");
		Session session = sessionFactory.getCurrentSession();
		return session
				.createCriteria(AppConfig.class)
				.setProjection(
						Projections.distinct(Projections.property("dataType")))
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<AppConfig> getAppConfigsByType(String type) {
		log.debug("Retrieving all Configs...");
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(AppConfig.class)
				.add((Criterion) Restrictions.eq("dataType", type)).list();
	}
}
