package com.aiken.pos.admin.web.controller;

import com.aiken.common.util.validation.DateUtil;
import com.aiken.common.util.validation.StringUtil;
import com.aiken.password.util.PasswordUtil;
import com.aiken.pos.admin.api.dto.ProfilesDto;
import com.aiken.pos.admin.api.dto.configdtos.*;
import com.aiken.pos.admin.constant.*;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.helper.SessionHelper;
import com.aiken.pos.admin.model.*;
import com.aiken.pos.admin.service.*;
import com.aiken.pos.admin.util.CustomValidationUtil;
import com.aiken.pos.admin.util.LoginUserUtil;
import com.aiken.pos.admin.web.form.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.*;

import static com.aiken.pos.admin.constant.ModelAttributeConst.*;

/**
 * Device WEB Controller
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-29
 */
@Controller
public class DeviceWebController {

    private Logger logger = LoggerFactory.getLogger(DeviceWebController.class);

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private MerchantTypeService merchantTypeService;
    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private CommonConfigSever commonConfigSever;
    private String selectedMerchant;
    private static int tempMerchantId = 0;
    private static int tempProfileId = 0;
    private static String merchantError = ErrorCode.NO_ERROR;
    private boolean isRemoveErr = false;
    private boolean isDefaultDcc = false;
    private boolean isBinValid = false;
    private String newEndBin = "";
    private String extEndBin = "";

    // load view all devices page to show devices updated by today
    @GetMapping(value = Endpoint.URL_VIEW_DEVICES_UPDATED_BY_TODAY)
    public String loadViewDevicesUpdatedByTodayPage(HttpServletRequest request, Model model) {

        logger.info("load view all devices page to show today updated devices");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        SessionHelper.removeFormInSession(request);
        CommonData commonData = new CommonData();
        CommonDataForm commonDataForm = new CommonDataForm();
        model.addAttribute(COMMON_DATA_FORM, commonDataForm);
        model.addAttribute(COMMON_DATA, commonData);
        List<Device> devices = deviceService.findAllDevicesAddedOnToday(DateUtil.getCurrentTime("yyyy-MM-dd"),
                DateUtil.getCurrentTime("yyyy-MM-dd"));
        model.addAttribute(DEVICES, devices);
        model.addAttribute(TABLE_STATUS, ErrorCode.TABLE_DATA_NOT_FOUND_FOR_TODAY_UPDATED_DEVICES);
        model.addAttribute(PAGE_STATUS, EventMessage.DEVICE_UPDATED_BY_TODAY);

        return Endpoint.PAGE_VIEW_DEVICES;
    }

    // load view all devices page
    @GetMapping(value = Endpoint.URL_VIEW_DEVICES)
    public String loadViewDevicesPage(HttpServletRequest request, Model model) {

        logger.info("load view all devices page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        CommonDataForm commonDataForm = new CommonDataForm();
        CommonData commonData = new CommonData();
        model.addAttribute(COMMON_DATA, commonData);
        model.addAttribute(COMMON_DATA_FORM, commonDataForm);
        List<Device> devices = deviceService.findAllDevices();
        model.addAttribute(DEVICES, devices);
        model.addAttribute(TABLE_STATUS, ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute(PAGE_STATUS, EventMessage.ALL_DEVICES);
        return Endpoint.PAGE_VIEW_DEVICES;
    }

    // Search devices by dates
    @PostMapping(value = Endpoint.URL_SEARCH_DEVICE_BY_DATE)
    public String getDevicesByDate(HttpServletRequest request, @ModelAttribute @Valid CommonDataForm commonDataForm,
                                   BindingResult result, Model model) {
        logger.info("load view all devices updated by given date range");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);

        if ((commonDataForm == null) || (commonDataForm.getStartDate().isEmpty())
                || (commonDataForm.getEndDate().isEmpty())) {

            return Endpoint.REDIRECT_TO_VIEW_ALL_DEVICES;
        }

        CommonData commonData = convertCommonDataFormToCommonData(commonDataForm);
        List<Device> devices = deviceService.findAllDevicesByDates(commonData.getStartDate(), commonData.getEndDate());
        commonDataForm.setStartDate(commonData.getStartDate());
        commonDataForm.setEndDate(commonData.getEndDate());
        model.addAttribute(DEVICES, devices);
        model.addAttribute(COMMON_DATA_FORM, commonDataForm);
        model.addAttribute(COMMON_DATA, commonData);
        model.addAttribute(TABLE_STATUS, ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute(PAGE_STATUS, EventMessage.SEARCH_RESULT_OF_DATES + " Between " + commonData.getStartDate()
                + " And " + commonData.getEndDate());

        return Endpoint.PAGE_VIEW_DEVICES;

    }

    // load all devices page belongs to the given searching text
    @GetMapping(value = Endpoint.URL_SEARCH_DEVICE_BY_ANY_TEXT)
    public String loadDevicesForAnyText(Model model, @RequestParam("searchData") String searchData) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        if (StringUtil.isNullOrWhiteSpace(searchData)) {
            return Endpoint.REDIRECT_TO_VIEW_ALL_DEVICES;
        }

        logger.info("view devices with given key>> Search Key: {}", searchData);
        // SessionHelper.removeFormInSession(request);
        List<Device> devices = deviceService.findAllByParam(searchData);
        CommonData commonData = new CommonData();
        commonData.setSearchData(searchData);
        CommonDataForm commonDataForm = new CommonDataForm();
        model.addAttribute(COMMON_DATA_FORM, commonDataForm);
        model.addAttribute(COMMON_DATA, commonData);
        model.addAttribute(DEVICES, devices);
        model.addAttribute(TABLE_STATUS, ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute(PAGE_STATUS, EventMessage.SEARCH_RESULT_OF_TEXT + " '" + searchData + "'");

        return Endpoint.PAGE_VIEW_DEVICES;
    }

    // load all devices page belongs to the given TID
    @GetMapping(value = Endpoint.URL_VIEW_DEVICES_WITH_TID)
    public String loadDevicesForGivenTid(Model model, @RequestParam("tid") String tid,
                                         @RequestParam("mid") String mid) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        if ((StringUtil.isNullOrWhiteSpace(tid)) && (StringUtil.isNullOrWhiteSpace(mid))) {
            return Endpoint.REDIRECT_TO_VIEW_ALL_DEVICES;
        }

        logger.info("view devices for given TID/MID>> MID: {} | TID: {} ", mid, tid);
        // SessionHelper.removeFormInSession(request);

        List<Device> devices = deviceService.findAllDevicesForTidOrMid(mid, tid);
        CommonData commonData = new CommonData();
        CommonDataForm commonDataForm = new CommonDataForm();
        commonData.setTid(tid);
        commonData.setMid(mid);
        model.addAttribute(COMMON_DATA_FORM, commonDataForm);
        model.addAttribute(COMMON_DATA, commonData);
        model.addAttribute(DEVICES, devices);
        model.addAttribute(TABLE_STATUS, ErrorCode.TABLE_DATA_NOT_FOUND);
        model.addAttribute(PAGE_STATUS, EventMessage.SEARCH_RESULT_OF_TID_MID + " - " + tid + " " + mid);

        return Endpoint.PAGE_VIEW_DEVICES;
    }

    // register new device link
    @GetMapping(value = Endpoint.URL_REG_NEW_DEVICE)
    public String registerNewDeviceLink(HttpServletRequest request, Model model) {

        logger.info("register new device link");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        DeviceForm deviceForm = formInfo.getDeviceForm();
        deviceForm.setVisaNoCvmLimit(new BigDecimal(CommonConstant.DEFAULT_VISA_NO_CVM_LIMIT));
        deviceForm.setCntactlsTrxnLimit(new BigDecimal(CommonConstant.DEFAULT_CNTACTLS_TRXN_LIMIT));
        deviceForm.setAutoSettleTime(CommonConstant.DEFAULT_AUTO_SETTLE_TIME);
        deviceForm.setNetwork4g(false);
        deviceForm.setNetwork3g(false);
        deviceForm.setNetwork2g(false);
        deviceForm.setAutoChange(true);
        deviceForm.setEventAutoUpdate(true);

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANTS, formInfo.getMerchants());
        model.addAttribute(PROFILES, formInfo.getProfiles());

        return Endpoint.PAGE_REGISTER_DEVICE;
    }

    // load register new device page
    @GetMapping(value = Endpoint.URL_REG_DEVICE)
    public String loadRegisterNewDevicePage(HttpServletRequest request, Model model) {

        logger.info("load register new device page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        model.addAttribute(DEVICE_FORM, formInfo.getDeviceForm());
        model.addAttribute(MERCHANTS, formInfo.getMerchants());
        model.addAttribute(PROFILES, formInfo.getProfiles());

        return Endpoint.PAGE_REGISTER_DEVICE;
    }

    // call new merchant link (register device)
    // Set-new-merchant
    @GetMapping(value = Endpoint.URL_NEW_MERCHANT)
    public String routeNewMerchantLink(HttpServletRequest request, Model model) {

        logger.info("route new merchant link (register device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        CommonDataForm commonData = new CommonDataForm();
        formInfo.setCommonDataForm(commonData);

        return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_MERCHANT;
    }

    // add new merchant link (register device)
    @GetMapping(value = Endpoint.URL_ADD_NEW_MERCHANT)
    public String addNewMerchantLink(HttpServletRequest request, Model model) {

        logger.info("add new merchant link (register device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        CommonDataForm commonData = formInfo.getCommonDataForm();

        deviceForm.setSerialNo(selectedMerchant);

        logger.info("MerchantBin Size: {}", formInfo.getMerchantBin().size());

        logger.info("add new merchant link (selectedMerchant): {}", formInfo.getDeviceForm().getVisaNoCvmLimit());
        MerchantForm merchantForm = new MerchantForm();

        merchantForm.setMid(commonData.getMid());
        merchantForm.setTid(commonData.getTid());

        // merchantForm.setAmexIp(CommonConstant.DEFAULT_AMEX_IP);

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

        return Endpoint.PAGE_ADD_NEW_MERCHANT;
    }


    // merchant add to cart (register device)
    @PostMapping(value = Endpoint.URL_NEW_ADD_TO_CART, params = "action=update")
    public String merchantAddToCart(HttpServletRequest request, @ModelAttribute @Valid MerchantForm merchantForm,
                                    BindingResult result, Model model) {

        logger.info("merchant add to cart (register device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        if (result.hasErrors()) {
            model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
            model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
            model.addAttribute(CURRENCIES, currencyService.getCurrencies());
            model.addAttribute(BIN_RULES, formInfo.getMerchantBin());
            return Endpoint.PAGE_ADD_NEW_MERCHANT;
        }
        /*
         * if (!merchantForm.isAmexTx() &&
         * merchantForm.getType().contains(MerchantTypes.AMEX)) {
         * logger.info("Amex Merchant not enabled"); model.addAttribute("message",
         * EventMessage.ERROR_AMEX_NOT_ENABLED); model.addAttribute("back_link",
         * Endpoint.URL_ADD_NEW_MERCHANT); return Endpoint.PAGE_OPERATION_FAIL; }
         */
        // validate merchant
        try {
            Map<String, Object> validationResponse = validateMerchant(merchantForm);
            logger.info("Validation Response[isValid]:" + (boolean) validationResponse.get("STATE"));
            if (!(boolean) validationResponse.get("STATE")) {
                // model.addAttribute("merchantForm", merchantForm);
                model.addAttribute(MESSAGE, (String) validationResponse.get("ERROR"));
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        } catch (Exception e) {
            logger.info("Validation Error: {}", e.getMessage());
        }

        // set temp merchant id
        merchantForm.setMerchantId(deviceService.getMaxSequence());
        //merchantForm.setMerchantId(++tempMerchantId);

        if (isValidTerminalId(request, merchantForm)) {
            // add merchant to cart
            addMerchantToCart(request, merchantForm);
            // Set merchant to with BIN Block Rules

            List<BinConfigForm> bbList = new ArrayList<BinConfigForm>();
            if (formInfo.getMerchantBin() != null) {
                formInfo.getMerchantBin().forEach(pm -> {
                    logger.info("MerchantBin : Merchant ID: {}", pm.getMerchantId());
                    if (pm.getMerchantId() == null || pm.getMerchantId() == 0) {
                        pm.setMerchantId(merchantForm.getMerchantId());
                        logger.info("MerchantBin : Merchant ID Set as: {}", pm.getMerchantId());
                    }
                    logger.info("MerchantBin : Merchant ID: {}", pm.getMerchantId());
                    bbList.add(pm);
                });
            }
            formInfo.setMerchantBin(bbList);
            logger.info("NEW MERCHANT ADDED");
            return Endpoint.REDIRECT_NAME + Endpoint.URL_REG_DEVICE;

        } else {
            logger.info("NEW MERCHANT-TERMINAL ID ALREADY AVAILABLE");
            model.addAttribute(MESSAGE, ErrorCode.TERMINAL_ID_EXIST);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // modify merchant cart link (register device)
    @GetMapping(value = Endpoint.URL_MODIFY_NEW_MERCHANT + "/{merchantId}")
    public String modifyMerchantLink(HttpServletRequest request, @PathVariable("merchantId") Integer merchantId,
                                     Model model) {

        logger.info("modify merchant cart link (register device)");
        logger.info("Request [Merchant Id]: {}", merchantId);

        // load login user
        LoginUserUtil.loadLoginUser(model);
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
//		model.addAttribute("selectedMerchant", selectedMerchant);
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());

        findSelectedMerchant(request, merchantId, model);
        logger.info("MERCHANT LOADED");

        return Endpoint.PAGE_MODIFY_NEW_MERCHANT;
    }

    // modify merchant cart link (register device)
    @GetMapping(value = Endpoint.URL_MODIFY_NEW_MERCHANT)
    public String modifyNewMerchantLink(HttpServletRequest request,
                                        Model model) {

        logger.info("modify merchant cart link (register device)");

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        Integer merchantId = formInfo.getSelectedMerchantID();

        logger.info("Request [Merchant Id]: {}", merchantId);

        // load login user
        LoginUserUtil.loadLoginUser(model);

        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
//			model.addAttribute("selectedMerchant", selectedMerchant);
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());

        findSelectedMerchant(request, merchantId, model);
        logger.info("MERCHANT LOADED");

        return Endpoint.PAGE_MODIFY_NEW_MERCHANT;
    }

    // modify merchant to cart (register device)
    @PostMapping(value = Endpoint.URL_NEW_MODIFY_TO_CART, params = "action=update")
    public String merchantModifyToCart(HttpServletRequest request, @ModelAttribute @Valid MerchantForm merchantForm,
                                       BindingResult result, Model model) {

        logger.info("modify merchant to cart (register device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        if (result.hasErrors()) {
            model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
            model.addAttribute(CURRENCIES, currencyService.getCurrencies());
            return Endpoint.PAGE_MODIFY_NEW_MERCHANT;
        }
        FormInfo formInfo = SessionHelper.getFormInSession(request);
//        if (checkMerchantInProfile(formInfo, merchantForm)) {
//            logger.info("This merchant has already been added to the profile");
//            model.addAttribute("message", ErrorCode.ERROR_MERCHANT_ALREADY_ADD_BY_PROFILE);
//            model.addAttribute("back_link", Endpoint.URL_ADD_ANOTHER_MERCHANT);
//            return Endpoint.PAGE_OPERATION_FAIL;
//        }

        /*
         * if (!merchantForm.isAmexTx() &&
         * merchantForm.getType().contains(MerchantTypes.AMEX)) {
         * logger.info("Amex Merchant not enabled"); model.addAttribute("message",
         * EventMessage.ERROR_AMEX_NOT_ENABLED); model.addAttribute("back_link",
         * Endpoint.URL_ADD_NEW_MERCHANT); return Endpoint.PAGE_OPERATION_FAIL; }
         */

        // validate merchant
        try {
            Map<String, Object> validationResponse = validateMerchant(merchantForm);
            logger.info("Validation Response[isValid]:" + (boolean) validationResponse.get("STATE"));
            if (!(boolean) validationResponse.get("STATE")) {
                // model.addAttribute("merchantForm", merchantForm);
                model.addAttribute(MESSAGE, validationResponse.get("ERROR"));
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        } catch (Exception e) {
            logger.info("Validation Error: {} ", e.getMessage());
        }

        modifyMerchantToCart(request, merchantForm);

        // Set merchant to with BIN Block Rules

        logger.info("MerchantBin : formInfo: {}", formInfo.getMerchantBin().size());
        List<BinConfigForm> bbList = new ArrayList<BinConfigForm>();
        if (formInfo.getMerchantBin() != null) {
            formInfo.getMerchantBin().forEach(pm -> {
                logger.info("MerchantBin : Merchant ID: {}", pm.getMerchantId());
                if (pm.getMerchantId() == null || pm.getMerchantId() == 0) {
                    pm.setMerchantId(merchantForm.getMerchantId());
                    logger.info("MerchantBin : Merchant ID Set as: {}", pm.getMerchantId());
                }
                pm.setMid(merchantForm.getMid());
                pm.setTid(merchantForm.getTid());
                logger.info("MerchantBin : Merchant ID: {}", pm.getMerchantId());
                bbList.add(pm);
            });
        }
        formInfo.setMerchantBin(bbList);

        return Endpoint.REDIRECT_NAME + Endpoint.URL_REG_DEVICE;
    }

    // delete merchant from cart (register device)
    @GetMapping(value = Endpoint.URL_DELETE_NEW_MERCHANT + "/{merchantId}")
    public String merchantDeleteCart(HttpServletRequest request, @PathVariable("merchantId") Integer merchantId,
                                     Model model) {

        logger.info("delete merchant from cart (register device)");
        logger.info("Request [Merchant Id]: {}", merchantId);

        // load login user
        LoginUserUtil.loadLoginUser(model);

        deleteMerchantFromCart(request, merchantId);
        logger.info("MERCHANT DELETED");

        return Endpoint.REDIRECT_NAME + Endpoint.URL_REG_DEVICE;
    }

    // save new device
    @PostMapping(value = Endpoint.URL_SAVE_DEVICE)
    public String saveDevice(HttpServletRequest request, @ModelAttribute @Valid DeviceForm deviceForm,
                             BindingResult result, Model model) throws GenericException {

        logger.info("save new device");
        try {
            FormInfo formInfo = SessionHelper.getFormInSession(request);
            model.addAttribute(MERCHANTS, formInfo.getMerchants());
            // load login user
            LoginUserUtil.loadLoginUser(model);

            if (result.hasErrors()) {
                return Endpoint.PAGE_REGISTER_DEVICE;
            }
            //set offline user to deviceForm
            if (!formInfo.getOfflineUser().isEmpty()) {
                deviceForm.setOfflineUser(formInfo.getOfflineUser());
            } else {
                deviceForm.setOfflineUser(formInfo.getDeviceForm().getOfflineUser());
            }
            Device device = convertDeviceFormToDevice(deviceForm);
            logger.info("offlineUser: {}", device.getOfflineUser());

            List<Merchant> merchants = convertMerchantFormToMerchant(formInfo);

            List<Profile> profiles = convertProfileFormToProfile(formInfo);

            List<BinConfig> binConfig = convertBinConfigFormToBinConfig(formInfo);


            if ((merchants == null) || (merchants.isEmpty())) {
                logger.info("Cannot add Device without Merchant");
                model.addAttribute(MESSAGE, EventMessage.ERROR_MERCHAT_DETAILS_NOT_FOUND);
                model.addAttribute(BACK_LINK, Endpoint.URL_REG_DEVICE);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            // Add BIN Configurations
            device.setBinConfig(binConfig);

            // add merchants
            device.setMerchants(merchants);

            device.setProfiles(profiles);
            //Check profile merchants

            isDefaultDcc = false;
            if (checkDefaultMerchants(profiles, merchants)) {
                logger.info("Cannot Set DCC merchant as default merchant in profile");
                model.addAttribute(MESSAGE, EventMessage.ERROR_DCC_MERCHANT_AS_DEFAULT_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }

            // save device
            Integer deviceId = deviceService.saveDevice(device);
            logger.info("Response [Device ID]: {}", deviceId);

            if (deviceId != null) {
                SessionHelper.removeFormInSession(request);
                // rest value
                tempMerchantId = 0;
                model.addAttribute(MESSAGE, EventMessage.DEVICE_REGISTERD_SUCCESSFULLY);
                model.addAttribute(BACK_LINK, Endpoint.URL_VIEW_DEVICES_UPDATED_BY_TODAY);
                return Endpoint.PAGE_OPERATION_SUCCESS;
            } else {
                // update load data in to the session
                formInfo.setMerchants(formInfo.getMerchants());
                formInfo.setDeviceForm(deviceForm);

                model.addAttribute(DEVICE_FORM, deviceForm);
                model.addAttribute(MERCHANTS, formInfo.getMerchants());

                model.addAttribute(MESSAGE, EventMessage.ERROR_OCCURED_TRY_AGAIN);
                model.addAttribute(BACK_LINK, Endpoint.URL_VIEW_DEVICES_UPDATED_BY_TODAY);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        } catch (GenericException e) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MSG_FAIL + ". " + e.getMessage());
            model.addAttribute(BACK_LINK, Endpoint.URL_REG_DEVICE);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    private boolean checkDefaultMerchants(List<Profile> profiles, List<Merchant> merchants) {
        isDefaultDcc = false;
        // get DCC Merchants
        List<Merchant> dccMerchants = new ArrayList<>();
        merchants.forEach(merchant -> {
            if (merchant != null)
                if (merchant.isDcc())
                    dccMerchants.add(merchant);
        });

        profiles.forEach(profile -> {
            if (profile.getProfileMerchants() != null) {
                profile.getProfileMerchants().forEach(pMerchant -> {
                    dccMerchants.forEach(dccMerchant -> {
                        if (pMerchant.isDefaultMerchant() && pMerchant.getMerchantId().equals(dccMerchant.getMerchantId())) {
                            isDefaultDcc = true;
                        }
                    });
                });
            }

        });
        return isDefaultDcc;
    }

    // modify device page load by serial no
    @GetMapping(value = Endpoint.URL_MODIFY_DEVICE + "/{deviceId}")
    public String modifyDevicePageLoader(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId,
                                         Model model) throws Exception {

        logger.info("modify device page load by serial no");
        logger.info("Request [Device ID]: {}", deviceId);
        merchantError = ErrorCode.NO_ERROR;
        SessionHelper.removeFormInSession(request);
        // load login user
        LoginUserUtil.loadLoginUser(model);
        // load data from db
        Device device = deviceService.findDeviceById(deviceId);

        List<MerchantForm> merchants = convertMerchantToMerchantForm(device);
        DeviceForm deviceForm = convertDeviceToDeviceForm(device);
        List<ProfileForm> profiles = convertProfileToProfileForm(device);
        List<BinConfigForm> binCong = convertBinConfigToBinConfigForm(device);

        // update load data in to the session
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        formInfo.setMerchants(merchants);
        formInfo.setDeviceForm(deviceForm);
        formInfo.setProfiles(profiles);
        formInfo.setMerchantBin(binCong);

        List<ProfileMerchant> profileMerchants = new ArrayList<ProfileMerchant>(0);

        profiles.forEach(profile -> {
            if (profile.getProfileMerchants() != null) {
                profile.getProfileMerchants().forEach(pMerchant -> {
                    ProfileMerchant profileMerchant = new ProfileMerchant();
                    profileMerchant.setAddedBy(pMerchant.getAddedBy());
                    profileMerchant.setAddedDate(pMerchant.getAddedDate());
                    profileMerchant.setDefaultMerchant(pMerchant.isDefault());
                    profileMerchant.setLastUpdate(pMerchant.getLastUpdate());
                    profileMerchant.setMerchantId(pMerchant.getMerchantId());
                    profileMerchant.setProfileId(pMerchant.getProfileId());
                    profileMerchant.setProfMergId(pMerchant.getProfMergId());
                    profileMerchant.setStatus(pMerchant.getStatus());
                    profileMerchant.setUpdatedBy(pMerchant.getUpdatedBy());
                    profileMerchants.add(profileMerchant);
                    logger.info("Load Profiles [Device ID]: {}  | [ProfileID]: {} | [MerchantID]: {}", deviceId, profile.getProfileId(), pMerchant.getMerchantId());
                });
            }

        });

        /*
         * profiles.forEach(profile -> profile.getProfileMerchants().forEach(pMerchant
         * -> { ProfileMerchant profileMerchant = new ProfileMerchant();
         * profileMerchant.setAddedBy(pMerchant.getAddedBy());
         * profileMerchant.setAddedDate(pMerchant.getAddedDate());
         * profileMerchant.setDefaultMerchant(pMerchant.isDefault());
         * profileMerchant.setLastUpdate(pMerchant.getLastUpdate());
         * profileMerchant.setMerchantId(pMerchant.getMerchantId());
         * profileMerchant.setProfileId(pMerchant.getProfileId());
         * profileMerchant.setProfMergId(pMerchant.getProfMergId());
         * profileMerchant.setStatus(pMerchant.getStatus());
         * profileMerchant.setUpdatedBy(pMerchant.getUpdatedBy());
         * profileMerchants.add(profileMerchant);
         * logger.info("Load Profiles [Device ID]:" + deviceId + "| [ProfileID]:" +
         * profile.getProfileId() + "| [MerchantID]:" + pMerchant.getMerchantId()); }));
         */
        logger.info("Current Profile Merchant count : {}", profileMerchants.size());
        formInfo.setProfileMerchants(profileMerchants);

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANTS, merchants);
        model.addAttribute(PROFILES, profiles);
        model.addAttribute("midtidSeg", device.isMidTidSeg());
        // reset
        tempMerchantId = 0;

        return Endpoint.PAGE_MODIFY_DEVICE;
    }

    // modify device page load by serial no
    @GetMapping(value = Endpoint.URL_MODIFY_DEVICE)
    public String modifyDeviceById(HttpServletRequest request, Model model) {

        logger.info("modify device page load from session");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        // update load data in to the session
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        model.addAttribute(DEVICE_FORM, formInfo.getDeviceForm());
        model.addAttribute(MERCHANTS, formInfo.getMerchants());
        model.addAttribute(PROFILES, formInfo.getProfiles());

        return Endpoint.PAGE_MODIFY_DEVICE;
    }

    // call new merchant link (register device)
    // Set-new-merchant
    @GetMapping(value = Endpoint.URL_NEW_ANOTHER_MERCHANT)
    public String routeNewAnotherMerchantLink(HttpServletRequest request, Model model) {

        logger.info("route new merchant link (register device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        CommonDataForm commonData = new CommonDataForm();
        formInfo.setCommonDataForm(commonData);

        return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_ANOTHER_MERCHANT;
    }

    // add new merchant page link(modify device)
    @GetMapping(value = Endpoint.URL_ADD_ANOTHER_MERCHANT)
    public String merchantAddToCartLinkOnModify(HttpServletRequest request, Model model) {

        logger.info("add new merchant page link (modify device)");

        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        CommonDataForm commonData = formInfo.getCommonDataForm();

        logger.info("MerchantBin Size: {}", formInfo.getMerchantBin().size());

        MerchantForm merchantForm = new MerchantForm();

        merchantForm.setMid(commonData.getMid());
        merchantForm.setTid(commonData.getTid());

        // merchantForm.setAmexIp(CommonConstant.DEFAULT_AMEX_IP);
        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
//		model.addAttribute("selectedMerchant", selectedMerchant);
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

        return Endpoint.PAGE_ANOTHER_MERCHANT;
    }

    // add merchant to cart (modify device)
    //current-add-to-cart
    @PostMapping(value = Endpoint.URL_CURRENT_ADD_TO_CART, params = "action=update")
    public String merchantAddToCartOnModify(HttpServletRequest request,
                                            @ModelAttribute @Valid MerchantForm merchantForm, BindingResult result, Model model) {

        logger.info("add merchant to cart (modify device)");

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        CommonDataForm commonData = formInfo.getCommonDataForm();

        // load login user
        LoginUserUtil.loadLoginUser(model);
        if (result.hasErrors()) {
            model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
            model.addAttribute(CURRENCIES, currencyService.getCurrencies());
            model.addAttribute(BIN_RULES, formInfo.getMerchantBin());
            return Endpoint.PAGE_ANOTHER_MERCHANT;
        }
        /*
         * if (!merchantForm.isAmexTx() &&
         * merchantForm.getType().contains(MerchantTypes.AMEX)) {
         * logger.info("Amex Merchant not enabled"); model.addAttribute("message",
         * EventMessage.ERROR_AMEX_NOT_ENABLED); model.addAttribute("back_link",
         * Endpoint.URL_ADD_NEW_MERCHANT); return Endpoint.PAGE_OPERATION_FAIL; }
         */

        // validate merchant
        try {
            Map<String, Object> validationResponse = validateMerchant(merchantForm);
            logger.info("Validation Response[isValid]:" + (boolean) validationResponse.get("STATE"));
            if (!(boolean) validationResponse.get("STATE")) {
                // model.addAttribute("merchantForm", merchantForm);
                model.addAttribute(MESSAGE, (String) validationResponse.get("ERROR"));
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_ANOTHER_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;

            }
        } catch (Exception e) {
            logger.info("Validation Error: {}", e.getMessage());
        }

        // set temp merchant id
        //merchantForm.setMerchantId(++tempMerchantId);
        merchantForm.setMerchantId(deviceService.getMaxSequence());
        // add merchant to cart

        if (isValidTerminalId(request, merchantForm)) {
            // add merchant to cart
            addMerchantToCart(request, merchantForm);

            List<BinConfigForm> bbList = new ArrayList<BinConfigForm>();
            if (formInfo.getMerchantBin() != null) {
                formInfo.getMerchantBin().forEach(pm -> {
                    logger.info("MerchantBin : Merchant ID: {}", pm.getMerchantId());
                    if (pm.getMerchantId() == null || pm.getMerchantId() == 0) {
                        pm.setMerchantId(merchantForm.getMerchantId());
                        logger.info("MerchantBin : Merchant ID Set as: {}", pm.getMerchantId());
                    }
                    logger.info("MerchantBin : Merchant ID: {}", pm.getMerchantId());
                    bbList.add(pm);
                });
            }
            formInfo.setMerchantBin(bbList);
            logger.info("NEW MERCHANT ADDED");

            return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_DEVICE;

        } else {
            logger.info("NEW MERCHANT-TERMINAL ID ALREADY AVAILABLE");
            model.addAttribute(MESSAGE, ErrorCode.TERMINAL_ID_EXIST);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_ANOTHER_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // modify merchant cart link from cart (modify device)
    //modify-current-merchant
    @GetMapping(value = Endpoint.URL_MODIFY_CURRENT_MERCHANT + "/{merchantId}")
    public String merchantModifyCartLink(HttpServletRequest request, @PathVariable("merchantId") Integer merchantId,
                                         Model model) {

        logger.info("modify merchant cart link from cart (modify device)");
        logger.info("Request [Merchant Id]: {}", merchantId);
        // load login user
        LoginUserUtil.loadLoginUser(model);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
//		model.addAttribute("selectedMerchant", selectedMerchant);
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(MERCHANT_ID, merchantId);
        model.addAttribute(DEVICE_FORM, formInfo.getDeviceForm());
        findSelectedMerchant(request, merchantId, model);
        logger.info("MERCHANT LOADED");

        return Endpoint.PAGE_MODIFY_CURRENT_MERCHANT;
    }

    //modify-current-merchant
    @GetMapping(value = Endpoint.URL_MODIFY_CURRENT_MERCHANT)
    public String currentMerchantModifyCartLink(HttpServletRequest request, Model model) {

        logger.info("modify merchant cart link from cart (modify device)");

        // load login user
        LoginUserUtil.loadLoginUser(model);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        Integer selectedMerchant = formInfo.getSelectedMerchantID();
        logger.info("Request [Merchant Id]: {}", selectedMerchant);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
//		model.addAttribute("selectedMerchant", selectedMerchant);
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(MERCHANT_ID, selectedMerchant);
        findSelectedMerchant(request, selectedMerchant, model);
        logger.info("MERCHANT LOADED");

        return Endpoint.PAGE_MODIFY_CURRENT_MERCHANT;
    }

    // modify merchant cart on modify cart (modify device)
    @PostMapping(value = Endpoint.URL_CURRENT_MODIFY_TO_CART, params = "action=update")
    public String merchantModifyCartOnModify(HttpServletRequest request,
                                             @ModelAttribute @Valid MerchantForm merchantForm, BindingResult result, Model model) {

        logger.info("modify merchant cart on modify cart (modify device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
//        if (checkMerchantInProfile(formInfo, merchantForm)) {
//            logger.info("This merchant has already been added to the profile");
//            model.addAttribute("message", ErrorCode.ERROR_MERCHANT_ALREADY_ADD_BY_PROFILE);
//            model.addAttribute("back_link", Endpoint.URL_ADD_ANOTHER_MERCHANT);
//            return Endpoint.PAGE_OPERATION_FAIL;
//        }
        if (result.hasErrors()) {
            model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
            model.addAttribute(CURRENCIES, currencyService.getCurrencies());
            model.addAttribute(BIN_RULES, formInfo.getMerchantBin());
            return Endpoint.PAGE_MODIFY_CURRENT_MERCHANT;
        }

        // validate merchant
        try {
            Map<String, Object> validationResponse = validateMerchant(merchantForm);
            logger.info("Validation Response[isValid]:" + (boolean) validationResponse.get("STATE"));
            if (!(boolean) validationResponse.get("STATE")) {
                model.addAttribute(MESSAGE, (String) validationResponse.get("ERROR"));
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_ANOTHER_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        } catch (Exception e) {
            logger.info("Validation Error: {}" + e.getMessage());
        }

        if (isValidTerminalId(request, merchantForm)) {
            // add merchant to cart
            modifyMerchantToCart(request, merchantForm);
            logger.info("NEW MERCHANT ADDED");
            return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_DEVICE;

        } else {
            logger.info("NEW MERCHANT-TERMINAL ID ALREADY AVAILABLE");
            model.addAttribute(MESSAGE, ErrorCode.TERMINAL_ID_EXIST);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_ANOTHER_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // delete merchant from cart (modify device)
    @GetMapping(value = Endpoint.URL_DELETE_CURRENT_MERCHANT + "/{merchantId}")
    public String deleteMerchantOnModify(HttpServletRequest request, @PathVariable("merchantId") Integer merchantId,
                                         Model model) {
        isRemoveErr = false;
        logger.info("delete merchant from cart (modify device)");
        logger.info("Request [Merchant Id]: {}", merchantId);
        // load login user
        LoginUserUtil.loadLoginUser(model);
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        formInfo.getProfileMerchants().forEach(profileMerchant -> {
            if (profileMerchant.getMerchantId().equals(merchantId) && profileMerchant.isDefaultMerchant()) {
                isRemoveErr = true;
            }
        });

        if (isRemoveErr) {
            model.addAttribute(MESSAGE, ErrorCode.ERROR_REMOVE_MERCHANT);
            merchantError = EventMessage.ERROR_PROFILE_COMMON_PARAMETERS;
            return Endpoint.PAGE_OPERATION_FAIL;
        } else {
            deleteMerchantFromCart(request, merchantId);
            logger.info("MERCHANT DELETED");

            return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_DEVICE;
        }
    }

    // save modify changes
    // edit-device
    @PostMapping(value = Endpoint.URL_EDIT_DEVICE)
    public String editDevice(HttpServletRequest request, @ModelAttribute @Valid DeviceForm deviceForm,
                             BindingResult result, Model model) throws GenericException {

        logger.info("save modify changes");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        try {
            FormInfo formInfo = SessionHelper.getFormInSession(request);
            model.addAttribute(MERCHANTS, formInfo.getMerchants());

            if (result.hasErrors()) {
                return Endpoint.PAGE_MODIFY_DEVICE;
            }

//            if (!merchantError.equals(ErrorCode.NO_ERROR)) {
//                model.addAttribute("message", merchantError + ". " + ErrorCode.PROFILE_MERCHANT_ERROR);
//                merchantError = EventMessage.ERROR_PROFILE_COMMON_PARAMETERS;
//                return Endpoint.PAGE_OPERATION_FAIL;
//            }

            //set offline user to deviceForm
            if (!formInfo.getOfflineUser().isEmpty()) {
                deviceForm.setOfflineUser(formInfo.getOfflineUser());
            } else {
                deviceForm.setOfflineUser(formInfo.getDeviceForm().getOfflineUser());
            }

            Device device = convertDeviceFormToDevice(deviceForm);
            List<Merchant> merchants = convertMerchantFormToMerchant(formInfo);
            List<Profile> profiles = convertProfileFormToProfile(formInfo);
            List<BinConfig> binConfig = convertBinConfigFormToBinConfig(formInfo);
            device.setProfiles(profiles);
            device.setBinConfig(binConfig);


            if ((merchants == null) || (merchants.isEmpty())) {
                logger.info("Cannot add Device without Merchant(Modify Device)");
                model.addAttribute(MESSAGE,
                        EventMessage.ERROR_OCCURED + ". " + EventMessage.ERROR_DEVICE_WITHOUT_MERCHANT);
                model.addAttribute(BACK_LINK, Endpoint.URL_MODIFY_DEVICE + "/" + device.getDeviceId());
                return Endpoint.PAGE_OPERATION_FAIL;
            }

            // add merchants
            device.setMerchants(merchants);

            isDefaultDcc = false;
            if (checkDefaultMerchants(profiles, merchants)) {
                logger.info("Cannot Set DCC merchant as default merchant in profile");
                model.addAttribute(MESSAGE, EventMessage.ERROR_DCC_MERCHANT_AS_DEFAULT_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }

            // Edit device
            Integer deviceId = deviceService.editDevice(device);
            logger.info("Response [Device ID]: {}", deviceId);

            if (deviceId != null) {
                SessionHelper.removeFormInSession(request);
                // reset value
                tempMerchantId = 0;
                model.addAttribute(MESSAGE, EventMessage.SUCCESSFULLY_UPDATED);
                model.addAttribute(BACK_LINK, Endpoint.URL_VIEW_DEVICES_UPDATED_BY_TODAY);
                return Endpoint.PAGE_OPERATION_SUCCESS;

            } else {
                model.addAttribute(MESSAGE, EventMessage.ERROR_MERCHAT_DETAILS_NOT_FOUND);
                model.addAttribute(BACK_LINK, Endpoint.URL_MODIFY_DEVICE + "/" + device.getDeviceId());
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        } catch (GenericException e) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MSG_FAIL + ". " + e.getMessage());
            model.addAttribute(BACK_LINK, Endpoint.URL_REG_DEVICE);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // delete device by device id
    @GetMapping(value = Endpoint.URL_DELETE_DEVICE + "/{deviceId}")
    public String deleteDevice(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId, Model model)
            throws GenericException {

        logger.info("delete device by device id");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        try {
            logger.info("Request [Device Id]: {}", deviceId);
            deviceService.deleteDevice(deviceId);
            SessionHelper.removeFormInSession(request);
            logger.info("Successfully Deleted");
        } catch (Exception e) {
            logger.error("Error Deleting Device: {}", e.getMessage());
            throw new GenericException(EventMessage.ERROR_MSG_FAIL);
        }
        model.addAttribute(MESSAGE, "Successfully Deleted");
        model.addAttribute(BACK_LINK, Endpoint.URL_VIEW_DEVICES_UPDATED_BY_TODAY);
        return Endpoint.PAGE_OPERATION_SUCCESS;
    }

    // push device
    @PostMapping(value = Endpoint.URL_PUSH_DEVICE + "/{deviceId}")
    public String pushDeviceById(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId, Model model)
            throws Exception {

        logger.info("push device");
        logger.info("Request [Device Id]: {}", deviceId);
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        Device device = deviceService.findDeviceById(deviceId);

        DeviceForm deviceForm = convertDeviceToDeviceForm(device);
        model.addAttribute(DEVICE_FORM, deviceForm);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        List<MerchantForm> merchants = convertMerchantToMerchantForm(device);

        formInfo.setMerchants(merchants);
        model.addAttribute(MERCHANTS, merchants);

        return Endpoint.PAGE_PUSH_DEVICE;
    }

    // view device by device id
    @GetMapping(value = Endpoint.URL_VIEW_DEVICE + "/{deviceId}")
    public String viewDeviceById(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId, Model model)
            throws Exception {

        logger.info("view device by device id");
        logger.info("Request [Device ID]: {}", deviceId);
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        Device device = deviceService.findDeviceById(deviceId);

        device.getMerchants().forEach(m -> {
            logger.info("getMerchantId: {}", m.getMerchantId());
            logger.info("getType: {}", m.getCategory());
        });

        DeviceForm deviceForm = convertDeviceToDeviceForm(device);
        model.addAttribute(DEVICE_FORM, deviceForm);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        List<MerchantForm> merchants = convertMerchantToMerchantForm(device);
        List<ProfileForm> profiles = convertProfileToProfileForm(device);

        formInfo.setMerchants(merchants);
        formInfo.setDeviceForm(deviceForm);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANTS, merchants);
        model.addAttribute(PROFILES, profiles);

        return Endpoint.PAGE_VIEW_DEVICE;
    }

    // view device by serial no
    @GetMapping(value = Endpoint.URL_SEARCH_DEVICE + "/{serialNo}")
    public String viewDeviceBySerialNo(HttpServletRequest request, @PathVariable("serialNo") String serialNo,
                                       Model model) throws Exception {

        logger.info("view device by serial no");
        logger.info("Request [Serial No]: {}", serialNo);
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        Device device = deviceService.findDeviceBySerial(serialNo);

        DeviceForm deviceForm = convertDeviceToDeviceForm(device);
        model.addAttribute(DEVICE_FORM, deviceForm);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        List<MerchantForm> merchants = convertMerchantToMerchantForm(device);

        formInfo.setMerchants(merchants);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANTS, merchants);

        return Endpoint.PAGE_VIEW_DEVICE;
    }

    // push device page load by serial no
    @GetMapping(value = Endpoint.URL_PUSH_DEVICE + "/{serialNo}")
    public String pushDeviceLoadBySerial(HttpServletRequest request, @PathVariable("serialNo") String serialNo,
                                         Model model) throws Exception {

        logger.info("push device page load by serial no");
        logger.info("Request [Serial No]: {}", serialNo);
        // load login user
        LoginUserUtil.loadLoginUser(model);

        SessionHelper.removeFormInSession(request);
        Device device = deviceService.findDeviceBySerial(serialNo);

        DeviceForm deviceForm = convertDeviceToDeviceForm(device);
        model.addAttribute(DEVICE_FORM, deviceForm);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        List<MerchantForm> merchants = convertMerchantToMerchantForm(device);

        formInfo.setMerchants(merchants);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANTS, merchants);

        return Endpoint.PAGE_PUSH_DEVICE;
    }

    @GetMapping(value = Endpoint.URL_CLREAR_ADD_NEW_PROFILE + "/{profileMode}")
    public String addProfileClearMerchant(HttpServletRequest request, @PathVariable("profileMode") String profileMode, Model model) {

        logger.info("Clear Merchant configurations(register device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        List<MerchantForm> merchantsList = new ArrayList<>(0);
        formInfo.getMerchants().forEach(m -> {
            m.setDefaultMerchant(false);
            m.setSelectMerchant(false);
            merchantsList.add(m);
            logger.info("Add_new_profile: {} | {}", m.getMerchantType(), m.getType());
        });
        formInfo.setMerchants(merchantsList);
        if (!(profileMode == null) && profileMode.equalsIgnoreCase("D")) {
            formInfo.getDeviceForm().setMidTidSeg(true);
            formInfo.getDeviceForm().setBankName("");
        } else if (!(profileMode == null) && profileMode.equalsIgnoreCase("C")) {
            formInfo.getDeviceForm().setBankName(BankTypes.COM_BANK);
            formInfo.getDeviceForm().setMidTidSeg(false);
        } else {
            formInfo.getDeviceForm().setBankName("");
            formInfo.getDeviceForm().setMidTidSeg(false);
        }

        return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_PROFILE;
    }

    @GetMapping(value = Endpoint.URL_CLEAR_ADD_ANOTHER_PROFILE + "/{profileMode}")
    public String addAnotherProfileClearMerchant(HttpServletRequest request, @PathVariable("profileMode") String profileMode, Model model) {

        logger.info("Clear Merchant configurations(Modify device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        List<MerchantForm> merchantsList = new ArrayList<>(0);
        formInfo.getMerchants().forEach(m -> {
            m.setDefaultMerchant(false);
            m.setSelectMerchant(false);
            merchantsList.add(m);

        });
        formInfo.setMerchants(merchantsList);
//		if (!(profileMode ==null)){ //if (!(profileMode ==null) && profileMode.equalsIgnoreCase("D")){
//			formInfo.setDiffSale(true);
//		}else formInfo.setDiffSale(false);
//		formInfo.setMerchants(merchantsList);
        if (!(profileMode == null) && profileMode.equalsIgnoreCase("D")) {
            formInfo.getDeviceForm().setMidTidSeg(true);
            formInfo.getDeviceForm().setBankName("");
        } else if (!(profileMode == null) && profileMode.equalsIgnoreCase("C")) {
            formInfo.getDeviceForm().setBankName(BankTypes.COM_BANK);
            formInfo.getDeviceForm().setMidTidSeg(false);
        } else {
            formInfo.getDeviceForm().setMidTidSeg(false);
            formInfo.getDeviceForm().setBankName("");
        }
        formInfo.setMerchants(merchantsList);
        return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_ANOTHER_PROFILE;
    }

    // Add new profile to current (modify device)
    @GetMapping(value = Endpoint.URL_ADD_ANOTHER_PROFILE)
    public String addAnotherProfile(HttpServletRequest request, Model model) {

        logger.info("add another new profile (Modify device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        List<MerchantForm> merchantForms = formInfo.getMerchants();
        logger.info("formInfo.getMerchants(): {}", formInfo.getMerchants().size());
        deviceForm.setSerialNo(selectedMerchant);

        ProfileForm profileForm = new ProfileForm();
        profileForm.setAction(ActionType.INSERT);
        profileForm.setMerchants(merchantForms);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(PROFILE_FORM, profileForm);
        model.addAttribute(DIFF_SALE, formInfo.isDiffSale());

        return Endpoint.PAGE_ADD_ANOTHER_PROFILE;
    }

    /**
     * add new profile
     *
     * @author Amesh madumalka
     * @version 1.0
     * @since 2021-10-08
     */
    @GetMapping(value = Endpoint.URL_ADD_NEW_PROFILE)
    public String addProfile(HttpServletRequest request, Model model) {

        logger.info("add new merchant link (register device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        List<MerchantForm> merchantForms = formInfo.getMerchants();
        deviceForm.setSerialNo(selectedMerchant);

        ProfileForm profileForm = new ProfileForm();
        profileForm.setMerchants(merchantForms);

        /*
         * List<MerchantForm> merchantsList = new ArrayList<>(0);
         * formInfo.getMerchants().forEach(m -> { m.setDefaultMerchant(false);
         * m.setSelectMerchant(false); merchantsList.add(m); });
         * formInfo.setMerchants(merchantsList);
         */

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(PROFILE_FORM, profileForm);
        model.addAttribute("midtidseg", formInfo.getDeviceForm().isMidTidSeg());
        model.addAttribute(DIFF_SALE, formInfo.isDiffSale());
        logger.info("diffSale {}", formInfo.isDiffSale());
        logger.info("midtidseg {}", formInfo.getDeviceForm().isMidTidSeg());
        return Endpoint.PAGE_ADD_NEW_PROFILE;
    }


    /**
     * add new profile to cart /
     */
    // new-profile-add-to-cart
    @PostMapping(value = Endpoint.URL_NEW_PROFILE_TO_CART)
    public String addProfileToCart(HttpServletRequest request, @ModelAttribute @Valid ProfileForm profileForm,
                                   BindingResult result, Model model) {
        logger.info("new-profile-add-to-cart");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        boolean profileValidate = CheckProfileValidate();

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        logger.info("mid-tis-seg: {}, bank name : {} ", formInfo.getDeviceForm().isMidTidSeg(), formInfo.getDeviceForm().getBankName());

        // if error, redirect to current page
        if (result.hasErrors()) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_PROFILE_COMMON_PARAMETERS);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        if (profileValidate) {
            if (formInfo.getDeviceForm().isMidTidSeg()) {
                String midTidValidation = profileValidationForDifferentMidTid(formInfo, model);
                if (midTidValidation != null) {
                    return midTidValidation;
                }
            }

            // profile validation for com bank
            String comBankImeiValidation = comBankImeiScanAndPayValidation(formInfo, model);
            if (comBankImeiValidation != null) {
                return comBankImeiValidation;
            }

            // profile validation to same currency
            String sameCurrencyValidation = profileValidationForSameCurrency(formInfo, model);
            if (sameCurrencyValidation != null) {
                return sameCurrencyValidation;
            }

            // profile validation for selected and default merchants
            String selectedMerchantValidation = defaultAndSelectedMerchantsValidation(formInfo, model);
            if (selectedMerchantValidation != null) {
                return selectedMerchantValidation;
            }


            MerchantForm filterDccMerchant = formInfo.getMerchants().stream()
                    .filter(pm -> pm.isSelectMerchant() && pm.isDcc()).findAny().orElse(null);
            //logger.info("filterDccMerchant> " + filterDccMerchant.getCurrency() +"| Type:" + filterDccMerchant.getType()) ;
            if (filterDccMerchant != null) {
                long filterNonDccMer = formInfo.getMerchants().stream().filter(
                                pm -> pm.isSelectMerchant() && !pm.isDcc() && pm.getType().equals(filterDccMerchant.getType()) && filterDccMerchant.getCurrency().equals(pm.getCurrency()))
                        .count();
                logger.info("filterNonDccMer> {}", filterNonDccMer);
                if (filterNonDccMer <= 0) {
                    model.addAttribute(MESSAGE, filterDccMerchant.getCurrency() + "-" + EventMessage.ERROR_NON_DCC_MERCHANT_NOT_FOUND);
                    return Endpoint.PAGE_OPERATION_FAIL;
                }
            }
            logger.info("#filterDccMerchant> {}", filterDccMerchant);
            long filterAmexMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant()).count();

            long filterAmexDefaultMer = formInfo.getMerchants().stream().filter(
                            pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                    .count();
            logger.info("filterAmexMer> {} | filterAmexDefaultMer> {}", filterAmexMer, filterAmexDefaultMer);
            if (filterAmexMer > 0 && filterAmexDefaultMer == 0) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            if (filterAmexMer > 0 && filterAmexDefaultMer > 1) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        }
        // temporary profile id
        profileForm.setProfileId(++tempProfileId);

        // add profile to cart
        if (isValidProfileName(request, profileForm)) {
            // add profiles to the list
            profileToCart(request, profileForm);

            List<MerchantForm> merchantsList = new ArrayList<>(0);
            formInfo.getMerchants().forEach(m -> {
                m.setDefaultMerchant(false);
                m.setSelectMerchant(false);
                merchantsList.add(m);
            });
            formInfo.setMerchants(merchantsList);
            return Endpoint.REDIRECT_NAME + Endpoint.URL_REG_DEVICE;
        } else {
            model.addAttribute(MESSAGE, ErrorCode.PROFILE_NAME_ALREADY_EXIST);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // modify-new-profile-add-to-cart
    @PostMapping(value = Endpoint.URL_MODIFY_NEW_PROFILE_ADD_TO_CART)
    public String modifyNewProfileAddToCart(HttpServletRequest request, @ModelAttribute @Valid ProfileForm profileForm,
                                            BindingResult result, Model model) {
        logger.info("modify-new-profile-add-to-cart");
        // load login user
        LoginUserUtil.loadLoginUser(model);
        boolean profileValidate = CheckProfileValidate();
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        // if error, redirect to current page
        if (result.hasErrors()) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_PROFILE_COMMON_PARAMETERS);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        if (profileValidate) {
            if (formInfo.getDeviceForm().isMidTidSeg()) {
                String midTidValidation = profileValidationForDifferentMidTid(formInfo, model);
                if (midTidValidation != null) {
                    return midTidValidation;
                }
            }

            //validate com bank imei scan & pay
            String comBankImeiValidation = comBankImeiScanAndPayValidation(formInfo, model);
            if (comBankImeiValidation != null) {
                return comBankImeiValidation;
            }

            // profile validation to same currency
            String sameCurrencyValidation = profileValidationForSameCurrency(formInfo, model);
            if (sameCurrencyValidation != null) {
                return sameCurrencyValidation;
            }

            // profile validation for selected and default merchants
            String selectedMerchantValidation = defaultAndSelectedMerchantsValidation(formInfo, model);
            if (selectedMerchantValidation != null) {
                return selectedMerchantValidation;
            }

            long filterAmexMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant()).count();

            long filterAmexDefaultMer = formInfo.getMerchants().stream().filter(
                            pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                    .count();
            logger.info("filterAmexMer> {} | filterAmexDefaultMer> {}", filterAmexMer, filterAmexDefaultMer);
            if (filterAmexMer > 0 && filterAmexDefaultMer == 0) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            if (filterAmexMer > 0 && filterAmexDefaultMer > 1) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        }
        // temporary profile id
        // profileForm.setProfileId(++tempProfileId);

        // add profile to cart
        if (isValidProfileName(request, profileForm)) {
            // add profiles to the list
            modifyProfileAddToCart(request, profileForm);

            List<MerchantForm> merchantsList = new ArrayList<>(0);
            formInfo.getMerchants().forEach(m -> {
                m.setDefaultMerchant(false);
                m.setSelectMerchant(false);
                merchantsList.add(m);
            });
            formInfo.setMerchants(merchantsList);
            return Endpoint.REDIRECT_NAME + Endpoint.URL_REG_DEVICE;
        } else {
            model.addAttribute(MESSAGE, ErrorCode.PROFILE_NAME_ALREADY_EXIST);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // admin-portal/new-profile-to-current-cart
    @PostMapping(value = Endpoint.URL_NEW_PROFILE_TO_CURRENT_CART)
    public String addProfileToCurrentCart(HttpServletRequest request, @ModelAttribute @Valid ProfileForm profileForm,
                                          BindingResult result, Model model) {

        // load login user
        LoginUserUtil.loadLoginUser(model);
        boolean profileValidate = CheckProfileValidate();
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        // if error, redirect to current page
        if (result.hasErrors()) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_PROFILE_COMMON_PARAMETERS);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        if (profileValidate) {
            if (formInfo.getDeviceForm().isMidTidSeg()) {
                String midTidValidation = profileValidationForDifferentMidTid(formInfo, model);
                if (midTidValidation != null) {
                    return midTidValidation;
                }
            }

            //validate com bank imei scan & pay
            String comBankImeiValidation = comBankImeiScanAndPayValidation(formInfo, model);
            if (comBankImeiValidation != null) {
                return comBankImeiValidation;
            }

            // profile validation to same currency
            String sameCurrencyValidation = profileValidationForSameCurrency(formInfo, model);
            if (sameCurrencyValidation != null) {
                return sameCurrencyValidation;
            }

            // profile validation for selected and default merchants
            String selectedMerchantValidation = defaultAndSelectedMerchantsValidation(formInfo, model);
            if (selectedMerchantValidation != null) {
                return selectedMerchantValidation;
            }

            long filterAmexMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant()).count();

            long filterAmexDefaultMer = formInfo.getMerchants().stream().filter(
                            pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                    .count();
            logger.info("filterAmexMer> {} | filterAmexDefaultMer> {}", filterAmexMer, filterAmexDefaultMer);
            if (filterAmexMer > 0 && filterAmexDefaultMer == 0) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            if (filterAmexMer > 0 && filterAmexDefaultMer > 1) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        }
        // temporary profile id
        profileForm.setProfileId(++tempProfileId);

        // add profile to cart
        if (isValidProfileName(request, profileForm)) {
            // add profiles to the list
            profileToCart(request, profileForm);

            List<MerchantForm> merchantsList = new ArrayList<>(0);
            formInfo.getMerchants().forEach(m -> {
                m.setDefaultMerchant(false);
                m.setSelectMerchant(false);
                merchantsList.add(m);
            });
            formInfo.setMerchants(merchantsList);
            return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_DEVICE;
        } else {
            model.addAttribute(MESSAGE, ErrorCode.PROFILE_NAME_ALREADY_EXIST);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // modify-profile-to-current-cart
    @PostMapping(value = Endpoint.URL_MODIFY_PROFILE_TO_CURRENT_CART)
    public String modifyProfileInCurrentCart(HttpServletRequest request, @ModelAttribute @Valid ProfileForm profileForm,
                                             BindingResult result, Model model) {

        // load login user
        LoginUserUtil.loadLoginUser(model);
        boolean profileValidate = CheckProfileValidate();
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        merchantError = ErrorCode.NO_ERROR;
        // if error, redirect to current page
        if (result.hasErrors()) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_PROFILE_COMMON_PARAMETERS);
            merchantError = EventMessage.ERROR_PROFILE_COMMON_PARAMETERS;
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        if (profileValidate) {
            if (formInfo.getDeviceForm().isMidTidSeg()) {
                String midTidValidation = profileValidationForDifferentMidTid(formInfo, model);
                if (midTidValidation != null) {
                    return midTidValidation;
                }
            }

            //validate com bank imei scan & pay
            String comBankImeiValidation = comBankImeiScanAndPayValidation(formInfo, model);
            if (comBankImeiValidation != null) {
                return comBankImeiValidation;
            }

            // profile validation to same currency
            String sameCurrencyValidation = profileValidationForSameCurrency(formInfo, model);
            if (sameCurrencyValidation != null) {
                return sameCurrencyValidation;
            }

            // profile validation for selected and default merchants
            String selectedMerchantValidation = defaultAndSelectedMerchantsValidation(formInfo, model);
            if (selectedMerchantValidation != null) {
                return selectedMerchantValidation;
            }

            MerchantForm filterDccMerchant = formInfo.getMerchants().stream()
                    .filter(pm -> pm.isSelectMerchant() && pm.isDcc()).findAny().orElse(null);
            //logger.info("filterDccMerchant> " + filterDccMerchant.getCurrency() +"| Type:" + filterDccMerchant.getType()) ;
            if (filterDccMerchant != null) {
                long filterNonDccMer = formInfo.getMerchants().stream().filter(
                                pm -> pm.isSelectMerchant() && !pm.isDcc() && pm.getType().equals(filterDccMerchant.getType()) && filterDccMerchant.getCurrency().equals(pm.getCurrency()))
                        .count();
                logger.info("filterNonDccMer> {}", filterNonDccMer);
                if (filterNonDccMer <= 0) {
                    model.addAttribute(MESSAGE, filterDccMerchant.getCurrency() + "-" + EventMessage.ERROR_NON_DCC_MERCHANT_NOT_FOUND);
                    return Endpoint.PAGE_OPERATION_FAIL;
                }
            }

            long filterAmexMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant()).count();

            long filterAmexDefaultMer = formInfo.getMerchants().stream().filter(
                            pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                    .count();
            logger.info("filterAmexMer> {}  | filterAmexDefaultMer> {}", filterAmexMer, filterAmexDefaultMer);
            if (filterAmexMer > 0 && filterAmexDefaultMer == 0) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT);
                merchantError = EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT;
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            if (filterAmexMer > 0 && filterAmexDefaultMer > 1) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS);
                merchantError = EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS;
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        }
        // add profile to cart
        if (isValidProfileName(request, profileForm)) {
            // add profiles to the list
            profileToCart(request, profileForm);

            List<MerchantForm> merchantsList = new ArrayList<>(0);
            formInfo.getMerchants().forEach(m -> {
                m.setDefaultMerchant(false);
                m.setSelectMerchant(false);
                merchantsList.add(m);
            });
            formInfo.setMerchants(merchantsList);
            return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_DEVICE;
        } else {
            model.addAttribute(MESSAGE, ErrorCode.PROFILE_NAME_ALREADY_EXIST);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            //merchantError=ErrorCode.PROFILE_NAME_ALREADY_EXIST;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // modify-profile-to-current-cart-
    @PostMapping(value = Endpoint.URL_MODIFY_PROFILE_TO_CURRENT_CART_GO_BACK)
    public String modifyProfileInCurrentCartGoBack(HttpServletRequest request, @ModelAttribute @Valid ProfileForm profileForm,
                                                   BindingResult result, Model model) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        merchantError = ErrorCode.NO_ERROR;
        // if error, redirect to current page
        if (result.hasErrors()) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_PROFILE_COMMON_PARAMETERS);
            merchantError = EventMessage.ERROR_PROFILE_COMMON_PARAMETERS;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        long filterSaleMer = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant()).count();

        long filterSaleDefaultMer = formInfo.getMerchants().stream().filter(
                        pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                .count();
        logger.info("#SaleMerchants> {} | #SalesDefaultMerchants> {}", filterSaleMer, filterSaleDefaultMer);
        if (filterSaleMer == 0) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_NO_SALE_MERCHANT);
            merchantError = EventMessage.ERROR_NO_SALE_MERCHANT;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        if (filterSaleMer > 0 && filterSaleDefaultMer == 0) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_NO_SALE_DEFAULT_MERCHANT);
            merchantError = EventMessage.ERROR_NO_SALE_DEFAULT_MERCHANT;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        if (filterSaleMer > 0 && filterSaleDefaultMer > 1) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_SALE_DEFAULT_MERCHANTS);
            merchantError = EventMessage.ERROR_MULTIPLE_SALE_DEFAULT_MERCHANTS;
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        long filterAmexMer = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant()).count();

        long filterAmexDefaultMer = formInfo.getMerchants().stream().filter(
                        pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                .count();
        logger.info("filterAmexMer> {}  | filterAmexDefaultMer> ", filterAmexMer, filterAmexDefaultMer);
        if (filterAmexMer > 0 && filterAmexDefaultMer == 0) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT);
            merchantError = EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        if (filterAmexMer > 0 && filterAmexDefaultMer > 1) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS);
            merchantError = EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS;
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        // add profile to cart
        if (isValidProfileName(request, profileForm)) {
            // add profiles to the list
            profileToCart(request, profileForm);

            List<MerchantForm> merchantsList = new ArrayList<>(0);
            formInfo.getMerchants().forEach(m -> {
                m.setDefaultMerchant(false);
                m.setSelectMerchant(false);
                merchantsList.add(m);
            });
            formInfo.setMerchants(merchantsList);
            return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_DEVICE;
        } else {
            model.addAttribute(MESSAGE, ErrorCode.PROFILE_NAME_ALREADY_EXIST);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            //merchantError=ErrorCode.PROFILE_NAME_ALREADY_EXIST;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    /**
     * delete profile from cart
     */
    @GetMapping(value = Endpoint.URL_DELETE_NEW_PROFILE + "/{profileId}")
    public String deleteNewProfileFromCart(HttpServletRequest request, @PathVariable("profileId") Integer profileId,
                                           Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);

        // delete profile from cart
        deleteProfileFromCart(request, profileId);
        logger.info("PROFILE DELETED");

        return Endpoint.REDIRECT_NAME + Endpoint.URL_REG_DEVICE;
    }

    @GetMapping(value = Endpoint.URL_DELETE_PROFILE + "/{profileId}")
    public String deleteProfileFromCart(HttpServletRequest request, @PathVariable("profileId") Integer profileId,
                                        Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);

        // delete profile from cart
        deleteProfileFromCart(request, profileId);
        logger.info("PROFILE DELETED");

        return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_DEVICE;
    }

    // merchant add to cart
    private void addMerchantToCart(HttpServletRequest request, MerchantForm merchantForm) {

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getMerchants() != null)
            logger.info("ADD: Before Cart Size: {}", formInfo.getMerchants().size());

        if (!merchantForm.getType().equalsIgnoreCase(MerchantTypes.SALE))
            merchantForm.setMerchantType(null);
        // to be insert merchant
        merchantForm.setAction(ActionType.INSERT);
        // add merchant to cart
        formInfo.getMerchants().add(merchantForm);

        if (formInfo.getMerchants() != null)
            logger.info("After Cart Size: {}", formInfo.getMerchants().size());
    }

    // validate Terminal ID before adding to cart
    private boolean isValidTerminalId(HttpServletRequest request, MerchantForm merchantForm) {

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getMerchants() == null) {
            logger.info("Cart is empty. TID Accepted");
            return true;
        }
        for (MerchantForm merchant : formInfo.getMerchants()) {
            if (merchant.getTid().contentEquals(merchantForm.getTid())
                    && (!merchant.getMerchantId().equals(merchantForm.getMerchantId()))) {
                logger.info("This TID available in the cart : {}", merchantForm.getTid());
                return false;
            }
        }
        return true;
    }

    // merchant modify cart
    private void modifyMerchantToCart(HttpServletRequest request, MerchantForm merchantForm) {

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getMerchants() != null)
            logger.info("Modify: Before Cart Size: {}", formInfo.getMerchants().size());

        boolean isRemove = false;
        logger.info("Merchant ID: {}", merchantForm.getMerchantId());

        if (formInfo.getMerchants() != null && !formInfo.getMerchants().isEmpty()) {

            Iterator<MerchantForm> iterator = formInfo.getMerchants().iterator();
            while (iterator.hasNext()) {
                MerchantForm existingMerchant = iterator.next();
                if (CustomValidationUtil.isSameId(existingMerchant.getMerchantId(), merchantForm.getMerchantId())) {
                    Integer existingMerchantId = existingMerchant.getMerchantId();
                    logger.info("Existing Merchant Id: {}", existingMerchantId);
                    iterator.remove(); // remove existing merchant
                    // only for edit modify flow
                    merchantForm.setMerchantId(existingMerchantId);
                    // to be update merchant
                    merchantForm.setAction(merchantForm.getAction().equalsIgnoreCase(ActionType.INSERT) ? ActionType.INSERT : ActionType.UPDATE);
                    logger.warn("EXISTING MERCHANT REMOVED");
                    isRemove = true;
                }
            }
        }

        if (isRemove) {
            formInfo.getMerchants().add(merchantForm); // add new merchant
            logger.warn("ADDED NEW MERCHANT");
        }

        if (formInfo.getMerchants() != null)
            logger.info("After Cart Size: {}", formInfo.getMerchants().size());
    }

    // merchant delete from cart
    private void deleteMerchantFromCart(HttpServletRequest request, Integer merchantId) {

        logger.info("Merchant Id: {}", merchantId);
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getMerchants() != null)
            logger.info("Before Cart Size: {}", formInfo.getMerchants().size());
        List<MerchantForm> removedMerchants = new ArrayList<>();
        if (formInfo.getMerchants() != null && !formInfo.getMerchants().isEmpty()) {

            Iterator<MerchantForm> iterator = formInfo.getMerchants().iterator();
            while (iterator.hasNext()) {
                MerchantForm existingMerchant = iterator.next();
                if (CustomValidationUtil.isSameId(existingMerchant.getMerchantId(), merchantId)) {
                    if (formInfo.getProfileMerchants() != null) {
                        logger.info("removed Merchant: {}", existingMerchant.getMerchantId());

                        Iterator<ProfileMerchant> itr = formInfo.getProfileMerchants().iterator();
                        while (itr.hasNext()) {
                            ProfileMerchant existingProfMerchant = itr.next();

                            if (CustomValidationUtil.isSameId(existingMerchant.getMerchantId(), existingProfMerchant.getMerchantId())) {

                                itr.remove();
                            }

                        }
                    }

                    iterator.remove();
                }

            }
        }
        if (formInfo.getMerchants() != null) {
            logger.info("removed Merchants list Size: {}", removedMerchants.size());
        }
        if (formInfo.getMerchants() != null)
            logger.info("After Cart Size: {}", formInfo.getMerchants().size());

        logger.info("Profile Merchants Count: {}", formInfo.getProfileMerchants().size());
    }

    private void findSelectedMerchant(HttpServletRequest request, Integer merchantId, Model model) {
        model.addAttribute("merchant", new Merchant());
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        formInfo.setSelectedMerchantID(merchantId);
        model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

        if (formInfo.getMerchants() != null && !formInfo.getMerchants().isEmpty()) {

            Iterator<MerchantForm> iterator = formInfo.getMerchants().iterator();
            while (iterator.hasNext()) {
                MerchantForm merchantForm = iterator.next();
                if (CustomValidationUtil.isSameId(merchantForm.getMerchantId(), merchantId)) {
                    logger.info("Selected Merchant Id: {}", merchantId);
                    model.addAttribute(MERCHANT_FORM, merchantForm); // load merchant by merchantId
                    break;
                }
            }
        }
    }

    private boolean isExistTid(HttpServletRequest request, String tid) {

        boolean isExistTid = false;
        logger.info("TID: {}", tid);

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getMerchants() != null && !formInfo.getMerchants().isEmpty()) {
            for (MerchantForm existingMerchant : formInfo.getMerchants()) {
                if (existingMerchant.getTid().equals(tid)) {
                    isExistTid = true;
                    break;
                }
            }
        }

        return isExistTid;
    }

    private boolean isExistTempMerchantId(HttpServletRequest request, int merchantId) {

        boolean isExist = false;
        logger.info("Temp Merchant Id: {}", merchantId);

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getMerchants() != null && !formInfo.getMerchants().isEmpty()) {
            for (MerchantForm existingMerchant : formInfo.getMerchants()) {
                if (CustomValidationUtil.isSameId(existingMerchant.getMerchantId(), merchantId)) {
                    isExist = true;
                    break;
                }
            }
        }

        return isExist;
    }

    // view merchant details (register device)
    /*
     * @AddedBy : Nandana Basnayake
     *
     * @Date : 25-02-2021
     */
    @GetMapping(value = Endpoint.URL_VIEW_MERCHANT + "/{merchantId}/{deviceSerial}")
    public String viewMerchantLink(HttpServletRequest request, @PathVariable("merchantId") Integer merchantId,
                                   @PathVariable("deviceSerial") String deviceSerial, Model model) {

        logger.info("view Merchant Link (View Merchant info)");
        logger.info("Request [Merchant Id]: {}", merchantId);

        findSelectedMerchant(request, merchantId, model);
        logger.info("MERCHANT LOADED");

        return Endpoint.PAGE_VIEW_MERCHANT;
    }

    private Map<String, Object> validateMerchant(MerchantForm merchantForm) {
        // validate merchant by type
        boolean isValid = true;
        String error = null;
        if (merchantForm.getType().equalsIgnoreCase(MerchantTypes.EPP)
                || merchantForm.getType().equalsIgnoreCase(MerchantTypes.EPP_AMEX)) {
            if (merchantForm.getMonth() == null || merchantForm.getMonth() <= 0) {
                isValid = false;
                error = "Month is required for Easy Payment Merchant";
            }
        } else if (merchantForm.getType().equalsIgnoreCase(MerchantTypes.QR)) {
            if (merchantForm.getMerchantUserId() == null || merchantForm.getMerchantUserId().isEmpty()
                    || merchantForm.getMerchantPassword() == null || merchantForm.getMerchantPassword().isEmpty()
                    || merchantForm.getChecksumKey() == null || merchantForm.getChecksumKey().isEmpty()) {
                isValid = true; //Need to modify this in future
                error = "Merchant User Id, Merchant Password and Checksum Key is required for Q Plus Merchant";
            }
        } else if (merchantForm.getType().equalsIgnoreCase(MerchantTypes.AMEX)) {
            /*
             * if (merchantForm.getAmexIp() == null || merchantForm.getAmexIp().isEmpty()) {
             * isValid = false; error = "IP is required for Amex Merchant"; }
             */
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("STATE", isValid);
        response.put("ERROR", error);

        return response;
    }

    private CommonData convertCommonDataFormToCommonData(CommonDataForm commonDataForm) {
        logger.info("convert CommonDataForm To CommonData");
        logger.info("Serial: {}", commonDataForm.getSerialNo());
        CommonData commonData = new CommonData();

        commonData.setStartDate(commonDataForm.getStartDate());
        commonData.setEndDate(commonDataForm.getEndDate());

        return commonData;
    }

    public DeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public MerchantTypeService getMerchantTypeService() {
        return merchantTypeService;
    }

    public void setMerchantTypeService(MerchantTypeService merchantTypeService) {
        this.merchantTypeService = merchantTypeService;
    }

    public String getSelectedMerchant() {
        return selectedMerchant;
    }

    public void setSelectedMerchant(String selectedMerchant) {
        this.selectedMerchant = selectedMerchant;
    }

    public CurrencyService getCurrencyService() {
        return currencyService;
    }

    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public boolean CheckProfileValidate() {
        CommonConfig commonConfig = null;
        try {
            commonConfig = commonConfigSever.findConfig();
        } catch (GenericException e) {
            throw new RuntimeException(e);
        }
        return commonConfig.isProfileValidation();

    }

    // add profiles to profile car (Amesh Madumalka 2021/10/08)
    private void profileToCart(HttpServletRequest request, ProfileForm profileForm) {
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        logger.info("Update Profile |Action: {}", profileForm.getAction());
        logger.info("Update Profile |ID: {}", profileForm.getProfileId());
        logger.info("Update Profile |Name: {}", profileForm.getProfileName());
        if (formInfo.getProfiles() != null) {
            logger.info("Profile Cart Size before {}", formInfo.getProfiles().size());
            profileForm.setAction(ActionType.INSERT);
            if (profileForm.getProfileId() != null) {
                ProfileForm filteredProfile = formInfo.getProfiles().stream()
                        .filter(pm -> pm.getProfileId().equals(profileForm.getProfileId())).findAny().orElse(null);
                if (filteredProfile != null) {
                    formInfo.getProfiles().remove(formInfo.getProfiles().indexOf(filteredProfile));
                    profileForm.setAction(ActionType.UPDATE);
                }
            }

            formInfo.getProfiles().add(profileForm);
            if (formInfo.getProfiles() != null) {
                logger.info("Profile Cart Size : {}", formInfo.getProfiles().size());
            }
        }
        logger.info("Profile Cart - ProfileMerchants : {}", formInfo.getProfileMerchants().size());

        /*
         * ListIterator<ProfileMerchant> iter =
         * formInfo.getProfileMerchants().listIterator(); while(iter.hasNext()){
         * if(iter.next().getProfileId().equals(profileForm.getProfileId())){
         * iter.remove(); } }
         */

        List<ProfileMerchant> merchantsList = new ArrayList<>(0);
        formInfo.getProfileMerchants().forEach(m -> {

            if (m.getProfileId() == 0) {
                m.setProfileId(profileForm.getProfileId());
                logger.info("Profile id.... : {}", m.getProfileId());

            }
            merchantsList.add(m);
            logger.info("Profile id------ :{}", m.getProfileId());
        });
        formInfo.setProfileMerchants(merchantsList);

    }

    private void modifyProfileAddToCart(HttpServletRequest request, ProfileForm profileForm) {
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getProfiles() != null) {
            logger.info("Profile Cart Size before {}", formInfo.getProfiles().size());
            profileForm.setAction(ActionType.INSERT);
            if (profileForm.getProfileId() != null) {
                ProfileForm filteredProfile = formInfo.getProfiles().stream()
                        .filter(pm -> pm.getProfileId().equals(profileForm.getProfileId())).findAny().orElse(null);
                if (filteredProfile != null) {
                    formInfo.getProfiles().remove(formInfo.getProfiles().indexOf(filteredProfile));
                    // profileForm.setAction(ActionType.UPDATE);
                }
            }

            formInfo.getProfiles().add(profileForm);
            if (formInfo.getProfiles() != null) {
                logger.info("Profile Cart Size after: {}", formInfo.getProfiles().size());
            }
        }

        logger.info("Profile Cart - ProfileMerchants : {}", formInfo.getProfileMerchants().size());
        List<ProfileMerchant> merchantsList = new ArrayList<>(0);
        formInfo.getProfileMerchants().forEach(m -> {

            if (m.getProfileId() == 0) {
                m.setProfileId(profileForm.getProfileId());

            }
            merchantsList.add(m);

        });
        formInfo.setProfileMerchants(merchantsList);

    }

    // validate profile name (Amesh Madumalka 2021/10/08)
    private boolean isValidProfileName(HttpServletRequest request, ProfileForm profileForm) {
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getProfiles() == null || formInfo.getProfiles().isEmpty()) {
            return true;
        }

        if (formInfo.getProfiles() != null && !formInfo.getProfiles().isEmpty()) {
            for (ProfileForm profile : formInfo.getProfiles()) {
                if (profile != null && !StringUtil.isNullOrWhiteSpace(profile.getProfileName())
                        && profile.getProfileName().equalsIgnoreCase(profileForm.getProfileName())) {
                    logger.info("profile.getProfileId(): {}", profile.getProfileId());
                    logger.info("profileForm.getProfileId(): {}", profileForm.getProfileId());
                    if (profileForm.getProfileId() != null
                            && profile.getProfileId().equals(profileForm.getProfileId())) {
                        return true;
                    }
                    logger.info("Can't add same profile name");
                    return false;
                }
            }
        }
        return true;
    }

    // delete profile from cart (Amesh Madumalka 2021/10/08)
    private void deleteProfileFromCart(HttpServletRequest request, Integer profileId) {

        logger.info("Merchant Id: {}", profileId);
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getProfiles() != null) {
            logger.info("Before profile Cart Size: {} ", formInfo.getProfiles().size());
        }

        if (formInfo.getProfiles() != null && !formInfo.getProfiles().isEmpty()) {

            Iterator<ProfileForm> iterator = formInfo.getProfiles().iterator();
            while (iterator.hasNext()) {
                ProfileForm existingProfile = iterator.next();
                if (CustomValidationUtil.isSameId(existingProfile.getProfileId(), profileId)) {
                    iterator.remove();
                }
            }
            Iterator<ProfileMerchant> profileMerchantIterator = formInfo.getProfileMerchants().iterator();
            while (profileMerchantIterator.hasNext()) {
                ProfileMerchant existingProfileMerchant = profileMerchantIterator.next();
                if (CustomValidationUtil.isSameId(existingProfileMerchant.getProfileId(), profileId)) {
                    profileMerchantIterator.remove();
                }
            }
        }

        if (formInfo.getProfiles() != null) {
            logger.info("After profile Cart Size: {}", formInfo.getProfiles().size());
        }
    }

    // configure-profile-merchant
    @GetMapping(value = Endpoint.URL_CONFIGURE_MERCHANT_TO_PROFILE + "/{merchantId}")
    public String configureMerchantWithProfile(HttpServletRequest request,
                                               @PathVariable("merchantId") Integer merchantId, Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);
        logger.info("merchantId: {}", merchantId);

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        List<MerchantForm> merchantList = formInfo.getMerchants();
        MerchantForm merchantForm = merchantList.stream()
                .filter(merchant -> merchantId.equals(merchant.getMerchantId())).findAny().orElse(null);

        logger.info("Configure Merchant to profile:> Selected Merchant: {}", merchantForm.getMerchantId());

        model.addAttribute(MERCHANT_FORM, merchantForm);
        return Endpoint.PAGE_CONFIGURE_MERCHANT_TO_PROFILE;
    }

    /// modify-profile-merchant
    @GetMapping(value = Endpoint.URL_MODIFY_MERCHANT_TO_PROFILE + "/{merchantId}/{profileId}")
    public String modifyMerchantWithProfile(HttpServletRequest request, @PathVariable("merchantId") Integer merchantId,
                                            @PathVariable("profileId") Integer profileId, Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);
        logger.info("merchantId: {}", merchantId);

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        List<MerchantForm> merchantList = formInfo.getMerchants();
        MerchantForm merchantForm = merchantList.stream()
                .filter(merchant -> merchantId.equals(merchant.getMerchantId())).findAny().orElse(null);
        merchantForm.setProfileId(profileId);
        logger.info("modify Merchant of profile:> Selected Merchant: {}", merchantForm.getMerchantId());

        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute("profileId", profileId);
        return Endpoint.PAGE_MODIFY_MERCHANT_TO_PROFILE;
    }

    /// modify-new-profile-merchant
    @GetMapping(value = Endpoint.URL_MODIFY_NEW_PROFILE_MERCHANT + "/{merchantId}/{profileId}")
    public String modifyMerchantWithNewProfile(HttpServletRequest request,
                                               @PathVariable("merchantId") Integer merchantId, @PathVariable("profileId") Integer profileId, Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);
        logger.info("merchantId: {}", merchantId);

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        List<MerchantForm> merchantList = formInfo.getMerchants();
        MerchantForm merchantForm = merchantList.stream()
                .filter(merchant -> merchantId.equals(merchant.getMerchantId())).findAny().orElse(null);
        merchantForm.setProfileId(profileId);
        logger.info("modify Merchant of profile:> Selected Merchant: {}", merchantForm.getMerchantId());

        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute("profileId", profileId);
        return Endpoint.PAGE_MODIFY_NEW_PROFILE_MERCHANT;
    }

    // configure-another-profile-merchant
    @GetMapping(value = Endpoint.URL_CONFIGURE_MERCHANT_TO_ANOTHER_PROFILE + "/{merchantId}")
    public String configureMerchantWithAnotherProfile(HttpServletRequest request,
                                                      @PathVariable("merchantId") Integer merchantId, Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);
        logger.info("merchantId: {}", merchantId);

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        List<MerchantForm> merchantList = formInfo.getMerchants();
        MerchantForm merchantForm = merchantList.stream()
                .filter(merchant -> merchantId.equals(merchant.getMerchantId())).findAny().orElse(null);

        logger.info("Configure Merchant to profile:> Selected Merchant: {}", merchantForm.getMerchantId());

        model.addAttribute(MERCHANT_FORM, merchantForm);
        return Endpoint.PAGE_CONFIGURE_MERCHANT_TO_ANOTHER_PROFILE;
    }

    // add-merchant-to-profile-cart
    @PostMapping(value = Endpoint.URL_MERCHANT_TO_PROFILE_CART)
    public String addMerchantToProfileCart(HttpServletRequest request, @ModelAttribute @Valid MerchantForm merchantForm,
                                           BindingResult result, Model model) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        // if error, redirect to current page
        if (result.hasErrors()) {
            return Endpoint.PAGE_ADD_NEW_PROFILE;
        }
        /*
         * if(!merchantForm.isSelectMerchant()) { logger.info("Merchant not selected");
         * model.addAttribute("message",
         * "Please select 'Add to Profile' option to add merchant into the profile");
         * model.addAttribute("back_link", Endpoint.URL_ADD_NEW_PROFILE); return
         * Endpoint.PAGE_OPERATION_FAIL; }
         */

        if ((merchantForm != null)) {
            FormInfo formInfo = SessionHelper.getFormInSession(request);
            ProfileMerchant profileMerchant = new ProfileMerchant();

            MerchantForm filterSaleMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.SALE)
                            && merchantForm.getType().equals(MerchantTypes.SALE)
                            && pm.getCurrency().equals(merchantForm.getCurrency()) && pm.isSelectMerchant())
                    .findAny().orElse(null);
            /*
             * if (!(filterSaleMer == null) && merchantForm.isSelectMerchant()) { if
             * (!filterSaleMer.getMerchantId().equals(merchantForm.getMerchantId())) {
             * logger.info("Merchant Already added | " + merchantForm.getType() + " |");
             * model.addAttribute("message",
             * "Can not Add more than one merchant for the same currency");
             * model.addAttribute("back_link", Endpoint.URL_ADD_NEW_PROFILE); return
             * Endpoint.PAGE_OPERATION_FAIL; }
             *
             * }
             */

            MerchantForm filterSaleDefaultMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.SALE)
                            && merchantForm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant()
                            && pm.isDefaultMerchant())
                    .findAny().orElse(null);
            if (!(filterSaleDefaultMer == null) && merchantForm.isDefaultMerchant()) {
                if (!filterSaleDefaultMer.getMerchantId().equals(merchantForm.getMerchantId())) {
                    logger.info("Merchant Already added as Default | {}", merchantForm.getType());
                    model.addAttribute(MESSAGE, "Cannot set more than one merchant as defualt merchant");
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_PROFILE);
                    return Endpoint.PAGE_OPERATION_FAIL;
                }

            }

            MerchantForm filterAmexDefaultMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.AMEX)
                            && merchantForm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant()
                            && pm.isDefaultMerchant())
                    .findAny().orElse(null);
            if (!(filterAmexDefaultMer == null) && merchantForm.isDefaultMerchant()) {
                if (!filterAmexDefaultMer.getMerchantId().equals(merchantForm.getMerchantId())) {
                    logger.info("Merchant Already added | {}", merchantForm.getType());
                    model.addAttribute(MESSAGE, "Cannot set more than one merchant as default merchant");
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_PROFILE);
                    return Endpoint.PAGE_OPERATION_FAIL;
                }
            }

            ProfileMerchant filteredMerchant = formInfo.getProfileMerchants().stream()
                    .filter(pm -> pm.getMerchantId().equals(merchantForm.getMerchantId())).findAny().orElse(null);
            if (filteredMerchant != null) {
                if (filteredMerchant.getProfileId() < 1) {
                    formInfo.getProfileMerchants().remove(formInfo.getProfileMerchants().indexOf(filteredMerchant));
                    logger.info("Profile Merchants Exist. Remove merchants & add again");
                }
            }

            if (merchantForm.isSelectMerchant()) {
                profileMerchant.setMerchantId(merchantForm.getMerchantId());
                profileMerchant.setDefaultMerchant(merchantForm.isDefaultMerchant());
                profileMerchant.setProfMergId(0);
                profileMerchant.setProfileId(0);
                profileMerchant.setStatus(EventStatus.NEW);
                formInfo.getProfileMerchants().add(profileMerchant);
            }
            List<MerchantForm> merchantsList = new ArrayList<>(0);
            formInfo.getMerchants().forEach(m -> {
                if (m.getMerchantId().equals(merchantForm.getMerchantId())) {
                    m.setDefaultMerchant(merchantForm.isDefaultMerchant());
                    m.setSelectMerchant(merchantForm.isSelectMerchant());
                }
                merchantsList.add(m);
            });
            formInfo.setMerchants(merchantsList);

            long filterSaleMernt = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant()).count();

            long filterSaleDefaultMernt = formInfo.getMerchants().stream().filter(
                            pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                    .count();
            logger.info("#SaleMerchants> {}  | #SalesDefaultMerchants> {}", filterSaleMer, filterSaleDefaultMer);
            if (filterSaleMernt == 0) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_NO_SALE_MERCHANT);
                merchantError = EventMessage.ERROR_NO_SALE_MERCHANT;
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            /*
             * if (filterSaleMernt > 0 && filterSaleDefaultMernt == 0) {
             * model.addAttribute("message", EventMessage.ERROR_NO_SALE_DEFAULT_MERCHANT);
             * merchantError=EventMessage.ERROR_NO_SALE_DEFAULT_MERCHANT; return
             * Endpoint.PAGE_OPERATION_FAIL; }
             */
            /*
             * if (filterSaleMernt > 0 && filterSaleDefaultMernt > 1) {
             * model.addAttribute("message",
             * EventMessage.ERROR_MULTIPLE_SALE_DEFAULT_MERCHANTS);
             * merchantError=EventMessage.ERROR_MULTIPLE_SALE_DEFAULT_MERCHANTS; return
             * Endpoint.PAGE_OPERATION_FAIL; }
             */

            long filterAmexMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant()).count();

            long filterAmexDefaultMernt = formInfo.getMerchants().stream().filter(
                            pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                    .count();
            logger.info("filterAmexMer> {} | filterAmexDefaultMer> {} ", filterAmexMer, filterAmexDefaultMer);
            if (filterAmexMer > 0 && filterAmexDefaultMernt == 0) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT);
                merchantError = EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT;
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            /*
             * if (filterAmexMer > 0 && filterAmexDefaultMernt > 1) {
             * model.addAttribute("message",
             * EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS);
             * merchantError=EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS; return
             * Endpoint.PAGE_OPERATION_FAIL; }
             */

            return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_PROFILE;
        } else {
            model.addAttribute(MESSAGE, ErrorCode.ERROR_CODE_NOT_FOUND);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_PROFILE);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // modify-merchant-to-current-profile-cart
    @PostMapping(value = Endpoint.URL_MODIFY_MERCHANT_IN_PROFILE_CART)
    public String modifyMerchantToProfileCart(HttpServletRequest request,
                                              @ModelAttribute @Valid MerchantForm merchantForm, BindingResult result, Model model) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        // if error, redirect to current page
        if (result.hasErrors()) {
            return Endpoint.PAGE_MODIFY_NEW_PROFILE;
        }
        /*
         * if(!merchantForm.isSelectMerchant()) { logger.info("Merchant not selected");
         * model.addAttribute("message",
         * "Please select 'Add to Profile' option to add merchant into the profile");
         * model.addAttribute("back_link", Endpoint.URL_ADD_NEW_PROFILE); return
         * Endpoint.PAGE_OPERATION_FAIL; }
         */

        if ((merchantForm != null)) {
            FormInfo formInfo = SessionHelper.getFormInSession(request);
            ProfileMerchant profileMerchant = new ProfileMerchant();

            MerchantForm filterSaleMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.SALE)
                            && merchantForm.getType().equals(MerchantTypes.SALE)
                            && pm.getCurrency().equals(merchantForm.getCurrency()) && pm.isSelectMerchant())
                    .findAny().orElse(null);
            /*
             * if (!(filterSaleMer == null) && merchantForm.isSelectMerchant()) { if
             * (!filterSaleMer.getMerchantId().equals(merchantForm.getMerchantId())) {
             * logger.info("Merchant Already added | " + merchantForm.getType() + " |");
             * model.addAttribute("message",
             * "Can not Add more than one merchant for the same currency");
             * model.addAttribute("back_link", Endpoint.URL_MODIFY_SELECTED_PROFILE); return
             * Endpoint.PAGE_OPERATION_FAIL; }
             *
             * }
             */

            MerchantForm filterSaleDefaultMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.SALE)
                            && merchantForm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant()
                            && pm.isDefaultMerchant())
                    .findAny().orElse(null);
            if (!(filterSaleDefaultMer == null) && merchantForm.isDefaultMerchant()) {
                if (!filterSaleDefaultMer.getMerchantId().equals(merchantForm.getMerchantId())) {
                    logger.info("Merchant Already added as Default | {}", merchantForm.getType());
                    model.addAttribute(MESSAGE, "Cannot set more than one merchant as default merchant");
                    model.addAttribute(BACK_LINK, Endpoint.URL_MODIFY_SELECTED_PROFILE);
                    return Endpoint.PAGE_OPERATION_FAIL;
                }

            }

            MerchantForm filterAmexDefaultMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.AMEX)
                            && merchantForm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant()
                            && pm.isDefaultMerchant())
                    .findAny().orElse(null);
            if (!(filterAmexDefaultMer == null) && merchantForm.isDefaultMerchant()) {
                if (!filterAmexDefaultMer.getMerchantId().equals(merchantForm.getMerchantId())) {
                    logger.info("Merchant Already added | {}", merchantForm.getType());
                    model.addAttribute(MESSAGE, "Cannot set more than one merchant as default merchant");
                    model.addAttribute(BACK_LINK, Endpoint.URL_MODIFY_SELECTED_PROFILE);
                    return Endpoint.PAGE_OPERATION_FAIL;
                }
            }

            ProfileMerchant filteredMerchant = formInfo.getProfileMerchants().stream()
                    .filter(pm -> pm.getMerchantId().equals(merchantForm.getMerchantId())).findAny().orElse(null);
            if (filteredMerchant != null) {
                logger.info("Merchant Exist | {}", filteredMerchant.getMerchantId());
                logger.info("Profile Merchant Count | {}", formInfo.getProfileMerchants().size());
                formInfo.getProfileMerchants().remove(formInfo.getProfileMerchants().indexOf(filteredMerchant));
                logger.info("Merchant Removed | {}", merchantForm.getMerchantId());

            }
            logger.info("Profile Merchant Count | {}", formInfo.getProfileMerchants().size());
            if (merchantForm.isSelectMerchant()) {
                profileMerchant.setMerchantId(merchantForm.getMerchantId());
                profileMerchant.setDefaultMerchant(merchantForm.isDefaultMerchant());
                profileMerchant.setProfMergId(0);
                profileMerchant.setProfileId(0);
                profileMerchant.setStatus(EventStatus.NEW);
                formInfo.getProfileMerchants().add(profileMerchant);
            }

            List<MerchantForm> merchantsList = new ArrayList<>(0);
            formInfo.getMerchants().forEach(m -> {
                if (m.getMerchantId().equals(merchantForm.getMerchantId())) {
                    m.setDefaultMerchant(merchantForm.isDefaultMerchant());
                    m.setSelectMerchant(merchantForm.isSelectMerchant());
                }
                merchantsList.add(m);
            });
            formInfo.setMerchants(merchantsList);

            return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_SELECTED_PROFILE;
        } else {
            model.addAttribute(MESSAGE, ErrorCode.ERROR_CODE_NOT_FOUND);
            model.addAttribute(BACK_LINK, Endpoint.URL_MODIFY_SELECTED_PROFILE);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // add-merchant-to-current-profile-cart
    @PostMapping(value = Endpoint.URL_MERCHANT_TO_CURRENT_PROFILE_CART)
    public String addMerchantToCurrentProfileCart(HttpServletRequest request,
                                                  @ModelAttribute @Valid MerchantForm merchantForm, BindingResult result, Model model) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        // if error, redirect to current page
        if (result.hasErrors()) {
            return Endpoint.PAGE_ADD_ANOTHER_PROFILE;
        }

        /*
         * if(!merchantForm.isSelectMerchant()) { logger.info("Merchant not selected");
         * model.addAttribute("message",
         * "Please select 'Add to Profile' option to add merchant into the profile");
         * model.addAttribute("back_link", Endpoint.URL_ADD_NEW_PROFILE); return
         * Endpoint.PAGE_OPERATION_FAIL; }
         */

        logger.info("Add-merchant-to-current-profile-cart");

        if (!(merchantForm == null)) {
            FormInfo formInfo = SessionHelper.getFormInSession(request);
            ProfileMerchant profileMerchant = new ProfileMerchant();

            MerchantForm filterSaleMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.SALE)
                            && merchantForm.getType().equals(MerchantTypes.SALE)
                            && pm.getCurrency().equals(merchantForm.getCurrency()) && pm.isSelectMerchant())
                    .findAny().orElse(null);
            /*
             * if (!(filterSaleMer == null) && merchantForm.isSelectMerchant()) { if
             * (!filterSaleMer.getMerchantId().equals(merchantForm.getMerchantId())) {
             * logger.info("Merchant Already added | " + merchantForm.getType() + " |");
             * model.addAttribute("message",
             * "Can not Add more than one merchant for the same currency");
             * model.addAttribute("back_link", Endpoint.URL_ADD_NEW_PROFILE); return
             * Endpoint.PAGE_OPERATION_FAIL; }
             *
             * }
             */

            /*
             * MerchantForm filterSaleDefaultMer = formInfo.getMerchants().stream()
             * .filter(pm -> pm.getType().equals(MerchantTypes.SALE) &&
             * merchantForm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() &&
             * pm.isDefaultMerchant()) .findAny().orElse(null); if (!(filterSaleDefaultMer
             * == null) && merchantForm.isDefaultMerchant()) { if
             * (!filterSaleDefaultMer.getMerchantId().equals(merchantForm.getMerchantId()))
             * { logger.info("Merchant Already added as Default | " + merchantForm.getType()
             * + " |"); model.addAttribute("message",
             * "Cannot set more than one merchant as defualt merchant");
             * model.addAttribute("back_link", Endpoint.URL_ADD_NEW_PROFILE); return
             * Endpoint.PAGE_OPERATION_FAIL; }
             *
             * }
             *
             * MerchantForm filterAmexDefaultMer = formInfo.getMerchants().stream()
             * .filter(pm -> pm.getType().equals(MerchantTypes.AMEX) &&
             * merchantForm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant() &&
             * pm.isDefaultMerchant()) .findAny().orElse(null); if (!(filterAmexDefaultMer
             * == null) && merchantForm.isDefaultMerchant()) { if
             * (!filterAmexDefaultMer.getMerchantId().equals(merchantForm.getMerchantId()))
             * { logger.info("Merchant Already added | " + merchantForm.getType() + " |");
             * model.addAttribute("message",
             * "Cannot set more than one merchant as defualt merchant");
             * model.addAttribute("back_link", Endpoint.URL_ADD_NEW_PROFILE); return
             * Endpoint.PAGE_OPERATION_FAIL; } }
             */
            logger.info("Add-merchant-to-current-profile-cart: Get ProfileMerchants | {} ", formInfo.getProfileMerchants().size());
            ProfileMerchant filteredMerchant = formInfo.getProfileMerchants().stream()
                    .filter(pm -> pm.getMerchantId().equals(merchantForm.getMerchantId())).findAny().orElse(null);
            if (filteredMerchant != null) {
                if (filteredMerchant.getProfileId() < 1) {
                    formInfo.getProfileMerchants().remove(formInfo.getProfileMerchants().indexOf(filteredMerchant));
                    logger.info("Profile Merchants Exist. Remove merchants & add again");
                }

            }
            if (merchantForm.isSelectMerchant()) {
                profileMerchant.setMerchantId(merchantForm.getMerchantId());
                profileMerchant.setDefaultMerchant(merchantForm.isDefaultMerchant());
                profileMerchant.setProfMergId(0);
                profileMerchant.setProfileId(0);
                profileMerchant.setStatus(EventStatus.NEW);
                formInfo.getProfileMerchants().add(profileMerchant);
            }

            List<MerchantForm> merchantsList = new ArrayList<>(0);
            formInfo.getMerchants().forEach(m -> {
                if (m.getMerchantId().equals(merchantForm.getMerchantId())) {
                    m.setDefaultMerchant(merchantForm.isDefaultMerchant());
                    m.setSelectMerchant(merchantForm.isSelectMerchant());
                }
                merchantsList.add(m);
            });
            formInfo.setMerchants(merchantsList);

            return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_ANOTHER_PROFILE;
        } else {
            model.addAttribute(MESSAGE, ErrorCode.ERROR_CODE_NOT_FOUND);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_ANOTHER_PROFILE);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
    }

    // modify-merchant-to-profile-cart
    @PostMapping(value = Endpoint.URL_MODIFY_MERCHANT_TO_PROFILE_CART)
    public String modifyMerchantInProfileCart(HttpServletRequest request,
                                              @ModelAttribute @Valid MerchantForm merchantForm, BindingResult result, Model model) {

        // load login user
        LoginUserUtil.loadLoginUser(model);

        // if error, redirect to current page
        if (result.hasErrors()) {
            return Endpoint.PAGE_MODIFY_PROFILE;
        }

        if (merchantForm != null) {
            logger.info(merchantForm.getMerchantId().toString());
            FormInfo formInfo = SessionHelper.getFormInSession(request);
            ProfileMerchant profileMerchant = new ProfileMerchant();
            profileMerchant.setMerchantId(merchantForm.getMerchantId());
            profileMerchant.setDefaultMerchant(merchantForm.isDefaultMerchant());
            profileMerchant.setProfMergId(0);
            profileMerchant.setProfileId(merchantForm.getProfileId());
            profileMerchant.setStatus(EventStatus.NEW);

            // Remove merchant if available, from ProfileMerchants()
            ProfileMerchant filteredMerchant = formInfo.getProfileMerchants().stream()
                    .filter(pm -> pm.getMerchantId().equals(merchantForm.getMerchantId()) && pm.getProfileId().equals(merchantForm.getProfileId())).findAny().orElse(null);
            if (filteredMerchant != null) {
                logger.info("Filtered Profile Id: {} | MerchantForm Profile ID: {}", filteredMerchant.getProfileId(), merchantForm.getProfileId());
                if (filteredMerchant.getProfileId().equals(merchantForm.getProfileId())) {
                    formInfo.getProfileMerchants().remove(formInfo.getProfileMerchants().indexOf(filteredMerchant));
                }
            }
            if (merchantForm.isSelectMerchant()) {
                formInfo.getProfileMerchants().add(profileMerchant);
            }

            List<MerchantForm> merchantsList = new ArrayList<>(0);
            formInfo.getMerchants().forEach(m -> {
                if (m.getMerchantId().equals(merchantForm.getMerchantId())) {
                    m.setDefaultMerchant(merchantForm.isDefaultMerchant());
                    m.setSelectMerchant(merchantForm.isSelectMerchant());
                }
                merchantsList.add(m);
            });
            formInfo.setMerchants(merchantsList);

            long filterSaleMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant()).count();

            long filterSaleDefaultMer = formInfo.getMerchants().stream().filter(
                            pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                    .count();
            logger.info("#SaleMerchants> {} | #SalesDefaultMerchants> {} ", filterSaleMer, filterSaleDefaultMer);
            /*
             * if (filterSaleMer == 0 ) { model.addAttribute("message",
             * EventMessage.ERROR_NO_SALE_MERCHANT);
             * merchantError=EventMessage.ERROR_NO_SALE_MERCHANT; return
             * Endpoint.PAGE_OPERATION_FAIL; } if (filterSaleMer > 0 && filterSaleDefaultMer
             * == 0) { model.addAttribute("message",
             * EventMessage.ERROR_NO_SALE_DEFAULT_MERCHANT);
             * merchantError=EventMessage.ERROR_NO_SALE_DEFAULT_MERCHANT; return
             * Endpoint.PAGE_OPERATION_FAIL; }
             */
            /*
             * if (filterSaleMer > 0 && filterSaleDefaultMer > 1) {
             * model.addAttribute("message",
             * EventMessage.ERROR_MULTIPLE_SALE_DEFAULT_MERCHANTS);
             * merchantError=EventMessage.ERROR_MULTIPLE_SALE_DEFAULT_MERCHANTS; return
             * Endpoint.PAGE_OPERATION_FAIL; }
             */

            long filterAmexMer = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant()).count();

            long filterAmexDefaultMer = formInfo.getMerchants().stream().filter(
                            pm -> pm.getType().equals(MerchantTypes.AMEX) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                    .count();
            logger.info("filterAmexMer> {} | filterAmexDefaultMer> {}", filterAmexMer, filterAmexDefaultMer);
            /*
             * if (filterAmexMer > 0 && filterAmexDefaultMer == 0) {
             * model.addAttribute("message", EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT);
             * merchantError=EventMessage.ERROR_NO_AMEX_DEFAULT_MERCHANT; return
             * Endpoint.PAGE_OPERATION_FAIL; }
             */
            /*
             * if (filterAmexMer > 0 && filterAmexDefaultMer > 1) {
             * model.addAttribute("message",
             * EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS);
             * merchantError=EventMessage.ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS; return
             * Endpoint.PAGE_OPERATION_FAIL; }
             */

            return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_CURRENT_PROFILE;
        } else {
            model.addAttribute(MESSAGE, ErrorCode.ERROR_CODE_NOT_FOUND);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_PROFILE);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

    }


    // view profile page
    @GetMapping(value = Endpoint.URL_VIEW_PROFILE + "/{deviceId}/{profileId}")
    public String viewProfilePage(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId,
                                  @PathVariable("profileId") Integer profileId, Model model) throws Exception {

        logger.info("view profile page load by profileId");
        logger.info("Device ID: {} | Profile ID: {}", deviceId, profileId);
        SessionHelper.removeFormInSession(request);
        // load login user
        LoginUserUtil.loadLoginUser(model);
        // load data from db
        Device device = deviceService.findDeviceById(deviceId);
        List<Profile> profiles = device.getProfiles();

        Profile profile = profiles.stream().filter(subProfile -> profileId.equals(subProfile.getProfileId())).findAny()
                .orElse(null);

        List<MerchantForm> merchants = convertMerchantToMerchantForm(device);
        DeviceForm deviceForm = convertDeviceToDeviceForm(device);

        List<MerchantForm> merchantList = new ArrayList<MerchantForm>();
        profile.getProfileMerchants().forEach(pm -> {
            merchants.forEach(m -> {
                if (m.getMerchantId().equals(pm.getMerchantId())) {
                    m.setSelectMerchant(true);
                    m.setDefaultMerchant(pm.isDefaultMerchant());
                    merchantList.add(m);
                }
            });
        });
        model.addAttribute(MERCHANTS, merchantList);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(PROFILE_FORM, profile);

        return Endpoint.PAGE_VIEW_PROFILE;
    }

    // Modify profile page
    // modify-profile
    @GetMapping(value = Endpoint.URL_MODIFY_PROFILE + "/{deviceId}/{profileId}")
    public String modifyProfilePage(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId,
                                    @PathVariable("profileId") Integer profileId, Model model) throws Exception {
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        logger.info("modify profile page load by profileId");
        logger.info("Device ID: {} | Profile ID: {} ", deviceId, profileId);
        // load login user
        LoginUserUtil.loadLoginUser(model);
        // load data from db
        //Device device = deviceService.findDeviceById(deviceId);

        List<MerchantForm> merchants = clearMerchantList(formInfo.getMerchants());// convertMerchantToMerchantForm(device);
        DeviceForm deviceForm = formInfo.getDeviceForm();// convertDeviceToDeviceForm(device);
        List<ProfileForm> profileForm = formInfo.getProfiles();//  convertProfileToProfileForm(device);

        ProfileForm profile = profileForm.stream().filter(subProfile -> profileId.equals(subProfile.getProfileId()))
                .findAny().orElse(null);
        List<MerchantForm> merchantList = new ArrayList<MerchantForm>();
        logger.info("Selected Profile ID: {}", profile.getProfileId());
        if (profile.getProfileMerchants() != null) {
            logger.info("Profile Merchants: {}", profile.getProfileMerchants().size());
            logger.info("Merchants: {}", merchants.size());

            merchants.forEach(pm -> {
                profile.getProfileMerchants().forEach(m -> {
                    if (m.getMerchantId().equals(pm.getMerchantId())) {
                        pm.setSelectMerchant(true);
                        pm.setDefaultMerchant(m.isDefault());
                    }

                });
                merchantList.add(pm);
            });
            logger.info("merchantList: {}", merchantList.size());
            formInfo.setMerchants(merchantList);
            profile.setMerchants(merchantList);
        } else {
            merchants.forEach(pm -> {
                formInfo.getProfileMerchants().forEach(m -> {
                    if (m.getMerchantId().equals(pm.getMerchantId()) && m.getProfileId().equals(profileId)) {
                        pm.setSelectMerchant(true);
                        pm.setDefaultMerchant(m.isDefaultMerchant());
                    }

                });
                merchantList.add(pm);
            });

            formInfo.setMerchants(merchantList);
            profile.setMerchants(merchantList);
        }

        formInfo.setDiffSale(formInfo.getDeviceForm().isDiffSaleMidTid());

        logger.info("merchantList: {}", merchantList.size());

        formInfo.setProfileForm(profile);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(PROFILE_FORM, profile);
        model.addAttribute(DIFF_SALE, formInfo.isDiffSale());

        return Endpoint.PAGE_MODIFY_PROFILE;
    }

    // Modify profile page
    @GetMapping(value = Endpoint.URL_MODIFY_CURRENT_PROFILE)
    public String loadModifyProfilePage(HttpServletRequest request, Model model) throws Exception {
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        logger.info("modify profile page load by profileId");

        // load login user
        LoginUserUtil.loadLoginUser(model);

        List<MerchantForm> merchants = formInfo.getMerchants();
        DeviceForm deviceForm = formInfo.getDeviceForm();
        // List<ProfileForm> profileForm = formInfo.
        ProfileForm profile = formInfo.getProfileForm();

        //logger.info("Profile Merchants:" + profile.getProfileMerchants().size());
        logger.info("Merchants: {}", merchants.size());
        List<MerchantForm> merchantList = new ArrayList<MerchantForm>();
        merchants.forEach(pm -> {
            /*
             * profile.getProfileMerchants().forEach(m -> {
             *
             * });
             */
            merchantList.add(pm);
        });
        logger.info("merchantList: {}", merchantList.size());
        formInfo.setMerchants(merchantList);
        profile.setMerchants(merchantList);
        formInfo.setProfileForm(profile);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(PROFILE_FORM, profile);
        model.addAttribute(DIFF_SALE, formInfo.isDiffSale());

        return Endpoint.PAGE_MODIFY_PROFILE;
    }

    // Modify profile page
    @GetMapping(value = Endpoint.URL_MODIFY_PROFILE_IN_CART)
    public String loadModifyNewProfilePage(HttpServletRequest request, Model model) throws Exception {
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        logger.info("modify new profile page");

        // load login user
        LoginUserUtil.loadLoginUser(model);
        // load data from db

        // Device device = deviceService.findDeviceById(deviceId);

        List<MerchantForm> merchants = formInfo.getMerchants();
        DeviceForm deviceForm = formInfo.getDeviceForm();
        // List<ProfileForm> profileForm = formInfo.
        ProfileForm profile = formInfo.getProfileForm();

        logger.info("Merchants: {}", merchants.size());
        List<MerchantForm> merchantList = new ArrayList<MerchantForm>();
        merchants.forEach(pm -> {
            profile.getProfileMerchants().forEach(m -> {

            });
            merchantList.add(pm);
        });
        logger.info("#merchant List: {}", merchantList.size());
        formInfo.setMerchants(merchantList);
        profile.setMerchants(merchantList);
        formInfo.setProfileForm(profile);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(PROFILE_FORM, profile);

        return Endpoint.PAGE_MODIFY_NEW_PROFILE;
    }

    // view profile page
    @GetMapping(value = Endpoint.URL_REMOVE_PROFILE + "/{deviceId}/{profileId}")
    public String removeProfile(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId,
                                @PathVariable("profileId") Integer profileId, Model model) throws Exception {
        SessionHelper.removeFormInSession(request);
        LoginUserUtil.loadLoginUser(model);
        try {
            logger.info("Remove profile by profileId");
            logger.info("Device ID: {} | Profile ID: {}", deviceId, profileId);
            deviceService.deleteDeviceProfile(profileId);
            logger.info("Successfully Deleted");
        } catch (Exception e) {
            logger.error("Error Deleting profile:" + e);
            throw new GenericException(EventMessage.ERROR_MSG_FAIL);
        }
        model.addAttribute(MESSAGE, "Successfully Deleted");
        model.addAttribute(BACK_LINK, Endpoint.URL_VIEW_DEVICE + "/" + deviceId);
        return Endpoint.PAGE_OPERATION_SUCCESS;
    }

    // modify-profile
    @GetMapping(value = Endpoint.URL_MODIFY_NEW_PROFILE + "/{profileId}/{profileMode}")
    public String modifyNewProfilePage(HttpServletRequest request, @PathVariable("profileId") Integer profileId,
                                       @PathVariable("profileMode") String profileMode, Model model) throws Exception {

        logger.info(":::::::::::::: Modify New Profile :::::::::::::::::::::::");
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        logger.info("modify profile page load by profileId");
        logger.info("Profile ID: {}", profileId);
        // load login user
        LoginUserUtil.loadLoginUser(model);
        List<MerchantForm> merchants = formInfo.getMerchants();
        DeviceForm deviceForm = formInfo.getDeviceForm();
        List<ProfileForm> profileForm = formInfo.getProfiles();

        List<MerchantForm> tempMerchantsList = new ArrayList<>(0);
        formInfo.getMerchants().forEach(m -> {
            m.setDefaultMerchant(false);
            m.setSelectMerchant(false);
            tempMerchantsList.add(m);
        });
        formInfo.setMerchants(tempMerchantsList);

        ProfileForm profile = profileForm.stream().filter(subProfile -> profileId.equals(subProfile.getProfileId()))
                .findAny().orElse(null);
        logger.info("Merchants: {}", merchants.size());

        List<MerchantForm> merchantList = new ArrayList<MerchantForm>();
        merchants.forEach(pm -> {
            formInfo.getProfileMerchants().forEach(m -> {
                logger.info(" |Merchant ID: {} | ProfileMerchant ID: {} | Profile ID: {} ", pm.getMerchantId(), m.getMerchantId(), m.getProfileId());
                if (m.getMerchantId().equals(pm.getMerchantId()) && m.getProfileId().equals(profileId)) {
                    pm.setSelectMerchant(true);
                    pm.setDefaultMerchant(m.isDefaultMerchant());
                }

            });
            merchantList.add(pm);
        });
        logger.info("merchantList: {}", merchantList.size());
        formInfo.setMerchants(merchantList);
        profile.setMerchants(merchantList);
        formInfo.setProfileForm(profile);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(PROFILE_FORM, profile);
        if (!(profileMode == null) && profileMode.equalsIgnoreCase("D")) {
            formInfo.getDeviceForm().setMidTidSeg(true);
        } else if (!(profileMode == null) && profileMode.equalsIgnoreCase("C")) {
            formInfo.getDeviceForm().setBankName(BankTypes.COM_BANK);
            formInfo.getDeviceForm().setMidTidSeg(false);
        } else {
            formInfo.setDiffSale(false);
        }
        model.addAttribute(DIFF_SALE, formInfo.isDiffSale());

        return Endpoint.PAGE_MODIFY_NEW_PROFILE;
    }

    // modify-selected-profile
    @GetMapping(value = Endpoint.URL_MODIFY_SELECTED_PROFILE)
    public String modifySelectedProfilePage(HttpServletRequest request, Model model) throws Exception {
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        logger.info("modify profile page in Cart");

        // load login user
        LoginUserUtil.loadLoginUser(model);
        List<MerchantForm> merchants = formInfo.getMerchants();
        DeviceForm deviceForm = formInfo.getDeviceForm();
        ProfileForm profile = formInfo.getProfileForm();

        List<MerchantForm> merchantList = new ArrayList<MerchantForm>();
        merchants.forEach(pm -> {
            formInfo.getProfileMerchants().forEach(m -> {
                if (m.getMerchantId().equals(pm.getMerchantId()) && m.getProfileId().equals(profile.getProfileId())) {
                    pm.setSelectMerchant(true);
                    pm.setDefaultMerchant(m.isDefaultMerchant());
                }

            });
            merchantList.add(pm);
        });
        logger.info("merchantList: {}", merchantList.size());
        formInfo.setMerchants(merchantList);
        profile.setMerchants(merchantList);
        formInfo.setProfileForm(profile);
        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(PROFILE_FORM, profile);

        return Endpoint.PAGE_MODIFY_SELECTED_PROFILE;
    }

    private List<MerchantForm> clearMerchantList(List<MerchantForm> merchants) {
        List<MerchantForm> merchantList = new ArrayList<MerchantForm>();
        if (merchants != null) {

            merchants.forEach(pm -> {
                pm.setSelectMerchant(false);
                pm.setDefaultMerchant(false);

                merchantList.add(pm);
            });
        }
        return merchantList;
    }

    // BIN Block Functions
    // load add new bin rule page
    // new_bin_rule
    @GetMapping(value = Endpoint.URL_NEW_BIN_RULE)
    public String loadNewBinRulePage(HttpServletRequest request, Model model) {

        logger.info("load add bew BIN Rule page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        BinConfigForm binConfigForm = new BinConfigForm();

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        model.addAttribute(DEVICE_FORM, formInfo.getDeviceForm());
        model.addAttribute(MERCHANTS, formInfo.getMerchants());
        model.addAttribute(BIN_CONFIG_FORM, binConfigForm);

        return Endpoint.PAGE_NEW_BIN_RULE;
    }

    // modify new bin rule page
    @GetMapping(value = Endpoint.URL_MODIFY_NEW_BIN_RULE + "/{merchantId}")
    public String modifyNewBinsRulePage(HttpServletRequest request, @PathVariable("merchantId") Integer merchantId, Model model) {

        logger.info("load add bew BIN Rule page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        BinConfigForm binConfigForm = new BinConfigForm();

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        model.addAttribute(DEVICE_FORM, formInfo.getDeviceForm());
        model.addAttribute(MERCHANTS, formInfo.getMerchants());
        model.addAttribute(BIN_CONFIG_FORM, binConfigForm);
        model.addAttribute(MERCHANT_ID, merchantId);

        return Endpoint.PAGE_ADD_BIN_RULE_TO_MERCHANT;
    }

    // modify Current bin rule page
    @GetMapping(value = Endpoint.URL_MODIFY_CURRENT_BIN_RULE + "/{merchantId}")
    public String modifyCurrentBinsRulePage(HttpServletRequest request, @PathVariable("merchantId") Integer merchantId, Model model) {

        logger.info("load add bew BIN Rule page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        BinConfigForm binConfigForm = new BinConfigForm();

        binConfigForm.setMerchantId(merchantId);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        model.addAttribute(DEVICE_FORM, formInfo.getDeviceForm());
        model.addAttribute(MERCHANTS, formInfo.getMerchants());
        model.addAttribute(BIN_CONFIG_FORM, binConfigForm);
        model.addAttribute(MERCHANT_ID, merchantId);

        return Endpoint.PAGE_ADD_BIN_RULE_TO_CURRENT_MERCHANT;
    }

    // load add new bin rule (modify device - new merchant) page
    // new_bin_rule-modify device
    @GetMapping(value = Endpoint.URL_NEW_BIN_RULE_MODIFY_DEVICE)
    public String loadNewBinRuleModifyDevicePage(HttpServletRequest request, Model model) {

        logger.info("load add new BIN Rule to New Merchant-Modify Device page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        BinConfigForm binConfigForm = new BinConfigForm();

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        model.addAttribute(DEVICE_FORM, formInfo.getDeviceForm());
        model.addAttribute(MERCHANTS, formInfo.getMerchants());
        model.addAttribute(BIN_CONFIG_FORM, binConfigForm);

        return Endpoint.PAGE_NEW_BIN_RULE_MODIFY_DEVICE;
    }

    // load add new bin rule page
    @PostMapping(value = "/admin-portal/new_rule_add_to_cart")
    public String addNewBinRuleToCart(HttpServletRequest request, @ModelAttribute @Valid BinConfigForm binConfigForm,
                                      BindingResult result, Model model) {

        logger.info("load add bew BIN Rule page");
        // load login user
        LoginUserUtil.loadLoginUser(model);


        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        MerchantForm merchantForm = new MerchantForm();
        CommonDataForm commonDataForm = new CommonDataForm();

        /*
         * if(binConfigForm!=null & true) { logger.info("INVALID RULE CONFIGURATION");
         * model.addAttribute("message", ErrorCode.ERROR_CODE_NOT_FOUND);
         * model.addAttribute("back_link", Endpoint.URL_ADD_NEW_MERCHANT); return
         * Endpoint.PAGE_OPERATION_FAIL; }
         */

        if (binConfigForm != null) {
            if (binConfigForm.getType().contains("SINGLE")) {
                if ((binConfigForm.getBinStartFrom() == null) || !StringUtils.isNumeric(binConfigForm.getBinStartFrom()) || (binConfigForm.getBinStartFrom().isEmpty())) {
                    logger.info("INVALID BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_FORMAT);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if ((binConfigForm.getBinStartFrom().length() < 6)) {
                    logger.info("INVALID BIN LENGTH");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_LENGTH);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else {
                    logger.info("VALID BIN CONFIG-SINGLE");

                }


            } else if (binConfigForm.getType().contains("MULTIPLE")) {
                if ((binConfigForm.getBinStartFrom() == null) || (binConfigForm.getBinEndFrom() == null) ||
                        !StringUtils.isNumeric(binConfigForm.getBinStartFrom()) || (binConfigForm.getBinStartFrom().isEmpty()) ||
                        !StringUtils.isNumeric(binConfigForm.getBinEndFrom()) || (binConfigForm.getBinEndFrom().isEmpty())) {
                    logger.info("INVALID BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_FORMAT);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if ((binConfigForm.getBinStartFrom().length() < 6) || (binConfigForm.getBinEndFrom().length() < 6)) {
                    logger.info("INVALID BIN LENGTH");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_LENGTH);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if (Integer.parseInt(binConfigForm.getBinEndFrom()) < Integer.parseInt(binConfigForm.getBinStartFrom())) {
                    logger.info("Start BIN > End BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_RANGE);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else {
                    logger.info("VALID BIN CONFIG-MULTIPLE");

                }

            } else if (binConfigForm.getType().contains("FULL_RANGE")) {
                binConfigForm.setBinStartFrom("");
                binConfigForm.setBinEndFrom("");
                logger.info("VALID BIN CONFIG-FULL-RANGE");
            } else {
                logger.info("Invalid Bin Rule. ");
                model.addAttribute(MESSAGE, ErrorCode.SYSTEM_ERROR);
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            //validate bin rules
            List<String> isValidBinRule = isValidateBinRule(request, binConfigForm);
            if (isValidBinRule.get(0).equals(CommonConstant.TRUE)) {
                logger.info("VALID BIN RULE: Accepted");
                // add merchant to cart
                addBinToCart(request, binConfigForm);
                commonDataForm.setTid(binConfigForm.getTid());
                commonDataForm.setMid(binConfigForm.getMid());
                formInfo.setCommonDataForm(commonDataForm);

                logger.info("NEW BIN RULE ADDED");

            } else {

                logger.info("INVALID BIN RULE: Rejected");
                model.addAttribute(MESSAGE, isValidBinRule.get(1) + " " + ErrorCode.ERROR_BIN_RULE_AVAILABLE);
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }

        } else {
            logger.info("INVALID RULE CONFIGURATION");
            model.addAttribute(MESSAGE, ErrorCode.ERROR_CODE_NOT_FOUND);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        deviceForm.setSerialNo(selectedMerchant);

        logger.info("add new merchant link (selectedMerchant): {}", formInfo.getDeviceForm().getVisaNoCvmLimit());


        // merchantForm.setAmexIp(CommonConstant.DEFAULT_AMEX_IP);

        /*
         * model.addAttribute("deviceForm", deviceForm);
         * model.addAttribute("merchantForm", merchantForm);
         * model.addAttribute("merchantTypes", merchantTypeService.getMerchantTypes());
         * model.addAttribute("currencies", currencyService.getCurrencies());
         * model.addAttribute("binrules", formInfo.getMerchantBin());
         */

        return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_MERCHANT;
        //return Endpoint.PAGE_ADD_NEW_MERCHANT;
    }

    // load add new bin rule page
    @PostMapping(value = "/admin-portal/modify-device-new_rule_add_to_cart")
    public String addNewBinRuleToModifyDeviceCart(HttpServletRequest request, @ModelAttribute @Valid BinConfigForm binConfigForm,
                                                  BindingResult result, Model model) {

        logger.info("New merchant (Modify device Page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        CommonDataForm commonDataForm = new CommonDataForm();

        if (binConfigForm != null) {
            // add merchant to cart
            if (binConfigForm.getType().contains("SINGLE")) {
                if ((binConfigForm.getBinStartFrom() == null) || !StringUtils.isNumeric(binConfigForm.getBinStartFrom()) || (binConfigForm.getBinStartFrom().isEmpty())) {
                    logger.info("INVALID BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_FORMAT);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if ((binConfigForm.getBinStartFrom().length() < 6)) {
                    logger.info("INVALID BIN LENGTH");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_LENGTH);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else {
                    logger.info("VALID BIN CONFIG-SINGLE");
                }

            } else if (binConfigForm.getType().contains("MULTIPLE")) {
                if ((binConfigForm.getBinStartFrom() == null) || (binConfigForm.getBinEndFrom() == null) ||
                        !StringUtils.isNumeric(binConfigForm.getBinStartFrom()) || (binConfigForm.getBinStartFrom().isEmpty()) ||
                        !StringUtils.isNumeric(binConfigForm.getBinEndFrom()) || (binConfigForm.getBinEndFrom().isEmpty())) {
                    logger.info("INVALID BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_FORMAT);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if ((binConfigForm.getBinStartFrom().length() < 6) || (binConfigForm.getBinEndFrom().length() < 6)) {
                    logger.info("INVALID BIN LENGTH");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_LENGTH);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if (Integer.parseInt(binConfigForm.getBinEndFrom()) < Integer.parseInt(binConfigForm.getBinStartFrom())) {
                    logger.info("Start BIN > End BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_RANGE);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else {
                    logger.info("VALID BIN CONFIG-MULTIPLE");
                }

            } else if (binConfigForm.getType().contains("FULL_RANGE")) {
                binConfigForm.setBinStartFrom("");
                binConfigForm.setBinEndFrom("");
                logger.info("VALID BIN CONFIG-FULL-RANGE");
            } else {
                logger.info("Invalid Bin Rule. ");
                model.addAttribute(MESSAGE, ErrorCode.SYSTEM_ERROR);
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            //validate bin rules
            List<String> isValidBinRule = isValidateBinRule(request, binConfigForm);
            if (isValidBinRule.get(0).equals(CommonConstant.TRUE)) {
                logger.info("VALID BIN RULE: Accepted");
                // add merchant to cart
                addBinToCart(request, binConfigForm);
                commonDataForm.setTid(binConfigForm.getTid());
                commonDataForm.setMid(binConfigForm.getMid());
                formInfo.setCommonDataForm(commonDataForm);

                logger.info("NEW BIN RULE ADDED");

            } else {

                logger.info("INVALID BIN RULE: Rejected");
                model.addAttribute(MESSAGE, isValidBinRule.get(1) + " " + ErrorCode.ERROR_BIN_RULE_AVAILABLE);
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }

        } else {
            logger.info("INVALID RULE CONFIGURATION");
            model.addAttribute(MESSAGE, ErrorCode.ERROR_CODE_NOT_FOUND);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_ANOTHER_MERCHANT;
        //return Endpoint.PAGE_ADD_NEW_MERCHANT;
    }

    // modify new bin rule page
    @PostMapping(value = "/admin-portal/new_rule_modify_to_cart")
    public String modifyNewBinRuleToCart(HttpServletRequest request, @ModelAttribute @Valid BinConfigForm binConfigForm,
                                         BindingResult result, Model model) {

        logger.info("load add bew BIN Rule page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        MerchantForm merchantForm = new MerchantForm();
        CommonDataForm commonDataForm = new CommonDataForm();

        if (binConfigForm != null) {
            // add merchant to cart
            if (binConfigForm.getType().contains("SINGLE")) {
                if ((binConfigForm.getBinStartFrom() == null) || !StringUtils.isNumeric(binConfigForm.getBinStartFrom()) || (binConfigForm.getBinStartFrom().isEmpty())) {
                    logger.info("INVALID BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_FORMAT);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if ((binConfigForm.getBinStartFrom().length() < 6)) {
                    logger.info("INVALID BIN LENGTH");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_LENGTH);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else {
                    logger.info("VALID BIN CONFIG-SINGLE");

                }

            } else if (binConfigForm.getType().contains("MULTIPLE")) {
                if ((binConfigForm.getBinStartFrom() == null) || (binConfigForm.getBinEndFrom() == null) ||
                        !StringUtils.isNumeric(binConfigForm.getBinStartFrom()) || (binConfigForm.getBinStartFrom().isEmpty()) ||
                        !StringUtils.isNumeric(binConfigForm.getBinEndFrom()) || (binConfigForm.getBinEndFrom().isEmpty())) {
                    logger.info("INVALID BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_FORMAT);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if ((binConfigForm.getBinStartFrom().length() < 6) || (binConfigForm.getBinEndFrom().length() < 6)) {
                    logger.info("INVALID BIN LENGTH");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_LENGTH);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if (Integer.parseInt(binConfigForm.getBinEndFrom()) < Integer.parseInt(binConfigForm.getBinStartFrom())) {
                    logger.info("Start BIN > End BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_RANGE);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else {
                    logger.info("VALID BIN CONFIG-MULTIPLE");
                }

            } else if (binConfigForm.getType().contains("FULL_RANGE")) {
                binConfigForm.setBinStartFrom("");
                binConfigForm.setBinEndFrom("");
                logger.info("VALID BIN CONFIG-FULL-RANGE");
            } else {
                logger.info("Invalid Bin Rule. ");
                model.addAttribute(MESSAGE, ErrorCode.SYSTEM_ERROR);
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            //validate bin rules
            List<String> isValidBinRule = isValidateBinRule(request, binConfigForm);
            if (isValidBinRule.get(0).equals(CommonConstant.TRUE)) {
                logger.info("VALID BIN RULE: Accepted");
                // add merchant to cart
                addBinToCart(request, binConfigForm);
                commonDataForm.setTid(binConfigForm.getTid());
                commonDataForm.setMid(binConfigForm.getMid());
                formInfo.setCommonDataForm(commonDataForm);

                logger.info("NEW BIN RULE ADDED");

            } else {

                logger.info("INVALID BIN RULE: Rejected");
                model.addAttribute(MESSAGE, isValidBinRule.get(1) + " " + ErrorCode.ERROR_BIN_RULE_AVAILABLE);
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }

        } else {
            logger.info("INVALID RULE CONFIGURATION");
            model.addAttribute(MESSAGE, ErrorCode.ERROR_CODE_NOT_FOUND);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        deviceForm.setSerialNo(selectedMerchant);

        logger.info("add new merchant link (selectedMerchant): {}", formInfo.getDeviceForm().getVisaNoCvmLimit());

        // merchantForm.setAmexIp(CommonConstant.DEFAULT_AMEX_IP);

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

        //return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_MERCHANT;
        return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_NEW_MERCHANT;
    }

    // modify current bin rule page
    @PostMapping(value = "/admin-portal/new_rule_modify_to_current_cart")
    public String modifyNewBinRuleToCurrentCart(HttpServletRequest request, @ModelAttribute @Valid BinConfigForm binConfigForm,
                                                BindingResult result, Model model) {

        logger.info("load add new BIN Rule page");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        MerchantForm merchantForm = new MerchantForm();
        CommonDataForm commonDataForm = new CommonDataForm();

        if (binConfigForm != null) {
            // add merchant to cart
            if (binConfigForm.getType().contains("SINGLE")) {
                if ((binConfigForm.getBinStartFrom() == null) || !StringUtils.isNumeric(binConfigForm.getBinStartFrom()) || (binConfigForm.getBinStartFrom().isEmpty())) {
                    logger.info("INVALID BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_FORMAT);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if ((binConfigForm.getBinStartFrom().length() < 6)) {
                    logger.info("INVALID BIN LENGTH");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_LENGTH);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else {
                    logger.info("VALID BIN CONFIG-SINGLE");
                }

            } else if (binConfigForm.getType().contains("MULTIPLE")) {
                if ((binConfigForm.getBinStartFrom() == null) || (binConfigForm.getBinEndFrom() == null) ||
                        !StringUtils.isNumeric(binConfigForm.getBinStartFrom()) || (binConfigForm.getBinStartFrom().isEmpty()) ||
                        !StringUtils.isNumeric(binConfigForm.getBinEndFrom()) || (binConfigForm.getBinEndFrom().isEmpty())) {
                    logger.info("INVALID BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_FORMAT);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if ((binConfigForm.getBinStartFrom().length() < 6) || (binConfigForm.getBinEndFrom().length() < 6)) {
                    logger.info("INVALID BIN LENGTH");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_LENGTH);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else if (Integer.parseInt(binConfigForm.getBinEndFrom()) < Integer.parseInt(binConfigForm.getBinStartFrom())) {
                    logger.info("Start BIN > End BIN");
                    model.addAttribute(MESSAGE, ErrorCode.ERROR_INVALID_BIN_RANGE);
                    model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                    return Endpoint.PAGE_OPERATION_FAIL;
                } else {
                    logger.info("VALID BIN CONFIG-MULTIPLE");
                    logger.info("{} > {}", Integer.parseInt(binConfigForm.getBinEndFrom()), Integer.parseInt(binConfigForm.getBinStartFrom()));

                }

            } else if (binConfigForm.getType().contains("FULL_RANGE")) {
                binConfigForm.setBinStartFrom("");
                binConfigForm.setBinEndFrom("");
                logger.info("VALID BIN CONFIG-FULL-RANGE");
            } else {
                logger.info("Invalid Bin Rule. ");
                model.addAttribute(MESSAGE, ErrorCode.SYSTEM_ERROR);
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            //validate bin rules
            List<String> isValidBinRule = isValidateBinRule(request, binConfigForm);
            if (isValidBinRule.get(0).equals(CommonConstant.TRUE)) {
                logger.info("VALID BIN RULE: Accepted");
                // add merchant to cart
                addBinToCart(request, binConfigForm);
                commonDataForm.setTid(binConfigForm.getTid());
                commonDataForm.setMid(binConfigForm.getMid());
                formInfo.setCommonDataForm(commonDataForm);

                logger.info("NEW BIN RULE ADDED");

            } else {

                logger.info("INVALID BIN RULE: Rejected");
                model.addAttribute(MESSAGE, isValidBinRule.get(1) + " " + ErrorCode.ERROR_BIN_RULE_AVAILABLE);
                model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
                return Endpoint.PAGE_OPERATION_FAIL;
            }

        } else {
            logger.info("INVALID RULE CONFIGURATION");
            model.addAttribute(MESSAGE, ErrorCode.ERROR_CODE_NOT_FOUND);
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

        //return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_MERCHANT;
        return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_CURRENT_MERCHANT + "/" + formInfo.getSelectedMerchantID(); //Endpoint.PAGE_MODIFY_NEW_MERCHANT;
    }

    // Bin Block add to cart
    private void addBinToCart(HttpServletRequest request, BinConfigForm binConfigForm) {

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getMerchantBin() != null)
            logger.info("ADD: Before Cart Size: {}", formInfo.getMerchantBin().size());
        logger.info("Rules for merchant: {}", binConfigForm.getMerchantId());

        // to be insert merchant
        binConfigForm.setFormAction(ActionType.INSERT);
        binConfigForm.setFormId(deviceService.getMaxSequence());
        // add merchant to cart
        formInfo.getMerchantBin().add(binConfigForm);

        if (formInfo.getMerchantBin() != null)
            logger.info("After Cart Size: {}", formInfo.getMerchantBin().size());
    }

    // Bin Block validate BIN Rule

    private List<String> isValidateBinRule(HttpServletRequest request, BinConfigForm binConfigForm) {


        logger.info("::::::::::::Validating new BIN Rule >>>>>>>>>>>");
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        List<String> res = new ArrayList<String>();  //[0] - Status , [1] - Description

        isBinValid = true;
        res.add(0, CommonConstant.TRUE);
        res.add(1, CommonConstant.VALID);
        newEndBin = "";
        extEndBin = "";


        if (formInfo.getMerchantBin() != null) {

            formInfo.getMerchantBin().forEach(mb -> {
                logger.info("New Rule:>> Card Type: {} | Trans Type: {} | Type: {} | SBIN: {} | EBIN: {}", binConfigForm.getCardType(), binConfigForm.getTransType(), binConfigForm.getType(), binConfigForm.getBinStartFrom(), binConfigForm.getBinEndFrom());
                logger.info("Ext Rule:>> Card Type: {} | Trans Type: {} | Type: {} | SBIN: {} | EBIN: {}", mb.getCardType(), mb.getTransType(), mb.getType(), mb.getBinStartFrom(), mb.getBinEndFrom());
                if (mb.getCardType().equals(binConfigForm.getCardType()) && mb.getTid().equalsIgnoreCase(binConfigForm.getTid()) && (mb.getTransType().equals(binConfigForm.getTransType()) || mb.getTransType().equals("ALL") || binConfigForm.getTransType().equals("ALL"))) {
                    if ((binConfigForm.getType().equals("SINGLE"))) {
                        newEndBin = binConfigForm.getBinStartFrom();
                    } else
                        newEndBin = binConfigForm.getBinEndFrom();

                    if ((mb.getType().equals("SINGLE"))) {
                        extEndBin = mb.getBinStartFrom();
                    } else
                        extEndBin = mb.getBinEndFrom();

                    logger.info("newEndBin: {} | extEndBin: {}", newEndBin, extEndBin);

                    if (mb.getType().equals("FULL_RANGE") && (binConfigForm.getType().equals("FULL_RANGE"))) {
                        res.add(0, CommonConstant.FALSE);
                        res.add(1, mb.getAction() + " (" + mb.getType() + " Type)");
                        return;
                    } else if (mb.getType().equals("FULL_RANGE") && (!binConfigForm.getType().equals("FULL_RANGE"))) {
                        res.add(0, CommonConstant.FALSE);
                        res.add(1, mb.getAction() + " (" + mb.getType() + " Type)");
                        return;
                    } else if (!mb.getType().equals("FULL_RANGE") && (binConfigForm.getType().equals("FULL_RANGE"))) {
                        res.add(0, CommonConstant.FALSE);
                        res.add(1, mb.getAction() + " (" + mb.getType() + " Type)");
                        return;
                    } else {
                        logger.info((Integer.parseInt(mb.getBinStartFrom()) + " <= " + Integer.parseInt(binConfigForm.getBinStartFrom()) + " && " + Integer.parseInt(binConfigForm.getBinStartFrom()) + " <= " + Integer.parseInt(extEndBin)));
                        logger.info((Integer.parseInt(mb.getBinStartFrom()) + " <= " + Integer.parseInt(newEndBin) + " && " + Integer.parseInt(newEndBin) + " <= " + Integer.parseInt(extEndBin)));

                        if (Integer.parseInt(mb.getBinStartFrom()) > Integer.parseInt(binConfigForm.getBinStartFrom()) || Integer.parseInt(binConfigForm.getBinStartFrom()) > Integer.parseInt(this.extEndBin)) {
                            if (Integer.parseInt(mb.getBinStartFrom()) <= Integer.parseInt(this.newEndBin) && Integer.parseInt(this.newEndBin) <= Integer.parseInt(this.extEndBin)) {
                                res.add(0, CommonConstant.FALSE);
                                res.add(1, mb.getAction());
                                return;
                            }
                            return;
                        }
                    }
                } else {
                    return;
                }
                res.add(0, CommonConstant.FALSE);
                res.add(1, mb.getAction());
            });

        }
        logger.info("Valid BIN Rule: {}", res.get(0));
        return res;
    }

    // BIN setup delete from cart
    private void deleteBinConfigFromCart(HttpServletRequest request, Integer ruleId) {

        logger.info("Bin Rule Id: {}", ruleId);
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getMerchantBin() != null)
            logger.info("Before Cart Size: {}", formInfo.getMerchantBin().size());
        if (formInfo.getMerchantBin() != null && !formInfo.getMerchantBin().isEmpty()) {

            Iterator<BinConfigForm> iterator = formInfo.getMerchantBin().iterator();
            while (iterator.hasNext()) {
                BinConfigForm existingRule = iterator.next();
                if (CustomValidationUtil.isSameId(existingRule.getFormId(), ruleId)) {

                    iterator.remove();
                }
            }
        }

        if (formInfo.getMerchantBin() != null)
            logger.info("After Cart Size: {}", formInfo.getMerchantBin().size());

        logger.info("BIN Rule Count: {}", formInfo.getMerchantBin().size());
    }

    // delete BIN Rule from cart (register device)
    @GetMapping(value = Endpoint.URL_DELETE_BIN_RULE + "/{ruleId}")
    public String binRuleDeleteCart(HttpServletRequest request, @PathVariable("ruleId") Integer ruleId,
                                    Model model) {

        logger.info("delete rule from cart (register device)");
        logger.info("Request [Rule Id]: {}", ruleId);

        // load login user
        LoginUserUtil.loadLoginUser(model);

        deleteBinConfigFromCart(request, ruleId);
        logger.info("BIN RULE DELETED");

        return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_MERCHANT;
    }

    // delete BIN Rule from cart (Modify device)
    @GetMapping(value = Endpoint.URL_DELETE_CURRENT_BIN_RULE + "/{ruleId}")
    public String currentBinRuleDeleteCart(HttpServletRequest request, @PathVariable("ruleId") Integer ruleId,
                                           Model model) {

        logger.info("delete rule from cart (register device)");
        logger.info("Request [Rule Id]: {}", ruleId);

        // load login user
        LoginUserUtil.loadLoginUser(model);

        deleteBinConfigFromCart(request, ruleId);
        logger.info("BIN RULE DELETED");

        return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_CURRENT_MERCHANT;
    }

    // delete BIN Rule from cart (Modify device)
    @GetMapping(value = Endpoint.URL_DELETE_NEW_BIN_RULE_MODIFY_DEVICE + "/{ruleId}")
    public String currentBinRuleDeleteInModifyDeviceCart(HttpServletRequest request, @PathVariable("ruleId") Integer ruleId,
                                                         Model model) {

        logger.info("delete rule from cart (modify device)");
        logger.info("Request [Rule Id]: {}", ruleId);

        // load login user
        LoginUserUtil.loadLoginUser(model);

        deleteBinConfigFromCart(request, ruleId);
        logger.info("BIN RULE DELETED");

        return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_ANOTHER_MERCHANT;
    }

    private List<Profile> convertProfileFormToProfile(FormInfo formInfo) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usr = userDetails.getUsername();
        List<Profile> profileList = new ArrayList<Profile>();

        formInfo.getProfiles().forEach(profileForm -> {
            Profile profile = new Profile();
            profile.setAmexCnt(profileForm.isAmexCnt());
            profile.setAmexCntls(profileForm.isAmexCntls());
            profile.setAmexCntslTrxn(profileForm.getAmexCntslTrxn());
            profile.setAmexNoCvm(profileForm.getAmexNoCvm());
            profile.setDefault(profileForm.isDefault());
            profile.setJcbCnt(profileForm.isJcbCnt());
            profile.setJcbCntls(profileForm.isJcbCntls());
            profile.setJcbCntslTrxn(profileForm.getJcbCntslTrxn());
            profile.setJcbNoCvm(profileForm.getJcbNoCvm());
            profile.setMcCnt(profileForm.isMcCnt());
            profile.setMcCntls(profileForm.isMcCntls());
            profile.setMcCntslTrxn(profileForm.getMcCntslTrxn());
            profile.setMcNoCvm(profileForm.getMcNoCvm());
            profile.setMerchantAdrs(profileForm.getMerchantAdrs());
            profile.setMerchantName(profileForm.getMerchantName());
            profile.setProfileId(profileForm.getProfileId());
            profile.setProfileName(profileForm.getProfileName());
            profile.setStatus(EventStatus.NEW);
            profile.setUpayCnt(profileForm.isUpayCnt());
            profile.setUpayCntls(profileForm.isUpayCntls());
            profile.setUpayCntslTrxn(profileForm.getUpayCntslTrxn());
            profile.setUpayNoCvm(profileForm.getVisaNoCvm());
            profile.setVisaCnt(profileForm.isVisaCnt());
            profile.setVisaCntls(profileForm.isVisaCntls());
            profile.setVisaCntslTrxn(profileForm.getVisaCntslTrxn());
            profile.setVisaNoCvm(profileForm.getVisaNoCvm());
            profile.setAction(profileForm.getAction() == null ? ActionType.UPDATE : profileForm.getAction());
            profile.setAddedBy(usr);
            profile.setUpdatedBy(usr);
            profile.setCustomerCopy(profileForm.isCustomerCopy());
            profile.setTlsEnable(profileForm.isTlsEnable());

            formInfo.getProfileMerchants().forEach(profileMerchant -> {
                if (profileForm.getProfileId().equals(profileMerchant.getProfileId())) {
                    if (profile.getProfileMerchants() != null) {
                        profile.getProfileMerchants().add(profileMerchant);
                    } else {
                        List<ProfileMerchant> pMerchants = new ArrayList<ProfileMerchant>(0);
                        pMerchants.add(profileMerchant);
                        profile.setProfileMerchants(pMerchants);
                    }
                }
            });
            profileList.add(profile);

        });

        return profileList;
    }

    private List<ProfileForm> convertProfileToProfileForm(Device device) {
        List<ProfileForm> profileList = new ArrayList<ProfileForm>();
        if (device.getProfiles() != null) {
            device.getProfiles().forEach(profile -> {
                ProfileForm profileForm = new ProfileForm();
                profileForm.setAmexCnt(profile.isAmexCnt());
                profileForm.setAmexCntls(profile.isAmexCntls());
                profileForm.setAmexCntslTrxn(profile.getAmexCntslTrxn());
                profileForm.setAmexNoCvm(profile.getAmexNoCvm());
                profileForm.setDefault(profile.isDefault());
                profileForm.setJcbCnt(profile.isJcbCnt());
                profileForm.setJcbCntls(profile.isJcbCntls());
                profileForm.setJcbCntslTrxn(profile.getJcbCntslTrxn());
                profileForm.setJcbNoCvm(profile.getJcbNoCvm());
                profileForm.setMcCnt(profile.isMcCnt());
                profileForm.setMcCntls(profile.isMcCntls());
                profileForm.setMcCntslTrxn(profile.getMcCntslTrxn());
                profileForm.setMcNoCvm(profile.getMcNoCvm());
                profileForm.setMerchantAdrs(profile.getMerchantAdrs());
                profileForm.setMerchantName(profile.getMerchantName());
                profileForm.setProfileId(profile.getProfileId());
                profileForm.setProfileName(profile.getProfileName());
                profileForm.setStatus(profile.getStatus());
                profileForm.setUpayCnt(profile.isUpayCnt());
                profileForm.setUpayCntls(profile.isUpayCntls());
                profileForm.setUpayCntslTrxn(profile.getUpayCntslTrxn());
                profileForm.setUpayNoCvm(profile.getVisaNoCvm());
                profileForm.setVisaCnt(profile.isVisaCnt());
                profileForm.setVisaCntls(profile.isVisaCntls());
                profileForm.setVisaCntslTrxn(profile.getVisaCntslTrxn());
                profileForm.setVisaNoCvm(profile.getVisaNoCvm());
                profileForm.setCustomerCopy(profile.isCustomerCopy());
                profileForm.setTlsEnable(profile.isTlsEnable());

                List<ProfileMerchantForm> profileMerchants = new ArrayList<ProfileMerchantForm>(0);

                if (profile.getProfileMerchants() != null) {
                    profile.getProfileMerchants().forEach(profileMerchant -> {
                        ProfileMerchantForm profileMerchantForm = new ProfileMerchantForm();
                        profileMerchantForm.setAddedBy(profileMerchant.getAddedBy());
                        profileMerchantForm.setAddedDate(profileMerchant.getAddedDate());
                        profileMerchantForm.setDefault(profileMerchant.isDefaultMerchant());
                        profileMerchantForm.setLastUpdate(profileMerchant.getLastUpdate());
                        profileMerchantForm.setMerchantId(profileMerchant.getMerchantId());
                        profileMerchantForm.setProfileId(profileMerchant.getProfileId());
                        profileMerchantForm.setProfMergId(profileMerchant.getProfMergId());
                        profileMerchantForm.setStatus(profileMerchant.getStatus());
                        profileMerchantForm.setUpdatedBy(profileMerchant.getUpdatedBy());
                        // profileMerchants.add();

                        profileMerchants.add(profileMerchantForm);
                        logger.info("Convert-Profile-To-ProfileForm: profile  ID| {} MerchantID| {} ", profile.getProfileId(), profileMerchant.getMerchantId());

                    });
                    profileForm.setProfileMerchants(profileMerchants);
                }
                profileList.add(profileForm);

            });
        }
        return profileList;
    }

    private DeviceForm convertDeviceToDeviceForm(Device device) throws JsonProcessingException {

        DeviceForm deviceForm = new DeviceForm();
        deviceForm.setDeviceId(device.getDeviceId());
        deviceForm.setSerialNo(device.getSerialNo());
        deviceForm.setBankCode(device.getBankName()); // should replace with code in the future
        deviceForm.setBankName(device.getBankName());
        deviceForm.setMerchantName(device.getMerchantName());
        deviceForm.setMerchantAddress(device.getMerchantAddress());
        deviceForm.setVisaNoCvmLimit(device.getVisaNoCvmLimit());
        deviceForm.setCntactlsTrxnLimit(device.getCntactlsTrxnLimit());
        deviceForm.setAutoSettle(device.isAutoSettle());
        deviceForm.setAutoSettleTime(device.getAutoSettleTime());
        deviceForm.setForceSettle(device.isForceSettle());
        deviceForm.setEcr(device.isEcr());
        deviceForm.setKeyIn(device.isKeyIn());
        deviceForm.setMobileNo(device.getMobileNo());
        deviceForm.setSimNo(device.getSimNo());
        deviceForm.setCustContactNo(device.getCustContactNo());
        deviceForm.setRemark(device.getRemark());
        deviceForm.setEcrQr(device.isEcrQr());
        deviceForm.setSignature(device.isSignature());
        deviceForm.setDebugMode(device.isDebugMode());
        deviceForm.setNoSettle(device.isNoSettle());
        deviceForm.setActivityTracker(device.isActivityTracker());
        deviceForm.setQrRefund(device.isQrRefund());
        deviceForm.setReversalHistory(device.isReversalHistory());
        deviceForm.setEnableAmex(device.isEnableAmex());
        deviceForm.setPushNotification(device.isPushNotification());
        deviceForm.setPreAuth(device.isPreAuth());
        deviceForm.setDcc(device.isDcc());
        deviceForm.setOffline(device.isOffline());
        deviceForm.setKeyInAmex(device.isKeyInAmex());
        deviceForm.setPopupMsg(device.isPopupMsg());

        deviceForm.setCardTypeValidation(device.isCardTypeValidation());
        deviceForm.setSaleReceipt(device.isSaleReceipt());
        deviceForm.setCurrencyFromCard(device.isCurrencyFromCard());
        deviceForm.setCurrencyFromBin(device.isCurrencyFromBin());
        deviceForm.setProceedWithLkr(device.isProceedWithLkr());
        deviceForm.setCardTap(device.isCardTap());
        deviceForm.setCardInsert(device.isCardInsert());
        deviceForm.setCardSwipe(device.isCardSwipe());
        deviceForm.setDccPayload(device.isDccPayload());
        deviceForm.setEcrIp(device.getEcrIp());
        deviceForm.setEcrPort(device.getEcrPort());
        deviceForm.setEcrAuthToken(device.isEcrAuthToken());
        deviceForm.setTranToSim(device.isTranToSim());
        deviceForm.setEcrWifi(device.isEcrWifi());

        logger.info("Network Type : {}", device.getNetwork());
        if (device.getNetwork() == null || device.getNetwork().isEmpty()) {
            deviceForm.setNetwork2g(false);
            deviceForm.setNetwork3g(false);
            deviceForm.setNetwork4g(false);
            deviceForm.setAutoChange(true);

        } else if (device.getNetwork().equalsIgnoreCase(CommonConstant.NETWORK_2G)) {
            deviceForm.setNetwork2g(true);
            deviceForm.setNetwork3g(false);
            deviceForm.setNetwork4g(false);
            deviceForm.setAutoChange(false);
        } else if (device.getNetwork().equalsIgnoreCase(CommonConstant.NETWORK_3G)) {
            deviceForm.setNetwork3g(true);
            deviceForm.setNetwork2g(false);
            deviceForm.setNetwork4g(false);
            deviceForm.setAutoChange(false);
        } else if (device.getNetwork().equalsIgnoreCase(CommonConstant.NETWORK_4G)) {
            deviceForm.setNetwork3g(false);
            deviceForm.setNetwork2g(false);
            deviceForm.setNetwork4g(true);
            deviceForm.setAutoChange(false);
        } else if (device.getNetwork().equalsIgnoreCase(CommonConstant.NETWORK_AUTO)) {
            deviceForm.setNetwork3g(false);
            deviceForm.setNetwork2g(false);
            deviceForm.setNetwork4g(false);
            deviceForm.setAutoChange(true);
        } else {
            deviceForm.setNetwork4g(false);
            deviceForm.setNetwork2g(false);
            deviceForm.setNetwork3g(false);
            deviceForm.setAutoChange(true);
        }

        deviceForm.setAutoReversal(device.isAutoReversal());
        deviceForm.setMerchantPortal(device.isMerchantPortal());
        deviceForm.setResendVoid(device.isResendVoid());
        deviceForm.setClientCredentials(device.isClientCredentials());
        deviceForm.setPrintReceipt(device.isPrintReceipt());
        deviceForm.setPrintless(device.isPrintless());
        deviceForm.setLkrDefaultCurr(device.isLkrDefaultCurr());
        deviceForm.setVoidPwd(device.getVoidPwd());
        deviceForm.setDiffSaleMidTid(device.isDiffSaleMidTid());
        deviceForm.setMkiOffline(device.isMkiOffline());
        deviceForm.setMkiPreAuth(device.isMkiPreAuth());
        deviceForm.setBlockMag(device.isBlockMag());
        deviceForm.setSignPriority(device.isSignPriority());
        deviceForm.setCustomerReceipt(device.isCustomerReceipt());
        deviceForm.setMidTidSeg(device.isMidTidSeg());
        deviceForm.setEventAutoUpdate(device.isEventAutoUpdate());
        deviceForm.setNewVoidPwd(device.getNewVoidPwd());
        deviceForm.setNewSettlementPwd(device.getNewSettlementPwd());
        deviceForm.setImeiScan(device.isImeiScan());
        deviceForm.setCommission(device.isCommission());
        deviceForm.setCommissionRate(device.getCommissionRate());
        deviceForm.setEnableSanhindaPay(device.isEnableSanhindaPay());
        deviceForm.setRefNumberEnable(device.isRefNumberEnable());
        deviceForm.setTleProfilesEnable(device.isTleProfilesEnable());
        // convert json to list

        if (device.getOfflineUser() != null && !device.getOfflineUser().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            List<OfflineUserForm> userList = mapper.readValue(device.getOfflineUser(), new TypeReference<List<OfflineUserForm>>() {
            });
            deviceForm.setOfflineUser(userList);
        } else {
            List<OfflineUserForm> offlineUserForm = deviceService.findAllOfflineUser();
            deviceForm.setOfflineUser(offlineUserForm);
        }

        return deviceForm;
    }

    @SuppressWarnings("unused")
    private Device convertDeviceFormToDevice(DeviceForm deviceForm) {

        Device device = new Device();
        device.setDeviceId(deviceForm.getDeviceId());
        device.setSerialNo(deviceForm.getSerialNo().trim());
        device.setBankCode(deviceForm.getBankName()); // should replace with code in the future
        device.setBankName(deviceForm.getBankName());
        device.setMerchantName(deviceForm.getMerchantName());
        device.setMerchantAddress(deviceForm.getMerchantAddress());
        device.setVisaNoCvmLimit(deviceForm.getVisaNoCvmLimit());
        device.setCntactlsTrxnLimit(deviceForm.getCntactlsTrxnLimit());
        device.setAutoSettle(deviceForm.isAutoSettle());
        device.setAutoSettleTime(deviceForm.getAutoSettleTime());
        device.setForceSettle(deviceForm.isForceSettle());
        device.setEcr(deviceForm.isEcr());
        device.setKeyIn(deviceForm.isKeyIn());
        device.setMobileNo(deviceForm.getMobileNo());
        device.setSimNo(deviceForm.getSimNo());
        device.setCustContactNo(deviceForm.getCustContactNo());
        device.setRemark(deviceForm.getRemark());
        device.setEcrQr(deviceForm.isEcrQr());
        device.setSignature(deviceForm.isSignature());
        device.setDebugMode(deviceForm.isDebugMode());
        device.setNoSettle(deviceForm.isNoSettle());
        device.setActivityTracker(deviceForm.isActivityTracker());
        device.setQrRefund(deviceForm.isQrRefund());
        device.setReversalHistory(deviceForm.isReversalHistory());
        device.setEnableAmex(deviceForm.isEnableAmex());
        device.setPushNotification(deviceForm.isPushNotification());
        device.setPreAuth(deviceForm.isPreAuth());
        device.setDcc(deviceForm.isDcc());
        device.setOffline(deviceForm.isOffline());
        device.setKeyInAmex(deviceForm.isKeyInAmex());
        device.setPopupMsg(deviceForm.isPopupMsg());
        device.setCardTypeValidation(deviceForm.isCardTypeValidation());
        device.setSaleReceipt(deviceForm.isSaleReceipt());
        device.setCurrencyFromBin(deviceForm.isCurrencyFromBin());
        device.setCurrencyFromCard(deviceForm.isCurrencyFromCard());
        device.setProceedWithLkr(deviceForm.isProceedWithLkr());
        device.setCardTap(deviceForm.isCardTap());
        device.setCardInsert(deviceForm.isCardInsert());
        device.setCardSwipe(deviceForm.isCardSwipe());
        device.setEcrIp(deviceForm.getEcrIp());
        device.setEcrPort(deviceForm.getEcrPort());
        device.setEcrAuthToken(deviceForm.isEcrAuthToken());
        device.setTranToSim(deviceForm.isTranToSim());
        device.setEcrWifi(deviceForm.isEcrWifi());

        //Update on 13-09-2022

        if (deviceForm.isNetwork2g()) {
            device.setNetwork(CommonConstant.NETWORK_2G);
        } else if (deviceForm.isNetwork3g()) {
            device.setNetwork(CommonConstant.NETWORK_3G);
        } else if (deviceForm.isNetwork4g()) {
            device.setNetwork(CommonConstant.NETWORK_4G);
        } else {
            device.setNetwork(CommonConstant.NETWORK_AUTO);
        }

        device.setDccPayload(deviceForm.isDccPayload());
        device.setAutoReversal(deviceForm.isAutoReversal());
        device.setMerchantPortal(deviceForm.isMerchantPortal());
        device.setResendVoid(deviceForm.isResendVoid());
        device.setClientCredentials(deviceForm.isClientCredentials());
        device.setPrintReceipt(deviceForm.isPrintReceipt());
        device.setPrintless(deviceForm.isPrintless());
        device.setLkrDefaultCurr(deviceForm.isLkrDefaultCurr());
        device.setVoidPwd(deviceForm.getVoidPwd());
        device.setDiffSaleMidTid(deviceForm.isDiffSaleMidTid());
        device.setMkiOffline(deviceForm.isMkiOffline());
        device.setMkiPreAuth(deviceForm.isMkiPreAuth());
        device.setBlockMag(deviceForm.isBlockMag());
        device.setSignPriority(deviceForm.isSignPriority());
        device.setCustomerReceipt(deviceForm.isCustomerReceipt());
        device.setMidTidSeg(deviceForm.isMidTidSeg());
        device.setEventAutoUpdate(deviceForm.isEventAutoUpdate());
        device.setNewVoidPwd(deviceForm.getNewVoidPwd());
        device.setNewSettlementPwd(deviceForm.getNewSettlementPwd());
        device.setImeiScan(deviceForm.isImeiScan());
        device.setCommission(deviceForm.isCommission());
        device.setCommissionRate(deviceForm.getCommissionRate());
        device.setRefNumberEnable(deviceForm.isRefNumberEnable());
        device.setTleProfilesEnable(deviceForm.isTleProfilesEnable());
        String str2 = new Gson().toJson(deviceForm.getOfflineUser());
        device.setOfflineUser(str2);
        device.setEnableSanhindaPay(deviceForm.isEnableSanhindaPay());

        return device;
    }

    private List<MerchantForm> convertMerchantToMerchantForm(Device device) {

        List<MerchantForm> merchants = new ArrayList<>();

        if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
            device.getMerchants().forEach(merchant -> {
                // set merchant data
                MerchantForm mForm = new MerchantForm();
                mForm.setMerchantId(merchant.getMerchantId());
                mForm.setType(merchant.getCategory());
                mForm.setMonth(merchant.getMonth());
                mForm.setMid(merchant.getMid());
                mForm.setTid(merchant.getTid());
                mForm.setCurrency(merchant.getCurrency());
                mForm.setDescription(merchant.getDescription());
                mForm.setMinAmount(merchant.getMinAmount());
                mForm.setMaxAmount(merchant.getMaxAmount());
                mForm.setVoidTx(merchant.isVoidTx());
                mForm.setAmexTx(merchant.isAmexTx());
                mForm.setDcc(merchant.isDcc());
                mForm.setPreAuth(merchant.isPreAuth());
                mForm.setOffline(merchant.isOffline());
                mForm.setJcb(merchant.isJcb());
                mForm.setMerchantType(merchant.getMerchantType());
                mForm.setLocalMer(merchant.isLocalMer());
                mForm.setForeignMer(merchant.isForeignMer());
                mForm.setOnUs(merchant.isOnUs());
                mForm.setOffUs(merchant.isOffUs());
                mForm.setMidTidSeg(merchant.isMidTidSeg());
                mForm.setIphoneImei(merchant.isIphoneImei());

                if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                    // set scan params
                    if (merchant.getScanParam() != null) {
                        mForm.setMerchantUserId(merchant.getScanParam().getMerchantUserId());
                        mForm.setMerchantPassword(merchant.getScanParam().getMerchantPassword());
                        mForm.setChecksumKey(merchant.getScanParam().getChecksumKey());
                        mForm.setVid(merchant.getScanParam().getVid());
                        mForm.setCid(merchant.getScanParam().getCid());
                        mForm.setQrRefId(merchant.getScanParam().isQrRefId());
                    }
                } else if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.AMEX)) {
                    // ser amex params
                    /*
                     * if (merchant.getAmexParam() != null) {
                     * mForm.setAmexIp(merchant.getAmexParam().getAmexIp()); }
                     */
                } else {
                    // not required
                }
                // set
                merchants.add(mForm);
            });
        }

        return merchants;
    }

    private List<Merchant> convertMerchantFormToMerchant(FormInfo formInfo) {
        List<Merchant> merchants = new ArrayList<>();

        if (formInfo.getMerchants() != null && !formInfo.getMerchants().isEmpty()) {
            formInfo.getMerchants().forEach(mForm -> {
                // set merchant data
                Merchant merchant = new Merchant();
                merchant.setMerchantId(mForm.getMerchantId());
                merchant.setCategory(mForm.getType());
                merchant.setMonth(mForm.getMonth());
                merchant.setMid(mForm.getMid().trim());
                merchant.setTid(mForm.getTid().trim());
                merchant.setCurrency(mForm.getCurrency());
                merchant.setDescription(mForm.getDescription());
                merchant.setMinAmount(mForm.getMinAmount());
                merchant.setMaxAmount(mForm.getMaxAmount());
                merchant.setVoidTx(mForm.isVoidTx());
                merchant.setAmexTx(mForm.isAmexTx());
                merchant.setAction(mForm.getAction());
                merchant.setDcc(mForm.isDcc());
                merchant.setPreAuth(mForm.isPreAuth());
                merchant.setOffline(mForm.isOffline());
                merchant.setJcb(mForm.isJcb());
                merchant.setMerchantType(mForm.getMerchantType());
                merchant.setLocalMer(mForm.isLocalMer());
                merchant.setForeignMer(mForm.isForeignMer());
                merchant.setOnUs(mForm.isOnUs());
                merchant.setOffUs(mForm.isOffUs());
                merchant.setMidTidSeg(mForm.isMidTidSeg());
                merchant.setIphoneImei(mForm.isIphoneImei());

                if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                    // set scan params
                    ScanParam sp = new ScanParam();
                    sp.setMerchantUserId(mForm.getMerchantUserId());
                    sp.setMerchantPassword(mForm.getMerchantPassword());
                    sp.setChecksumKey(mForm.getChecksumKey());
                    sp.setVid(mForm.getVid());
                    sp.setCid(mForm.getCid());
                    sp.setQrRefId(mForm.isQrRefId());
                    merchant.setScanParam(sp);
                } else if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.AMEX)) {
                    // ser amex params
                    /*
                     * AmexParam ap = new AmexParam(); ap.setAmexIp(mForm.getAmexIp());
                     * merchant.setAmexParam(ap);
                     */
                } else {
                    // not required
                }
                // set merchant
                merchants.add(merchant);
            });
        }

        return merchants;
    }

    private List<BinConfig> convertBinConfigFormToBinConfig(FormInfo formInfo) {
        List<BinConfig> binConfig = new ArrayList<>();

        if (formInfo.getMerchantBin() != null && !formInfo.getMerchantBin().isEmpty()) {
            formInfo.getMerchantBin().forEach(mForm -> {
                // set merchant data
                BinConfig bConfig = new BinConfig();
                //bConfig.setAction()
                bConfig.setAction(mForm.getAction());
                bConfig.setBinEnd(mForm.getBinEndFrom());
                bConfig.setBinStartFrom(mForm.getBinStartFrom());
                bConfig.setCardType(mForm.getCardType());
                bConfig.setFormId(mForm.getFormId());
                bConfig.setMerchantId(mForm.getMerchantId());
                bConfig.setMid(mForm.getMid().trim());
                bConfig.setTid(mForm.getTid().trim());
                bConfig.setTransType(mForm.getTransType());
                bConfig.setType(mForm.getType());
                bConfig.setMerchantId(mForm.getMerchantId());

                binConfig.add(bConfig);
            });
        }

        return binConfig;
    }

    private List<BinConfigForm> convertBinConfigToBinConfigForm(Device device) {
        List<BinConfigForm> binConfigForm = new ArrayList<>();

        if (device.getBinConfig() != null && !device.getBinConfig().isEmpty()) {
            device.getBinConfig().forEach(mForm -> {
                // set merchant data
                BinConfigForm bConfig = new BinConfigForm();
                //bConfig.setAction()
                bConfig.setAction(mForm.getAction());
                bConfig.setBinEndFrom(mForm.getBinEnd());
                bConfig.setBinStartFrom(mForm.getBinStartFrom());
                bConfig.setCardType(mForm.getCardType());
                bConfig.setFormId(mForm.getFormId());
                bConfig.setMerchantId(mForm.getMerchantId());
                bConfig.setMid(mForm.getMid());
                bConfig.setTid(mForm.getTid());
                bConfig.setTransType(mForm.getTransType());
                bConfig.setType(mForm.getType());
                bConfig.setMerchantId(mForm.getMerchantId());

                binConfigForm.add(bConfig);
            });
        }

        return binConfigForm;
    }

    @PostMapping(value = "/admin-portal/new-add-to-cart", params = "action=copy")
    public String copyNewBinRuleToCart(HttpServletRequest request, @ModelAttribute @Valid MerchantForm merchantForm,
                                       BindingResult result, Model model) {

        logger.info("add new merchant link (register device)");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        CommonDataForm commonData = formInfo.getCommonDataForm();

        deviceForm.setSerialNo(selectedMerchant);

        logger.info("MerchantBin Size: {}", formInfo.getMerchantBin().size());

        logger.info("add new merchant link (selectedMerchant): {}", formInfo.getDeviceForm().getVisaNoCvmLimit());
        //

		/*merchantForm.setMid(commonData.getMid());
		merchantForm.setTid(commonData.getTid());*/

        // merchantForm.setAmexIp(CommonConstant.DEFAULT_AMEX_IP);

        List<BinConfigForm> merchantBin = new ArrayList<>(0);

        if (formInfo.getMerchantBin() != null && merchantForm.getMid() != null && merchantForm.getTid() != null) {
            formInfo.getMerchantBin().forEach(mb -> {
                if (mb.getMid().equalsIgnoreCase(merchantForm.getMid()) && mb.getTid().equalsIgnoreCase(merchantForm.getTid())) {
                    merchantBin.add(mb);
                }
            });
        } else
            logger.info("No Bin data to copy");
        if (merchantBin.size() == 0) {
            logger.info("No Bin data to copy");
        }
        logger.info("Merchant Bin Size to be copied: {}", merchantBin.size());
        FormInfo formBinInfo = SessionHelper.getFormBinInSession(request);
        formBinInfo.setMerchantBin(merchantBin);
        logger.info("Copied Merchant Bin Size: {}", formBinInfo.getMerchantBin().size());

        String str = new Gson().toJson(formBinInfo.getMerchantBin());
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        cb.setContents(strSel, null);
        logger.info("JsonData-BIN: {}", str);

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

        return Endpoint.PAGE_ADD_NEW_MERCHANT;
    }

    @PostMapping(value = "/admin-portal/new-add-to-cart", params = "action=import")
    public String importNewBinRuleToCart(HttpServletRequest request, @ModelAttribute @Valid MerchantForm merchantForm,
                                         BindingResult result, Model model) {

        logger.info("Applying copied bin-rule data");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        FormInfo formBinInfo = SessionHelper.getFormBinInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        CommonDataForm commonDataForm = new CommonDataForm();

        logger.info("MerchantBin Size: {}", formInfo.getMerchantBin().size());


        List<BinConfigForm> merchantBin = new ArrayList<>(0);

        if (merchantForm.getMid() == null && merchantForm.getTid() == null) {
            logger.info("MID-TID Not found");
            model.addAttribute(MESSAGE, "MID/TID Not found. Please enter MID/TID first");
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        if (formBinInfo.getMerchantBin() != null && merchantForm.getMid() != null && merchantForm.getTid() != null) {
            formBinInfo.getMerchantBin().forEach(mb -> {

                BinConfigForm bConfig = new BinConfigForm();
                //bConfig.setAction()
                bConfig.setAction(mb.getAction());
                bConfig.setBinEndFrom(mb.getBinEndFrom());
                bConfig.setBinStartFrom(mb.getBinStartFrom());
                bConfig.setCardType(mb.getCardType());
                bConfig.setMid(merchantForm.getMid());
                bConfig.setTid(merchantForm.getTid());
                bConfig.setTransType(mb.getTransType());
                bConfig.setType(mb.getType());
                bConfig.setMerchantId(merchantForm.getMerchantId());


                List<String> isValidBinRule = isValidateBinRule(request, bConfig);
                if (isValidBinRule.get(0).equals(CommonConstant.TRUE)) {
                    logger.info("VALID BIN RULE: Accepted");
                    // add merchant to cart
                    addBinToCart(request, bConfig);
                    commonDataForm.setTid(bConfig.getTid());
                    commonDataForm.setMid(bConfig.getMid());
                    formInfo.setCommonDataForm(commonDataForm);

                    logger.info("NEW BIN RULE ADDED");

                }

            });
        } else
            logger.info("No Bin data to copy");

        if (merchantBin.size() == 0) {
            logger.info("No Bin data to copy");
        }
        /*if (isBinError==true){
            logger.info("INVALID BIN RULE: Rejected");
            model.addAttribute("message","BIN Validation Failed");
            model.addAttribute("back_link", Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;

        }*/
        logger.info("Merchant Bin Size to be copied: {}", merchantBin.size());

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

        return Endpoint.PAGE_ADD_NEW_MERCHANT;
    }

    // modify new bin rule page
    @PostMapping(value = Endpoint.URL_NEW_MODIFY_TO_CART, params = "action=copy")
    public String copyBinRuleToCart(HttpServletRequest request, @ModelAttribute @Valid MerchantForm merchantForm,
                                    BindingResult result, Model model) {

        logger.info("Copy BIN Rules from cart");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        /*MerchantForm merchantForm = new MerchantForm();*/
        CommonDataForm commonDataForm = new CommonDataForm();
        // merchantForm.setAmexIp(CommonConstant.DEFAULT_AMEX_IP);
        logger.info("merchantId: {}", merchantForm.getMerchantId());

        List<BinConfigForm> merchantBin = new ArrayList<>(0);

        if (formInfo.getMerchantBin() != null && merchantForm.getMid() != null && merchantForm.getTid() != null) {
            formInfo.getMerchantBin().forEach(mb -> {
                if (mb.getMid().equalsIgnoreCase(merchantForm.getMid()) && mb.getTid().equalsIgnoreCase(merchantForm.getTid())) {
                    merchantBin.add(mb);
                }
            });
        } else
            logger.info("No Bin data to copy");
        if (merchantBin.size() == 0) {
            logger.info("No Bin data to copy");
        }
        logger.info("Merchant Bin Size to be copied: {}", merchantBin.size());
        FormInfo formBinInfo = SessionHelper.getFormBinInSession(request);
        formBinInfo.setMerchantBin(merchantBin);
        logger.info("Copied Merchant Bin Size: {}", formBinInfo.getMerchantBin().size());

        String str = new Gson().toJson(formBinInfo.getMerchantBin());
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        cb.setContents(strSel, null);
        logger.info("JsonData-BIN: {}", str);
        logger.info("bin rules: {}", formInfo.getMerchantBin().size());

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

        logger.info("NEW_MODIFY_TO_CART:END");

        //return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_MERCHANT;
        return Endpoint.PAGE_MODIFY_NEW_MERCHANT;
    }

    // modify new bin rule page
    @PostMapping(value = Endpoint.URL_NEW_MODIFY_TO_CART, params = "action=import")
    public String importBinRuleToCart(HttpServletRequest request, @ModelAttribute @Valid MerchantForm merchantForm,
                                      BindingResult result, Model model) {

        logger.info("Apply copied BIN Rules from cart");
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formBinInfo = SessionHelper.getFormBinInSession(request);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        /*MerchantForm merchantForm = new MerchantForm();*/
        CommonDataForm commonDataForm = new CommonDataForm();
        // merchantForm.setAmexIp(CommonConstant.DEFAULT_AMEX_IP);

        List<BinConfigForm> merchantBin = new ArrayList<>(0);

        if (merchantForm.getMid() == null && merchantForm.getTid() == null) {
            logger.info("MID-TID Not found");
            model.addAttribute(MESSAGE, "MID/TID Not found. Please enter MID/TID first");
            model.addAttribute(BACK_LINK, Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        if (formBinInfo.getMerchantBin() != null && merchantForm.getMid() != null && merchantForm.getTid() != null) {
            formBinInfo.getMerchantBin().forEach(mb -> {

                BinConfigForm bConfig = new BinConfigForm();
                //bConfig.setAction()
                bConfig.setAction(mb.getAction());
                bConfig.setBinEndFrom(mb.getBinEndFrom());
                bConfig.setBinStartFrom(mb.getBinStartFrom());
                bConfig.setCardType(mb.getCardType());
                bConfig.setMid(merchantForm.getMid());
                bConfig.setTid(merchantForm.getTid());
                bConfig.setTransType(mb.getTransType());
                bConfig.setType(mb.getType());
                bConfig.setMerchantId(merchantForm.getMerchantId());

                List<String> isValidBinRule = isValidateBinRule(request, bConfig);
                if (isValidBinRule.get(0).equals(CommonConstant.TRUE)) {
                    logger.info("VALID BIN RULE: Accepted");
                    // add merchant to cart
                    addBinToCart(request, bConfig);
                    commonDataForm.setTid(bConfig.getTid());
                    commonDataForm.setMid(bConfig.getMid());
                    formInfo.setCommonDataForm(commonDataForm);

                    logger.info("NEW BIN RULE ADDED");

                }

            });
        } else
            logger.info("No Bin data to copy");

        if (merchantBin.isEmpty()) {
            logger.info("No Bin data to copy");
        }
        /*if (isBinError==true){
            logger.info("INVALID BIN RULE: Rejected");
            model.addAttribute("message","BIN Validation Failed");
            model.addAttribute("back_link", Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;

        }*/
        logger.info("Merchant Bin Size to be copied: {}", merchantBin.size());

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANT_FORM, merchantForm);
        model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
        model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
        model.addAttribute(CURRENCIES, currencyService.getCurrencies());
        model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

        //return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_MERCHANT;
        return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_NEW_MERCHANT;
    }

    @GetMapping(value = Endpoint.URL_COPY_DEVICE + "/{deviceId}/{mode}")
    public String copyDevice(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId, @PathVariable("deviceId") String mode, Model model)
            throws Exception {

        logger.info("Copy device by device id");
        logger.info("modify device page load by serial no");
        logger.info("Request [Device ID]: {}", deviceId);
        merchantError = ErrorCode.NO_ERROR;
        SessionHelper.removeFormInSession(request);
        // load login user
        LoginUserUtil.loadLoginUser(model);
        // load data from db
        Device device = deviceService.findDeviceById(deviceId);

        List<MerchantForm> merchants = convertMerchantToMerchantForm(device);
        DeviceForm deviceForm = convertDeviceToDeviceForm(device);
        List<ProfileForm> profiles = convertProfileToProfileForm(device);
        List<BinConfigForm> BinCong = convertBinConfigToBinConfigForm(device);

        String str2 = new Gson().toJson(deviceForm.getOfflineUser());
        logger.info("Device Info: {} ", str2);
        // update load data in to the session
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        formInfo.setMerchants(merchants);
        formInfo.setDeviceForm(deviceForm);
        formInfo.setProfiles(profiles);
        formInfo.setMerchantBin(BinCong);

        List<ProfileMerchant> profileMerchants = new ArrayList<ProfileMerchant>(0);

        profiles.forEach(profile -> {
            if (profile.getProfileMerchants() != null) {
                profile.getProfileMerchants().forEach(pMerchant -> {
                    ProfileMerchant profileMerchant = new ProfileMerchant();
                    profileMerchant.setAddedBy(pMerchant.getAddedBy());
                    profileMerchant.setAddedDate(pMerchant.getAddedDate());
                    profileMerchant.setDefaultMerchant(pMerchant.isDefault());
                    profileMerchant.setLastUpdate(pMerchant.getLastUpdate());
                    profileMerchant.setMerchantId(pMerchant.getMerchantId());
                    profileMerchant.setProfileId(pMerchant.getProfileId());
                    profileMerchant.setProfMergId(pMerchant.getProfMergId());
                    profileMerchant.setStatus(pMerchant.getStatus());
                    profileMerchant.setUpdatedBy(pMerchant.getUpdatedBy());
                    profileMerchants.add(profileMerchant);
                    logger.info("Load Profiles [Device ID]: {}| [ProfileID]: {} | [MerchantID]: {}", deviceId, profile.getProfileId(), pMerchant.getMerchantId());
                });
            }
        });

        logger.info("Current Profile Merchant count: {}", profileMerchants.size());
        formInfo.setProfileMerchants(profileMerchants);

        String str = new Gson().toJson(device);
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        cb.setContents(strSel, null);

        logger.info("Device Info: {}", str);

        DataFlavor flavor = DataFlavor.stringFlavor;
        if (cb.isDataFlavorAvailable(flavor)) {
            try {
                String text = (String) cb.getData(flavor);
                System.out.println(text);
                Device newDev = new Gson().fromJson(text, Device.class);
                logger.info("Device Info: {}", newDev.toString());
            } catch (UnsupportedFlavorException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        model.addAttribute(DEVICE_FORM, deviceForm);
        model.addAttribute(MERCHANTS, merchants);
        model.addAttribute(PROFILES, profiles);

        // reset
        tempMerchantId = 0;

        //return Endpoint.PAGE_MODIFY_DEVICE;
        if (mode != null & mode.contains("M")) {
            return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_DEVICE + "/" + deviceId;
        }
        return Endpoint.REDIRECT_NAME + Endpoint.URL_VIEW_DEVICE + "/" + deviceId;
    }

    @GetMapping(value = Endpoint.URL_PASTE_DEVICE)
    public String pasteDevice(HttpServletRequest request, Model model) {

        logger.info("Paste device from Clipboard");
        logger.info("Load Register New device");

        SessionHelper.removeFormInSession(request);
        // load login user
        LoginUserUtil.loadLoginUser(model);
        // load data from clipboard
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

        DataFlavor flavor = DataFlavor.stringFlavor;
        if (cb.isDataFlavorAvailable(flavor)) {
            try {
                String text = (String) cb.getData(flavor);

                logger.info("Device Info: Copied from Clipboard");
                logger.info("Data: {}", text);
                Device device = new Gson().fromJson(text, Device.class);
                logger.info("Device Info: {}", device);

                List<MerchantForm> merchants = convertMerchantToMerchantForm(device);
                DeviceForm deviceForm = convertDeviceToDeviceForm(device);
                List<ProfileForm> profiles = convertProfileToProfileForm(device);
                List<BinConfigForm> binCong = convertBinConfigToBinConfigForm(device);

                // update load data in to the session
                FormInfo formInfo = SessionHelper.getFormInSession(request);
                formInfo.setMerchants(merchants);
                formInfo.setDeviceForm(deviceForm);
                formInfo.setProfiles(profiles);
                formInfo.setMerchantBin(binCong);

                List<ProfileMerchant> profileMerchants = new ArrayList<ProfileMerchant>(0);

                profiles.forEach(profile -> {
                    if (profile.getProfileMerchants() != null) {
                        profile.getProfileMerchants().forEach(pMerchant -> {
                            ProfileMerchant profileMerchant = new ProfileMerchant();
                            profileMerchant.setAddedBy(pMerchant.getAddedBy());
                            profileMerchant.setAddedDate(pMerchant.getAddedDate());
                            profileMerchant.setDefaultMerchant(pMerchant.isDefault());
                            profileMerchant.setLastUpdate(pMerchant.getLastUpdate());
                            profileMerchant.setMerchantId(pMerchant.getMerchantId());
                            profileMerchant.setProfileId(pMerchant.getProfileId());
                            profileMerchant.setProfMergId(pMerchant.getProfMergId());
                            profileMerchant.setStatus(pMerchant.getStatus());
                            profileMerchant.setUpdatedBy(pMerchant.getUpdatedBy());
                            profileMerchants.add(profileMerchant);
                            logger.info("Load Profiles [Device ID]: {} | [ProfileID]: {} | [MerchantID]: {} ", deviceForm.getDeviceId(), profile.getProfileId(), pMerchant.getMerchantId());
                        });
                    }
                });

                logger.info("Current Profile Merchant count: {}", profileMerchants.size());
                formInfo.setProfileMerchants(profileMerchants);

                model.addAttribute(PROFILES, profiles);
                model.addAttribute(DEVICE_FORM, deviceForm);
                model.addAttribute(MERCHANT_FORM, merchants);
                model.addAttribute(MERCHANT_TYPES, merchantTypeService.getMerchantTypes());
                model.addAttribute(SALE_MERCHANT_TYPES, merchantTypeService.getSaleMerchantTypes());
                model.addAttribute(CURRENCIES, currencyService.getCurrencies());
                model.addAttribute(BIN_RULES, formInfo.getMerchantBin());

                return Endpoint.REDIRECT_NAME + Endpoint.URL_REG_DEVICE;

            } catch (UnsupportedFlavorException e) {
                System.out.println(e);
                logger.info("FlavorException: {}", e.getMessage());
                model.addAttribute(MESSAGE, EventMessage.ERROR_MSG_FAIL + ". " + e.getMessage());
                model.addAttribute(BACK_LINK, Endpoint.URL_REG_DEVICE);
                return Endpoint.PAGE_OPERATION_FAIL;
            } catch (IOException e) {
                logger.info("IOException: {}", e.getMessage());
                model.addAttribute(MESSAGE, EventMessage.ERROR_MSG_FAIL + ". " + e.getMessage());
                model.addAttribute(BACK_LINK, Endpoint.URL_REG_DEVICE);
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        }
        return Endpoint.REDIRECT_NAME + Endpoint.URL_REG_DEVICE;
    }

    // add new offline user
    @GetMapping(value = Endpoint.URL_ADD_OFFLINE_USER)
    public String viewNewOfflineUserForm(HttpServletRequest request, Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        List<OfflineUserForm> offlineUser = formInfo.getDeviceForm().getOfflineUser();

        if (offlineUser != null) {
            formInfo.setOfflineUser(offlineUser);
        }

        DeviceForm deviceForm = new DeviceForm();

        if (formInfo.getOfflineUser().isEmpty()) {
            List<OfflineUserForm> offlineUserForm = deviceService.findAllOfflineUser();
            deviceForm.setOfflineUser(offlineUserForm);
        } else {
            deviceForm.setOfflineUser(formInfo.getOfflineUser());
        }
        model.addAttribute(OFFLINE_USER_FORM, deviceForm);
        return Endpoint.PAGE_OFFLINE_USER;
    }

    // save new offline user
    @PostMapping(value = Endpoint.URL_SAVE_OFFLINE_USER)
    public String saveOfflineUser(@ModelAttribute DeviceForm deviceForm, HttpServletRequest request, Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);
        List<OfflineUserForm> offlineUser = deviceForm.getOfflineUser();
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        //set deviceForm in to formInfo
        formInfo.setDeviceForm(deviceForm);

        formInfo.setOfflineUser(offlineUser);
        return Endpoint.REDIRECT_NAME + Endpoint.URL_REG_DEVICE;
    }

    // view exciting  offline user
    @GetMapping(value = Endpoint.URL_EDIT_OFFLINE_USER)
    public String viewToEditOfflineUserForm(HttpServletRequest request, Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();

        //if offline user not existion then set data
        if ((formInfo.getOfflineUser() == null || formInfo.getOfflineUser().isEmpty()) && (deviceForm.getOfflineUser() == null || deviceForm.getOfflineUser().isEmpty())) {
            List<OfflineUserForm> offlineUserForm = deviceService.findAllOfflineUser();
            deviceForm.setOfflineUser(offlineUserForm);
        }

        if (!formInfo.getOfflineUser().isEmpty()) {
            deviceForm.setOfflineUser(formInfo.getOfflineUser());
        }
        model.addAttribute(OFFLINE_USER_FORM, deviceForm);
        return Endpoint.PAGE_MODIFY_OFFLINE_USER;
    }

    // modify existing  offline user
    @PostMapping(value = Endpoint.URL_MODIFY_SAVE_OFFLINE_USER)
    public String modifyOfflineUser(@ModelAttribute DeviceForm offlineUserForm, HttpServletRequest request, Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);
        List<OfflineUserForm> offlineUser = offlineUserForm.getOfflineUser();
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        formInfo.setOfflineUser(offlineUser);
        formInfo.getDeviceForm().setOfflineUser(offlineUser);

        return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_DEVICE;
    }

    @GetMapping(value = Endpoint.URL_VIEW_OFFLINE_USER)
    public String viewOfflineUserForm(HttpServletRequest request, Model model) {

        // current login user
        LoginUserUtil.loadLoginUser(model);
        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();

        //if offline user not existion then set data
        if ((formInfo.getOfflineUser() == null || formInfo.getOfflineUser().isEmpty()) && (deviceForm.getOfflineUser() == null || deviceForm.getOfflineUser().isEmpty())) {
            List<OfflineUserForm> offlineUserList = deviceService.findAllOfflineUser();
            deviceForm.setOfflineUser(offlineUserList);
        }
        if (!formInfo.getOfflineUser().isEmpty()) {
            deviceForm.setOfflineUser(formInfo.getOfflineUser());
        }
        model.addAttribute(OFFLINE_USER_FORM, deviceForm);
        return Endpoint.PAGE_VIEW_OFFLINE_USER;

    }

    @GetMapping(value = Endpoint.URL_VIEW_SETTING_CONFIGURATION + "/{serialNo}")
    public String viewSettingConfiguration(HttpServletRequest request, Model model, @PathVariable("serialNo") String serialNo) throws Exception {

        // current login user
        LoginUserUtil.loadLoginUser(model);


        SaveConfigurationDto configSetting = configurationService.findConfigSetting(serialNo);

        model.addAttribute("merchant", configSetting.getMerchants() != null ? configSetting.getMerchants() : new MerchantDto());
        model.addAttribute("profile", configSetting.getProfiles() != null ? configSetting.getProfiles() : new ProfilesDto());
        model.addAttribute("ecr", configSetting.getEcrSettings() != null ? configSetting.getEcrSettings() : new EcrSettingDto());
        model.addAttribute("transaction", configSetting.getTransaction() != null ? configSetting.getTransaction() : new TransactionDto());
        model.addAttribute("communication", configSetting.getCommunicationParametersSettings() != null ? configSetting.getCommunicationParametersSettings() : new CommunicationParameterDto());
        model.addAttribute("tleSettings", configSetting.getTleSettings() != null ? configSetting.getTleSettings() : new TleSettingDto());
        model.addAttribute("qrScanParams", configSetting.getQrScanParams() != null ? configSetting.getQrScanParams() : new PortalDto());
        model.addAttribute("receiptSettings", configSetting.getReceipt() != null ? configSetting.getReceipt() : new ReceiptSettingDto());
        model.addAttribute("security", configSetting.getSecurity() != null ? configSetting.getSecurity() : new SecuritySettingDto());
        model.addAttribute("logging", configSetting.getLogging() != null ? configSetting.getLogging() : new LoggingSettingDto());
        model.addAttribute("activityTracking", configSetting.getActivityTracking() != null ? configSetting.getActivityTracking() : new TrackingSettingDto());
        model.addAttribute("adminPortalConfig", configSetting.getAdminPortalConfig() != null ? configSetting.getAdminPortalConfig() : new PortalSettingDto());
        model.addAttribute("dccSetting", configSetting.getDccSettings() != null ? configSetting.getDccSettings() : new DccSettingDto());
        model.addAttribute("activeUser", configSetting.getActiveUsers() != null ? configSetting.getActiveUsers() : new ActiveUserDto());
        model.addAttribute("authSetting", configSetting.getAuthServerCredentials() != null ? configSetting.getAuthServerCredentials() : new AuthSettingDto());


        return Endpoint.PAGE_VIEW_SETTING_CONFIG;

    }

    //setting password view
    @GetMapping(value = Endpoint.URL_VIEW_PASSWORD + "/{serialNo}")
    @ResponseBody
    public Map<String, String> fetchPassword(@PathVariable("serialNo") String serialNo) {
        try {
            return PasswordUtil.passwordGenerateOldAndNew(serialNo);
        }catch (Exception e){
            logger.info("Fail to fetch password");
            throw new BadCredentialsException("Fail to fetch password");
        }

    }

    private String profileValidationForDifferentMidTid(FormInfo formInfo, Model model) {

        long filterMidTidMerLkr = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.LKR) && formInfo.getDeviceForm().isMidTidSeg()).count();
        long filterDcc = formInfo.getMerchants().stream()
                .filter(pm -> pm.isSelectMerchant() && pm.isDcc() && pm.getCurrency().equals(CurrentyType.LKR) && formInfo.getDeviceForm().isMidTidSeg()).count();
        long filterNonDcc = formInfo.getMerchants().stream().
                filter(pm -> pm.isSelectMerchant() && !pm.isDcc() && pm.isForeignMer() && pm.getCurrency().equals(CurrentyType.LKR) && formInfo.getDeviceForm().isMidTidSeg()).count();
        long filterOnUs = formInfo.getMerchants().stream()
                .filter(pm -> pm.isSelectMerchant() && pm.isLocalMer() && pm.isOnUs() && pm.getCurrency().equals(CurrentyType.LKR) && formInfo.getDeviceForm().isMidTidSeg()).count();
        long filterOofUs = formInfo.getMerchants().stream()
                .filter(pm -> pm.isSelectMerchant() && pm.isLocalMer() && pm.isOffUs() && pm.getCurrency().equals(CurrentyType.LKR) && formInfo.getDeviceForm().isMidTidSeg()).count();

        if (filterMidTidMerLkr != 4) {
            model.addAttribute(MESSAGE, EventMessage.Should_Add_Four_LKR_MERCHANT);
            merchantError = EventMessage.Should_Add_Four_LKR_MERCHANT;
            return Endpoint.PAGE_OPERATION_FAIL;
        } else if (filterDcc > 1) {
            model.addAttribute(MESSAGE, EventMessage.Should_Add_ONE_LKR_DCC_MERCHANT);
            merchantError = EventMessage.Should_Add_ONE_LKR_DCC_MERCHANT;
            return Endpoint.PAGE_OPERATION_FAIL;
        } else if (filterNonDcc > 1) {
            model.addAttribute(MESSAGE, EventMessage.Should_Add_ONE_LKR_NONDCC_MERCHANT);
            merchantError = EventMessage.Should_Add_ONE_LKR_NONDCC_MERCHANT;
            return Endpoint.PAGE_OPERATION_FAIL;
        } else if (filterOnUs > 1) {
            model.addAttribute(MESSAGE, EventMessage.Should_Add_ONE_LKR_LOCAL_ONUS_MERCHANT);
            merchantError = EventMessage.Should_Add_ONE_LKR_LOCAL_ONUS_MERCHANT;
            return Endpoint.PAGE_OPERATION_FAIL;
        } else if (filterOofUs > 1) {
            model.addAttribute(MESSAGE, EventMessage.Should_Add_ONE_LKR_LOCAL_OFFUS_MERCHANT);
            merchantError = EventMessage.Should_Add_ONE_LKR_LOCAL_OFFUS_MERCHANT;
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        return null;
    }

    private String profileValidationForSameCurrency(FormInfo formInfo, Model model) {
        long filterSaleMerLkr = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.LKR) && !formInfo.getDeviceForm().isMidTidSeg() && !formInfo.getDeviceForm().getBankName().equals(BankTypes.COM_BANK)).count();
        if (filterSaleMerLkr > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.LKR);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.LKR;
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        long filterSaleMerUsd = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.USD) && !formInfo.getDeviceForm().isMidTidSeg()).count();
        if (filterSaleMerUsd > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.USD);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.USD;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        long filterSaleMerEur = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.EUR) && !formInfo.getDeviceForm().isMidTidSeg()).count();
        if (filterSaleMerEur > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.EUR);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.EUR;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        long filterSaleMerGbp = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.GBP) && !formInfo.getDeviceForm().isMidTidSeg()).count();
        if (filterSaleMerGbp > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.GBP);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.GBP;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        long filterSaleMerJpy = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.JPY) && !formInfo.getDeviceForm().isMidTidSeg()).count();
        if (filterSaleMerJpy > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.JPY);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.JPY;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        long filterSaleMerAud = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.AUD) && !formInfo.getDeviceForm().isMidTidSeg()).count();
        if (filterSaleMerAud > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.AUD);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.AUD;
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        long filterSaleMerCad = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.AUD) && !formInfo.getDeviceForm().isMidTidSeg()).count();
        if (filterSaleMerCad > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.AUD);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.AUD;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        long filterSaleMerChf = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.CHF) && !formInfo.getDeviceForm().isMidTidSeg()).count();
        if (filterSaleMerChf > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.CHF);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.CHF;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        long filterSaleMerCny = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.CNY) && !formInfo.getDeviceForm().isMidTidSeg()).count();
        if (filterSaleMerCny > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.CNY);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.CNY;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        long filterSaleMerSgd = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.SGD) && !formInfo.getDeviceForm().isMidTidSeg()).count();
        if (filterSaleMerSgd > 2) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.SGD);
            merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.SGD;
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        return null;
    }

    private String comBankImeiScanAndPayValidation(FormInfo formInfo, Model model) {

        //get scan & pay merchant count
        long filterIPhoneScanAndPayMerLkr = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.LKR) && pm.isIphoneImei() && formInfo.getDeviceForm().getBankName().equals(BankTypes.COM_BANK)).count();

        if (filterIPhoneScanAndPayMerLkr == 1) {

            long filtercomDccLkr = formInfo.getMerchants().stream()
                    .filter(pm -> pm.isSelectMerchant() && pm.isDcc() && pm.getCurrency().equals(CurrentyType.LKR) && formInfo.getDeviceForm().getBankName().equals(BankTypes.COM_BANK)).count();
            if (filtercomDccLkr > 1) {
                model.addAttribute(MESSAGE, EventMessage.Should_Add_ONE_DCC_LKR_IPHONE_IMEI_MERCHANT);
                merchantError = EventMessage.Should_Add_ONE_DCC_LKR_IPHONE_IMEI_MERCHANT;
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            long filtercomNonDccLkr = formInfo.getMerchants().stream()
                    .filter(pm -> pm.isSelectMerchant() && !pm.isDcc() && !pm.isIphoneImei() && pm.getCurrency().equals(CurrentyType.LKR) && formInfo.getDeviceForm().getBankName().equals(BankTypes.COM_BANK)).count();
            if (filtercomNonDccLkr != 1) {
                model.addAttribute(MESSAGE, EventMessage.Should_Add_ONE_NON_DCC_LKR_IPHONE_IMEI_MERCHANT);
                merchantError = EventMessage.Should_Add_ONE_NON_DCC_LKR_IPHONE_IMEI_MERCHANT;
                return Endpoint.PAGE_OPERATION_FAIL;
            }
            //get i phone lkr merchant count
            long filterIPhoneMerLkr = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.LKR) && formInfo.getDeviceForm().getBankName().equals(BankTypes.COM_BANK)).count();
            if (filterIPhoneMerLkr > 3) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_LKR_CURRENCY_IN_COMMERCIAL_BANK);
                merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_LKR_CURRENCY_IN_COMMERCIAL_BANK;
                return Endpoint.PAGE_OPERATION_FAIL;
            }

        } else if (filterIPhoneScanAndPayMerLkr > 1) {
            model.addAttribute(MESSAGE, EventMessage.Should_Add_ONE_LKR_IPHONE_IMEI_MERCHANT);
            merchantError = EventMessage.Should_Add_ONE_LKR_IPHONE_IMEI_MERCHANT;
            return Endpoint.PAGE_OPERATION_FAIL;
        } else {
            long filtercomSaleMerLkr = formInfo.getMerchants().stream()
                    .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.getCurrency().equals(CurrentyType.LKR) && !formInfo.getDeviceForm().isMidTidSeg()).count();
            if (filtercomSaleMerLkr > 2) {
                model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.LKR);
                merchantError = EventMessage.ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY + " : " + CurrentyType.LKR;
                return Endpoint.PAGE_OPERATION_FAIL;
            }
        }
        return null;
    }

    private String defaultAndSelectedMerchantsValidation(FormInfo formInfo, Model model) {

        long filterSaleMer = formInfo.getMerchants().stream()
                .filter(pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant()).count();

        long filterSaleDefaultMer = formInfo.getMerchants().stream().filter(
                        pm -> pm.getType().equals(MerchantTypes.SALE) && pm.isSelectMerchant() && pm.isDefaultMerchant())
                .count();
        long filterDCCasDefaultMer = formInfo.getMerchants().stream().filter(
                        pm -> pm.isSelectMerchant() && pm.isDefaultMerchant() && pm.isDcc())
                .count();
        logger.info("#SaleMerchants> {}   | #SalesDefaultMerchants> {}", filterSaleMer, filterSaleDefaultMer);
        if (filterSaleMer == 0) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_NO_SALE_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        if (filterSaleMer > 0 && filterSaleDefaultMer == 0) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_NO_SALE_DEFAULT_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        if (filterSaleMer > 0 && filterSaleDefaultMer > 1) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_MULTIPLE_SALE_DEFAULT_MERCHANTS);
            return Endpoint.PAGE_OPERATION_FAIL;
        }
        if (filterDCCasDefaultMer > 0) {
            model.addAttribute(MESSAGE, EventMessage.ERROR_DCC_MERCHANT_AS_DEFAULT_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        return null;
    }

    //check modify merchant in existing profile or not
//    private boolean checkMerchantInProfile(FormInfo formInfo, MerchantForm merchantForm) {
//        //validation check
//        boolean ProfileValidate = CheckProfileValidate();
//        if (ProfileValidate) {
//            List<ProfileMerchant> ProfileMerchant = formInfo.getProfileMerchants();
//            if (!ProfileMerchant.isEmpty()) {
//                for (ProfileMerchant p : ProfileMerchant) {
//                    if (Objects.equals(p.getMerchantId(), merchantForm.getMerchantId())) return true;
//                }
//            }
//        }
//        return false;
//    }

}