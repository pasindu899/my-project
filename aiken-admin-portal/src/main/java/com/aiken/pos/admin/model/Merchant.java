package com.aiken.pos.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

/**
 * Database Model
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-12
 */

public class Merchant {

    private Integer merchantId;
    @JsonIgnore
    private Integer tempMerchantId;
    private String category;

    private String merchantType;
    private Integer month;
    private String mid;
    private String tid;
    private String currency;
    private String description;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private boolean voidTx;
    private boolean amexTx;
    
    private boolean dcc;
    private boolean preAuth;
    private boolean offline;

    private boolean jcb;

    private ScanParam scanParam;
    private AmexParam amexParam;
    @JsonIgnore(value = true)
    private String action;

    private boolean localMer;

    private boolean foreignMer;

    private boolean onUs;

    private boolean offUs;

    private boolean midTidSeg;

    private boolean iphoneImei;

    @Override
    public String toString() {
        return "Merchant : [" + "type:" + category + "|" + "mid:" + mid + "|" + "tid:" + tid + "|" + "currency:" + currency
                + "|" + "description:" + description + "|" + "minAmount:" + minAmount + "|" + "maxAmount:" + maxAmount
                + "]";
    }

    public Merchant() {
        super();
    }

    public Merchant(Integer merchantId, String category, Integer month, String mid, String tid, String currency,
                    String description, BigDecimal minAmount, BigDecimal maxAmount, boolean voidTx) {
        super();
        this.merchantId = merchantId;
        this.category = category;
        this.month = month;
        this.mid = mid;
        this.tid = tid;
        this.currency = currency;
        this.description = description;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.voidTx = voidTx;
    }

    public Merchant(Integer merchantId, String category, Integer month, String mid, String tid, String currency,
                    String description, BigDecimal minAmount, BigDecimal maxAmount, boolean voidTx, boolean amexTx) {
        super();
        this.merchantId = merchantId;
        this.category = category;
        this.month = month;
        this.mid = mid;
        this.tid = tid;
        this.currency = currency;
        this.description = description;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.voidTx = voidTx;
        this.amexTx = amexTx;
    }

    public Merchant(String category, Integer month, String mid, String tid, String currency, String description,
                    BigDecimal minAmount, BigDecimal maxAmount, boolean voidTx) {
        super();
        this.category = category;
        this.month = month;
        this.mid = mid;
        this.tid = tid;
        this.currency = currency;
        this.description = description;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.voidTx = voidTx;
    }

    public Merchant(String category, Integer month, String mid, String tid, String currency, String description,
                    BigDecimal minAmount, BigDecimal maxAmount, boolean voidTx, boolean amexTx) {
        super();
        this.category = category;
        this.month = month;
        this.mid = mid;
        this.tid = tid;
        this.currency = currency;
        this.description = description;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.voidTx = voidTx;
        this.amexTx = amexTx;
    }

    public Merchant(Integer merchantId, String category, String mid, String tid) {
        super();
        this.merchantId = merchantId;
        this.category = category;
        this.mid = mid;
        this.tid = tid;
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

    public ScanParam getScanParam() {
        return scanParam;
    }

    public void setScanParam(ScanParam scanParam) {
        this.scanParam = scanParam;
    }

    public AmexParam getAmexParam() {
        return amexParam;
    }

    public void setAmexParam(AmexParam amexParam) {
        this.amexParam = amexParam;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getTempMerchantId() {
        return tempMerchantId;
    }

    public void setTempMerchantId(Integer tempMerchantId) {
        this.tempMerchantId = tempMerchantId;
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

    public boolean isMidTidSeg() {
        return midTidSeg;
    }

    public void setMidTidSeg(boolean midTidSeg) {
        this.midTidSeg = midTidSeg;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
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
}
