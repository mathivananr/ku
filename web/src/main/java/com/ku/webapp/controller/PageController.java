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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ku.Constants;
import com.ku.common.KUException;
import com.ku.model.MerchantType;
import com.ku.model.Page;
import com.ku.service.MerchantManager;
import com.ku.service.PageManager;
import com.ku.service.ReportManager;
import com.ku.util.CommonUtil;
import com.ku.util.StringUtil;

@Controller
public class PageController extends BaseFormController {

	private PageManager pageManager;
	private MerchantManager merchantManager;
	private ReportManager reportManager;

	@Autowired
	public void setPageManager(PageManager pageManager) {
		this.pageManager = pageManager;
	}

	@Autowired
	public void setMerchantManager(MerchantManager merchantManager) {
		this.merchantManager = merchantManager;
	}

	@Autowired
	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	@ModelAttribute
	@RequestMapping(value = "/user/createPage", method = RequestMethod.GET)
	public ModelAndView showCreatePage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute(Constants.PAGE, new Page());
		return new ModelAndView("/user/createPage", model.asMap());
	}

	@ModelAttribute
	@RequestMapping(value = "/user/editPage", method = RequestMethod.GET)
	public ModelAndView showEditPage(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		try {
			model.addAttribute(Constants.PAGE, pageManager.getMyPage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			saveError(request, "you dont own any page yet! just create one");
			model.addAttribute(Constants.PAGE, new Page());
		}
		return new ModelAndView("/user/createPage", model.asMap());
	}

	@ModelAttribute
	@RequestMapping(value = "/admin/pages", method = RequestMethod.GET)
	public ModelAndView showPages(final HttpServletRequest request,
			final HttpServletResponse response) throws KUException {
		Model model = new ExtendedModelMap();
		model.addAttribute(Constants.PAGE_LIST, pageManager.getAllPage());
		return new ModelAndView("/admin/pageList", model.asMap());
	}

	@ModelAttribute
	@RequestMapping(value = "/user/savePage", method = RequestMethod.POST)
	public ModelAndView savePage(Page page, BindingResult errors,
			HttpServletRequest request) {
		Model model = new ExtendedModelMap();
		try {
			Calendar now = new GregorianCalendar();
			if (StringUtil.isEmptyString(page.getPageId())) {
				page.setCreatedOn(now);
			}
			page.setUpdatedOn(now);
			page.setOwner(CommonUtil.getLoggedInUser());
			Page updatedPage = pageManager.savePage(page);
			saveMessage(request, "Page saved successfully.");
			model.addAttribute(Constants.PAGE, updatedPage);
			return new ModelAndView("/user/createPage", model.asMap());
		} catch (KUException e) {
			model.addAttribute(Constants.PAGE, page);
			return new ModelAndView("/user/createPage", model.asMap());
		}
	}

	@RequestMapping(value = "/shopping/{pageName}", method = RequestMethod.GET)
	public ModelAndView showShoppingPage(final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("pageName") String pageName) throws KUException {
		Model model = new ExtendedModelMap();
		try {
			Set<MerchantType> merchantTypes = new HashSet<MerchantType>(
					merchantManager.getAllMerchantTypes());
			Page page = pageManager.getPageByName(pageName);
			if (page != null) {
				model.addAttribute(Constants.MERCHANT_TYPE_LIST, merchantTypes);
				model.addAttribute(Constants.PAGE, page);
			} else {
				saveError(request, "oops! the page not found");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			saveError(request, "oops! problem in this page contact page owner");
		}
		return new ModelAndView("/ku/page", model.asMap());
	}
	
	@RequestMapping(value = "/gotoMerchant/{pageId}/{merchantId}", method = RequestMethod.GET)
	public ModelAndView gotoMerchantURL(final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("pageId") String pageId,
			@PathVariable("merchantId") String merchantId) throws KUException {
		String url = "/";
		try {
			url = pageManager.getMerchantURL(pageId, merchantId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			saveError(request,
					"oops! problem in this page merchant, contact page owner");
		}
		return new ModelAndView("redirect:" + url);
	}
	
	@RequestMapping(value = "/gotoOffer/{pageId}/{offerId}", method = RequestMethod.GET)
	public ModelAndView gotoOfferURL(final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("pageId") String pageId,
			@PathVariable("offerId") String offerId) throws KUException {
		Model model = new ExtendedModelMap();
		
		return new ModelAndView("/ku/page", model.asMap());
	}
}
