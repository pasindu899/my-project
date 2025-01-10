package com.aiken.pos.admin.model;

/**
 *  * 
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2021-08-10
 */

public class EventType {

	private String code;
	private String name;

	public EventType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventType(String code, String name) {
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