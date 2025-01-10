package com.aiken.pos.admin.web.form;

import javax.validation.constraints.NotEmpty;


/**
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2021-07-08
 */

public class EventForm {

    private Integer eventId;
    @NotEmpty(message = "Serial No is required")
    private String serialNo;
    @NotEmpty(message = "Start Date is required")
    private String dateFrom;
    @NotEmpty(message = "End Date is required")
    private String dateTo;
    private String status;
    private String addedBy;
    private String updatedOn;
    private String bankName;
    private String eventType;
    private String reportFromDate;
    private String reportToDate;
    private String merchantName;
    private String type;
    private String merchantTransType;

    private String clearMerchant;
    private String executeType;
    private String onetimePassword;
    private String addedDate;
    private String updatedBy;


    public EventForm() {
        super();
    }

    public EventForm(String serialNo, String dateFrom, String dateTo, String addedBy, String status) {
        super();
        this.serialNo = serialNo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.addedBy = addedBy;
        this.status = status;
    }

    public EventForm(String serialNo, String dateFrom, String dateTo, String addedBy, String status,
                     String updatedOn) {
        super();
        this.serialNo = serialNo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.addedBy = addedBy;
        this.status = status;
        this.updatedOn = updatedOn;
    }


    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getReportFromDate() {
        return reportFromDate;
    }

    public void setReportFromDate(String reportFromDate) {
        this.reportFromDate = reportFromDate;
    }

    public String getReportToDate() {
        return reportToDate;
    }

    public void setReportToDate(String reportToDate) {
        this.reportToDate = reportToDate;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMerchantTransType() {
        return merchantTransType;
    }

    public void setMerchantTransType(String merchantTransType) {
        this.merchantTransType = merchantTransType;
    }

    public String getClearMerchant() {
        return clearMerchant;
    }

    public void setClearMerchant(String clearMerchant) {
        this.clearMerchant = clearMerchant;
    }

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public String getOnetimePassword() {
        return onetimePassword;
    }

    public void setOnetimePassword(String onetimePassword) {
        this.onetimePassword = onetimePassword;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
