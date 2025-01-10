package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-15
 */
public class EcrSettingDto {

    private Boolean cardTypeValidation;
    private Boolean saleReceipt;
    private Boolean currencyFromBin;
    private Boolean currencyFromCard;
    private Boolean proceedWithLkr;
    private Boolean cardTap;
    private Boolean cardInsert;
    private Boolean cardSwipe;
    private Boolean dccPayload;
    private String ecrIp;
    private String ecrPort;
    private Boolean ecrAuthToken;
    private Boolean tranToSim;
    private Boolean ecrWifi;
    private String ecrStatus;
    private String ecrMaxErrorCount;

    public Boolean getCardTypeValidation() {
        return cardTypeValidation;
    }

    public void setCardTypeValidation(Boolean cardTypeValidation) {
        this.cardTypeValidation = cardTypeValidation;
    }

    public Boolean getSaleReceipt() {
        return saleReceipt;
    }

    public void setSaleReceipt(Boolean saleReceipt) {
        this.saleReceipt = saleReceipt;
    }

    public Boolean getCurrencyFromBin() {
        return currencyFromBin;
    }

    public void setCurrencyFromBin(Boolean currencyFromBin) {
        this.currencyFromBin = currencyFromBin;
    }

    public Boolean getCurrencyFromCard() {
        return currencyFromCard;
    }

    public void setCurrencyFromCard(Boolean currencyFromCard) {
        this.currencyFromCard = currencyFromCard;
    }

    public Boolean getProceedWithLkr() {
        return proceedWithLkr;
    }

    public void setProceedWithLkr(Boolean proceedWithLkr) {
        this.proceedWithLkr = proceedWithLkr;
    }

    public Boolean getCardTap() {
        return cardTap;
    }

    public void setCardTap(Boolean cardTap) {
        this.cardTap = cardTap;
    }

    public Boolean getCardInsert() {
        return cardInsert;
    }

    public void setCardInsert(Boolean cardInsert) {
        this.cardInsert = cardInsert;
    }

    public Boolean getCardSwipe() {
        return cardSwipe;
    }

    public void setCardSwipe(Boolean cardSwipe) {
        this.cardSwipe = cardSwipe;
    }

    public Boolean getDccPayload() {
        return dccPayload;
    }

    public void setDccPayload(Boolean dccPayload) {
        this.dccPayload = dccPayload;
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

    public Boolean getEcrAuthToken() {
        return ecrAuthToken;
    }

    public void setEcrAuthToken(Boolean ecrAuthToken) {
        this.ecrAuthToken = ecrAuthToken;
    }

    public Boolean getTranToSim() {
        return tranToSim;
    }

    public void setTranToSim(Boolean tranToSim) {
        this.tranToSim = tranToSim;
    }

    public Boolean getEcrWifi() {
        return ecrWifi;
    }

    public void setEcrWifi(Boolean ecrWifi) {
        this.ecrWifi = ecrWifi;
    }

    public String getEcrStatus() {
        return ecrStatus;
    }

    public void setEcrStatus(String ecrStatus) {
        this.ecrStatus = ecrStatus;
    }

    public String getEcrMaxErrorCount() {
        return ecrMaxErrorCount;
    }

    public void setEcrMaxErrorCount(String ecrMaxErrorCount) {
        this.ecrMaxErrorCount = ecrMaxErrorCount;
    }
}
