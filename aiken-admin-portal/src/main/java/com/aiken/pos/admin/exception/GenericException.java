package com.aiken.pos.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Generic Exception
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends Exception {

	private static final long serialVersionUID = 1L;

	private String error;

	public GenericException(String error) {
		super(error);
		this.error = error;
	}

	@Override
	public String toString() {
		return "System Error: " + error;
	}

}
