/**
 * 
 */
package com.aiken.pos.admin.web.form;

import javax.validation.constraints.NotNull;

/**
 * @author Nandana Basnayake
 *
 * @created_at Nov 11, 2021
 */

public class ForgotPasswordForm {
	
	@NotNull
    private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
