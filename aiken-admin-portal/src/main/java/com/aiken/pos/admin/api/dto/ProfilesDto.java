package com.aiken.pos.admin.api.dto;

/**
 * Profile Dto
 *
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2021-10-05
 */

import java.math.BigDecimal;
import java.util.List;

public class ProfilesDto {
    private Integer pId;
    private String pName;
    private boolean pDefault;
    private String mName;
    private String mAdrs;
    private boolean visaCnt;
    private boolean visaCntls;
    private BigDecimal visaNoCvm;
    private BigDecimal visaCntslTrxn;
    private boolean mcCnt;
    private boolean mcCntls;
    private BigDecimal mcNoCvm;
    private BigDecimal mcCntslTrxn;
    private boolean amexCnt;
    private boolean amexCntls;
    private BigDecimal amexNoCvm;
    private BigDecimal amexCntslTrxn;
    private boolean upayCnt;
    private boolean upayCntls;
    private BigDecimal upayNoCvm;
    private BigDecimal upayCntslTrxn;
    private boolean jcbCnt;
    private boolean jcbCntls;
    private BigDecimal jcbNoCvm;
    private BigDecimal jcbCntslTrxn;
    private String status;
    private boolean customerCopy;
    private boolean tlsEnable;

    private List<ProfileMerchantMappingDto> pMerchants;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public boolean ispDefault() {
        return pDefault;
    }

    public void setpDefault(boolean pDefault) {
        this.pDefault = pDefault;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAdrs() {
        return mAdrs;
    }

    public void setmAdrs(String mAdrs) {
        this.mAdrs = mAdrs;
    }

    public boolean isVisaCnt() {
        return visaCnt;
    }

    public void setVisaCnt(boolean visaCnt) {
        this.visaCnt = visaCnt;
    }

    public boolean isVisaCntls() {
        return visaCntls;
    }

    public void setVisaCntls(boolean visaCntls) {
        this.visaCntls = visaCntls;
    }

    public BigDecimal getVisaNoCvm() {
        return visaNoCvm;
    }

    public void setVisaNoCvm(BigDecimal visaNoCvm) {
        this.visaNoCvm = visaNoCvm;
    }

    public BigDecimal getVisaCntslTrxn() {
        return visaCntslTrxn;
    }

    public void setVisaCntslTrxn(BigDecimal visaCntslTrxn) {
        this.visaCntslTrxn = visaCntslTrxn;
    }

    public boolean isMcCnt() {
        return mcCnt;
    }

    public void setMcCnt(boolean mcCnt) {
        this.mcCnt = mcCnt;
    }

    public boolean isMcCntls() {
        return mcCntls;
    }

    public void setMcCntls(boolean mcCntls) {
        this.mcCntls = mcCntls;
    }

    public BigDecimal getMcNoCvm() {
        return mcNoCvm;
    }

    public void setMcNoCvm(BigDecimal mcNoCvm) {
        this.mcNoCvm = mcNoCvm;
    }

    public BigDecimal getMcCntslTrxn() {
        return mcCntslTrxn;
    }

    public void setMcCntslTrxn(BigDecimal mcCntslTrxn) {
        this.mcCntslTrxn = mcCntslTrxn;
    }

    public boolean isAmexCnt() {
        return amexCnt;
    }

    public void setAmexCnt(boolean amexCnt) {
        this.amexCnt = amexCnt;
    }

    public boolean isAmexCntls() {
        return amexCntls;
    }

    public void setAmexCntls(boolean amexCntls) {
        this.amexCntls = amexCntls;
    }

    public BigDecimal getAmexNoCvm() {
        return amexNoCvm;
    }

    public void setAmexNoCvm(BigDecimal amexNoCvm) {
        this.amexNoCvm = amexNoCvm;
    }

    public BigDecimal getAmexCntslTrxn() {
        return amexCntslTrxn;
    }

    public void setAmexCntslTrxn(BigDecimal amexCntslTrxn) {
        this.amexCntslTrxn = amexCntslTrxn;
    }

    public boolean isUpayCnt() {
        return upayCnt;
    }

    public void setUpayCnt(boolean upayCnt) {
        this.upayCnt = upayCnt;
    }

    public boolean isUpayCntls() {
        return upayCntls;
    }

    public void setUpayCntls(boolean upayCntls) {
        this.upayCntls = upayCntls;
    }

    public BigDecimal getUpayNoCvm() {
        return upayNoCvm;
    }

    public void setUpayNoCvm(BigDecimal upayNoCvm) {
        this.upayNoCvm = upayNoCvm;
    }

    public BigDecimal getUpayCntslTrxn() {
        return upayCntslTrxn;
    }

    public void setUpayCntslTrxn(BigDecimal upayCntslTrxn) {
        this.upayCntslTrxn = upayCntslTrxn;
    }

    public boolean isJcbCnt() {
        return jcbCnt;
    }

    public void setJcbCnt(boolean jcbCnt) {
        this.jcbCnt = jcbCnt;
    }

    public boolean isJcbCntls() {
        return jcbCntls;
    }

    public void setJcbCntls(boolean jcbCntls) {
        this.jcbCntls = jcbCntls;
    }

    public BigDecimal getJcbNoCvm() {
        return jcbNoCvm;
    }

    public void setJcbNoCvm(BigDecimal jcbNoCvm) {
        this.jcbNoCvm = jcbNoCvm;
    }

    public BigDecimal getJcbCntslTrxn() {
        return jcbCntslTrxn;
    }

    public void setJcbCntslTrxn(BigDecimal jcbCntslTrxn) {
        this.jcbCntslTrxn = jcbCntslTrxn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProfileMerchantMappingDto> getpMerchants() {
        return pMerchants;
    }

    public void setpMerchants(List<ProfileMerchantMappingDto> pMerchants) {
        this.pMerchants = pMerchants;
    }

    public boolean isCustomerCopy() {
        return customerCopy;
    }

    public void setCustomerCopy(boolean customerCopy) {
        this.customerCopy = customerCopy;
    }

    public boolean isTlsEnable() {
        return tlsEnable;
    }

    public void setTlsEnable(boolean tlsEnable) {
        this.tlsEnable = tlsEnable;
    }
}
