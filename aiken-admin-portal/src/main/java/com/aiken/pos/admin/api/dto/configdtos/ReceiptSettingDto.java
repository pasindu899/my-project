package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */
public class ReceiptSettingDto {

    private Boolean digitalBillEnable;
    private Boolean customerCopyEnable;
    private Boolean printless;


    public Boolean getDigitalBillEnable() {
        return digitalBillEnable;
    }

    public void setDigitalBillEnable(Boolean digitalBillEnable) {
        this.digitalBillEnable = digitalBillEnable;
    }

    public Boolean getCustomerCopyEnable() {
        return customerCopyEnable;
    }

    public void setCustomerCopyEnable(Boolean customerCopyEnable) {
        this.customerCopyEnable = customerCopyEnable;
    }

    public Boolean getPrintless() {
        return printless;
    }

    public void setPrintless(Boolean printless) {
        this.printless = printless;
    }

}
