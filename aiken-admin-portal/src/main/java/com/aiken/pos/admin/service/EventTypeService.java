package com.aiken.pos.admin.service;

import com.aiken.pos.admin.constant.EventTypes;
import com.aiken.pos.admin.model.EventType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


/**
 * *
 *
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2021-08-10
 */

@Service
public class EventTypeService {

    public List<EventType> getEventTypes() {

        return Arrays.asList(

                new EventType(EventTypes.ACTIVITY_REPORT, "Activity-Report Enable / Disable"),
                new EventType(EventTypes.ACTIVITY_TRACKER, "Activity-Tracker Enable / Disable"),
                new EventType(EventTypes.AMEX_DCC, "AMEX DCC Enable / Disable"),
                new EventType(EventTypes.AMEX_OFFLINE, "AMEX Offline Enable / Disable"),
                new EventType(EventTypes.AMEX_PRE_AUTH, "AMEX Pre-Auth Enable / Disable"),
                new EventType(EventTypes.AMEX_MID_TID_CHANGE, "AMEX Merchant - MID / TID Change"),
                new EventType(EventTypes.CONFIG_AMEX, "Amex Sale Enable / Disable"),
                new EventType(EventTypes.ADD_EPP, "Add EPP Merchant"),

                new EventType(EventTypes.ADD_PROFILE, "Add New Profile"),
                new EventType(EventTypes.AUTO_SETTLE, "Auto Settlement Enable / Disable"),
                new EventType(EventTypes.AUTO_REVERSAL, "Auto Reversal Enable / Disable"),

                new EventType(EventTypes.CONFIG_AUTH_SERVER, "Auth Server Configurations"),


                new EventType(EventTypes.BIN_BLOCK_CONFIG, "BIN Block Configurations"),
                new EventType(EventTypes.BLOCK_MAG_TRANSACTIONS, "Block MAG Transactions"),
                new EventType(EventTypes.CLEAR_BATCH, "Clear Current Batch"),

                new EventType(EventTypes.CLEAR_REVERSAL, "Clear Reversal"),
                new EventType(EventTypes.ENABLE_CUSTOMER_RECEIPT, "Customer Receipt Enable/Disable"),
                new EventType(EventTypes.ENABLE_COMMISSION_RATE, "Commission Rate Enable/Disable"),
                new EventType(EventTypes.CONFIGURE_OFFLINE_USERS, "Configure Offline Users"),
                new EventType(EventTypes.DELETE_EPP, "Delete EPP Merchant"),
                new EventType(EventTypes.DIFFERENT_MID_TID_SETTINGS, "Different MID-TID Settings"),
                new EventType(EventTypes.DIGITAL_SIGNATURE, "Digital Signature Enable / Disable"),
                //new EventType(EventTypes.DISABLE_FRGN_PMNTS_FOR_LKR_CARDS,"Foreign Currency Payments from LKR Cards Enable / Disable"),
                new EventType(EventTypes.DEBUG_MODE, "Debug-Mode Enable / Disable"),
                new EventType(EventTypes.ECR_SALE, "ECR SALE Enable / Disable / Configure"),
                new EventType(EventTypes.ECR_QR, "ECT QR Enable / Disable"),
                new EventType(EventTypes.EPP_MID_TID_CHANGE, "EPP Merchant - MID / TID Change"),
                new EventType(EventTypes.EPP_AMEX_MID_TID_CHANGE, "EPP-AMEX Merchant - MID / TID Change"),
                new EventType(EventTypes.EVENT_AUTO_UPDATE, "Event Auto Update Enable / Disable"),
                new EventType(EventTypes.FORCE_SETTLE, "Force Settlement Enable / Disable"),
                new EventType(EventTypes.DISABLE_FRGN_PMNTS_FOR_LKR_CARDS, "Foreign Currency Payments from LKR Cards Enable / Disable"),
                new EventType(EventTypes.ENABLE_IMEI_SCAN, "Imei Scan Enable / Disable"),
                new EventType(EventTypes.NAME_CHANGE, "Merchant Name Change"),
                new EventType(EventTypes.ADDRESS_CHANGE, "Merchant Address Change"),
                new EventType(EventTypes.MANUAL_KEY_IN, "Manual-Key-In Enable / Disable"),
                new EventType(EventTypes.MANUAL_KEY_IN_AMEX_ONLY, "Manual-Key-In-Only-For-AMEX Enable / Disable"),
                new EventType(EventTypes.MANUAL_KEY_IN_FOR_PRE_AUTH, "Manual-Key-In for Pre-Auth Enable / Disable"),
                new EventType(EventTypes.MANUAL_KEY_IN_FOR_OFFLINE, "Manual-Key-In for Offline Enable / Disable"),
                new EventType(EventTypes.MODIFY_PROFILE, "Modify Existing Profile"),
                new EventType(EventTypes.NETWORK_MODE_CHANGE, "Network Change"),
                new EventType(EventTypes.NO_SETTLE, "No-Settlement Enable / Disable"),
                new EventType(EventTypes.ONE_CLICK_SETUP, "One-Click-Setup"),
                new EventType(EventTypes.ONE_TIME_SETTING_PASSWORD, "Onetime Password For Settings"),
                new EventType(EventTypes.PUSH_NOTIFICATION, "Push Notofication Enable / Disable"),
                new EventType(EventTypes.PRINTLESS, "Printless Mode Enable / Disable"),
                new EventType(EventTypes.QR_PARAM_CHANGE, "QR Params Change"),
                new EventType(EventTypes.QR_VID_CID_CHANGE, "QR-SOLO VID / CID Change"),
                new EventType(EventTypes.QR_MID_TID_CHANGE, "QR Merchant - MID / TID Change"),
                new EventType(EventTypes.QR_REFUND, "QR Refund Enable / Disable"),
                new EventType(EventTypes.ENABLE_REFERENCE_DATA, "Reference Data Enable/Disable"),
                new EventType(EventTypes.REMOVE_PROFILE, "Remove Existing Profile"),
                new EventType(EventTypes.REVERSAL_HISTORY, "Reversal-History Enable / Disable"),
                new EventType(EventTypes.SALE_DCC, "SALE DCC Enable / Disable"),
                new EventType(EventTypes.SALE_JCB, "SALE JCB Enable / Disable"),
                new EventType(EventTypes.SALE_OFFLINE, "SALE Offline Enable / Disable"),
                new EventType(EventTypes.SALE_PRE_AUTH, "SALE Pre-Auth Enable / Disable"),
                new EventType(EventTypes.SALE_MID_TID_CHANGE, "SALE Merchant - MID / TID Change"),
                new EventType(EventTypes.ENABLE_SANHINDA_PAYMENTS, "Sampath SANHINDA Pay Enable/Disable"),
                new EventType(EventTypes.SIGNATURE_PIN_PRIORITY, "Signature-PIN Priority Enable/Disable"),
                new EventType(EventTypes.ENABLE_TLE_PROFILE, "TLE Profile Enable/Disable"),
                new EventType(EventTypes.VOID_AND_SETTLEMENT_PASSWORD, "Void And Settlement Password")

        );
    }

    public List<EventType> getEventTransactionTypes() {

        return Arrays.asList(
                new EventType(EventTypes.SALE, "Sale"),
                new EventType(EventTypes.SALE_VISA, "Sale - VISA Only"),
                new EventType(EventTypes.SALE_MASTER, "Sale - MASTER Only"),
                new EventType(EventTypes.SALE_JCB_CUP, "Sale - JCB/CUP Only"),
                new EventType(EventTypes.SALE_OTHER, "Sale - Other Merchants")
        );
    }


    public List<EventType> getExecuteTypes() {

        return Arrays.asList(
                new EventType(EventTypes.AUTO_UPDATE, "AUTO UPDATE"),
                new EventType(EventTypes.MANUAL_UPDATE, "MANUAL UPDATE")
        );
    }
}
