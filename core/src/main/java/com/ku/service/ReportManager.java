package com.ku.service;

import java.util.List;

import com.ku.common.KUException;
import com.ku.model.Report;

public interface ReportManager extends GenericManager<Report, Long> {

		Report saveReport(Report report) throws KUException;

		List<Report> getAllReport() throws KUException;

		Report getReportById(Long reportId) throws KUException;

		Report getMyReport() throws KUException;

}
