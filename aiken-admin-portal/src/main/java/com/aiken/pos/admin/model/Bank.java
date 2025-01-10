package com.aiken.pos.admin.model;

/**
 * Database Model
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-27
 */

public class Bank {

	private Integer bankId;
	private String bankName;
	private String bankCode;
	private String description;

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}