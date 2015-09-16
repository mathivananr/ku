package com.ku.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ku.dao.LookupDao;
import com.ku.model.AppConfig;
import com.ku.model.LabelValue;
import com.ku.model.MerchantType;
import com.ku.model.Role;
import com.ku.service.LookupManager;

/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
	@Autowired
	LookupDao dao;

	/**
	 * {@inheritDoc}
	 */
	public List<LabelValue> getAllRoles() {
		List<Role> roles = dao.getRoles();
		List<LabelValue> list = new ArrayList<LabelValue>();
		for (Role role1 : roles) {
			list.add(new LabelValue(role1.getName(), role1.getName()));
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<LabelValue> getMerchantTypes() {
		List<MerchantType> types = dao.getMerchantTypes();
		List<LabelValue> list = new ArrayList<LabelValue>();
		for (MerchantType type1 : types) {
			list.add(new LabelValue(type1.getTypeName(), type1.getTypeId()
					.toString()));
		}
		return list;
	}

	public Map<String, String> getConfigs() {
		Map<String, String> configs = new HashMap<String, String>();
		for (AppConfig config : dao.getConfigs()) {
			configs.put(config.getKeyName(), config.getKeyValue());
		}
		return configs;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getAppConfigTypes() {
		return dao.getAppConfigTypes();
	}

	public List<AppConfig> getAppConfigsByType(String type) {
		return dao.getAppConfigsByType(type);
	}
}
