package com.aiken.pos.admin.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aiken.pos.admin.constant.MerchantTypes;
import com.aiken.pos.admin.model.MerchantType;

/**
 * Merchant Service
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-03-01
 */

@Service
public class MerchantTypeService {

	public List<MerchantType> getMerchantTypes() {

		return Arrays.asList(
				new MerchantType(MerchantTypes.SALE, "Sale"),
/*				new MerchantType(MerchantTypes.SALE_VISA, "Sale - VISA Only"),
				new MerchantType(MerchantTypes.SALE_MASTER, "Sale - MASTER Only"),
				new MerchantType(MerchantTypes.SALE_JCB_CUP, "Sale - JCB/CUP"),
				new MerchantType(MerchantTypes.SALE_OTHER, "Sale - Other"),*/
				new MerchantType(MerchantTypes.EPP, "Easy Payment"),
				new MerchantType(MerchantTypes.QR, "QR"),
				new MerchantType(MerchantTypes.AMEX, "Amex"),
				new MerchantType(MerchantTypes.EPP_AMEX, "EPP Amex"));
	}

	public List<MerchantType> getSaleMerchantTypes() {

		return Arrays.asList(
			new MerchantType(MerchantTypes.DEFAULT_SALE, "Default Sale"),
			new MerchantType(MerchantTypes.SALE_VISA, "Visa Only Sale"),
			new MerchantType(MerchantTypes.SALE_MASTER, "Master Only Sale"),
			new MerchantType(MerchantTypes.SALE_JCB_CUP, "CUP/JCB Only Sale")
			);
	}
}
