package com.aiken.pos.admin.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

/**
 * Database Model
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-12
 */

public class MerchantDto {

    // merchant params
    private Integer merchantId;
    @Deprecated
    private String type;
    private String category;
    private Integer month;
    private String mid;
    private String tid;
    private String currency;
    private String description;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private boolean voidTx;
    private boolean amexTx;

    // scan Pram Props
    @JsonIgnore(value = true)
    private Integer scanParamId;
    private String merchantUserId;
    private String merchantPassword;
    private String checksumKey;

    // amex prams
    @JsonIgnore(value = true)
    private Integer amexParamId;
    private String amexIp;

    private String merchantType;
    private boolean localMer;

    private boolean foreignMer;

    private boolean onUs;

    private boolean offUs;

    private boolean iphoneImei;

    public boolean isLocalMer() {
        return localMer;
    }

    public void setLocalMer(boolean localMer) {
        this.localMer = localMer;
    }

    public boolean isForeignMer() {
        return foreignMer;
    }

    public void setForeignMer(boolean foreignMer) {
        this.foreignMer = foreignMer;
    }

    public boolean isOnUs() {
        return onUs;
    }

    public void setOnUs(boolean onUs) {
        this.onUs = onUs;
    }

    public boolean isOffUs() {
        return offUs;
    }

    public void setOffUs(boolean offUs) {
        this.offUs = offUs;
    }

    public MerchantDto() {
        super();
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public boolean isVoidTx() {
        return voidTx;
    }

    public void setVoidTx(boolean voidTx) {
        this.voidTx = voidTx;
    }

    public boolean isAmexTx() {
        return amexTx;
    }

    public void setAmexTx(boolean amexTx) {
        this.amexTx = amexTx;
    }

    public Integer getScanParamId() {
        return scanParamId;
    }

    public void setScanParamId(Integer scanParamId) {
        this.scanParamId = scanParamId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public String getChecksumKey() {
        return checksumKey;
    }

    public void setChecksumKey(String checksumKey) {
        this.checksumKey = checksumKey;
    }

    public Integer getAmexParamId() {
        return amexParamId;
    }

    public void setAmexParamId(Integer amexParamId) {
        this.amexParamId = amexParamId;
    }

    public String getAmexIp() {
        return amexIp;
    }

    public void setAmexIp(String amexIp) {
        this.amexIp = amexIp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public boolean isIphoneImei() {
        return iphoneImei;
    }

    public void setIphoneImei(boolean iphoneImei) {
        this.iphoneImei = iphoneImei;
    }
}
