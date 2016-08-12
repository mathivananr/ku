package com.ku.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ku.common.KUException;
import com.ku.service.ReportManager;

@Controller
public class ReportController extends BaseFormController {

	private ReportManager reportManager;

	@Autowired
	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}
	
	@RequestMapping(value = "/user/report", method = RequestMethod.GET)
	public ModelAndView updateCoupon(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "offer-link");
		model.addAttribute("report", reportManager.getMyReport());
		return new ModelAndView("/user/report", model.asMap());
	}
}
