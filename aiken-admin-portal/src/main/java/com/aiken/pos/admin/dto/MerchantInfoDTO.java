package com.aiken.pos.admin.dto;

public class MerchantInfoDTO {
    private String bankCode;
    private String serialNo;
    private String merchantName;
    private String merchantAddress;
    private String category;
    private String mid;
    private String tid;
    private String description;
    private boolean merchantPortal;

    public MerchantInfoDTO(String bankCode, String serialNo, String merchantName, String merchantAddress, String category, String mid, String tid, String description, boolean merchantPortal) {
        this.bankCode = bankCode;
        this.serialNo = serialNo;
        this.merchantName = merchantName;
        this.merchantAddress = merchantAddress;
        this.category = category;
        this.mid = mid;
        this.tid = tid;
        this.description = description;
        this.merchantPortal = merchantPortal;
    }

    public MerchantInfoDTO() {
    }

    public boolean isMerchantPortal() {
        return merchantPortal;
    }

    public void setMerchantPortal(boolean merchantPortal) {
        this.merchantPortal = merchantPortal;
    }



    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MerchantInfoDTO{" +
                "bankCode='" + bankCode + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantAddress='" + merchantAddress + '\'' +
                ", category='" + category + '\'' +
                ", mid='" + mid + '\'' +
                ", tid='" + tid + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
