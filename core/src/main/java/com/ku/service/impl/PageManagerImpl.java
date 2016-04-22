package com.ku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ku.common.KUException;
import com.ku.dao.PageDao;
import com.ku.model.Page;
import com.ku.service.PageManager;
import com.ku.util.CommonUtil;

@Service("pageManager")
public class PageManagerImpl extends GenericManagerImpl<Page, Long> implements
		PageManager {

	private PageDao pageDao;

	@Autowired
	public PageManagerImpl(PageDao pageDao) {
		super(pageDao);
		this.pageDao = pageDao;
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
}
