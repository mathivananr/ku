package com.ku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ku.common.KUException;
import com.ku.dao.ReportDao;
import com.ku.model.Report;
import com.ku.model.Report;
import com.ku.service.ReportManager;
import com.ku.util.CommonUtil;

@Service("reportManager")
public class ReportManagerImpl extends GenericManagerImpl<Report, Long>
		implements ReportManager {

	private ReportDao reportDao;

	@Autowired
	public ReportManagerImpl(ReportDao reportDao) {
		super(reportDao);
		this.reportDao = reportDao;
	}

	public Report saveReport(Report report) throws KUException {
		return reportDao.saveReport(report);
	}

	public List<Report> getAllReport() throws KUException {
		return reportDao.getAllReport();
	}

	public Report getReportById(Long reportId) throws KUException {
		return reportDao.getReportById(reportId);
	}

	public List<Report> getMyReport() throws KUException {
		return reportDao.getReportByOwnerId(CommonUtil.getLoggedInUserId());
	}
}
