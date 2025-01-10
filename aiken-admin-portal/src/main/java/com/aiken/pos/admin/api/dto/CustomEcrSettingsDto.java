/**
 *
 */
package com.aiken.pos.admin.api.dto;

/**
 * @author Nandana Basnayake
 *
 * @created_On Sep 13, 2022
 */
public class CustomEcrSettingsDto {

    private boolean cardTypeValidation;
    private boolean saleReceipt;
    private boolean currencyFromBin;
    private boolean currencyFromCard;
    private boolean proceedWithLkr;
    private boolean cardTap;
    private boolean cardInsert;
    private boolean cardSwipe;
    private boolean dccPayload;
    private String ecrIp;
    private String ecrPort;
    private boolean ecrAuthToken;
    private boolean tranToSim;
    private boolean ecrWifi;

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

    public boolean isDccPayload() {
        return dccPayload;
    }

    public void setDccPayload(boolean dccPayload) {
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
}
