package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */
public class OtherSettingDto {

    private String autoSettleState;
    private String autoSettleFailed;

    @Override
    public String toString() {
        return "SettlementSettingDto{" +
                "autoSettleState='" + autoSettleState + '\'' +
                ", autoSettleFailed='" + autoSettleFailed + '\'' +
                '}';
    }

    public String getAutoSettleState() {
        return autoSettleState;
    }

    public void setAutoSettleState(String autoSettleState) {
        this.autoSettleState = autoSettleState;
    }

    public String getAutoSettleFailed() {
        return autoSettleFailed;
    }

    public void setAutoSettleFailed(String autoSettleFailed) {
        this.autoSettleFailed = autoSettleFailed;
    }
}
