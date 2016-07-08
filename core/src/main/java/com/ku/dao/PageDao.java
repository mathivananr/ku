package com.ku.dao;

import java.util.List;

import com.ku.common.KUException;
import com.ku.model.Page;
import com.ku.model.User;

public interface PageDao extends GenericDao<Page, Long> {

	Page savePage(Page page);

	List<Page> getAllPage();

	Page getPageById(Long pageId) throws KUException;

	Page getPageByName(String pageName) throws KUException;
	
	Page getPageByOwnerId(String userId) throws KUException;
	
	User getOwnerByPageId(String pageId) throws KUException;
}
