package com.ku.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ku.Constants;
import com.ku.common.KUException;
import com.ku.service.SchedulerManager;
import com.ku.service.SchedulerService;
import com.ku.util.StringUtil;

/**
 * Implementation of SchedulerService interface.
 * 
 * @author mathi
 */
@Service("schedulerManager")
public class SchedulerManagerImpl implements SchedulerManager, SchedulerService {

	private final Logger log = (Logger) LoggerFactory
			.getLogger(com.ku.service.impl.SchedulerManagerImpl.class);

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> scheduleJob(Map<String, Object> schedulerDetails)
			throws KUException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String groupName = Constants.DEFAULT_SCHEDULER_GROUP;
		try {
			if (StringUtil.isEmptyString(schedulerDetails.get("groupName"))) {
				schedulerDetails.put("groupName", groupName);
			}
			if (validateJobDetails(schedulerDetails)) {
				// schedule it
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
				scheduler.start();
				scheduler.scheduleJob(getNewSchedulerJob(schedulerDetails),
						getNewSchedulerTrigger(schedulerDetails));
				responseMap.put("message", schedulerDetails.get("jobName")
						+ " scheduled successfully");
			}
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new KUException("Problem in scheduling the job" + " "
					+ schedulerDetails.get("jobName"));
		}
		return responseMap;
	}

	/**
	 * validate the empty parameters
	 * 
	 * @param schedulerDetails
	 * @return
	 * @throws KUException
	 */
	public boolean validateJobDetails(Map<String, Object> schedulerDetails)
			throws KUException {
		StringBuffer errors = new StringBuffer();
		if (StringUtil.isEmptyString(schedulerDetails.get("jobName"))) {
			errors.append("Job Name");
		}
		if (StringUtil.isEmptyString(schedulerDetails.get("jobClass"))) {
			errors.append(", Job Class");
		}
		if (StringUtil.isEmptyString(schedulerDetails.get("triggerName"))) {
			errors.append(", Trigger Name");
		}

		if (StringUtil.isEmptyString(errors.toString())) {
			return true;
		} else {
			throw new KUException(errors.toString() + " should not be empty");
		}
	}

	/**
	 * get the scheduler job
	 * 
	 * @param schedulerDetails
	 * @return
	 * @throws KUException
	 */
	@SuppressWarnings("unchecked")
	public JobDetail getNewSchedulerJob(Map<String, Object> schedulerDetails)
			throws KUException {
		try {
			Class<?> jobClass = Class.forName(schedulerDetails.get("jobClass")
					.toString());
			JobDetail job = JobBuilder
					.newJob((Class<? extends Job>) jobClass)
					.withIdentity(schedulerDetails.get("jobName").toString(),
							schedulerDetails.get("groupName").toString())
					.build();
			return job;
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new KUException("Requested Job Class "
					+ schedulerDetails.get("jobClass") + " not found");
		}
	}

	/**
	 * get the trigger to schedule the job
	 * 
	 * @param schedulerDetails
	 * @return
	 * @throws KUException
	 */
	public Trigger getNewSchedulerTrigger(Map<String, Object> schedulerDetails)
			throws KUException {
		if (!StringUtil.isEmptyString(schedulerDetails.get("triggerType"))
				&& schedulerDetails.get("triggerType").toString()
						.equalsIgnoreCase("cron")) {
			return getCronTrigger(schedulerDetails);
		} else {
			return getTimeTrigger(schedulerDetails);
		}
	}

	/**
	 * get the trigger for cron expression
	 * 
	 * @param schedulerDetails
	 * @return
	 * @throws KUException
	 */
	public Trigger getCronTrigger(Map<String, Object> schedulerDetails)
			throws KUException {
		if (!StringUtil.isEmptyString(schedulerDetails.get("cronExpression"))) {
			// configure the scheduler time
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(
							schedulerDetails.get("triggerName").toString(),
							schedulerDetails.get("groupName").toString())
					.withSchedule(
							CronScheduleBuilder.cronSchedule(schedulerDetails
									.get("cronExpression").toString())).build();
			return trigger;

		} else {
			throw new KUException("cron expression should not be empty");
		}
	}

	/**
	 * get the trigger for timing interval
	 * 
	 * @param schedulerDetails
	 * @return
	 * @throws KUException
	 */
	public Trigger getTimeTrigger(Map<String, Object> schedulerDetails)
			throws KUException {
		if (!StringUtil.isEmptyString(schedulerDetails.get("interval"))) {
			// configure the scheduler time
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(
							schedulerDetails.get("triggerName").toString(),
							schedulerDetails.get("groupName").toString())
					.withSchedule(
							SimpleScheduleBuilder
									.simpleSchedule()
									.withIntervalInMilliseconds(
											Long.valueOf(schedulerDetails.get(
													"interval").toString()))
									.repeatForever()).build();
			return trigger;
		} else {
			throw new KUException("Interval should not be empty");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> startScheduler() throws KUException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			responseMap.put("message", "Scheduler Started");
			return responseMap;
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new KUException("Problem in starting the scheduler");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> shutdownScheduler() throws KUException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.shutdown(true);
			responseMap.put("message", "Scheduler Stopped");
			return responseMap;
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new KUException("Problem in shutting down the scheduler");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> rescheduleJob(
			Map<String, Object> schedulerDetails) throws KUException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Trigger newTrigger = null;
		String groupName = Constants.DEFAULT_SCHEDULER_GROUP;
		try {
			if (!StringUtil.isEmptyString(schedulerDetails.get("groupName"))) {
				groupName = schedulerDetails.get("groupName").toString();
			}
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			if (!StringUtil.isEmptyString(schedulerDetails.get("triggerName"))) {
				// retrieve the trigger
				Trigger oldTrigger = scheduler.getTrigger(new TriggerKey(
						schedulerDetails.get("triggerName").toString(),
						groupName));
				newTrigger = getRescheduledTrigger(oldTrigger, schedulerDetails);
				scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
				responseMap.put("message", "Job rescheduled successfully");
				return responseMap;
			} else {
				throw new KUException("Trigger Name should not be empty");
			}
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new KUException("Problem in rescheduling the job");
		}
	}

	/**
	 * get rescheduled trigger
	 * 
	 * @param oldTrigger
	 * @param schedulerDetails
	 * @return
	 * @throws KUException
	 */
	public Trigger getRescheduledTrigger(Trigger oldTrigger,
			Map<String, Object> schedulerDetails) throws KUException {
		TriggerBuilder triggerBuilder = oldTrigger.getTriggerBuilder();

		if (!StringUtil.isEmptyString(schedulerDetails.get("triggerType"))
				&& schedulerDetails.get("triggerType").toString()
						.equalsIgnoreCase("cron")) {
			return getCronTrigger(triggerBuilder, schedulerDetails);
		} else {
			return getTimeTrigger(triggerBuilder, schedulerDetails);
		}
	}

	/**
	 * get cron trigger
	 * 
	 * @param triggerBuilder
	 * @param schedulerDetails
	 * @return
	 * @throws KUException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Trigger getCronTrigger(TriggerBuilder triggerBuilder,
			Map<String, Object> schedulerDetails) throws KUException {
		if (!StringUtil.isEmptyString(schedulerDetails.get("cronExpression"))) {
			// configure the scheduler time
			Trigger trigger = triggerBuilder.withSchedule(
					CronScheduleBuilder.cronSchedule(schedulerDetails.get(
							"cronExpression").toString())).build();
			return trigger;

		} else {
			throw new KUException("cron expression should not be empty");
		}
	}

	/**
	 * get time interval trigger
	 * 
	 * @param triggerBuilder
	 * @param schedulerDetails
	 * @return
	 * @throws KUException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Trigger getTimeTrigger(TriggerBuilder triggerBuilder,
			Map<String, Object> schedulerDetails) throws KUException {
		if (!StringUtil.isEmptyString(schedulerDetails.get("interval"))) {
			Trigger trigger = triggerBuilder.withSchedule(
					SimpleScheduleBuilder
							.simpleSchedule()
							.withIntervalInMilliseconds(
									Long.valueOf(schedulerDetails.get(
											"interval").toString()))
							.repeatForever()).build();
			return trigger;
		} else {
			throw new KUException("Interval should not be empty");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> unscheduleJob(
			Map<String, Object> schedulerDetails) throws KUException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String groupName = Constants.DEFAULT_SCHEDULER_GROUP;
		try {
			if (!StringUtil.isEmptyString(schedulerDetails.get("groupName"))) {
				groupName = schedulerDetails.get("groupName").toString();
			}
			if (!StringUtil.isEmptyString(schedulerDetails.get("triggerName"))) {
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
				// Unschedule a particular trigger from the job (a job may have
				// more than one trigger)
				scheduler.unscheduleJob(new TriggerKey(schedulerDetails.get(
						"triggerName").toString(), groupName));
				responseMap.put("message", "Job unscheduled successfully");
				return responseMap;
			} else {
				throw new KUException("Trigger Name should not be empty");
			}
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new KUException("Problem in unscheduling job");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> deleteJob(Map<String, Object> schedulerDetails)
			throws KUException {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String groupName = Constants.DEFAULT_SCHEDULER_GROUP;
		try {
			if (!StringUtil.isEmptyString(schedulerDetails.get("groupName"))) {
				groupName = schedulerDetails.get("groupName").toString();
			}
			if (!StringUtil.isEmptyString(schedulerDetails.get("jobName"))) {
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
				// Schedule the job with the trigger
				scheduler.deleteJob(new JobKey(schedulerDetails.get("jobName")
						.toString(), groupName));
				responseMap.put("message", "Job deleted successfully");
				return responseMap;
			} else {
				throw new KUException("Job Name should not be empty");
			}
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			throw new KUException(" Problem in deleting job");
		}
	}
}
