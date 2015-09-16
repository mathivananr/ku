package com.ku.service;

import com.ku.common.KUException;
import com.ku.model.SupportRequest;

public interface SupportRequestManager extends
		GenericManager<SupportRequest, Long> {
	SupportRequest closeSupportRequest(String id) throws KUException;
}
