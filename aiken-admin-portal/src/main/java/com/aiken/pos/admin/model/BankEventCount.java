/**
 * 
 */
package com.aiken.pos.admin.model;

/**
 * @author Nandana Basnayake
 *
 * @created_On Oct 12, 2022
 */
public class BankEventCount {
	private Integer id;
	private String evetDate;
	private String eventTime;
	private Integer evetCount;
	private String bankCode;
	private String bankName;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEvetDate() {
		return evetDate;
	}
	public void setEvetDate(String evetDate) {
		this.evetDate = evetDate;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	public Integer getEvetCount() {
		return evetCount;
	}
	public void setEvetCount(Integer evetCount) {
		this.evetCount = evetCount;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	

}
