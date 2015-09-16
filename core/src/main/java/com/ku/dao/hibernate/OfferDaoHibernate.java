package com.ku.dao.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ku.common.KUException;
import com.ku.dao.OfferDao;
import com.ku.model.Offer;
import com.ku.model.OfferLabel;
import com.ku.util.StringUtil;

@Repository("offerDao")
public class OfferDaoHibernate extends GenericDaoHibernate<Offer, Long>
		implements OfferDao {

	/**
	 * Constructor to create a Generics-based version using Offer as the entity
	 */
	public OfferDaoHibernate() {
		super(Offer.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public Offer saveOffer(Offer offer) {
		return (Offer) getSession().merge(offer);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Offer> getAllOffers() throws KUException {
		try {
			return getSession().createCriteria(Offer.class).list();
		} catch (HibernateException e) {
			throw new KUException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<OfferLabel> getAllOfferLabels() throws KUException {
		try {
			return getSession().createCriteria(OfferLabel.class).list();
		} catch (HibernateException e) {
			throw new KUException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Offer getOfferById(Long offerId) throws KUException {
		List<Offer> offers = getSession().createCriteria(Offer.class)
				.add(Restrictions.eq("offerId", offerId)).list();
		if (offers != null && offers.size() > 0) {
			return offers.get(0);
		} else {
			throw new KUException("No Offer found for id " + offerId);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Offer getOffer(String offerTitle, String merchantName,
			String couponCode, Calendar offerEnd) throws KUException {
		Criteria criteria = getSession().createCriteria(Offer.class);
		if (!StringUtil.isEmptyString(offerTitle)) {
			criteria.add(Restrictions.eq("offerTitle", offerTitle));
		}
		if (!StringUtil.isEmptyString(merchantName)) {
			criteria.add(Restrictions.eq("merchantName", merchantName));
		}
		if (!StringUtil.isEmptyString(couponCode)) {
			criteria.add(Restrictions.eq("couponCode", couponCode));
		}
		if (!StringUtil.isEmptyString(offerEnd)) {
			criteria.add(Restrictions.eq("offerEnd", offerEnd));
		}
		List<Offer> offers = criteria.list();
		if (offers != null && offers.size() > 0) {
			return offers.get(0);
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
	public Offer getOfferByTitle(String offerTitle) throws KUException {
		List<Offer> offers = getSession().createCriteria(Offer.class)
				.add(Restrictions.eq("offerTitle", offerTitle)).list();
		if (offers != null && offers.size() > 0) {
			return offers.get(0);
		} else {
			throw new KUException("No Offer foud for title" + offerTitle);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Offer> getOffersByLabel(String label) throws KUException {
		try {
			Criteria c = getSession().createCriteria(Offer.class, "offer");
			c.createAlias("offer.labels", "offerLabel");
			c.add(Restrictions.eq("offerLabel.label", label));
			return (List<Offer>) c.list();
		} catch (HibernateException e) {
			throw new KUException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<OfferLabel> getOfferLabels(List<String> labelsString)
			throws KUException {

		List<OfferLabel> offerLabels = getSession()
				.createCriteria(OfferLabel.class)
				.add(Restrictions.in("label", labelsString)).list();
		if (offerLabels != null) {
			return offerLabels;
		} else {
			return new ArrayList<OfferLabel>();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public OfferLabel getOfferLabelById(Long labelId) throws KUException {
		List<OfferLabel> offerLabels = getSession()
				.createCriteria(OfferLabel.class)
				.add(Restrictions.eq("labelId", labelId)).list();
		if (offerLabels != null && offerLabels.size() > 0) {
			return offerLabels.get(0);
		} else {
			throw new KUException("No Offer found for id " + labelId);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public OfferLabel getOfferLabelByLabel(String label) throws KUException {
		List<OfferLabel> offerLabels = getSession()
				.createCriteria(OfferLabel.class)
				.add(Restrictions.eq("label", label)).list();
		if (offerLabels != null && offerLabels.size() > 0) {
			return offerLabels.get(0);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public OfferLabel saveOfferLabel(OfferLabel offerLabel) throws KUException {
		return (OfferLabel) getSession().merge(offerLabel);
	}
}
