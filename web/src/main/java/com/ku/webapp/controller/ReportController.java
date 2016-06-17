package com.ku.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ku.service.ReportManager;

@Controller
public class ReportController extends BaseFormController {

	private ReportManager reportManager;

	@Autowired
	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}
}
