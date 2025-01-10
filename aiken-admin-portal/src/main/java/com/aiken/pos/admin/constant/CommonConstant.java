package com.aiken.pos.admin.constant;

/**
 * Application Common Constants
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-02-10
 */
public interface CommonConstant {

    String SESSION_VAR_NAME = "DEVICE_FORM";

    String SESSION_DEVICE_BIN_FORM = "DEVICE_BIN_FORM";

    String DEFAULT_VISA_NO_CVM_LIMIT = "7500.01";
    String DEFAULT_CNTACTLS_TRXN_LIMIT = "20000.01";
    String DEFAULT_AUTO_SETTLE_TIME = "23:00:00";
    String DEFAULT_AMEX_IP = "192.168.12.67";
    Integer DEFAULT_AMEX_PORT = 8001;
    String DEFAULT_AMEX_TPDU = "6000070000";
    String DEFAULT_AMEX_NII = "007";

    String TRUE = "TRUE";
    String FALSE = "FALSE";
    String SINGLE = "SINGLE";
    String MULTIPLE = "MULTIPLE";
    String VALID = "VALID";
    String INVALID = "INVALID";
    String BLACKLIST = "BLACKLIST";
    String WHITELIST = "WHITELIST";

    String NETWORK_2G = "2G";
    String NETWORK_3G = "3G";
    String NETWORK_4G = "4G";
    String NETWORK_AUTO = "AUTO";


}
