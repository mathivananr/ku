package com.ku.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ku.common.KUException;
import com.ku.dao.PageDao;
import com.ku.model.Page;
import com.ku.model.User;

@Repository
public class PageDaoHibernate extends GenericDaoHibernate<Page, Long> implements
		PageDao {

	public PageDaoHibernate() {
		super(Page.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public Page savePage(Page page) {
		return (Page) getSession().merge(page);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Page> getAllPage() {
		return getSession().createCriteria(Page.class).list();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Page getPageById(Long pageId) throws KUException {
		List<Page> pages = getSession().createCriteria(Page.class)
				.add(Restrictions.eq("pageId", pageId)).list();
		if (pages != null && pages.size() > 0) {
			return pages.get(0);
		} else {
			throw new KUException("No Page found for id " + pageId);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Page getPageByName(String pageName) throws KUException {
		List<Page> pages = getSession().createCriteria(Page.class)
				.add(Restrictions.eq("pageName", pageName)).list();
		if (pages != null && pages.size() > 0) {
			return pages.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Page getPageByOwnerId(String ownerId) throws KUException {
		List<Page> pages = getSession().createCriteria(Page.class)
				.add(Restrictions.eq("owner.id", Long.parseLong(ownerId))).list();
		if (pages != null && pages.size() > 0) {
			return pages.get(0);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public User getOwnerByPageId(String pageId) throws KUException {
		List<Page> pages = getSession().createCriteria(Page.class)
				.add(Restrictions.eq("pageId", Long.parseLong(pageId))).list();
		if (pages != null && pages.size() > 0) {
			return pages.get(0).getOwner();
		} else {
			return null;
		}
	}
}
