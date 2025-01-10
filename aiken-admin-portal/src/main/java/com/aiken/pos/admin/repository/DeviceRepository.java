package com.aiken.pos.admin.repository;

import com.aiken.common.util.validation.DateUtil;
import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.constant.ActionType;
import com.aiken.pos.admin.constant.MerchantTypes;
import com.aiken.pos.admin.model.*;
import com.aiken.pos.admin.web.form.OfflineUserForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Device Repository
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-29
 */

@Repository
public class DeviceRepository implements GenericRepository<Device, Integer> {

    private Logger logger = LoggerFactory.getLogger(DeviceRepository.class);

    // SQL SCRIPTS
    private final String INSERT_DEVICE = "insert into public.device "
            + "(serial_no, token, bank_code, bank_name, merchant_name, merchant_address, visa_no_cvm_limit, cntactls_trxn_limit, "
            + " auto_settle, auto_settle_time, force_settle, ecr, key_in, added_date, added_by, last_update, updated_by, status, "
            + " mobile_no, sim_no, cust_contact_no, remark, ecr_qr, signature, debug_mode, no_settle,activity_tracker,qr_refund, "
            + " reversal_history,is_profile,push_notification,enable_amex,pre_auth,dcc,offline,key_in_amex,popup_msg, "
            + " card_type_val,sale_receipt,curr_from_bin,curr_from_card,prcd_with_lkr,card_tap,card_insert,card_swipe, "
            + " dcc_payload,merchant_portal,resend_void_sms,live_client,auto_reversal,network,printless_mode,lkr_default_curr, "
            + " void_pwd,diff_sale_mid_tid,mki_pre_auth,mki_offline,sing_priority,block_mag,cust_receipt,is_seg,auto_update,"
            + "print_receipt,new_void_pwd,new_sattle_pwd,imei_scan,commission,commission_rate,offline_users,ecr_ip,ecr_port,ecr_auth_token,tran_to_sim,ecr_wifi,sanhinda_pay,ref_no,tle_profile) "
            + " values(:serial_no, :token, :bank_code, :bank_name, :merchant_name, :merchant_address,  "
            + " :visa_no_cvm_limit, :cntactls_trxn_limit, :auto_settle, :auto_settle_time, :force_settle, :ecr, :key_in,"
            + "  :added_date, :added_by, :last_update, :updated_by, :status, :mobile_no, :sim_no, :cust_contact_no, :remark, "
            + " :ecr_qr, :signature, :debug_mode, :no_settle,:activity_tracker,:qr_refund,:reversal_history,:is_profile, "
            + " :push_notification,:enable_amex,:pre_auth,:dcc,:offline,:key_in_amex,:popup_msg, "
            + " :card_type_val,:sale_receipt,:curr_from_bin,:curr_from_card,:prcd_with_lkr,:card_tap,:card_insert,:card_swipe, "
            + " :dcc_payload,:merchant_portal,:resend_void_sms,:live_client,:auto_reversal,:network,:printless_mode,:lkr_default_curr,:void_pwd,"
            + " :diff_sale_mid_tid,:mki_pre_auth,:mki_offline,:sing_priority,:block_mag,:cust_receipt,:is_seg, :auto_update,:print_receipt,"
            + " :new_void_pwd,:new_sattle_pwd,:imei_scan,:commission,:commission_rate,:offline_users,:ecr_ip,:ecr_port,:ecr_auth_token,:tran_to_sim,:ecr_wifi,:sanhinda_pay,:ref_no,:tle_profile)";

    private final String INSERT_MERCHANT = "insert into public.merchant "
            + "(category, month, mid, tid, currency, description, min_amount, max_amount, void_tx, amex_tx, device_id,dcc,pre_auth,offline,merchant_type,jcb,"
            + "is_seg,is_local,is_foreign,is_on_us,is_off_us,iphone_imei) "
            + " values(:category, :month, :mid, :tid, :currency, :description, :min_amount, :max_amount, "
            + " :void_tx, :amex_tx, :device_id,:dcc,:pre_auth,:offline,:merchant_type,:jcb,"
            + ":is_seg,:is_local,:is_foreign,:is_on_us,:is_off_us,:iphone_imei) ";

    private final String INSERT_BIN_CONFIG = "insert into public.bin_config "
            + "(device_id, card_type, tranx_type, action, config_type, bin_start, bin_end, mid, tid,merchant_id)"
            + " values(:device_id, :card_type, :tranx_type, :action, :config_type, :bin_start, :bin_end, :mid, :tid,:merchant_id)";

    private final String INSERT_PROFILE = "insert into public.profile "
            + "(profile_name, is_default, merchant_name, merchant_adrs, visa_cnt, visa_cntls, visa_nocvm_limit, visa_cntls_trxn_limit, "
            + "mc_cnt, mc_cntls, mc_nocvm_limit, mc_cntls_trxn_limit, amex_cnt, amex_cntls, amex_nocvm_limit, amex_cntls_trxn_limit,"
            + "upay_cnt, upay_cntls, upay_nocvm_limit, upay_cntls_trxn_limit, jcb_cnt, jcb_cntls, jcb_nocvm_limit, jcb_cntls_trxn_limit,"
            + "added_date, added_by, last_update, updated_by, status, device_id,customer_copy,prof_ref_id,tls)"
            + "values(:profile_name, :is_default, :merchant_name, :merchant_adrs, :visa_cnt, :visa_cntls, :visa_nocvm_limit, :visa_cntls_trxn_limit,"
            + ":mc_cnt, :mc_cntls, :mc_nocvm_limit, :mc_cntls_trxn_limit, :amex_cnt, :amex_cntls, :amex_nocvm_limit, :amex_cntls_trxn_limit,"
            + ":upay_cnt, :upay_cntls, :upay_nocvm_limit, :upay_cntls_trxn_limit, :jcb_cnt, :jcb_cntls, :jcb_nocvm_limit, :jcb_cntls_trxn_limit,"
            + ":added_date, :added_by, :last_update, :updated_by, :status, :device_id,:customer_copy,:prof_ref_id,:tls)";

    private final String INSERT_PROF_MERCHANT = "insert into public.profile_merchant"
            + "(profile_id, merchant_id, is_default, added_date, added_by, last_update, updated_by, status,prof_mer_ref_id)"
            + "values(:profile_id, :merchant_id, :is_default, :added_date, :added_by, :last_update, :updated_by, :status,:prof_mer_ref_id)";

    private final String INSERT_SCAN_PARAM = "insert into public.scan_param "
            + "(m_user_id, m_password, checksum_key, merchant_id,vid,cid,is_qr_ref_id) "
            + " values(:m_user_id, :m_password, :checksum_key, :merchant_id,:vid,:cid,:is_qr_ref_id)";

    private final String INSERT_AMEX_PARAM = "insert into public.amex_param " + "(amex_ip, merchant_id) "
            + " values(:amex_ip, :merchant_id)";

    private final String INSERT_DEVICE_COM_STATUS = "insert into public.com_config " + "(serial_no, operator1, sim1, operator2, sim2, ref1, ref2, ref3) "
            + " values(:serial_no, :operator1, :sim1, :operator2, :sim2, :ref1, :ref2, :ref3)";

    private final String UPDATE_DEVICE = "update public.device set serial_no = :serial_no, "
            + " bank_code = :bank_code, bank_name = :bank_name, merchant_name = :merchant_name, merchant_address = :merchant_address, "
            + " last_update = :last_update, updated_by = :updated_by, status = :status, visa_no_cvm_limit = :visa_no_cvm_limit, "
            + " cntactls_trxn_limit = :cntactls_trxn_limit, auto_settle = :auto_settle, auto_settle_time = :auto_settle_time, "
            + " force_settle = :force_settle, ecr = :ecr, key_in = :key_in, mobile_no = :mobile_no, sim_no = :sim_no, "
            + " cust_contact_no = :cust_contact_no, remark = :remark, ecr_qr = :ecr_qr, signature = :signature,"
            + " debug_mode = :debug_mode, no_settle = :no_settle, activity_tracker = :activity_tracker,"
            + " qr_refund =:qr_refund,reversal_history =:reversal_history, is_profile = :is_profile ,enable_amex=:enable_amex,"
            + " push_notification=:push_notification,pre_auth=:pre_auth,dcc=:dcc,offline=:offline,key_in_amex=:key_in_amex,"
            + " popup_msg=:popup_msg,card_type_val=:card_type_val,sale_receipt=:sale_receipt,curr_from_bin=:curr_from_bin, "
            + " curr_from_card=:curr_from_card,prcd_with_lkr=:prcd_with_lkr,card_tap=:card_tap,card_insert=:card_insert,card_swipe=:card_swipe,network=:network, "
            + " dcc_payload=:dcc_payload,merchant_portal=:merchant_portal,resend_void_sms=:resend_void_sms,live_client=:live_client,auto_reversal=:auto_reversal, "
            + " printless_mode=:printless_mode,lkr_default_curr=:lkr_default_curr,void_pwd=:void_pwd,diff_sale_mid_tid=:diff_sale_mid_tid,"
            + " mki_pre_auth=:mki_pre_auth,mki_offline=:mki_offline,block_mag=:block_mag,sing_priority=:sing_priority,cust_receipt=:cust_receipt,"
            + " is_seg=:is_seg,auto_update=:auto_update,print_receipt=:print_receipt,new_void_pwd=:new_void_pwd,new_sattle_pwd=:new_sattle_pwd,"
            + " imei_scan=:imei_scan,commission=:commission,commission_rate=:commission_rate,offline_users=:offline_users,"
            + " ecr_ip=:ecr_ip,ecr_port=:ecr_port,ecr_auth_token=:ecr_auth_token,tran_to_sim=:tran_to_sim,ecr_wifi=:ecr_wifi,"
            + " sanhinda_pay=:sanhinda_pay,ref_no=:ref_no,tle_profile=:tle_profile"
            + " where device_id = :device_id";

    private final String UPDATE_MERCHANT = "update public.merchant set "
            + " category = :category, month = :month, mid = :mid, tid = :tid, currency = :currency, "
            + " description = :description, min_amount = :min_amount, max_amount = :max_amount, "
            + " void_tx = :void_tx, amex_tx = :amex_tx, dcc=:dcc, pre_auth=:pre_auth, offline=:offline,merchant_type=:merchant_type,jcb=:jcb, "
            + " is_seg=:is_seg,is_local=:is_local,is_foreign=:is_foreign,is_on_us=:is_on_us,is_off_us=:is_off_us,iphone_imei=:iphone_imei"
            + " where device_id = :device_id and merchant_id = :merchant_id";

    private final String UPDATE_PROFILE = "update public.profile set "
            + "profile_name = :profile_name, is_default = :is_default, merchant_name = :merchant_name, merchant_adrs = :merchant_adrs,"
            + "visa_cnt = :visa_cnt, visa_cntls = :visa_cntls, visa_nocvm_limit = :visa_nocvm_limit, visa_cntls_trxn_limit = :visa_cntls_trxn_limit,"
            + "mc_cnt = :mc_cnt, mc_cntls = :mc_cntls, mc_nocvm_limit = :mc_nocvm_limit, mc_cntls_trxn_limit = :mc_cntls_trxn_limit,"
            + "amex_cnt = :amex_cnt, amex_cntls = :amex_cntls, amex_nocvm_limit = :amex_nocvm_limit, amex_cntls_trxn_limit = :amex_cntls_trxn_limit,"
            + "upay_cnt = :upay_cnt, upay_cntls = :upay_cntls, upay_nocvm_limit = :upay_nocvm_limit, upay_cntls_trxn_limit = :upay_cntls_trxn_limit,"
            + "jcb_cnt = :jcb_cnt, jcb_cntls = :jcb_cntls, jcb_nocvm_limit = :jcb_nocvm_limit, jcb_cntls_trxn_limit = :jcb_cntls_trxn_limit,"
            + "last_update = :last_update, updated_by = :updated_by, status = :status,customer_copy=:customer_copy,prof_ref_id =:prof_ref_id,tls=:tls "
            + "where device_id = :device_id and profile_id = :profile_id";

    private final String UPDATE_SCAN_PARAM = "update public.scan_param set "
            + " m_user_id = :m_user_id, m_password = :m_password, checksum_key = :checksum_key, vid=:vid, cid=:cid ,is_qr_ref_id=:is_qr_ref_id "
            + " where merchant_id = :merchant_id";

    private final String UPDATE_AMEX_PARAM = "update public.amex_param set " + " amex_ip = :amex_ip "
            + " where merchant_id = :merchant_id";

    private final String UPDATE_DEVICE_TOKEN = "update public.device set "
            + " token = :token, last_update = :last_update, updated_by = :updated_by, status = :status "
            + " where serial_no = :serial_no";

    private final String UPDATE_DEVICE_STATUS = "update public.device set "
            + " last_update = :last_update, updated_by = :updated_by, status = :status "
            + " where serial_no = :serial_no";
    private final String UPDATE_DEVICE_COM_STATUS = "update public.com_config set "
            + " serial_no=:serial_no, operator1=:operator1, sim1=:sim1, operator2=:operator2, sim2=:sim2, ref1=:ref1, ref2=:ref2, ref3=ref3 "
            + " where serial_no = :serial_no";

    private final String SELECT_DEVICE_BELONGS_TO_TID_OR_MID = "SELECT "
            + " d.device_id , d.serial_no , d.token , d.bank_code , d.bank_name , d.merchant_name , d.merchant_address ,"
            + " d.added_date , d.added_by , d.last_update , d.updated_by , d.status , d.mobile_no , d.sim_no , d.cust_contact_no "
            + " FROM merchant m"
            + " INNER JOIN device d ON m.device_id = d.device_id where (CASE WHEN :mid IS NOT NULL THEN mid LIKE ('%' || :mid || '%') ELSE TRUE END) AND"
            + " (CASE WHEN :tid IS NOT NULL THEN tid LIKE ('%' || :tid || '%') ELSE TRUE END)"
            + " order by d.last_update desc";

    private final String DELETE_DEVICE = "delete from public.device where device_id = :device_id";
    private final String DELETE_MERCHANT_BY_MERCHANT_ID = "delete from public.merchant where merchant_id = :merchant_id";
    private final String DELETE_SCAN_PRAM = "delete from public.scan_param where merchant_id = :merchant_id";
    private final String DELETE_AMEX_PRAM = "delete from public.amex_param where merchant_id = :merchant_id";

    private final String DELETE_MERCHANT_BY_DEVICE_ID_MERCHANT_ID = "delete from public.merchant where device_id = :device_id and merchant_id = :merchant_id";

    private final String DELETE_MERCHANT_FROM_PROFILE_MERCHANT = "delete from public.profile_merchant where merchant_id = :merchant_id";

    private final String DELETE_BIN_CONFIG_BY_MERCHANT_ID = "delete from public.bin_config where merchant_id = :merchant_id";

    private final String DELETE_BIN_CONFIG_BY_DEVICE_ID = "delete from public.bin_config where device_id = :device_id";

    private final String FIND_ALL_DEVICES = "select * from public.device order by last_update desc";
    private final String FIND_DEVICE_BY_SERIAL_NO = "select * from public.device where serial_no = :serial_no";
    private final String FIND_DEVICE_BY_DEVICE_ID = "select * from public.device where device_id = :device_id";
    //FIND_DEVICE_ADDED_BY_TODAY
    private final String FIND_DEVICE_BY_DATE = "select * from public.device where"
            + " TO_TIMESTAMP(last_update,'YYYY/MM/DD') between TO_TIMESTAMP(:fromDate,'YYYY/MM/DD') and"
            + " TO_TIMESTAMP(:toDate,'YYYY/MM/DD') order by last_update desc";

    private final String FIND_DEVICE_ADDED_BY_TODAY = "select * from public.device where"
            + " TO_TIMESTAMP(added_date,'YYYY/MM/DD') between TO_TIMESTAMP(:fromDate,'YYYY/MM/DD') and"
            + " TO_TIMESTAMP(:toDate,'YYYY/MM/DD') order by last_update desc";
    private final String FIND_DEVICE_BY_PARAM = "select * from public.device where"
            + " upper(serial_no) LIKE ('%' || upper(:param) || '%') or upper(bank_name) LIKE ('%' || upper(:param) || '%')"
            + " or upper(merchant_name) LIKE ('%' || upper(:param) || '%') or upper(merchant_address) LIKE ('%' || upper(:param) || '%') order by last_update desc";
    private final String FIND_MERCHANTS_BY_DEVICE_ID = "select * from public.merchant where device_id = :device_id order by merchant_id";
    private final String FIND_SCAN_PARAM_BY_MERCHANT_ID = "select * from public.scan_param where merchant_id = :merchant_id";
    private final String FIND_AMEX_PARAM_BY_MERCHANT_ID = "select * from public.amex_param where merchant_id = :merchant_id";

    private final String FIND_BIN_CONFIG_BY_DEVICE_ID = "select * from public.bin_config where device_id = :device_id";

    private final String FIND_MERCHANT_BY_DEVICE_ID_MERCHANT_ID = "select * from public.merchant where device_id = :device_id and merchant_id = :merchant_id";
    private final String FIND_MERCHANT_TIDS_BY_DEVICE_ID = "select tid from public.merchant where device_id = :device_id";
    private final String FIND_MERCHANT_IDS_BY_DEVICE_ID = "select merchant_id from public.merchant where device_id = :device_id";
    private final String FIND_LAST_DEVICE_ID = "select device_id from public.device where serial_no = :serial_no";
    private final String FIND_LAST_MERCHANT_ID = "select MAX(merchant_id) from public.merchant where device_id = :device_id";

    private final String FIND_PROFILE_BY_DEVICE_ID = "select * from public.profile where device_id = :device_id";
    private final String FIND_PROFILE_MERCHANT_BY_PROFILE_ID = "select * from public.profile_merchant where profile_id = :profile_id";
    private final String FIND_PROFILE_ID_BY_DEVICE_ID = "select profile_id from public.profile where device_id = :device_id";
    private final String FIND_PROFILE_MERCHANT_ID_BY_PROFILE_ID = "select prof_merg_id from public.profile_merchant where profile_id = :profile_id";
    private final String DELETE_PROFILE_BY_ID = "delete from public.profile where profile_id = :profile_id";
    private final String DELETE_PROFILE_MERCHANT_BY_ID = "delete from public.profile_merchant where prof_merg_id = :prof_merg_id";

    private final String FIND_MAX_SEQUENCE = "SELECT MAX(cur_value) AS cur_val FROM sequenz";
    private final String UPDATE_MAX_SEQUENCE = "INSERT INTO public.sequenz (cur_value) values (:cur_val)";

    private final String FIND_COM_DEVICE = "SELECT COUNT(serial_no) FROM public.com_config WHERE serial_no =:serial_no";
    private final String FIND_ALL_OFFLINE_USER = "SELECT * FROM public.offline_user";
    private static final String FIND_DEVICE_EXISTS_BY_SERIAL_NO = "SELECT EXISTS ( SELECT 1 FROM public.device WHERE serial_no = :serial_no) ";


    @Autowired
    private NamedParameterJdbcTemplate template;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Integer insert(Device device) throws IllegalArgumentException {
        logger.info("################## START INSERT ##################");
        logger.info(device.toString());
        // save device
        int currentDeviceId = saveDevice(device);
        logger.info("Device id: {}", currentDeviceId);
        logger.info("Device Save Successfully...");

        // save merchants
        if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
            logger.info("No. of Merchants: {}", device.getMerchants().size());

            device.getMerchants().forEach(merchant -> {
                logger.info(merchant.toString());
                // temp merchant id
                merchant.setTempMerchantId(merchant.getMerchantId());
                // save merchant
                int currentMerchantId = saveMerchant(merchant, currentDeviceId);

                logger.info("< Applying BIN Block Configurations >");
                device.getBinConfig().forEach(binData -> {

                    if (binData.getMerchantId().equals(merchant.getMerchantId())) {
                        saveBinConfig(binData, currentDeviceId, currentMerchantId);
                        logger.info("BIN Data Updated: | {} | {} | {}", binData.getTransType(), binData.getCardType(), binData.getMerchantId());
                    }
                });
                logger.info("BIN block configuration completed for the merchant: {} | {} ", currentMerchantId, merchant.getMerchantId());

                // update merchant id with primary key
                merchant.setMerchantId(currentMerchantId);
                logger.info("Merchants Save Successfully...");
                logger.info("Merchant Id: {}", currentMerchantId);

                if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                    // save scan prams
                    if (merchant.getScanParam() != null) {
                        logger.info(merchant.getScanParam().toString());
                        saveScanParam(merchant.getScanParam(), currentMerchantId);
                        logger.info("Scan Param Save Successfully....");
                    } else {
                        logger.warn("<<<<< NOT SCAN PARAMS TO SAVE >>>>>>");
                    }
                } else if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.AMEX)) {
                    // save amex prams
                    if (merchant.getAmexParam() != null) {
                        logger.info(merchant.getAmexParam().toString());
                        saveAmexParam(merchant.getAmexParam(), currentMerchantId);
                        logger.info("Amex Param Save Successfully....");
                    } else {
                        logger.warn("<<<<< NOT AMEX PARAMS TO SAVE >>>>>>");
                    }
                }
            });
            /*
             * logger.info("Applying BIN Block Configurations");
             * logger.info("Available BIN Configuration Count: " +
             * device.getBinConfig().size()); device.getBinConfig().forEach((binData) -> {
             *
             * saveBinConfig(binData,currentDeviceId); logger.info("BIN Data Updated: |" +
             * binData.getTransType() + "| " + binData.getCardType()); });
             * logger.info("BIN Block Configuration Completed");
             */
        } else {
            logger.warn("<<<<< NOT MERCHANTS TO SAVE >>>>>>");
        }

        // Save profiles (Amesh Madumalka 2021-10-07)
        logger.info("Profile available : {}", device.isProfile());
        if (device.isProfile() && device.getProfiles() != null) {
            device.getProfiles().forEach(profile -> {
                if (profile != null) {
                    profile.setAddedBy(device.getAddedBy());
                    profile.setAddedDate(DateUtil.getCurrentTime());
                    profile.setUpdatedBy(device.getUpdatedBy());
                    profile.setLastUpdate(DateUtil.getCurrentTime());
                    int profileId = saveProfile(profile, currentDeviceId);
                    logger.debug("Profile Id : {}", profileId);

                    if (profileId > 0 && profile.getProfileMerchants() != null
                            && !profile.getProfileMerchants().isEmpty() && device.getMerchants() != null
                            && !device.getMerchants().isEmpty()) {

                        // save profile merchant with merchant primary key
                        device.getMerchants()
                                .forEach(merchant -> profile.getProfileMerchants().forEach(profileMerchant -> {
                                    if (merchant != null && merchant.getTempMerchantId() != null
                                            && merchant.getMerchantId() != null && profileMerchant != null
                                            && profileMerchant.getMerchantId() != null && merchant.getTempMerchantId()
                                            .intValue() == profileMerchant.getMerchantId().intValue()) {

                                        // save profile merchant with correct merchant id
                                        profileMerchant.setMerchantId(merchant.getMerchantId());
                                        profileMerchant.setAddedBy(profile.getAddedBy());
                                        profileMerchant.setAddedDate(profile.getAddedDate());
                                        profileMerchant.setUpdatedBy(profile.getUpdatedBy());
                                        profileMerchant.setLastUpdate(DateUtil.getCurrentTime());

                                        int profMerId = saveProfMerchant(profileMerchant, profileId);
                                        logger.debug("Profile Merchant id {}", profMerId);

                                    } else {
                                        logger.warn("Merchant id is not match with temp merchant id");
                                    }
                                }));
                    } else {
                        logger.warn("Profile Not Insert");
                    }
                } else {
                    logger.info("Null profile object");
                }
            });
        } else {
            logger.warn("<<<<< NOT PROFILES TO SAVE >>>>>>");
        }

        logger.info("################## END INSERT ##################");

        return currentDeviceId;
    }

    @Override
    public List<Integer> insertAll(List<Device> models) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer update(Device device) throws IllegalArgumentException {
        logger.info("################## START UPDATE ##################");

        logger.info("Action: {}", device.getAction());
        if (device.getAction() == null)
            return null;
        List<MerchantMapper> newMerchantMap = new ArrayList<>();
        Integer deviceId = device.getDeviceId();
        // find device in db
        Device currentDevice = findDeviceById(deviceId);

        if (device.getAction().equals(ActionType.UPDATE)) {
            logger.info("Update Request [Serial No] : {}", device.getSerialNo());
            logger.info("Device id : {}", deviceId);

            if (currentDevice != null && currentDevice.getDeviceId() != null) {
                logger.info("Found device in db");
                // update device
                updateDevice(device, deviceId);
                logger.info("Device Update Successfully...");

                List<Long> tids = findMerchantTidsByDeviceId(deviceId);
                logger.info("Current tid list for this device: {}", tids);

                List<Integer> merchantIds = findMerchantIdsByDeviceId(deviceId);
                logger.info("Current merchantId list for this device: {}", merchantIds);

                if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
                    logger.info("Update Request [No. of Merchants]:{} ", device.getMerchants().size());

                    // process merchants
                    device.getMerchants().forEach(merchant -> {
                        logger.info("Merchant ID: {}", merchant.getMerchantId());
                        logger.info("Merchant type: {}", merchant.getCategory());
                        logger.info("MID : {}", merchant.getMid());
                        logger.info("TID : {}", merchant.getTid());
                        logger.info("Action : {}", merchant.getAction());

                        boolean isNew = false;
                        int currentMerchantId = 0;

                        if (merchant.getAction() != null && merchant.getAction().equals(ActionType.INSERT)) {
                            // save merchant, detect as new merchant
                            logger.info("Detect as new merchant, going to insert as new merchant");
                            currentMerchantId = saveMerchant(merchant, currentDevice.getDeviceId());
                            int merchId = currentMerchantId;

                            logger.info("Applying bin-block configurations");
                            device.getBinConfig().forEach(binData -> {

                                if (binData.getMerchantId().equals(merchant.getMerchantId())) {
                                    saveBinConfig(binData, device.getDeviceId(), merchId);
                                    logger.info("BIN Data Updated: |{} | {} |{}", binData.getTransType(), binData.getCardType(), binData.getMerchantId());
                                }
                            });
                            logger.info("BIN-block configuration completed for the merchant: {} | {} ", currentMerchantId, merchant.getMerchantId());

                            MerchantMapper merchantMap = new MerchantMapper();
                            merchantMap.setTempId(merchant.getMerchantId());
                            merchantMap.setMerchantId(currentMerchantId);
                            logger.info("CurrentMerchantId: {} | TempId: {}", currentMerchantId, merchant.getMerchantId());
                            newMerchantMap.add(merchantMap);
                            merchant.setMerchantId(currentMerchantId);
                            logger.info("Merchant Save Successfully...");
                            isNew = true;
                        } else {
                            // update existing merchant
                            logger.info("detect as existing merchant, going to update existing merchant");
                            updateMerchant(merchant, currentDevice.getDeviceId());
                            currentMerchantId = merchant.getMerchantId();
                            logger.info("Merchant Update Successfully...");
                            // remove merchant id from list
                            merchantIds.remove(merchant.getMerchantId());
                            //Update BIN Config
                            //Remove all BIN Config belongs to selected Merchant
                            template.update(DELETE_BIN_CONFIG_BY_MERCHANT_ID,
                                    new MapSqlParameterSource().addValue("merchant_id", merchant.getMerchantId()));
                            logger.info("BIN_CONFIG_FOR_MERCHANT DELETED : {}", merchant.getMerchantId());
                            logger.info("Applying bin-block configurations");
                            device.getBinConfig().forEach(binData -> {

                                if (binData.getMerchantId().equals(merchant.getMerchantId())) {
                                    saveBinConfig(binData, device.getDeviceId(), merchant.getMerchantId());
                                    logger.info("BIN Data Updated: | {} | {} | {}", binData.getTransType(), binData.getCardType(), binData.getMerchantId());
                                }
                            });
                            logger.info("BIN block configuration completed for the merchant: {} | {} ", currentMerchantId, merchant.getMerchantId());

                        }

                        logger.info("Current MerchantId : {}", currentMerchantId);

                        if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                            // update scan prams
                            if (merchant.getScanParam() != null) {
                                if (isNew) {
                                    saveScanParam(merchant.getScanParam(), currentMerchantId);
                                    logger.info("Scan Param Save Successfully...");
                                } else {
                                    Merchant currentMerchant = findMerchantByDeviceIdAndMerchantId(
                                            currentDevice.getDeviceId(), currentMerchantId);
                                    updateScanParam(merchant.getScanParam(), currentMerchant.getMerchantId());
                                    logger.info("Scan Param Update Successfully...");
                                }
                            } else {
                                logger.warn("<<<<< NOT SCAN PARAMS TO UPDATE >>>>>>");
                            }
                        } else if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.AMEX)) {
                            // update amex prams
                            if (merchant.getAmexParam() != null) {
                                if (isNew) {
                                    saveAmexParam(merchant.getAmexParam(), currentMerchantId);
                                    logger.info("Amex Param Save Successfully...");
                                } else {
                                    Merchant currentMerchant = findMerchantByDeviceIdAndMerchantId(
                                            currentDevice.getDeviceId(), currentMerchantId);
                                    updateAmexParam(merchant.getAmexParam(), currentMerchant.getMerchantId());
                                    logger.info("Amex Param Update Successfully...");
                                }
                            } else {
                                logger.warn("<<<<< NOT AMEX PARAMS TO UPDATE >>>>>>");
                            }
                        } else {
                            // not required
                        }
                    });
                } else {
                    logger.warn("<<<<< NOT MERCHANTS TO UPDATE >>>>>>");
                }

                // remove deleted merchants
                if (merchantIds != null && !merchantIds.isEmpty()) {
                    merchantIds.forEach(merchantId -> {
                        //remove all bin config belongs to delete Merchant
                        template.update(DELETE_BIN_CONFIG_BY_MERCHANT_ID,
                                new MapSqlParameterSource().addValue("merchant_id", merchantId));
                        logger.info("bin Configuration delete --> delete merchantId {}", merchantId);

                        template.update(DELETE_SCAN_PRAM,
                                new MapSqlParameterSource().addValue("merchant_id", merchantId));
                        logger.info("SCAN_PRAM DELETED...");

                        template.update(DELETE_AMEX_PRAM,
                                new MapSqlParameterSource().addValue("merchant_id", merchantId));
                        logger.info("AMEX_PRAM DELETED...");

                        //Delete merchant from Profile_Merchant table
                        template.update(DELETE_MERCHANT_FROM_PROFILE_MERCHANT,
                                new MapSqlParameterSource()
                                        .addValue("merchant_id", merchantId));

                        template.update(DELETE_MERCHANT_BY_DEVICE_ID_MERCHANT_ID,
                                new MapSqlParameterSource().addValue("device_id", currentDevice.getDeviceId())
                                        .addValue("merchant_id", merchantId));
                        logger.info("Merchant Id: {} was DELETED", merchantId);
                    });
                }

                logger.info("All done...");
            } else {
                // can't find device
                logger.warn("Can't find device with give serial no");
            }

            logger.info("#profiles available: {}", device.getProfiles().size());
            // update profiles (Amesh Madumalka - 2021/10/08)
            if (device.getProfiles() != null) {
                logger.info("Profile Count: {}", device.getProfiles().size());
                // take current profiles from db accordingly device id
                List<Profile> profileList = findProfileById(deviceId);

                // take profile id list
                List<Integer> profileIdList = findProfileIdByDeviceId(deviceId);

                // find profile Merchants id against profile id
                List<Integer> profileMerchantIdList = new ArrayList<>();
                if (profileIdList != null && !profileIdList.isEmpty()) {
                    profileIdList.forEach(profileId -> {
                        List<Integer> profileMerIdList = findProfileMerIdByProfileId(profileId);
                        profileMerchantIdList.addAll(profileMerIdList);
                    });
                }
                logger.info("Current profile merchant id's : {}", profileMerchantIdList);

                // temp profile merchant list
                List<ProfileMerchant> tempProfileMerList = new ArrayList<>();

                // loop profile list
                device.getProfiles().forEach(profile -> {
                    logger.info("profile ActionType : {}", profile.getAction());
                    logger.info("profile ID : {}", profile.getProfileId());

                    logger.info("profile AddedBy : {} ", profile.getAddedBy());
                    logger.info("profile UpdatedBy : {}", profile.getUpdatedBy());
					
					
					/*Integer isProfAvaialable = profileIdList.
							.filter(profId -> profId.equals(profile.getProfileId())
									)
							.findAny().orElse(null);*/

                    if (!profileIdList.contains(profile.getProfileId().intValue())) {

                        logger.info(":::::Profile ID: {} | STATUS: {} ", profile.getProfileId().intValue(), profile.getAction());

                        if (profile.getAction().equals(ActionType.UPDATE)) {
                            profile.setAction(ActionType.INSERT);
                        }
                    }

                    /*
                     * if (isProfAvaialable!=null) { if
                     * (profile.getProfileId().equals(isProfAvaialable)) {
                     *
                     * } }
                     */

                    profile.setAddedDate(DateUtil.getCurrentTime());
                    profile.setLastUpdate(DateUtil.getCurrentTime());
                    //profile.setMerchantName(device.getMerchantName());
                    //profile.setMerchantAdrs(device.getMerchantAddress());
                    device.setAddedDate(DateUtil.getCurrentTime());
                    device.setAddedBy(profile.getAddedBy());
                    //device.setAddedDate(DateUtil.getCurrentTime());
                    //device.setAddedBy(profile.getAddedBy());
                    if (!StringUtil.isNullOrWhiteSpace(profile.getAction())
                            && profile.getAction().equalsIgnoreCase(ActionType.INSERT)) {

                        // save newly added profiles
                        int newProfileId = saveProfile(profile, device.getDeviceId());
                        profile.setProfileId(newProfileId);
                        // add profile merchants to temp list
                        profile.getProfileMerchants()
                                .forEach(profileMerchant -> profileMerchant.setProfileId(newProfileId));
                        tempProfileMerList.addAll(profile.getProfileMerchants());
                        logger.info("Newly added profile : {}", newProfileId);

                    } else if (!StringUtil.isNullOrWhiteSpace(profile.getAction())
                            && profile.getAction().equalsIgnoreCase(ActionType.UPDATE)) {
                        logger.info("Update profile");
                        logger.info("profileList Count: {}", profileList.size());
                        // update existing profiles
                        profileList.forEach(existProfile -> {
                            logger.info("existing Profile ID: {} | Profile ID: {}", existProfile.getProfileId().intValue(), profile.getProfileId().intValue());
                            if (existProfile.getProfileId().intValue() == profile.getProfileId().intValue()) {
                                logger.info("get existProfile ProfileName: {}", existProfile.getProfileName());
                                // update profile
                                updateProfile(profile, device.getDeviceId(), existProfile.getProfileId());
                                logger.info("profileIdList: {}", profileIdList.size());
                                // remove that merchant from merchant list
                                if (profileIdList != null && !profileIdList.isEmpty()) {
                                    profileIdList.remove(existProfile.getProfileId());
                                }
                                logger.info("profileIdList after remove: {}", profileIdList.size());
                                // add profile merchants to temp list
                                logger.info("profile merchants: {}", profile.getProfileMerchants().size());
                                tempProfileMerList.addAll(profile.getProfileMerchants());
                                logger.info("Update existing profile");
                            }
                        });

                    } else {
                        logger.warn("Profile hasn't any action");
                    }
                });

                // remove profile merchant mapping table
                if (profileMerchantIdList != null && !profileMerchantIdList.isEmpty()) {
                    profileMerchantIdList.forEach(profileMerId -> {
                        if (profileMerId != null && profileMerId > 0) {
                            deleteProfileMerchantById(profileMerId);
                            logger.info("Deleted profile merchant {} :", profileMerId);
                        }
                    });
                } else {
                    logger.warn("No data in profile merchant mapping table");
                }

                // save new profile merchants
                if (tempProfileMerList != null && !tempProfileMerList.isEmpty()) {
                    tempProfileMerList.forEach(profileMerchant -> {

                        MerchantMapper mp = newMerchantMap.stream()
                                .filter(merchnt -> profileMerchant.getMerchantId().equals(merchnt.getTempId()))
                                .findAny().orElse(null);
                        if (mp != null) {
                            profileMerchant.setMerchantId(mp.getMerchantId());
                        }
                        profileMerchant.setAddedDate(device.getAddedDate());
                        profileMerchant.setAddedBy(device.getAddedBy());
                        profileMerchant.setLastUpdate(device.getLastUpdate());
                        profileMerchant.setUpdatedBy(device.getUpdatedBy());
                        saveProfMerchant(profileMerchant, profileMerchant.getProfileId());
                        logger.info("save new profile merchants");
                    });
                }

                // remove profile (if deleted by front end)
                if (profileIdList != null && !profileIdList.isEmpty()) {
                    logger.warn("Profile available to delete");
                    profileIdList.forEach(this::deleteProfileById);
                } else {
                    logger.warn("No profiles to deleted");
                }

            } else {
                logger.warn("No profiles to update");
            }
        } else if (device.getAction().equals(ActionType.TOKEN_UPDATE)) {
            // update token by serial no
            logger.info("update token by serial no");
            logger.info("serial no: {}", device.getSerialNo());
            deviceId = updateDeviceToken(device, device.getSerialNo());
            logger.info("return device id: {} ", deviceId);

        } else if (device.getAction().equals(ActionType.STATE_UPDATE)) {
            // update token by serial no
            logger.info("update state by serial no");
            logger.info("device serial no: {}", device.getSerialNo());
            logger.info("new status: {}", device.getStatus());
            deviceId = updateDeviceStatus(device, device.getSerialNo());
            logger.info("return device id:- {}", deviceId);

        } else {
            logger.warn("undefined action type");
        }

        logger.info("################## END UPDATE ##################");

        return deviceId;
    }

    public Integer syncProfiles(Device device) throws IllegalArgumentException {
        logger.info("################## START SYNC  ##################");

        if (device == null) {
            logger.info("Device Profile Data Not Found");
            return null;
        }

        logger.info("Action: {}", device.getAction());
        if (device.getAction() == null)
            return null;
        List<MerchantMapper> newMerchantMap = new ArrayList<>();
        Integer deviceId = device.getDeviceId();
        Device currentDevice = findDeviceById(deviceId);

        if (device.getAction().equals(ActionType.UPDATE)) {
            logger.info("Update Request [Serial No] : {}", device.getSerialNo());
            logger.info("Device id:- {}", deviceId);
            // find device in db
            // Device currentDevice = findDeviceById(deviceId);

            if (currentDevice != null && currentDevice.getDeviceId() != null) {
                logger.info("Found device in db");
                // update device
                updateDevice(device, deviceId);
                logger.info("Device Update Successfully...");

                List<Long> tids = findMerchantTidsByDeviceId(deviceId);
                logger.info("Current tid list for this device: {}", tids);

                List<Integer> merchantIds = findMerchantIdsByDeviceId(deviceId);
                logger.info("Current merchantId list for this device: {}", merchantIds);

                if (device.getMerchants() != null && !device.getMerchants().isEmpty()) {
                    logger.info("Update Request [No. of Merchants]: {}", device.getMerchants().size());

                    // process merchants
                    device.getMerchants().forEach(merchant -> {
                        logger.info("Merchant ID: {}", merchant.getMerchantId());
                        logger.info("Merchant Type: {}", merchant.getCategory());
                        logger.info("MID : {}", merchant.getMid());
                        logger.info("TID : {}", merchant.getTid());
                        logger.info("Action : {}", merchant.getAction());

                        boolean isNew = false;
                        int currentMerchantId = 0;

                        if (merchant.getAction() != null && merchant.getAction().equals(ActionType.INSERT)) {
                            // save merchant, detect as new merchant
                            logger.info("detect as new merchant, going to insert as new merchant");
                            currentMerchantId = saveMerchant(merchant, currentDevice.getDeviceId());
                            int merchId = currentMerchantId;

                            logger.info("Applying BIN Block Configurations");
                            device.getBinConfig().forEach(binData -> {

                                if (binData.getMerchantId().equals(merchant.getMerchantId())) {
                                    saveBinConfig(binData, device.getDeviceId(), merchId);
                                    logger.info("BIN Data Updated: | {} | {} | {} ", binData.getTransType(), binData.getCardType(), binData.getMerchantId());
                                }
                            });
                            logger.info("BIN Block Configuration Completed for the merchant: {} | {} ", currentMerchantId, merchant.getMerchantId());

                            MerchantMapper merchantMap = new MerchantMapper();
                            merchantMap.setTempId(merchant.getMerchantId());
                            merchantMap.setMerchantId(currentMerchantId);
                            logger.info("CurrentMerchantId: {} | TempId: {} ", currentMerchantId, merchant.getMerchantId());
                            newMerchantMap.add(merchantMap);
                            merchant.setMerchantId(currentMerchantId);
                            logger.info("Merchant Save Successfully...");
                            isNew = true;
                        } else {
                            // update existing merchant
                            logger.info("detect as existing merchant, going to update existing merchant");
                            updateMerchant(merchant, currentDevice.getDeviceId());
                            currentMerchantId = merchant.getMerchantId();
                            logger.info("Merchant Update Successfully...");
                            // remove merchant id from list
                            merchantIds.remove(merchant.getMerchantId());
                            //Update BIN Config
                            //Remove all BIN Config belongs to selected Merchant
                            template.update(DELETE_BIN_CONFIG_BY_MERCHANT_ID,
                                    new MapSqlParameterSource().addValue("merchant_id", merchant.getMerchantId()));
                            logger.info("BIN_CONFIG_FOR_MERCHANT DELETED : {}", merchant.getMerchantId());
                            logger.info("Applying BIN Block Configurations");
                            device.getBinConfig().forEach(binData -> {

                                if (binData.getMerchantId().equals(merchant.getMerchantId())) {
                                    saveBinConfig(binData, device.getDeviceId(), merchant.getMerchantId());
                                    logger.info("BIN Data Updated: | {} | {} | {} ", binData.getTransType(), binData.getCardType(), binData.getMerchantId());
                                }
                            });
                            logger.info("BIN Block Configuration Completed for the merchant: {} | {} ", currentMerchantId, merchant.getMerchantId());

                        }

                        logger.info("Current MerchantId : {}", currentMerchantId);

                        if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.QR)) {
                            // update scan prams
                            if (merchant.getScanParam() != null) {
                                if (isNew) {
                                    saveScanParam(merchant.getScanParam(), currentMerchantId);
                                    logger.info("Scan Param Save Successfully...");
                                } else {
                                    Merchant currentMerchant = findMerchantByDeviceIdAndMerchantId(
                                            currentDevice.getDeviceId(), currentMerchantId);
                                    updateScanParam(merchant.getScanParam(), currentMerchant.getMerchantId());
                                    logger.info("Scan Param Update Successfully...");
                                }
                            } else {
                                logger.warn("<<<<< NOT SCAN PARAMS TO UPDATE >>>>>>");
                            }
                        } else if (merchant.getCategory().equalsIgnoreCase(MerchantTypes.AMEX)) {
                            // update amex prams
                            if (merchant.getAmexParam() != null) {
                                if (isNew) {
                                    saveAmexParam(merchant.getAmexParam(), currentMerchantId);
                                    logger.info("Amex Param Save Successfully...");
                                } else {
                                    Merchant currentMerchant = findMerchantByDeviceIdAndMerchantId(
                                            currentDevice.getDeviceId(), currentMerchantId);
                                    updateAmexParam(merchant.getAmexParam(), currentMerchant.getMerchantId());
                                    logger.info("Amex Param Update Successfully...");
                                }
                            } else {
                                logger.warn("<<<<< NOT AMEX PARAMS TO UPDATE >>>>>>");
                            }
                        } else {
                            // not required
                        }
                    });
                } else {
                    logger.warn("<<<<< NOT MERCHANTS TO UPDATE >>>>>>");
                }

                // remove deleted merchants
                if (merchantIds != null && !merchantIds.isEmpty()) {
                    merchantIds.forEach(merchantId -> {

                        template.update(DELETE_SCAN_PRAM,
                                new MapSqlParameterSource().addValue("merchant_id", merchantId));
                        logger.info("SCAN_PRAM DELETED");

                        template.update(DELETE_AMEX_PRAM,
                                new MapSqlParameterSource().addValue("merchant_id", merchantId));
                        logger.info("AMEX_PRAM DELETED");

                        //Delete merchant from Profile_Merchant table
                        template.update(DELETE_MERCHANT_FROM_PROFILE_MERCHANT,
                                new MapSqlParameterSource()
                                        .addValue("merchant_id", merchantId));

                        template.update(DELETE_MERCHANT_BY_DEVICE_ID_MERCHANT_ID,
                                new MapSqlParameterSource().addValue("device_id", currentDevice.getDeviceId())
                                        .addValue("merchant_id", merchantId));
                        logger.info("Merchant Id: {} was DELETED", merchantId);
                    });
                }

                logger.info("All done...");
            } else {
                // can't find device
                logger.warn("Can't find device with give serial no");
            }

            logger.info("#profiles available: {}", device.getProfiles().size());
            // update profiles (Amesh Madumalka - 2021/10/08)
            if (device.getProfiles() != null) {
                logger.info("Profile Count: {}", device.getProfiles().size());
                // take current profiles from db accordingly device id
                List<Profile> profileList = findProfileById(deviceId);

                // take profile id list
                List<Integer> profileIdList = findProfileIdByDeviceId(deviceId);

                // find profile Merchants id against profile id
                List<Integer> profileMerchantIdList = new ArrayList<>();
                if (profileIdList != null && !profileIdList.isEmpty()) {
                    profileIdList.forEach(profileId -> {
                        List<Integer> profileMerIdList = findProfileMerIdByProfileId(profileId);
                        profileMerchantIdList.addAll(profileMerIdList);
                    });
                }
                logger.info("Current profile merchant id's : {}", profileMerchantIdList);

                // temp profile merchant list
                List<ProfileMerchant> tempProfileMerList = new ArrayList<>();

                // loop profile list
                device.getProfiles().forEach(profile -> {
                    logger.info("profile ActionType : {}", profile.getAction());
                    logger.info("profile ID : {}", profile.getProfileId());

                    logger.info("profile AddedBy : {}", profile.getAddedBy());
                    logger.info("profile UpdatedBy : {}", profile.getUpdatedBy());


					/*Integer isProfAvaialable = profileIdList.
							.filter(profId -> profId.equals(profile.getProfileId())
									)
							.findAny().orElse(null);*/

                    if (!profileIdList.contains(profile.getProfileId().intValue())) {

                        logger.info(":::::Profile ID: {} | STATUS: {} ", profile.getProfileId().intValue(), profile.getAction());

                        if (profile.getAction().equals(ActionType.UPDATE)) {
                            profile.setAction(ActionType.INSERT);
                        }
                    }

                    /*
                     * if (isProfAvaialable!=null) { if
                     * (profile.getProfileId().equals(isProfAvaialable)) {
                     *
                     * } }
                     */

                    profile.setAddedDate(DateUtil.getCurrentTime());
                    profile.setLastUpdate(DateUtil.getCurrentTime());
                    //profile.setMerchantName(device.getMerchantName());
                    //profile.setMerchantAdrs(device.getMerchantAddress());
                    device.setAddedDate(DateUtil.getCurrentTime());
                    device.setAddedBy(profile.getAddedBy());
                    //device.setAddedDate(DateUtil.getCurrentTime());
                    //device.setAddedBy(profile.getAddedBy());
                    if (!StringUtil.isNullOrWhiteSpace(profile.getAction())
                            && profile.getAction().equalsIgnoreCase(ActionType.INSERT)) {

                        // save newly added profiles
                        int newProfileId = saveProfile(profile, device.getDeviceId());
                        profile.setProfileId(newProfileId);
                        // add profile merchants to temp list
                        profile.getProfileMerchants()
                                .forEach(profileMerchant -> profileMerchant.setProfileId(newProfileId));
                        tempProfileMerList.addAll(profile.getProfileMerchants());
                        logger.info("Newly added profile : {} ", newProfileId);

                    } else if (!StringUtil.isNullOrWhiteSpace(profile.getAction())
                            && profile.getAction().equalsIgnoreCase(ActionType.UPDATE)) {
                        logger.info("Update profile");
                        logger.info("profileList Count: {}", profileList.size());
                        // update existing profiles
                        profileList.forEach(existProfile -> {
                            logger.info("existing Profile ID: {} | Profile ID: {}", existProfile.getProfileId().intValue(), profile.getProfileId().intValue());
                            if (existProfile.getProfileId().intValue() == profile.getProfileId().intValue()) {
                                logger.info("get existProfile ProfileName: {}", existProfile.getProfileName());
                                // update profile
                                updateProfile(profile, device.getDeviceId(), existProfile.getProfileId());
                                logger.info("profileIdList: {}", profileIdList.size());
                                // remove that merchant from merchant list
                                if (profileIdList != null && !profileIdList.isEmpty()) {
                                    profileIdList.remove(existProfile.getProfileId());
                                }
                                logger.info("profileIdList after remove: {}", profileIdList.size());
                                // add profile merchants to temp list
                                logger.info("profile merchants: {}", profile.getProfileMerchants().size());
                                tempProfileMerList.addAll(profile.getProfileMerchants());
                                logger.info("Update existing profile");
                            }
                        });

                    } else {
                        logger.warn("Profile hasn't any action");
                    }
                });

                // remove profile merchant mapping table
                if (profileMerchantIdList != null && !profileMerchantIdList.isEmpty()) {
                    profileMerchantIdList.forEach(profileMerId -> {
                        if (profileMerId != null && profileMerId > 0) {
                            deleteProfileMerchantById(profileMerId);
                            logger.info("Deleted profile merchant :{}", profileMerId);
                        }
                    });
                } else {
                    logger.warn("No data in profile merchant mapping table");
                }

                // save new profile merchants
                if (tempProfileMerList != null && !tempProfileMerList.isEmpty()) {
                    tempProfileMerList.forEach(profileMerchant -> {

                        MerchantMapper mp = newMerchantMap.stream()
                                .filter(merchnt -> profileMerchant.getMerchantId().equals(merchnt.getTempId()))
                                .findAny().orElse(null);
                        if (mp != null) {
                            profileMerchant.setMerchantId(mp.getMerchantId());
                        }
                        profileMerchant.setAddedDate(device.getAddedDate());
                        profileMerchant.setAddedBy(device.getAddedBy());
                        profileMerchant.setLastUpdate(device.getLastUpdate());
                        profileMerchant.setUpdatedBy(device.getUpdatedBy());
                        saveProfMerchant(profileMerchant, profileMerchant.getProfileId());
                        logger.info("save new profile merchants");
                    });
                }

                // remove profile (if deleted by front end)
                if (profileIdList != null && !profileIdList.isEmpty()) {
                    logger.warn("Profile available to delete");
                    profileIdList.forEach(this::deleteProfileById);
                } else {
                    logger.warn("No profiles to deleted");
                }

            } else {
                logger.warn("No profiles to update");
            }
        } else if (device.getAction().equals(ActionType.TOKEN_UPDATE)) {
            // update token by serial no
            logger.info("update token by serial no");
            logger.info("serial no: {}", device.getSerialNo());
            deviceId = updateDeviceToken(device, device.getSerialNo());
            logger.info("return device id: {}", deviceId);

        } else if (device.getAction().equals(ActionType.STATE_UPDATE)) {
            // update token by serial no
            logger.info("update state by serial no");
            logger.info("device serial no: {}", device.getSerialNo());
            logger.info("new status: {}", device.getStatus());
            deviceId = updateDeviceStatus(device, device.getSerialNo());
            logger.info("return device id: {}", deviceId);

        } else {
            logger.warn("undefined action type");
        }

        logger.info("################## END UPDATE ##################");

        return deviceId;
    }

    @Override
    public List<Integer> updateAll(List<Device> models) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer deleteById(Integer deviceId) throws IllegalArgumentException {
        List<Integer> profileIds = findProfileIdByDeviceId(deviceId);
        if (profileIds != null && !profileIds.isEmpty()) {
            for (Integer profileId : profileIds) {
                deleteProfileById(profileId);
                logger.info("profile Deleted: {}", profileId);
            }
        }
        List<Integer> merchantIds = findMerchantIdsByDeviceId(deviceId);
        logger.info("Merchant Ids: {}", merchantIds);

        if (merchantIds != null && !merchantIds.isEmpty()) {
            for (Integer merchantId : merchantIds) {
                logger.info("Merchant Id: {}", merchantId);
                template.update(DELETE_SCAN_PRAM, new MapSqlParameterSource().addValue("merchant_id", merchantId));
                logger.info("SCAN_PRAM DELETED");

                template.update(DELETE_AMEX_PRAM, new MapSqlParameterSource().addValue("merchant_id", merchantId));
                logger.info("AMEX_PRAM DELETED");

                template.update(DELETE_MERCHANT_BY_MERCHANT_ID,
                        new MapSqlParameterSource().addValue("merchant_id", merchantId));
                logger.info("MERCHANT DELETED");
            }
        }
        template.update(DELETE_BIN_CONFIG_BY_DEVICE_ID, new MapSqlParameterSource().addValue("device_id", deviceId));
        logger.info("BIN Config deleted");

        template.update(DELETE_DEVICE, new MapSqlParameterSource().addValue("device_id", deviceId));
        logger.info("DEVICE DELETED");

        return deviceId;
    }

    @Override
    public Integer delete(Device entity) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> deleteAll(List<Device> models) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> deleteAll() {
        // TODO Auto-generated method stub
        return null;
    }

    // find all devices
    @Override
    public List<Device> findAll() {
        return template.query(FIND_ALL_DEVICES, (rs, rowNum) -> {

            Device device = new Device();
            device.setDeviceId(rs.getInt("device_id"));
            device.setSerialNo(rs.getString("serial_no"));

            String actualToken = rs.getString("token");
            boolean isTokenError = false;
            try {
                if (actualToken != null && !actualToken.isEmpty() && actualToken.length() >= 10) {
                    device.setToken("..." + actualToken.substring((actualToken.length() - 8), actualToken.length()));
                } else {
                    isTokenError = true;
                }
            } catch (Exception e) {
                logger.error("Token Split Error: {}", e.getMessage());
                isTokenError = true;
            }
            if (isTokenError) {
                device.setToken(actualToken);
            }

            device.setBankCode(rs.getString("bank_code"));
            device.setBankName(rs.getString("bank_name"));
            device.setMerchantName(rs.getString("merchant_name"));
            device.setMerchantAddress(rs.getString("merchant_address"));
            device.setAddedDate(rs.getString("added_date"));
            device.setAddedBy(rs.getString("added_by"));
            device.setLastUpdate(rs.getString("last_update"));
            device.setUpdatedBy(rs.getString("updated_by"));
            device.setStatus(rs.getString("status"));
            device.setMobileNo(rs.getString("mobile_no"));
            device.setSimNo(rs.getString("sim_no"));
            device.setCustContactNo(rs.getString("cust_contact_no"));
            device.setRemark(rs.getString("remark"));

            return device;
        });
    }

    // find all devices by MID and TID
    public List<Device> findAllForGivenTidOrMid(String mid, String tid) {
        return template.query(SELECT_DEVICE_BELONGS_TO_TID_OR_MID,
                new MapSqlParameterSource().addValue("mid", mid).addValue("tid", tid), (rs, rowNum) -> {
                    Device device = new Device();
                    device.setDeviceId(rs.getInt("device_id"));
                    device.setSerialNo(rs.getString("serial_no"));

                    String actualToken = rs.getString("token");
                    boolean isTokenError = false;
                    try {
                        if (actualToken != null && !actualToken.isEmpty() && actualToken.length() >= 10) {
                            device.setToken(
                                    "..." + actualToken.substring((actualToken.length() - 8), actualToken.length()));
                        } else {
                            isTokenError = true;
                        }
                    } catch (Exception e) {
                        logger.error("Token Split Error: {}", e.getMessage());
                        isTokenError = true;
                    }
                    if (isTokenError) {
                        device.setToken(actualToken);
                    }
                    device.setBankCode(rs.getString("bank_code"));
                    device.setBankName(rs.getString("bank_name"));
                    device.setMerchantName(rs.getString("merchant_name"));
                    device.setMerchantAddress(rs.getString("merchant_address"));
                    device.setAddedDate(rs.getString("added_date"));
                    device.setAddedBy(rs.getString("added_by"));
                    device.setLastUpdate(rs.getString("last_update"));
                    device.setUpdatedBy(rs.getString("updated_by"));
                    device.setStatus(rs.getString("status"));
                    device.setMobileNo(rs.getString("mobile_no"));
                    device.setSimNo(rs.getString("sim_no"));
                    device.setCustContactNo(rs.getString("cust_contact_no"));
                    return device;
                });
    }

    // find all devices by any key
    public List<Device> findAllByParam(String param) {
        return template.query(FIND_DEVICE_BY_PARAM, new MapSqlParameterSource().addValue("param", param),
                (rs, rowNum) -> {
                    Device device = new Device();
                    device.setDeviceId(rs.getInt("device_id"));
                    device.setSerialNo(rs.getString("serial_no"));
                    device.setBankCode(rs.getString("bank_code"));
                    device.setBankName(rs.getString("bank_name"));
                    device.setMerchantName(rs.getString("merchant_name"));
                    device.setMerchantAddress(rs.getString("merchant_address"));
                    device.setAddedDate(rs.getString("added_date"));
                    device.setAddedBy(rs.getString("added_by"));
                    device.setLastUpdate(rs.getString("last_update"));
                    device.setUpdatedBy(rs.getString("updated_by"));
                    device.setStatus(rs.getString("status"));
                    device.setMobileNo(rs.getString("mobile_no"));
                    device.setSimNo(rs.getString("sim_no"));
                    device.setCustContactNo(rs.getString("cust_contact_no"));
                    return device;
                });
    }

    @Override
    public List<Device> findAllById(List<String> ids) throws IllegalArgumentException {
        return null;
    }

    // find device by device id
    @Override
    public Optional<Device> findById(Integer deviceId) throws IllegalArgumentException {

        logger.info("Device ID: {}", deviceId);
        // find device
        return Optional.of((Device) template.queryForObject(FIND_DEVICE_BY_DEVICE_ID,

                new MapSqlParameterSource().addValue("device_id", deviceId),

                (rs1, rowNum1) -> {
                    List<Merchant> merchants = new ArrayList<>();
                    try {
                        // find merchants
                        merchants = template.query(FIND_MERCHANTS_BY_DEVICE_ID,
                                new MapSqlParameterSource().addValue("device_id", deviceId),

                                (rs2, rowNum2) -> {
                                    Merchant merchant = new Merchant();

                                    int merchantId = rs2.getInt("merchant_id");
                                    String merchantType = rs2.getString("category");
                                    logger.info("Merchant Id  : {}", merchantId);
                                    logger.info("Merchant Type: {}", merchantType);

                                    if (merchantType.equalsIgnoreCase(MerchantTypes.QR)) {
                                        try {
                                            // find scan param
                                            ScanParam scanParam = template.queryForObject(
                                                    FIND_SCAN_PARAM_BY_MERCHANT_ID,
                                                    new MapSqlParameterSource().addValue("merchant_id", merchantId),
                                                    (rs3, rowNum3) -> {

                                                        return new ScanParam(rs3.getInt("scan_param_id"),
                                                                rs3.getString("m_user_id"), rs3.getString("m_password"),
                                                                rs3.getString("checksum_key"), rs3.getString("vid"),
                                                                rs3.getString("cid"),
                                                                rs3.getBoolean("is_qr_ref_id"));
                                                    });
                                            // set Q+
                                            merchant.setScanParam(scanParam);
                                        } catch (EmptyResultDataAccessException e2) {
                                            logger.warn("Error Loading ScanParam");
                                        }
                                    } else if (merchantType.equalsIgnoreCase(MerchantTypes.AMEX)) {
                                        try {
                                            // find amex param
                                            AmexParam amexParam = template.queryForObject(
                                                    FIND_AMEX_PARAM_BY_MERCHANT_ID,
                                                    new MapSqlParameterSource().addValue("merchant_id", merchantId),
                                                    (rs4, rowNum4) -> {

                                                        return new AmexParam(rs4.getString("amex_ip"));
                                                    });
                                            // set Amex
                                            merchant.setAmexParam(amexParam);
                                        } catch (EmptyResultDataAccessException e2) {
                                            logger.warn("Error Loading AmexParam");
                                        }
                                    } else {
                                        // not required
                                    }
                                    // build merchant
                                    merchant.setMerchantId(merchantId);
                                    merchant.setCategory(merchantType);
                                    merchant.setMonth(rs2.getInt("month"));
                                    merchant.setMid(rs2.getString("mid"));
                                    merchant.setTid(rs2.getString("tid"));
                                    merchant.setCurrency(rs2.getString("currency"));
                                    merchant.setDescription(rs2.getString("description"));
                                    merchant.setMinAmount(rs2.getBigDecimal("min_amount"));
                                    merchant.setMaxAmount(rs2.getBigDecimal("max_amount"));
                                    merchant.setVoidTx(rs2.getBoolean("void_tx"));
                                    merchant.setAmexTx(rs2.getBoolean("amex_tx"));
                                    merchant.setDcc(rs2.getBoolean("dcc"));
                                    merchant.setPreAuth(rs2.getBoolean("pre_auth"));
                                    merchant.setOffline(rs2.getBoolean("offline"));
                                    merchant.setMerchantType(rs2.getString("merchant_type"));
                                    merchant.setJcb(rs2.getBoolean("jcb"));
                                    merchant.setLocalMer(rs2.getBoolean("is_local"));
                                    merchant.setForeignMer(rs2.getBoolean("is_foreign"));
                                    merchant.setOnUs(rs2.getBoolean("is_on_us"));
                                    merchant.setOffUs(rs2.getBoolean("is_off_us"));
                                    merchant.setIphoneImei(rs2.getBoolean("iphone_imei"));

                                    return merchant;
                                });
                    } catch (EmptyResultDataAccessException e) {
                        logger.warn("Error Loading Merchants");
                    }

                    // Find Bin Configs for each merchant
                    List<BinConfig> binCongfigs = new ArrayList<>();
                    try {
                        // find merchants
                        binCongfigs = template.query(FIND_BIN_CONFIG_BY_DEVICE_ID,
                                new MapSqlParameterSource().addValue("device_id", deviceId),

                                (rs5, rowNum5) -> {
                                    BinConfig bConfig = new BinConfig();

//									BinConfig bConfig = new BinConfig();
                                    //bConfig.setAction()
                                    bConfig.setAction(rs5.getString("action"));
                                    bConfig.setBinEnd(rs5.getString("bin_end"));
                                    bConfig.setBinStartFrom(rs5.getString("bin_start"));
                                    bConfig.setCardType(rs5.getString("card_type"));
                                    bConfig.setFormId(rs5.getInt("config_id"));
                                    bConfig.setMerchantId(rs5.getInt("merchant_id"));
                                    bConfig.setMid(rs5.getString("mid"));
                                    bConfig.setTid(rs5.getString("tid"));
                                    bConfig.setTransType(rs5.getString("tranx_type"));
                                    bConfig.setType(rs5.getString("config_type"));

                                    return bConfig;
                                });
                    } catch (EmptyResultDataAccessException e) {
                        logger.warn("Error Loading Merchants");
                    }

                    // build device
                    Device device = new Device();
                    device.setDeviceId(rs1.getInt("device_id"));
                    device.setSerialNo(rs1.getString("serial_no"));
                    device.setToken(rs1.getString("token"));
                    device.setBankCode(rs1.getString("bank_code"));
                    device.setBankName(rs1.getString("bank_name"));
                    device.setMerchantName(rs1.getString("merchant_name"));
                    device.setMerchantAddress(rs1.getString("merchant_address"));
                    device.setAddedDate(rs1.getString("added_date"));
                    device.setAddedBy(rs1.getString("added_by"));
                    device.setLastUpdate(rs1.getString("last_update"));
                    device.setUpdatedBy(rs1.getString("updated_by"));
                    device.setStatus(rs1.getString("status"));
                    device.setVisaNoCvmLimit(rs1.getBigDecimal("visa_no_cvm_limit"));
                    device.setCntactlsTrxnLimit(rs1.getBigDecimal("cntactls_trxn_limit"));
                    device.setAutoSettle(rs1.getBoolean("auto_settle"));
                    device.setAutoSettleTime(rs1.getString("auto_settle_time"));
                    device.setForceSettle(rs1.getBoolean("force_settle"));
                    device.setEcr(rs1.getBoolean("ecr"));
                    device.setKeyIn(rs1.getBoolean("key_in"));
                    device.setMobileNo(rs1.getString("mobile_no"));
                    device.setSimNo(rs1.getString("sim_no"));
                    device.setCustContactNo(rs1.getString("cust_contact_no"));
                    device.setRemark(rs1.getString("remark"));
                    device.setEcrQr(rs1.getBoolean("ecr_qr"));
                    device.setSignature(rs1.getBoolean("signature"));
                    device.setDebugMode(rs1.getBoolean("debug_mode"));
                    device.setNoSettle(rs1.getBoolean("no_settle"));
                    device.setActivityTracker(rs1.getBoolean("activity_tracker"));
                    device.setQrRefund(rs1.getBoolean("qr_refund"));
                    device.setReversalHistory(rs1.getBoolean("reversal_history"));
                    device.setEnableAmex(rs1.getBoolean("enable_amex"));
                    device.setPushNotification(rs1.getBoolean("push_notification"));
                    device.setPreAuth(rs1.getBoolean("pre_auth"));
                    device.setDcc(rs1.getBoolean("dcc"));
                    device.setOffline(rs1.getBoolean("offline"));
                    device.setKeyInAmex(rs1.getBoolean("key_in_amex"));
                    device.setPopupMsg(rs1.getBoolean("popup_msg"));

                    device.setCardTypeValidation(rs1.getBoolean("card_type_val"));
                    device.setSaleReceipt(rs1.getBoolean("sale_receipt"));
                    device.setCurrencyFromBin(rs1.getBoolean("curr_from_bin"));
                    device.setCurrencyFromCard(rs1.getBoolean("curr_from_card"));
                    device.setProceedWithLkr(rs1.getBoolean("prcd_with_lkr"));
                    device.setCardTap(rs1.getBoolean("card_tap"));
                    device.setCardInsert(rs1.getBoolean("card_insert"));
                    device.setCardSwipe(rs1.getBoolean("card_swipe"));
                    device.setEcrIp(rs1.getString("ecr_ip"));
                    device.setEcrPort(rs1.getString("ecr_port"));
                    device.setEcrAuthToken(rs1.getBoolean("ecr_auth_token"));
                    device.setTranToSim(rs1.getBoolean("tran_to_sim"));
                    device.setEcrWifi(rs1.getBoolean("ecr_wifi"));


                    device.setDccPayload(rs1.getBoolean("dcc_payload"));
                    device.setNetwork(rs1.getString("network"));
                    device.setMerchantPortal(rs1.getBoolean("merchant_portal"));
                    device.setResendVoid(rs1.getBoolean("resend_void_sms"));
                    device.setClientCredentials(rs1.getBoolean("live_client"));
                    device.setPrintReceipt(rs1.getBoolean("print_receipt"));
                    device.setAutoReversal(rs1.getBoolean("auto_reversal"));
                    device.setPrintless(rs1.getBoolean("printless_mode"));

                    device.setLkrDefaultCurr(rs1.getBoolean("lkr_default_curr"));
                    device.setVoidPwd(rs1.getString("void_pwd")); //diff_sale_mid_tid
                    device.setDiffSaleMidTid(rs1.getBoolean("diff_sale_mid_tid"));
                    device.setMkiPreAuth(rs1.getBoolean("mki_pre_auth"));
                    device.setMkiOffline(rs1.getBoolean("mki_offline"));
                    device.setSignPriority(rs1.getBoolean("sing_priority"));
                    device.setBlockMag(rs1.getBoolean("block_mag"));
                    device.setCustomerReceipt(rs1.getBoolean("cust_receipt"));
                    device.setMidTidSeg(rs1.getBoolean("is_seg"));
                    device.setEventAutoUpdate(rs1.getBoolean("auto_update"));
                    //device.setSimNo(rs1.getString("sim_no"));
                    device.setNewVoidPwd(rs1.getString("new_void_pwd"));
                    device.setNewSettlementPwd(rs1.getString("new_sattle_pwd"));
                    device.setImeiScan(rs1.getBoolean("imei_scan"));
                    device.setCommission(rs1.getBoolean("commission"));
                    device.setCommissionRate(rs1.getFloat("commission_rate"));
                    device.setOfflineUser(rs1.getString("offline_users"));
                    device.setEnableSanhindaPay(rs1.getBoolean("sanhinda_pay"));
                    device.setRefNumberEnable(rs1.getBoolean("ref_no"));
                    device.setTleProfilesEnable(rs1.getBoolean("tle_profile"));


                    logger.info("Repo Live Client: {}", device.isClientCredentials());
                    // set merchants
                    if (merchants != null)
                        logger.info("No of Merchants: {}", merchants.size());
                    device.setMerchants(merchants);

                    // set BIN Rules
                    if (binCongfigs != null)
                        logger.info("No of binCongfigs: {}", binCongfigs.size());
                    device.setBinConfig(binCongfigs);

                    // set profiles (Amesh Madumalka 2021-10-07)
                    List<Profile> profileList = findProfileById(deviceId);
                    // Set profile merchants into profile
                    List<Profile> newProfileList = new ArrayList<Profile>();
                    if (profileList != null && !profileList.isEmpty()) {
                        profileList.forEach(profile -> {
                            List<ProfileMerchant> profileMerchant = findProfMerchantById(profile.getProfileId());
                            if (profileMerchant != null && !profileMerchant.isEmpty()) {
                                profile.setProfileMerchants(profileMerchant);
                            }
                            newProfileList.add(profile);
                        });
                        device.setProfiles(newProfileList);
                    }

                    // return device with all childs
                    return device;
                }));
    }

    // find device by device id
    @Override
    public Optional<Device> findByKey(String key) throws IllegalArgumentException {

        logger.info("Device Serial No: {}", key);
        // find device
        return Optional.of((Device) template.queryForObject(FIND_DEVICE_BY_SERIAL_NO,

                new MapSqlParameterSource().addValue("serial_no", key),

                (rs1, rowNum1) -> {
                    int deviceId = rs1.getInt("device_id");
                    logger.info("Device ID: {}", deviceId);
                    List<Merchant> merchants = new ArrayList<>();
                    try {
                        // find merchants
                        merchants = template.query(FIND_MERCHANTS_BY_DEVICE_ID,
                                new MapSqlParameterSource().addValue("device_id", deviceId),

                                (rs2, rowNum2) -> {
                                    Merchant merchant = new Merchant();

                                    int merchantId = rs2.getInt("merchant_id");
                                    String merchantType = rs2.getString("category");
                                    logger.info("Merchant Id  : " + merchantId);
                                    logger.info("Merchant Type: " + merchantType);

                                    if (merchantType.equalsIgnoreCase(MerchantTypes.QR)) {
                                        try {
                                            // find scan param
                                            ScanParam scanParam = template.queryForObject(
                                                    FIND_SCAN_PARAM_BY_MERCHANT_ID,
                                                    new MapSqlParameterSource().addValue("merchant_id", merchantId),
                                                    (rs3, rowNum3) -> {

                                                        return new ScanParam(rs3.getInt("scan_param_id"),
                                                                rs3.getString("m_user_id"), rs3.getString("m_password"),
                                                                rs3.getString("checksum_key"), rs3.getString("vid"),
                                                                rs3.getString("cid"),
                                                                rs3.getBoolean("is_qr_ref_id"));
                                                    });
                                            // set Q+
                                            merchant.setScanParam(scanParam);
                                        } catch (EmptyResultDataAccessException e2) {
                                            logger.warn("Error Loading ScanParam");
                                        }
                                    } else if (merchantType.equalsIgnoreCase(MerchantTypes.AMEX)) {
                                        try {
                                            // find amex param
                                            AmexParam amexParam = template.queryForObject(
                                                    FIND_AMEX_PARAM_BY_MERCHANT_ID,
                                                    new MapSqlParameterSource().addValue("merchant_id", merchantId),
                                                    (rs4, rowNum4) -> {

                                                        return new AmexParam(rs4.getString("amex_ip"));
                                                    });
                                            // set Amex
                                            merchant.setAmexParam(amexParam);
                                        } catch (EmptyResultDataAccessException e2) {
                                            logger.warn("Error Loading AmexParam");
                                        }
                                    } else {
                                        // not required
                                    }
                                    // build merchant
                                    merchant.setMerchantId(merchantId);
                                    merchant.setCategory(merchantType);
                                    merchant.setMonth(rs2.getInt("month"));
                                    merchant.setMid(rs2.getString("mid"));
                                    merchant.setTid(rs2.getString("tid"));
                                    merchant.setCurrency(rs2.getString("currency"));
                                    merchant.setDescription(rs2.getString("description"));
                                    merchant.setMinAmount(rs2.getBigDecimal("min_amount"));
                                    merchant.setMaxAmount(rs2.getBigDecimal("max_amount"));
                                    merchant.setVoidTx(rs2.getBoolean("void_tx"));
                                    merchant.setAmexTx(rs2.getBoolean("amex_tx"));
                                    merchant.setDcc(rs2.getBoolean("dcc"));
                                    merchant.setPreAuth(rs2.getBoolean("pre_auth"));
                                    merchant.setOffline(rs2.getBoolean("offline"));
                                    merchant.setMerchantType(rs2.getString("merchant_type"));
                                    merchant.setJcb(rs2.getBoolean("jcb"));
                                    merchant.setLocalMer(rs2.getBoolean("is_local"));
                                    merchant.setForeignMer(rs2.getBoolean("is_foreign"));
                                    merchant.setOnUs(rs2.getBoolean("is_on_us"));
                                    merchant.setOffUs(rs2.getBoolean("is_off_us"));
                                    merchant.setIphoneImei(rs2.getBoolean("iphone_imei"));
                                    return merchant;
                                });
                    } catch (EmptyResultDataAccessException e) {
                        logger.error("Error Loading Merchants: {}", e.getMessage());
                    }

                    // Find Bin Configs for each merchant
                    List<BinConfig> binCongfigs = new ArrayList<>();
                    try {
                        // find merchants
                        binCongfigs = template.query(FIND_BIN_CONFIG_BY_DEVICE_ID,
                                new MapSqlParameterSource().addValue("device_id", deviceId),

                                (rs5, rowNum5) -> {
                                    BinConfig bConfig = new BinConfig();

//									BinConfig bConfig = new BinConfig();
                                    //bConfig.setAction()
                                    bConfig.setAction(rs5.getString("action"));
                                    bConfig.setBinEnd(rs5.getString("bin_end"));
                                    bConfig.setBinStartFrom(rs5.getString("bin_start"));
                                    bConfig.setCardType(rs5.getString("card_type"));
                                    bConfig.setFormId(rs5.getInt("config_id"));
                                    bConfig.setMerchantId(rs5.getInt("merchant_id"));
                                    bConfig.setMid(rs5.getString("mid"));
                                    bConfig.setTid(rs5.getString("tid"));
                                    bConfig.setTransType(rs5.getString("tranx_type"));
                                    bConfig.setType(rs5.getString("config_type"));

                                    return bConfig;
                                });
                    } catch (EmptyResultDataAccessException e) {
                        logger.warn("Error Loading Merchants");
                    }


                    // build device
                    Device device = new Device();
                    device.setDeviceId(rs1.getInt("device_id"));
                    device.setSerialNo(rs1.getString("serial_no"));
                    device.setToken(rs1.getString("token"));
                    device.setBankCode(rs1.getString("bank_code"));
                    device.setBankName(rs1.getString("bank_name"));
                    device.setMerchantName(rs1.getString("merchant_name"));
                    device.setMerchantAddress(rs1.getString("merchant_address"));
                    device.setAddedDate(rs1.getString("added_date"));
                    device.setAddedBy(rs1.getString("added_by"));
                    device.setLastUpdate(rs1.getString("last_update"));
                    device.setUpdatedBy(rs1.getString("updated_by"));
                    device.setStatus(rs1.getString("status"));
                    device.setVisaNoCvmLimit(rs1.getBigDecimal("visa_no_cvm_limit"));
                    device.setCntactlsTrxnLimit(rs1.getBigDecimal("cntactls_trxn_limit"));
                    device.setAutoSettle(rs1.getBoolean("auto_settle"));
                    device.setAutoSettleTime(rs1.getString("auto_settle_time"));
                    device.setForceSettle(rs1.getBoolean("force_settle"));
                    device.setEcr(rs1.getBoolean("ecr"));
                    device.setKeyIn(rs1.getBoolean("key_in"));
                    device.setMobileNo(rs1.getString("mobile_no"));
                    device.setSimNo(rs1.getString("sim_no"));
                    device.setCustContactNo(rs1.getString("cust_contact_no"));
                    device.setRemark(rs1.getString("remark"));
                    device.setEcrQr(rs1.getBoolean("ecr_qr"));
                    device.setSignature(rs1.getBoolean("signature"));
                    device.setDebugMode(rs1.getBoolean("debug_mode"));
                    device.setNoSettle(rs1.getBoolean("no_settle"));
                    device.setActivityTracker(rs1.getBoolean("activity_tracker"));
                    device.setQrRefund(rs1.getBoolean("qr_refund"));
                    device.setReversalHistory(rs1.getBoolean("reversal_history"));
                    device.setEnableAmex(rs1.getBoolean("enable_amex"));
                    device.setPushNotification(rs1.getBoolean("push_notification"));
                    device.setPreAuth(rs1.getBoolean("pre_auth"));
                    device.setDcc(rs1.getBoolean("dcc"));
                    device.setOffline(rs1.getBoolean("offline"));
                    device.setKeyInAmex(rs1.getBoolean("key_in_amex"));
                    device.setPopupMsg(rs1.getBoolean("popup_msg"));

                    device.setCardTypeValidation(rs1.getBoolean("card_type_val"));
                    device.setSaleReceipt(rs1.getBoolean("sale_receipt"));
                    device.setCurrencyFromBin(rs1.getBoolean("curr_from_bin"));
                    device.setCurrencyFromCard(rs1.getBoolean("curr_from_card"));
                    device.setProceedWithLkr(rs1.getBoolean("prcd_with_lkr"));
                    device.setCardTap(rs1.getBoolean("card_tap"));
                    device.setCardInsert(rs1.getBoolean("card_insert"));
                    device.setCardSwipe(rs1.getBoolean("card_swipe"));
                    device.setEcrIp(rs1.getString("ecr_ip"));
                    device.setEcrPort(rs1.getString("ecr_port"));
                    device.setEcrAuthToken(rs1.getBoolean("ecr_auth_token"));
                    device.setTranToSim(rs1.getBoolean("tran_to_sim"));
                    device.setEcrWifi(rs1.getBoolean("ecr_wifi"));

                    device.setDccPayload(rs1.getBoolean("dcc_payload"));
                    device.setNetwork(rs1.getString("network"));
                    device.setMerchantPortal(rs1.getBoolean("merchant_portal"));
                    device.setResendVoid(rs1.getBoolean("resend_void_sms"));
                    device.setClientCredentials(rs1.getBoolean("live_client"));
                    device.setPrintReceipt(rs1.getBoolean("print_receipt"));
                    device.setAutoReversal(rs1.getBoolean("auto_reversal"));
                    device.setPrintless(rs1.getBoolean("printless_mode"));

                    device.setLkrDefaultCurr(rs1.getBoolean("lkr_default_curr"));
                    device.setVoidPwd(rs1.getString("void_pwd"));
                    device.setDiffSaleMidTid(rs1.getBoolean("diff_sale_mid_tid"));
                    device.setMkiPreAuth(rs1.getBoolean("mki_pre_auth"));
                    device.setMkiOffline(rs1.getBoolean("mki_offline"));
                    device.setSignPriority(rs1.getBoolean("sing_priority"));
                    device.setBlockMag(rs1.getBoolean("block_mag"));
                    device.setCustomerReceipt(rs1.getBoolean("cust_receipt"));
                    device.setMidTidSeg(rs1.getBoolean("is_seg"));
                    device.setEventAutoUpdate(rs1.getBoolean("auto_update"));
                    device.setNewVoidPwd(rs1.getString("new_void_pwd"));
                    device.setNewSettlementPwd(rs1.getString("new_sattle_pwd"));
                    device.setImeiScan(rs1.getBoolean("imei_scan"));
                    device.setCommission(rs1.getBoolean("commission"));
                    device.setCommissionRate(rs1.getFloat("commission_rate"));
                    device.setOfflineUser(rs1.getString("offline_users"));
                    device.setEnableSanhindaPay(rs1.getBoolean("sanhinda_pay"));
                    device.setRefNumberEnable(rs1.getBoolean("ref_no"));
                    device.setTleProfilesEnable(rs1.getBoolean("tle_profile"));

                    // set merchants
                    if (merchants != null) {
                        logger.info("No of Merchants: {}", merchants.size());
                        device.setMerchants(merchants);
                    }
                    // set merchants
                    if (binCongfigs != null) {
                        logger.info("No of binCongfigs: {}", binCongfigs.size());
                        device.setBinConfig(binCongfigs);
                    }


                    // set profiles (Amesh Madumalka 2021-10-07)
                    List<Profile> profileList = findProfileById(deviceId);
                    // Set profile merchants into profile
                    List<Profile> newProfileList = new ArrayList<Profile>();
                    if (profileList != null && !profileList.isEmpty()) {
                        profileList.forEach(profile -> {
                            List<ProfileMerchant> profileMerchant = findProfMerchantById(profile.getProfileId());
                            if (profileMerchant != null && !profileMerchant.isEmpty()) {
                                profile.setProfileMerchants(profileMerchant);
                            }
                            newProfileList.add(profile);
                        });
                        device.setProfiles(newProfileList);
                    }

                    // return device with all childs
                    return device;
                }));
    }

    @Override
    public boolean existsById(Integer id) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Integer count() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean existsByParams(Device params) throws IllegalArgumentException {
        if (params == null || StringUtil.isNullOrWhiteSpace(params.getSerialNo())) {
            logger.info("device dto or serial number should not be null or empty");
            throw new BadCredentialsException("serial number should not be null or empty");
        }
        Boolean deviceExists = template.queryForObject(FIND_DEVICE_EXISTS_BY_SERIAL_NO, new MapSqlParameterSource()
                .addValue("serial_no", params.getSerialNo()), Boolean.class);

        return Boolean.TRUE.equals(deviceExists);
    }

    @SuppressWarnings({"unused", "unused"})
    @Override
    public List<Device> findAllByDates(List<String> params) throws IllegalArgumentException {

        return template.query(FIND_DEVICE_BY_DATE,
                new MapSqlParameterSource().addValue("fromDate", params.get(0)).addValue("toDate", params.get(1)),
                (rs, rowNum) -> {

                    Device device = new Device();
                    device.setDeviceId(rs.getInt("device_id"));
                    device.setSerialNo(rs.getString("serial_no"));
                    device.setBankCode(rs.getString("bank_code"));
                    device.setBankName(rs.getString("bank_name"));
                    device.setMerchantName(rs.getString("merchant_name"));
                    device.setMerchantAddress(rs.getString("merchant_address"));
                    device.setAddedDate(rs.getString("added_date"));
                    device.setAddedBy(rs.getString("added_by"));
                    device.setLastUpdate(rs.getString("last_update"));
                    device.setUpdatedBy(rs.getString("updated_by"));
                    device.setStatus(rs.getString("status"));
                    device.setMobileNo(rs.getString("mobile_no"));
                    device.setSimNo(rs.getString("sim_no"));
                    device.setCustContactNo(rs.getString("cust_contact_no"));
                    device.setRemark(rs.getString("remark"));

                    return device;
                });

    }

    public List<Device> findAllAddedByTodays(List<String> params) throws IllegalArgumentException {

        return template.query(FIND_DEVICE_ADDED_BY_TODAY,
                new MapSqlParameterSource().addValue("fromDate", params.get(0)).addValue("toDate", params.get(1)),
                (rs, rowNum) -> {

                    Device device = new Device();
                    device.setDeviceId(rs.getInt("device_id"));
                    device.setSerialNo(rs.getString("serial_no"));
                    device.setBankCode(rs.getString("bank_code"));
                    device.setBankName(rs.getString("bank_name"));
                    device.setMerchantName(rs.getString("merchant_name"));
                    device.setMerchantAddress(rs.getString("merchant_address"));
                    device.setAddedDate(rs.getString("added_date"));
                    device.setAddedBy(rs.getString("added_by"));
                    device.setLastUpdate(rs.getString("last_update"));
                    device.setUpdatedBy(rs.getString("updated_by"));
                    device.setStatus(rs.getString("status"));
                    device.setMobileNo(rs.getString("mobile_no"));
                    device.setSimNo(rs.getString("sim_no"));
                    device.setCustContactNo(rs.getString("cust_contact_no"));
                    device.setRemark(rs.getString("remark"));

                    return device;
                });

    }

    // find device id
    private Integer findCurrentDeviceIdBySerialNo(String serialNo) {
        try {
            return template.queryForObject(FIND_LAST_DEVICE_ID,
                    new MapSqlParameterSource().addValue("serial_no", serialNo), Integer.class);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Incorrect result size");
        }

        return null;
    }

    // find merchant id
    private Integer findCurrentMerchantIdByDeviceId(int currentDeviceId) {
        try {
            return template.queryForObject(FIND_LAST_MERCHANT_ID,
                    new MapSqlParameterSource().addValue("device_id", currentDeviceId), Integer.class);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Incorrect result size");
        }

        return null;
    }

    // find device by serial no
    private Device findDeviceBySerialNo(String serialNo) {
        Device currentDevice = null;
        try {
            currentDevice = template.queryForObject(FIND_DEVICE_BY_SERIAL_NO,
                    new MapSqlParameterSource().addValue("serial_no", serialNo), (rs, rowNo) -> {
                        return new Device(rs.getInt("device_id"), rs.getString("serial_no"));
                    });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Cant't find device");
        }

        return currentDevice;
    }

    // find device by serial no
    private Device findDeviceById(Integer deviceId) {
        Device currentDevice = null;
        try {
            currentDevice = template.queryForObject(FIND_DEVICE_BY_DEVICE_ID,
                    new MapSqlParameterSource().addValue("device_id", deviceId), (rs, rowNo) -> {
                        return new Device(rs.getInt("device_id"), rs.getString("device_id"));
                    });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Cant't find device");
        }

        return currentDevice;
    }

    // save device
    private int saveDevice(Device device) {
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(INSERT_DEVICE, new MapSqlParameterSource().addValue("serial_no", device.getSerialNo())
                        .addValue("token", device.getToken()).addValue("bank_code", device.getBankCode())
                        .addValue("bank_name", device.getBankName()).addValue("merchant_name", device.getMerchantName())
                        .addValue("merchant_address", device.getMerchantAddress()).addValue("added_date", device.getAddedDate())
                        .addValue("added_by", device.getAddedBy()).addValue("last_update", device.getLastUpdate())
                        .addValue("updated_by", device.getUpdatedBy()).addValue("status", device.getStatus())
                        .addValue("visa_no_cvm_limit", device.getVisaNoCvmLimit())
                        .addValue("cntactls_trxn_limit", device.getCntactlsTrxnLimit())
                        .addValue("auto_settle", device.isAutoSettle()).addValue("auto_settle_time", device.getAutoSettleTime())
                        .addValue("force_settle", device.isForceSettle()).addValue("ecr", device.isEcr())
                        .addValue("key_in", device.isKeyIn()).addValue("mobile_no", device.getMobileNo())
                        .addValue("sim_no", device.getSimNo()).addValue("cust_contact_no", device.getCustContactNo())
                        .addValue("remark", device.getRemark()).addValue("ecr_qr", device.isEcrQr())
                        .addValue("signature", device.isSignature()).addValue("debug_mode", device.isDebugMode())
                        .addValue("no_settle", device.isNoSettle()).addValue("activity_tracker", device.isActivityTracker())
                        .addValue("qr_refund", device.isQrRefund()).addValue("reversal_history", device.isReversalHistory())
                        .addValue("is_profile", device.isProfile()).addValue("enable_amex", device.isEnableAmex())
                        .addValue("push_notification", device.isPushNotification()).addValue("pre_auth", device.isPreAuth())
                        .addValue("dcc", device.isDcc()).addValue("offline", device.isOffline())
                        .addValue("key_in_amex", device.isKeyInAmex()).addValue("popup_msg", device.isPopupMsg())

                        .addValue("card_type_val", device.isCardTypeValidation())
                        .addValue("sale_receipt", device.isSaleReceipt())
                        .addValue("curr_from_bin", device.isCurrencyFromBin())
                        .addValue("curr_from_card", device.isCurrencyFromCard())
                        .addValue("prcd_with_lkr", device.isProceedWithLkr())
                        .addValue("card_tap", device.isCardTap())
                        .addValue("card_insert", device.isCardInsert())
                        .addValue("card_swipe", device.isCardSwipe())
                        .addValue("ecr_ip", device.getEcrIp())
                        .addValue("ecr_port", device.getEcrPort())
                        .addValue("ecr_auth_token", device.isEcrAuthToken())
                        .addValue("tran_to_sim", device.isTranToSim())
                        .addValue("ecr_wifi", device.isEcrWifi())

                        .addValue("dcc_payload", device.isDccPayload())
                        .addValue("network", device.getNetwork())
                        .addValue("merchant_portal", device.isMerchantPortal())
                        .addValue("resend_void_sms", device.isResendVoid())
                        .addValue("live_client", device.isClientCredentials())
                        .addValue("print_receipt", device.isPrintReceipt())
                        .addValue("auto_reversal", device.isAutoReversal())
                        .addValue("printless_mode", device.isPrintless())
                        .addValue("lkr_default_curr", device.isLkrDefaultCurr())
                        .addValue("void_pwd", device.getVoidPwd())
                        .addValue("diff_sale_mid_tid", device.isDiffSaleMidTid())
                        .addValue("mki_pre_auth", device.isMkiPreAuth())
                        .addValue("mki_offline", device.isMkiOffline())
                        .addValue("sing_priority", device.isSignPriority())
                        .addValue("block_mag", device.isBlockMag())
                        .addValue("cust_receipt", device.isCustomerReceipt())
                        .addValue("is_seg", device.isMidTidSeg())
                        .addValue("auto_update", device.isEventAutoUpdate())
                        .addValue("new_void_pwd", device.getNewVoidPwd())
                        .addValue("new_sattle_pwd", device.getNewSettlementPwd())
                        .addValue("imei_scan", device.isImeiScan())
                        .addValue("commission", device.isCommission())
                        .addValue("commission_rate", device.getCommissionRate())
                        .addValue("offline_users", device.getOfflineUser(), java.sql.Types.OTHER)
                        .addValue("sanhinda_pay", device.isEnableSanhindaPay())
                        .addValue("ref_no", device.isRefNumberEnable())
                        .addValue("tle_profile", device.isTleProfilesEnable()),
                holder, new String[]{"device_id"});
        return holder.getKey().intValue();
    }

    // save merchant
    private int saveMerchant(Merchant merchant, int deviceId) {
        KeyHolder holder = new GeneratedKeyHolder();
        //merchant_type
        template.update(INSERT_MERCHANT, new MapSqlParameterSource().addValue("category", merchant.getCategory())
                        .addValue("month", merchant.getMonth()).addValue("mid", merchant.getMid())
                        .addValue("tid", merchant.getTid()).addValue("currency", merchant.getCurrency())
                        .addValue("description", merchant.getDescription()).addValue("min_amount", merchant.getMinAmount())
                        .addValue("max_amount", merchant.getMaxAmount()).addValue("void_tx", merchant.isVoidTx())
                        .addValue("amex_tx", merchant.isAmexTx()).addValue("dcc", merchant.isDcc())
                        .addValue("pre_auth", merchant.isPreAuth()).addValue("offline", merchant.isOffline())
                        .addValue("merchant_type", merchant.getMerchantType())
                        .addValue("jcb", merchant.isJcb())
                        .addValue("is_local", merchant.isLocalMer())
                        .addValue("is_foreign", merchant.isForeignMer())
                        .addValue("is_on_us", merchant.isOnUs())
                        .addValue("is_off_us", merchant.isOffUs())
                        .addValue("is_seg", merchant.isMidTidSeg())
                        .addValue("iphone_imei", merchant.isIphoneImei())
                        .addValue("device_id", deviceId), holder,
                new String[]{"merchant_id"});

        return holder.getKey().intValue();
    }

    // save bin config
    private int saveBinConfig(BinConfig binConfig, int deviceId, int merchantId) {
        KeyHolder holder = new GeneratedKeyHolder();

        template.update(INSERT_BIN_CONFIG, new MapSqlParameterSource()
                        .addValue("card_type", binConfig.getCardType())
                        .addValue("tranx_type", binConfig.getTransType())
                        .addValue("action", binConfig.getAction())
                        .addValue("config_type", binConfig.getType())
                        .addValue("bin_start", binConfig.getBinStartFrom())
                        .addValue("bin_end", binConfig.getBinEnd())
                        .addValue("mid", binConfig.getMid())
                        .addValue("tid", binConfig.getTid())
                        .addValue("merchant_id", merchantId)
                        .addValue("device_id", deviceId), holder,
                new String[]{"config_id"});

        return holder.getKey().intValue();
    }

    // save q+ scan params
    private void saveScanParam(ScanParam scanParam, int merchantId) {
        template.update(INSERT_SCAN_PARAM,
                new MapSqlParameterSource().addValue("m_user_id", scanParam.getMerchantUserId())
                        .addValue("m_password", scanParam.getMerchantPassword())
                        .addValue("checksum_key", scanParam.getChecksumKey()).addValue("merchant_id", merchantId)
                        .addValue("vid", scanParam.getVid()).addValue("cid", scanParam.getCid())
                        .addValue("is_qr_ref_id", scanParam.isQrRefId()));
    }

    // save amex params
    private void saveAmexParam(AmexParam amexParam, int merchantId) {
        template.update(INSERT_AMEX_PARAM, new MapSqlParameterSource().addValue("amex_ip", amexParam.getAmexIp())
                .addValue("merchant_id", merchantId));
    }

    // update device
    private void updateDevice(Device device, int deviceId) {
        template.update(UPDATE_DEVICE, new MapSqlParameterSource().addValue("serial_no", device.getSerialNo().trim())
                .addValue("bank_code", device.getBankCode()).addValue("bank_name", device.getBankName())
                .addValue("merchant_name", device.getMerchantName())
                .addValue("merchant_address", device.getMerchantAddress())
                .addValue("last_update", device.getLastUpdate()).addValue("updated_by", device.getUpdatedBy())
                .addValue("status", device.getStatus()).addValue("visa_no_cvm_limit", device.getVisaNoCvmLimit())
                .addValue("cntactls_trxn_limit", device.getCntactlsTrxnLimit())
                .addValue("auto_settle", device.isAutoSettle()).addValue("auto_settle_time", device.getAutoSettleTime())
                .addValue("force_settle", device.isForceSettle()).addValue("ecr", device.isEcr())
                .addValue("key_in", device.isKeyIn()).addValue("mobile_no", device.getMobileNo())
                .addValue("sim_no", device.getSimNo()).addValue("cust_contact_no", device.getCustContactNo())
                .addValue("remark", device.getRemark()).addValue("ecr_qr", device.isEcrQr())
                .addValue("signature", device.isSignature()).addValue("debug_mode", device.isDebugMode())
                .addValue("no_settle", device.isNoSettle()).addValue("activity_tracker", device.isActivityTracker())
                .addValue("device_id", deviceId).addValue("qr_refund", device.isQrRefund())
                .addValue("is_profile", device.isProfile()).addValue("reversal_history", device.isReversalHistory())
                .addValue("enable_amex", device.isEnableAmex()).addValue("pre_auth", device.isPreAuth())
                .addValue("push_notification", device.isPushNotification())
                .addValue("dcc", device.isDcc()).addValue("popup_msg", device.isPopupMsg())
                .addValue("offline", device.isOffline()).addValue("key_in_amex", device.isKeyInAmex())
                .addValue("card_type_val", device.isCardTypeValidation())
                .addValue("sale_receipt", device.isSaleReceipt())
                .addValue("curr_from_bin", device.isCurrencyFromBin())
                .addValue("curr_from_card", device.isCurrencyFromCard())
                .addValue("prcd_with_lkr", device.isProceedWithLkr())
                .addValue("card_tap", device.isCardTap())
                .addValue("card_insert", device.isCardInsert())
                .addValue("card_swipe", device.isCardSwipe())
                .addValue("ecr_ip", device.getEcrIp())
                .addValue("ecr_port", device.getEcrPort())
                .addValue("ecr_auth_token", device.isEcrAuthToken())
                .addValue("tran_to_sim", device.isTranToSim())
                .addValue("ecr_wifi", device.isEcrWifi())

                .addValue("dcc_payload", device.isDccPayload())
                .addValue("network", device.getNetwork())
                .addValue("merchant_portal", device.isMerchantPortal())
                .addValue("resend_void_sms", device.isResendVoid())
                .addValue("live_client", device.isClientCredentials())
                .addValue("print_receipt", device.isPrintReceipt())
                .addValue("auto_reversal", device.isAutoReversal())
                .addValue("printless_mode", device.isPrintless())
                .addValue("lkr_default_curr", device.isLkrDefaultCurr())
                .addValue("void_pwd", device.getVoidPwd())
                .addValue("diff_sale_mid_tid", device.isDiffSaleMidTid())
                .addValue("mki_pre_auth", device.isMkiPreAuth())
                .addValue("mki_offline", device.isMkiOffline())
                .addValue("sing_priority", device.isSignPriority())
                .addValue("block_mag", device.isBlockMag())
                .addValue("cust_receipt", device.isCustomerReceipt())
                .addValue("is_seg", device.isMidTidSeg())
                .addValue("auto_update", device.isEventAutoUpdate())
                .addValue("new_void_pwd", device.getNewVoidPwd())
                .addValue("new_sattle_pwd", device.getNewSettlementPwd())
                .addValue("imei_scan", device.isImeiScan())
                .addValue("commission", device.isCommission())
                .addValue("commission_rate", device.getCommissionRate())
                .addValue("offline_users", device.getOfflineUser(), java.sql.Types.OTHER)
                .addValue("sanhinda_pay", device.isEnableSanhindaPay())
                .addValue("ref_no", device.isRefNumberEnable())
                .addValue("tle_profile", device.isTleProfilesEnable())
        );


    }

    // update merchant
    private void updateMerchant(Merchant merchant, int deviceId) {
        template.update(UPDATE_MERCHANT,
                new MapSqlParameterSource().addValue("category", merchant.getCategory())
                        .addValue("month", merchant.getMonth()).addValue("mid", merchant.getMid().trim())
                        .addValue("tid", merchant.getTid().trim()).addValue("currency", merchant.getCurrency())
                        .addValue("description", merchant.getDescription())
                        .addValue("min_amount", merchant.getMinAmount()).addValue("max_amount", merchant.getMaxAmount())
                        .addValue("void_tx", merchant.isVoidTx()).addValue("amex_tx", merchant.isAmexTx())
                        .addValue("device_id", deviceId).addValue("merchant_id", merchant.getMerchantId())
                        .addValue("dcc", merchant.isDcc()).addValue("pre_auth", merchant.isPreAuth())
                        .addValue("merchant_type", merchant.getMerchantType())
                        .addValue("jcb", merchant.isJcb())
                        .addValue("offline", merchant.isOffline())
                        .addValue("is_local", merchant.isLocalMer())
                        .addValue("is_foreign", merchant.isForeignMer())
                        .addValue("is_on_us", merchant.isOnUs())
                        .addValue("is_off_us", merchant.isOffUs())
                        .addValue("is_seg", merchant.isMidTidSeg())
                        .addValue("iphone_imei", merchant.isIphoneImei()));
    }

    // update q+ scan param
    private void updateScanParam(ScanParam scanParam, int merchantId) {
        template.update(UPDATE_SCAN_PARAM,
                new MapSqlParameterSource().addValue("m_user_id", scanParam.getMerchantUserId())
                        .addValue("m_password", scanParam.getMerchantPassword())
                        .addValue("checksum_key", scanParam.getChecksumKey()).addValue("merchant_id", merchantId)
                        .addValue("vid", scanParam.getVid()).addValue("cid", scanParam.getCid())
                        .addValue("is_qr_ref_id", scanParam.isQrRefId()));
    }

    // update amex param
    private void updateAmexParam(AmexParam amexParam, int merchantId) {
        template.update(UPDATE_AMEX_PARAM, new MapSqlParameterSource().addValue("amex_ip", amexParam.getAmexIp())
                .addValue("merchant_id", merchantId));
    }

    // update token
    private Integer updateDeviceToken(Device device, String serialNo) {
        template.update(UPDATE_DEVICE_TOKEN,
                new MapSqlParameterSource().addValue("token", device.getToken())
                        .addValue("last_update", device.getLastUpdate()).addValue("updated_by", device.getUpdatedBy())
                        .addValue("status", device.getStatus()).addValue("serial_no", serialNo));

        Device currentDevice = findDeviceBySerialNo(serialNo);
        if (currentDevice != null)
            return currentDevice.getDeviceId();
        else
            return null;
    }

    // update token
    private Integer updateDeviceStatus(Device device, String serialNo) {
        template.update(UPDATE_DEVICE_STATUS,
                new MapSqlParameterSource().addValue("last_update", device.getLastUpdate())
                        .addValue("updated_by", device.getUpdatedBy()).addValue("status", device.getStatus())
                        .addValue("serial_no", serialNo));

        Device currentDevice = findDeviceBySerialNo(serialNo);
        if (currentDevice != null)
            return currentDevice.getDeviceId();
        else
            return null;
    }

    // find merchant tids by device id
    private List<Long> findMerchantTidsByDeviceId(int deviceId) {
        List<Long> tids = null;
        try {
            tids = template.queryForList(FIND_MERCHANT_TIDS_BY_DEVICE_ID,
                    new MapSqlParameterSource().addValue("device_id", deviceId), Long.class);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Cant't find merchant tids by device id");
        }

        return tids;
    }

    // find merchant by device id & tid
    private Merchant findMerchantByDeviceIdAndMerchantId(int deviceId, int merchantId) {
        Merchant currentMerchant = null;
        try {
            currentMerchant = template.queryForObject(FIND_MERCHANT_BY_DEVICE_ID_MERCHANT_ID,
                    new MapSqlParameterSource().addValue("device_id", deviceId).addValue("merchant_id", merchantId),
                    (rs, rowNo) -> {
                        return new Merchant(rs.getInt("merchant_id"), rs.getString("category"), rs.getString("mid"),
                                rs.getString("tid"));
                    });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Can't find merchant by device id & merchant id");
        }

        return currentMerchant;
    }

    // find merchant ids by device id
    private List<Integer> findMerchantIdsByDeviceId(int deviceId) {
        List<Integer> merchantIds = null;
        try {
            merchantIds = template.queryForList(FIND_MERCHANT_IDS_BY_DEVICE_ID,
                    new MapSqlParameterSource().addValue("device_id", deviceId), Integer.class);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Can't find merchant  ids by device id");
        }

        return merchantIds;
    }

    // insert profile (Amesh Madumalka 2021/10/07)
    private int saveProfile(Profile profile, int deviceId) {
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(INSERT_PROFILE, new MapSqlParameterSource().addValue("profile_name", profile.getProfileName())
                        .addValue("is_default", profile.isDefault()).addValue("merchant_name", profile.getMerchantName())
                        .addValue("merchant_adrs", profile.getMerchantAdrs()).addValue("visa_cnt", profile.isVisaCnt())
                        .addValue("visa_cntls", profile.isVisaCntls()).addValue("visa_nocvm_limit", profile.getVisaNoCvm())
                        .addValue("visa_cntls_trxn_limit", profile.getVisaCntslTrxn()).addValue("mc_cnt", profile.isMcCnt())
                        .addValue("mc_cntls", profile.isMcCntls()).addValue("mc_nocvm_limit", profile.getMcNoCvm())
                        .addValue("mc_cntls_trxn_limit", profile.getMcCntslTrxn()).addValue("amex_cnt", profile.isAmexCnt())
                        .addValue("amex_cntls", profile.isAmexCntls()).addValue("amex_nocvm_limit", profile.getAmexNoCvm())
                        .addValue("amex_cntls_trxn_limit", profile.getAmexCntslTrxn()).addValue("upay_cnt", profile.isUpayCnt())
                        .addValue("upay_cntls", profile.isUpayCntls()).addValue("upay_nocvm_limit", profile.getUpayNoCvm())
                        .addValue("upay_cntls_trxn_limit", profile.getUpayCntslTrxn()).addValue("jcb_cnt", profile.isJcbCnt())
                        .addValue("jcb_cntls", profile.isJcbCntls()).addValue("jcb_nocvm_limit", profile.getJcbNoCvm())
                        .addValue("jcb_cntls_trxn_limit", profile.getJcbCntslTrxn())
                        .addValue("added_date", profile.getAddedDate()).addValue("added_by", profile.getAddedBy())
                        .addValue("last_update", profile.getLastUpdate()).addValue("updated_by", profile.getUpdatedBy())
                        .addValue("customer_copy", profile.isCustomerCopy()).addValue("prof_ref_id", profile.getProfileRefId())
                        .addValue("tls", profile.isTlsEnable())
                        .addValue("status", profile.getStatus()).addValue("device_id", deviceId), holder,
                new String[]{"profile_id"});

        return holder.getKey().intValue();
    }

    // insert profile merchant (Amesh Madumalka 2021/10/07)
    private int saveProfMerchant(ProfileMerchant profileMerchant, int profileId) {
        KeyHolder holder = new GeneratedKeyHolder();
        logger.info("repo: Merchant id: {}", profileMerchant.getMerchantId());
        template.update(INSERT_PROF_MERCHANT, new MapSqlParameterSource().addValue("profile_id", profileId)
                        .addValue("merchant_id", profileMerchant.getMerchantId())
                        .addValue("is_default", profileMerchant.isDefaultMerchant())
                        .addValue("added_date", profileMerchant.getAddedDate())
                        .addValue("added_by", profileMerchant.getAddedBy())
                        .addValue("last_update", profileMerchant.getLastUpdate()).addValue("prof_mer_ref_id", profileMerchant.getProfMerRefId())
                        .addValue("updated_by", profileMerchant.getUpdatedBy()).addValue("status", profileMerchant.getStatus()),

                holder, new String[]{"prof_merg_id"});

        return holder.getKey().intValue();
    }

    // find profiles - (Amesh Madumalka 2021-10-07)
    public List<Profile> findProfileById(int deviceId) {
        List<Profile> profiles = new ArrayList<>();
        try {
            if (deviceId > 0) {
                profiles = template.query(FIND_PROFILE_BY_DEVICE_ID,
                        new MapSqlParameterSource().addValue("device_id", deviceId), (rs, rowNum) -> {
                            Profile profile = new Profile();
                            int profileId = rs.getInt("profile_id");
                            profile.setProfileId(profileId);
                            profile.setProfileName(rs.getString("profile_name"));
                            profile.setDefault(rs.getBoolean("is_default"));
                            profile.setMerchantName(rs.getString("merchant_name"));
                            profile.setMerchantAdrs(rs.getString("merchant_adrs"));
                            profile.setVisaCnt(rs.getBoolean("visa_cnt"));
                            profile.setVisaCntls(rs.getBoolean("visa_cntls"));
                            profile.setVisaNoCvm(rs.getBigDecimal("visa_nocvm_limit"));
                            profile.setVisaCntslTrxn(rs.getBigDecimal("visa_cntls_trxn_limit"));
                            profile.setMcCnt(rs.getBoolean("mc_cnt"));
                            profile.setMcCntls(rs.getBoolean("mc_cntls"));
                            profile.setMcNoCvm(rs.getBigDecimal("mc_nocvm_limit"));
                            profile.setMcCntslTrxn(rs.getBigDecimal("mc_cntls_trxn_limit"));
                            profile.setAmexCnt(rs.getBoolean("amex_cnt"));
                            profile.setAmexCntls(rs.getBoolean("amex_cntls"));
                            profile.setAmexNoCvm(rs.getBigDecimal("amex_nocvm_limit"));
                            profile.setAmexCntslTrxn(rs.getBigDecimal("amex_cntls_trxn_limit"));
                            profile.setUpayCnt(rs.getBoolean("upay_cnt"));
                            profile.setUpayCntls(rs.getBoolean("upay_cntls"));
                            profile.setUpayNoCvm(rs.getBigDecimal("upay_nocvm_limit"));
                            profile.setUpayCntslTrxn(rs.getBigDecimal("upay_cntls_trxn_limit"));
                            profile.setJcbCnt(rs.getBoolean("jcb_cnt"));
                            profile.setJcbCntls(rs.getBoolean("jcb_cntls"));
                            profile.setJcbNoCvm(rs.getBigDecimal("jcb_nocvm_limit"));
                            profile.setJcbCntslTrxn(rs.getBigDecimal("jcb_cntls_trxn_limit"));
                            profile.setAddedDate(rs.getString("added_date"));
                            profile.setAddedBy(rs.getString("added_by"));
                            profile.setLastUpdate(rs.getString("last_update"));
                            profile.setUpdatedBy(rs.getString("updated_by"));
                            profile.setStatus(rs.getString("status"));
                            profile.setCustomerCopy(rs.getBoolean("customer_copy"));
                            profile.setProfileRefId(rs.getInt("prof_ref_id"));
                            profile.setTlsEnable(rs.getBoolean("tls"));
                            List<ProfileMerchant> profileMerchants = findProfMerchantById(profileId);
                            if (profileMerchants != null && !profileMerchants.isEmpty()) {
                                profile.setProfileMerchants(profileMerchants);
                            }

                            return profile;
                        });
            } else {
                logger.warn("Invalid device id");
            }
        } catch (Exception e) {
            logger.error("Error finding profiles : {}", e.getMessage());
        }
        return profiles;
    }

    // find profile merchants - (Amesh Madumalka 2021-10-07)
    private List<ProfileMerchant> findProfMerchantById(int profileId) {
        List<ProfileMerchant> profileMerchants = new ArrayList<>();
        try {
            if (profileId > 0) {
                profileMerchants = template.query(FIND_PROFILE_MERCHANT_BY_PROFILE_ID,
                        new MapSqlParameterSource().addValue("profile_id", profileId), (rs1, rowNum1) -> {
                            ProfileMerchant profileMerchant = new ProfileMerchant();
                            profileMerchant.setProfMergId(rs1.getInt("prof_merg_id"));
                            profileMerchant.setProfileId(rs1.getInt("profile_id"));
                            profileMerchant.setMerchantId(rs1.getInt("merchant_id"));
                            profileMerchant.setDefaultMerchant(rs1.getBoolean("is_default"));
                            profileMerchant.setAddedDate(rs1.getString("added_date"));
                            profileMerchant.setAddedBy(rs1.getString("added_by"));
                            profileMerchant.setLastUpdate(rs1.getString("last_update"));
                            profileMerchant.setUpdatedBy(rs1.getString("updated_by"));
                            profileMerchant.setStatus(rs1.getString("status"));
                            profileMerchant.setProfMerRefId(rs1.getInt("prof_mer_ref_id"));

                            return profileMerchant;
                        });
            } else {
                logger.warn("Invalid profile merchant id");
            }
        } catch (Exception e) {
            logger.error("Error finding profile merchants : {}", e.getMessage());
        }
        return profileMerchants;
    }

    // update profiles (Amesh Madumalka 2021-10-07)
    private void updateProfile(Profile profile, int deviceId, int profileId) {
        logger.info("updateProfile: {}", profile.getProfileName());
        template.update(UPDATE_PROFILE, new MapSqlParameterSource().addValue("profile_name", profile.getProfileName())
                .addValue("is_default", profile.isDefault()).addValue("merchant_name", profile.getMerchantName())
                .addValue("merchant_adrs", profile.getMerchantAdrs())
                .addValue("merchant_address", profile.getMerchantAdrs()).addValue("visa_cnt", profile.isVisaCnt())
                .addValue("visa_cntls", profile.isVisaCntls()).addValue("visa_nocvm_limit", profile.getVisaNoCvm())
                .addValue("visa_cntls_trxn_limit", profile.getVisaCntslTrxn()).addValue("mc_cnt", profile.isMcCnt())
                .addValue("mc_cntls", profile.isMcCntls()).addValue("mc_nocvm_limit", profile.getMcNoCvm())
                .addValue("mc_cntls_trxn_limit", profile.getMcCntslTrxn()).addValue("amex_cnt", profile.isAmexCnt())
                .addValue("amex_cntls", profile.isAmexCntls()).addValue("amex_nocvm_limit", profile.getAmexNoCvm())
                .addValue("amex_cntls_trxn_limit", profile.getAmexCntslTrxn()).addValue("upay_cnt", profile.isUpayCnt())
                .addValue("upay_cntls", profile.isUpayCntls()).addValue("upay_nocvm_limit", profile.getUpayNoCvm())
                .addValue("upay_cntls_trxn_limit", profile.getUpayCntslTrxn()).addValue("jcb_cnt", profile.isJcbCnt())
                .addValue("jcb_cntls", profile.isJcbCntls()).addValue("jcb_nocvm_limit", profile.getJcbNoCvm())
                .addValue("jcb_cntls_trxn_limit", profile.getJcbCntslTrxn())
                .addValue("last_update", profile.getLastUpdate()).addValue("updated_by", profile.getUpdatedBy())
                .addValue("status", profile.getStatus()).addValue("profile_id", profileId)
                .addValue("customer_copy", profile.isCustomerCopy()).addValue("prof_ref_id", profile.getProfileRefId())
                .addValue("tls", profile.isTlsEnable())
                .addValue("device_id", deviceId));
    }

    // find profile id by device id (Amesh Madumalka 2021-10-08)
    private List<Integer> findProfileIdByDeviceId(int deviceId) {
        List<Integer> profileIdList = null;
        try {
            profileIdList = template.queryForList(FIND_PROFILE_ID_BY_DEVICE_ID,
                    new MapSqlParameterSource().addValue("device_id", deviceId), Integer.class);
        } catch (DataAccessException e) {
            logger.error("Error finding profile id according to device id");
        }

        return profileIdList;
    }

    // delete profile by profile id (Amesh Madumalka 2021-10-08)
    // Modified by @Nandana Basnayake
    public void deleteProfileById(int profileId) {
        List<Integer> merchantIds = findProfileMerIdByProfileId(profileId);
        logger.info("Profile Merchant Ids: {}", merchantIds);

        if (merchantIds != null && !merchantIds.isEmpty()) {
            for (Integer merchantId : merchantIds) {
                deleteProfileMerchantById(merchantId);
                logger.info("Merchant Deleted: {}", merchantId);
            }
        }
        template.update(DELETE_PROFILE_BY_ID, new MapSqlParameterSource().addValue("profile_id", profileId));
        logger.debug("Profile deleted");
    }

    // find profile merchant by profile id (Amesh Madumalka 2021-10-08)
    private List<Integer> findProfileMerIdByProfileId(int profileId) {
        List<Integer> profileMerchantIdList = null;
        try {
            profileMerchantIdList = template.queryForList(FIND_PROFILE_MERCHANT_ID_BY_PROFILE_ID,
                    new MapSqlParameterSource().addValue("profile_id", profileId), Integer.class);
        } catch (DataAccessException e) {
            logger.error("Error finding profile merchant id according to profile id");
        }

        return profileMerchantIdList;
    }

    // delete profile merchants by their primary key (Amesh Madumalka 2021-10-08)
    private void deleteProfileMerchantById(int profileMerchantId) {
        template.update(DELETE_PROFILE_MERCHANT_BY_ID,
                new MapSqlParameterSource().addValue("prof_merg_id", profileMerchantId));
        logger.debug("Delete profile merchants by id");
    }

    @Override
    public List<Event> findAllByKey(String param) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String deleteByKey(String key) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String deleteByParams(List<String> params) throws IllegalArgumentException {
        return null;
    }

    // find max Sequence
    public Integer getMaxSeqValue() {
        try {
            Integer val = template.queryForObject(FIND_MAX_SEQUENCE,
                    new MapSqlParameterSource().addValue("", ""), Integer.class);
            logger.info("getMaxSeqValue: {}", val);
            if (val != null) {
                ++val;

            } else
                val = 1;
            logger.info("getMaxSeqValue: {}", val);
            template.update(UPDATE_MAX_SEQUENCE,
                    new MapSqlParameterSource().addValue("cur_val", val));
            return (val);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Incorrect result size");
        }

        return null;
    }

    private int saveDeviceComData(DeviceComStatus deviceComStatus) {
        KeyHolder holder = new GeneratedKeyHolder();
        logger.info("Saving DeviceCom Status");
        logger.info(" DeviceCom Status:Operator1: {}", deviceComStatus.getOperator1());
        logger.info(" DeviceCom Status:Operator2: {}", deviceComStatus.getOperator2());
        logger.info(" DeviceCom Status:Sim1: {}", deviceComStatus.getSim1());
        logger.info(" DeviceCom Status:Sim2: {}", deviceComStatus.getSim2());
        template.update(INSERT_DEVICE_COM_STATUS, new MapSqlParameterSource().addValue("serial_no", deviceComStatus.getSerialNo().trim())
                        .addValue("operator1", deviceComStatus.getOperator1())
                        .addValue("sim1", deviceComStatus.getSim1())
                        .addValue("operator2", deviceComStatus.getOperator2())
                        .addValue("sim2", deviceComStatus.getSim2())
                        .addValue("ref1", deviceComStatus.getRef1())
                        .addValue("ref2", deviceComStatus.getRef2())
                        .addValue("ref3", deviceComStatus.getRef3()),

                holder, new String[]{"com_id"});

        return holder.getKey().intValue();
    }

    private void updateDeviceComData(DeviceComStatus deviceComStatus) {
        logger.info("UpdateDeviceComData: {}", deviceComStatus.getSerialNo());
        template.update(UPDATE_DEVICE_COM_STATUS, new MapSqlParameterSource()
                .addValue("serial_no", deviceComStatus.getSerialNo().trim())
                .addValue("operator1", deviceComStatus.getOperator1())
                .addValue("sim1", deviceComStatus.getSim1())
                .addValue("operator2", deviceComStatus.getOperator2())
                .addValue("sim2", deviceComStatus.getSim2())
                .addValue("ref1", deviceComStatus.getRef1())
                .addValue("ref2", deviceComStatus.getRef2())
                .addValue("ref3", deviceComStatus.getRef3()));
    }

    public boolean updateComStatus(DeviceComStatus devComStatus) {
        logger.info("########## Update Device COM Status ##########################");
        if (devComStatus == null)
            return false;
        try {
            Integer val = template.queryForObject(FIND_COM_DEVICE,
                    new MapSqlParameterSource().addValue("serial_no", devComStatus.getSerialNo().trim()), Integer.class);
            logger.info("Is Serial Available?: {}", val);
            if (val == 0) {
                //Insert
                logger.info("Inserting Device Status... ");
                saveDeviceComData(devComStatus);
                logger.info("Insert Device Status: Success ");
                return true;


            } else {
                //Update
                logger.info("Updating Device Status... ");
                updateDeviceComData(devComStatus);
                logger.info("Update Device Status: Success ");
                return true;
            }
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Incorrect result: {}", e.getMessage());
        }
        logger.info("Updating Device Status: End with an ERROR");
        return false;
    }

    public List<OfflineUserForm> findAllOfflineUser() {
        return template.query(FIND_ALL_OFFLINE_USER, (rs, rowNum) -> {
            OfflineUserForm offlineUserForm = new OfflineUserForm();
            offlineUserForm.setUserName(rs.getString("user_name"));
            offlineUserForm.setPassword(rs.getString("password"));
            offlineUserForm.setActiveUser(rs.getBoolean("active_user"));

            return offlineUserForm;
        });

    }

}
