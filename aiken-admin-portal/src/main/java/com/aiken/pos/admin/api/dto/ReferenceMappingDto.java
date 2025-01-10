package com.aiken.pos.admin.api.dto;
/**
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2023-04-16
 */
public class ReferenceMappingDto {
    private Integer actualProfMerchantId;
    private Integer deviceRefProMerchantId;

    private Integer merchantId;

    public Integer getActualProfMerchantId() {
        return actualProfMerchantId;
    }

    public void setActualProfMerchantId(Integer actualProfMerchantId) {
        this.actualProfMerchantId = actualProfMerchantId;
    }

    public Integer getDeviceRefProMerchantId() {
        return deviceRefProMerchantId;
    }

    public void setDeviceRefProMerchantId(Integer deviceRefProMerchantId) {
        this.deviceRefProMerchantId = deviceRefProMerchantId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
}
