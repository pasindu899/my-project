package com.aiken.pos.admin.model;

/**
 * Token Model
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-02-10
 */
public class Token {

	private String serialNo;
	private String token;
	private String lastUpdate;
	private String updatedBy;

	@Override
	public String toString() {
		return "[" + "serialNo:" + serialNo + "|" + "lastUpdate:" + lastUpdate + "]";
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
