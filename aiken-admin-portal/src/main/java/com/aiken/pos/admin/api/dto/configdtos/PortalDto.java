package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-18
 */
public class PortalDto {
    private String scanUrl;
    private String viewUrl;
    private String refundUrl;
    private String qrMaxAmount;
    private String qrMinAmount;

    public String getScanUrl() {
        return scanUrl;
    }

    public void setScanUrl(String scanUrl) {
        this.scanUrl = scanUrl;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getRefundUrl() {
        return refundUrl;
    }

    public void setRefundUrl(String refundUrl) {
        this.refundUrl = refundUrl;
    }

    public String getQrMaxAmount() {
        return qrMaxAmount;
    }

    public void setQrMaxAmount(String qrMaxAmount) {
        this.qrMaxAmount = qrMaxAmount;
    }

    public String getQrMinAmount() {
        return qrMinAmount;
    }

    public void setQrMinAmount(String qrMinAmount) {
        this.qrMinAmount = qrMinAmount;
    }
}
