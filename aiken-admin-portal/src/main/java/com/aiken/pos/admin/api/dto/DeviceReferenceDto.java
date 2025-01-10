package com.aiken.pos.admin.api.dto;

import java.util.List;

/**
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2023-04-16
 */
public class DeviceReferenceDto {
    private String serialNo;
    private  List<ProfileReferenceDto> profiles;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public List<ProfileReferenceDto> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfileReferenceDto> profiles) {
        this.profiles = profiles;
    }
}
