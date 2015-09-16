package com.ku.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ku.common.KUException;

@Controller
public class CommonController extends BaseFormController {

	@RequestMapping(value = "/site/termsAndConditions", method = RequestMethod.GET)
	public ModelAndView showTACPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		return new ModelAndView("/site/termsAndConditions", model.asMap());
	}

	@RequestMapping(value = "/site/privacyPolicy", method = RequestMethod.GET)
	public ModelAndView showPrivacyPolicyPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		return new ModelAndView("/site/privacyPolicy", model.asMap());
	}

	@RequestMapping(value = "/site/about", method = RequestMethod.GET)
	public ModelAndView showAboutPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		return new ModelAndView("/site/about", model.asMap());
	}

	@RequestMapping(value = "/site/contact", method = RequestMethod.GET)
	public ModelAndView showContactPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		return new ModelAndView("/site/contact", model.asMap());
	}

	@RequestMapping(value = "/site/faq", method = RequestMethod.GET)
	public ModelAndView showFAQPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		return new ModelAndView("/site/faq", model.asMap());
	}
}
