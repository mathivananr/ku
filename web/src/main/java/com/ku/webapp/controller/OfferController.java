package com.ku.webapp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ku.common.KUException;
import com.ku.model.Offer;
import com.ku.model.OfferLabel;
import com.ku.service.OfferManager;

@Controller
public class OfferController extends BaseFormController {

	private OfferManager offerManager;

	@Autowired
	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}

	@RequestMapping(value = "/add-coupon", method = RequestMethod.GET)
	public ModelAndView showCouponForm(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "offer-link");
		model.addAttribute("offer", new Offer());
		return new ModelAndView("/ku/coupon", model.asMap());
	}

	@RequestMapping(value = "/add-coupon", method = RequestMethod.POST)
	@ModelAttribute
	public ModelAndView addCoupon(Offer offer, BindingResult errors,
			final HttpServletRequest request, final HttpServletResponse response) {
		Model model = new ExtendedModelMap();
		try {
			offer = offerManager.saveOffer(offer);
			model.addAttribute("offer", offer);
		} catch (KUException e) {
			e.printStackTrace();
			model.addAttribute("offer", offer);
		}
		model.addAttribute("activeMenu", "offer-link");
		return new ModelAndView("/ku/coupon", model.asMap());
	}

	@RequestMapping(value = "/coupon-list", method = RequestMethod.GET)
	public ModelAndView updateCoupon(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "offer-link");
		model.addAttribute("offerList", offerManager.getAllOffers());
		return new ModelAndView("/ku/coupon-list", model.asMap());
	}

	@RequestMapping(value = "/edit-coupon", method = RequestMethod.GET)
	public ModelAndView showCouponUpdate(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		String offerId = request.getParameter("offerId");
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "offer-link");
		model.addAttribute("offer",
				offerManager.getOfferById(Long.parseLong(offerId)));
		return new ModelAndView("/ku/coupon", model.asMap());
	}

	@RequestMapping(value = "/add-label", method = RequestMethod.GET)
	public ModelAndView showOfferLabelForm(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "offer-link");
		model.addAttribute("offerLabel", new OfferLabel());
		return new ModelAndView("/ku/offer-label", model.asMap());
	}

	@RequestMapping(value = "/add-label", method = RequestMethod.POST)
	@ModelAttribute
	public ModelAndView addLabel(OfferLabel offerLabel, BindingResult errors,
			final HttpServletRequest request, final HttpServletResponse response) {
		Model model = new ExtendedModelMap();
		try {
			offerLabel = offerManager.saveOfferLabel(offerLabel);
			model.addAttribute("offerLabel", offerLabel);
		} catch (KUException e) {
			e.printStackTrace();
			model.addAttribute("offerLabel", offerLabel);
		}
		model.addAttribute("activeMenu", "offer-link");
		return new ModelAndView("/ku/offer-label", model.asMap());
	}

	@RequestMapping(value = "/edit-label", method = RequestMethod.GET)
	public ModelAndView showOfferLabelUpdate(final HttpServletRequest request,
			final HttpServletResponse response){
		String labelId = request.getParameter("labelId");
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "offer-link");
		try {
			model.addAttribute("offerLabel",
				offerManager.getOfferLabelById(Long.parseLong(labelId)));
		} catch(KUException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("offerLabel", new Offer());
			saveError(request, e.getMessage());
		}
		return new ModelAndView("/ku/offer-label", model.asMap());
	}
	
	@RequestMapping(value = "/label-list", method = RequestMethod.GET)
	public ModelAndView listLabel(final HttpServletRequest request,
			final HttpServletResponse response){
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "offer-link");
		try {
			model.addAttribute("offerLabelList", offerManager.getAllOfferLabels());
		}catch(KUException e) {
			log.error(e.getMessage(), e);
			model.addAttribute("offerLabelList", new ArrayList<OfferLabel>());
			saveError(request, e.getMessage());
		}
		return new ModelAndView("/ku/offer-label-list", model.asMap());
	}
	
	@RequestMapping(value = "/{label}", method = RequestMethod.GET)
	public ModelAndView showOffers(final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("label") String label)
			throws KUException {
		log.info("getting offers for :: " + label);
		label = label.replaceAll("-", " ");
		Model model = new ExtendedModelMap();
		model.addAttribute("activeMenu", "offer-link");
		model.addAttribute("offers", offerManager.getOffersByLabel(label));
		return new ModelAndView("/ku/offers", model.asMap());
	}
}
