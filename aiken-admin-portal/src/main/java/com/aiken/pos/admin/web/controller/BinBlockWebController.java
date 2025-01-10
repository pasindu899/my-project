package com.aiken.pos.admin.web.controller;

import com.aiken.pos.admin.constant.ActionType;
import com.aiken.pos.admin.constant.CommonConstant;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.constant.ErrorCode;
import com.aiken.pos.admin.helper.SessionHelper;
import com.aiken.pos.admin.model.BinConfig;
import com.aiken.pos.admin.service.CurrencyService;
import com.aiken.pos.admin.service.DeviceService;
import com.aiken.pos.admin.service.MerchantTypeService;
import com.aiken.pos.admin.util.LoginUserUtil;
import com.aiken.pos.admin.web.form.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;


/**
 * BINBLOCK WEB Controller
 *
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2023-03-07
 */
@Controller
public class BinBlockWebController {

    private Logger logger = LoggerFactory.getLogger(BinBlockWebController.class);
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private MerchantTypeService merchantTypeService;
    @Autowired
    private CurrencyService currencyService;


    private boolean isBinValid = false;

    private boolean isBinError = false;
    private String newEndBin="";
    private String extEndBin="";

    // modify new bin rule page
    //new-rule-modify-to-cart

    //new-rule-modify-to-cart



    // copy_bin_rule_to_current_cart
    @PostMapping(value = Endpoint.URL_CURRENT_MODIFY_TO_CART,params="action=copy")
    public String merchantModifyCartOnModify(HttpServletRequest request,
                                             @ModelAttribute @Valid MerchantForm merchantForm, BindingResult result, Model model) {

        logger.info("Start copying current bin-rule data");

        String mid = request.getParameter("mid");

        logger.info("MID from Form: " + merchantForm.getMid() );
        // load login user
        LoginUserUtil.loadLoginUser(model);


        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        //MerchantForm merchantForm = new MerchantForm();
        CommonDataForm commonDataForm = new CommonDataForm();



        model.addAttribute("deviceForm", deviceForm);
        model.addAttribute("merchantForm", merchantForm);
        model.addAttribute("merchantTypes", merchantTypeService.getMerchantTypes());
        model.addAttribute("saleMerchantTypes", merchantTypeService.getSaleMerchantTypes());
        model.addAttribute("currencies", currencyService.getCurrencies());
        model.addAttribute("binrules", formInfo.getMerchantBin());





        List<BinConfigForm> merchantBin = new ArrayList<>(0);

        if (formInfo.getMerchantBin() != null && merchantForm.getMid()!=null && merchantForm.getTid()!=null) {
            formInfo.getMerchantBin().forEach(mb -> {
                if (mb.getMid().equalsIgnoreCase(merchantForm.getMid()) && mb.getTid().equalsIgnoreCase(merchantForm.getTid())){
                    merchantBin.add(mb);
                }
            });
        }else
            logger.info("No Bin data to copy");
        if (merchantBin.size()==0){
            logger.info("No Bin data to copy");
        }
        logger.info("Merchant Bin Size to be copied: " + merchantBin.size());
        FormInfo formBinInfo = SessionHelper.getFormBinInSession(request);
        formBinInfo.setMerchantBin(merchantBin);
        logger.info("Copied Merchant Bin Size: " + formBinInfo.getMerchantBin().size());

        String str = new Gson().toJson(formBinInfo.getMerchantBin());
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        cb.setContents(strSel, null);
        logger.info ("JsonData-BIN: " + str);

        //return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_MERCHANT;
        return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_CURRENT_MERCHANT; //Endpoint.PAGE_MODIFY_NEW_MERCHANT;
    }

    // paste_bin_rule_to_current_cart
    @PostMapping(value = Endpoint.URL_CURRENT_MODIFY_TO_CART,params="action=import")
    public String merchantModifyCartImportBin(HttpServletRequest request,
                                             @ModelAttribute @Valid MerchantForm merchantForm, BindingResult result, Model model) {

        logger.info("Applying copied bin-rule data");

        logger.info("MID from Form: " + merchantForm.getMid() );
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formBinInfo = SessionHelper.getFormBinInSession(request);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        //MerchantForm merchantForm = new MerchantForm();
        CommonDataForm commonDataForm = new CommonDataForm();

        List<BinConfigForm> merchantBin = new ArrayList<>(0);

        if (merchantForm.getMid()==null && merchantForm.getTid()==null)
        {
            logger.info("MID-TID Not found");
            model.addAttribute("message","MID/TID Not found. Please enter MID/TID first");
            model.addAttribute("back_link", Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        if (formBinInfo.getMerchantBin() != null && merchantForm.getMid()!=null && merchantForm.getTid()!=null) {
            formBinInfo.getMerchantBin().forEach(mb -> {

                BinConfigForm bConfig = new BinConfigForm();
                //bConfig.setAction()
                bConfig.setAction(mb.getAction());
                bConfig.setBinEndFrom(mb.getBinEndFrom());
                bConfig.setBinStartFrom(mb.getBinStartFrom());
                bConfig.setCardType(mb.getCardType());
                bConfig.setMid(merchantForm.getMid());
                bConfig.setTid(merchantForm.getTid());
                bConfig.setTransType(mb.getTransType());
                bConfig.setType(mb.getType());
                bConfig.setMerchantId(merchantForm.getMerchantId());


                List<String> isValidBinRule = isValidateBinRule(request, bConfig);
                if (isValidBinRule.get(0).equals(CommonConstant.TRUE)) {
                    logger.info("VALID BIN RULE: Accepted");
                    // add merchant to cart
                    addBinToCart(request, bConfig);
                    commonDataForm.setTid(bConfig.getTid());
                    commonDataForm.setMid(bConfig.getMid());
                    formInfo.setCommonDataForm(commonDataForm);

                    logger.info("NEW BIN RULE ADDED");

                }else {

                    isBinError = true;

                }

            });
        }else
            logger.info("No Bin data to copy");

        if (merchantBin.size()==0){
            logger.info("No Bin data to copy");
        }
        /*if (isBinError==true){
            logger.info("INVALID BIN RULE: Rejected");
            model.addAttribute("message","BIN Validation Failed");
            model.addAttribute("back_link", Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;

        }*/
        logger.info("Merchant Bin Size to be copied: " + merchantBin.size());


        model.addAttribute("deviceForm", deviceForm);
        model.addAttribute("merchantForm", merchantForm);
        model.addAttribute("merchantTypes", merchantTypeService.getMerchantTypes());
        model.addAttribute("saleMerchantTypes", merchantTypeService.getSaleMerchantTypes());
        model.addAttribute("currencies", currencyService.getCurrencies());
        model.addAttribute("binrules", formInfo.getMerchantBin());

        //return Endpoint.REDIRECT_NAME + Endpoint.URL_ADD_NEW_MERCHANT;
        return Endpoint.REDIRECT_NAME + Endpoint.URL_MODIFY_CURRENT_MERCHANT; //Endpoint.PAGE_MODIFY_NEW_MERCHANT;
    }


    @PostMapping(value = Endpoint.URL_CURRENT_ADD_TO_CART,params="action=copy") //current-add-to-cart
    public String copyNewBinRuleToModifyCart(HttpServletRequest request, @ModelAttribute @Valid MerchantForm merchantForm,
                                                Model model) {

        logger.info("Start copying current bin-rule data");

        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        CommonDataForm commonData  = formInfo.getCommonDataForm();

        logger.info("MerchantBin Size:" + formInfo.getMerchantBin().size());

        List<BinConfigForm> merchantBin = new ArrayList<>(0);

        if (formInfo.getMerchantBin() != null && merchantForm.getMid()!=null && merchantForm.getTid()!=null) {
            formInfo.getMerchantBin().forEach(mb -> {
                if (mb.getMid().equalsIgnoreCase(merchantForm.getMid()) && mb.getTid().equalsIgnoreCase(merchantForm.getTid())){
                    merchantBin.add(mb);
                }
            });
        }else
            logger.info("No Bin data to copy");
        if (merchantBin.size()==0){
            logger.info("No Bin data to copy");
        }
        logger.info("Merchant Bin Size to be copied: " + merchantBin.size());
        FormInfo formBinInfo = SessionHelper.getFormBinInSession(request);
        formBinInfo.setMerchantBin(merchantBin);
        logger.info("Copied Merchant Bin Size: " + formBinInfo.getMerchantBin().size());

        String str = new Gson().toJson(formBinInfo.getMerchantBin());
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        cb.setContents(strSel, null);
        logger.info ("JsonData-BIN: " + str);


        // merchantForm.setAmexIp(CommonConstant.DEFAULT_AMEX_IP);
        model.addAttribute("merchantForm", merchantForm);
        model.addAttribute("merchantTypes", merchantTypeService.getMerchantTypes());
        model.addAttribute("saleMerchantTypes", merchantTypeService.getSaleMerchantTypes());
//		model.addAttribute("selectedMerchant", selectedMerchant);
        model.addAttribute("currencies", currencyService.getCurrencies());
        model.addAttribute("binrules", formInfo.getMerchantBin());

        return Endpoint.PAGE_ANOTHER_MERCHANT;
    }

    @PostMapping(value = Endpoint.URL_CURRENT_ADD_TO_CART,params="action=import") //current-add-to-cart
    public String importNewBinRuleToModifyCart(HttpServletRequest request, @ModelAttribute @Valid MerchantForm merchantForm,
                                             BindingResult result, Model model) {

        logger.info("Applying copied bin-rule data");

        logger.info("MID from Form: " + merchantForm.getMid() );
        // load login user
        LoginUserUtil.loadLoginUser(model);

        FormInfo formBinInfo = SessionHelper.getFormBinInSession(request);

        FormInfo formInfo = SessionHelper.getFormInSession(request);
        DeviceForm deviceForm = formInfo.getDeviceForm();
        //MerchantForm merchantForm = new MerchantForm();
        CommonDataForm commonDataForm = new CommonDataForm();

        List<BinConfigForm> merchantBin = new ArrayList<>(0);

        if (merchantForm.getMid()==null && merchantForm.getTid()==null)
        {
            logger.info("MID-TID Not found");
            model.addAttribute("message","MID/TID Not found. Please enter MID/TID first");
            model.addAttribute("back_link", Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;
        }

        if (formBinInfo.getMerchantBin() != null && merchantForm.getMid()!=null && merchantForm.getTid()!=null) {
            formBinInfo.getMerchantBin().forEach(mb -> {

                BinConfigForm bConfig = new BinConfigForm();
                //bConfig.setAction()
                bConfig.setAction(mb.getAction());
                bConfig.setBinEndFrom(mb.getBinEndFrom());
                bConfig.setBinStartFrom(mb.getBinStartFrom());
                bConfig.setCardType(mb.getCardType());
                bConfig.setMid(merchantForm.getMid());
                bConfig.setTid(merchantForm.getTid());
                bConfig.setTransType(mb.getTransType());
                bConfig.setType(mb.getType());
                bConfig.setMerchantId(merchantForm.getMerchantId());


                List<String> isValidBinRule = isValidateBinRule(request, bConfig);
                if (isValidBinRule.get(0).equals(CommonConstant.TRUE)) {
                    logger.info("VALID BIN RULE: Accepted");
                    // add merchant to cart
                    addBinToCart(request, bConfig);
                    commonDataForm.setTid(bConfig.getTid());
                    commonDataForm.setMid(bConfig.getMid());
                    formInfo.setCommonDataForm(commonDataForm);

                    logger.info("NEW BIN RULE ADDED");

                }else {


                }

            });
        }else
            logger.info("No Bin data to copy");

        if (merchantBin.size()==0){
            logger.info("No Bin data to copy");
        }
        /*if (isBinError==true){
            logger.info("INVALID BIN RULE: Rejected");
            model.addAttribute("message","BIN Validation Failed");
            model.addAttribute("back_link", Endpoint.URL_ADD_NEW_MERCHANT);
            return Endpoint.PAGE_OPERATION_FAIL;

        }*/
        logger.info("Merchant Bin Size to be copied: " + merchantBin.size());




        // merchantForm.setAmexIp(CommonConstant.DEFAULT_AMEX_IP);
        model.addAttribute("merchantForm", merchantForm);
        model.addAttribute("merchantTypes", merchantTypeService.getMerchantTypes());
        model.addAttribute("saleMerchantTypes", merchantTypeService.getSaleMerchantTypes());
//		model.addAttribute("selectedMerchant", selectedMerchant);
        model.addAttribute("currencies", currencyService.getCurrencies());
        model.addAttribute("binrules", formInfo.getMerchantBin());

        return Endpoint.PAGE_ANOTHER_MERCHANT;
    }


    // Bin Block add to cart
    private void addBinToCart(HttpServletRequest request, BinConfigForm binConfigForm) {

        FormInfo formInfo = SessionHelper.getFormInSession(request);

        if (formInfo.getMerchantBin() != null)
            logger.info("ADD: Before Cart Size: " + formInfo.getMerchantBin().size());
        logger.info("Rules for merchant: " + binConfigForm.getMerchantId());

        // to be insert merchant
        binConfigForm.setFormAction(ActionType.INSERT);
        binConfigForm.setFormId(deviceService.getMaxSequence());
        // add merchant to cart
        formInfo.getMerchantBin().add(binConfigForm);

        if (formInfo.getMerchantBin() != null)
            logger.info("After Cart Size: " + formInfo.getMerchantBin().size());
    }

    // Bin Block validate BIN Rule

    private List<String> isValidateBinRule(HttpServletRequest request, BinConfigForm binConfigForm) {


        logger.info("::::::::::::Validating new BIN Rule >>>>>>>>>>>");
        FormInfo formInfo = SessionHelper.getFormInSession(request);

        List<String> res = new ArrayList<String>();  //[0] - Status , [1] - Description

        isBinValid = true;
        res.add(0, CommonConstant.TRUE);
        res.add(1, CommonConstant.VALID);
        newEndBin = "";
        extEndBin = "";


        if (formInfo.getMerchantBin() != null) {

            formInfo.getMerchantBin().forEach(mb -> {
                logger.info("New Rule:>> Card Type:" + binConfigForm.getCardType() +"| Trans Type: " + binConfigForm.getTransType() +"| Type: " + binConfigForm.getType() +"| SBIN: " + binConfigForm.getBinStartFrom() +"| EBIN: " + binConfigForm.getBinEndFrom() + "| MID:" + binConfigForm.getMid() + "| TID:" + binConfigForm.getTid() );
                logger.info("Ext Rule:>> Card Type:" + mb.getCardType() +"| Trans Type: " + mb.getTransType()+"| Type: " + mb.getType()+"| SBIN: " + mb.getBinStartFrom() +"| EBIN: " + mb.getBinEndFrom() + mb.getBinEndFrom() + "| MID:" + mb.getMid() + "| TID:" + mb.getTid());
                if(mb.getCardType().equals(binConfigForm.getCardType()) && mb.getTid().equalsIgnoreCase(binConfigForm.getTid()) && (mb.getTransType().equals(binConfigForm.getTransType())  || mb.getTransType().equals("ALL") || binConfigForm.getTransType().equals("ALL"))){

                    if((binConfigForm.getType().equals("SINGLE"))) {
                        newEndBin = binConfigForm.getBinStartFrom();
                    }else
                        newEndBin = binConfigForm.getBinEndFrom();

                    if((mb.getType().equals("SINGLE"))) {
                        extEndBin = mb.getBinStartFrom();
                    }else
                        extEndBin = mb.getBinEndFrom();


                    logger.info("newEndBin:" + newEndBin +"| extEndBin: " + extEndBin);


                    if(mb.getType().equals("FULL_RANGE") && (binConfigForm.getType().equals("FULL_RANGE")) ) {
                        res.add(0, CommonConstant.FALSE);
                        res.add(1, mb.getAction() +" (" + mb.getType() + " Type)");
                        return ;
                    }else if(mb.getType().equals("FULL_RANGE") && (!binConfigForm.getType().equals("FULL_RANGE"))) {
                        res.add(0, CommonConstant.FALSE);
                        res.add(1, mb.getAction() +" (" + mb.getType() + " Type)");
                        return ;
                    }else if(!mb.getType().equals("FULL_RANGE") && (binConfigForm.getType().equals("FULL_RANGE"))) {
                        res.add(0, CommonConstant.FALSE);
                        res.add(1, mb.getAction() +" (" + mb.getType() + " Type)");
                        return ;
                    }else {
                        logger.info(( Integer.parseInt(mb.getBinStartFrom()) +  " <= " +  Integer.parseInt(binConfigForm.getBinStartFrom()) + " && " + Integer.parseInt(binConfigForm.getBinStartFrom()) +  " <= " +   Integer.parseInt(extEndBin)));
                        logger.info(( Integer.parseInt(mb.getBinStartFrom()) +" <= " + Integer.parseInt(newEndBin) + " && " + Integer.parseInt(newEndBin) + " <= " +  Integer.parseInt(extEndBin)));

                        if (Integer.parseInt(mb.getBinStartFrom()) > Integer.parseInt(binConfigForm.getBinStartFrom()) || Integer.parseInt(binConfigForm.getBinStartFrom()) > Integer.parseInt(this.extEndBin)) {
                            if (Integer.parseInt(mb.getBinStartFrom()) <= Integer.parseInt(this.newEndBin) && Integer.parseInt(this.newEndBin) <= Integer.parseInt(this.extEndBin)) {
                                res.add(0, CommonConstant.FALSE);
                                res.add(1, mb.getAction());
                                return ;
                            }
                            return;
                        }
                    }
                }else {
                    return;
                }
                res.add(0, CommonConstant.FALSE);
                res.add(1, mb.getAction());
            });

        }
        logger.info("Valid BIN Rule: " + res.get(0));
        return res;
    }

}
