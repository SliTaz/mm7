
package com.zbensoft.mmsmp.ownbiz.ra.own.entity;

public class UserOrderEntity {
    private String cellPhoneNo = "";
    private String chargeParty = "";
    private String serviceUniqueId = "";
    private String orderDate = "";
    private String orderMethod = "4";
    private String feeType = "";
    private String fee = "";
    private String spOrderId = "";
    private String notifySpFlag = "";
    private String provinceCode = "";

    public UserOrderEntity() {
    }

    public String getCellPhoneNo() {
        return this.cellPhoneNo;
    }

    public void setCellPhoneNo(String cellPhoneNo) {
        this.cellPhoneNo = cellPhoneNo;
    }

    public String getChargeParty() {
        return this.chargeParty;
    }

    public void setChargeParty(String chargeParty) {
        this.chargeParty = chargeParty;
    }

    public String getServiceUniqueId() {
        return this.serviceUniqueId;
    }

    public void setServiceUniqueId(String serviceUniqueId) {
        this.serviceUniqueId = serviceUniqueId;
    }

    public String getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderMethod() {
        return this.orderMethod;
    }

    public void setOrderMethod(String orderMethod) {
        this.orderMethod = orderMethod;
    }

    public String getFeeType() {
        return this.feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFee() {
        return this.fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSpOrderId() {
        return this.spOrderId;
    }

    public void setSpOrderId(String spOrderId) {
        this.spOrderId = spOrderId;
    }

    public String getNotifySpFlag() {
        return this.notifySpFlag;
    }

    public void setNotifySpFlag(String notifySpFlag) {
        this.notifySpFlag = notifySpFlag;
    }

    public String getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
