package com.aiken.pos.admin.constant;

/**
 * Application Endpoints
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-21
 */
public interface Endpoint {

    // app root level constants
    String APP_ROOT = "/";
    String ROOT_NAME = "admin-portal";
    String LOGIN = "/login";
    String LOGOUT = "/logout";
    String NEW_USER_LOGIN = "/new-login";


    String URL_ROOT = APP_ROOT + ROOT_NAME;

    String REDIRECT_NAME = "redirect:";
    String ROOT_REDIRECT = REDIRECT_NAME + URL_ROOT;

    // app main module constants
    String URL_API_V1 = URL_ROOT + "/api/v1";
    String URL_DEVICES = URL_API_V1 + "/devices";
    String URL_EVENTS = URL_API_V1 + "/events";
    String URL_CHECK_UPDATE = URL_API_V1 + "/check-update";
    String URL_ACTIVITY = URL_API_V1 + "/activity";
    String URL_CONFIG_SETTING = URL_API_V1 + "/config-settings";
    String URL_ONE_CLICK = "/profiles/";
    String URL_RESET_PASSWORD = "/pw-reset";
    String URL_FORGOT_PASSWORD = "/forgot-password";

    String URL_DEVICE_PROFILE = "/device-profile";
    String URL_TEST = "/test/";

    String URL_SYNC = "/sync";

    // webapp constants
    String URL_USER_INFO = URL_ROOT + "/user-info";
    String URL_VIEW_DEVICES = URL_ROOT + "/view-all-devices";
    String URL_VIEW_DEVICES_UPDATED_BY_TODAY = URL_ROOT + "/view-devices";
    String URL_REG_DEVICE = URL_ROOT + "/register-device";
    String URL_REG_NEW_DEVICE = URL_ROOT + "/register-new-device";
    String URL_MODIFY_DEVICE = URL_ROOT + "/modify-device";
    String URL_PUSH_DEVICE = URL_ROOT + "/push-device";
    String URL_SAVE_DEVICE = URL_ROOT + "/save-device";
    String URL_EDIT_DEVICE = URL_ROOT + "/edit-device";
    String URL_DELETE_DEVICE = URL_ROOT + "/delete-device";
    String URL_VIEW_DEVICE = URL_ROOT + "/view-device";
    String URL_SEARCH_DEVICE = URL_ROOT + "/search-device";
    String URL_ADD_NEW_MERCHANT = URL_ROOT + "/new-merchant";
    String URL_NEW_MERCHANT = URL_ROOT + "/set-new-merchant";
    String URL_ADD_ANOTHER_MERCHANT = URL_ROOT + "/add-another-merchant";
    String URL_ADD_TO_CART = URL_ROOT + "/add-to-cart";
    String URL_NEW_ADD_TO_CART = URL_ROOT + "/new-add-to-cart";
    String URL_CURRENT_ADD_TO_CART = URL_ROOT + "/current-add-to-cart";
    String URL_NEW_MODIFY_TO_CART = URL_ROOT + "/new-modify-to-cart";
    String URL_CURRENT_MODIFY_TO_CART = URL_ROOT + "/current-modify-to-cart";
    String URL_MODIFY_TO_CART = URL_ROOT + "/modify-to-cart";
    String URL_DELETE_NEW_MERCHANT = URL_ROOT + "/delete-new-merchant";
    String URL_DELETE_CURRENT_MERCHANT = URL_ROOT + "/delete-current-merchant";
    String URL_MODIFY_NEW_MERCHANT = URL_ROOT + "/modify-new-merchant";
    String URL_MODIFY_CURRENT_MERCHANT = URL_ROOT + "/modify-current-merchant";
    String URL_VIEW_MERCHANT = URL_ROOT + "/merchant-details";
    String URL_VIEW_DEVICES_WITH_TID = URL_ROOT + "/tid-devices-details";
    String URL_VIEW_REPORTS = URL_ROOT + "/view-reports";
    String URL_VIEW_PASSWORD = URL_ROOT + "/view-password";

    String URL_VIEW_REPORT_FOR_SALE_MID_TID = URL_VIEW_REPORTS + "/banks/saleMIDTID";

    String URL_VIEW_REPORTS_BY_BANK_CODE_REPORT2 = URL_VIEW_REPORTS + "/banks/amexMIDTID";

    String URL_VIEW_REPORTS_BY_BANK_CODE_REPORT3 = URL_VIEW_REPORTS + "/banks/report3";

    String URL_VIEW_REPORTS_BY_BANK_CODE_REPORT4 = URL_VIEW_REPORTS + "/banks/devices";

    String URL_VIEW_REPORTS_BY_BANK_CODE_REPORT5 = URL_VIEW_REPORTS + "/banks/comParams";

    String URL_VIEW_REPORT_FOR_ALL_MID_ID = URL_VIEW_REPORTS + "/banks/allMIDTID";

    String URL_DOWNLOAD_REPORT_FOR_SALE_MID_TID = URL_VIEW_REPORTS + "/download-excel/saleMIDTID";

    String URL_DOWNLOAD_REPORT_FOR_ALL_MID_TID = URL_VIEW_REPORTS + "/download-excel/allMIDTID";

    String URL_DOWNLOAD_REPORT_FOR_DEVICES = URL_VIEW_REPORTS + "/download-excel/devices";

    String URL_DOWNLOAD_REPORT_FOR_COMPARAMS = URL_VIEW_REPORTS + "/download-excel/comParams";

    String URL_VIEW_REPORTS_BY_BANK_CODE_REPORT2_DOWNLOAD = URL_VIEW_REPORTS + "/download-excel/amexMIDTID";

    String URL_VIEW_REPORTS_DEVICE = URL_ROOT + "/view-reports/device";

    String URL_SEARCH_ACTIVITIES_BY_SERIAL_NO = URL_ROOT + "/search-activities-by-serial-no";
    String URL_NEW_ANOTHER_MERCHANT = URL_ROOT + "/set-new-another-merchant";


    String URL_VIEW_ALL_EVENTS = URL_ROOT + "/view-all-events";
    String URL_SELECT_TODAY_DEVICE_FOR_EVENT = URL_ROOT + "/select-device";
    String URL_NEW_EVENTS = URL_ROOT + "/new-event";
    String URL_VIEW_EVENTS_BY_DATES = URL_ROOT + "/view-event-by-dates";
    String URL_LOAD_UPDATE_EVENT = URL_ROOT + "/update-event";
    String URL_VIEW_EVENT = URL_ROOT + "/view-event";
    String URL_SAVE_EVENT_CHANGES = URL_ROOT + "/save-update-event";
    String URL_DELETE_EVENT = URL_ROOT + "/delete-event";
    String URL_LOAD_EVENT_BY_SEARCH = URL_ROOT + "/search-event";
    String URL_LOAD_EVENT_BY_ANY_TEXT = URL_ROOT + "/search-event-by-key";
    String URL_SEARCH_DEVICE_FOR_EVENT_BY_ANY_TEXT = URL_ROOT + "/search-device-by-key-for-event";
    String URL_VIEW_TODAY_EVENTS = URL_ROOT + "/view-events";
    String URL_SELECT_ALL_DEVICES_FOR_EVENT = URL_ROOT + "/select-all-device-for-event";
    String URL_SEARCH_DEVICE_FOR_EVENT_BY_DATE = URL_ROOT + "/search-device-by-dates-for-event";
    String URL_SEARCH_DEVICE_BY_ANY_TEXT = URL_ROOT + "/search-devices-by-key";
    String URL_SEARCH_DEVICE_BY_DATE = URL_ROOT + "/search-devices-by-dates";
    String URL_VIEW_DEVICES_WITH_TID_TID_FOR_EVENT = URL_ROOT + "/search-device-by-ids-for-event";
    //profiles
    String URL_ADD_NEW_PROFILE = URL_ROOT + "/add-new-profile";
    String URL_NEW_PROFILE_TO_CART = URL_ROOT + "/new-profile-add-to-cart";
    String URL_DELETE_NEW_PROFILE = URL_ROOT + "/delete-new-profile";
    String URL_DELETE_PROFILE = URL_ROOT + "/delete-profile";
    String URL_CONFIGURE_MERCHANT_TO_PROFILE = URL_ROOT + "/configure-profile-merchant";
    String URL_MERCHANT_TO_PROFILE_CART = URL_ROOT + "/add-merchant-to-profile-cart";
    String URL_VIEW_PROFILE = URL_ROOT + "/view-profile";
    String URL_REMOVE_PROFILE = URL_ROOT + "/remove-profile";
    String URL_ADD_ANOTHER_PROFILE = URL_ROOT + "/add-another-profile";
    String URL_MERCHANT_TO_CURRENT_PROFILE_CART = URL_ROOT + "/add-merchant-to-current-profile-cart";
    String URL_CONFIGURE_MERCHANT_TO_ANOTHER_PROFILE = URL_ROOT + "/configure-another-profile-merchant";
    String URL_ADD_OFFLINE_USER = URL_ROOT + "/add-offline-user";
    String URL_EDIT_OFFLINE_USER = URL_ROOT + "/edit-offline-user";
    String URL_SAVE_OFFLINE_USER = URL_ROOT + "/save-offline-user";
    String URL_MODIFY_SAVE_OFFLINE_USER = URL_ROOT + "/modify-save-offline-user";
    String URL_VIEW_OFFLINE_USER = URL_ROOT + "/view-offline-user";
    String URL_VIEW_SETTING_CONFIGURATION = URL_ROOT + "/setting-config";
    String URL_NEW_PROFILE_TO_CURRENT_CART = URL_ROOT + "/new-profile-to-current-cart";
    String URL_MODIFY_PROFILE = URL_ROOT + "/modify-profile";
    String URL_MODIFY_CURRENT_PROFILE = URL_ROOT + "/modify-current-profile";
    String URL_MODIFY_MERCHANT_TO_PROFILE = URL_ROOT + "/modify-profile-merchant";
    String URL_MODIFY_MERCHANT_TO_PROFILE_CART = URL_ROOT + "/modify-merchant-to-profile-cart";
    String URL_MODIFY_PROFILE_TO_CURRENT_CART = URL_ROOT + "/modify-profile-to-current-cart";
    String URL_CLEAR_MERCHANTS_REDIRECT_ADD_NEW_PROFILE = URL_ROOT + "/clear-merchants-redirect-add-new";
    String URL_MODIFY_NEW_PROFILE = URL_ROOT + "/modify-new-profile";
    String URL_MODIFY_NEW_PROFILE_MERCHANT = URL_ROOT + "/modify-new-profile-merchant";
    String URL_MODIFY_MERCHANT_IN_PROFILE_CART = URL_ROOT + "/modify-merchant-to-current-profile-cart";
    String URL_MODIFY_PROFILE_IN_CART = URL_ROOT + "/modify-new-profile-in-cart";
    String URL_MODIFY_SELECTED_PROFILE = URL_ROOT + "/modify-selected-profile";
    String URL_MODIFY_NEW_PROFILE_ADD_TO_CART = URL_ROOT + "/modify-new-profile-add-to-cart";
    String URL_CLREAR_ADD_NEW_PROFILE = URL_ROOT + "/clear-add-new-profile";
    String URL_CLEAR_ADD_ANOTHER_PROFILE = URL_ROOT + "/clear-add-another-profile";
    String URL_MODIFY_PROFILE_TO_CURRENT_CART_GO_BACK = "/go-back";

    String URL_MANAGE_ACCOUNT = URL_ROOT + "/manage-account";
    String URL_CHANGE_PASSWORD = URL_ROOT + "/change-password";
    String URL_REGISTER_USER = URL_ROOT + "/register-user";
    String URL_SAVE_USER = URL_ROOT + "/save-user";
    String URL_VIEW_ALL_USERS = URL_ROOT + "/view-users";
    String URL_ENABLE_USER = URL_ROOT + "/enable-user";
    String URL_LOCK_USER = URL_ROOT + "/lock-user";
    String URL_RESET_USER_PASSWORD = URL_ROOT + "/reset-user-pw";

    //BIN Rules
    String URL_NEW_BIN_RULE = URL_ROOT + "/new-bin-rule";
    String URL_MODIFY_NEW_BIN_RULE = URL_ROOT + "/modify-new-bin-rule";
    String URL_MODIFY_CURRENT_BIN_RULE = URL_ROOT + "/modify-current-bin-rule";
    String URL_DELETE_BIN_RULE = URL_ROOT + "/delete-new-bin-rule";
    String URL_DELETE_CURRENT_BIN_RULE = URL_ROOT + "/delete-current-bin-rule";
    String URL_NEW_BIN_RULE_MODIFY_DEVICE = URL_ROOT + "/new-bin-rule-modify-device";
    String URL_DELETE_NEW_BIN_RULE_MODIFY_DEVICE = URL_ROOT + "/delete-new-bin-rule-modify-device";

    String URL_COPY_DEVICE = URL_ROOT + "/copy-device";

    String URL_PASTE_DEVICE = URL_ROOT + "/paste-device";

    //Dashboard
    String URL_SETTINGS_DASHBOARD_CONFIG = URL_ROOT + "/dashboard-config";
    String URL_SETTINGS_CONFIG = URL_ROOT + "/configurations";
    String URL_UPDATE_DASHBOARD_CONFIG = URL_ROOT + "/update-dashboar-config";
    String URL_UPDATE_COMMON_CONFIG = URL_ROOT + "/update-Common-config";
    String REDIRECT_TO_VIEW_DEVICES = ROOT_REDIRECT + "/view-devices";
    String REDIRECT_TO_VIEW_ALL_DEVICES = ROOT_REDIRECT + "/view-all-devices";
    String REDIRECT_TO_VIEW_ALL_EVENTS = ROOT_REDIRECT + "/view-all-events";
    String REDIRECT_TO_SELECT_ALL_DEVICES_FOR_EVENT = ROOT_REDIRECT + "/select-all-device-for-event";
    String REDIRECT_TO_RESET_PASSWORD = REDIRECT_NAME + "/new-login";
    String REDIRECT_TO_MANAGE_ACCOUNT = ROOT_REDIRECT + "/manage-account";

    // web pages
    String PAGE_PATH = "pages";
    String PAGE_INDEX = "index";
    String PAGE_INDEX1 = "dashboard";
    String PAGE_LOGIN = "login";
    String PAGE_NEW_LOGIN = "new-login";
    String PAGE_FORGOT_PASSWORD = "forgot-password";

    String PAGE_VIEW_DEVICES = PAGE_PATH + "/view-devices";
    String PAGE_REGISTER_DEVICE = PAGE_PATH + "/register-device";
    String PAGE_MODIFY_DEVICE = PAGE_PATH + "/modify-device";
    String PAGE_PUSH_DEVICE = PAGE_PATH + "/push-device";
    String PAGE_VIEW_DEVICE = PAGE_PATH + "/view-device";
    String PAGE_ADD_NEW_MERCHANT = PAGE_PATH + "/add-new-merchant";
    String PAGE_ANOTHER_MERCHANT = PAGE_PATH + "/add-another-merchant";
    String PAGE_DEVICE_SAVE_SUCCESS = PAGE_PATH + "/device-save-success";
    String PAGE_DEVICE_SAVE_FAIL = PAGE_PATH + "/device-save-fail";
    String PAGE_DEVICE_MODIFY_SUCCESS = PAGE_PATH + "/device-modify-success";
    String PAGE_DEVICE_MODIFY_FAIL = PAGE_PATH + "/device-modify-fail";
    String PAGE_MODIFY_NEW_MERCHANT = PAGE_PATH + "/modify-new-merchant";
    String PAGE_MODIFY_CURRENT_MERCHANT = PAGE_PATH + "/modify-current-merchant";
    String PAGE_VIEW_MERCHANT = PAGE_PATH + "/merchant-details";
    String PAGE_ADD_SETTLE_REQUEST = PAGE_PATH + "/settle-request";
    String PAGE_ADD_NEW_EVENT = PAGE_PATH + "/add-new-event";
    String PAGE_NEW_EVENT_SUCCESS = PAGE_PATH + "/event-save-success";
    String PAGE_EVENT_FAIL = PAGE_PATH + "/event-save-fail";
    String PAGE_SELECT_DEVICE_FOR_EVENTS = PAGE_PATH + "/select-device-for-events";
    String PAGE_VIEW_EVENTS = PAGE_PATH + "/view-events";
    String PAGE_EVENT_EXIST_SAVE_FAILED = PAGE_PATH + "/event-save-fail-event-exist";
    String PAGE_MODIFY_EVENT = PAGE_PATH + "/modify-event";
    String PAGE_VIEW_EVENT = PAGE_PATH + "/view-event";
    String PAGE_SUCCESS = PAGE_PATH + "/event-success";
    String PAGE_VIEW_REPORTS = PAGE_PATH + "/view-reports";
    String PAGE_ADD_NEW_PROFILE = PAGE_PATH + "/add-new-profile";
    String PAGE_CONFIGURE_MERCHANT_TO_PROFILE = PAGE_PATH + "/configure-profile-merchant";
    String PAGE_VIEW_PROFILE = PAGE_PATH + "/view-profile";
    String PAGE_ADD_ANOTHER_PROFILE = PAGE_PATH + "/add-another-profile";
    String PAGE_CONFIGURE_MERCHANT_TO_ANOTHER_PROFILE = PAGE_PATH + "/configure-another-profile-merchant";
    String PAGE_OFFLINE_USER = PAGE_PATH + "/offline-user";
    String PAGE_MODIFY_OFFLINE_USER = PAGE_PATH + "/modify-offline-user";
    String PAGE_VIEW_OFFLINE_USER = PAGE_PATH + "/view-offline-user";
    String PAGE_MODIFY_PROFILE = PAGE_PATH + "/modify-profile";
    String PAGE_MODIFY_MERCHANT_TO_PROFILE = PAGE_PATH + "/modify-profile-merchant";
    String PAGE_MODIFY_NEW_PROFILE = PAGE_PATH + "/modify-new-profile";
    String PAGE_MODIFY_NEW_PROFILE_MERCHANT = PAGE_PATH + "/modify-new-profile-merchant";
    String PAGE_MODIFY_SELECTED_PROFILE = PAGE_PATH + "/modify-selected-profile";

    String PAGE_MANAGE_ACCOUNT = PAGE_PATH + "/manage-account";
    String PAGE_REGISTER_USER = PAGE_PATH + "/register-user";
    String PAGE_VIEW_USERS = PAGE_PATH + "/view-users";

    String PAGES_VIEW_REPORTS = "pages/view-reports";
    String PAGES_VIEW_REPORTS_SALEMIDTID = "pages/saleMIDTID";
    String PAGES_VIEW_REPORTS_AMEXMIDTID = "pages/amexMIDTID";
    String PAGES_VIEW_REPORTS_ALLMIDTID = "pages/allMIDTID";
    String PAGE_VIEW_REPORTS_DEVICES = "pages/devices";
    String PAGE_VIEW_SETTING_CONFIG = "pages/setting-config";
    String PAGE_VIEW_REPORTS_COM_PARAMS = "pages/communicationParams";
    // BIN Rule pages
    String PAGE_NEW_BIN_RULE = PAGE_PATH + "/add_new_bin_rule";
    String PAGE_ADD_BIN_RULE_TO_MERCHANT = PAGE_PATH + "/add_bin_rule_to_new_merchant";
    String PAGE_ADD_BIN_RULE_TO_CURRENT_MERCHANT = PAGE_PATH + "/add_bin_rule_to_current_merchant";
    String PAGE_NEW_BIN_RULE_MODIFY_DEVICE = PAGE_PATH + "/add_bin_rule_to_new_merchant-modify-device";

    //Dashboard Page
    String PAGE_DASHBOARD_CONFIG = PAGE_PATH + "/dashboard-config";

    String PAGE_CONFIG = PAGE_PATH + "/common_config";

    // validation
    String PAGE_VALIDATE_MERCHANT = PAGE_PATH + "/validate-merchant";

    // error pages
    String PAGE_ERROR_BAD_REQUEST = "error/400";
    String PAGE_ERROR_UNAUTHORIZED = "error/401";
    String PAGE_ERROR_FORBIDDEN = "error/403";
    String PAGE_ERROR_NOT_FOUND = "error/404";
    String PAGE_ERROR_INTERNAL_SERVER_ERROR = "error/500";
    String PAGE_ERROR_403 = "/error/403";
    String ERROR_PATH_403 = "/403";

    String PAGE_OPERATION_SUCCESS = PAGE_PATH + "/success";
    String PAGE_OPERATION_FAIL = PAGE_PATH + "/fail";
    String PAGE_SERIALNO_VALIDATION_FAIL = PAGE_PATH + "/validate-serial-no";


}
