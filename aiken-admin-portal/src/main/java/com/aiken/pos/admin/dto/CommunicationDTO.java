package com.aiken.pos.admin.dto;

public class CommunicationDTO {
    private String bankCode;
    private String serialNo;
    private String merchantName;
    private String mid;
    private String tid;
    private String operator1;
    private String operator2;
    private String sim1;
    private String sim2;
    private String ref1;
    private String ref2;

    public CommunicationDTO(String bankCode, String serialNo, String merchantName, String mid, String tid, String operator1, String operator2, String sim1, String sim2, String ref1, String ref2) {
        this.bankCode = bankCode;
        this.serialNo = serialNo;
        this.merchantName = merchantName;
        this.mid = mid;
        this.tid = tid;
        this.operator1 = operator1;
        this.operator2 = operator2;
        this.sim1 = sim1;
        this.sim2 = sim2;
        this.ref1 = ref1;
        this.ref2 = ref2;
    }

    public CommunicationDTO() {

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

    public String getOperator1() {
        return operator1;
    }

    public void setOperator1(String operator1) {
        this.operator1 = operator1;
    }

    public String getOperator2() {
        return operator2;
    }

    public String getSim1() {
        return sim1;
    }

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public String getRef2() {
        return ref2;
    }

    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }

    public void setSim1(String sim1) {
        this.sim1 = sim1;
    }

    public String getSim2() {
        return sim2;
    }

    public void setSim2(String sim2) {
        this.sim2 = sim2;
    }

    public void setOperator2(String operator2) {
        this.operator2 = operator2;
    }
}
