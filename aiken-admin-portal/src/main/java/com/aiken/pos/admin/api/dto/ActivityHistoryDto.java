package com.aiken.pos.admin.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Activity History Dto
 *
 * @author Chathuranga Dissanayake
 * @version 1.0
 * @since 2021-09-22
 */

public class ActivityHistoryDto {

	@JsonIgnore
	private Long id;
	private String serialNo;
	private String typ;
	private String step;
	private String desc;
	private String actn;
	private String status;
	@JsonIgnore
	private String data1;
	@JsonIgnore
	private String data2;
	@JsonIgnore
	private String data3;
	private String date;
	private String time;
	@JsonIgnore
	private String isData;
	
	public ActivityHistoryDto() {
		super();
		
	}
	public ActivityHistoryDto(String serialNo, String typ, String step, String desc, String actn, String status, String data1,
			String data2, String data3, String date, String time, String isData) {
		super();
		this.serialNo = serialNo;
		this.typ = typ;
		this.step = step;
		this.desc = desc;
		this.actn = actn;
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
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
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
	public String getActn() {
		return actn;
	}
	public void setActn(String actn) {
		this.actn = actn;
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
