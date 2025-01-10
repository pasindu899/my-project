package com.aiken.pos.admin.api.controller;

import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.api.dto.*;
import com.aiken.pos.admin.config.UserRoleMapper;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.constant.EventMessage;
import com.aiken.pos.admin.constant.EventTypes;
import com.aiken.pos.admin.constant.MerchantTypes;
import com.aiken.pos.admin.model.Device;
import com.aiken.pos.admin.model.Event;
import com.aiken.pos.admin.model.Merchant;
import com.aiken.pos.admin.service.DeviceService;
import com.aiken.pos.admin.service.EventService;
import com.aiken.pos.admin.web.form.OfflineUserForm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Check Update API Controller
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-29
 */

@RestController
@RequestMapping(value = Endpoint.URL_CHECK_UPDATE)
public class CheckUpdateApiController {

    private Logger logger = LoggerFactory.getLogger(DeviceApiController.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private EventService eventService;

    @GetMapping("/{serialNo}/{eventType}/{eventId}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<CheckUpdateDto> getDeviceBySerial(@PathVariable("serialNo") String serialNo,
                                                            @PathVariable("eventType") String eventType,
                                                            @PathVariable("eventId") Integer eventId) throws Exception {

        logger.info("EVENT: GET-UPDATE:\t" + " Get Update from Event.....|START|");
        logger.info("EVENT: GET-UPDATE:\t" + "Request:[" + "Serial No:" + serialNo + " | Event Type:" + eventType + " | " + " | Event Id:" + eventId + "]");
        //logger.info("Request:[" + "Serial No:" + serialNo  + " | Event Type:" + eventType + " | " + " | Event Id:" + eventId + "]");

        if (StringUtil.isNullOrWhiteSpace(serialNo)
                || StringUtil.isNullOrWhiteSpace(eventType)
                || eventId == null) {
            // invalid params
            logger.info("EVENT: GET-UPDATE:\t" + " Bad Request");
            return new ResponseEntity<CheckUpdateDto>(HttpStatus.BAD_REQUEST);
        }

        if (eventService.existsById(eventId)) {
            logger.info("EVENT: GET-UPDATE:\t" + " Find Event ID:\t + Event Id:\" + eventId");
            Device device = deviceService.findDeviceBySerial(serialNo);

            Event event = eventService.findEventbyId(eventId);
            logger.info("EVENT: GET-UPDATE:\t" + " Find Event Data:\t + Event ID:" + event.getEventId());
            CheckUpdateDto checkUpdateDto = new CheckUpdateDto();

            switch (eventType) {
                case EventTypes.AUTO_SETTLE:
                    checkUpdateDto.setAutoSettle(device.isAutoSettle());
                    checkUpdateDto.setAutoSettleTime(device.getAutoSettleTime());
                    break;
                case EventTypes.FORCE_SETTLE:
                    checkUpdateDto.setForceSettle(device.isForceSettle());
                    break;
                case EventTypes.NO_SETTLE:
                    checkUpdateDto.setNoSettle(device.isNoSettle());
                    break;
                case EventTypes.CLEAR_BATCH:
                    checkUpdateDto.setClearBatch(true);
                    if (event.getClearMerchant() != null && !event.getClearMerchant().equalsIgnoreCase("0")) {
                        checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNoFilterByMerchantId(serialNo, event.getClearMerchant()));
                        checkUpdateDto.setClearBatchAction(EventMessage.CLEAR_FOR_SELECTED_MERCHANT);
                    } else
                        checkUpdateDto.setClearBatchAction(EventMessage.CLEAR_FOR_ALL_MERCHANTS);
                    break;
                case EventTypes.CLEAR_REVERSAL:
                    checkUpdateDto.setClearRevaersal(true);
                    break;
                case EventTypes.NAME_CHANGE:
                    checkUpdateDto.setProfiles(loadProfileToProfileDto(device));
                    checkUpdateDto.setMerchantName(device.getMerchantName());
                    checkUpdateDto.setProfiles(loadProfileMerchantInfo(device));
                    //loadProfileMerchantInfo
                    break;
                case EventTypes.ADDRESS_CHANGE:
                    checkUpdateDto.setMerchantAddress(device.getMerchantAddress());
                    checkUpdateDto.setProfiles(loadProfileToProfileDto(device));
                    break;
                case EventTypes.QR_PARAM_CHANGE:
                    checkUpdateDto.setQrParamChange(true);
                    checkUpdateDto.setQrMerchants(loadQrMerchantData(device, false));
                    break;
                case EventTypes.QR_MID_TID_CHANGE:
                    checkUpdateDto.setQrMidTidChange(true);
                    checkUpdateDto.setQrMerchants(loadQrMerchantData(device, true));
                    break;
                case EventTypes.ECR_SALE:
                    checkUpdateDto.setEcrSale(device.isEcr());
                    checkUpdateDto.setEcrSettings(loadECRSettings(device));
                    break;
                case EventTypes.ECR_QR:
                    checkUpdateDto.setEcrQr(device.isEcrQr());
                    break;
                case EventTypes.MANUAL_KEY_IN:
                    checkUpdateDto.setKeyIn(device.isKeyIn());
                    checkUpdateDto.setKeyInForAmex(device.isKeyInAmex());
                    break;
                case EventTypes.MANUAL_KEY_IN_AMEX_ONLY:
                    checkUpdateDto.setKeyInForAmex(device.isKeyInAmex());
                    break;
                case EventTypes.ADD_EPP:
                    checkUpdateDto.setMerchantName(device.getMerchantName());
                    checkUpdateDto.setMerchantAddress(device.getMerchantAddress());
                    checkUpdateDto.setAddEpp(true);
                    checkUpdateDto.setEppMerchants(loadEppMerchantData(device));
                    break;
                case EventTypes.DELETE_EPP:
                    checkUpdateDto.setMerchantName(device.getMerchantName());
                    checkUpdateDto.setMerchantAddress(device.getMerchantAddress());
                    checkUpdateDto.setDeleteEpp(true);
                    checkUpdateDto.setEppMerchants(loadEppMerchantData(device));
                    break;
                case EventTypes.EPP_MID_TID_CHANGE:
                    checkUpdateDto.setEppSaleMidTidChange(true);
                    checkUpdateDto.setEppMerchants(loadEppMerchantData(device));
                    break;
                case EventTypes.DIGITAL_SIGNATURE:
                    checkUpdateDto.setSignature(device.isSignature());
                    break;
                case EventTypes.DEBUG_MODE:
                    checkUpdateDto.setDebugMode(device.isDebugMode());
                    break;
                case EventTypes.ACTIVITY_TRACKER:
                    checkUpdateDto.setActivityTracker(device.isActivityTracker());
                    break;
                case EventTypes.ACTIVITY_REPORT:
                    checkUpdateDto.setReportFromDate(event.getReportFromDate());
                    checkUpdateDto.setReportToDate(event.getReportToDate());
                    checkUpdateDto.setType(event.getType());
                    break;
                case EventTypes.QR_REFUND:
                    checkUpdateDto.setQrRefund(device.isQrRefund());
                    break;
                case EventTypes.REVERSAL_HISTORY:
                    checkUpdateDto.setReversalHistory(device.isReversalHistory());
                    break;
                case EventTypes.PUSH_NOTIFICATION:
                    checkUpdateDto.setPushNotification(device.isPushNotification());
                    break;
                case EventTypes.CONFIG_AMEX:
                    checkUpdateDto.setEnableAmex(device.isEnableAmex());
                    break;
                case EventTypes.ADD_PROFILE:
                    checkUpdateDto.setAddProfile(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    checkUpdateDto.setProfiles(loadProfileToProfileDto(device));
                    break;
                case EventTypes.REMOVE_PROFILE:
                    checkUpdateDto.setRemoveProfile(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    checkUpdateDto.setProfiles(loadProfileToProfileDto(device));
                    break;
                case EventTypes.MODIFY_PROFILE:
                    checkUpdateDto.setModifyProfile(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    checkUpdateDto.setMidTidSeg(device.isMidTidSeg());
                    checkUpdateDto.setProfiles(loadProfileToProfileDto(device));
                    break;
                case EventTypes.ONE_CLICK_SETUP:
                    checkUpdateDto.setOneClickSetup(true);
                    checkUpdateDto.setMidTidSeg(device.isMidTidSeg());
                    checkUpdateDto.setOneClickData(loadOneClickSetupDto(serialNo));
                    break;
                case EventTypes.SALE_DCC:
                    checkUpdateDto.setSaleDcc(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    break;
                case EventTypes.SALE_OFFLINE:
                    checkUpdateDto.setSaleOffline(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    break;
                case EventTypes.SALE_PRE_AUTH:
                    checkUpdateDto.setSalePreAuth(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    break;
                case EventTypes.AMEX_DCC:
                    checkUpdateDto.setAmexDcc(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    break;
                case EventTypes.AMEX_OFFLINE:
                    checkUpdateDto.setAmexOffline(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    break;
                case EventTypes.AMEX_PRE_AUTH:
                    checkUpdateDto.setAmexPreAuth(false);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    break;
                case EventTypes.QR_VID_CID_CHANGE:
                    checkUpdateDto.setQrVidCidChange(true);
                    checkUpdateDto.setQrMerchants(loadQrMerchantData(device, false));
                    break;
                case EventTypes.SALE_MID_TID_CHANGE:
                    checkUpdateDto.setSaleMidTidChange(true);

                    if (event.getMerchantTransType().equalsIgnoreCase(MerchantTypes.DEFAULT_SALE) || event.getMerchantTransType().isEmpty() || event.getMerchantTransType() == null) {
                        checkUpdateDto.setSaleMerchants(loadMerchantData(device, MerchantTypes.SALE));
                    } else checkUpdateDto.setSaleMerchants(loadSaleMerchantData(device, event.getMerchantTransType()));

                    break;
                case EventTypes.AMEX_MID_TID_CHANGE:
                    checkUpdateDto.setAmexMidTidChange(true);
                    checkUpdateDto.setAmexMerchants(loadMerchantData(device, MerchantTypes.AMEX));
                    break;
                case EventTypes.BIN_BLOCK_CONFIG:
                    checkUpdateDto.setBinBlock(true);
                    checkUpdateDto.setBinBlockData(loadBinBlockData(device));
                    break;
                case EventTypes.AUTO_REVERSAL:
                    checkUpdateDto.setAutoRevaersal(device.isAutoReversal());
                    break;
                case EventTypes.PRINTLESS:
                    checkUpdateDto.setPrintless(device.isPrintless());
                    break;
                case EventTypes.NETWORK_MODE_CHANGE:
                    checkUpdateDto.setNetworkChange(true);
                    checkUpdateDto.setNetworkMode(device.getNetwork());
                    break;
                case EventTypes.CONFIG_AUTH_SERVER:
                    checkUpdateDto.setAuthServerConfig(true);
                    checkUpdateDto.setAuthServerSettings(loadAuthSettings(device));
                    break;
                case EventTypes.DISABLE_FRGN_PMNTS_FOR_LKR_CARDS:
                    checkUpdateDto.setDisableFrgnPmntForLkrCard(device.isLkrDefaultCurr());
                    break;
                case EventTypes.VOID_AND_SETTLEMENT_PASSWORD:
                    checkUpdateDto.setVoidSettlePwd(device.getVoidPwd());
                    checkUpdateDto.setNewVoidPwd(device.getNewVoidPwd());
                    checkUpdateDto.setNewSettlementPwd(device.getNewSettlementPwd());
                    break;
                case EventTypes.DIFFERENT_MID_TID_SETTINGS:
                    //checkUpdateDto.setDiffSaleMidTid(true);
                    //checkUpdateDto.setDifSaleMidTidType(event.getMerchantTransType());
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    checkUpdateDto.setProfiles(loadProfileToProfileDto(device));
                    break;
                case EventTypes.MANUAL_KEY_IN_FOR_PRE_AUTH:
                    checkUpdateDto.setKeyInForPreAuth(device.isMkiPreAuth());
                    break;
                case EventTypes.MANUAL_KEY_IN_FOR_OFFLINE:
                    checkUpdateDto.setKeyInForOffline(device.isMkiOffline());
                    break;
                case EventTypes.SIGNATURE_PIN_PRIORITY:
                    checkUpdateDto.setEnablePinPriority(device.isSignPriority());
                    break;
                case EventTypes.BLOCK_MAG_TRANSACTIONS:
                    checkUpdateDto.setBlockMag(device.isBlockMag());
                    break;
                case EventTypes.ENABLE_CUSTOMER_RECEIPT:
                    checkUpdateDto.setCustomerReceipt(device.isCustomerReceipt());
                    break;
                case EventTypes.ENABLE_IMEI_SCAN:
                    checkUpdateDto.setImeiScan(device.isImeiScan());
                    break;
                case EventTypes.ENABLE_COMMISSION_RATE:
                    checkUpdateDto.setCommission(device.isCommission());
                    checkUpdateDto.setCommissionRate(device.getCommissionRate());
                    break;
                case EventTypes.CONFIGURE_OFFLINE_USERS:
                    if (device.getOfflineUser() != null && !device.getOfflineUser().isEmpty()) {
                        ObjectMapper mapper = new ObjectMapper();
                        List<OfflineUserForm> userList = mapper.readValue(device.getOfflineUser().toString(), new TypeReference<List<OfflineUserForm>>() {
                        });
                        checkUpdateDto.setOfflineUser(userList);
                    } else {
                        checkUpdateDto.setOfflineUser(null);
                    }
                    break;
                case EventTypes.SALE_JCB:
                    checkUpdateDto.setSaleJCB(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    break;
                case EventTypes.EVENT_AUTO_UPDATE:
                    checkUpdateDto.setEventAutoUpdate(device.isEventAutoUpdate());
                    break;
                case EventTypes.ENABLE_SANHINDA_PAYMENTS:
                    checkUpdateDto.setEnableSanhindaPay(device.isEnableSanhindaPay());
                    break;
                case EventTypes.ONE_TIME_SETTING_PASSWORD:
                    checkUpdateDto.setOnetimePassword(event.getOnetimePassword());
                    break;
                case EventTypes.ENABLE_REFERENCE_DATA:
                    checkUpdateDto.setRefNumberEnable(device.isRefNumberEnable());
                    break;
                case EventTypes.ENABLE_TLE_PROFILE:
                    checkUpdateDto.setTleProfilesEnable(device.isTleProfilesEnable());
                    break;
                default:
                    break;
            }
            logger.info("EVENT: GET-UPDATE:\t" + " Get Update from Event.....|END-SUCCESS|");
            return new ResponseEntity<CheckUpdateDto>(checkUpdateDto, HttpStatus.OK);
        }
        logger.info("EVENT: GET-UPDATE:\t" + " Get Update from Event.....|END-NOT_FOUND|");
        return new ResponseEntity<CheckUpdateDto>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{serialNo}/{dateTime}/{eventType}/{eventId}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<CheckUpdateDto> getDeviceBySerial(@PathVariable("serialNo") String serialNo,
                                                            @PathVariable("dateTime") String dateTime,
                                                            @PathVariable("eventType") String eventType,
                                                            @PathVariable("eventId") Integer eventId) throws Exception {

        logger.info("Execute Get Token Operation...");
        logger.info("Request:[" + "Serial No:" + serialNo + " | Date Time:" + dateTime + " | Event Type:" + eventType +
                " | " + " | Event Id:" + eventId + "]");

        if (StringUtil.isNullOrWhiteSpace(serialNo)
                || StringUtil.isNullOrWhiteSpace(dateTime)
                || StringUtil.isNullOrWhiteSpace(eventType)
                || eventId == null || eventId == 0) {
            // invalid params
            return new ResponseEntity<CheckUpdateDto>(HttpStatus.BAD_REQUEST);
        }

        List<Event> events = eventService.findAllEventsByParams(serialNo.trim(), dateTime.trim());
        // not found
        if (events == null || events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Event event = events.stream()
                .filter(item -> eventId.equals(item.getEventId()))
                .findAny()
                .orElse(null);

        long eventCount = events.stream().filter(ev -> ev.getEventId() == eventId).count();
        logger.info("Event Count: " + eventCount);

        if (eventCount <= 0) {
            // no event found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } else {
            Device device = deviceService.findDeviceBySerial(serialNo);
            CheckUpdateDto checkUpdateDto = new CheckUpdateDto();

            switch (eventType) {
                case EventTypes.AUTO_SETTLE:
                    checkUpdateDto.setAutoSettle(device.isAutoSettle());
                    checkUpdateDto.setAutoSettleTime(device.getAutoSettleTime());
                    break;
                case EventTypes.FORCE_SETTLE:
                    checkUpdateDto.setForceSettle(device.isForceSettle());
                    break;
                case EventTypes.CLEAR_BATCH:
                    checkUpdateDto.setClearBatch(true);
                    if (event.getClearMerchant() != null && !event.getClearMerchant().equalsIgnoreCase("0")) {
                        checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNoFilterByMerchantId(serialNo, event.getClearMerchant()));
                        checkUpdateDto.setClearBatchAction(EventMessage.CLEAR_FOR_SELECTED_MERCHANT);
                    } else
                        checkUpdateDto.setClearBatchAction(EventMessage.CLEAR_FOR_ALL_MERCHANTS);
                    break;
                case EventTypes.CLEAR_REVERSAL:
                    checkUpdateDto.setClearRevaersal(true);
                    break;
                case EventTypes.NAME_CHANGE:
                    checkUpdateDto.setMerchantName(device.getMerchantName());
                    checkUpdateDto.setProfiles(loadProfileToProfileDto(device));
                    break;
                case EventTypes.ADDRESS_CHANGE:
                    checkUpdateDto.setMerchantAddress(device.getMerchantAddress());
                    checkUpdateDto.setProfiles(loadProfileToProfileDto(device));
                    break;
                case EventTypes.QR_PARAM_CHANGE:
                    checkUpdateDto.setQrParamChange(true);
                    checkUpdateDto.setQrMerchants(loadQrMerchantData(device, false));
                    break;
                case EventTypes.QR_MID_TID_CHANGE:
                    checkUpdateDto.setQrMidTidChange(true);
                    checkUpdateDto.setQrMerchants(loadQrMerchantData(device, true));
                    break;
                case EventTypes.ECR_SALE:
                    checkUpdateDto.setEcrSale(device.isEcr());
                    checkUpdateDto.setEcrSettings(loadECRSettings(device));
                    break;
                case EventTypes.ECR_QR:
                    checkUpdateDto.setEcrQr(device.isEcrQr());
                    break;
                case EventTypes.MANUAL_KEY_IN:
                    checkUpdateDto.setKeyIn(device.isKeyIn());
                    break;
                case EventTypes.MANUAL_KEY_IN_AMEX_ONLY:
                    checkUpdateDto.setKeyInForAmex(device.isKeyInAmex());
                    break;
                case EventTypes.ADD_EPP:
                    checkUpdateDto.setMerchantName(device.getMerchantName());
                    checkUpdateDto.setMerchantAddress(device.getMerchantAddress());
                    checkUpdateDto.setAddEpp(true);
                    checkUpdateDto.setEppMerchants(loadEppMerchantData(device));
                    break;
                case EventTypes.DELETE_EPP:
                    checkUpdateDto.setMerchantName(device.getMerchantName());
                    checkUpdateDto.setMerchantAddress(device.getMerchantAddress());
                    checkUpdateDto.setDeleteEpp(true);
                    checkUpdateDto.setEppMerchants(loadEppMerchantData(device));
                    break;
                case EventTypes.EPP_MID_TID_CHANGE:
                    checkUpdateDto.setEppSaleMidTidChange(true);
                    checkUpdateDto.setEppMerchants(loadEppMerchantData(device));
                    break;
                case EventTypes.DIGITAL_SIGNATURE:
                    checkUpdateDto.setSignature(device.isSignature());
                    break;
                case EventTypes.DEBUG_MODE:
                    checkUpdateDto.setDebugMode(device.isDebugMode());
                    break;
                case EventTypes.ACTIVITY_TRACKER:
                    checkUpdateDto.setActivityTracker(device.isActivityTracker());
                    break;
                case EventTypes.ACTIVITY_REPORT:
                    checkUpdateDto.setReportFromDate(event.getReportFromDate());
                    checkUpdateDto.setReportToDate(event.getReportToDate());
                    checkUpdateDto.setType(event.getType());
                    break;
                case EventTypes.QR_REFUND:
                    checkUpdateDto.setQrRefund(device.isQrRefund());
                    break;
                case EventTypes.REVERSAL_HISTORY:
                    checkUpdateDto.setReversalHistory(device.isReversalHistory());
                    break;
                case EventTypes.QR_VID_CID_CHANGE:
                    checkUpdateDto.setQrVidCidChange(true);
                    checkUpdateDto.setQrMerchants(loadQrMerchantData(device, false));
                    break;
                case EventTypes.SALE_MID_TID_CHANGE:
                    checkUpdateDto.setSaleMidTidChange(true);
                    //checkUpdateDto.setSaleMerchants(loadMerchantData(device,MerchantTypes.SALE));
                    if (event.getMerchantTransType().equalsIgnoreCase(MerchantTypes.DEFAULT_SALE) || event.getMerchantTransType().isEmpty() || event.getMerchantTransType() == null) {
                        checkUpdateDto.setSaleMerchants(loadMerchantData(device, MerchantTypes.SALE));
                    } else checkUpdateDto.setSaleMerchants(loadSaleMerchantData(device, event.getMerchantTransType()));
                    break;
                case EventTypes.AMEX_MID_TID_CHANGE:
                    checkUpdateDto.setAmexMidTidChange(true);
                    checkUpdateDto.setAmexMerchants(loadMerchantData(device, MerchantTypes.AMEX));
                    break;
                case EventTypes.BIN_BLOCK_CONFIG:
                    checkUpdateDto.setBinBlock(true);
                    checkUpdateDto.setBinBlockData(loadBinBlockData(device));
                    break;
                case EventTypes.AUTO_REVERSAL:
                    checkUpdateDto.setAutoRevaersal(device.isAutoReversal());
                    break;
                case EventTypes.PRINTLESS:
                    checkUpdateDto.setPrintless(device.isPrintless());
                    break;
                case EventTypes.NETWORK_MODE_CHANGE:
                    checkUpdateDto.setNetworkChange(true);
                    checkUpdateDto.setNetworkMode(device.getNetwork());
                    break;
                case EventTypes.CONFIG_AUTH_SERVER:
                    checkUpdateDto.setAuthServerConfig(true);
                    checkUpdateDto.setAuthServerSettings(loadAuthSettings(device));
                    break;
                case EventTypes.DISABLE_FRGN_PMNTS_FOR_LKR_CARDS:
                    checkUpdateDto.setDisableFrgnPmntForLkrCard(device.isLkrDefaultCurr());
                    break;
                case EventTypes.VOID_AND_SETTLEMENT_PASSWORD:
                    checkUpdateDto.setVoidSettlePwd(device.getVoidPwd());
                    checkUpdateDto.setNewVoidPwd(device.getNewVoidPwd());
                    checkUpdateDto.setNewSettlementPwd(device.getNewSettlementPwd());
                    break;
                case EventTypes.DIFFERENT_MID_TID_SETTINGS:
                    //checkUpdateDto.setDiffSaleMidTid(true);
                    //checkUpdateDto.setDifSaleMidTidType(event.getMerchantTransType());
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    checkUpdateDto.setProfiles(loadProfileToProfileDto(device));
                    break;
                case EventTypes.MANUAL_KEY_IN_FOR_PRE_AUTH:
                    checkUpdateDto.setKeyInForPreAuth(device.isMkiPreAuth());
                    break;
                case EventTypes.MANUAL_KEY_IN_FOR_OFFLINE:
                    checkUpdateDto.setKeyInForOffline(device.isMkiOffline());
                    break;
                case EventTypes.SIGNATURE_PIN_PRIORITY:
                    checkUpdateDto.setEnablePinPriority(device.isSignPriority());
                    break;
                case EventTypes.BLOCK_MAG_TRANSACTIONS:
                    checkUpdateDto.setBlockMag(device.isBlockMag());
                    break;
                case EventTypes.ENABLE_CUSTOMER_RECEIPT:
                    checkUpdateDto.setCustomerReceipt(device.isCustomerReceipt());
                    break;
                case EventTypes.ENABLE_SANHINDA_PAYMENTS:
                    checkUpdateDto.setEnableSanhindaPay(device.isEnableSanhindaPay());
                    break;
                case EventTypes.ENABLE_REFERENCE_DATA:
                    checkUpdateDto.setRefNumberEnable(device.isRefNumberEnable());
                    break;
                case EventTypes.ENABLE_TLE_PROFILE:
                    checkUpdateDto.setTleProfilesEnable(device.isTleProfilesEnable());
                    break;
                case EventTypes.SALE_JCB:
                    checkUpdateDto.setSaleJCB(true);
                    checkUpdateDto.setMerchants(loadDeviceMerchantsBySerialNo(serialNo));
                    break;
                default:
                    break;
            }

            return new ResponseEntity<CheckUpdateDto>(checkUpdateDto, HttpStatus.OK);
        }
    }

    private List<CustomQrMerchantDto> loadQrMerchantData(Device device, boolean isMidTidChange) throws Exception {
        List<CustomQrMerchantDto> qrMerchants = new ArrayList<>();
        if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
            for (Merchant m : device.getMerchants()) {
                if (m.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                    // set merchant data
                    CustomQrMerchantDto mDto = new CustomQrMerchantDto();
                    mDto.setType(m.getCategory());
                    mDto.setMerchantId(m.getMerchantId());
                    if (isMidTidChange) {
                        mDto.setMid(m.getMid());
                        mDto.setTid(m.getTid());
                    }
                    mDto.setCurrency(m.getCurrency());
                    mDto.setDescription(m.getDescription());
                    // set q+ data
                    if (!isMidTidChange && m.getScanParam() != null) {
                        mDto.setMerchantUserId(m.getScanParam().getMerchantUserId());
                        mDto.setMerchantPassword(m.getScanParam().getMerchantPassword());
                        mDto.setChecksumKey(m.getScanParam().getChecksumKey());
                        mDto.setVid(m.getScanParam().getVid());
                        mDto.setCid(m.getScanParam().getCid());
                        mDto.setQrRefId(m.getScanParam().isQrRefId());
                    }

                    qrMerchants.add(mDto);
                }
            }
        }
        return qrMerchants;
    }

    private List<CustomEppMerchantDto> loadEppMerchantData(Device device) throws Exception {
        List<CustomEppMerchantDto> eppMerchants = new ArrayList<>();
        if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
            for (Merchant m : device.getMerchants()) {
                if (m.getCategory().equalsIgnoreCase(MerchantTypes.EPP)) {
                    // set merchant data
                    CustomEppMerchantDto mDto = new CustomEppMerchantDto();
                    mDto.setMerchantId(m.getMerchantId());
                    mDto.setType(m.getCategory());
                    mDto.setMonth(m.getMonth());
                    mDto.setMid(m.getMid());
                    mDto.setTid(m.getTid());
                    mDto.setCurrency(m.getCurrency());
                    mDto.setDescription(m.getDescription());

                    eppMerchants.add(mDto);
                }
            }
        }
        return eppMerchants;
    }


    private List<CustomCommonMerchantDto> loadMerchantData(Device device, String merchatType) throws Exception {
        List<CustomCommonMerchantDto> comMerchants = new ArrayList<>();
        if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
            for (Merchant m : device.getMerchants()) {
                if (m.getCategory().equalsIgnoreCase(merchatType)) {
                    // set merchant data
                    CustomCommonMerchantDto mDto = new CustomCommonMerchantDto();
                    mDto.setMerchantId(m.getMerchantId());
                    mDto.setType(m.getCategory());
                    mDto.setMerchantType(m.getMerchantType());
                    mDto.setMid(m.getMid());
                    mDto.setTid(m.getTid());
                    comMerchants.add(mDto);
                }
            }
        }
        return comMerchants;
    }

    private List<CustomCommonMerchantDto> loadSaleMerchantData(Device device, String merchantType) throws Exception {
        List<CustomCommonMerchantDto> comMerchants = new ArrayList<>();
        if (device.getMerchants() != null && !device.getMerchants().isEmpty() && merchantType != null && !merchantType.isEmpty()) {
            for (Merchant m : device.getMerchants()) {
                if (m.getCategory().equalsIgnoreCase(MerchantTypes.SALE) && (m.getMerchantType().equalsIgnoreCase(merchantType))) {
                    // set merchant data
                    CustomCommonMerchantDto mDto = new CustomCommonMerchantDto();
                    mDto.setMerchantId(m.getMerchantId());
                    mDto.setType(m.getCategory());
                    mDto.setMerchantType(m.getMerchantType());
                    mDto.setMid(m.getMid());
                    mDto.setTid(m.getTid());
                    comMerchants.add(mDto);
                }
            }
        }
        return comMerchants;
    }


    private CustomEcrSettingsDto loadECRSettings(Device device) throws Exception {
        CustomEcrSettingsDto ecrSettings = new CustomEcrSettingsDto();

        try {
            ecrSettings.setCardTypeValidation(device.isCardTypeValidation());
            ecrSettings.setSaleReceipt(device.isSaleReceipt());
            ecrSettings.setCurrencyFromBin(device.isCurrencyFromBin());
            ecrSettings.setCurrencyFromCard(device.isCurrencyFromCard());
            ecrSettings.setProceedWithLkr(device.isProceedWithLkr());
            ecrSettings.setCardTap(device.isCardTap());
            ecrSettings.setCardInsert(device.isCardInsert());
            ecrSettings.setCardSwipe(device.isCardSwipe());
            ecrSettings.setDccPayload(device.isDccPayload());
            ecrSettings.setEcrIp(device.getEcrIp());
            ecrSettings.setEcrPort(device.getEcrPort());
            ecrSettings.setEcrAuthToken(device.isEcrAuthToken());
            ecrSettings.setTranToSim(device.isTranToSim());
            ecrSettings.setEcrWifi(device.isEcrWifi());

        } catch (Exception ex) {
            logger.warn("ECR Settings Error :" + ex.getMessage().toString());
        }

        return ecrSettings;
    }


    private CustomAuthServerConfigDto loadAuthSettings(Device device) throws Exception {
        CustomAuthServerConfigDto authSettings = new CustomAuthServerConfigDto();

        try {
            authSettings.setMerchantPortal(device.isMerchantPortal());
            authSettings.setClientCredentials(device.isClientCredentials());
            authSettings.setResendVoid(device.isResendVoid());
            authSettings.setPrintReceipt(device.isPrintReceipt());

        } catch (Exception ex) {
            logger.warn("Auth Settings Error :" + ex.getMessage().toString());
        }

        return authSettings;
    }


    private List<BinConfigDto> loadBinBlockData(Device device) throws Exception {

        //Set BIN Config Data
        List<BinConfigDto> binConfigDto = new ArrayList<>();
        if (device.getBinConfig() != null && !device.getBinConfig().isEmpty()) {
            device.getBinConfig().forEach(m -> {
                BinConfigDto conf = new BinConfigDto();
                conf.setAction(m.getAction());
                conf.setBinEndFrom(m.getBinEnd());
                conf.setBinStartFrom(m.getBinStartFrom());
                conf.setCardType(m.getCardType());
                conf.setMerchantId(m.getMerchantId());
                conf.setMid(m.getMid());
                conf.setTid(m.getTid());
                conf.setTransType(m.getTransType());
                conf.setType(m.getType());

                binConfigDto.add(conf);
            });

        }
        return binConfigDto;
    }


    private List<ProfilesDto> loadProfileToProfileDto(Device device) {


        List<ProfilesDto> profileList = new ArrayList<ProfilesDto>();
        if
        (device.getProfiles() != null) {
            device.getProfiles().forEach(profile -> {
                ProfilesDto profileDto = new ProfilesDto();
                profileDto.setAmexCnt(profile.isAmexCnt());
                profileDto.setAmexCntls(profile.isAmexCntls());
                profileDto.setAmexCntslTrxn(profile.getAmexCntslTrxn());
                profileDto.setAmexNoCvm(profile.getAmexNoCvm());
                profileDto.setpDefault(profile.isDefault());
                profileDto.setJcbCnt(profile.isJcbCnt());
                profileDto.setJcbCntls(profile.isJcbCntls());
                profileDto.setJcbCntslTrxn(profile.getJcbCntslTrxn());
                profileDto.setJcbNoCvm(profile.getJcbNoCvm());
                profileDto.setMcCnt(profile.isMcCnt());
                profileDto.setMcCntls(profile.isMcCntls());
                profileDto.setMcCntslTrxn(profile.getMcCntslTrxn());
                profileDto.setMcNoCvm(profile.getMcNoCvm());
                profileDto.setmAdrs(profile.getMerchantAdrs());
                profileDto.setmName(profile.getMerchantName());
                profileDto.setpId(profile.getProfileId());
                profileDto.setpName(profile.getProfileName());
                profileDto.setStatus(profile.getStatus());
                profileDto.setUpayCnt(profile.isUpayCnt());
                profileDto.setUpayCntls(profile.isUpayCntls());
                profileDto.setUpayCntslTrxn(profile.getUpayCntslTrxn());
                profileDto.setUpayNoCvm(profile.getVisaNoCvm());
                profileDto.setVisaCnt(profile.isVisaCnt());
                profileDto.setVisaCntls(profile.isVisaCntls());
                profileDto.setVisaCntslTrxn(profile.getVisaCntslTrxn());
                profileDto.setVisaNoCvm(profile.getVisaNoCvm());
                profileDto.setCustomerCopy(profile.isCustomerCopy());
                profileDto.setTlsEnable(profile.isTlsEnable());


                List<ProfileMerchantMappingDto> profileMerchantMappingDto = new ArrayList<ProfileMerchantMappingDto>(0);

                profile.getProfileMerchants().forEach(profileMerchant -> {
                    ProfileMerchantMappingDto profileMerchantDto = new ProfileMerchantMappingDto();
                    profileMerchantDto.setmId(profileMerchant.getMerchantId());
                    profileMerchantDto.setpId(profileMerchant.getProfileId());
                    profileMerchantDto.setPmDefault(profileMerchant.isDefaultMerchant());
                    profileMerchantDto.setPmId(profileMerchant.getProfMergId());
                    profileMerchantDto.setStatus(profileMerchant.getStatus());

                    profileMerchantMappingDto.add(profileMerchantDto);

                });

                profileDto.setpMerchants(profileMerchantMappingDto);

                profileList.add(profileDto);


            });
        }
        return profileList;

    }

    private List<ProfilesDto> loadProfileMerchantInfo(Device device) {


        List<ProfilesDto> profileList = new ArrayList<ProfilesDto>();
        if
        (device.getProfiles() != null) {
            device.getProfiles().forEach(profile -> {
                ProfilesDto profileDto = new ProfilesDto();
                profileDto.setpId(profile.getProfileId());
                profileDto.setmAdrs(profile.getMerchantAdrs());
                profileDto.setmName(profile.getMerchantName());

                profileDto.setpName(profile.getProfileName());
                profileDto.setStatus(profile.getStatus());

                profileList.add(profileDto);


            });
        }
        return profileList;

    }

    //Load Oneclick Setup data for event
    private ProfileDeviceDto loadOneClickSetupDto(String serialNo) throws Exception {

        if (StringUtil.isNullOrWhiteSpace(serialNo)) {
            return null;
        }

        Device device = deviceService.findDeviceBySerial(serialNo);
        ProfileDeviceDto deviceDto = new ProfileDeviceDto();
        if (device != null) {
            deviceDto.setDeviceId(device.getDeviceId());
            deviceDto.setSerialNo(device.getSerialNo());
            deviceDto.setToken(device.getToken());
            deviceDto.setBankCode(device.getBankCode());
            deviceDto.setmName(device.getMerchantName());
            deviceDto.setmAdrs(device.getMerchantAddress());
            deviceDto.setForceSettle(device.isForceSettle());
            deviceDto.setNoSettle(device.isNoSettle());
            deviceDto.setAutoSettle(device.isAutoSettle());
            deviceDto.setSettleTime(device.getAutoSettleTime());
            deviceDto.setSaleEcr(device.isEcr());
            deviceDto.setQrEcr(device.isEcrQr());
            deviceDto.seteSign(device.isSignature());
            deviceDto.setDebugMode(device.isDebugMode());
            deviceDto.setKeyIn(device.isKeyIn());
            deviceDto.setActTrack(device.isActivityTracker());
            deviceDto.setRevHstry(device.isReversalHistory());
            deviceDto.setQrRefund(device.isQrRefund());
            deviceDto.setNotification(device.isPushNotification());
            deviceDto.setEnableAmex(device.isEnableAmex());
            deviceDto.setKeyInForAmex(device.isKeyInAmex());
            deviceDto.setDcc(device.isDcc());
            deviceDto.setOffline(device.isOffline());
            deviceDto.setPreAuth(device.isPreAuth());
            deviceDto.setPopupMsg(device.isPopupMsg());
            deviceDto.setMidTidSeg(device.isMidTidSeg());
            deviceDto.setEventAutoUpdate(device.isEventAutoUpdate());
            /*
             * deviceDto.setCardTypeValidation(device.isCardTypeValidation());
             * deviceDto.setSaleReceipt(device.isSaleReceipt());
             * deviceDto.setCurrencyFromCard(device.isCurrencyFromCard());
             * deviceDto.setCurrencyFromBin(device.isCurrencyFromBin());
             * deviceDto.setProceedWithLkr(device.isProceedWithLkr());
             * deviceDto.setCardTap(device.isCardTap());
             * deviceDto.setCardInsert(device.isCardInsert());
             * deviceDto.setCardSwipe(device.isCardSwipe());
             * deviceDto.setDccPayload(device.isDccPayload());
             */
            deviceDto.setNetwork(device.getNetwork());
            deviceDto.setAutoReversal(device.isAutoReversal());
            deviceDto.setPrintless(device.isPrintless());
            deviceDto.setDisableFrgnPmntForLkrCard(device.isLkrDefaultCurr());
            deviceDto.setVoidSettlePwd(device.getVoidPwd());
            deviceDto.setEcrSettings(loadECRSettings(device));
            deviceDto.setAuthServerSettings(loadAuthSettings(device));
            // deviceDto.setDiffSaleMidTid(device.isDiffSaleMidTid());
            deviceDto.setKeyInForOffline(device.isMkiOffline());
            deviceDto.setKeyInForPreAuth(device.isMkiPreAuth());
            deviceDto.setEnablePinPriority(device.isSignPriority());
            deviceDto.setBlockMag(device.isBlockMag());
            deviceDto.setCustomerReceipt(device.isCustomerReceipt());
            deviceDto.setNewVoidPwd(device.getNewVoidPwd());
            deviceDto.setNewSettlementPwd(device.getNewSettlementPwd());
            deviceDto.setImeiScan(device.isImeiScan());
            deviceDto.setCommission(device.isCommission());
            deviceDto.setCommissionRate(device.getCommissionRate());
            deviceDto.setEnableSanhindaPay(device.isEnableSanhindaPay());
            deviceDto.setRefNumberEnable(device.isRefNumberEnable());
            // convert json to list
            if (device.getOfflineUser() != null && !device.getOfflineUser().isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                List<OfflineUserForm> userList = mapper.readValue(device.getOfflineUser().toString(), new TypeReference<List<OfflineUserForm>>() {
                });
                deviceDto.setOfflineUser(userList);
            } else {
                List<OfflineUserForm> offlineUserForm = deviceService.findAllOfflineUser();
                deviceDto.setOfflineUser(offlineUserForm);
            }

            // load ECR Settings


            //merchant dto list
            List<ProfileMerchantDto> merchantDtos = new ArrayList<>();
            if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
                device.getMerchants().forEach(m -> {
                    // set merchant data
                    ProfileMerchantDto mDto = new ProfileMerchantDto();
                    mDto.setmId(m.getMerchantId());
                    mDto.setRefMid(m.getMerchantId());
                    mDto.setCategory(m.getCategory());
                    mDto.setMerchantType(m.getMerchantType());
                    mDto.setMid(m.getMid());
                    mDto.setTid(m.getTid());
                    mDto.setCurrency(m.getCurrency());
                    mDto.setDesc(m.getDescription());
                    mDto.setMinAmount(m.getMinAmount());
                    mDto.setMaxAmount(m.getMaxAmount());
                    mDto.setEppMonth(m.getMonth());
                    mDto.setDcc(m.isDcc());
                    mDto.setPreAuth(m.isPreAuth());
                    mDto.setOffline(m.isOffline());
                    mDto.setJcb((m.isJcb()));
                    mDto.setLocalMer(m.isLocalMer());
                    mDto.setForeignMer(m.isForeignMer());
                    mDto.setOnUs(m.isOnUs());
                    mDto.setOffUs(m.isOffUs());
                    mDto.setIphoneImei(m.isIphoneImei());

                    if (m.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                        // set q+ data
                        if (m.getScanParam() != null) {
                            mDto.setQrUserId(m.getScanParam().getMerchantUserId());
                            mDto.setQrPassword(m.getScanParam().getMerchantPassword());
                            mDto.setQrKey(m.getScanParam().getChecksumKey());
                            mDto.setVid(m.getScanParam().getVid());
                            mDto.setCid(m.getScanParam().getCid());
                            mDto.setQrRefId(m.getScanParam().isQrRefId());

                        }
                    }

                    merchantDtos.add(mDto);
                });
            }
            deviceDto.setMerchants(merchantDtos);

            //Set BIN Config Data
            List<BinConfigDto> binConfigDto = new ArrayList<>();
            if (device.getBinConfig() != null && !device.getBinConfig().isEmpty()) {
                device.getBinConfig().forEach(m -> {
                    BinConfigDto conf = new BinConfigDto();
                    conf.setAction(m.getAction());
                    conf.setBinEndFrom(m.getBinEnd());
                    conf.setBinStartFrom(m.getBinStartFrom());
                    conf.setCardType(m.getCardType());
                    conf.setMerchantId(m.getMerchantId());
                    conf.setMid(m.getMid());
                    conf.setTid(m.getTid());
                    conf.setTransType(m.getTransType());
                    conf.setType(m.getType());

                    binConfigDto.add(conf);
                });

            }

            deviceDto.setBinConfig(binConfigDto);

            //profile Dto List
            List<ProfilesDto> profileDtoList = new ArrayList<>();
            if (device.getProfiles() != null && !device.getProfiles().isEmpty()) {
                device.getProfiles().forEach(profile -> {
                    ProfilesDto profileDto = new ProfilesDto();
                    profileDto.setpId(profile.getProfileId());
                    profileDto.setpName(profile.getProfileName());
                    profileDto.setpDefault(profile.isDefault());
                    profileDto.setmName(profile.getMerchantName());
                    profileDto.setmAdrs(profile.getMerchantAdrs());
                    profileDto.setVisaCnt(profile.isVisaCnt());
                    profileDto.setVisaCntls(profile.isVisaCntls());
                    profileDto.setVisaNoCvm(profile.getVisaNoCvm());
                    profileDto.setVisaCntslTrxn(profile.getVisaCntslTrxn());
                    profileDto.setMcCnt(profile.isMcCnt());
                    profileDto.setMcCntls(profile.isMcCntls());
                    profileDto.setMcNoCvm(profile.getMcNoCvm());
                    profileDto.setMcCntslTrxn(profile.getMcCntslTrxn());
                    profileDto.setUpayCnt(profile.isUpayCnt());
                    profileDto.setUpayCntls(profile.isUpayCntls());
                    profileDto.setUpayNoCvm(profile.getUpayNoCvm());
                    profileDto.setUpayCntslTrxn(profile.getUpayCntslTrxn());
                    profileDto.setJcbCnt(profile.isJcbCnt());
                    profileDto.setJcbCntls(profile.isJcbCntls());
                    profileDto.setJcbNoCvm(profile.getJcbNoCvm());
                    profileDto.setJcbCntslTrxn(profile.getJcbCntslTrxn());
                    profileDto.setStatus(profile.getStatus());
                    profileDto.setCustomerCopy(profile.isCustomerCopy());
                    profileDto.setTlsEnable(profile.isTlsEnable());

                    //profile merchant dto List
                    List<ProfileMerchantMappingDto> profileMerchantMappingDtoList = new ArrayList<>();
                    if (profile.getProfileMerchants() != null && !profile.getProfileMerchants().isEmpty()) {
                        profile.getProfileMerchants().forEach(profileMerchant -> {
                            ProfileMerchantMappingDto profileMerchantMappingDto = new ProfileMerchantMappingDto();
                            profileMerchantMappingDto.setPmId(profileMerchant.getProfMergId());
                            profileMerchantMappingDto.setpId(profileMerchant.getProfileId());
                            profileMerchantMappingDto.setmId(profileMerchant.getMerchantId());
                            profileMerchantMappingDto.setPmDefault(profileMerchant.isDefaultMerchant());
                            profileMerchantMappingDto.setStatus(profileMerchant.getStatus());

                            profileMerchantMappingDtoList.add(profileMerchantMappingDto);
                        });
                    }

                    profileDto.setpMerchants(profileMerchantMappingDtoList);
                    profileDtoList.add(profileDto);
                });
            }
            deviceDto.setProfiles(profileDtoList);
        }
        return deviceDto;
    }

    private List<ProfileMerchantDto> loadDeviceMerchantsBySerialNo(String serialNo) throws Exception {

        if (StringUtil.isNullOrWhiteSpace(serialNo)) {
            return null;
        }

        Device device = deviceService.findDeviceBySerial(serialNo);
        List<ProfileMerchantDto> merchantDtos = new ArrayList<>();
        if (device != null) {

            //merchant dto list

            if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
                device.getMerchants().forEach(m -> {
                    // set merchant data
                    ProfileMerchantDto mDto = new ProfileMerchantDto();
                    mDto.setRefMid(m.getMerchantId());
                    mDto.setCategory(m.getCategory());
                    mDto.setMerchantType(m.getMerchantType());
                    mDto.setMid(m.getMid());
                    mDto.setmId(m.getMerchantId());
                    mDto.setTid(m.getTid());
                    mDto.setCurrency(m.getCurrency());
                    mDto.setDesc(m.getDescription());
                    mDto.setMinAmount(m.getMinAmount());
                    mDto.setMaxAmount(m.getMaxAmount());
                    mDto.setEppMonth(m.getMonth());
                    mDto.setDcc(m.isDcc());
                    mDto.setPreAuth(m.isPreAuth());
                    mDto.setOffline(m.isOffline());
                    mDto.setJcb(m.isJcb());
                    mDto.setLocalMer(m.isLocalMer());
                    mDto.setForeignMer(m.isForeignMer());
                    mDto.setOnUs(m.isOnUs());
                    mDto.setOffUs(m.isOffUs());
                    mDto.setIphoneImei(m.isIphoneImei());

                    if (m.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                        // set q+ data
                        if (m.getScanParam() != null) {
                            mDto.setQrUserId(m.getScanParam().getMerchantUserId());
                            mDto.setQrPassword(m.getScanParam().getMerchantPassword());
                            mDto.setQrKey(m.getScanParam().getChecksumKey());
                            mDto.setVid(m.getScanParam().getVid());
                            mDto.setCid(m.getScanParam().getCid());
                            mDto.setQrRefId(m.getScanParam().isQrRefId());

                        }
                    }

                    merchantDtos.add(mDto);
                });
            }
        }
        return merchantDtos;
    }

    private List<ProfileMerchantDto> loadDeviceMerchantsBySerialNoFilterByMerchantId(String serialNo, String merchantId) throws Exception {

        if (StringUtil.isNullOrWhiteSpace(serialNo)) {
            return null;
        }

        Device device = deviceService.findDeviceBySerial(serialNo);
        List<ProfileMerchantDto> merchantDtos = new ArrayList<>();
        if (device != null) {

            //merchant dto list

            if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
                device.getMerchants().forEach(m -> {
                    // set merchant data
                    ProfileMerchantDto mDto = new ProfileMerchantDto();

                    if (m.getMerchantId().toString().equalsIgnoreCase(merchantId)) {
                        mDto.setmId(m.getMerchantId());
                        mDto.setCategory(m.getCategory());
                        mDto.setMerchantType(m.getMerchantType());
                        mDto.setMid(m.getMid());
                        mDto.setTid(m.getTid());
                        mDto.setCurrency(m.getCurrency());
                        merchantDtos.add(mDto);
                    }
                });
            }
        }
        return merchantDtos;
    }

}
