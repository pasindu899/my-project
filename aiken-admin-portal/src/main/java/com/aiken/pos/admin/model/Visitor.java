/**
 * 
 */
package com.aiken.pos.admin.model;

import java.time.LocalDateTime;

/**
 * @author Nandana Basnayake
 *
 * @created_On Nov 29, 2022
 */

public class Visitor {

	private int id;
	private String user;
	private String ip;
	private String method;
	private String url;
	private String page;
	private String queryString;
	private String refererPage;
	private String userAgent;
	private LocalDateTime loggedTime;
	private boolean uniqueVisit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getRefererPage() {
		return refererPage;
	}
	public void setRefererPage(String refererPage) {
		this.refererPage = refererPage;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public LocalDateTime getLoggedTime() {
		return loggedTime;
	}
	public void setLoggedTime(LocalDateTime loggedTime) {
		this.loggedTime = loggedTime;
	}
	public boolean isUniqueVisit() {
		return uniqueVisit;
	}
	public void setUniqueVisit(boolean uniqueVisit) {
		this.uniqueVisit = uniqueVisit;
	}

}
