package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-18
 */
public class CommunicationParameterDto {

    private Boolean enableAmex;
    private String commTimeout;
    private String commTpdu;
    private String amexPort;
    private String amexNii;
    private String dialogIp;
    private String mobitelIp;
    private String amexDialogIp;
    private String amexMobitelIp;
    private Boolean jcbEnable;
    private String jcbDialogIp;
    private String jcbMobitelIp;
    private String dialogApn;
    private String mobitelApn;
    private String commPort;
    private String jcbPort;

    public Boolean getEnableAmex() {
        return enableAmex;
    }

    public void setEnableAmex(Boolean enableAmex) {
        this.enableAmex = enableAmex;
    }

    public String getCommTimeout() {
        return commTimeout;
    }

    public void setCommTimeout(String commTimeout) {
        this.commTimeout = commTimeout;
    }

    public String getCommTpdu() {
        return commTpdu;
    }

    public void setCommTpdu(String commTpdu) {
        this.commTpdu = commTpdu;
    }

    public String getAmexPort() {
        return amexPort;
    }

    public void setAmexPort(String amexPort) {
        this.amexPort = amexPort;
    }

    public String getAmexNii() {
        return amexNii;
    }

    public void setAmexNii(String amexNii) {
        this.amexNii = amexNii;
    }

    public String getDialogIp() {
        return dialogIp;
    }

    public void setDialogIp(String dialogIp) {
        this.dialogIp = dialogIp;
    }

    public String getMobitelIp() {
        return mobitelIp;
    }

    public void setMobitelIp(String mobitelIp) {
        this.mobitelIp = mobitelIp;
    }

    public String getAmexDialogIp() {
        return amexDialogIp;
    }

    public void setAmexDialogIp(String amexDialogIp) {
        this.amexDialogIp = amexDialogIp;
    }

    public String getAmexMobitelIp() {
        return amexMobitelIp;
    }

    public void setAmexMobitelIp(String amexMobitelIp) {
        this.amexMobitelIp = amexMobitelIp;
    }

    public Boolean getJcbEnable() {
        return jcbEnable;
    }

    public void setJcbEnable(Boolean jcbEnable) {
        this.jcbEnable = jcbEnable;
    }

    public String getJcbDialogIp() {
        return jcbDialogIp;
    }

    public void setJcbDialogIp(String jcbDialogIp) {
        this.jcbDialogIp = jcbDialogIp;
    }

    public String getJcbMobitelIp() {
        return jcbMobitelIp;
    }

    public void setJcbMobitelIp(String jcbMobitelIp) {
        this.jcbMobitelIp = jcbMobitelIp;
    }

    public String getDialogApn() {
        return dialogApn;
    }

    public void setDialogApn(String dialogApn) {
        this.dialogApn = dialogApn;
    }

    public String getMobitelApn() {
        return mobitelApn;
    }

    public void setMobitelApn(String mobitelApn) {
        this.mobitelApn = mobitelApn;
    }

    public String getCommPort() {
        return commPort;
    }

    public void setCommPort(String commPort) {
        this.commPort = commPort;
    }

    public String getJcbPort() {
        return jcbPort;
    }

    public void setJcbPort(String jcbPort) {
        this.jcbPort = jcbPort;
    }
}
