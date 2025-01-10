package com.aiken.pos.admin.service;

import com.aiken.pos.admin.constant.DeviceType;
import com.aiken.pos.admin.constant.UserConstants;
import com.aiken.pos.admin.util.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class DeviceTypeService {
    private final BankTypeService bankTypeService = new BankTypeService();

    public List<String> getDeviceType() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        List<String> deviceType = new ArrayList<>();

        if (userDetails.getUserRole().equals(UserConstants.ROLE_BANK_USER)) {

            switch (userDetails.getUserGroup()) {
                case DeviceType.N700_Devices:
                    deviceType.clear();
                    deviceType.add(DeviceType.N700_Devices);
                    break;
                case DeviceType.N910_Devices:
                    deviceType.clear();
                    deviceType.add(DeviceType.N910_Devices);
                    break;
                case DeviceType.ALL:
                    deviceType.clear();
                    deviceType.add(DeviceType.ALL);
                    break;

                default:
                    deviceType.clear();
                    deviceType.add(DeviceType.N700_Devices);
                    deviceType.add(DeviceType.N910_Devices);
                    deviceType.add(DeviceType.ALL);
                    break;
            }

        } else {
            deviceType.clear();
            deviceType.add(DeviceType.N700_Devices);
            deviceType.add(DeviceType.N910_Devices);
            deviceType.add(DeviceType.ALL);
        }

        return deviceType;
    }

    public List<String> getBankTypes() {
        return bankTypeService.getBankTypes();
    }

    public List<String> getAllBankList() {
        return bankTypeService.getAllBankList();
    }
}
