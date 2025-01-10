package com.aiken.pos.admin.api.dto;

import com.aiken.pos.admin.web.form.OfflineUserForm;

import java.util.List;

/**
 * Device Dto
 *
 * @author Amesh Madumalka
 * @version 1.0
 * @since 2021-10-02
 */
public class ProfileDeviceDto {
    private Integer deviceId;
    private String serialNo;
    private String token;
    private String bankCode;
    private String mName;
    private String mAdrs;
    private boolean forceSettle;
    private boolean noSettle;
    private boolean autoSettle;
    private String settleTime;
    private boolean saleEcr;
    private boolean qrEcr;
    private boolean eSign;
    private boolean debugMode;
    private boolean keyIn;
    private boolean actTrack;
    private boolean revHstry;
    private boolean qrRefund;
    private boolean enableAmex;
    private boolean notification;
    private boolean preAuth;
    private boolean dcc;
    private boolean offline;
    private boolean keyInForAmex;
    private boolean popupMsg;

    private boolean keyInForPreAuth;

    private boolean keyInForOffline;

    private boolean enablePinPriority;

    private boolean blockMag;

    private boolean customerReceipt;
    private String newVoidPwd;
    private String newSettlementPwd;

    //ECR 
    /*
     * private boolean cardTypeValidation; private boolean saleReceipt; private
     * boolean dccPayload;
     *
     * private boolean currencyFromBin; private boolean currencyFromCard; private
     * boolean proceedWithLkr;
     *
     * private boolean cardTap; private boolean cardInsert; private boolean
     * cardSwipe;
     */

    private String network;

    private boolean autoReversal;

    private boolean printless;

    private boolean disableFrgnPmntForLkrCard;

    private String voidSettlePwd;

    private CustomEcrSettingsDto ecrSettings;

    private CustomAuthServerConfigDto authServerSettings;
    private boolean midTidSeg;
    private boolean eventAutoUpdate;
    private boolean imeiScan;
    private boolean commission;
    private float commissionRate;
    private boolean enableSanhindaPay;
    private boolean refNumberEnable;
    private boolean tleProfilesEnable;
    private List<OfflineUserForm> offlineUser;
    private List<ProfileMerchantDto> merchants;

    private List<ProfilesDto> profiles;

    private List<BinConfigDto> binConfig;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAdrs() {
        return mAdrs;
    }

    public void setmAdrs(String mAdrs) {
        this.mAdrs = mAdrs;
    }

    public boolean isForceSettle() {
        return forceSettle;
    }

    public void setForceSettle(boolean forceSettle) {
        this.forceSettle = forceSettle;
    }

    public boolean isNoSettle() {
        return noSettle;
    }

    public void setNoSettle(boolean noSettle) {
        this.noSettle = noSettle;
    }

    public boolean isAutoSettle() {
        return autoSettle;
    }

    public void setAutoSettle(boolean autoSettle) {
        this.autoSettle = autoSettle;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public boolean isSaleEcr() {
        return saleEcr;
    }

    public void setSaleEcr(boolean saleEcr) {
        this.saleEcr = saleEcr;
    }

    public boolean isQrEcr() {
        return qrEcr;
    }

    public void setQrEcr(boolean qrEcr) {
        this.qrEcr = qrEcr;
    }

    public boolean iseSign() {
        return eSign;
    }

    public void seteSign(boolean eSign) {
        this.eSign = eSign;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isKeyIn() {
        return keyIn;
    }

    public void setKeyIn(boolean keyIn) {
        this.keyIn = keyIn;
    }

    public boolean isActTrack() {
        return actTrack;
    }

    public void setActTrack(boolean actTrack) {
        this.actTrack = actTrack;
    }

    public boolean isRevHstry() {
        return revHstry;
    }

    public void setRevHstry(boolean revHstry) {
        this.revHstry = revHstry;
    }

    public boolean isQrRefund() {
        return qrRefund;
    }

    public void setQrRefund(boolean qrRefund) {
        this.qrRefund = qrRefund;
    }

    public List<ProfileMerchantDto> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<ProfileMerchantDto> merchants) {
        this.merchants = merchants;
    }

    public List<ProfilesDto> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfilesDto> profiles) {
        this.profiles = profiles;
    }

    public boolean isEnableAmex() {
        return enableAmex;
    }

    public void setEnableAmex(boolean enableAmex) {
        this.enableAmex = enableAmex;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isPreAuth() {
        return preAuth;
    }

    public void setPreAuth(boolean preAuth) {
        this.preAuth = preAuth;
    }

    public boolean isDcc() {
        return dcc;
    }

    public void setDcc(boolean dcc) {
        this.dcc = dcc;
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
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

    public List<BinConfigDto> getBinConfig() {
        return binConfig;
    }

    public void setBinConfig(List<BinConfigDto> binConfig) {
        this.binConfig = binConfig;
    }

    /*
     * public boolean isCardTypeValidation() { return cardTypeValidation; }
     *
     * public void setCardTypeValidation(boolean cardTypeValidation) {
     * this.cardTypeValidation = cardTypeValidation; }
     *
     * public boolean isSaleReceipt() { return saleReceipt; }
     *
     * public void setSaleReceipt(boolean saleReceipt) { this.saleReceipt =
     * saleReceipt; }
     *
     * public boolean isCurrencyFromBin() { return currencyFromBin; }
     *
     * public void setCurrencyFromBin(boolean currencyFromBin) {
     * this.currencyFromBin = currencyFromBin; }
     *
     * public boolean isCurrencyFromCard() { return currencyFromCard; }
     *
     * public void setCurrencyFromCard(boolean currencyFromCard) {
     * this.currencyFromCard = currencyFromCard; }
     *
     * public boolean isProceedWithLkr() { return proceedWithLkr; }
     *
     * public void setProceedWithLkr(boolean proceedWithLkr) { this.proceedWithLkr =
     * proceedWithLkr; }
     *
     * public boolean isCardTap() { return cardTap; }
     *
     * public void setCardTap(boolean cardTap) { this.cardTap = cardTap; }
     *
     * public boolean isCardInsert() { return cardInsert; }
     *
     * public void setCardInsert(boolean cardInsert) { this.cardInsert = cardInsert;
     * }
     *
     * public boolean isCardSwipe() { return cardSwipe; }
     *
     * public void setCardSwipe(boolean cardSwipe) { this.cardSwipe = cardSwipe; }
     */

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public boolean isAutoReversal() {
        return autoReversal;
    }

    public void setAutoReversal(boolean autoReversal) {
        this.autoReversal = autoReversal;
    }


    /*
     * public boolean isDccPayload() { return dccPayload; }
     *
     * public void setDccPayload(boolean dccPayload) { this.dccPayload = dccPayload;
     * }
     */

    public boolean isPrintless() {
        return printless;
    }

    public void setPrintless(boolean printless) {
        this.printless = printless;
    }

    public CustomEcrSettingsDto getEcrSettings() {
        return ecrSettings;
    }

    public void setEcrSettings(CustomEcrSettingsDto ecrSettings) {
        this.ecrSettings = ecrSettings;
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

    public void setCustomerReceipt(boolean customerReceipt) {
        this.customerReceipt = customerReceipt;
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

    public CustomAuthServerConfigDto getAuthServerSettings() {
        return authServerSettings;
    }

    public void setAuthServerSettings(CustomAuthServerConfigDto authServerSettings) {
        this.authServerSettings = authServerSettings;
    }

    public boolean isEnableSanhindaPay() {
        return enableSanhindaPay;
    }

    public void setEnableSanhindaPay(boolean enableSanhindaPay) {
        this.enableSanhindaPay = enableSanhindaPay;
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