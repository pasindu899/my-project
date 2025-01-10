/**
 * 
 */
package com.aiken.pos.admin.api.dto;

/**
 * @author Nandana Basnayake
 *
 * @created_On Sep 14, 2022
 */
public class CustomAuthServerConfigDto {
	
    private boolean merchantPortal;
    private boolean resendVoid;
    private boolean clientCredentials;
	private boolean printReceipt;

	public boolean isPrintReceipt() {
		return printReceipt;
	}

	public void setPrintReceipt(boolean printReceipt) {
		this.printReceipt = printReceipt;
	}

	public boolean isMerchantPortal() {
		return merchantPortal;
	}
	public void setMerchantPortal(boolean merchantPortal) {
		this.merchantPortal = merchantPortal;
	}
	public boolean isResendVoid() {
		return resendVoid;
	}
	public void setResendVoid(boolean resendVoid) {
		this.resendVoid = resendVoid;
	}
	public boolean isClientCredentials() {
		return clientCredentials;
	}
	public void setClientCredentials(boolean clientCredentials) {
		this.clientCredentials = clientCredentials;
	}
	
	

}
