package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */
public class TransactionDto {

    private String maxTransCount;
    private String maxAmount;
    private Boolean inputRefNumber;
    private Boolean saleEnable;
    private Boolean voidEnable;
    private Boolean eppEnable;
    private Boolean qrPaymentEnable;
    private Boolean voidQr;
    private Boolean fallbackL4;
    private Boolean reversalHistory;
    private Boolean signaturePadEnable;
    private Boolean preAuthEnable;
    private Boolean amexPreAuth;
    private Boolean mkiPreAuth;
    private Boolean mkiPreAuthOffline;
    private Boolean offlineSaleEnable;
    private Boolean supervisorPin;
    private Boolean manualKeyIn;
    private Boolean amexKeyIn;
    private Boolean autoSettle;
    private Boolean forceSettle;
    private Boolean noSettle;
    private Boolean foreignCurrencyPmtFromLkr;
    private Boolean separateMidTid;
    private Boolean pushyEnable;
    private String visaRfNoCvmLimit;
    private String qpsNoPswLimit;


    public String getMaxTransCount() {
        return maxTransCount;
    }

    public void setMaxTransCount(String maxTransCount) {
        this.maxTransCount = maxTransCount;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Boolean getInputRefNumber() {
        return inputRefNumber;
    }

    public void setInputRefNumber(Boolean inputRefNumber) {
        this.inputRefNumber = inputRefNumber;
    }


    public Boolean getPreAuthEnable() {
        return preAuthEnable;
    }

    public void setPreAuthEnable(Boolean preAuthEnable) {
        this.preAuthEnable = preAuthEnable;
    }

    public Boolean getAmexPreAuth() {
        return amexPreAuth;
    }

    public void setAmexPreAuth(Boolean amexPreAuth) {
        this.amexPreAuth = amexPreAuth;
    }

    public Boolean getMkiPreAuth() {
        return mkiPreAuth;
    }

    public void setMkiPreAuth(Boolean mkiPreAuth) {
        this.mkiPreAuth = mkiPreAuth;
    }

    public Boolean getMkiPreAuthOffline() {
        return mkiPreAuthOffline;
    }

    public void setMkiPreAuthOffline(Boolean mkiPreAuthOffline) {
        this.mkiPreAuthOffline = mkiPreAuthOffline;
    }

    public Boolean getSaleEnable() {
        return saleEnable;
    }

    public void setSaleEnable(Boolean saleEnable) {
        this.saleEnable = saleEnable;
    }

    public Boolean getVoidEnable() {
        return voidEnable;
    }

    public void setVoidEnable(Boolean voidEnable) {
        this.voidEnable = voidEnable;
    }

    public Boolean getEppEnable() {
        return eppEnable;
    }

    public void setEppEnable(Boolean eppEnable) {
        this.eppEnable = eppEnable;
    }

    public Boolean getQrPaymentEnable() {
        return qrPaymentEnable;
    }

    public void setQrPaymentEnable(Boolean qrPaymentEnable) {
        this.qrPaymentEnable = qrPaymentEnable;
    }

    public Boolean getVoidQr() {
        return voidQr;
    }

    public void setVoidQr(Boolean voidQr) {
        this.voidQr = voidQr;
    }

    public Boolean getFallbackL4() {
        return fallbackL4;
    }

    public void setFallbackL4(Boolean fallbackL4) {
        this.fallbackL4 = fallbackL4;
    }

    public Boolean getReversalHistory() {
        return reversalHistory;
    }

    public void setReversalHistory(Boolean reversalHistory) {
        this.reversalHistory = reversalHistory;
    }

    public Boolean getSignaturePadEnable() {
        return signaturePadEnable;
    }

    public void setSignaturePadEnable(Boolean signaturePadEnable) {
        this.signaturePadEnable = signaturePadEnable;
    }

    public Boolean getOfflineSaleEnable() {
        return offlineSaleEnable;
    }

    public void setOfflineSaleEnable(Boolean offlineSaleEnable) {
        this.offlineSaleEnable = offlineSaleEnable;
    }

    public Boolean getSupervisorPin() {
        return supervisorPin;
    }

    public void setSupervisorPin(Boolean supervisorPin) {
        this.supervisorPin = supervisorPin;
    }

    public Boolean getManualKeyIn() {
        return manualKeyIn;
    }

    public void setManualKeyIn(Boolean manualKeyIn) {
        this.manualKeyIn = manualKeyIn;
    }

    public Boolean getAmexKeyIn() {
        return amexKeyIn;
    }

    public void setAmexKeyIn(Boolean amexKeyIn) {
        this.amexKeyIn = amexKeyIn;
    }

    public Boolean getAutoSettle() {
        return autoSettle;
    }

    public void setAutoSettle(Boolean autoSettle) {
        this.autoSettle = autoSettle;
    }

    public Boolean getForceSettle() {
        return forceSettle;
    }

    public void setForceSettle(Boolean forceSettle) {
        this.forceSettle = forceSettle;
    }

    public Boolean getNoSettle() {
        return noSettle;
    }

    public void setNoSettle(Boolean noSettle) {
        this.noSettle = noSettle;
    }

    public Boolean getForeignCurrencyPmtFromLkr() {
        return foreignCurrencyPmtFromLkr;
    }

    public void setForeignCurrencyPmtFromLkr(Boolean foreignCurrencyPmtFromLkr) {
        this.foreignCurrencyPmtFromLkr = foreignCurrencyPmtFromLkr;
    }

    public Boolean getSeparateMidTid() {
        return separateMidTid;
    }

    public void setSeparateMidTid(Boolean separateMidTid) {
        this.separateMidTid = separateMidTid;
    }

    public Boolean getPushyEnable() {
        return pushyEnable;
    }

    public void setPushyEnable(Boolean pushyEnable) {
        this.pushyEnable = pushyEnable;
    }

    public String getVisaRfNoCvmLimit() {
        return visaRfNoCvmLimit;
    }

    public void setVisaRfNoCvmLimit(String visaRfNoCvmLimit) {
        this.visaRfNoCvmLimit = visaRfNoCvmLimit;
    }

    public String getQpsNoPswLimit() {
        return qpsNoPswLimit;
    }

    public void setQpsNoPswLimit(String qpsNoPswLimit) {
        this.qpsNoPswLimit = qpsNoPswLimit;
    }
}

