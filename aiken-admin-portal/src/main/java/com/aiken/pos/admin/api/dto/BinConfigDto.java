package com.aiken.pos.admin.api.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2022-03-09
 */

public class BinConfigDto {

	
	private String cardType;
	private String transType;
	private String action;
	private String type;
	private String binStartFrom;
	private String binEndFrom;
	private String mid;
	private String tid;
	private Integer merchantId;
	
	


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



	public BinConfigDto() {
		super();
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



	public String getBinEndFrom() {
		return binEndFrom;
	}



	public void setBinEndFrom(String binEndFrom) {
		this.binEndFrom = binEndFrom;
	}

	public Integer getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

}
