package com.aiken.pos.admin.api.dto;

import java.math.BigDecimal;

/**
 * Merchant Dto
 *
 * @author Amesh Madumalka
 * @version 1.0
 * @since 2021-10-02
 */
public class ProfileMerchantDto {
    private Integer refMid;
    private String category;
    private String mid;
    private Integer mId;
    private String tid;
    private String currency;
    private String desc;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Integer eppMonth;
    private String qrUserId;
    private String qrPassword;
    private String qrKey;
    private String vid;
    private String cid;
    private boolean dcc;
    private boolean preAuth;
    private boolean offline;

    private boolean jcb;

    private String merchantType;

    private boolean qrRefId;

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

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public Integer getEppMonth() {
        return eppMonth;
    }

    public void setEppMonth(Integer eppMonth) {
        this.eppMonth = eppMonth;
    }

    public String getQrUserId() {
        return qrUserId;
    }

    public void setQrUserId(String qrUserId) {
        this.qrUserId = qrUserId;
    }

    public String getQrPassword() {
        return qrPassword;
    }

    public void setQrPassword(String qrPassword) {
        this.qrPassword = qrPassword;
    }

    public String getQrKey() {
        return qrKey;
    }

    public void setQrKey(String qrKey) {
        this.qrKey = qrKey;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public boolean isDcc() {
        return dcc;
    }

    public void setDcc(boolean dcc) {
        this.dcc = dcc;
    }

    public boolean isPreAuth() {
        return preAuth;
    }

    public void setPreAuth(boolean preAuth) {
        this.preAuth = preAuth;
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public boolean isQrRefId() {
        return qrRefId;
    }

    public void setQrRefId(boolean qrRefId) {
        this.qrRefId = qrRefId;
    }

    public boolean isJcb() {
        return jcb;
    }

    public void setJcb(boolean jcb) {
        this.jcb = jcb;
    }

    public boolean isIphoneImei() {
        return iphoneImei;
    }

    public void setIphoneImei(boolean iphoneImei) {
        this.iphoneImei = iphoneImei;
    }

    public Integer getRefMid() {
        return refMid;
    }

    public void setRefMid(Integer refMid) {
        this.refMid = refMid;
    }
}