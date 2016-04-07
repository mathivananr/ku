package com.ku.webapp.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ku.Constants;
import com.ku.common.KUException;
import com.ku.model.Merchant;
import com.ku.model.MerchantType;
import com.ku.service.MerchantManager;
import com.ku.util.StringUtil;

@Controller
public class MerchantController extends BaseFormController {

	private MerchantManager merchantManager;

	@Autowired
	public void setMerchantManager(MerchantManager merchantManager) {
		this.merchantManager = merchantManager;
	}

	@RequestMapping(value = "/shopping", method = RequestMethod.GET)
	public ModelAndView showShoppingPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		Set<MerchantType> merchantTypes = new HashSet<MerchantType>(
				merchantManager.getAllMerchantTypes());
		model.addAttribute(Constants.MERCHANT_TYPE_LIST, merchantTypes);
		model.addAttribute("activeMenu", "shopping-link");
		return new ModelAndView("/ku/shopping", model.asMap());
	}
	
	@RequestMapping(value = "/contribute", method = RequestMethod.GET)
	public ModelAndView showContributePage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		Set<MerchantType> merchantTypes = new HashSet<MerchantType>(
				merchantManager.getAllMerchantTypes());
		model.addAttribute(Constants.MERCHANT_TYPE_LIST, merchantTypes);
		model.addAttribute("activeMenu", "shopping-link");
		return new ModelAndView("/ku/shopping", model.asMap());
	}
	
	@RequestMapping(value = "/booking", method = RequestMethod.GET)
	public ModelAndView showBookingPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		Set<MerchantType> merchantTypes = new HashSet<MerchantType>(
				merchantManager.getMerchantTypeLikeName(Constants.MERCHANT_TYPE_BOOKING));
		model.addAttribute(Constants.MERCHANT_TYPE_LIST, merchantTypes);
		model.addAttribute("activeMenu", "booking-link");
		return new ModelAndView("/ku/booking", model.asMap());
	}

	/*@RequestMapping(value = "/recharge", method = RequestMethod.GET)
	public ModelAndView showRechargePage(final HttpServletRequest request,
			final HttpServletResponse response) throws MUException {
		Model model = new ExtendedModelMap();
		try {
			model.addAttribute("merchantType", merchantManager
					.getMerchantTypeByName(Constants.MERCHANT_TYPE_RECHARGE));
		} catch (MUException e) {
			model.addAttribute("merchantType", new MerchantType());
		}
		model.addAttribute("activeMenu", "recharge-link");
		return new ModelAndView("/ku/rechargeForm", model.asMap());
	}*/
	
	@ModelAttribute
	@RequestMapping(value = "/admin/merchant", method = RequestMethod.GET)
	public ModelAndView showMerchantPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		String merchantId = request.getParameter("id");
		Set<MerchantType> merchantTypes = new HashSet<MerchantType>(
				merchantManager.getAllMerchantTypes());
		model.addAttribute(Constants.MERCHANT_TYPE_LIST, merchantTypes);
		if (!StringUtil.isEmptyString(merchantId)) {
			model.addAttribute("merchant",
					merchantManager.getMerchantById(Long.parseLong(merchantId)));
		} else {
			model.addAttribute(Constants.MERCHANT, new Merchant());
		}
		return new ModelAndView("/admin/merchant", model.asMap());
	}

	@ModelAttribute
	@RequestMapping(value = "/admin/merchants", method = RequestMethod.GET)
	public ModelAndView showMerchants(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute(Constants.MERCHANT_LIST,
				merchantManager.getAllMerchant());
		return new ModelAndView("/admin/merchantList", model.asMap());
	}

	@ModelAttribute
	@RequestMapping(value = "/admin/merchantType", method = RequestMethod.GET)
	public ModelAndView showMerchantTypePage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		String merchantTypeId = request.getParameter("id");
		if (!StringUtil.isEmptyString(merchantTypeId)) {
			model.addAttribute("merchantType", merchantManager
					.getMerchantTypeById(Long.parseLong(merchantTypeId)));
		} else {
			model.addAttribute("merchantType", new MerchantType());
		}
		return new ModelAndView("/admin/merchantType", model.asMap());
	}

	@ModelAttribute
	@RequestMapping(value = "/admin/merchantTypes", method = RequestMethod.GET)
	public ModelAndView showMerchantTypes(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		Set<MerchantType> merchantTypes = new HashSet<MerchantType>(
				merchantManager.getAllMerchantTypes());
		model.addAttribute(Constants.MERCHANT_TYPE_LIST, merchantTypes);
		return new ModelAndView("/admin/merchantTypeList", model.asMap());
	}

	@ModelAttribute
	@RequestMapping(value = "/admin/saveMerchantType", method = RequestMethod.POST)
	public ModelAndView saveMerchantType(MerchantType merchantType,
			BindingResult errors, HttpServletRequest request) {
		Model model = new ExtendedModelMap();
		Calendar now = new GregorianCalendar();
		if (StringUtil.isEmptyString(merchantType.getTypeId())) {
			merchantType.setCreatedOn(now);
		}
		merchantType.setUpdatedOn(now);
		merchantManager.saveMerchantType(merchantType);
		saveMessage(request, "Merchant type saved successfully.");
		model.addAttribute(Constants.MERCHANT_TYPE_LIST,
				merchantManager.getAllMerchantTypes());
		return new ModelAndView("/admin/merchantTypeList", model.asMap());
	}

	@ModelAttribute
	@RequestMapping(value = "/admin/saveMerchantDetails", method = RequestMethod.POST)
	public ModelAndView saveMerchant(Merchant merchant, BindingResult errors,
			HttpServletRequest request) {
		Model model = new ExtendedModelMap();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
					.getFile("file");
			Calendar now = new GregorianCalendar();
			if (StringUtil.isEmptyString(merchant.getMerchantId())) {
				merchant.setCreatedOn(now);
			}
			merchant.setUpdatedOn(now);
			String uploadDir = getServletContext().getRealPath("/files");
			if(StringUtil.isEmptyString(uploadDir)) {
				uploadDir = getServletContext().getRealPath("/images");
			}
			merchantManager.saveMerchant(merchant, file, uploadDir);
			saveMessage(request, "Merchant saved successfully.");
			model.addAttribute(Constants.MERCHANT_LIST,
					merchantManager.getAllMerchant());
			return new ModelAndView("/admin/merchantList", model.asMap());
		} catch (KUException e) {
			Set<MerchantType> merchantTypes = new HashSet<MerchantType>(
					merchantManager.getAllMerchantTypes());
			model.addAttribute(Constants.MERCHANT_TYPE_LIST, merchantTypes);
			model.addAttribute(Constants.MERCHANT, merchant);
			return new ModelAndView("/admin/merchant", model.asMap());
		}
	}
}
