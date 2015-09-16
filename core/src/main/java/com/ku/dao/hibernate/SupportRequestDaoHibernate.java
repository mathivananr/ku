package com.ku.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.ku.dao.SupportRequestDao;
import com.ku.model.SupportRequest;

@Repository("supportRequestDao")
public class SupportRequestDaoHibernate extends
		GenericDaoHibernate<SupportRequest, Long> implements SupportRequestDao {

	public SupportRequestDaoHibernate() {
		super(SupportRequest.class);
		// TODO Auto-generated constructor stub
	}

}
