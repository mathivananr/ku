package com.ku.job;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ku.common.KUException;
import com.ku.service.EmailOfferProcessor;

/**
 * job to schedule the payoom offers processing 
 * 
 * @author mathi
 * 
 */
@DisallowConcurrentExecution
public class ProcessPayoomOffers implements Job {
	private Log log = LogFactory.getLog(com.ku.job.ProcessPayoomOffers.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.info("payoom email offer processor");
		ApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(ContextLoaderListener
						.getCurrentWebApplicationContext().getServletContext());
		EmailOfferProcessor emailOfferProcessor = (EmailOfferProcessor) springContext
				.getBean("emailOfferProcessor");
		try {
			emailOfferProcessor.processPayoomOffers();
		} catch (KUException | ParseException e) {
			log.error(e.getMessage(), e);
		}

	}

}
