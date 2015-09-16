package com.ku.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ku.common.KUException;
import com.ku.model.AppLog;
import com.ku.service.LogManager;

@Controller
public class LogController extends BaseFormController {
	private LogManager logManager;

	@Autowired
	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	@RequestMapping(value = "/log", method = RequestMethod.GET)
	public @ResponseBody
	void log(final HttpServletRequest request,
			final HttpServletResponse response,
			@RequestParam("title") String title,
			@RequestParam("link") String link) throws KUException {
		log.info(request.getRemoteAddr() + " clicked :: " + link + " :: "
				+ title);
		logManager.log(new AppLog(title, link, request.getRemoteAddr()));
	}
}
