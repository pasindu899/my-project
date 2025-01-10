package com.aiken.pos.admin.api.dto;

import com.aiken.pos.admin.web.form.OfflineUserForm;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

/**
 * Rest API DTO
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-02-10
 */
public class DeviceDto {

    // Device Props
    private Integer deviceId;
    private String serialNo;
    private String token;
    private String bankCode;
    private String bankName;
    private String merchantName;
    private String merchantAddress;
    private String addedDate;
    private String addedBy;
    private String lastUpdate;
    private String updatedBy;
    private String status;

    private BigDecimal visaNoCvmLimit;
    private BigDecimal cntactlsTrxnLimit;

    // auto settle
    @JsonIgnore(value = true)
    private Integer deviceConfigId;
    private boolean autoSettle;
    private String autoSettleTime;
    private boolean forceSettle;
    private boolean ecr;
    private boolean keyIn;
    private boolean ecrQr;
    private boolean signature;
    private boolean debugMode;
    private boolean noSettle;
    private boolean activityTracker;
    private boolean qrRefund;
    private boolean reversalHistory;
    private boolean pushNotification;
    private boolean enableAmex;
    private boolean dcc;
    private boolean offline;
    private boolean preAuth;

    //ECR 
    private boolean cardTypeValidation;
    private boolean saleReceipt;

    private boolean currencyFromBin;
    private boolean currencyFromCard;
    private boolean proceedWithLkr;

    private boolean cardTap;
    private boolean cardInsert;
    private boolean cardSwipe;

    private String network;

    private boolean autoReversal;
    private boolean printless;

    private boolean merchantPortal;
    private boolean resendVoid;
    private boolean clientCredentials;

    private boolean dccPayload;

    private boolean keyInForPreAuth;

    private boolean keyInForOffline;

    private boolean enablePinPriority;

    private boolean blockMag;

    private boolean customerReceipt;

    private List<MerchantDto> merchants;
    private boolean midTidSeg;
    private boolean eventAutoUpdate;
    private boolean printReceipt;

    private boolean imeiScan;
    private boolean commission;
    private float commissionRate;
    private boolean enableSanhindaPay;
    private boolean tleProfilesEnable;
    private List<OfflineUserForm> offlineUser;

    @Override
    public String toString() {
        return "Device : [" + "serialNo:" + serialNo + "|" + "bankCode:" + bankCode + "|" + "bankName:" + bankName + "|"
                + "merchantName:" + merchantName + "|" + "merchantAddress:" + merchantAddress + "autoSettle:"
                + autoSettle + "|" + "autoSettleTime:" + autoSettleTime + "|" + "forceSettle:" + forceSettle + "|" +
                "noSettle:" + noSettle + "|" + "ecr:" + ecr + "|" + "keyIn:" + keyIn + "|" + "activityHistory:" + activityTracker + "]";
    }

    public boolean isMidTidSeg() {
        return midTidSeg;
    }

    public void setMidTidSeg(boolean midTidSeg) {
        this.midTidSeg = midTidSeg;
    }

    public DeviceDto() {
        super();
    }

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDeviceConfigId() {
        return deviceConfigId;
    }

    public void setDeviceConfigId(Integer deviceConfigId) {
        this.deviceConfigId = deviceConfigId;
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

    public List<MerchantDto> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<MerchantDto> merchants) {
        this.merchants = merchants;
    }

    public BigDecimal getVisaNoCvmLimit() {
        return visaNoCvmLimit;
    }

    public void setVisaNoCvmLimit(BigDecimal visaNoCvmLimit) {
        this.visaNoCvmLimit = visaNoCvmLimit;
    }

    public BigDecimal getCntactlsTrxnLimit() {
        return cntactlsTrxnLimit;
    }

    public void setCntactlsTrxnLimit(BigDecimal cntactlsTrxnLimit) {
        this.cntactlsTrxnLimit = cntactlsTrxnLimit;
    }

    public boolean isEcr() {
        return ecr;
    }

    public void setEcr(boolean ecr) {
        this.ecr = ecr;
    }

    public boolean isKeyIn() {
        return keyIn;
    }

    public void setKeyIn(boolean keyIn) {
        this.keyIn = keyIn;
    }

    public boolean isEcrQr() {
        return ecrQr;
    }

    public void setEcrQr(boolean ecrQr) {
        this.ecrQr = ecrQr;
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

    public boolean isQrRefund() {
        return qrRefund;
    }

    public void setQrRefund(boolean qrRefund) {
        this.qrRefund = qrRefund;
    }

    public boolean isActivityTracker() {
        return activityTracker;
    }

    public void setActivityTracker(boolean activityTracker) {
        this.activityTracker = activityTracker;
    }

    public boolean isReversalHistory() {
        return reversalHistory;
    }

    public void setReversalHistory(boolean reversalHistory) {
        this.reversalHistory = reversalHistory;
    }

    public boolean isPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public boolean isEnableAmex() {
        return enableAmex;
    }

    public void setEnableAmex(boolean enableAmex) {
        this.enableAmex = enableAmex;
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

    public boolean isPreAuth() {
        return preAuth;
    }

    public void setPreAuth(boolean preAuth) {
        this.preAuth = preAuth;
    }

    public boolean isCardTypeValidation() {
        return cardTypeValidation;
    }

    public void setCardTypeValidation(boolean cardTypeValidation) {
        this.cardTypeValidation = cardTypeValidation;
    }

    public boolean isSaleReceipt() {
        return saleReceipt;
    }

    public void setSaleReceipt(boolean saleReceipt) {
        this.saleReceipt = saleReceipt;
    }

    public boolean isCurrencyFromBin() {
        return currencyFromBin;
    }

    public void setCurrencyFromBin(boolean currencyFromBin) {
        this.currencyFromBin = currencyFromBin;
    }

    public boolean isCurrencyFromCard() {
        return currencyFromCard;
    }

    public void setCurrencyFromCard(boolean currencyFromCard) {
        this.currencyFromCard = currencyFromCard;
    }

    public boolean isProceedWithLkr() {
        return proceedWithLkr;
    }

    public void setProceedWithLkr(boolean proceedWithLkr) {
        this.proceedWithLkr = proceedWithLkr;
    }

    public boolean isCardTap() {
        return cardTap;
    }

    public void setCardTap(boolean cardTap) {
        this.cardTap = cardTap;
    }

    public boolean isCardInsert() {
        return cardInsert;
    }

    public void setCardInsert(boolean cardInsert) {
        this.cardInsert = cardInsert;
    }

    public boolean isCardSwipe() {
        return cardSwipe;
    }

    public void setCardSwipe(boolean cardSwipe) {
        this.cardSwipe = cardSwipe;
    }

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

    public boolean isMerchantPortal() {
        return merchantPortal;
    }

    public void setMerchantPortal(boolean merchantPortal) {
        this.merchantPortal = merchantPortal;
    }

    public boolean isResendVoid() {
        return resendVoid;
    }

    public void setResendVoid(boolean resendVoid) {
        this.resendVoid = resendVoid;
    }

    public boolean isClientCredentials() {
        return clientCredentials;
    }

    public void setClientCredentials(boolean clientCredentials) {
        this.clientCredentials = clientCredentials;
    }

    public boolean isDccPayload() {
        return dccPayload;
    }

    public void setDccPayload(boolean dccPayload) {
        this.dccPayload = dccPayload;
    }

    public boolean isPrintless() {
        return printless;
    }

    public void setPrintless(boolean printless) {
        this.printless = printless;
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

    public boolean isEventAutoUpdate() {
        return eventAutoUpdate;
    }

    public void setEventAutoUpdate(boolean eventAutoUpdate) {
        this.eventAutoUpdate = eventAutoUpdate;
    }

    public boolean isPrintReceipt() {
        return printReceipt;
    }

    public void setPrintReceipt(boolean printReceipt) {
        this.printReceipt = printReceipt;
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

    public boolean isEnableSanhindaPay() {
        return enableSanhindaPay;
    }

    public void setEnableSanhindaPay(boolean enableSanhindaPay) {
        this.enableSanhindaPay = enableSanhindaPay;
    }

    public List<OfflineUserForm> getOfflineUser() {
        return offlineUser;
    }

    public void setOfflineUser(List<OfflineUserForm> offlineUser) {
        this.offlineUser = offlineUser;
    }

    public boolean isTleProfilesEnable() {
        return tleProfilesEnable;
    }

    public void setTleProfilesEnable(boolean tleProfilesEnable) {
        this.tleProfilesEnable = tleProfilesEnable;
    }
}
