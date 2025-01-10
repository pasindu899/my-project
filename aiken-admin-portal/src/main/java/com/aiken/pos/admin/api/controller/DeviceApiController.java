package com.aiken.pos.admin.api.controller;


import com.aiken.common.util.validation.DateUtil;
import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.api.dto.*;
import com.aiken.pos.admin.config.UserRoleMapper;
import com.aiken.pos.admin.constant.ActionType;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.constant.MerchantTypes;
import com.aiken.pos.admin.exception.GenericException;
import com.aiken.pos.admin.model.*;
import com.aiken.pos.admin.service.DashboardService;
import com.aiken.pos.admin.service.DeviceService;
import com.aiken.pos.admin.web.form.OfflineUserForm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Device Rest API Controller
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-29
 */

@RestController
@RequestMapping(value = Endpoint.URL_DEVICES)
public class DeviceApiController {

    private Logger logger = LoggerFactory.getLogger(DeviceApiController.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DashboardService dashboardService;

    @PutMapping
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<Integer> modifyToken(@RequestBody Token token) {

        logger.info("Execute Update Operation...");
        logger.info("Request:" + token);

        Integer deviceId = deviceService.modifyToken(token);
        logger.info("Response[deviceId]:" + deviceId);

        return new ResponseEntity<Integer>(deviceId, HttpStatus.OK);
    }

    @PutMapping("/{serialNo}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<Integer> modifyDeviceStatus(@PathVariable("serialNo") String serialNo) {

        logger.info("Execute Update Operation...");
        logger.info("Request:" + serialNo);

        Integer deviceId = deviceService.modifyDeviceStatus(serialNo);
        logger.info("Response:" + deviceId);

        return new ResponseEntity<Integer>(deviceId, HttpStatus.OK);
    }

    @PutMapping("/{serialNo}/{status}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<Integer> modifyDeviceStatus(@PathVariable("serialNo") String serialNo, @PathVariable("status") String status) {

        logger.info("DEVICE-API: modifyDeviceStatus:\t" + " Get Update from Device API.....");
        logger.info("Request:" + serialNo);

        Integer deviceId = deviceService.modifyDeviceStatus(serialNo, status);
        logger.info("Response:" + deviceId);

        return new ResponseEntity<Integer>(deviceId, HttpStatus.OK);
    }

    // combank 1.1.19 shared pref issue fix endpoint
    @Deprecated
    @GetMapping("/check-status/{serialNo}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_MANAGER_OR_POS_USER_OR_USER)
    public ResponseEntity<String> checkStatus(@PathVariable("serialNo") String serialNo) throws Exception {

        logger.info("Execute Get Token Operation...");
        logger.info("Request:[" + "Serial No:" + serialNo + "]");
        if (StringUtil.isNullOrWhiteSpace(serialNo))
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

        Device device = deviceService.findDeviceBySerial(serialNo);

        return new ResponseEntity<String>(device.getStatus(), HttpStatus.OK);
    }

    @GetMapping("/{serialNo}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<DeviceDto> getDeviceBySerial(@PathVariable("serialNo") String serialNo) throws Exception {

        logger.info("ONECLICK:-Srart oneclick");
        logger.info("Request:[" + "Serial No:" + serialNo + "]");
        if (StringUtil.isNullOrWhiteSpace(serialNo))
            return new ResponseEntity<DeviceDto>(HttpStatus.NOT_FOUND);

        Device device = deviceService.findDeviceBySerial(serialNo);
        // set device data
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setDeviceId(device.getDeviceId());
        deviceDto.setSerialNo(device.getSerialNo());
        deviceDto.setToken(device.getToken());
        deviceDto.setBankCode(device.getBankCode());
        deviceDto.setBankName(device.getBankName());
        deviceDto.setMerchantName(device.getMerchantName());
        deviceDto.setMerchantAddress(device.getMerchantAddress());
        deviceDto.setAddedDate(device.getAddedDate());
        deviceDto.setAddedBy(device.getAddedBy());
        deviceDto.setLastUpdate(device.getLastUpdate());
        deviceDto.setUpdatedBy(device.getUpdatedBy());
        deviceDto.setStatus(device.getStatus());
        deviceDto.setVisaNoCvmLimit(device.getVisaNoCvmLimit());
        deviceDto.setCntactlsTrxnLimit(device.getCntactlsTrxnLimit());
        deviceDto.setAutoSettle(device.isAutoSettle());
        deviceDto.setAutoSettleTime(device.getAutoSettleTime());
        deviceDto.setForceSettle(device.isForceSettle());
        deviceDto.setEcr(device.isEcr());
        deviceDto.setKeyIn(device.isKeyIn());
        deviceDto.setEcrQr(device.isEcrQr());
        deviceDto.setSignature(device.isSignature());
        deviceDto.setDebugMode(device.isDebugMode());
        deviceDto.setNoSettle(device.isNoSettle());
        deviceDto.setActivityTracker(device.isActivityTracker());
        deviceDto.setQrRefund(device.isQrRefund());
        deviceDto.setReversalHistory(device.isReversalHistory());
        deviceDto.setPushNotification(device.isPushNotification());
        deviceDto.setEnableAmex(device.isEnableAmex());
        deviceDto.setDcc(device.isDcc());
        deviceDto.setOffline(device.isOffline());
        deviceDto.setPreAuth(device.isPreAuth());
        deviceDto.setKeyInForOffline(device.isMkiOffline());
        deviceDto.setKeyInForPreAuth(device.isMkiPreAuth());
        deviceDto.setEnablePinPriority(device.isSignPriority());
        deviceDto.setBlockMag(device.isBlockMag());
        deviceDto.setCustomerReceipt(device.isCustomerReceipt());
        deviceDto.setMidTidSeg(device.isMidTidSeg());
        deviceDto.setEventAutoUpdate(device.isEventAutoUpdate());
        deviceDto.setImeiScan(device.isImeiScan());
        deviceDto.setCommission(device.isCommission());
        deviceDto.setCommissionRate(device.getCommissionRate());
        deviceDto.setEnableSanhindaPay(device.isEnableSanhindaPay());
        deviceDto.setTleProfilesEnable(device.isTleProfilesEnable());
        if (device.getOfflineUser() != null && !device.getOfflineUser().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            List<OfflineUserForm> userList = mapper.readValue(device.getOfflineUser().toString(), new TypeReference<List<OfflineUserForm>>() {
            });
            deviceDto.setOfflineUser(userList);
        }

        /*
         * deviceDto.setCardTypeValidation(device.isCardTypeValidation());
         * deviceDto.setSaleReceipt(device.isSaleReceipt());
         * deviceDto.setCurrencyFromCard(device.isCurrencyFromCard());
         * deviceDto.setCurrencyFromBin(device.isCurrencyFromBin());
         * deviceDto.setProceedWithLkr(device.isProceedWithLkr());
         * deviceDto.setCardTap(device.isCardTap());
         * deviceDto.setCardInsert(device.isCardInsert());
         * deviceDto.setCardSwipe(device.isCardSwipe());
         */


        List<MerchantDto> merchantDtos = new ArrayList<>();

        if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
            device.getMerchants().forEach(m -> {
                // set merchant data
                MerchantDto mDto = new MerchantDto();
                mDto.setMerchantId(m.getMerchantId());
                mDto.setType(m.getCategory());
                mDto.setMerchantType(m.getMerchantType());
                mDto.setCategory(m.getCategory());
                mDto.setMonth(m.getMonth());
                mDto.setMid(m.getMid());
                mDto.setTid(m.getTid());
                mDto.setCurrency(m.getCurrency());
                mDto.setDescription(m.getDescription());
                mDto.setMinAmount(m.getMinAmount());
                mDto.setMaxAmount(m.getMaxAmount());
                mDto.setVoidTx(m.isVoidTx());
                mDto.setAmexTx(m.isAmexTx());
                mDto.setForeignMer(m.isForeignMer());
                mDto.setLocalMer(m.isLocalMer());
                mDto.setOnUs(m.isOnUs());
                mDto.setOffUs(m.isOffUs());
                mDto.setIphoneImei(m.isIphoneImei());

                if (m.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                    // set q+ data
                    if (m.getScanParam() != null) {
                        mDto.setScanParamId(m.getScanParam().getScanParamId());
                        mDto.setMerchantUserId(m.getScanParam().getMerchantUserId());
                        mDto.setMerchantPassword(m.getScanParam().getMerchantPassword());
                        mDto.setChecksumKey(m.getScanParam().getChecksumKey());

                    }
                } else if (m.getCategory().equalsIgnoreCase(MerchantTypes.AMEX)) {
                    // set amex data
                    if (m.getAmexParam() != null) {
                        mDto.setAmexParamId(m.getAmexParam().getAmexParamId());
                        mDto.setAmexIp(m.getAmexParam().getAmexIp());
                    }
                }

                merchantDtos.add(mDto);
            });
        }

        deviceDto.setMerchants(merchantDtos);

        return new ResponseEntity<DeviceDto>(deviceDto, HttpStatus.OK);
    }

    // combank 1.1.19 shared pref issue fix endpoint
    @Deprecated
    @GetMapping("/{serialNo}/{merchantType}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<CustomDeviceDto> getDeviceBySerial(@PathVariable("serialNo") String serialNo, @PathVariable("merchantType") String merchantType) throws Exception {

        logger.info("Request:[" + "Serial No:" + serialNo + " | Merchant Type:" + merchantType + "]");

        if (StringUtil.isNullOrWhiteSpace(serialNo) || StringUtil.isNullOrWhiteSpace(merchantType))
            return new ResponseEntity<CustomDeviceDto>(HttpStatus.BAD_REQUEST);

        Device device = deviceService.findDeviceBySerial(serialNo);

        if (merchantType.equalsIgnoreCase(MerchantTypes.QR)) {
            // set device data
            CustomDeviceDto deviceDto = new CustomDeviceDto();
            deviceDto.setDeviceId(device.getDeviceId());
            deviceDto.setBankCode(device.getBankCode());
            deviceDto.setMerchantName(device.getMerchantName());
            deviceDto.setMerchantAddress(device.getMerchantAddress());
            deviceDto.setVisaNoCvmLimit(device.getVisaNoCvmLimit());
            deviceDto.setCntactlsTrxnLimit(device.getCntactlsTrxnLimit());
            deviceDto.setAutoSettle(device.isAutoSettle());
            deviceDto.setAutoSettleTime(device.getAutoSettleTime());
            deviceDto.setForceSettle(device.isForceSettle());
            deviceDto.setEcr(device.isEcr());
            deviceDto.setKeyIn(device.isKeyIn());
            deviceDto.setStatus(device.getStatus());
            deviceDto.setEcrQr(device.isEcrQr());
            deviceDto.setSignature(device.isSignature());
            deviceDto.setDebugMode(device.isDebugMode());
            deviceDto.setNoSettle(device.isNoSettle());
            deviceDto.setActivityTracker(device.isActivityTracker());
            deviceDto.setQrRefund(device.isQrRefund());
            deviceDto.setPushNotifications(device.isPushNotification());
            deviceDto.setDcc(device.isDcc());
            deviceDto.setOffline(device.isOffline());
            deviceDto.setPreAuth(device.isPreAuth());
            deviceDto.setNetwork(device.getNetwork());
            deviceDto.setAutoReversal(device.isAutoReversal());
            deviceDto.setPrintless(device.isPrintless());
            deviceDto.setDisableFrgnPmntForLkrCard(device.isLkrDefaultCurr());
            deviceDto.setVoidSettlePwd(device.getVoidPwd());

            // Load ECR Settings
            deviceDto.setEcrSettings(loadECRSettings(device));

            deviceDto.setAuthServerSettings(loadAuthSettings(device));
            deviceDto.setDiffSaleMidTid(device.isDiffSaleMidTid());
            deviceDto.setKeyInForOffline(device.isMkiOffline());
            deviceDto.setKeyInForPreAuth(device.isMkiPreAuth());
            deviceDto.setEnablePinPriority(device.isSignPriority());
            deviceDto.setBlockMag(device.isBlockMag());
            deviceDto.setCustomerReceipt(device.isCustomerReceipt());

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
             */


            List<CustomMerchantDto> merchantDtos = new ArrayList<>();
            if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
                device.getMerchants().forEach(m -> {
                    if (m.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                        // set merchant data
                        CustomQrMerchantDto mDto = new CustomQrMerchantDto();
                        mDto.setType(m.getCategory());
                        mDto.setMid(m.getMid());
                        mDto.setTid(m.getTid());
                        mDto.setCurrency(m.getCurrency());
                        mDto.setDescription(m.getDescription());
                        // set q+ data
                        if (m.getScanParam() != null) {
                            mDto.setMerchantUserId(m.getScanParam().getMerchantUserId());
                            mDto.setMerchantPassword(m.getScanParam().getMerchantPassword());
                            mDto.setChecksumKey(m.getScanParam().getChecksumKey());
                            mDto.setVid(m.getScanParam().getVid());
                            mDto.setCid(m.getScanParam().getCid());
                            mDto.setQrRefId(m.getScanParam().isQrRefId());
                        }
                        merchantDtos.add(mDto);
                    }
                });
            }

            deviceDto.setMerchants(merchantDtos);
            return new ResponseEntity<CustomDeviceDto>(deviceDto, HttpStatus.OK);
        }
        return new ResponseEntity<CustomDeviceDto>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN)
    public ResponseEntity<List<Device>> getAllDevices() {

        List<Device> devices = deviceService.findAllDevices();
        logger.info("No of Devies:" + devices.size());

        return new ResponseEntity<List<Device>>(devices, HttpStatus.OK);
    }

    /**
     * New one click setup endpoint
     * Amesh Madumalka - 2021/10/06
     */
    @GetMapping(Endpoint.URL_ONE_CLICK + "{serialNo}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<ProfileDeviceDto> getDeviceBySerialNo(@PathVariable("serialNo") String serialNo) throws Exception {

        logger.info("ONECLICK: Start one Click for SerialNo:" + serialNo);
        if (StringUtil.isNullOrWhiteSpace(serialNo)) {
            return new ResponseEntity<ProfileDeviceDto>(HttpStatus.NOT_FOUND);
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
            deviceDto.setDcc(device.isDcc());
            deviceDto.setOffline(device.isOffline());
            deviceDto.setPreAuth(device.isPreAuth());
            deviceDto.setKeyInForAmex(device.isKeyInAmex());
            deviceDto.setPopupMsg(device.isPopupMsg());

            deviceDto.setMidTidSeg(device.isMidTidSeg());
            deviceDto.setEventAutoUpdate(device.isEventAutoUpdate());
            deviceDto.setNetwork(device.getNetwork());
            deviceDto.setAutoReversal(device.isAutoReversal());
            deviceDto.setPrintless(device.isPrintless());

            deviceDto.setDisableFrgnPmntForLkrCard(device.isLkrDefaultCurr());
            deviceDto.setVoidSettlePwd(device.getVoidPwd());

            // Load ECR Settings
            deviceDto.setEcrSettings(loadECRSettings(device));

            deviceDto.setAuthServerSettings(loadAuthSettings(device));
            //deviceDto.setDiffSaleMidTid(device.isDiffSaleMidTid());

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
            deviceDto.setTleProfilesEnable(device.isTleProfilesEnable());
            if (device.getOfflineUser() != null && !device.getOfflineUser().isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                List<OfflineUserForm> userList = mapper.readValue(device.getOfflineUser().toString(), new TypeReference<List<OfflineUserForm>>() {
                });
                deviceDto.setOfflineUser(userList);
            }
//            else {
//                List<OfflineUserForm> offlineUserForm = deviceService.findAllOfflineUser();
//                deviceDto.setOfflineUser(offlineUserForm);
//            }
            //deviceDto.set

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
                    mDto.setJcb(m.isJcb());
                    mDto.setForeignMer(m.isForeignMer());
                    mDto.setLocalMer(m.isLocalMer());
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
            deviceDto.setMerchants(merchantDtos);

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
        return new ResponseEntity<>(deviceDto, HttpStatus.OK);
    }

    /**
     * save device temporarily
     * Amesh Madumalka - 2021/10/06
     */
    @Deprecated
    @PostMapping
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<String> saveDevice(@Valid @RequestBody Device device) throws Exception {

        if (device == null) {
            logger.info("Device :" + device);
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        //save new device
        deviceService.saveDevice(device);

        return new ResponseEntity<String>(HttpStatus.OK);
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

    @GetMapping(Endpoint.URL_TEST + "{days}")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<Dashboard> sampleDays(@PathVariable("days") Integer days) throws GenericException {

        logger.info("Execute sampleDays...");
        logger.info("#Days:" + days);

        Dashboard dashborad = dashboardService.FindBankOneClickCountForLast30D();
        logger.info("Response:" + days);

        return new ResponseEntity<Dashboard>(dashborad, HttpStatus.OK);

    }

    @GetMapping("/userstatus")
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<Dashboard> findUserStatus() throws GenericException {

        logger.info("Execute User Status...");


        Dashboard dashborad = dashboardService.findDailyCount();


        return new ResponseEntity<Dashboard>(dashborad, HttpStatus.OK);

    }

    @PostMapping(Endpoint.URL_DEVICE_PROFILE)
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<DeviceReferenceDto> updateDeviceProfiles(@Valid @RequestBody DeviceProfileUpdateDto profiles) throws Exception {

        if (profiles == null) {
            logger.info("Device Profile Data Not Found");
            return new ResponseEntity<DeviceReferenceDto>(HttpStatus.NOT_FOUND);
        }
        Device device = deviceService.findDeviceBySerial(profiles.getSerialNo());

        List<Profile> syncProfiles = packSyncProfileData(device, profiles);
        device.setProfiles(syncProfiles);
        deviceService.editDevice(device);

        logger.info("Device Profile Data:" + profiles.getProfiles().get(0));

        return new ResponseEntity<DeviceReferenceDto>(sendSynceReferenceIds(profiles.getSerialNo()), HttpStatus.OK);
    }

    private DeviceReferenceDto sendSynceReferenceIds(String serialNo) throws Exception {
        DeviceReferenceDto syncedData = new DeviceReferenceDto();
        if (serialNo != null) {
            syncedData.setSerialNo(serialNo);
            Device device = deviceService.findDeviceBySerial(serialNo);
            List<ProfileReferenceDto> profileList = new ArrayList<ProfileReferenceDto>();
            device.getProfiles().forEach(profile -> {
                ProfileReferenceDto newP = new ProfileReferenceDto();
                logger.info("profile ID :" + profile.getProfileId());

                newP.setActualProfileId(profile.getProfileId());
                newP.setDeviceProfileRefId(profile.getProfileRefId());


                if (profile.getProfileMerchants() != null) {
                    List<ReferenceMappingDto> profilesMerchants = new ArrayList<ReferenceMappingDto>();

                    logger.info("profile Merchant :" + profile.getProfileMerchants().size());
                    profile.getProfileMerchants()
                            .forEach(devPMerchant -> {
                                ReferenceMappingDto pMerchant = new ReferenceMappingDto();
                                pMerchant.setActualProfMerchantId(devPMerchant.getProfMergId());
                                pMerchant.setDeviceRefProMerchantId(devPMerchant.getProfMerRefId());
                                pMerchant.setMerchantId(devPMerchant.getMerchantId());

                                profilesMerchants.add(pMerchant);
                            });
                    newP.setProfilesMerchants(profilesMerchants);
                }

                profileList.add(newP);
            });
            syncedData.setProfiles(profileList);
        }


        return syncedData;
    }

    private List<Profile> packSyncProfileData(Device device, DeviceProfileUpdateDto profiles) {


        List<Profile> devProfiles = new ArrayList<Profile>();

        if (device.getProfiles() == null && profiles.getProfiles() != null) {

            profiles.getProfiles().forEach(profile -> {
                Profile newP = new Profile();
                logger.info("profile ID :" + profile.getpId());

                newP.setProfileId(profile.getpId());
                newP.setProfileRefId(profile.getpId());
                newP.setProfileName(profile.getpName());
                newP.setDefault(profile.ispDefault());
                newP.setMerchantName(profile.getmName());
                newP.setMerchantAdrs(profile.getmAdrs());
                newP.setVisaCnt(profile.isVisaCnt());
                newP.setVisaCntls(profile.isVisaCntls());
                newP.setVisaNoCvm(profile.getVisaNoCvm());
                newP.setVisaCntslTrxn(profile.getVisaCntslTrxn());
                newP.setMcCnt(profile.isMcCnt());
                newP.setMcCntls(profile.isMcCntls());
                newP.setMcNoCvm(profile.getMcNoCvm());
                newP.setMcCntls(profile.isMcCntls());
                newP.setAmexCnt(profile.isAmexCnt());
                newP.setAmexNoCvm(profile.getAmexNoCvm());
                newP.setAmexCntslTrxn(profile.getAmexCntslTrxn());
                newP.setUpayCnt(profile.isUpayCnt());
                newP.setUpayCntls(profile.isUpayCntls());
                newP.setUpayNoCvm(profile.getUpayNoCvm());
                newP.setUpayCntslTrxn(profile.getUpayCntslTrxn());
                newP.setJcbCnt(profile.isJcbCnt());
                newP.setJcbCntls(profile.isJcbCntls());
                newP.setJcbNoCvm(profile.getJcbNoCvm());
                newP.setJcbCntslTrxn(profile.getJcbCntslTrxn());
                newP.setStatus("New");
                newP.setCustomerCopy(profile.isCustomerCopy());
                newP.setAction(ActionType.INSERT);

                newP.setAddedDate(DateUtil.getCurrentTime());
                newP.setLastUpdate(DateUtil.getCurrentTime());
                newP.setAddedBy(ActionType.AUTO_SYNC);
                newP.setUpdatedBy(ActionType.AUTO_SYNC);

                if (profile.getpMerchants() != null) {
                    List<ProfileMerchant> pMerchantList = new ArrayList<ProfileMerchant>();
                    logger.info("profile Merchant :" + profile.getpMerchants().size());
                    profile.getpMerchants()
                            .forEach(devPMerchant -> {
                                ProfileMerchant pMerchant = new ProfileMerchant();
                                pMerchant.setProfMerRefId(devPMerchant.getPmId());
                                pMerchant.setProfileId(profile.getpId());
                                pMerchant.setMerchantId(devPMerchant.getmId());
                                pMerchant.setDefaultMerchant(devPMerchant.isPmDefault());
                                pMerchant.setStatus("NEW");
                                pMerchant.setAddedBy(ActionType.AUTO_SYNC);
                                pMerchant.setAddedDate(DateUtil.getCurrentTime());
                                pMerchant.setLastUpdate(DateUtil.getCurrentTime());
                                pMerchantList.add(pMerchant);
                            });
                    newP.setProfileMerchants(pMerchantList);
                }

                devProfiles.add(newP);
            });
        }

        return devProfiles;
    }

    @PutMapping(Endpoint.URL_SYNC)
    @PreAuthorize(UserRoleMapper.PRE_AUTH_ADMIN_OR_POS_USER)
    public ResponseEntity<String> updateDeviceComStatus(@RequestBody DeviceComStatus deviceComStatus) throws GenericException {

        logger.info("Execute Syncing Operation...");

        if (deviceComStatus == null) {
            logger.info("DeviceComStatus :NULL");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (deviceService.syncDeviceComData(deviceComStatus)) {
            logger.info("DeviceComStatus :Sync Success");
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            logger.info("DeviceComStatus :Sync Failed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
