package com.ku.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ku.common.KUException;
import com.ku.model.Merchant;
import com.ku.model.MerchantType;

public interface MerchantDao extends GenericDao<Merchant, Long> {

	Merchant saveMerchant(Merchant merchant);

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
}
