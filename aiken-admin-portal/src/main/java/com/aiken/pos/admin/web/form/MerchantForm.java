package com.aiken.pos.admin.web.form;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Database Model
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-12
 */

public class MerchantForm {

	// merchant params
	private Integer merchantId;
	private String type;
	@Min(0)
	@Max(96)
	private Integer month;
	@NotEmpty(message = "MID is required")
	private String mid;
	@NotEmpty(message = "TID is required")
	private String tid;
	private String currency;
	@NotEmpty(message = "Description is required")
	private String description;
	private BigDecimal minAmount;
	private BigDecimal maxAmount;
	private boolean voidTx;
	private boolean amexTx;
	
    private boolean dcc;
    private boolean preAuth;
    private boolean offline;

	private boolean jcb;

	// scan Pram Props
	@JsonIgnore(value = true)
	private Integer scanParamId;
	private String merchantUserId;
	private String merchantPassword;
	private String checksumKey;
	
	private String cid;
	private String vid;

	private boolean qrRefId;

	// amex prams
	@JsonIgnore(value = true)
	private Integer amexParamId;
	private String amexIp;

	@JsonIgnore(value = true)
	private String action;

	@JsonIgnore
	private boolean selectMerchant;
	private boolean defaultMerchant;
	private Integer profileId;

	private String merchantType;

	private boolean localMer;

	private boolean foreignMer;

	private boolean onUs;
	private boolean offUs;

	private boolean midTidSeg;

	private boolean iphoneImei;


	public MerchantForm() {
		super();
	}

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isSelectMerchant() {
		return selectMerchant;
	}

	public void setSelectMerchant(boolean selectMerchant) {
		this.selectMerchant = selectMerchant;
	}

	public boolean isDefaultMerchant() {
		return defaultMerchant;
	}

	public void setDefaultMerchant(boolean defaultMerchant) {
		this.defaultMerchant = defaultMerchant;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
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
}
