package com.aiken.pos.admin.exception;

import java.util.List;

/**
 * Global Error Response Message Template
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */

public class ErrorResponse {
	public ErrorResponse(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
	}
 
	private String message;
 
	private List<String> details;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}
 
}