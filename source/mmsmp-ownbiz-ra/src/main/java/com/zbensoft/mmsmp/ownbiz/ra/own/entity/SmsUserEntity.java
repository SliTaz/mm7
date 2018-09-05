

package com.zbensoft.mmsmp.ownbiz.ra.own.entity;

import java.io.Serializable;

public class SmsUserEntity implements Serializable {
    private long uniqueid;
    private String productId;
    private String serviceId;
    private String cellPhonenum;
    private String orderTime;
    private String sendTime;
    private String channel;
    private String serviceName;
    private String spid;
    private int fee;
    private int status;
    private int sendCount;

    public SmsUserEntity() {
    }

    public long getUniqueid() {
        return this.uniqueid;
    }

    public void setUniqueid(long uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCellPhonenum() {
        return this.cellPhonenum;
    }

    public void setCellPhonenum(String cellPhonenum) {
        this.cellPhonenum = cellPhonenum;
    }

    public String getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSpid() {
        return this.spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public int getFee() {
        return this.fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSendCount() {
        return this.sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }
}
