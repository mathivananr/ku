package com.ku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ku.Constants;
import com.ku.common.KUException;
import com.ku.dao.SupportRequestDao;
import com.ku.model.SupportRequest;
import com.ku.service.SupportRequestManager;

@Service("supportRequest")
public class SupportRequestManagerImpl extends
		GenericManagerImpl<SupportRequest, Long> implements
		SupportRequestManager {

	private SupportRequestDao supportRequestDao;

	@Autowired
	public SupportRequestManagerImpl(SupportRequestDao supportRequestDao) {
		super(supportRequestDao);
		this.supportRequestDao = supportRequestDao;
	}

	public SupportRequest closeSupportRequest(String id) throws KUException {
		SupportRequest supportRequest = supportRequestDao.get(Long
				.parseLong(id));
		supportRequest.setStatus(Constants.SR_CLOSE);
		supportRequestDao.save(supportRequest);
		return supportRequest;
	}
}
