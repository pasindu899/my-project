package com.aiken.pos.admin.api.dto.configdtos;


import com.aiken.pos.admin.model.ConfigSettingData;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */

public class ConfigurationSettingDto {

    private SaveConfigurationDto saveConfigurationDto;
    private ConfigSettingData configSettingData;
    private Integer configId;
    private String serialNo;
    private String settingData;
    private String addedDate;
    private String addedBy;
    private String updateDate;
    private String updatedBy;

    public SaveConfigurationDto getSaveConfigurationDto() {
        return saveConfigurationDto;
    }

    public void setSaveConfigurationDto(SaveConfigurationDto saveConfigurationDto) {
        this.saveConfigurationDto = saveConfigurationDto;
    }

    public ConfigSettingData getConfigSettingData() {
        return configSettingData;
    }

    public void setConfigSettingData(ConfigSettingData configSettingData) {
        this.configSettingData = configSettingData;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSettingData() {
        return settingData;
    }

    public void setSettingData(String settingData) {
        this.settingData = settingData;
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

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
