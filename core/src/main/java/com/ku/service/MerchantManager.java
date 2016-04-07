package com.ku.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ku.common.KUException;
import com.ku.model.Merchant;
import com.ku.model.MerchantType;
import com.ku.model.Offer;
import com.ku.model.OfferLabel;

public interface MerchantManager extends GenericManager<Merchant, Long> {

	Merchant saveMerchant(Merchant merchant);
	
	Merchant saveMerchant(Merchant merchant, CommonsMultipartFile file,
			String uploadDir) throws KUException;
	
	List<Merchant> getAllMerchant();

	Merchant getMerchantById(Long merchantId) throws KUException;

	Merchant getMerchantByName(String merchantName) throws KUException;

	MerchantType saveMerchantType(MerchantType merchantType);

	List<MerchantType> getAllMerchantTypes();

	MerchantType getMerchantTypeById(Long merchantTypeId) throws KUException;

	MerchantType getMerchantTypeByName(String merchantTypeName)
			throws KUException;

	List<MerchantType> getMerchantTypeLikeName(String merchantTypeName)
			throws KUException;
	
	List<Merchant> getMerchantByType(String merchantType);

	List<Merchant> getMerchantByTypes(List<String> merchantTypes);
	
	OfferLabel getOfferLabelByLabel(String label) throws KUException;
	
	OfferLabel saveOfferLabel(OfferLabel offerLabel) throws KUException;
}
