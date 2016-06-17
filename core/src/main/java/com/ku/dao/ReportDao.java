package com.ku.dao;

import java.util.List;

import com.ku.common.KUException;
import com.ku.model.Report;

public interface ReportDao extends GenericDao<Report, Long> {

	Report saveReport(Report report);

	List<Report> getAllReport();

	Report getReportById(Long reportId) throws KUException;

	
	Report getReportByOwnerId(String userId) throws KUException;
}