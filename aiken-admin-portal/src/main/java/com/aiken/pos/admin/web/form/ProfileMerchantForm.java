package com.aiken.pos.admin.web.form;
/**
 * Profile Merchant Info POJO
 *
 * @author Amesh Madumalka
 * @version 1.0
 * @since 2021-10-09
 */
public class ProfileMerchantForm {
    private Integer profMergId;
    private Integer profileId;
    private Integer merchantId;
    private boolean isDefault;
    private String addedDate;
    private String addedBy;
    private String lastUpdate;
    private String updatedBy;
    private String status;

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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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
}
