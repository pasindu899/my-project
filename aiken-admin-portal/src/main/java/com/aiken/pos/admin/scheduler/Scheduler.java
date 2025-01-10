package com.aiken.pos.admin.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.aiken.common.util.validation.DateUtil;
import com.aiken.pos.admin.service.ActivityHistoryService;
import com.aiken.pos.admin.service.EventTypeService;

/**
 * Task Schedular
 *
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2021-09-23
 */

@Component
public class Scheduler {
	private Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	@Autowired
    private ActivityHistoryService activityHistoryService;
	
	// Scheduled to run 11:30 PM everyday
	@Scheduled(cron = "0 30 23 * * ?", zone = "Asia/Colombo")
	public void cronJobSch() {
		logger.info("######## Scheduler Started ##########");
		logger.info("Scheduler run time:" + DateUtil.getCurrentTime());
		activityHistoryService.deleteAll();
		logger.info("Clear Activity History");
		logger.info("######## Scheduler Ended ##########");
	}

}
