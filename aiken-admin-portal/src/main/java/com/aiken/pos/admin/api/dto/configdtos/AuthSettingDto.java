package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-18
 */
public class AuthSettingDto {

    private String credentialsType;
    private String transferType;
    private String url;
    private String authServerPort;
    private String merchantPortalPort;

    public String getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(String credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthServerPort() {
        return authServerPort;
    }

    public void setAuthServerPort(String authServerPort) {
        this.authServerPort = authServerPort;
    }

    public String getMerchantPortalPort() {
        return merchantPortalPort;
    }

    public void setMerchantPortalPort(String merchantPortalPort) {
        this.merchantPortalPort = merchantPortalPort;
    }
}
