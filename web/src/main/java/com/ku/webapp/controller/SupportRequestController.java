package com.ku.webapp.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ku.Constants;
import com.ku.common.KUException;
import com.ku.model.JsonResponse;
import com.ku.model.SupportRequest;
import com.ku.service.SupportRequestManager;
import com.ku.util.StringUtil;

@Controller
public class SupportRequestController extends BaseFormController {

	private SupportRequestManager supportRequestManager;

	@Autowired
	public void setSupportRequestManager(
			SupportRequestManager supportRequestManager) {
		this.supportRequestManager = supportRequestManager;
	}

	@RequestMapping(value = "/admin/supportRequest", method = RequestMethod.GET)
	public ModelAndView supportRequestPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "recharge-link");
		String id = request.getParameter("id");
		if (!StringUtil.isEmptyString(id) && !id.equals("undefined")) {
			model.addAttribute("supportRequest",
					supportRequestManager.get(Long.parseLong(id)));
		} else {
			model.addAttribute("supportRequest", new SupportRequest());
		}
		return new ModelAndView("/ku/supportRequest", model.asMap());
	}

	@RequestMapping(value = "/site/supportRequest", method = RequestMethod.GET)
	public ModelAndView showSupportRequestPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "recharge-link");
		String id = request.getParameter("id");
		if (!StringUtil.isEmptyString(id) && !id.equals("undefined")) {
			model.addAttribute("supportRequest",
					supportRequestManager.get(Long.parseLong(id)));
		} else {
			model.addAttribute("supportRequest", new SupportRequest());
		}
		return new ModelAndView("/ku/supportRequest", model.asMap());
	}
	
	@RequestMapping(value = "/riseSupportRequest", method = RequestMethod.POST)
	public ModelAndView addUser(SupportRequest supportRequest,
			BindingResult errors, HttpServletRequest request) {
		Model model = new ExtendedModelMap();
		Calendar now = new GregorianCalendar();
		if (StringUtil.isEmptyString(supportRequest.getId())) {
			supportRequest.setCreatedOn(now);
			supportRequest.setStatus(Constants.SR_OPEN);
		}
		supportRequest.setUpdatedOn(now);
		supportRequestManager.save(supportRequest);
		model.addAttribute("activeMenu", "recharge-link");
		return new ModelAndView("/ku/rechargeForm", model.asMap());
	}

	@RequestMapping(value = "/admin/closeSupportRequest", method = RequestMethod.POST)
	public @ResponseBody
	JsonResponse completeRecharge(@RequestParam("id") String id) {
		JsonResponse res = new JsonResponse();
		try {
			if (!StringUtil.isEmptyString(id)) {
				supportRequestManager.closeSupportRequest(id);
				res.setStatus(Constants.SUCCESS);
				return res;
			} else {
				res.setStatus(Constants.FAIL);
				return res;
			}
		} catch (KUException e) {
			res.setStatus(Constants.FAIL);
			return res;
		}
	}

	@ModelAttribute
	@RequestMapping(value = "/admin/supportRequests", method = RequestMethod.GET)
	public ModelAndView showSupportRequests(final HttpServletRequest request,
			final HttpServletResponse response) {
		Model model = new ExtendedModelMap();
		model.addAttribute(Constants.SUPPORT_REQUEST_LIST,
				supportRequestManager.getAll());
		return new ModelAndView("/admin/supportRequestList", model.asMap());
	}
}
