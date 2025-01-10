/**
 * 
 */
package com.aiken.pos.admin.web.controller;

import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.constant.EventMessage;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.helper.SessionHelper;
import com.aiken.pos.admin.model.CommonConfig;
import com.aiken.pos.admin.model.DashboardConfig;
import com.aiken.pos.admin.service.CommonConfigSever;
import com.aiken.pos.admin.service.DashboardService;
import com.aiken.pos.admin.util.LoginUserUtil;
import com.aiken.pos.admin.web.form.CommonConfigForm;
import com.aiken.pos.admin.web.form.DashboardConfigForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Nandana Basnayake
 *
 * @created_On Oct 28, 2022
 */
@Controller
public class SettingsWebController {
	private Logger logger = LoggerFactory.getLogger(SettingsWebController.class);
	
    @Autowired
    private DashboardService dashboardService;
	@Autowired
	private CommonConfigSever commonConfigSever;

    
    
	// load - Dashboar config web page
    @GetMapping(value = Endpoint.URL_SETTINGS_DASHBOARD_CONFIG)
    public String loadViewDashboardConfigPage(HttpServletRequest request, Model model) throws GenericException {

        logger.info("load Dashboard settings page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        
        
        DashboardConfig dbConfig = dashboardService.findDashboradPermission();
        
        DashboardConfigForm dbConfigForm = convertToDashboardConfigForm(dbConfig);
        
        logger.info("Dashboard settings page: " + dbConfigForm.isRoleAdminWdg1()); //${wgt1}
        
		/*
		 * CommonDataForm commonDataForm = new CommonDataForm(); List<Event> event =
		 * eventService.findAllEventsByDate(DateUtil.getCurrentTime("yyyy-MM-dd"),
		 * DateUtil.getCurrentTime("yyyy-MM-dd")); CommonData commonData = new
		 * CommonData(); model.addAttribute("commonData", commonData);
		 * model.addAttribute("event", event); model.addAttribute("commonDataForm",
		 * commonDataForm); model.addAttribute("tableStatus",
		 * ErrorCode.TABLE_DATA_NOT_FOUND_FOR_TODAY_EVENTS);
		 * model.addAttribute("pageStatus", EventMessage.TODAY_EVENTS);
		 */
        
        model.addAttribute("dbConfigForm", dbConfigForm);

        return Endpoint.PAGE_DASHBOARD_CONFIG;
    }

	@GetMapping(value = Endpoint.URL_SETTINGS_CONFIG)
	public String loadViewCommonConfigPage(HttpServletRequest request, Model model)  {

		logger.info("load Common Configuration page");
		// load login user
		LoginUserUtil.loadLoginUser(model);
		SessionHelper.removeFormInSession(request);

        CommonConfig commonConfig= null;
        try {
            commonConfig = commonConfigSever.findConfig();
        } catch (GenericException e) {
            throw new RuntimeException(e);
        }

        CommonConfigForm commonConfigForm = convertToCommonConfigForm(commonConfig);

		model.addAttribute("CommonConfigForm", commonConfigForm);

		return Endpoint.PAGE_CONFIG;
	}


	// Update Events
    @PostMapping(value = Endpoint.URL_UPDATE_DASHBOARD_CONFIG)
    public String updateEvent(HttpServletRequest request, @ModelAttribute @Valid DashboardConfigForm configForm,
                                     BindingResult result, Model model) throws Exception {

        logger.info("Save updates of the configurations");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        if (result.hasErrors()) {
            logger.info("save update device info has Error");
            result.getAllErrors();

            logger.info(result.getAllErrors().toString());
            //return Endpoint.PAGE_EVENT_FAIL;
            
            model.addAttribute("message",EventMessage.ERROR_MSG_FAIL);
			model.addAttribute("back_link", Endpoint.URL_VIEW_TODAY_EVENTS);
			return Endpoint.PAGE_OPERATION_FAIL;
        }

		  dashboardService.updateDashboradPermission(convertFormToDashboardConfig(configForm));
        
        model.addAttribute("message", EventMessage.SUCCESSFULLY_UPDATED);
		model.addAttribute("back_link", Endpoint.URL_SETTINGS_DASHBOARD_CONFIG);
		return Endpoint.PAGE_OPERATION_SUCCESS;
    }

	@PostMapping(value = Endpoint.URL_UPDATE_COMMON_CONFIG)
	public String updateCommomConfig(HttpServletRequest request, @ModelAttribute @Valid CommonConfigForm commonConfigForm,
							  BindingResult result, Model model) throws Exception {

		logger.info("Save updates of the Common configurations");
		// load login user
		LoginUserUtil.loadLoginUser(model);

		if (result.hasErrors()) {
			logger.info("save update device info has Error");
			result.getAllErrors();

			logger.info(result.getAllErrors().toString());
			//return Endpoint.PAGE_EVENT_FAIL;

			model.addAttribute("message",EventMessage.ERROR_MSG_FAIL);
			model.addAttribute("back_link", Endpoint.URL_VIEW_TODAY_EVENTS);
			return Endpoint.PAGE_OPERATION_FAIL;
		}
		commonConfigSever.updateCommonConfiguragion(convertFormToCommonConfig(commonConfigForm));

		model.addAttribute("message", EventMessage.SUCCESSFULLY_UPDATED);
		model.addAttribute("back_link", Endpoint.URL_SETTINGS_CONFIG);
		return Endpoint.PAGE_OPERATION_SUCCESS;
	}


	private CommonConfigForm convertToCommonConfigForm(CommonConfig commonConfig) {
		CommonConfigForm commonConfigForm = new CommonConfigForm();


		if (commonConfig != null) {
			commonConfigForm.setProfileValidation(commonConfig.isProfileValidation());
		}
		return commonConfigForm;

	}


	private CommonConfig convertFormToCommonConfig(CommonConfigForm commonConfigForm) {

		CommonConfig commonConfig =new CommonConfig();
		if (commonConfigForm != null) {
			commonConfig.setProfileValidation(commonConfigForm.isProfileValidation());
		}
		return commonConfig;
	}


	private DashboardConfig convertFormToDashboardConfig(DashboardConfigForm configForm) {

		DashboardConfig config = new DashboardConfig();

		if (configForm != null) {
			config.setRoleAdminWdg1(configForm.isRoleAdminWdg1());
			config.setRoleAdminWdg2(configForm.isRoleAdminWdg2());
			config.setRoleAdminWdg3(configForm.isRoleAdminWdg3());
			config.setRoleAdminWdg4(configForm.isRoleAdminWdg4());
			config.setRoleAdminWdg5(configForm.isRoleAdminWdg5());

			config.setRoleBankUserWdg1(configForm.isRoleBankUserWdg1());
			config.setRoleBankUserWdg2(configForm.isRoleBankUserWdg2());
			config.setRoleBankUserWdg3(configForm.isRoleBankUserWdg3());
			config.setRoleBankUserWdg4(configForm.isRoleBankUserWdg4());
			config.setRoleBankUserWdg5(configForm.isRoleBankUserWdg5());

			config.setRoleMangrWdg1(configForm.isRoleMangrWdg1());
			config.setRoleMangrWdg2(configForm.isRoleMangrWdg2());
			config.setRoleMangrWdg3(configForm.isRoleMangrWdg3());
			config.setRoleMangrWdg4(configForm.isRoleMangrWdg4());
			config.setRoleMangrWdg5(configForm.isRoleMangrWdg5());

			config.setRolePosWdg1(configForm.isRolePosWdg1());
			config.setRolePosWdg2(configForm.isRolePosWdg2());
			config.setRolePosWdg3(configForm.isRolePosWdg3());
			config.setRolePosWdg4(configForm.isRolePosWdg4());
			config.setRolePosWdg5(configForm.isRolePosWdg5());

			config.setRoleUserWdg1(configForm.isRoleUserWdg1());
			config.setRoleUserWdg2(configForm.isRoleUserWdg2());
			config.setRoleUserWdg3(configForm.isRoleUserWdg3());
			config.setRoleUserWdg4(configForm.isRoleUserWdg4());
			config.setRoleUserWdg5(configForm.isRoleUserWdg5());
		}

		return config;

	}

	private DashboardConfigForm convertToDashboardConfigForm(DashboardConfig config) {

		DashboardConfigForm configForm = new DashboardConfigForm();

		if (config != null) {
			configForm.setRoleAdminWdg1(config.isRoleAdminWdg1());
			configForm.setRoleAdminWdg2(config.isRoleAdminWdg2());
			configForm.setRoleAdminWdg3(config.isRoleAdminWdg3());
			configForm.setRoleAdminWdg4(config.isRoleAdminWdg4());
			configForm.setRoleAdminWdg5(config.isRoleAdminWdg5());

			configForm.setRoleBankUserWdg1(config.isRoleBankUserWdg1());
			configForm.setRoleBankUserWdg2(config.isRoleBankUserWdg2());
			configForm.setRoleBankUserWdg3(config.isRoleBankUserWdg3());
			configForm.setRoleBankUserWdg4(config.isRoleBankUserWdg4());
			configForm.setRoleBankUserWdg5(config.isRoleBankUserWdg5());

			configForm.setRoleMangrWdg1(config.isRoleMangrWdg1());
			configForm.setRoleMangrWdg2(config.isRoleMangrWdg2());
			configForm.setRoleMangrWdg3(config.isRoleMangrWdg3());
			configForm.setRoleMangrWdg4(config.isRoleMangrWdg4());
			configForm.setRoleMangrWdg5(config.isRoleMangrWdg5());

			configForm.setRolePosWdg1(config.isRolePosWdg1());
			configForm.setRolePosWdg2(config.isRolePosWdg2());
			configForm.setRolePosWdg3(config.isRolePosWdg3());
			configForm.setRolePosWdg4(config.isRolePosWdg4());
			configForm.setRolePosWdg5(config.isRolePosWdg5());

			configForm.setRoleUserWdg1(config.isRoleUserWdg1());
			configForm.setRoleUserWdg2(config.isRoleUserWdg2());
			configForm.setRoleUserWdg3(config.isRoleUserWdg3());
			configForm.setRoleUserWdg4(config.isRoleUserWdg4());
			configForm.setRoleUserWdg5(config.isRoleUserWdg5());
		}

		return configForm;

	}
}
