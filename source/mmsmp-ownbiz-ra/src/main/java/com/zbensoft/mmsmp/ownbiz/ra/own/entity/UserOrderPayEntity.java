package com.zbensoft.mmsmp.ownbiz.ra.own.entity;

import java.io.Serializable;

public class UserOrderPayEntity implements Serializable {
    private String globalMessageid;
    private String cellPhonenum;
    private String serviceUniqueid;
    private String feeType;
    private double fee;
    private int status;
    private String spId;
    private String orderTime;

    private String  keyId;

    public UserOrderPayEntity() {
    }

    public String getGlobalMessageid() {
        return this.globalMessageid;
    }

    public void setGlobalMessageid(String globalMessageid) {
        this.globalMessageid = globalMessageid;
    }

    public String getCellPhonenum() {
        return this.cellPhonenum;
    }

    public void setCellPhonenum(String cellPhonenum) {
        this.cellPhonenum = cellPhonenum;
    }

    public String getServiceUniqueid() {
        return this.serviceUniqueid;
    }

    public void setServiceUniqueid(String serviceUniqueid) {
        this.serviceUniqueid = serviceUniqueid;
    }

    public String getFeeType() {
        return this.feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public double getFee() {
        return this.fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSpId() {
        return this.spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
}
