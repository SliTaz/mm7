package com.zbensoft.mmsmp.ownbiz.ra.own.entity;

import java.io.Serializable;

public class VasServiceRelationEntity implements Serializable {
    private String spProductId; //sp产品编号
    private String spId;		//
    private String productName;	//产品名称
    private String vasserviceUniqueId; //产品编号
    private String accessNumber; 	//接入号
    private String serviceId;		//产品业务编号
    private String serviceName;		//产品业务名称	
    private long cpId;
    private int cpType;
    private String accessUrl;		
    private double orderFee;	//订购费率
    private String orderCode;	//订购指令
    private String cancelOrderCode;
    private String onDemandCode;	//点播指令
    private double dbFee;			//点播费率
    private String feeType;			//product_info中的orderType
    private String isNotifySms;
    private String reportUrl;		//给sp返回状态报告的url
    private String clientLinkManTel;

    public VasServiceRelationEntity() {
    }

    public String getSpProductId() {
        return this.spProductId;
    }

    public void setSpProductId(String spProductId) {
        this.spProductId = spProductId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVasserviceUniqueId() {
        return this.vasserviceUniqueId;
    }

    public void setVasserviceUniqueId(String vasserviceUniqueId) {
        this.vasserviceUniqueId = vasserviceUniqueId;
    }

    public String getAccessNumber() {
        return this.accessNumber;
    }

    public void setAccessNumber(String accessNumber) {
        this.accessNumber = accessNumber;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getCpId() {
        return this.cpId;
    }

    public void setCpId(long cpId) {
        this.cpId = cpId;
    }

    public int getCpType() {
        return this.cpType;
    }

    public void setCpType(int cpType) {
        this.cpType = cpType;
    }

    public String getAccessUrl() {
        return this.accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public double getOrderFee() {
        return this.orderFee;
    }

    public void setOrderFee(double orderFee) {
        this.orderFee = orderFee;
    }

    public double getDbFee() {
        return this.dbFee;
    }

    public void setDbFee(double dbFee) {
        this.dbFee = dbFee;
    }

    public String getFeeType() {
        return this.feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCancelOrderCode() {
        return this.cancelOrderCode;
    }

    public void setCancelOrderCode(String cancelOrderCode) {
        this.cancelOrderCode = cancelOrderCode;
    }

    public String getOnDemandCode() {
        return this.onDemandCode;
    }

    public void setOnDemandCode(String onDemandCode) {
        this.onDemandCode = onDemandCode;
    }

    public String getSpId() {
        return this.spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getIsNotifySms() {
        return this.isNotifySms;
    }

    public void setIsNotifySms(String isNotifySms) {
        this.isNotifySms = isNotifySms;
    }

    public String getReportUrl() {
        return this.reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getClientLinkManTel() {
        return this.clientLinkManTel;
    }

    public void setClientLinkManTel(String clientLinkManTel) {
        this.clientLinkManTel = clientLinkManTel;
    }
}
