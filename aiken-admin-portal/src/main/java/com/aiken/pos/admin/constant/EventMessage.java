package com.aiken.pos.admin.constant;

/** 
 * 
 * Application Event Messages
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */
public interface EventMessage {

	String SUCCESSFULLY_SAVED = " Successfully Saved ";
	String SUCCESSFULLY_UPDATED = " Successfully Updated ";
	String SUCCESSFULLY_DELETED = " Successfully Deleted ";  
	String DEVICE_REGISTERD_SUCCESSFULLY = "New device registered successfuly";
	String PASSWORD_CHANGED_SUCCESSFULLY = "Successfuly changed the Password";
	String PASSWORD_CHANGED_PLEASE_LOGIN = "Successfuly changed the Password. Please login with new password";
	String USER_CREATED_SUCCESSFULLY = "New user created successfuly";
	
	String ERROR_MSG_BAD_REQUEST = "ERROR : 400, Bad Request ";
	String ERROR_MSG_UNAUTHORIZED = "ERROR : 401, Authentication ";
	String ERROR_MSG_ACCESS_DENIED = "ERROR : 403, Access Denied ";
	String ERROR_MSG_NOT_FOUND = "ERROR : 404, Resource Not Found ";
	String ERROR_MSG_INTERNAL_SERVER_ERROR_1 = "ERROR : 500, Operation Fail, Constraint Violation ";
	String ERROR_MSG_INTERNAL_SERVER_ERROR_2 = "ERROR : 500, Operation Fail, Duplicate Key ";
	String ERROR_MSG_INTERNAL_SERVER_ERROR_3 = "ERROR : 500, Operation Fail, System Error ";
	
	String ERROR_MSG_FAIL = "Operation Fail ";

	String ERROR_MSG_RECORD_NOT_FOUND = "record not found with given reference id ";
	String ERROR_MSG_RECORD_ALREADY_EXISTS = "Device already exists ";
	String ERROR_UPDATE_EVENT = "Error Occurred.Update not succeeded";
	String INVALID_CREDENTIALS= "Invalid Credentials ";
	
	String TODAY_EVENTS = "Today Events";
	String ALL_EVENTS = "All Events";
	String ALL_USERS = "All Users";
	String SEARCH_RESULTS = "Search Results";
	String DEVICE_UPDATED_BY_TODAY = "Today Updated Devices";
	String ALL_DEVICES = "All Devices";
	String SEARCH_RESULT_OF_DATES = "Search Result of Dates";
	String SEARCH_RESULT_OF_TEXT = "Search Result of Text";
	String SEARCH_RESULT_OF_TID_MID = "Search Result in TID/MID";
	String ERROR_MSG_EVENT_ALREADY_EXISTS = "This event already exist";
	String ERROR_OCCURED_TRY_AGAIN = "Error Occurred. Please try again";
	String ERROR_SERIAL_NO_NOT_FOUND = "Serial Number not found";
	String ERROR_MERCHAT_DETAILS_NOT_FOUND = "Merchant details are required for adding new Devices";
	String ERROR_AMEX_NOT_ENABLED = "AMEX Configuration is not enabled. Please Enable AMEX to add AMEX Merchant.";
	String ERROR_DEVICE_WITHOUT_MERCHANT="Can not register Device without Merchant";
	String ERROR_OCCURED = "An Error Occurred";
	String ERROR_REPORT_DATES_NOT_SELECTED = "Please select the date range for the required report";
	String ERROR_PROFILE_COMMON_PARAMETERS = "Profile Common Parameters are required";
	String ERROR_NO_AMEX_DEFAULT_MERCHANT = "Please select default merchant for AMEX";
	String ERROR_MULTIPLE_AMEX_DEFAULT_MERCHANTS = "Please select only one merchant as default merchant for AMEX";
	String ERROR_NO_SALE_DEFAULT_MERCHANT = "Please select default merchant for SALE";
	String ERROR_MULTIPLE_SALE_DEFAULT_MERCHANTS = "Please select only one merchant as default merchant for SALE";
	String ERROR_NO_SALE_MERCHANT = "You must select at least one SALE merchant";
	String ERROR_DCC_MERCHANT_AS_DEFAULT_MERCHANT = "You Can not set DCC merchant as the profile default merchant";
	String ERROR_MULTIPLE_MERCHANTS_FOR_SAME_CURRENCY = "You can not create profile with more than two merchants in same currency";
	String ERROR_MULTIPLE_MERCHANTS_FOR_LKR_CURRENCY_IN_COMMERCIAL_BANK = "You can not create a profile with more than three merchants in Lkr in a commercial bank.";
	String ERROR_NON_DCC_MERCHANT_NOT_FOUND = "NON-DCC Merchant Not Found";

	String CLEAR_FOR_SELECTED_MERCHANT = "CLEAR_FOR_SELECTED_MERCHANT";

	String CLEAR_FOR_ALL_MERCHANTS = "CLEAR_FOR_ALL_MERCHANTS";

	String  Should_Add_Four_LKR_MERCHANT =" You Should Add four LKR Merchant - one Dcc(Foreign), one NonDcc(Foreign), one ON-US(Same Bank) and one OFF-US(Other Bank) ";
	String  Should_Add_ONE_LKR_DCC_MERCHANT =" You can not create profile with more than one Dcc(Foreign) LKR Merchant ";
	String  Should_Add_ONE_LKR_NONDCC_MERCHANT =" You can not create profile with more than one NonDcc(Foreign) LKR Merchant ";
	String  Should_Add_ONE_LKR_LOCAL_ONUS_MERCHANT =" You can not create profile with more than one Local(OnUs) LKR Merchant ";
	String  Should_Add_ONE_LKR_LOCAL_OFFUS_MERCHANT =" You can not create profile with more than one Local(OffUs) LKR Merchant ";
	String  Should_Add_ONE_LKR_IPHONE_IMEI_MERCHANT =" Should add only one IPhone iMEI LKR Merchant ";
	String  Should_Add_ONE_DCC_LKR_IPHONE_IMEI_MERCHANT =" Should add only one Dcc LKR Merchant with IPhone iMEI LKR Merchant ";
	String  Should_Add_ONE_NON_DCC_LKR_IPHONE_IMEI_MERCHANT =" Should add only one Non-Dcc LKR Merchant with IPhone iMEI LKR Merchant ";
	
	
	
	

	
	
	 

}
