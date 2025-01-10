/**
 *
 */
package com.aiken.pos.admin.service;

import com.aiken.pos.admin.constant.BankTypes;
import com.aiken.pos.admin.constant.DeviceType;
import com.aiken.pos.admin.constant.UserConstants;
import com.aiken.pos.admin.util.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nandana Basnayake
 *
 * @created_On Nov 25, 2021
 */
@Service
public class BankTypeService {

    public List<String> getBankTypes() {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        List<String> bankTypes = new ArrayList<String>();

        if (userDetails.getUserRole().equals(UserConstants.ROLE_BANK_USER)) {

            switch (userDetails.getUserGroup()) {
                case BankTypes.BOC:
                    bankTypes.clear();
                    bankTypes.add(BankTypes.BOC);
                    break;
                case BankTypes.COM_BANK:
                    bankTypes.clear();
                    bankTypes.add(BankTypes.COM_BANK);
                    break;
                case BankTypes.DFCC:
                    bankTypes.clear();
                    bankTypes.add(BankTypes.DFCC);
                    break;
                case BankTypes.HNB:
                    bankTypes.clear();
                    bankTypes.add(BankTypes.HNB);
                    break;
			/*case BankTypes.NDB:
				bankTypes.clear();
				bankTypes.add(BankTypes.NDB);
				break;*/
                case BankTypes.PEOPLES:
                    bankTypes.clear();
                    bankTypes.add(BankTypes.PEOPLES);
                    break;
                case BankTypes.SAMPATH:
                    bankTypes.clear();
                    bankTypes.add(BankTypes.SAMPATH);
                    break;
                case BankTypes.SDB:
                    bankTypes.clear();
                    bankTypes.add(BankTypes.SDB);
                    break;
                case BankTypes.SEYLAN:
                    bankTypes.clear();
                    bankTypes.add(BankTypes.SEYLAN);
                    break;
                default:
                    bankTypes.clear();
                    bankTypes.add(BankTypes.BOC);
                    bankTypes.add(BankTypes.COM_BANK);
                    bankTypes.add(BankTypes.DFCC);
                    bankTypes.add(BankTypes.HNB);
                    //bankTypes.add(BankTypes.NDB);
                    bankTypes.add(BankTypes.PEOPLES);
                    bankTypes.add(BankTypes.SAMPATH);
                    bankTypes.add(BankTypes.SDB);
                    bankTypes.add(BankTypes.SEYLAN);
                    break;
            }

        } else {
            bankTypes.clear();

            bankTypes.add(BankTypes.BOC);
            bankTypes.add(BankTypes.COM_BANK);
            bankTypes.add(BankTypes.DFCC);
            bankTypes.add(BankTypes.HNB);
            //bankTypes.add(BankTypes.NDB);
            bankTypes.add(BankTypes.PEOPLES);
            bankTypes.add(BankTypes.SAMPATH);
            bankTypes.add(BankTypes.SDB);
            bankTypes.add(BankTypes.SEYLAN);

        }

        return bankTypes;
    }

    public List<String> getAllBankList() {
        List<String> bankTypes = new ArrayList<String>();
        bankTypes.clear();
        bankTypes.add(BankTypes.BOC);
        bankTypes.add(BankTypes.COM_BANK);
        bankTypes.add(BankTypes.DFCC);
        bankTypes.add(BankTypes.HNB);
        //bankTypes.add(BankTypes.NDB);
        bankTypes.add(BankTypes.PEOPLES);
        bankTypes.add(BankTypes.SAMPATH);
        bankTypes.add(BankTypes.SDB);
        bankTypes.add(BankTypes.SEYLAN);
        return bankTypes;
    }
}
