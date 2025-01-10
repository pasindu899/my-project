package com.aiken.pos.admin.model;

/**
 * Database Model
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-27
 */

public class ScanParam {

	private Integer scanParamId;
	private String merchantUserId;
	private String merchantPassword;
	private String checksumKey;
	
	private String cid;
	private String vid;

	private boolean qrRefId;

	@Override
	public String toString() {
		return "ScanParam : [" + "merchantUserId:" + merchantUserId + "|" + "merchantPassword:" + merchantPassword
				+ "cid:" + cid + "|" + "vid:" + vid + "]";
	}

	public ScanParam() {
		super();
	}

	public ScanParam(Integer scanParamId, String merchantUserId, String merchantPassword, String checksumKey,String vid,String cid,boolean qrRefId) {
		super();
		this.scanParamId = scanParamId;
		this.merchantUserId = merchantUserId;
		this.merchantPassword = merchantPassword;
		this.checksumKey = checksumKey;
		this.vid = vid;
		this.cid = cid;
		this.qrRefId = qrRefId;
	}

	public ScanParam(String merchantUserId, String merchantPassword, String checksumKey,String vid,String cid,boolean qrRefId) {
		super();
		this.merchantUserId = merchantUserId;
		this.merchantPassword = merchantPassword;
		this.checksumKey = checksumKey;
		this.vid = vid;
		this.cid = cid;
		this.qrRefId = qrRefId;
	}

	public ScanParam(Integer scanParamId, String merchantUserId) {
		super();
		this.scanParamId = scanParamId;
		this.merchantUserId = merchantUserId;
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

	public boolean isQrRefId() {
		return qrRefId;
	}

	public void setQrRefId(boolean qrRefId) {
		this.qrRefId = qrRefId;
	}
}