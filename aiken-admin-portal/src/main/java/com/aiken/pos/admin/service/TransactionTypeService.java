package com.aiken.pos.admin.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aiken.pos.admin.constant.EventTypes;
import com.aiken.pos.admin.constant.MerchantTypes;
import com.aiken.pos.admin.constant.TransactionTypes;
import com.aiken.pos.admin.model.EventType;
import com.aiken.pos.admin.model.MerchantType;

/**
 *  * 
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2021-08-10
 */

@Service
public class TransactionTypeService {

	public List<EventType> getTransactionTypes() {

		return Arrays.asList(
				new EventType(TransactionTypes.EPP,TransactionTypes.EPP),
				new EventType(TransactionTypes.MANUAL_KEY_IN,TransactionTypes.MANUAL_KEY_IN),
				new EventType(TransactionTypes.QR,TransactionTypes.QR),
				new EventType(TransactionTypes.QR_ECR,TransactionTypes.QR_ECR),
				new EventType(TransactionTypes.SALE,TransactionTypes.SALE),
				new EventType(TransactionTypes.SALE_ECR,TransactionTypes.SALE_ECR),
				new EventType(TransactionTypes.REVERSAL,TransactionTypes.REVERSAL),
				new EventType(TransactionTypes.SETTLEMENT,TransactionTypes.SETTLEMENT),
				new EventType(TransactionTypes.TODAY_ALL,TransactionTypes.TODAY_ALL));
	}
}
