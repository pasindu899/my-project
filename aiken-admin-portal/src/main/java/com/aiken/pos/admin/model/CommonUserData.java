/**
 * 
 */
package com.aiken.pos.admin.model;

/**
 * @author Nandana Basnayake
 *
 * @created at Nov 6, 2021 
 */
public class CommonUserData {
	private String newPassword;
	private String renewPassword;
	private String currentPassword;
	private int userId;
	private String email;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRenewPassword() {
		return renewPassword;
	}
	public void setRenewPassword(String renewPassword) {
		this.renewPassword = renewPassword;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
