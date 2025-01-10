/**
 * 
 */
package com.aiken.pos.admin.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.exception.ResourceNotFoundException;
import com.aiken.pos.admin.model.Dashboard;
import com.aiken.pos.admin.model.DashboardConfig;
import com.aiken.pos.admin.model.DashboardWidgetPerm;
import com.aiken.pos.admin.repository.DashboardRepository;

/**
 * @author Nandana Basnayake
 *
 * @created_On Oct 7, 2022
 */
@Service
public class DashboardService {
	
	@Autowired
	private DashboardRepository dashboardRepository;
	
	private Logger logger = LoggerFactory.getLogger(DashboardService.class);
	
	@Transactional
	public void sapmeDates(Integer days) throws GenericException {
		// No response required


		List<LocalDate> listOfDates = dateRange(days);

		System.out.println(listOfDates.size());		
		logger.info("DateArraySize: " + listOfDates.size());
	}
	
	
	private List<LocalDate> dateRange(Integer days){
		LocalDate  endDate = LocalDate.now().plusDays(1);
		LocalDate startDate = endDate.plusDays(-days);
		long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);

		List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
										 	.limit(numOfDays)
										 	.collect(Collectors.toList());
		
		logger.info("Define Date Range:> " + "Start Date:" + listOfDates.get(0) + " | End Date:" + listOfDates.get(days-1) + " | #Days:" + listOfDates.size());
		
		return listOfDates;
	}
	
	
	@Transactional
	public Dashboard findUserStatus()throws GenericException {
		
		try {
			Dashboard dashboard = dashboardRepository.findUserCount();
			if (dashboard!=null) {
				logger.info("Size: " +dashboard);
				return dashboard;
			} else {
				logger.warn("User Data Not Found");
				throw new ResourceNotFoundException("User Data Not Found");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error("Error:User Data Not Found" + e.getMessage());
			throw new ResourceNotFoundException("User Data Not Found");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding User details");
		}


	}
	
	@Transactional
	public Dashboard findDailyCount()throws GenericException {
		
		try {
			Dashboard dashboard = dashboardRepository.findDailyCount();
			if (dashboard!=null) {
				logger.info("Daily Count Avaialble: " +dashboard);
				return dashboard;
			} else {
				logger.warn("Daily Count Not Found");
				throw new ResourceNotFoundException("Daily Count Data Not Found");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error("Error:User Daily Count Not Found" + e.getMessage());
			throw new ResourceNotFoundException("Daily Count Data Not Found");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding Daily Count details");
		}


	}
	
	
	
	@Transactional
	public Dashboard findEventCountForLast7D()throws GenericException {
		
		try {
			Dashboard dashboard = dashboardRepository.findEventCountForLastDays_7();
			if (dashboard!=null) {
				logger.info("dashboard: " +dashboard);
				return dashboard;
			} else {
				logger.warn("Event Data Not Found");
				throw new ResourceNotFoundException("Event Data Not Found for last 7 days");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error("Error: Data Not Found for last 7 days " + e.getMessage());
			throw new ResourceNotFoundException("Event Data Not Found for last 7 days");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding Events");
		}

	}
	
	
	
	@Transactional
	public Dashboard FindOneClickCountForLast7D()throws GenericException {
		
		try {
			Dashboard dashboard = dashboardRepository.findOneClickForLastDays_7();
			if (dashboard!=null) {
				logger.info("Size: " +dashboard);
				return dashboard;
			} else {
				logger.warn("One-click Data Not Found");
				throw new ResourceNotFoundException("One-click Data Not Found");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error("One-click Data Not Found" + e.getMessage());
			throw new ResourceNotFoundException("One-click Data Not Found");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding One-click details");
		}


	}
	
	@Transactional
	public Dashboard FindBankOneClickCountForLast30D()throws GenericException {
		
		try {
			Dashboard dashboard = dashboardRepository.findBankOneClickForLastDays_30();
			if (dashboard!=null) {
				logger.info("Size: " +dashboard);
				return dashboard;
			} else {
				logger.warn("One-click Data Not Found");
				throw new ResourceNotFoundException("One-click Data Not Found");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error("One-click Data Not Found" + e.getMessage());
			throw new ResourceNotFoundException("One-click Data Not Found");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding One-click details");
		}


	}
	
	@Transactional
	public Dashboard findBankEventCountForLast30D()throws GenericException {
		
		try {
			Dashboard dashboard = dashboardRepository.findBankEventsForLastDays_30();
			if (dashboard!=null) {
				logger.info("dashboard: " +dashboard);
				return dashboard;
			} else {
				logger.warn("Event Data Not Found");
				throw new ResourceNotFoundException("Event Data Not Found for last 30 days");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error("Error: Data Not Found for last 7 days " + e.getMessage());
			throw new ResourceNotFoundException("Event Data Not Found for last 30 days");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding Events");
		}

	}
	
	@Transactional
	public Dashboard findBankDeviceRegForLast7D()throws GenericException {
		
		try {
			Dashboard dashboard = dashboardRepository.findNewRegDevicesForLastDays_7();
			if (dashboard!=null) {
				logger.info("dashboard: " +dashboard);
				return dashboard;
			} else {
				logger.warn("Event Data Not Found");
				throw new ResourceNotFoundException("Device Reg Data Not Found for last 7 days");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error("Error: Device Reg Data Not Found for last 7 days " + e.getMessage());
			throw new ResourceNotFoundException("Device Reg Data Not Found for last 7 days");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding Device Reg");
		}

	}
	
	@Transactional
	public DashboardConfig findDashboradPermission()throws GenericException {
		
		try {
			DashboardConfig dashboardPerms = dashboardRepository.findAllPermissions();
			if (dashboardPerms!=null) {
				logger.info("DB User Permission Available");
				return dashboardPerms;
			} else {
				logger.warn("User Permission Not Found");
				throw new ResourceNotFoundException("User Permission Not Found");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error("Error:User Permission Not Found" + e.getMessage());
			throw new ResourceNotFoundException("User Permission Not Found");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding User Permission");
		}


	}
	
	@Transactional
	public Integer updateDashboradPermission(DashboardConfig dbconf)throws GenericException {
		
		try {
			return  dashboardRepository.updateWidgetPermission(dbconf);
			
		} catch (EmptyResultDataAccessException e) {
			logger.error("Error:Update Permission Not Found" + e.getMessage());
			throw new ResourceNotFoundException("Update Permission Not Found");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding Update Permission");
		}
	}
	public DashboardWidgetPerm findDashboradPermissionByUserRole(String userRole)throws GenericException {
		
		try {
			DashboardWidgetPerm dashboardPerms = dashboardRepository.findWidgetPermissionByUserRole(userRole);
			if (dashboardPerms!=null) {
				logger.info("DB User Permission Available");
				return dashboardPerms;
			} else {
				logger.warn("User Permission Not Found");
				throw new ResourceNotFoundException("User Permission Not Found");
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error("Error:User Permission Not Found" + e.getMessage());
			throw new ResourceNotFoundException("User Permission Not Found");
		} catch (Exception e) {
			logger.error("Error: " + e);
			throw new GenericException("Error Finding User Permission");
		}

	}

}
