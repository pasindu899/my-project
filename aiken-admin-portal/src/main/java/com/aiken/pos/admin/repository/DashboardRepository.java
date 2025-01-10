/**
 * 
 */
package com.aiken.pos.admin.repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.aiken.pos.admin.constant.EventStatus;
import com.aiken.pos.admin.constant.UserConstants;
import com.aiken.pos.admin.model.BankEventCount;
import com.aiken.pos.admin.model.Dashboard;
import com.aiken.pos.admin.model.DashboardConfig;
import com.aiken.pos.admin.model.DashboardWidgetPerm;
import com.aiken.pos.admin.model.Device;
import com.aiken.pos.admin.model.Event;
import com.aiken.pos.admin.model.Merchant;
import com.aiken.pos.admin.model.SysUser;
import com.aiken.pos.admin.service.BankTypeService;

/**
 * @author Nandana Basnayake
 *
 * @created_On Oct 7, 2022
 */

@Repository
public class DashboardRepository implements GenericRepository<Dashboard, Integer> {
	
	private Logger logger = LoggerFactory.getLogger(DashboardRepository.class);
	
	private final String FIND_ACTIVE_AND_ONLINE_USER_COUNT = "SELECT count(*) as all_users,(select count(*) FROM sys_user "
			+ " WHERE last_login IS NOT NULL and last_logout IS NULL ) as active_users  FROM sys_user WHERE active='true'";
	
	
	private final String FIND_EVENTS_COUNT_FOR_LAST_DAYS_7 = "SELECT last_update::date as event_date,  count(*) as event_count FROM event" 
			+ " WHERE status='DONE' AND TO_TIMESTAMP(last_update,'YYYY/MM/DD HH24:MI:SS') >= date_trunc('DAY',NOW()) - INTERVAL '7 DAYS'"
			+ " GROUP BY last_update::date ORDER BY last_update::date ASC";
	
	private final String FIND_ONE_CLICK_COUNT_FOR_LAST_DAYS_7 = "SELECT last_update::date as event_date,  count(*) as one_click_count FROM device "
			+ " WHERE status='ONE_CLICK_UPDATED' AND TO_TIMESTAMP(last_update,'YYYY/MM/DD HH24:MI:SS') >= date_trunc('DAY',NOW()) - INTERVAL '7 DAYS'"
			+ " GROUP BY last_update::date"
			+ " ORDER BY last_update::date ASC";

//	private final String FIND_BANK_WISE_ONE_CLICK_COUNT_FOR_LAST_DAYS_30 = "SELECT last_update::date as event_date,  count(*) as one_click_count,bank_code FROM device"
//			+ " WHERE status='ONE_CLICK_UPDATED' AND TO_TIMESTAMP(last_update,'YYYY/MM/DD HH24:MI:SS') >= date_trunc('DAY',NOW()) - INTERVAL '30 DAYS'" 
//			+ " GROUP BY last_update::date,bank_code ORDER BY last_update::date ASC";
	
	private final String FIND_BANK_WISE_ONE_CLICK_COUNT_FOR_LAST_DAYS_30 = "SELECT count(*) as one_click_count,bank_code FROM device"
			+ " WHERE status='ONE_CLICK_UPDATED' AND TO_TIMESTAMP(last_update,'YYYY/MM/DD HH24:MI:SS') >= date_trunc('DAY',NOW()) - INTERVAL '30 DAYS'"
			+ "	GROUP BY bank_code";

//	private final String FIND_BANK_WISE_EVENT_COUNT_FOR_LAST_DAYS_30 ="SELECT e.last_update::date as event_date, d.bank_code as bank_code,count(*) as event_count FROM event e"
//			+ " INNER JOIN device d ON e.serial_no=d.serial_no"
//			+ " WHERE e.status='DONE' AND TO_TIMESTAMP(e.last_update,'YYYY/MM/DD HH24:MI:SS') >= date_trunc('DAY',NOW()) - INTERVAL '30 DAYS'"
//			+ " GROUP BY e.last_update::date,d.bank_code ORDER BY e.last_update::date ASC";
	
	private final String FIND_BANK_WISE_EVENT_COUNT_FOR_LAST_DAYS_30 ="SELECT  d.bank_code as bank_code,count(*) as event_count FROM event e"
			+ "	INNER JOIN device d ON e.serial_no=d.serial_no"
			+ "	WHERE e.status='DONE' AND TO_TIMESTAMP(e.last_update,'YYYY/MM/DD HH24:MI:SS') >= date_trunc('DAY',NOW()) - INTERVAL '30 DAYS'"
			+ "	GROUP BY d.bank_code";
	
	
	private final String FIND_BANK_WISE_DEVICE_REGISTRATION_FOR_LAST_DAYS_7 ="SELECT bank_code, count(*) as event_count FROM device"
			+ " WHERE TO_TIMESTAMP(added_date,'YYYY/MM/DD HH24:MI:SS') >= date_trunc('DAY',NOW()) - INTERVAL '7 DAYS'"
			+ " GROUP BY bank_code ORDER BY bank_code ASC";
	
	private final String FIND_DAITLY_EVENT_COUNT ="SELECT count(*) as event_count FROM event"
			+ "	WHERE status='DONE' AND TO_TIMESTAMP(last_update,'YYYY/MM/DD HH24:MI:SS') >= date_trunc('DAY',NOW()) - INTERVAL '0 DAYS'";
				
	private final String FIND_DAITLY_ONECLICK_COUNT ="SELECT count(*) as one_click_count FROM device"
			+ "	WHERE status='ONE_CLICK_UPDATED' AND TO_TIMESTAMP(last_update,'YYYY/MM/DD HH24:MI:SS') >= date_trunc('DAY',NOW()) - INTERVAL '0 DAYS'";
	
	private final String FIND_DEVICE_COUNT ="SELECT count(*) as device_count FROM public.device";
	
	private final String FIND_DASHBOARD_WIDGET_VIEW_PERMISSION ="SELECT * FROM public.dashboard_wdgt_perms";
	
	private final String FIND_WIDGET_PERMISSION_BY_USER_ROLE ="SELECT * FROM public.dashboard_wdgt_perms  WHERE user_role=:user_role";
	
	private final String UPDATE_DASHBOARD_WIDGET_VIEW_PERMISSION ="UPDATE public.dashboard_config"
			+ "	SET config_id=:config_id, roleadmin1=:roleadmin1, roleadmin2=:roleadmin2, roleadmin3=:roleadmin3, roleadmin4=:roleadmin4, roleadmin5=:roleadmin5,"
			+ " rolemangr1=:rolemangr1, rolemangr2=:rolemangr2, rolemangr3=:rolemangr3, rolemangr4=:rolemangr4, rolemangr5=:rolemangr5,"
			+ " rolepos1=:rolepos1, rolepos2=:rolepos2, rolepos3=:rolepos3, rolepos4=:rolepos4, rolepos5=:rolepos5, "
			+ "roleuser1=:roleuser1, roleuser2=:roleuser2, roleuser3=:roleuser3, roleuser4=:roleuser4, roleuser5=:roleuser5, "
			+ "rolebankuser1=:rolebankuser1, rolebankuser2=:rolebankuser2, rolebankuser3=:rolebankuser3, rolebankuser4=:rolebankuser4, rolebankuser5=:rolebankuser5";
			
	
	private final String UPDATE_DASHBOARD_WIDGET_PERMISSION = "UPDATE public.dashboard_wdgt_perms"
			+ "	SET enablewdg1=:enablewdg1, enablewdg2=:enablewdg2, enablewdg3=:enablewdg3, "
			+ " enablewdg4=:enablewdg4, enablewdg5=:enablewdg5"
			+ "	WHERE user_role=:user_role";
			
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	@Autowired
	private BankTypeService bankTyples;
	
	
	@Override
	public Integer insert(Dashboard model) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> insertAll(List<Dashboard> models) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(Dashboard model) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> updateAll(List<Dashboard> models) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteById(Integer id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Dashboard entity) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> deleteAll(List<Dashboard> models) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * public Optional<Device> findById(Integer deviceId) throws
	 * IllegalArgumentException {
	 * 
	 * logger.info("Device ID: " + deviceId); // find device 
	 * return  Optional.of((Device) template.queryForObject(FIND_DEVICE_BY_DEVICE_ID,
	 */

	public Dashboard findUserCount() {

		logger.info("################## START find-User-Count ##################");
		try {
			return template.queryForObject(FIND_ACTIVE_AND_ONLINE_USER_COUNT,
					new MapSqlParameterSource(), (rs, rowNum) -> {

				Dashboard dashboard = new Dashboard();
				
				dashboard.setActiveUserCount(rs.getInt("active_users"));
				dashboard.setAllUserCount(rs.getInt("all_users"));
				
				return dashboard;
			});
		}catch (DataAccessException ex) {
			logger.error("Error: " + ex.getMessage());
			return null;
		}
		
	}
	
	
	public Dashboard findEventCountForLastDays_7() {
		logger.info("################## START find-Event-Count-For-Last-7Days ##################");
		
		Dashboard dashboard = new Dashboard();
		List<BankEventCount>  event7D = new ArrayList<>();
		List<BankEventCount>  eventCount7D = new ArrayList<>();
		
		
		try {
			event7D = template.query(FIND_EVENTS_COUNT_FOR_LAST_DAYS_7, (rs, rowNum) -> {

				BankEventCount eventData7D = new BankEventCount();

				eventData7D.setEvetCount(rs.getInt("event_count"));
				eventData7D.setEvetDate(rs.getString("event_date"));

				return eventData7D;
			});

			List<LocalDate> dateRange =  getAllDates(7);
			
			for (LocalDate dt : dateRange) {
		          BankEventCount eventData = event7D.stream().filter(evnt -> dt.toString().equals(evnt.getEvetDate().toString())).findAny().orElse(null);
		          if (eventData==null) {
		        	  eventData = new BankEventCount();
		        	  eventData.setEvetDate(dt.toString());
		        	  eventData.setEvetCount(0);
		          }
		          eventCount7D.add(eventData);
		      }

			dashboard.setEventCount((List<BankEventCount>) eventCount7D);
			return dashboard;
		}catch (DataAccessException ex) {
			logger.error("Error: " + ex.getMessage());
			return null;
		}

	}
	
	public Dashboard findOneClickForLastDays_7() {
		
		logger.info("################## START find-One-Click-For-Last-7Days ##################");
		
		Dashboard dashboard = new Dashboard();
		try {
			List<BankEventCount>  oneClick7D = new ArrayList<>();
			List<BankEventCount>  oneClickCount7D = new ArrayList<>();
			oneClick7D = template.query(FIND_ONE_CLICK_COUNT_FOR_LAST_DAYS_7, (rs, rowNum) -> {

				BankEventCount oneClickData7D = new BankEventCount();
				
				oneClickData7D.setEvetCount(rs.getInt("one_click_count"));
				oneClickData7D.setEvetDate(rs.getString("event_date"));

				return oneClickData7D;
			});
			
			List<LocalDate> dateRange =  getAllDates(7);
			
			for (LocalDate dt : dateRange) {
		          BankEventCount eventData = oneClick7D.stream().filter(evnt -> dt.toString().equals(evnt.getEvetDate().toString())).findAny().orElse(null);
		          if (eventData==null) {
		        	  eventData = new BankEventCount();
		        	  eventData.setEvetDate(dt.toString());
		        	  eventData.setEvetCount(0);
		          }
		          oneClickCount7D.add(eventData);
		      }

			dashboard.setOneClickCount((List<BankEventCount>) oneClickCount7D);
			return dashboard;
		}catch (DataAccessException ex) {
			logger.error("Error: " + ex.getMessage());
			return null;
		}
		
		
	}
	
	public Dashboard findBankOneClickForLastDays_30() {
		
		
		logger.info("################## START find-Bank-One-Click-For-Last-30Days ##################");
		Dashboard dashboard = new Dashboard();
		try {
			List<BankEventCount>  bankOneClick30 = new ArrayList<>();
			List<BankEventCount>  bankOneClickCount30 = new ArrayList<>();
			bankOneClick30 = template.query(FIND_BANK_WISE_ONE_CLICK_COUNT_FOR_LAST_DAYS_30, (rs, rowNum) -> {

				BankEventCount oneClickData30 = new BankEventCount();
				
				oneClickData30.setBankCode(rs.getString("bank_code"));
				oneClickData30.setEvetCount(rs.getInt("one_click_count"));
				//oneClickData30.setEvetDate(rs.getString("event_date"));

				return oneClickData30;
			});
			
			
					
			for (String bankCode : bankTyples.getAllBankList()) {
		          BankEventCount eventData = bankOneClick30.stream().filter(evnt -> bankCode.toString().equalsIgnoreCase(evnt.getBankCode().toString())).findAny().orElse(null);
		          if (eventData==null) {
		        	  eventData = new BankEventCount();
		        	  eventData.setBankCode(bankCode.toString());
		        	  eventData.setEvetCount(0);
		          }
		          bankOneClickCount30.add(eventData);
		      }
			
			dashboard.setBankOneClickCount((List<BankEventCount>) bankOneClickCount30);
			return dashboard;
		}catch (DataAccessException ex) {
			logger.error("Error: " + ex.getMessage());
			return null;
		}
	}
	
	public Dashboard findBankEventsForLastDays_30() {
		logger.info("################## START find-Bank-Events-For-Last-30Days ##################");
		Dashboard dashboard = new Dashboard();
		List<BankEventCount>  bankevent30 = new ArrayList<>();
		List<BankEventCount>  bankEventCount30 = new ArrayList<>();
		
		try {
			bankevent30 = template.query(FIND_BANK_WISE_EVENT_COUNT_FOR_LAST_DAYS_30, (rs, rowNum) -> {

				BankEventCount eventData30 = new BankEventCount();
				
				eventData30.setBankCode(rs.getString("bank_code"));
				eventData30.setEvetCount(rs.getInt("event_count"));
				
				return eventData30;
			});
			
			for (String bankCode : bankTyples.getAllBankList()) {
		          BankEventCount eventData = bankevent30.stream().filter(evnt -> bankCode.toString().equalsIgnoreCase(evnt.getBankCode().toString())).findAny().orElse(null);
		          if (eventData==null) {
		        	  eventData = new BankEventCount();
		        	  eventData.setBankCode(bankCode.toString());
		        	  eventData.setEvetCount(0);
		          }
		          bankEventCount30.add(eventData);
		      }
			
			
			dashboard.setBankEventCount((List<BankEventCount>) bankEventCount30);
			return dashboard;
			
		}catch (DataAccessException ex) {
			logger.error("Error: " + ex.getMessage());
			return null;
		}

	}
	
	public Dashboard findNewRegDevicesForLastDays_7() {
		logger.info("################## START find-New-Reg-Devices-For-Last-7Days ##################");
		Dashboard dashboard = new Dashboard();
		List<BankEventCount>  newRegDeviceCountD7 = new ArrayList<>();
		try {
			newRegDeviceCountD7 = template.query(FIND_BANK_WISE_DEVICE_REGISTRATION_FOR_LAST_DAYS_7, (rs, rowNum) -> {

				BankEventCount deviceCountD7 = new BankEventCount();
				
				deviceCountD7.setBankCode(rs.getString("bank_code"));
				deviceCountD7.setEvetCount(rs.getInt("event_count"));

				return deviceCountD7;
			});
			
			dashboard.setNewRegDeviceCount((List<BankEventCount>) newRegDeviceCountD7);
			return dashboard;
		}catch (DataAccessException ex) {
			logger.error("Error: " + ex.getMessage());
			return null;
		}
	}
	
	public Dashboard findDailyCount() {

		logger.info("################## START find-Daily count-Events/Oneclicks ##################");
		try {
			Dashboard dailyCount = new Dashboard();
			template.queryForObject(FIND_DAITLY_EVENT_COUNT,
					new MapSqlParameterSource(), (rs, rowNum) -> {

				dailyCount.setEventDailyCount(rs.getInt("event_count"));
				
				return dailyCount;
			});
			template.queryForObject(FIND_DAITLY_ONECLICK_COUNT,
					new MapSqlParameterSource(), (rs, rowNum) -> {

						dailyCount.setOneClickDailyCount(rs.getInt("one_click_count"));
				
				return dailyCount;
			});
			template.queryForObject(FIND_DEVICE_COUNT,
					new MapSqlParameterSource(), (rs, rowNum) -> {

						dailyCount.setDeviceCount(rs.getInt("device_count"));
				
				return dailyCount;
			});
			return dailyCount;
			
		}catch (DataAccessException ex) {
			logger.error("Error: " + ex.getMessage());
			return null;
		}
		
	}
	
	public DashboardConfig findAllPermissions() {

		logger.info("################## START find-User-Dashboard-Permissions ##################");
		try {
			List<DashboardWidgetPerm> dbWidget = new ArrayList<>();
			DashboardConfig dashConfig = new DashboardConfig();
			dbWidget = template.query(FIND_DASHBOARD_WIDGET_VIEW_PERMISSION,
					new MapSqlParameterSource(), (rs, rowNum) -> {
					
						//dashConfig.setRoleAdminWdg1(rs.getBoolean("roleAdmin1"));
						DashboardWidgetPerm dbperm = new DashboardWidgetPerm();
						
						dbperm.setUserRole(rs.getString("user_role"));
						dbperm.setEnableWdg1(rs.getBoolean("enablewdg1"));
						dbperm.setEnableWdg2(rs.getBoolean("enablewdg2"));
						dbperm.setEnableWdg3(rs.getBoolean("enablewdg3"));
						dbperm.setEnableWdg4(rs.getBoolean("enablewdg4"));
						dbperm.setEnableWdg5(rs.getBoolean("enablewdg5"));

				return dbperm;
			});
			
			if (dbWidget!=null) {
				
				dbWidget.forEach(pm -> {
					logger.info("Permission check : Role:" + pm.getUserRole() );
							if (pm.getUserRole().contains(UserConstants.ROLE_ADMIN)) {
								 dashConfig.setRoleAdminWdg1(pm.isEnableWdg1());
								 dashConfig.setRoleAdminWdg2(pm.isEnableWdg2());
								 dashConfig.setRoleAdminWdg3(pm.isEnableWdg3());
								 dashConfig.setRoleAdminWdg4(pm.isEnableWdg4());
								 dashConfig.setRoleAdminWdg5(pm.isEnableWdg5());
								 logger.info("Permission check : Role:" + pm.getUserRole() + ": Done" );
							}else if (pm.getUserRole().contains(UserConstants.ROLE_BANK_USER)) {
								dashConfig.setRoleBankUserWdg1(pm.isEnableWdg1());
								dashConfig.setRoleBankUserWdg2(pm.isEnableWdg2());
								dashConfig.setRoleBankUserWdg3(pm.isEnableWdg3());
								dashConfig.setRoleBankUserWdg4(pm.isEnableWdg4());
								dashConfig.setRoleBankUserWdg5(pm.isEnableWdg5());
								 logger.info("Permission check : Role:" + pm.getUserRole() + ": Done" );
							}else if (pm.getUserRole().contains(UserConstants.ROLE_MANAGER)) {
								dashConfig.setRoleMangrWdg1(pm.isEnableWdg1());
								dashConfig.setRoleMangrWdg2(pm.isEnableWdg2());
								dashConfig.setRoleMangrWdg3(pm.isEnableWdg3());
								dashConfig.setRoleMangrWdg4(pm.isEnableWdg4());
								dashConfig.setRoleMangrWdg5(pm.isEnableWdg5());
								 logger.info("Permission check : Role:" + pm.getUserRole() + ": Done" );
							}else if (pm.getUserRole().contains(UserConstants.ROLE_POS_USER)) {
								dashConfig.setRolePosWdg1(pm.isEnableWdg1());
								dashConfig.setRolePosWdg2(pm.isEnableWdg2());
								dashConfig.setRolePosWdg3(pm.isEnableWdg3());
								dashConfig.setRolePosWdg4(pm.isEnableWdg4());
								dashConfig.setRolePosWdg5(pm.isEnableWdg5());
								 logger.info("Permission check : Role:" + pm.getUserRole() + ": Done" );
							}else if (pm.getUserRole().contains(UserConstants.ROLE_USER)) {
								dashConfig.setRoleUserWdg1(pm.isEnableWdg1());
								dashConfig.setRoleUserWdg2(pm.isEnableWdg2());
								dashConfig.setRoleUserWdg3(pm.isEnableWdg3());
								dashConfig.setRoleUserWdg4(pm.isEnableWdg4());
								dashConfig.setRoleUserWdg5(pm.isEnableWdg5());
								 logger.info("Permission check : Role:" + pm.getUserRole() + ": Done" );
								
							}				
				});
			}
			
			return dashConfig;
		}catch (DataAccessException ex) {
			logger.error("Error: find-All-Permissions: " + ex.getMessage());
			return null;
		}
		
	}
	
   
    public Integer updateWidgetPermission(DashboardConfig dbconf) throws IllegalArgumentException {
    	int res = 0;
    	if (dbconf!=null) {

    		res=updatePermission(UserConstants.ROLE_ADMIN,dbconf.isRoleAdminWdg1(),dbconf.isRoleAdminWdg2(),dbconf.isRoleAdminWdg3(),dbconf.isRoleAdminWdg4(),dbconf.isRoleAdminWdg5());
    		res=updatePermission(UserConstants.ROLE_BANK_USER,dbconf.isRoleBankUserWdg1(),dbconf.isRoleBankUserWdg2(),dbconf.isRoleBankUserWdg3(),dbconf.isRoleBankUserWdg4(),dbconf.isRoleBankUserWdg5());
    		res=updatePermission(UserConstants.ROLE_MANAGER,dbconf.isRoleMangrWdg1(),dbconf.isRoleMangrWdg2(),dbconf.isRoleMangrWdg3(),dbconf.isRoleMangrWdg4(),dbconf.isRoleMangrWdg5());
    		res=updatePermission(UserConstants.ROLE_POS_USER,dbconf.isRolePosWdg1(),dbconf.isRolePosWdg2(),dbconf.isRolePosWdg3(),dbconf.isRolePosWdg4(),dbconf.isRolePosWdg5());
    		res=updatePermission(UserConstants.ROLE_USER,dbconf.isRoleUserWdg1(),dbconf.isRoleUserWdg2(),dbconf.isRoleUserWdg3(),dbconf.isRoleUserWdg4(),dbconf.isRoleUserWdg5());
    	
    	}
        
        return res;
    }
	
	
	// Update Permission
	private int updatePermission(String userRole,Boolean wgt1,Boolean wgt2,Boolean wgt3,Boolean wgt4,Boolean wgt5) {
			logger.info("Data 1: " + wgt1 + "2:" + wgt2 + "3:" + wgt3 + "4:" + wgt4 + "5:" + wgt5 + "User:" + userRole);
			KeyHolder holder = new GeneratedKeyHolder();
			template.update(UPDATE_DASHBOARD_WIDGET_PERMISSION, new MapSqlParameterSource()
					.addValue("enablewdg1", wgt1)
					.addValue("enablewdg2", wgt2)
					.addValue("enablewdg3", wgt3)
					.addValue("enablewdg4", wgt4)
					.addValue("enablewdg5", wgt5)
					.addValue("user_role",userRole), holder,
					new String[] { "perm_id" });

			return holder.getKey().intValue();
		}
		
		// Find Widget Permission by user role
	public DashboardWidgetPerm findWidgetPermissionByUserRole(String userRole) {
			logger.info("find-Widget-Permission-By-UserRole - : " + userRole);
	        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("user_role", userRole);

	        return template.queryForObject(FIND_WIDGET_PERMISSION_BY_USER_ROLE, namedParameters, (rs, rowNum) -> {
	        	DashboardWidgetPerm dbperm = new DashboardWidgetPerm();
				
				dbperm.setUserRole(rs.getString("user_role"));
				dbperm.setEnableWdg1(rs.getBoolean("enablewdg1"));
				dbperm.setEnableWdg2(rs.getBoolean("enablewdg2"));
				dbperm.setEnableWdg3(rs.getBoolean("enablewdg3"));
				dbperm.setEnableWdg4(rs.getBoolean("enablewdg4"));
				dbperm.setEnableWdg5(rs.getBoolean("enablewdg5"));
	            return dbperm;
	         });
		}


	@Override
	public List<Dashboard> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Dashboard> findAllById(List<String> ids) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Dashboard> findById(Integer id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Dashboard> findByKey(String key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsByParams(Dashboard params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Dashboard> findAllByDates(List<String> params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> findAllByKey(String param) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteByKey(String key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteByParams(List<String> params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> deleteAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<LocalDate> getAllDates(Integer days){
		LocalDate  endDate = LocalDate.now().plusDays(1);
		LocalDate startDate = endDate.plusDays(-days);
		long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);

		List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
										 	.limit(numOfDays)
										 	.collect(Collectors.toList());
		
		logger.info("Define Date Range:> " + "Start Date:" + listOfDates.get(0) + " | End Date:" + listOfDates.get(days-1) + " | #Days:" + listOfDates.size());
		
		return listOfDates;
	}
	

}
