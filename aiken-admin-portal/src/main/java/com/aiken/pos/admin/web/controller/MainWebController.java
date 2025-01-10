package com.aiken.pos.admin.web.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aiken.pos.admin.constant.AppStatus;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.model.Dashboard;
import com.aiken.pos.admin.model.DashboardConfig;
import com.aiken.pos.admin.model.DashboardWidgetPerm;
import com.aiken.pos.admin.service.DashboardService;
import com.aiken.pos.admin.service.DeviceService;
import com.aiken.pos.admin.util.CustomUserDetails;
import com.aiken.pos.admin.util.LoginUserUtil;
import com.aiken.pos.admin.web.form.DashboardConfigForm;

import ch.qos.logback.classic.Logger;

/**
 * Base Layout WEB Controller
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-29
 */
@Controller
public class MainWebController {
	private Logger logger = (Logger) LoggerFactory.getLogger(MainWebController.class);

	@Autowired
	private DashboardService dashboardService;

	private boolean showDeviceCount;

	@GetMapping(value = Endpoint.APP_ROOT)
	public String root(HttpServletRequest request, Model model) {

		return Endpoint.ROOT_REDIRECT;
	}

	@GetMapping(value = Endpoint.URL_ROOT)
	public String toIndex(HttpServletRequest request, Model model) throws GenericException {
		// load login user
		LoginUserUtil.loadLoginUser(model);
		if (LoginUserUtil.isNewUserLogin()) {
			return Endpoint.LOGIN;
		}
		DashboardWidgetPerm wdgtPerm = new DashboardWidgetPerm();

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(userDetails.getUserRole().contains("ROLE_ADMIN")){
			showDeviceCount=true;
		}else
			showDeviceCount=false;
				
		wdgtPerm = dashboardService.findDashboradPermissionByUserRole(userDetails.getUserRole().toString());

		model.addAttribute("showDeviceCount", showDeviceCount);
		model.addAttribute("wgt1", wdgtPerm.isEnableWdg1());
		model.addAttribute("wgt2", wdgtPerm.isEnableWdg2());
		model.addAttribute("wgt3", wdgtPerm.isEnableWdg3());
		model.addAttribute("wgt4", wdgtPerm.isEnableWdg4());
		model.addAttribute("wgt5", wdgtPerm.isEnableWdg5());

		model.addAttribute("standardDate", new Date());
		model.addAttribute("localDateTime", LocalDateTime.now());
		model.addAttribute("localDate", LocalDate.now());
		model.addAttribute("timestamp", Instant.now());

		Dashboard eventCount = dashboardService.findEventCountForLast7D();
		Dashboard oneclickCount = dashboardService.FindOneClickCountForLast7D();
		Dashboard bankOneclickCount30D = dashboardService.FindBankOneClickCountForLast30D();
		Dashboard bankEventCount30D = dashboardService.findBankEventCountForLast30D();
		Dashboard regDeviceCount30D = dashboardService.findBankDeviceRegForLast7D();
		Dashboard userStatus = dashboardService.findUserStatus();
		Dashboard dailyCount = dashboardService.findDailyCount();

		for (int i = 0; i < 7; i++) {

		}
		Map<String, Integer> eventData7D = new TreeMap<>();
		if (eventCount.getEventCount() != null) {
			eventCount.getEventCount().forEach(ec -> {
				if (ec.getEvetDate() != null && ec.getEvetCount() != null) {
					eventData7D.put(ec.getEvetDate(), ec.getEvetCount());
				}
			});
		}
		Map<String, Integer> oneClickData7D = new TreeMap<>();
		if (oneclickCount != null) {
			oneclickCount.getOneClickCount().forEach(ec -> {
				if (ec.getEvetDate() != null && ec.getEvetCount() != null) {
					oneClickData7D.put(ec.getEvetDate(), ec.getEvetCount());
				}
			});
		}

		Map<String, Integer> bankOneClickData30D = new TreeMap<>();
		if (bankOneclickCount30D.getBankOneClickCount() != null) {
			bankOneclickCount30D.getBankOneClickCount().forEach(ec -> {
				if (ec.getBankCode() != null && ec.getEvetCount() != null) {
					bankOneClickData30D.put(ec.getBankCode(), ec.getEvetCount());
				}
			});
		}

		Map<String, Integer> bankEventData30D = new TreeMap<>();
		if (bankEventCount30D.getBankEventCount() != null) {
			bankEventCount30D.getBankEventCount().forEach(ec -> {
				if (ec.getBankCode() != null && ec.getEvetCount() != null) {
					bankEventData30D.put(ec.getBankCode(), ec.getEvetCount());
				}
			});
		}

		Map<String, Integer> bankRegDeviceData30D = new TreeMap<>();
		if (regDeviceCount30D.getNewRegDeviceCount() != null) {
			regDeviceCount30D.getNewRegDeviceCount().forEach(ec -> {
				if (ec.getBankCode() != null && ec.getEvetCount() != null) {
					bankRegDeviceData30D.put(ec.getBankCode(), ec.getEvetCount());
				}
			});
		}

		

		model.addAttribute("oneClickData7D", oneClickData7D);
		model.addAttribute("eventData7D", eventData7D);
		model.addAttribute("bankOneClickData30D", bankOneClickData30D);
		model.addAttribute("bankEventData30D", bankEventData30D);
		model.addAttribute("bankRegDeviceData30D", bankRegDeviceData30D);
		model.addAttribute("userStatus", userStatus);
		model.addAttribute("dailyCount", dailyCount);
		model.addAttribute("appVersion", AppStatus.APP_STATUS_VERSION);

		return Endpoint.PAGE_INDEX;
	}

	@GetMapping(Endpoint.LOGIN)
	public String login() {

		return Endpoint.PAGE_LOGIN;
	}

	@GetMapping(Endpoint.NEW_USER_LOGIN)
	public String newLogin() {
		return Endpoint.PAGE_NEW_LOGIN;
	}

	@GetMapping(Endpoint.ERROR_PATH_403)
	public String accessDenied() {
		return Endpoint.PAGE_ERROR_403;
	}

}