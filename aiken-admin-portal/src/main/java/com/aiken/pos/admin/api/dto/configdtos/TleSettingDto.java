package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-18
 */
public class TleSettingDto {

    private Boolean masterPosEnable;
    private String masterPosPort;
    private String masterPosIp;
    private Boolean tleEnable;
    private String tleTpdu;
    private String nonTleTpdu;
    private String tleNii;
    private String nonTleNii;
    private Boolean amexTleEnable;
    private String amexTleTpdu;
    private String amexNonTleTpdu;
    private String amexTleNii;
    private String amexNonTleNii;

    public Boolean getMasterPosEnable() {
        return masterPosEnable;
    }

    public void setMasterPosEnable(Boolean masterPosEnable) {
        this.masterPosEnable = masterPosEnable;
    }

    public String getMasterPosPort() {
        return masterPosPort;
    }

    public void setMasterPosPort(String masterPosPort) {
        this.masterPosPort = masterPosPort;
    }

    public String getMasterPosIp() {
        return masterPosIp;
    }

    public void setMasterPosIp(String masterPosIp) {
        this.masterPosIp = masterPosIp;
    }

    public Boolean getTleEnable() {
        return tleEnable;
    }

    public void setTleEnable(Boolean tleEnable) {
        this.tleEnable = tleEnable;
    }

    public String getTleTpdu() {
        return tleTpdu;
    }

    public void setTleTpdu(String tleTpdu) {
        this.tleTpdu = tleTpdu;
    }

    public String getNonTleTpdu() {
        return nonTleTpdu;
    }

    public void setNonTleTpdu(String nonTleTpdu) {
        this.nonTleTpdu = nonTleTpdu;
    }

    public String getTleNii() {
        return tleNii;
    }

    public void setTleNii(String tleNii) {
        this.tleNii = tleNii;
    }

    public String getNonTleNii() {
        return nonTleNii;
    }

    public void setNonTleNii(String nonTleNii) {
        this.nonTleNii = nonTleNii;
    }

    public Boolean getAmexTleEnable() {
        return amexTleEnable;
    }

    public void setAmexTleEnable(Boolean amexTleEnable) {
        this.amexTleEnable = amexTleEnable;
    }

    public String getAmexTleTpdu() {
        return amexTleTpdu;
    }

    public void setAmexTleTpdu(String amexTleTpdu) {
        this.amexTleTpdu = amexTleTpdu;
    }

    public String getAmexNonTleTpdu() {
        return amexNonTleTpdu;
    }

    public void setAmexNonTleTpdu(String amexNonTleTpdu) {
        this.amexNonTleTpdu = amexNonTleTpdu;
    }

    public String getAmexTleNii() {
        return amexTleNii;
    }

    public void setAmexTleNii(String amexTleNii) {
        this.amexTleNii = amexTleNii;
    }

    public String getAmexNonTleNii() {
        return amexNonTleNii;
    }

    public void setAmexNonTleNii(String amexNonTleNii) {
        this.amexNonTleNii = amexNonTleNii;
    }
}
