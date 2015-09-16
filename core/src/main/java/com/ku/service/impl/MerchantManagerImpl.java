package com.ku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ku.common.KUException;
import com.ku.dao.MerchantDao;
import com.ku.model.Merchant;
import com.ku.model.MerchantType;
import com.ku.service.MerchantManager;

@Service("merchantManager")
public class MerchantManagerImpl extends GenericManagerImpl<Merchant, Long>
		implements MerchantManager {

	private MerchantDao merchantDao;

	@Autowired
	public MerchantManagerImpl(MerchantDao merchantDao) {
		super(merchantDao);
		this.merchantDao = merchantDao;
	}

	public Merchant saveMerchant(Merchant merchant) {
		return merchantDao.saveMerchant(merchant);
	}

	public List<Merchant> getAllMerchant() {
		return merchantDao.getAllMerchant();
	}

	public Merchant getMerchantById(Long merchantId) throws KUException {
		return merchantDao.getMerchantById(merchantId);
	}

	public Merchant getMerchantByName(String merchantName) throws KUException {
		return merchantDao.getMerchantByName(merchantName);
	}

	public MerchantType saveMerchantType(MerchantType merchantType) {
		return merchantDao.saveMerchantType(merchantType);
	}

	public List<MerchantType> getAllMerchantTypes() {
		return merchantDao.getAllMerchantTypes();
	}

	public MerchantType getMerchantTypeById(Long merchantTypeId)
			throws KUException {
		return merchantDao.getMerchantTypeById(merchantTypeId);
	}

	public List<Merchant> getMerchantByType(String merchantType) {
		return merchantDao.getMerchantByType(merchantType);
	}

	public List<Merchant> getMerchantByTypes(List<String> merchantTypes) {
		return merchantDao.getMerchantByTypes(merchantTypes);
	}

	public MerchantType getMerchantTypeByName(String merchantTypeName)
			throws KUException {
		return merchantDao.getMerchantTypeByName(merchantTypeName);
	}
	
	public List<MerchantType> getMerchantTypeLikeName(String merchantTypeName)
			throws KUException{
		return merchantDao.getMerchantTypeLikeName(merchantTypeName);
	}
}
