package com.aiken.pos.admin.api.dto;

import com.aiken.pos.admin.web.form.OfflineUserForm;

import java.util.List;

/**
 * Rest API DTO
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-08-09
 */
public class CheckUpdateDto {

    private Integer deviceId;
    private boolean autoSettle;
    private String autoSettleTime;
    private boolean forceSettle;
    private boolean clearBatch;
    private String clearBatchAction;
    private String merchantName;
    private String merchantAddress;
    private boolean ecrSale;
    private boolean ecrQr;
    private boolean qrParamChange;
    private boolean qrVidCidChange;
    private boolean qrMidTidChange;
    private boolean keyIn;
    private boolean addEpp;
    private boolean deleteEpp;
    private String status;
    private boolean signature;
    private boolean debugMode;
    private boolean noSettle;
    private boolean activityTracker;
    private String reportFromDate;
    private String reportToDate;
    private String type;
    private boolean qrRefund;
    private boolean reversalHistory;
    private boolean enableAmex;
    private boolean pushNotification;
    private boolean saleDcc;

    private boolean saleJCB;
    private boolean SaleOffline;
    private boolean salePreAuth;
    private boolean amexDcc;
    private boolean amexOffline;
    private boolean amexPreAuth;
    private boolean addProfile;
    private boolean removeProfile;
    private boolean modifyProfile;
    private boolean saleMidTidChange;
    private boolean amexMidTidChange;
    private boolean eppSaleMidTidChange;
    private boolean eppAmexMidTidChange;
    private boolean keyInForAmex;
    private boolean popupMsg;
    private boolean binBlock;
    private boolean clearRevaersal;
    private boolean autoRevaersal;
    private boolean printless;
    private boolean authServerConfig;
    private boolean networkChange;
    private String voidSettlePwd;
    private boolean disableFrgnPmntForLkrCard;

    private boolean keyInForPreAuth;

    private boolean keyInForOffline;

    private boolean enablePinPriority;

    private boolean blockMag;

    private boolean customerReceipt;
    private boolean midTidSeg;
    private boolean eventAutoUpdate;
    private String newVoidPwd;
    private String newSettlementPwd;
    private boolean imeiScan;
    private boolean commission;
    private float commissionRate;
    private List<OfflineUserForm> offlineUser;
    private boolean enableSanhindaPay;
    private String onetimePassword;
    private boolean refNumberEnable;
    private boolean tleProfilesEnable;


//ECR
    /*
     * private boolean cardTypeValidation; private boolean saleReceipt;
     *
     * private boolean currencyFromBin; private boolean currencyFromCard; private
     * boolean proceedWithLkr;
     *
     * private boolean cardTap; private boolean cardInsert; private boolean
     * cardSwipe;
     */

    private boolean oneClickSetup;

    private CustomEcrSettingsDto ecrSettings;
    private CustomAuthServerConfigDto authServerSettings;
    private ProfileDeviceDto oneClickData;

    private List<CustomQrMerchantDto> qrMerchants;
    private List<CustomEppMerchantDto> eppMerchants;

    private List<CustomCommonMerchantDto> saleMerchants;
    private List<CustomCommonMerchantDto> amexMerchants;

    private List<BinConfigDto> binBlockData;


    private String networkMode;


    public boolean isNetworkChange() {
        return networkChange;
    }

    public void setNetworkChange(boolean networkChange) {
        this.networkChange = networkChange;
    }

    public String getNetworkMode() {
        return networkMode;
    }

    public void setNetworkMode(String networkMode) {
        this.networkMode = networkMode;
    }

    public List<BinConfigDto> getBinBlockData() {
        return binBlockData;
    }

    public void setBinBlockData(List<BinConfigDto> binBlockData) {
        this.binBlockData = binBlockData;
    }

    public List<CustomCommonMerchantDto> getSaleMerchants() {
        return saleMerchants;
    }

    public void setSaleMerchants(List<CustomCommonMerchantDto> saleMerchants) {
        this.saleMerchants = saleMerchants;
    }

    public List<CustomCommonMerchantDto> getAmexMerchants() {
        return amexMerchants;
    }

    public void setAmexMerchants(List<CustomCommonMerchantDto> amexMerchants) {
        this.amexMerchants = amexMerchants;
    }

    private List<ProfilesDto> profiles;

    private List<ProfileMerchantDto> merchants;

    public List<ProfileMerchantDto> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<ProfileMerchantDto> merchants) {
        this.merchants = merchants;
    }

    public ProfileDeviceDto getOneClickData() {
        return oneClickData;
    }

    public void setOneClickData(ProfileDeviceDto oneClickData) {
        this.oneClickData = oneClickData;
    }

    public boolean isOneClickSetup() {
        return oneClickSetup;
    }

    public void setOneClickSetup(boolean oneClickSetup) {
        this.oneClickSetup = oneClickSetup;
    }

    public boolean isAddProfile() {
        return addProfile;
    }

    public void setAddProfile(boolean addProfile) {
        this.addProfile = addProfile;
    }

    public boolean isRemoveProfile() {
        return removeProfile;
    }

    public void setRemoveProfile(boolean removeProfile) {
        this.removeProfile = removeProfile;
    }

    public boolean isModifyProfile() {
        return modifyProfile;
    }

    public void setModifyProfile(boolean modifyProfile) {
        this.modifyProfile = modifyProfile;
    }

    public List<ProfilesDto> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfilesDto> profiles) {
        this.profiles = profiles;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isAutoSettle() {
        return autoSettle;
    }

    public void setAutoSettle(boolean autoSettle) {
        this.autoSettle = autoSettle;
    }

    public String getAutoSettleTime() {
        return autoSettleTime;
    }

    public void setAutoSettleTime(String autoSettleTime) {
        this.autoSettleTime = autoSettleTime;
    }

    public boolean isForceSettle() {
        return forceSettle;
    }

    public void setForceSettle(boolean forceSettle) {
        this.forceSettle = forceSettle;
    }

    public boolean isClearBatch() {
        return clearBatch;
    }

    public void setClearBatch(boolean clearBatch) {
        this.clearBatch = clearBatch;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public boolean isEcrSale() {
        return ecrSale;
    }

    public void setEcrSale(boolean ecrSale) {
        this.ecrSale = ecrSale;
    }

    public boolean isEcrQr() {
        return ecrQr;
    }

    public void setEcrQr(boolean ecrQr) {
        this.ecrQr = ecrQr;
    }

    public boolean isKeyIn() {
        return keyIn;
    }

    public void setKeyIn(boolean keyIn) {
        this.keyIn = keyIn;
    }

    public boolean isAddEpp() {
        return addEpp;
    }

    public void setAddEpp(boolean addEpp) {
        this.addEpp = addEpp;
    }

    public boolean isDeleteEpp() {
        return deleteEpp;
    }

    public void setDeleteEpp(boolean deleteEpp) {
        this.deleteEpp = deleteEpp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CustomQrMerchantDto> getQrMerchants() {
        return qrMerchants;
    }

    public void setQrMerchants(List<CustomQrMerchantDto> qrMerchants) {
        this.qrMerchants = qrMerchants;
    }

    public List<CustomEppMerchantDto> getEppMerchants() {
        return eppMerchants;
    }

    public void setEppMerchants(List<CustomEppMerchantDto> eppMerchants) {
        this.eppMerchants = eppMerchants;
    }

    public boolean isQrParamChange() {
        return qrParamChange;
    }

    public void setQrParamChange(boolean qrParamChange) {
        this.qrParamChange = qrParamChange;
    }

    public boolean isQrMidTidChange() {
        return qrMidTidChange;
    }

    public void setQrMidTidChange(boolean qrMidTidChange) {
        this.qrMidTidChange = qrMidTidChange;
    }

    public boolean isSignature() {
        return signature;
    }

    public void setSignature(boolean signature) {
        this.signature = signature;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isNoSettle() {
        return noSettle;
    }

    public void setNoSettle(boolean noSettle) {
        this.noSettle = noSettle;
    }


    public String getReportFromDate() {
        return reportFromDate;
    }

    public void setReportFromDate(String reportFromDate) {
        this.reportFromDate = reportFromDate;
    }

    public String getReportToDate() {
        return reportToDate;
    }

    public void setReportToDate(String reportToDate) {
        this.reportToDate = reportToDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isQrRefund() {
        return qrRefund;
    }

    public void setQrRefund(boolean qrRefund) {
        this.qrRefund = qrRefund;
    }

    public boolean isReversalHistory() {
        return reversalHistory;
    }

    public void setReversalHistory(boolean reversalHistory) {
        this.reversalHistory = reversalHistory;
    }

    public boolean isActivityTracker() {
        return activityTracker;
    }

    public void setActivityTracker(boolean activityTracker) {
        this.activityTracker = activityTracker;
    }

    public boolean isEnableAmex() {
        return enableAmex;
    }

    public void setEnableAmex(boolean enableAmex) {
        this.enableAmex = enableAmex;
    }

    public boolean isPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public boolean isQrVidCidChange() {
        return qrVidCidChange;
    }

    public void setQrVidCidChange(boolean qrVidCidChange) {
        this.qrVidCidChange = qrVidCidChange;
    }

    public boolean isSaleMidTidChange() {
        return saleMidTidChange;
    }

    public void setSaleMidTidChange(boolean saleMidTidChange) {
        this.saleMidTidChange = saleMidTidChange;
    }

    public boolean isAmexMidTidChange() {
        return amexMidTidChange;
    }

    public void setAmexMidTidChange(boolean amexMidTidChange) {
        this.amexMidTidChange = amexMidTidChange;
    }

    public boolean isEppSaleMidTidChange() {
        return eppSaleMidTidChange;
    }

    public void setEppSaleMidTidChange(boolean eppSaleMidTidChange) {
        this.eppSaleMidTidChange = eppSaleMidTidChange;
    }

    public boolean isEppAmexMidTidChange() {
        return eppAmexMidTidChange;
    }

    public void setEppAmexMidTidChange(boolean eppAmexMidTidChange) {
        this.eppAmexMidTidChange = eppAmexMidTidChange;
    }

    public boolean isSaleDcc() {
        return saleDcc;
    }

    public void setSaleDcc(boolean saleDcc) {
        this.saleDcc = saleDcc;
    }

    public boolean isSaleOffline() {
        return SaleOffline;
    }

    public void setSaleOffline(boolean saleOffline) {
        SaleOffline = saleOffline;
    }

    public boolean isSalePreAuth() {
        return salePreAuth;
    }

    public void setSalePreAuth(boolean salePreAuth) {
        this.salePreAuth = salePreAuth;
    }

    public boolean isAmexDcc() {
        return amexDcc;
    }

    public void setAmexDcc(boolean amexDcc) {
        this.amexDcc = amexDcc;
    }

    public boolean isAmexOffline() {
        return amexOffline;
    }

    public void setAmexOffline(boolean amexOffline) {
        this.amexOffline = amexOffline;
    }

    public boolean isAmexPreAuth() {
        return amexPreAuth;
    }

    public void setAmexPreAuth(boolean amexPreAuth) {
        this.amexPreAuth = amexPreAuth;
    }

    public boolean isKeyInForAmex() {
        return keyInForAmex;
    }

    public void setKeyInForAmex(boolean keyInForAmex) {
        this.keyInForAmex = keyInForAmex;
    }

    public boolean isPopupMsg() {
        return popupMsg;
    }

    public void setPopupMsg(boolean popupMsg) {
        this.popupMsg = popupMsg;
    }

    public boolean isBinBlock() {
        return binBlock;
    }

    public void setBinBlock(boolean binBlock) {
        this.binBlock = binBlock;

    }

    public boolean isClearRevaersal() {
        return clearRevaersal;
    }

    public void setClearRevaersal(boolean clearRevaersal) {
        this.clearRevaersal = clearRevaersal;

    }

    public CustomEcrSettingsDto getEcrSettings() {
        return ecrSettings;
    }

    public void setEcrSettings(CustomEcrSettingsDto ecrSettings) {
        this.ecrSettings = ecrSettings;
    }

    public CustomAuthServerConfigDto getAuthServerSettings() {
        return authServerSettings;
    }

    public void setAuthServerSettings(CustomAuthServerConfigDto authServerSettings) {
        this.authServerSettings = authServerSettings;
    }

    public boolean isAutoRevaersal() {
        return autoRevaersal;
    }

    public void setAutoRevaersal(boolean autoRevaersal) {
        this.autoRevaersal = autoRevaersal;
    }

    public boolean isPrintless() {
        return printless;
    }

    public void setPrintless(boolean printless) {
        this.printless = printless;
    }


    public boolean isAuthServerConfig() {
        return authServerConfig;
    }

    public void setAuthServerConfig(boolean authServerConfig) {
        this.authServerConfig = authServerConfig;
    }

    public boolean isDisableFrgnPmntForLkrCard() {
        return disableFrgnPmntForLkrCard;
    }

    public void setDisableFrgnPmntForLkrCard(boolean disableFrgnPmntForLkrCard) {
        this.disableFrgnPmntForLkrCard = disableFrgnPmntForLkrCard;
    }

    public String getVoidSettlePwd() {
        return voidSettlePwd;
    }

    public void setVoidSettlePwd(String voidSettlePwd) {
        this.voidSettlePwd = voidSettlePwd;
    }

    public boolean isKeyInForPreAuth() {
        return keyInForPreAuth;
    }

    public void setKeyInForPreAuth(boolean keyInForPreAuth) {
        this.keyInForPreAuth = keyInForPreAuth;
    }

    public boolean isKeyInForOffline() {
        return keyInForOffline;
    }

    public void setKeyInForOffline(boolean keyInForOffline) {
        this.keyInForOffline = keyInForOffline;
    }

    public String getClearBatchAction() {
        return clearBatchAction;
    }

    public void setClearBatchAction(String clearBatchAction) {
        this.clearBatchAction = clearBatchAction;
    }

    public boolean isEnablePinPriority() {
        return enablePinPriority;
    }

    public void setEnablePinPriority(boolean enablePinPriority) {
        this.enablePinPriority = enablePinPriority;
    }

    public boolean isBlockMag() {
        return blockMag;
    }

    public void setBlockMag(boolean blockMag) {
        this.blockMag = blockMag;
    }

    public boolean isCustomerReceipt() {
        return customerReceipt;
    }

    public boolean isSaleJCB() {
        return saleJCB;
    }

    public void setSaleJCB(boolean saleJCB) {
        this.saleJCB = saleJCB;
    }

    public void setCustomerReceipt(boolean customerReceipt) {
        this.customerReceipt = customerReceipt;
    }

    public boolean isEventAutoUpdate() {
        return eventAutoUpdate;
    }

    public void setEventAutoUpdate(boolean eventAutoUpdate) {
        this.eventAutoUpdate = eventAutoUpdate;
    }

    public boolean isMidTidSeg() {
        return midTidSeg;
    }

    public void setMidTidSeg(boolean midTidSeg) {
        this.midTidSeg = midTidSeg;
    }

    public String getNewVoidPwd() {
        return newVoidPwd;
    }

    public void setNewVoidPwd(String newVoidPwd) {
        this.newVoidPwd = newVoidPwd;
    }

    public String getNewSettlementPwd() {
        return newSettlementPwd;
    }

    public void setNewSettlementPwd(String newSettlementPwd) {
        this.newSettlementPwd = newSettlementPwd;
    }

    public boolean isImeiScan() {
        return imeiScan;
    }

    public void setImeiScan(boolean imeiScan) {
        this.imeiScan = imeiScan;
    }

    public boolean isCommission() {
        return commission;
    }

    public void setCommission(boolean commission) {
        this.commission = commission;
    }

    public float getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(float commissionRate) {
        this.commissionRate = commissionRate;
    }

    public List<OfflineUserForm> getOfflineUser() {
        return offlineUser;
    }

    public void setOfflineUser(List<OfflineUserForm> offlineUser) {
        this.offlineUser = offlineUser;
    }

    public boolean isEnableSanhindaPay() {
        return enableSanhindaPay;
    }

    public void setEnableSanhindaPay(boolean enableSanhindaPay) {
        this.enableSanhindaPay = enableSanhindaPay;
    }

    public String getOnetimePassword() {
        return onetimePassword;
    }

    public void setOnetimePassword(String onetimePassword) {
        this.onetimePassword = onetimePassword;
    }

    public boolean isRefNumberEnable() {
        return refNumberEnable;
    }

    public void setRefNumberEnable(boolean refNumberEnable) {
        this.refNumberEnable = refNumberEnable;
    }

    public boolean isTleProfilesEnable() {
        return tleProfilesEnable;
    }

    public void setTleProfilesEnable(boolean tleProfilesEnable) {
        this.tleProfilesEnable = tleProfilesEnable;
    }
}

