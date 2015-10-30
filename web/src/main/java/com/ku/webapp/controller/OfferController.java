package com.ku.webapp.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ku.common.KUException;
import com.ku.model.Offer;
import com.ku.model.OfferLabel;
import com.ku.service.OfferManager;
import com.ku.util.StringUtil;

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
	
	@RequestMapping(value = "/get/offers", method = RequestMethod.GET)
	public @ResponseBody String getOffers(final HttpServletRequest request,
			final HttpServletResponse response,
			@RequestParam("label") String label, @RequestParam("pageNo") String pageNo)
			throws KUException {
		log.info("getting offers for :: " + label);
		List<String> labels = new ArrayList<String>();
		if(!StringUtil.isEmptyString(label)){
			if(label.contains("-")){
				labels.addAll(Arrays.asList(label.split("-")));
			} else {
				labels.add(label);
			}
		}
		if(!StringUtil.isEmptyString(pageNo)) {
			int endLimit = Integer.parseInt(pageNo) * 25;
			int startLimit = endLimit - 25;
			String content = offerManager.getOffersContent(labels, startLimit , 25); 
			return content;
		} else {
			return "";
		}
	}
	
	@RequestMapping(value = "/pullFlipkart", method = RequestMethod.GET)
	public @ResponseBody String pullFlipkart(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException, ParseException, IOException {
		log.info("pull flipkart");
		//offerManager.pullFlipkartOffers();
		return "success";
	}
	
	@RequestMapping(value = "/{label}", method = RequestMethod.GET)
	public ModelAndView showOffers(final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("label") String label)
			throws KUException {
		log.info("getting offers for :: " + label);
		List<String> labels = new ArrayList<String>();
		if(!StringUtil.isEmptyString(label)){
			if(label.contains("-")){
				labels.addAll(Arrays.asList(label.split("-")));
			} else {
				labels.add(label);
			}
		}
		//label = label.replaceAll("-", " ");
		Model model = new ExtendedModelMap();
		model.addAttribute("label", label);
		model.addAttribute("activeMenu", "offer-link");
		model.addAttribute("offers", offerManager.getOffersByLabels(labels, 0 , 25));
		Date now = new Date();
		SimpleDateFormat dt1 = new SimpleDateFormat("MMMMM d yyyy");
		String title = (label.contains("offer") || label.contains("coupon") || label.contains("deal") ? label.replace("-", " ") : label.replace("-", " ") + " offers, deals, coupons ") + " - "+dt1.format(now);
		model.addAttribute("pageTitle", title);
		String keywords = label;
		label = label.replaceAll("-", " ");
		if(!label.contains("offer") && !label.contains("coupon") && !label.contains("deal")){
			keywords += ", "+label+" offer, "+label+" coupon, "+label+",  latest "+label+" offers, best "+label+" deals, new "+label +" coupons, today "+label+" offers";
		} else {
			keywords += ", latest "+label+", best "+label+", new "+label+", today "+label;
		}
		model.addAttribute("metaKeywords", keywords);
		String description = " find latest "+ (label.contains("offer") || label.contains("coupon") || label.contains("deal") ? label.replace("-", " ") : label.replace("-", " ") + " offers, deals, coupons ")+" which is verfied and updated frequently";
		model.addAttribute("metaDescription", description );
		return new ModelAndView("/ku/offers", model.asMap());
	}
	
}
