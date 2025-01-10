package com.aiken.pos.admin.web.form;

/**
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2021-07-08
 */

public class CommonDataForm {

    private String searchData;
    private String tid;
    private String mid;
    private String serialNo;
    private String callDate;
    private String startDate;
    private String endDate;


    public CommonDataForm() {
        super();
    }

    public CommonDataForm(String searchData, String tid, String serialNo, String callDate, String startDate, String endDate) {
        super();
        this.searchData = searchData;
        this.tid = tid;
        this.serialNo = serialNo;
        this.callDate = callDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSearchData() {
        return searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }


}
