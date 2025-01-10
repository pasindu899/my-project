package com.aiken.pos.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Database Model
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-27
 */
public class Event {

    private Integer eventId;
    private String serialNo;
    private String eventType;
    private String fromDate;
    private String toDate;
    private String addedBy;
    private String addedDate;
    private String lastUpdate;
    private String updatedBy;
    private String status;
    @JsonIgnore(value = true)
    private String action;
    private String reportFromDate;
    private String reportToDate;
    private String merchantName;
    private String type;
    private String eventDesc;
    private String merchantTransType;

    private String clearMerchant;
    private String executeType;
    private String onetimePassword;


    public Event() {

    }

    public Event(Integer eventId, String status) {
        this.eventId = eventId;
        this.status = status;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
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
}
