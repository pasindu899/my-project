package com.aiken.pos.admin.model;

/**
 * Database Model
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-03-01
 */

public class MerchantType {

	private String code;
	private String name;

	public MerchantType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MerchantType(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}