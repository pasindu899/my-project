package com.aiken.pos.admin.model;


/**
 * @author Nandana Basnayake
 * @version 1.0
 * @since 2023-09-21
 */

public class DeviceComStatus {

    private Integer comId;
    private String serialNo;
    private String operator1;
    private String operator2;
    private String sim1;
    private String sim2;
    private String ref1;
    private String ref2;
    private String ref3;

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getOperator1() {
        return operator1;
    }

    public void setOperator1(String operator1) {
        this.operator1 = operator1;
    }

    public String getOperator2() {
        return operator2;
    }

    public void setOperator2(String operator2) {
        this.operator2 = operator2;
    }

    public String getSim1() {
        return sim1;
    }

    public void setSim1(String sim1) {
        this.sim1 = sim1;
    }

    public String getSim2() {
        return sim2;
    }

    public void setSim2(String sim2) {
        this.sim2 = sim2;
    }

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public String getRef2() {
        return ref2;
    }

    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }

    public String getRef3() {
        return ref3;
    }

    public void setRef3(String ref3) {
        this.ref3 = ref3;
    }
}
