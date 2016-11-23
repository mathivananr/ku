package com.ku.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ku.common.KUException;
import com.ku.dao.ReportDao;
import com.ku.model.Report;

@Repository
public class ReportDaoHibernate extends GenericDaoHibernate<Report, Long>
		implements ReportDao {

	public ReportDaoHibernate() {
		super(Report.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public Report saveReport(Report report) {
		return (Report) getSession().merge(report);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Report> getAllReport() {
		return getSession().createCriteria(Report.class).list();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Report getReportById(Long reportId) throws KUException {
		List<Report> reports = getSession().createCriteria(Report.class)
				.add(Restrictions.eq("reportId", reportId)).list();
		if (reports != null && reports.size() > 0) {
			return reports.get(0);
		} else {
			throw new KUException("No Report found for id " + reportId);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Report> getReportByOwnerId(String ownerId) throws KUException {
		log.info(ownerId);
		List<Report> reports = getSession().createCriteria(Report.class)
				.add(Restrictions.eq("owner.id", Long.parseLong(ownerId)))
				.list();
		if (reports != null && reports.size() > 0) {
			return reports;
		} else {
			return null;
		}
	}
}