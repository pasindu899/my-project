package com.aiken.pos.admin.api.dto.configdtos;

import com.aiken.pos.admin.api.dto.ProfilesDto;

import java.util.List;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */
public class SaveConfigurationDto {
    private TransactionDto transaction;
    private EcrSettingDto ecrSettings;
    private CommunicationParameterDto communicationParametersSettings;
    private ReceiptSettingDto receipt;
    private SecuritySettingDto security;
    private LoggingSettingDto logging;
    private TrackingSettingDto activityTracking;
    private PortalSettingDto adminPortalConfig;
    private PortalDto qrScanParams;
    private DccSettingDto dccSettings;
    private TleSettingDto tleSettings;
    private AuthSettingDto authServerCredentials;
    private OtherSettingDto otherSettlement;
    private List<MerchantDto> merchants;
    private List<ProfilesDto> profiles;
    private List<ActiveUserDto> activeUsers;

    public TransactionDto getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDto transaction) {
        this.transaction = transaction;
    }


    public EcrSettingDto getEcrSettings() {
        return ecrSettings;
    }

    public void setEcrSettings(EcrSettingDto ecrSettings) {
        this.ecrSettings = ecrSettings;
    }

    public ReceiptSettingDto getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptSettingDto receipt) {
        this.receipt = receipt;
    }

    public SecuritySettingDto getSecurity() {
        return security;
    }

    public void setSecurity(SecuritySettingDto security) {
        this.security = security;
    }

    public LoggingSettingDto getLogging() {
        return logging;
    }

    public void setLogging(LoggingSettingDto logging) {
        this.logging = logging;
    }

    public TrackingSettingDto getActivityTracking() {
        return activityTracking;
    }

    public void setActivityTracking(TrackingSettingDto activityTracking) {
        this.activityTracking = activityTracking;
    }

    public OtherSettingDto getOtherSettlement() {
        return otherSettlement;
    }

    public void setOtherSettlement(OtherSettingDto otherSettlement) {
        this.otherSettlement = otherSettlement;
    }

    public List<MerchantDto> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<MerchantDto> merchants) {
        this.merchants = merchants;
    }

    public List<ProfilesDto> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfilesDto> profiles) {
        this.profiles = profiles;
    }

    public CommunicationParameterDto getCommunicationParametersSettings() {
        return communicationParametersSettings;
    }

    public void setCommunicationParametersSettings(CommunicationParameterDto communicationParametersSettings) {
        this.communicationParametersSettings = communicationParametersSettings;
    }

    public PortalSettingDto getAdminPortalConfig() {
        return adminPortalConfig;
    }

    public void setAdminPortalConfig(PortalSettingDto adminPortalConfig) {
        this.adminPortalConfig = adminPortalConfig;
    }

    public PortalDto getQrScanParams() {
        return qrScanParams;
    }

    public void setQrScanParams(PortalDto qrScanParams) {
        this.qrScanParams = qrScanParams;
    }

    public DccSettingDto getDccSettings() {
        return dccSettings;
    }

    public void setDccSettings(DccSettingDto dccSettings) {
        this.dccSettings = dccSettings;
    }

    public List<ActiveUserDto> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(List<ActiveUserDto> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public TleSettingDto getTleSettings() {
        return tleSettings;
    }

    public void setTleSettings(TleSettingDto tleSettings) {
        this.tleSettings = tleSettings;
    }

    public AuthSettingDto getAuthServerCredentials() {
        return authServerCredentials;
    }

    public void setAuthServerCredentials(AuthSettingDto authServerCredentials) {
        this.authServerCredentials = authServerCredentials;
    }
}
