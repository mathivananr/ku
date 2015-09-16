package com.ku.service;

import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.ku.common.KUException;

/**
 * Web Service interface for manage schedules.
 * 
 * @author mathi
 */
@WebService
@Path("/scheduler")
public interface SchedulerService {

	/**
	 * schedule the job
	 * 
	 * @param schedulerDetails
	 * @throws KUException
	 */
	@POST
	@Path("/scheduleJob")
	@Produces("application/json")
	Map<String, Object> scheduleJob(Map<String, Object> schedulerDetails)
			throws KUException;

	
	/**
	 * start scheduler
	 * 
	 * @return
	 * 
	 * @throws KUException
	 */
	@POST
	@Path("/startScheduler")
	@Produces("application/json")
	Map<String, Object> startScheduler() throws KUException;
	
	/**
	 * shutdown scheduler
	 * 
	 * @return
	 * 
	 * @throws KUException
	 */
	@POST
	@Path("/shutdownScheduler")
	@Produces("application/json")
	Map<String, Object> shutdownScheduler() throws KUException;
	
	/**
	 * reschedule the job
	 * 
	 * @param schedulerDetails
	 * @throws KUException
	 */
	@POST
	@Path("/rescheduleJob")
	@Produces("application/json")
	Map<String, Object> rescheduleJob(Map<String, Object> schedulerDetails)
			throws KUException;

	/**
	 * unschedule job
	 * 
	 * @param schedulerDetails
	 * @throws KUException
	 */
	@POST
	@Path("/unscheduleJob")
	@Produces("application/json")
	Map<String, Object> unscheduleJob(Map<String, Object> schedulerDetails)
			throws KUException;

	/**
	 * delete job from schedule
	 * 
	 * @param schedulerDetails
	 * @throws KUException
	 */
	@POST
	@Path("/deleteJob")
	@Produces("application/json")
	Map<String, Object> deleteJob(Map<String, Object> schedulerDetails)
			throws KUException;
}
