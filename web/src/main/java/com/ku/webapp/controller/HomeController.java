package com.ku.webapp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ku.Constants;
import com.ku.common.KUException;
import com.ku.model.Offer;
import com.ku.service.OfferManager;

@Controller
@RequestMapping(value = { "", "/" })
public class HomeController extends BaseFormController {
	private OfferManager offerManager;

	@Autowired
	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showHome(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "offer-link");
		model.addAttribute("pageTitle", "");
		model.addAttribute("metaKeywords", "");
		model.addAttribute("metaDescription", "");
		model.addAttribute("label", "");
		model.addAttribute("pageNo", 2);
		try {
			model.addAttribute("offers", offerManager.getOffersByLabels(new ArrayList<String>(), 0 , Constants.OFFER_TO_LOAD));
		} catch(KUException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("offers", new ArrayList<Offer>());
		}
		return new ModelAndView("/ku/offers", model.asMap());
	}
}
