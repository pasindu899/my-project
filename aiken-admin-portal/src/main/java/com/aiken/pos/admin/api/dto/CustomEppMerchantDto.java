package com.aiken.pos.admin.api.dto;

/**
 * Database Model
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-12
 */

public class CustomEppMerchantDto extends CustomMerchantDto{

    private Integer merchantId;
    private String type;
    private Integer month;
    private String mid;
    private String tid;
    private String currency;
    private String description;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
