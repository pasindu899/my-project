package com.aiken.pos.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global Error - Resource Not FoundException
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String error;

	public ResourceNotFoundException(String error) {
		super(error);
		this.error = error;
	}

	@Override
	public String toString() {
		return "ERROR: " + error + "|" + super.toString();
	}
}