package com.aiken.pos.admin.web.controller;

import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.constant.ErrorCode;
import com.aiken.pos.admin.constant.EventMessage;
import com.aiken.pos.admin.helper.SessionHelper;
import com.aiken.pos.admin.model.ActivityHistory;
import com.aiken.pos.admin.model.CommonData;
import com.aiken.pos.admin.model.Device;
import com.aiken.pos.admin.service.ActivityHistoryService;
import com.aiken.pos.admin.service.DeviceService;
import com.aiken.pos.admin.util.LoginUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Activity History Web Controller
 *
 * @author Chathuranga Dissanayake
 * @version 1.0
 * @since 2021-09-27
 */
@Controller
public class ActivityHistoryWebController {

    private Logger logger = LoggerFactory.getLogger(ActivityHistoryWebController.class);

    @Autowired
    private ActivityHistoryService activityHistoryService;
    @Autowired
    private DeviceService deviceService;

//    // load view all Activities page
//    @GetMapping(value = Endpoint.URL_VIEW_REPORTS)
//    public String loadViewActivityHistoryPage(HttpServletRequest request, Model model) {
//
//        logger.info("load view all activities page");
//        // load login user
//        LoginUserUtil.loadLoginUser(model);
//
//        SessionHelper.removeFormInSession(request);
//        CommonData commonData = new CommonData();
//        model.addAttribute("commonData", commonData);
//
//        List<ActivityHistory> activityHistories = Collections.<ActivityHistory>emptyList();
//        model.addAttribute("activityHistories", activityHistories);
//        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
//        return Endpoint.PAGE_VIEW_REPORTS;
//    }

    // load view Activities by serial no page
    @GetMapping(value = Endpoint.URL_SEARCH_ACTIVITIES_BY_SERIAL_NO)
    public String loadViewActivityHistoryPageBySerialNo(@RequestParam("serialNo") String serialNo, Model model) throws Exception {
        logger.info("load view reports page");
        logger.info("Request [Serial No]:" + serialNo);
        LoginUserUtil.loadLoginUser(model);
        CommonData commonData = new CommonData();

        if (serialNo == null || serialNo.isEmpty()) {
            model.addAttribute("commonData", commonData);
            List<ActivityHistory> activityHistories = new ArrayList<ActivityHistory>();
            model.addAttribute("activityHistories", activityHistories);
            model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
            return Endpoint.PAGE_VIEW_REPORTS;
        }
        List<ActivityHistory> activityHistories = activityHistoryService.findAllBySerialNo(serialNo);
        if (activityHistories.isEmpty()) {
            logger.info("Serial Not found: " + serialNo);
            model.addAttribute("message", serialNo);
            return Endpoint.PAGE_SERIALNO_VALIDATION_FAIL;
        }
        Device device = deviceService.findDeviceBySerial(serialNo);
        commonData.setSerialNo(serialNo);
        commonData.setMerchantName(device.getMerchantName());
        commonData.setMerchantAddress(device.getMerchantAddress());
        commonData.setBankName(device.getBankName());
        model.addAttribute("commonData", commonData);
        model.addAttribute("activityHistories", activityHistories);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);

        return Endpoint.PAGE_VIEW_REPORTS;
    }
}
