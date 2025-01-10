package com.aiken.pos.admin.api.dto;

/**
 * Database Model
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-12
 */

public class CustomQrMerchantDto extends CustomMerchantDto{
	private Integer merchantId;
    private String type;
    private String mid;
    private String tid;
    private String currency;
    private String description;
    private String merchantUserId;
    private String merchantPassword;
    private String checksumKey;
    private String vid;
    private String cid;

    private boolean qrRefId;

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

    public boolean isQrRefId() {
        return qrRefId;
    }

    public void setQrRefId(boolean qrRefId) {
        this.qrRefId = qrRefId;
    }
}
