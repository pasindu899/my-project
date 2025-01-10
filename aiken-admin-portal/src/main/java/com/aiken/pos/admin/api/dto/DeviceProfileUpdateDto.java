package com.aiken.pos.admin.api.dto;

import java.util.List;

/**
 * Device Profile Update Dto
 * Used to update the profiles as per the device configurations
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2023-04-06
 */
public class DeviceProfileUpdateDto {


    private String serialNo;

    private List<ProfileMerchantDto> merchants;

    private List<ProfilesDto> profiles;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public List<ProfileMerchantDto> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<ProfileMerchantDto> merchants) {
        this.merchants = merchants;
    }

    public List<ProfilesDto> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfilesDto> profiles) {
        this.profiles = profiles;
    }
}
