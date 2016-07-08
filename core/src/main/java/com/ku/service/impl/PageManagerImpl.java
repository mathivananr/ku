package com.ku.service.impl;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ku.Constants;
import com.ku.common.KUException;
import com.ku.dao.PageDao;
import com.ku.model.Merchant;
import com.ku.model.Page;
import com.ku.model.Report;
import com.ku.model.User;
import com.ku.service.MerchantManager;
import com.ku.service.PageManager;
import com.ku.service.ReportManager;
import com.ku.util.CommonUtil;

@Service("pageManager")
public class PageManagerImpl extends GenericManagerImpl<Page, Long> implements
		PageManager {

	private PageDao pageDao;
	private MerchantManager merchantManager;
	private ReportManager reportManager;

	@Autowired
	public PageManagerImpl(PageDao pageDao) {
		super(pageDao);
		this.pageDao = pageDao;
	}
	
	@Autowired
	public void setMerchantManager(MerchantManager merchantManager) {
		this.merchantManager = merchantManager;
	}

	@Autowired
	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public Page savePage(Page page) throws KUException{
		return pageDao.savePage(page);
	}

	public List<Page> getAllPage() throws KUException{
		return pageDao.getAllPage();
	}

	public Page getPageById(Long pageId) throws KUException{
		return pageDao.getPageById(pageId);
	}

	public Page getPageByName(String pageName) throws KUException{
		return pageDao.getPageByName(pageName);
	}
	
	public Page getMyPage() throws KUException {
		return pageDao.getPageByOwnerId(CommonUtil.getLoggedInUserId()); 
	}
	
	public String getMerchantURL(String pageId, String merchantId) throws KUException {
		Merchant merchant = merchantManager.getMerchantById(Long.parseLong(merchantId));
		String targetLink = merchant.getTargetLink(); 
		Report report = new Report();
		report.setMerchantId(merchant.getMerchantId().toString());
		report.setMerchantName(merchant.getMerchantName());
		report.setTargetLink(targetLink);
		report.setCreatedOn(new GregorianCalendar());
		report.setUpdatedOn(new GregorianCalendar());
		report.setUsername(CommonUtil.getLoggedInUserName());
		report.setStatus(Constants.REFER);
		report.setConversionIp("");
		report.setOwner(getOwnerByPageId(pageId));
		reportManager.saveReport(report);
		return targetLink;
	}
	
	public User getOwnerByPageId(String pageId) throws KUException {
		return pageDao.getOwnerByPageId(pageId);
	}
}
