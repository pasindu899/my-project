package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */
public class PortalSettingDto {

    private String oneClickSetupIp;
    private String oneClickSetupPort;
    private String oneClickSetupProtocol;
    private String oneClickSetupAutoUpdate;

    @Override
    public String toString() {
        return "PortalSettingDto{" +
                ", oneClickSetupIp='" + oneClickSetupIp + '\'' +
                ", oneClickSetupPort='" + oneClickSetupPort + '\'' +
                ", oneClickSetupProtocol='" + oneClickSetupProtocol + '\'' +
                ", oneClickSetupAutoUpdate='" + oneClickSetupAutoUpdate + '\'' +
                '}';
    }

    public String getOneClickSetupIp() {
        return oneClickSetupIp;
    }

    public void setOneClickSetupIp(String oneClickSetupIp) {
        this.oneClickSetupIp = oneClickSetupIp;
    }

    public String getOneClickSetupPort() {
        return oneClickSetupPort;
    }

    public void setOneClickSetupPort(String oneClickSetupPort) {
        this.oneClickSetupPort = oneClickSetupPort;
    }

    public String getOneClickSetupProtocol() {
        return oneClickSetupProtocol;
    }

    public void setOneClickSetupProtocol(String oneClickSetupProtocol) {
        this.oneClickSetupProtocol = oneClickSetupProtocol;
    }

    public String getOneClickSetupAutoUpdate() {
        return oneClickSetupAutoUpdate;
    }

    public void setOneClickSetupAutoUpdate(String oneClickSetupAutoUpdate) {
        this.oneClickSetupAutoUpdate = oneClickSetupAutoUpdate;
    }
}
