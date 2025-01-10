package com.aiken.pos.admin.model;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2022-03-07
 */

public class BinConfig {

	private Integer formId;
	@NotEmpty(message = "Cart Type is required")
	private String cardType;
	@NotEmpty(message = "Transaction type is required")
	private String transType;
	@NotEmpty(message = "Action is required")
	private String action;
	@NotEmpty(message = "Type is required")
	private String type;
	private String binStartFrom;
	private String binEnd;
	private String mid;
	private String tid;
	@JsonIgnore(value = true)
	private String formAction;
	private Integer merchantId;
	private Integer deviceId;
	
	public BinConfig(Integer formId,String cardType,String transType,String action,String type,String binStartFrom,String binEnd,String mid,String tid,Integer deviceId) {
		super();
		this.formId = formId;
		this.cardType = cardType;
		this.transType = transType;
		this.action = action;
		this.type = type;
		this.binStartFrom = binStartFrom;
		this.binEnd = binEnd;
		this.mid = mid;
		this.tid = tid;
		this.deviceId = deviceId;
	}


	public String getMid() {
		return mid;
	}



	public void setMid(String mid) {
		this.mid = mid;
	}



	public String getTid() {
		return tid;
	}



	public void setTid(String tid) {
		this.tid = tid;
	}



	public BinConfig() {
		super();
	}



	public Integer getFormId() {
		return formId;
	}



	public void setFormId(Integer formId) {
		this.formId = formId;
	}



	public String getCardType() {
		return cardType;
	}



	public void setCardType(String cardType) {
		this.cardType = cardType;
	}



	public String getTransType() {
		return transType;
	}



	public void setTransType(String transType) {
		this.transType = transType;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getBinStartFrom() {
		return binStartFrom;
	}



	public void setBinStartFrom(String binStartFrom) {
		this.binStartFrom = binStartFrom;
	}



	public String getBinEnd() {
		return binEnd;
	}



	public void setBinEnd(String binEndFrom) {
		this.binEnd = binEndFrom;
	}



	public String getFormAction() {
		return formAction;
	}



	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}



	public Integer getMerchantId() {
		return merchantId;
	}



	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}


//	public String getBinEnd() {
//		return binEnd;
//	}
//
//
//	public void setBinEnd(String binEnd) {
//		this.binEnd = binEnd;
//	}


	public Integer getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	
	

}
