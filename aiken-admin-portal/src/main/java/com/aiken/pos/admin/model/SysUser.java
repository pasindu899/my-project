package com.aiken.pos.admin.model;

import java.util.Date;

/**
 * Database Model
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-27
 */

public class SysUser {

	private Integer userId;
	private Integer empId;
	private String username;
	private String password;
	private String userRole;
	private boolean active;
	private String firstName;
	private String lastName;
    private String addedDate;
    private String addedBy;
    private String lastUpdate;
    private String updatedBy;
    private boolean newUser;
    private int failedAttempt;
    private String lockTime;
    private boolean accountNonLocked;
    private boolean reqReset;
    private String reqResetOn;
    private String userGroup;
	private String email;
    
    //New entry
    private String lastLoginTime;
    private String lastLogoutTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
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

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public int getFailedAttempt() {
		return failedAttempt;
	}

	public void setFailedAttempt(int failedAttempt) {
		this.failedAttempt = failedAttempt;
	}

	public String getLockTime() {
		return lockTime;
	}

	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	
	public boolean isReqReset() {
		return reqReset;
	}

	public void setReqReset(boolean reqReset) {
		this.reqReset = reqReset;
	}

	public String getReqResetOn() {
		return reqResetOn;
	}

	public void setReqResetOn(String reqResetOn) {
		this.reqResetOn = reqResetOn;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getLoginTime() {
		return lastLoginTime;
	}

	public void setLoginTime(String loginTime) {
		this.lastLoginTime = loginTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(String lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}