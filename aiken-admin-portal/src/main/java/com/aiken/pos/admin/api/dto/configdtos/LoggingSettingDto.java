package com.aiken.pos.admin.api.dto.configdtos;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-October-10
 */
public class LoggingSettingDto {

    private String logLevel;
    private Boolean sdkLog;
    private Boolean emvLog;
    private String maxLogFileSize;
    private String logDeleteTime;
    private String logMinFreeSpace;
    private String logFreeUpLevel;

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public Boolean getSdkLog() {
        return sdkLog;
    }

    public void setSdkLog(Boolean sdkLog) {
        this.sdkLog = sdkLog;
    }

    public Boolean getEmvLog() {
        return emvLog;
    }

    public void setEmvLog(Boolean emvLog) {
        this.emvLog = emvLog;
    }

    public String getMaxLogFileSize() {
        return maxLogFileSize;
    }

    public void setMaxLogFileSize(String maxLogFileSize) {
        this.maxLogFileSize = maxLogFileSize;
    }

    public String getLogDeleteTime() {
        return logDeleteTime;
    }

    public void setLogDeleteTime(String logDeleteTime) {
        this.logDeleteTime = logDeleteTime;
    }

    public String getLogMinFreeSpace() {
        return logMinFreeSpace;
    }

    public void setLogMinFreeSpace(String logMinFreeSpace) {
        this.logMinFreeSpace = logMinFreeSpace;
    }

    public String getLogFreeUpLevel() {
        return logFreeUpLevel;
    }

    public void setLogFreeUpLevel(String logFreeUpLevel) {
        this.logFreeUpLevel = logFreeUpLevel;
    }
}
