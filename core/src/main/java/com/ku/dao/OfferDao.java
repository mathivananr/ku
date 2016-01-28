package com.ku.dao;

import java.util.Calendar;
import java.util.List;

import com.ku.common.KUException;
import com.ku.model.Offer;
import com.ku.model.OfferLabel;

public interface OfferDao extends GenericDao<Offer, Long> {

	Offer saveOffer(Offer offer);

	List<Offer> getAllOffers() throws KUException;

	Offer getOfferById(Long offerId) throws KUException;

	Offer getOfferByTitle(String offerTitle) throws KUException;

	Offer getOffer(String offerTitle, String merchantName,
			String couponCode, Calendar offerEnd, String targetURL) throws KUException;
			
	List<Offer> getOffersByLabel(String label) throws KUException;

	List<OfferLabel> getOfferLabels(List<String> labelsString)
			throws KUException;

	List<OfferLabel> getAllOfferLabels() throws KUException;

	OfferLabel getOfferLabelById(Long labelId) throws KUException;

	OfferLabel getOfferLabelByLabel(String label) throws KUException;

	OfferLabel saveOfferLabel(OfferLabel offerLabel) throws KUException;
	
	List<Offer> getOffersByLabels(List<String> labels, int start, int end)
			throws KUException;

	List<String> getSuggestLabels(String label)
			throws KUException;
}
