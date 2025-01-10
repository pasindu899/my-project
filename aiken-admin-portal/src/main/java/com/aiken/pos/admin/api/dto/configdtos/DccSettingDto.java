package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-18
 */
public class DccSettingDto {
    private Boolean dccEnable;
    private String dccTpdu;
    private String dccNii;
    private String oriInsName;

    public Boolean getDccEnable() {
        return dccEnable;
    }

    public void setDccEnable(Boolean dccEnable) {
        this.dccEnable = dccEnable;
    }

    public String getDccTpdu() {
        return dccTpdu;
    }

    public void setDccTpdu(String dccTpdu) {
        this.dccTpdu = dccTpdu;
    }

    public String getDccNii() {
        return dccNii;
    }

    public void setDccNii(String dccNii) {
        this.dccNii = dccNii;
    }

    public String getOriInsName() {
        return oriInsName;
    }

    public void setOriInsName(String oriInsName) {
        this.oriInsName = oriInsName;
    }
}
