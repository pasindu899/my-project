package com.aiken.pos.admin.api.dto.configdtos;

import java.math.BigDecimal;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-18
 */
public class MerchantDto {

    // merchant params
    private Integer id;
    private String type;
    private String category;
    private String merchantName;
    private String merchantAddress;
    private String mid;
    private String tid;
    private String currency;
    private String description;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Boolean foreignMer;
    private Boolean supportsOffline;
    private Boolean supportsPreAuth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Boolean getForeignMer() {
        return foreignMer;
    }

    public void setForeignMer(Boolean foreignMer) {
        this.foreignMer = foreignMer;
    }

    public Boolean getSupportsOffline() {
        return supportsOffline;
    }

    public void setSupportsOffline(Boolean supportsOffline) {
        this.supportsOffline = supportsOffline;
    }

    public Boolean getSupportsPreAuth() {
        return supportsPreAuth;
    }

    public void setSupportsPreAuth(Boolean supportsPreAuth) {
        this.supportsPreAuth = supportsPreAuth;
    }
}
