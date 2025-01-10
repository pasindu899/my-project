package com.aiken.pos.admin.api.dto;

/**
 * Profile Merchant Dto
 *
 * @author Amesh Madumalka
 * @version 1.0
 * @since 2021-10-02
 */
public class ProfileMerchantMappingDto {
    private Integer pmId;
    private Integer pId;
    private Integer mId;
    private boolean pmDefault;
    private String status;

    public Integer getPmId() {
        return pmId;
    }

    public void setPmId(Integer pmId) {
        this.pmId = pmId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public boolean isPmDefault() {
        return pmDefault;
    }

    public void setPmDefault(boolean pmDefault) {
        this.pmDefault = pmDefault;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}