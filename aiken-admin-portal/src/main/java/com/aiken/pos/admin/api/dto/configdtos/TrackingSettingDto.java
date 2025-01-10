package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */
public class TrackingSettingDto {

    private Boolean activityHistory;
    private Integer maxSuccActivityCount;
    private Integer maxFailActivityCount;

    public Boolean getActivityHistory() {
        return activityHistory;
    }

    public void setActivityHistory(Boolean activityHistory) {
        this.activityHistory = activityHistory;
    }

    public Integer getMaxSuccActivityCount() {
        return maxSuccActivityCount;
    }

    public void setMaxSuccActivityCount(Integer maxSuccActivityCount) {
        this.maxSuccActivityCount = maxSuccActivityCount;
    }

    public Integer getMaxFailActivityCount() {
        return maxFailActivityCount;
    }

    public void setMaxFailActivityCount(Integer maxFailActivityCount) {
        this.maxFailActivityCount = maxFailActivityCount;
    }
}
