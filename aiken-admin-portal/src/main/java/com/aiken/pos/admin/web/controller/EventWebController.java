package com.aiken.pos.admin.web.controller;

import com.aiken.common.util.validation.DateUtil;
import com.aiken.pos.admin.constant.*;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.helper.SessionHelper;
import com.aiken.pos.admin.model.CommonData;
import com.aiken.pos.admin.model.Device;
import com.aiken.pos.admin.model.Event;
import com.aiken.pos.admin.model.Merchant;
import com.aiken.pos.admin.service.*;
import com.aiken.pos.admin.util.LoginUserUtil;
import com.aiken.pos.admin.web.form.CommonDataForm;
import com.aiken.pos.admin.web.form.EventForm;
import com.aiken.pos.admin.web.form.FormInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Event WEB Controller
 *
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2021-08-10
 */
@Controller
public class EventWebController {

    private Logger logger = LoggerFactory.getLogger(EventWebController.class);

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventTypeService eventTypeService;
    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private MerchantTypeService merchantTypeService;

    // load and view all today events
    @GetMapping(value = Endpoint.URL_VIEW_TODAY_EVENTS)
    public String loadViewTodayEventPage(HttpServletRequest request, Model model) {

        logger.info("load view all event page for today events");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        CommonDataForm commonDataForm = new CommonDataForm();
        List<Event> event = eventService.findAllEventsByDate(DateUtil.getCurrentTime("yyyy-MM-dd"),
                DateUtil.getCurrentTime("yyyy-MM-dd"));
        CommonData commonData = new CommonData();
        model.addAttribute("commonData", commonData);
        model.addAttribute("event", event);
        model.addAttribute("commonDataForm", commonDataForm);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND_FOR_TODAY_EVENTS);
        model.addAttribute("pageStatus", EventMessage.TODAY_EVENTS);

        return Endpoint.PAGE_VIEW_EVENTS;
    }

    // load and view all events
    @GetMapping(value = Endpoint.URL_VIEW_ALL_EVENTS)
    public String loadViewEventPage(HttpServletRequest request, Model model) {

        logger.info("load view all event page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        CommonDataForm commonDataForm = new CommonDataForm();
        List<Event> event = eventService.findAllEvents();
        CommonData commonData = new CommonData();
        model.addAttribute("commonData", commonData);
        model.addAttribute("event", event);
        model.addAttribute("commonDataForm", commonDataForm);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute("pageStatus", EventMessage.ALL_EVENTS);

        return Endpoint.PAGE_VIEW_EVENTS;
    }

    // load and view all events by dates
    @GetMapping(value = Endpoint.URL_VIEW_EVENTS_BY_DATES + "/{startDate}/{endDate}")
    public String loadViewEventWithDateFilter(HttpServletRequest request, @PathVariable("startDate") String startDate,
                                              @PathVariable("endDate") String endDate, Model model) {

        logger.info("load view all event page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        CommonData commonData = new CommonData();
        model.addAttribute("commonData", commonData);
        List<Event> event = eventService.findAllEventsByDate(commonData.getStartDate(), commonData.getEndDate());
        model.addAttribute("event", event);
        model.addAttribute("commonData", commonData);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute("pageStatus", EventMessage.SEARCH_RESULT_OF_DATES + " Between " + startDate + " And " + endDate);

        return Endpoint.PAGE_VIEW_EVENTS;
    }

    // Load Device selection page for create new event
    @GetMapping(value = Endpoint.URL_SELECT_TODAY_DEVICE_FOR_EVENT)
    public String listDevices(HttpServletRequest request, Model model) {

        logger.info("load view all devices page for selection");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        CommonData commonData = new CommonData();
        List<Device> device = deviceService.findAllDevicesByDates(DateUtil.getCurrentTime("yyyy-MM-dd"),
                DateUtil.getCurrentTime("yyyy-MM-dd"));
        model.addAttribute("devicePage", device);
        model.addAttribute("commonData", commonData);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND_FOR_TODAY_UPDATED_DEVICES);
        model.addAttribute("pageStatus", EventMessage.DEVICE_UPDATED_BY_TODAY);
        CommonDataForm commonDataForm = new CommonDataForm();
        model.addAttribute("commonDataForm", commonDataForm);

        return Endpoint.PAGE_SELECT_DEVICE_FOR_EVENTS;
    }

    // load view all devices page
    @GetMapping(value = Endpoint.URL_SELECT_ALL_DEVICES_FOR_EVENT)
    public String loadViewAllDevicesPage(HttpServletRequest request, Model model) {

        logger.info("load all devices page to select device for events");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        CommonDataForm commonDataForm = new CommonDataForm();
        CommonData commonData = new CommonData();
        model.addAttribute("commonData", commonData);
        model.addAttribute("commonDataForm", commonDataForm);
        List<Device> device = deviceService.findAllDevices();
        model.addAttribute("devicePage", device);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute("pageStatus", EventMessage.ALL_DEVICES);

        return Endpoint.PAGE_SELECT_DEVICE_FOR_EVENTS;
    }

    // Search devices by dates
    @PostMapping(value = Endpoint.URL_SEARCH_DEVICE_FOR_EVENT_BY_DATE)
    public String getDevicesByDate(HttpServletRequest request, @ModelAttribute @Valid CommonDataForm commonDataForm,
                                   BindingResult result, Model model) {
        logger.info("load view all devices updated by given date range");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);

        if ((commonDataForm == null) || (commonDataForm.getStartDate().isEmpty())
                || (commonDataForm.getEndDate().isEmpty())) {

            return Endpoint.REDIRECT_TO_SELECT_ALL_DEVICES_FOR_EVENT;
        }

        CommonData commonData = convertCommonDataFormToCommonData(commonDataForm);
        List<Device> devices = deviceService.findAllDevicesByDates(commonData.getStartDate(), commonData.getEndDate());
        commonDataForm.setStartDate(commonData.getStartDate());
        commonDataForm.setEndDate(commonData.getEndDate());
        model.addAttribute("devicePage", devices);
        model.addAttribute("commonDataForm", commonDataForm);
        model.addAttribute("commonData", commonData);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute("pageStatus", EventMessage.SEARCH_RESULT_OF_DATES + " Between " + commonData.getStartDate() + " And " + commonData.getEndDate());

        return Endpoint.PAGE_SELECT_DEVICE_FOR_EVENTS;

    }

    // load all devices belongs to the given TID for event
    @GetMapping(value = Endpoint.URL_VIEW_DEVICES_WITH_TID_TID_FOR_EVENT)
    public String loadDevicesForGivenMidTid(Model model, @RequestParam("tid") String tid,
                                            @RequestParam("mid") String mid) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        if ((tid.isEmpty() || tid == null) && (mid.isEmpty() || mid == null)) {
            return Endpoint.REDIRECT_TO_SELECT_ALL_DEVICES_FOR_EVENT;
        }

        logger.info("view devices for given TID/MID>> MID: " + mid + " | TID: " + tid);
        // SessionHelper.removeFormInSession(request);

        List<Device> devices = deviceService.findAllDevicesForTidOrMid(mid, tid);
        CommonData commonData = new CommonData();
        CommonDataForm commonDataForm = new CommonDataForm();
        commonData.setTid(tid);
        commonData.setMid(mid);
        model.addAttribute("commonDataForm", commonDataForm);
        model.addAttribute("commonData", commonData);
        model.addAttribute("devicePage", devices);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute("pageStatus", EventMessage.SEARCH_RESULT_OF_TID_MID + " - " + tid + " " + mid);

        return Endpoint.PAGE_SELECT_DEVICE_FOR_EVENTS;
    }

    // load all devices page belongs to the given searching text
    @GetMapping(value = Endpoint.URL_SEARCH_DEVICE_FOR_EVENT_BY_ANY_TEXT)
    public String loadDevicesForEventByAnyText(Model model, @RequestParam("searchData") String searchData) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        if ((searchData.isEmpty() || searchData == null)) {
            return Endpoint.REDIRECT_TO_SELECT_ALL_DEVICES_FOR_EVENT;
        }

        logger.info("Load devices with given key for event>> Search Key: " + searchData);
        // SessionHelper.removeFormInSession(request);
        List<Device> devices = deviceService.findAllByParam(searchData);
        CommonData commonData = new CommonData();
        commonData.setSearchData(searchData);
        CommonDataForm commonDataForm = new CommonDataForm();
        model.addAttribute("commonDataForm", commonDataForm);
        model.addAttribute("commonData", commonData);
        model.addAttribute("devicePage", devices);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute("pageStatus", EventMessage.SEARCH_RESULT_OF_TEXT + " '" + searchData + "'");

        return Endpoint.PAGE_SELECT_DEVICE_FOR_EVENTS;
    }

    @PostMapping(value = Endpoint.URL_LOAD_EVENT_BY_SEARCH)
    public String getEventsByDates(HttpServletRequest request, @ModelAttribute @Valid CommonDataForm commonDataForm,
                                   BindingResult result, Model model) {
        logger.info("load view all event for given date range");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);

        if ((commonDataForm == null) || (commonDataForm.getStartDate().isEmpty())
                || (commonDataForm.getEndDate().isEmpty())) {

            return Endpoint.REDIRECT_TO_VIEW_ALL_EVENTS;
        }

        CommonData commonData = convertCommonDataFormToCommonData(commonDataForm);
        List<Event> event = eventService.findAllEventsByDate(commonData.getStartDate(), commonData.getEndDate());
        commonDataForm.setStartDate(commonData.getStartDate());
        commonDataForm.setEndDate(commonData.getEndDate());
        model.addAttribute("event", event);
        model.addAttribute("commonData", commonData);
        model.addAttribute("commonDataForm", commonDataForm);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute("pageStatus", EventMessage.SEARCH_RESULT_OF_DATES + " Between " + commonData.getStartDate() + " And " + commonData.getEndDate());

        return Endpoint.PAGE_VIEW_EVENTS;

    }

    // load  all events page belongs to the given searching text
    @GetMapping(value = Endpoint.URL_LOAD_EVENT_BY_ANY_TEXT)
    public String getEventsByAnyText(Model model, @RequestParam("searchData") String searchData) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        if ((searchData.isEmpty() || searchData == null)) {
            return Endpoint.REDIRECT_TO_VIEW_ALL_EVENTS;
        }

        logger.info("view events with given key>> Search Key: " + searchData);

        List<Event> events = eventService.findAllEventsByParam(searchData);
        CommonData commonData = new CommonData();
        commonData.setSearchData(searchData);

        CommonDataForm commonDataForm = new CommonDataForm();
        model.addAttribute("commonDataForm", commonDataForm);
        model.addAttribute("commonData", commonData);
        model.addAttribute("event", events);
        model.addAttribute("tableStatus", ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute("pageStatus", EventMessage.SEARCH_RESULT_OF_TEXT + " '" + searchData + "'");

        return Endpoint.PAGE_VIEW_EVENTS;
    }

    // Load new event setup page
    @GetMapping(value = Endpoint.URL_NEW_EVENTS + "/{serialNo}")
    public String loadNewEventPage(HttpServletRequest request, @PathVariable("serialNo") String serialNo, Model model)
            throws Exception {

        logger.info("Device selected by serial no");
        logger.info("Request [Serial No]:" + serialNo);
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        // load data from db
        Device device = deviceService.findDeviceBySerial(serialNo);
        Merchant newMerch = new Merchant();
        newMerch.setCategory("Clear-For-All-Merchants");
        newMerch.setMid("");
        newMerch.setTid("");
        newMerch.setCurrency("");
        newMerch.setMerchantId(0);
        List<Merchant> merchants = device.getMerchants();
        merchants.add(newMerch);
        EventForm eventForm = new EventForm();
        eventForm.setSerialNo(device.getSerialNo());
        model.addAttribute("transactionTypes", transactionTypeService.getTransactionTypes());
        model.addAttribute("clearMerchantList", merchants);
        model.addAttribute("eventTypes", eventTypeService.getEventTypes());
        model.addAttribute("eventTransactionTypes", eventTypeService.getEventTransactionTypes());
        model.addAttribute("saleMerchantTypes", merchantTypeService.getSaleMerchantTypes());
        model.addAttribute("eventForm", eventForm);
        model.addAttribute("device", device);
        model.addAttribute("executeTypes", eventTypeService.getExecuteTypes());

        return Endpoint.PAGE_ADD_NEW_EVENT;
    }

    // Load update event page
    @GetMapping(value = Endpoint.URL_LOAD_UPDATE_EVENT + "/{serialNo}/{eventId}")
    public String selectEventForUpdate(HttpServletRequest request, @PathVariable("serialNo") String serialNo,
                                       @PathVariable("eventId") Integer eventId, Model model) throws Exception {

        logger.info("Event Update [Serial No]|[EventId]: {}" + "|" + "{}", serialNo, eventId);
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        // load data from db
        Device device = deviceService.findDeviceBySerial(serialNo);
        Merchant newMerch = new Merchant();
        newMerch.setCategory("Clear-For-All-Merchants");
        newMerch.setMid("");
        newMerch.setTid("");
        newMerch.setCurrency("");
        newMerch.setMerchantId(0);
        List<Merchant> merchants = device.getMerchants();
        merchants.add(newMerch);
        Event event = eventService.findEventbyId(eventId);

        EventForm eventForm = new EventForm();
        eventForm = convertEventToEventForm(event);
        eventForm.setDateFrom(replaceCharUsingCharArray(eventForm.getDateFrom().trim(), 'T', 10));
        eventForm.setDateTo(replaceCharUsingCharArray(eventForm.getDateTo().trim(), 'T', 10));
        model.addAttribute("transactionTypes", transactionTypeService.getTransactionTypes());
        model.addAttribute("eventTypes", eventTypeService.getEventTypes());
        model.addAttribute("clearMerchantList", merchants);
        model.addAttribute("saleMerchantTypes", merchantTypeService.getSaleMerchantTypes());
        model.addAttribute("eventForm", eventForm);
        model.addAttribute("device", device);
        model.addAttribute("executeTypes", eventTypeService.getExecuteTypes());

        return Endpoint.PAGE_MODIFY_EVENT;
    }

    // Load view event page
    @GetMapping(value = Endpoint.URL_VIEW_EVENT + "/{serialNo}/{eventId}")
    public String selectEventForView(HttpServletRequest request, @PathVariable("serialNo") String serialNo,
                                     @PathVariable("eventId") Integer eventId, Model model) throws Exception {

        logger.info("Event View [Serial No]|[EventId]: {}" + "|" + "{}", serialNo, eventId);
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        // load data from db
        Device device = deviceService.findDeviceBySerial(serialNo);
        Merchant newMerch = new Merchant();
        newMerch.setCategory("Clear-For-All-Merchants");
        newMerch.setMid("");
        newMerch.setTid("");
        newMerch.setCurrency("");
        newMerch.setMerchantId(0);
        List<Merchant> merchants = device.getMerchants();
        merchants.add(newMerch);
        Event event = eventService.findEventbyId(eventId);

        EventForm eventForm;
        eventForm = convertEventToEventForm(event);
        eventForm.setDateFrom(replaceCharUsingCharArray(eventForm.getDateFrom().trim(), 'T', 10));
        eventForm.setDateTo(replaceCharUsingCharArray(eventForm.getDateTo().trim(), 'T', 10));
        model.addAttribute("transactionTypes", transactionTypeService.getTransactionTypes());
        model.addAttribute("eventTypes", eventTypeService.getEventTypes());
        model.addAttribute("clearMerchantList", merchants);
        model.addAttribute("saleMerchantTypes", merchantTypeService.getSaleMerchantTypes());
        model.addAttribute("eventForm", eventForm);
        model.addAttribute("device", device);
        model.addAttribute("executeTypes", eventTypeService.getExecuteTypes());

        return Endpoint.PAGE_VIEW_EVENT;
    }

    // Update Events
    @PostMapping(value = Endpoint.URL_SAVE_EVENT_CHANGES)
    public String updateEvent(HttpServletRequest request, @ModelAttribute @Valid EventForm eventForm,
                              BindingResult result, Model model) throws Exception {

        logger.info("Save updates of the Event");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        if (result.hasErrors()) {
            logger.info("save update device info has Error");
            result.getAllErrors();

            logger.info(result.getAllErrors().toString());
            //return Endpoint.PAGE_EVENT_FAIL;

            model.addAttribute("message", EventMessage.ERROR_MSG_FAIL);
            model.addAttribute("back_link", Endpoint.URL_VIEW_TODAY_EVENTS);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        if (eventForm.getEventType().contains(EventTypes.ACTIVITY_REPORT) && (eventForm.getReportFromDate() == "" || eventForm.getReportToDate() == "")) {
            model.addAttribute("back_link", Endpoint.URL_LOAD_UPDATE_EVENT + "/" + eventForm.getSerialNo() + "/" + eventForm.getEventId());
            model.addAttribute("message", EventMessage.ERROR_REPORT_DATES_NOT_SELECTED);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        logger.info(eventForm.toString());
        Event event = convertEventFormToEvent(eventForm);
        event.setAction(ActionType.UPDATE);

        if (eventService.existsByParams(event)) {
            logger.info("Event exist: Cannot perform this action");
            model.addAttribute("back_link", Endpoint.URL_LOAD_UPDATE_EVENT + "/" + eventForm.getSerialNo() + "/" + eventForm.getEventId());
            model.addAttribute("message", EventMessage.ERROR_MSG_EVENT_ALREADY_EXISTS);
            return Endpoint.PAGE_OPERATION_FAIL;

        }
        eventService.updateEvent(true, event);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        model.addAttribute("eventForm", formInfo.getEventForm());
        model.addAttribute("message", EventMessage.SUCCESSFULLY_UPDATED);
        model.addAttribute("back_link", Endpoint.URL_VIEW_TODAY_EVENTS);
        return Endpoint.PAGE_OPERATION_SUCCESS;
    }

    // Create new Event
    @PostMapping(value = Endpoint.URL_NEW_EVENTS)
    public String saveEvent(HttpServletRequest request, @ModelAttribute @Valid EventForm eventForm,
                            BindingResult result, Model model) throws Exception {
        logger.info("Save new event");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        if (result.hasErrors()) {
            logger.info("save new event has Error");
            result.getAllErrors();

            logger.info(result.getAllErrors().toString());
            model.addAttribute("back_link", Endpoint.URL_VIEW_ALL_EVENTS);
            model.addAttribute("message", EventMessage.ERROR_OCCURED_TRY_AGAIN);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        if (eventForm.getEventType().contains(EventTypes.ACTIVITY_REPORT) && (eventForm.getReportFromDate() == "" || eventForm.getReportToDate() == "")) {
            model.addAttribute("back_link", Endpoint.URL_LOAD_UPDATE_EVENT + "/" + eventForm.getSerialNo() + "/" + eventForm.getEventId());
            model.addAttribute("message", EventMessage.ERROR_REPORT_DATES_NOT_SELECTED);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        Event event = convertEventFormToEvent(eventForm);
        event.setAction(ActionType.INSERT);
        if (eventService.existsByParams(event)) {
            logger.info("Event exist: Cannot perform this action");
            model.addAttribute("back_link", Endpoint.URL_NEW_EVENTS + "/" + eventForm.getSerialNo());
            model.addAttribute("message", EventMessage.ERROR_MSG_EVENT_ALREADY_EXISTS);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        eventService.saveEvent(event);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        model.addAttribute("eventForm", formInfo.getEventForm());
        model.addAttribute("message", EventMessage.SUCCESSFULLY_SAVED);
        model.addAttribute("back_link", Endpoint.URL_VIEW_TODAY_EVENTS);
        return Endpoint.PAGE_OPERATION_SUCCESS;
    }

    // Delete Event by event id
    @GetMapping(value = Endpoint.URL_DELETE_EVENT + "/{eventId}")
    public String deleteEvent(HttpServletRequest request, @PathVariable("eventId") Integer eventId, Model model)
            throws GenericException {

        logger.info("Delete event by event id");
        logger.info("Event id:" + eventId);
        try {
            eventService.deleteEvent(eventId);
            SessionHelper.removeFormInSession(request);
            logger.info("Successfully Deleted");
        } catch (Exception e) {
            logger.error("Error Deleting Device:" + e);
            throw new GenericException(EventMessage.ERROR_MSG_FAIL);
        }
        model.addAttribute("message", EventMessage.SUCCESSFULLY_DELETED);
        model.addAttribute("back_link", Endpoint.URL_VIEW_TODAY_EVENTS);
        return Endpoint.PAGE_OPERATION_SUCCESS;
    }

    private Event convertEventFormToEvent(EventForm eventForm) {
        Event event = new Event();
        event.setSerialNo(eventForm.getSerialNo());
        event.setFromDate(eventForm.getDateFrom().replace("T", "-"));
        event.setToDate(eventForm.getDateTo().replace("T", "-"));
        event.setAddedBy(eventForm.getAddedBy());
        event.setStatus(eventForm.getStatus());
        event.setEventType(eventForm.getEventType());
        event.setEventId(eventForm.getEventId());
        event.setReportFromDate(eventForm.getReportFromDate());
        event.setReportToDate(eventForm.getReportToDate());
        event.setType(eventForm.getType());
        event.setMerchantTransType(eventForm.getMerchantTransType());
        event.setClearMerchant(eventForm.getClearMerchant());
        event.setExecuteType(eventForm.getExecuteType());
        event.setOnetimePassword(eventForm.getOnetimePassword());
        return event;
    }

    private EventForm convertEventToEventForm(Event event) {
        EventForm eventForm = new EventForm();
        eventForm.setEventId(event.getEventId());
        eventForm.setSerialNo(event.getSerialNo());
        eventForm.setDateFrom(event.getFromDate());
        eventForm.setDateTo(event.getToDate());
        eventForm.setStatus(event.getStatus());
        eventForm.setAddedBy(event.getAddedBy());
        eventForm.setUpdatedOn(event.getLastUpdate());
        eventForm.setEventType(event.getEventType());
        eventForm.setReportFromDate(event.getReportFromDate());
        eventForm.setReportToDate(event.getReportToDate());
        eventForm.setType(event.getType());
        eventForm.setMerchantTransType(event.getMerchantTransType());
        eventForm.setClearMerchant(event.getClearMerchant());
        eventForm.setExecuteType(event.getExecuteType());
        eventForm.setOnetimePassword(event.getOnetimePassword());
        eventForm.setAddedDate(event.getAddedDate());
        eventForm.setUpdatedBy(event.getUpdatedBy());
        return eventForm;
    }

    private CommonData convertCommonDataFormToCommonData(CommonDataForm commonDataForm) {
        CommonData commonData = new CommonData();

        commonData.setStartDate(commonDataForm.getStartDate());
        commonData.setEndDate(commonDataForm.getEndDate());

        return commonData;
    }

    private String replaceCharUsingCharArray(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }


}