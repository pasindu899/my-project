/**
 * 
 */
package com.aiken.pos.admin.model;

/**
 * @author Nandana Basnayake
 *
 * @created_On Oct 10, 2022
 */
public class DashboardEventData {
	private String bankName;
	private String bankCode;
	private String eventName;
	private String eventDate;
	private String eventStatus;
	private String merchantName;
	private String merchantAddress;
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantAddress() {
		return merchantAddress;
	}
	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	
}
