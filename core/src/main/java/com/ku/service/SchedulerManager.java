package com.ku.service;

import java.util.Map;

import com.ku.common.KUException;

/**
 * Web Service interface for manage schedules.
 * 
 * @author mathi
 */

public interface SchedulerManager {

    /**
     * schedule the job
     * 
     * @param schedulerDetails
     * @throws KUException
     */
    Map<String, Object> scheduleJob(Map<String, Object> schedulerDetails)
            throws KUException;

    /**
	 * start scheduler
	 * 
	 * @return
	 * 
	 * @throws KUException
	 */
	Map<String, Object> startScheduler() throws KUException;
	
	/**
	 * shutdown scheduler
	 * 
	 * @return
	 * 
	 * @throws KUException
	 */
	Map<String, Object> shutdownScheduler() throws KUException;

    /**
     * reschedule the job
     * 
     * @param schedulerDetails
     * @throws KUException
     */
    Map<String, Object> rescheduleJob(Map<String, Object> schedulerDetails)
            throws KUException;

    /**
     * unschedule job
     * 
     * @param schedulerDetails
     * @throws KUException
     */
    Map<String, Object> unscheduleJob(Map<String, Object> schedulerDetails)
            throws KUException;

    /**
     * delete job from schedule
     * 
     * @param schedulerDetails
     * @throws KUException
     */
    Map<String, Object> deleteJob(Map<String, Object> schedulerDetails)
            throws KUException;
}
