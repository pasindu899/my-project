package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */
public class SecuritySettingDto {
    private Integer baseMainKeyIndexPin;
    private String keyLoadPassword;
    private Boolean debugMode;
    private Boolean pushNotification;

    public Integer getBaseMainKeyIndexPin() {
        return baseMainKeyIndexPin;
    }

    public void setBaseMainKeyIndexPin(Integer baseMainKeyIndexPin) {
        this.baseMainKeyIndexPin = baseMainKeyIndexPin;
    }

    public String getKeyLoadPassword() {
        return keyLoadPassword;
    }

    public void setKeyLoadPassword(String keyLoadPassword) {
        this.keyLoadPassword = keyLoadPassword;
    }

    public Boolean getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(Boolean debugMode) {
        this.debugMode = debugMode;
    }

    public Boolean getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(Boolean pushNotification) {
        this.pushNotification = pushNotification;
    }
}
