package com.ku.service;

import java.util.List;

import com.ku.common.KUException;
import com.ku.model.Page;
import com.ku.model.User;

public interface PageManager extends GenericManager<Page, Long> {

	Page savePage(Page page) throws KUException;

	List<Page> getAllPage() throws KUException;

	Page getPageById(Long pageId) throws KUException;

	Page getPageByName(String pageName) throws KUException;
	
	Page getMyPage() throws KUException;
	
	String getMerchantURL(String pageId, String merchantId) throws KUException;
	
	User getOwnerByPageId(String pageId) throws KUException;
}
