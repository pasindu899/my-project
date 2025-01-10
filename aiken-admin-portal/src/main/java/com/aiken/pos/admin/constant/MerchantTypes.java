package com.aiken.pos.admin.constant;

/**
 * Application Action Types
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-02-10
 */

public interface MerchantTypes {
	String SALE = "SALE";
	String QR = "QR";
	String EPP = "EPP";
	String AMEX = "AMEX";
	String EPP_AMEX = "EPP_AMEX";

	String DEFAULT_SALE = "SALE";
	String SALE_VISA = "SALE_VISA_ONLY";
	String SALE_MASTER = "SALE_MASTER_ONLY";
	String SALE_JCB_CUP = "SALE_JCB_CUP";
	String SALE_OTHER = "SALE_OTHER";
}
