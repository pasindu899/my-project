/**
 * 
 */
package com.aiken.pos.admin.model;
import java.util.List;

/**
 * @author Nandana Basnayake
 *
 * @created_On Oct 10, 2022
 */
public class Dashboard {
	
	private Integer allUserCount;
	private Integer activeUserCount;
	private Integer effectDays;
	private String eventStatus;
	private String status;
	private String eventDate;
	private Integer eventDailyCount;
	private Integer oneClickDailyCount;
	private Integer deviceCount;
	
	private List<DashboardEventData> dashboardData;
	
	private List<BankEventCount> bankOneClickCount;
	private List<BankEventCount> oneClickCount;
	private List<BankEventCount> eventCount;
	private List<BankEventCount> bankEventCount;
	private List<BankEventCount> newRegDeviceCount;
	
	
	public Integer getAllUserCount() {
		return allUserCount;
	}
	public void setAllUserCount(Integer allUserCount) {
		this.allUserCount = allUserCount;
	}
	public Integer getActiveUserCount() {
		return activeUserCount;
	}
	public void setActiveUserCount(Integer activeUserCount) {
		this.activeUserCount = activeUserCount;
	}
	public Integer getEffectDays() {
		return effectDays;
	}
	public void setEffectDays(Integer effectDays) {
		this.effectDays = effectDays;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<DashboardEventData> getDashboardData() {
		return dashboardData;
	}
	public void setDashboardData(List<DashboardEventData> dashboardData) {
		this.dashboardData = dashboardData;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public List<BankEventCount> getBankOneClickCount() {
		return bankOneClickCount;
	}
	public void setBankOneClickCount(List<BankEventCount> bankOneClickCount) {
		this.bankOneClickCount = bankOneClickCount;
	}
	public List<BankEventCount> getBankEventCount() {
		return bankEventCount;
	}
	public void setBankEventCount(List<BankEventCount> bankEventCount) {
		this.bankEventCount = bankEventCount;
	}
	public List<BankEventCount> getNewRegDeviceCount() {
		return newRegDeviceCount;
	}
	public void setNewRegDeviceCount(List<BankEventCount> newRegDeviceCount) {
		this.newRegDeviceCount = newRegDeviceCount;
	}
	public List<BankEventCount> getOneClickCount() {
		return oneClickCount;
	}
	public void setOneClickCount(List<BankEventCount> oneClickCount) {
		this.oneClickCount = oneClickCount;
	}
	public List<BankEventCount> getEventCount() {
		return eventCount;
	}
	public void setEventCount(List<BankEventCount> eventCount) {
		this.eventCount = eventCount;
	}
	public Integer getEventDailyCount() {
		return eventDailyCount;
	}
	public void setEventDailyCount(Integer eventDailyCount) {
		this.eventDailyCount = eventDailyCount;
	}
	public Integer getOneClickDailyCount() {
		return oneClickDailyCount;
	}
	public void setOneClickDailyCount(Integer oneClickDailyCount) {
		this.oneClickDailyCount = oneClickDailyCount;
	}
	public Integer getDeviceCount() {
		return deviceCount;
	}
	public void setDeviceCount(Integer deviceCount) {
		this.deviceCount = deviceCount;
	}
	

}
