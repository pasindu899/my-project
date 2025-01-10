package com.aiken.pos.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

/**
 * Profile model
 *
 * @author Amesh Madumalka
 * @version 1.0
 * @since 2021-10-02
 */
public class Profile {
    private Integer profileId;

    private Integer profileRefId;
    private String profileName;
    private boolean isDefault;
    private String merchantName;
    private String merchantAdrs;
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
    private String addedDate;
    private String addedBy;
    private String lastUpdate;
    private String updatedBy;
    private String status;
    private Integer deviceId;
    @JsonIgnore
    private String action;

    private boolean customerCopy;

    private boolean tlsEnable;


    private List<ProfileMerchant> profileMerchants;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAdrs() {
        return merchantAdrs;
    }

    public void setMerchantAdrs(String merchantAdrs) {
        this.merchantAdrs = merchantAdrs;
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

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<ProfileMerchant> getProfileMerchants() {
        return profileMerchants;
    }

    public void setProfileMerchants(List<ProfileMerchant> profileMerchants) {
        this.profileMerchants = profileMerchants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getProfileRefId() {
        return profileRefId;
    }

    public void setProfileRefId(Integer profileRefId) {
        this.profileRefId = profileRefId;
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