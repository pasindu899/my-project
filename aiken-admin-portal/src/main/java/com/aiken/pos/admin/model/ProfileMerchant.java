package com.aiken.pos.admin.model;

/**
 * Profile Merchant model
 *
 * @author Amesh Madumalka
 * @version 1.0
 * @since 2021-10-02
 */
public class ProfileMerchant {
    private Integer profMergId;
    private Integer profileId;
    private Integer merchantId;
    private boolean defaultMerchant;
    private String addedDate;
    private String addedBy;
    private String lastUpdate;
    private String updatedBy;
    private String status;
    private String currency;
    private String categoty;

    private Integer profMerRefId;

    public Integer getProfMergId() {
        return profMergId;
    }

    public void setProfMergId(Integer profMergId) {
        this.profMergId = profMergId;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public boolean isDefaultMerchant() {
		return defaultMerchant;
	}

	public void setDefaultMerchant(boolean defaultMerchant) {
		this.defaultMerchant = defaultMerchant;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCategoty() {
		return categoty;
	}

	public void setCategoty(String categoty) {
		this.categoty = categoty;
	}

    public Integer getProfMerRefId() {
        return profMerRefId;
    }

    public void setProfMerRefId(Integer profMerRefId) {
        this.profMerRefId = profMerRefId;
    }
}