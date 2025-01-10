package com.aiken.pos.admin.constant;

/**
 * Application Error Codes
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */
public interface ErrorCode {

    // system error codes
    String SYSTEM_ERROR = "SYSTEM_ERROR";
    String SYSTEM_MESSAGE_TAG = "errorMessage";

    String ERROR_CODE_NOT_FOUND = "NOT_FOUND";
    String ERROR_CODE_FORBIDDEN = "FORBIDDEN";
    String ERROR_CODE_UNAUTHORIZED = "UNAUTHORIZED";
    String ERROR_CODE_BAD_REQUEST = "BAD_REQUEST";
    String ERROR_CODE_INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    String TABLE_DATA_NOT_FOUND = "Records not found";
    String TABLE_DATA_NOT_FOUND_FOR_TODAY_EVENTS = "There are no records for today events";
    String TABLE_DATA_NOT_FOUND_FOR_TODAY_UPDATED_DEVICES = "There are no records for today updated devices";
    String TERMINAL_ID_EXIST = "This Terminal ID is already available in the merchant list";
    String PROFILE_NAME_ALREADY_EXIST = "This profile name already exist in the profile list";
    String NO_ERROR = "NO_ERROR";
    String PROFILE_MERCHANT_ERROR ="Please goto Modify Profile change the merchant configurations again if needed";
	String ERROR_REMOVE_MERCHANT = "This merchant is refering as Default Merchant in the device profile(s). Please select another merchant as Default merchant before deleting this merchant";
	String ERROR_INVALID_BIN_LENGTH = "Length of the BIN should have 6 or 8 digits";
	String ERROR_INVALID_BIN_FORMAT = "BIN should have numbers only. (Ex: 402345 / 60344523)";
	String ERROR_INVALID_BIN_ENTRY ="Rule Conflict with existing rules. Please check again.";
	String ERROR_BIN_RULE_AVAILABLE = "rule is already avaialble for these BINs. Please check the existing rules";
	String ERROR_INVALID_BIN_RANGE = "End BIN should be greater than Start BIN";

    String ERROR_MERCHANT_ALREADY_ADD_BY_PROFILE = "This merchant has already been added to the profile. If you want to modify this merchant, please remove it from the profile";
	
}
