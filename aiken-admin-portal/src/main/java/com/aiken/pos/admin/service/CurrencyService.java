package com.aiken.pos.admin.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aiken.pos.admin.constant.MerchantTypes;
import com.aiken.pos.admin.model.MerchantType;

/**
 * Currency Service
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-03-01
 */

@Service
public class CurrencyService {

	public List<String> getCurrencies() {

		return Arrays.asList(
				"LKR",
				"USD",
				"EUR",
				"GBP",
				"JPY",
				"AUD",
				"CAD",
				"CHF",
				"CNY",
				"SGD"
				);
	}
}
