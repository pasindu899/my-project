package com.aiken.pos.admin.model;

/**
 * Database Model
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-27
 */

public class AmexParam {

    private Integer amexParamId;
    private String amexIp;

    public AmexParam() {
        super();
    }


    public AmexParam(String amexIp) {
        super();
        this.amexIp = amexIp;
    }

    @Override
    public String toString() {
        return "AmexParam[" +
                "amexParamId=" + amexParamId +
                ", amexIp='" + amexIp +
                ']';
    }

    public Integer getAmexParamId() {
        return amexParamId;
    }

    public void setAmexParamId(Integer amexParamId) {
        this.amexParamId = amexParamId;
    }

    public String getAmexIp() {
        return amexIp;
    }

    public void setAmexIp(String amexIp) {
        this.amexIp = amexIp;
    }

}