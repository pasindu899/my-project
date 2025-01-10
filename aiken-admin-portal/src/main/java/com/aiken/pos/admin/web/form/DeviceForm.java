package com.aiken.pos.admin.web.form;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

/**
 * Device Form POJO
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-29
 */

public class DeviceForm {
    // Device Props
    private Integer deviceId;
    @NotEmpty(message = "Serial No is required")
    private String serialNo;
    private String token;
    private String bankCode;
    @NotEmpty(message = "Bank Name is required")
    private String bankName;
    @NotEmpty(message = "Merchant Name is required")
    private String merchantName;
    @NotEmpty(message = "Merchant Address is required")
    private String merchantAddress;

    //@NotNull(message = "Visa No CVM Limit is required")
    private BigDecimal visaNoCvmLimit;
    //@NotNull(message = "Contactless Transaction Limit is required")
    private BigDecimal cntactlsTrxnLimit;

    // auto settle
    private boolean autoSettle;
    private String autoSettleTime;
    private boolean forceSettle;

    private boolean ecr;
    private boolean keyIn;
    private String mobileNo;
    private String simNo;
    private String custContactNo;
    private String remark;
    private boolean ecrQr;
    private boolean signature;
    private boolean debugMode;
    private boolean noSettle;
    private boolean activityTracker;
    private boolean qrRefund;
    private boolean reversalHistory;
    private boolean pushNotification;
    private boolean enableAmex;
    private boolean preAuth;

    private boolean dcc;
    private boolean offline;
    private boolean keyInAmex;
    private boolean popupMsg;

    //ECR 
    private boolean cardTypeValidation;
    private boolean saleReceipt;

    private boolean currencyFromBin;
    private boolean currencyFromCard;
    private boolean proceedWithLkr;

    private boolean cardTap;
    private boolean cardInsert;
    private boolean cardSwipe;

    private boolean network2g;
    private boolean network3g;
    private boolean network4g;

    private boolean autoReversal;

    private boolean merchantPortal;
    private boolean resendVoid;
    private boolean clientCredentials;

    private boolean dccPayload;

    private boolean printless;

    private boolean lkrDefaultCurr;

    private boolean autoChange;

    private String voidPwd;
    private String newVoidPwd;
    private String newSettlementPwd;

    private boolean diffSaleMidTid;

    private boolean mkiPreAuth;

    private boolean mkiOffline;

    private boolean signPriority;

    private boolean blockMag;

    private boolean customerReceipt;

    private boolean midTidSeg;
    private boolean eventAutoUpdate;
    private boolean printReceipt;
    private boolean imeiScan;
    private boolean commission;
    private float commissionRate;
    private List<OfflineUserForm> offlineUser;
    private String ecrIp;
    private String ecrPort;
    private boolean ecrAuthToken;
    private boolean tranToSim;
    private boolean ecrWifi;
    private boolean enableSanhindaPay;
    private boolean refNumberEnable;
    private boolean tleProfilesEnable;

    public DeviceForm() {
        super();
    }

    public DeviceForm(String serialNo, String bankCode, String bankName, String merchantName, String merchantAddress) {
        super();
        this.serialNo = serialNo;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.merchantName = merchantName;
        this.merchantAddress = merchantAddress;
    }

    public DeviceForm(Integer deviceId, String serialNo, String bankCode, String bankName, String merchantName,
                      String merchantAddress) {
        super();
        this.deviceId = deviceId;
        this.serialNo = serialNo;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.merchantName = merchantName;
        this.merchantAddress = merchantAddress;
    }


    public boolean isNetwork2g() {
        return network2g;
    }

    public void setNetwork2g(boolean network2g) {
        this.network2g = network2g;
    }

    public boolean isNetwork3g() {
        return network3g;
    }

    public void setNetwork3g(boolean network3g) {
        this.network3g = network3g;
    }

    public boolean isNetwork4g() {
        return network4g;
    }

    public void setNetwork4g(boolean network4g) {
        this.network4g = network4g;
    }

    public boolean isPreAuth() {
        return preAuth;
    }

    public void setPreAuth(boolean preAuth) {
        this.preAuth = preAuth;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public String getCustContactNo() {
        return custContactNo;
    }

    public void setCustContactNo(String custContactNo) {
        this.custContactNo = custContactNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public boolean isActivityTracker() {
        return activityTracker;
    }

    public void setActivityTracker(boolean activityTracker) {
        this.activityTracker = activityTracker;
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

    public boolean isKeyInAmex() {
        return keyInAmex;
    }

    public void setKeyInAmex(boolean keyInAmex) {
        this.keyInAmex = keyInAmex;
    }

    public boolean isPopupMsg() {
        return popupMsg;
    }

    public void setPopupMsg(boolean popupMsg) {
        this.popupMsg = popupMsg;
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

    public boolean isLkrDefaultCurr() {
        return lkrDefaultCurr;
    }

    public void setLkrDefaultCurr(boolean lkrDefaultCurr) {
        this.lkrDefaultCurr = lkrDefaultCurr;
    }

    public boolean isAutoChange() {
        return autoChange;
    }

    public void setAutoChange(boolean autoChange) {
        this.autoChange = autoChange;
    }

    public String getVoidPwd() {
        return voidPwd;
    }

    public void setVoidPwd(String voidPwd) {
        this.voidPwd = voidPwd;
    }

    public boolean isDiffSaleMidTid() {
        return diffSaleMidTid;
    }

    public void setDiffSaleMidTid(boolean diffSaleMidTid) {
        this.diffSaleMidTid = diffSaleMidTid;
    }

    public boolean isMkiPreAuth() {
        return mkiPreAuth;
    }

    public void setMkiPreAuth(boolean mkiPreAuth) {
        this.mkiPreAuth = mkiPreAuth;
    }

    public boolean isMkiOffline() {
        return mkiOffline;
    }

    public void setMkiOffline(boolean mkiOffline) {
        this.mkiOffline = mkiOffline;
    }

    public boolean isSignPriority() {
        return signPriority;
    }

    public void setSignPriority(boolean signPriority) {
        this.signPriority = signPriority;
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

    public boolean isMidTidSeg() {
        return midTidSeg;
    }

    public void setMidTidSeg(boolean midTidSeg) {
        this.midTidSeg = midTidSeg;
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

    public String getEcrIp() {
        return ecrIp;
    }

    public void setEcrIp(String ecrIp) {
        this.ecrIp = ecrIp;
    }

    public String getEcrPort() {
        return ecrPort;
    }

    public void setEcrPort(String ecrPort) {
        this.ecrPort = ecrPort;
    }

    public boolean isEcrAuthToken() {
        return ecrAuthToken;
    }

    public void setEcrAuthToken(boolean ecrAuthToken) {
        this.ecrAuthToken = ecrAuthToken;
    }

    public boolean isTranToSim() {
        return tranToSim;
    }

    public void setTranToSim(boolean tranToSim) {
        this.tranToSim = tranToSim;
    }

    public boolean isEcrWifi() {
        return ecrWifi;
    }

    public void setEcrWifi(boolean ecrWifi) {
        this.ecrWifi = ecrWifi;
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
