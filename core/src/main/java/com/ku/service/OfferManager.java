package com.ku.service;

import java.util.Calendar;
import java.util.List;

import com.ku.common.KUException;
import com.ku.model.Offer;
import com.ku.model.OfferLabel;

public interface OfferManager extends GenericManager<Offer, Long> {

	Offer saveOffer(Offer offer) throws KUException;

	List<Offer> getAllOffers() throws KUException;

	Offer getOfferById(Long offerId) throws KUException;

	Offer getOfferByTitle(String offerTitle) throws KUException;

	Offer getOffer(String offerTitle, String merchantName,
			String couponCode, Calendar offerEnd) throws KUException;
			
	List<Offer> getOffersByLabel(String label) throws KUException;

	List<Offer> saveOffers(List<Offer> offers) throws KUException;

	List<OfferLabel> getAllOfferLabels() throws KUException;

	OfferLabel getOfferLabelById(Long labelId) throws KUException;

	OfferLabel getOfferLabelByLabel(String label) throws KUException;

	OfferLabel saveOfferLabel(OfferLabel offerLabel) throws KUException;
	
	List<OfferLabel> saveOfferLabels(List<OfferLabel> offerLabels)
			throws KUException;
	
	List<OfferLabel> importOfferLabels(String filePath)
			throws KUException;
}
