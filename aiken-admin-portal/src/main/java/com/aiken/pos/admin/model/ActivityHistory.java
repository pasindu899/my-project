package com.aiken.pos.admin.model;

/**
 * Activity History Model
 *
 * @author Chathuranga Dissanayake
 * @version 1.0
 * @since 2021-09-22
 */

public class ActivityHistory {
	
	private Long id;
	private String serialNo;
	private String type;
	private String step;
	private String desc;
	private String action;
	private String status;
	private String data1;
	private String data2;
	private String data3;
	private String date;
	private String time;
	private String isData;
	
	public ActivityHistory() {
		super();
	}
	
	public ActivityHistory(String serialNo,String type, String step, String desc, String action, String status, String data1,
			String data2, String data3, String date, String time, String isData) {
		super();
		this.serialNo = serialNo;
		this.type = type;
		this.step = step;
		this.desc = desc;
		this.action = action;
		this.status = status;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.date = date;
		this.time = time;
		this.isData = isData;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIsData() {
		return isData;
	}
	public void setIsData(String isData) {
		this.isData = isData;
	}
}
