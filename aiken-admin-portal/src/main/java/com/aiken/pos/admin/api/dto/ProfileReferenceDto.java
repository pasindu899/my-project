package com.aiken.pos.admin.api.dto;

import java.util.List;

/**
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2023-04-16
 */
public class ProfileReferenceDto {

    private Integer actualProfileId;
    private Integer deviceProfileRefId;
    private List<ReferenceMappingDto> profilesMerchants;

    public Integer getActualProfileId() {
        return actualProfileId;
    }

    public void setActualProfileId(Integer actualProfileId) {
        this.actualProfileId = actualProfileId;
    }

    public Integer getDeviceProfileRefId() {
        return deviceProfileRefId;
    }

    public void setDeviceProfileRefId(Integer deviceProfileRefId) {
        this.deviceProfileRefId = deviceProfileRefId;
    }

    public List<ReferenceMappingDto> getProfilesMerchants() {
        return profilesMerchants;
    }

    public void setProfilesMerchants(List<ReferenceMappingDto> profilesMerchants) {
        this.profilesMerchants = profilesMerchants;
    }
}
