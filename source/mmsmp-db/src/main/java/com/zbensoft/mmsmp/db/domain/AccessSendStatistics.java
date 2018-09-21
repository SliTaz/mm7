package com.zbensoft.mmsmp.db.domain;

public class AccessSendStatistics {
	private String serviceName;
	private String productInfoId;
	private String productName;
	private String cooperName;
	private String keyId;
	private int ondemandCount;
	private double ondemandFee;
	private int orderCount;
	private double orderFee;
	private double ondemandTotal;
	private double orderTotal;
	private String timeStart;
	private String timeEnd;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getProductInfoId() {
		return productInfoId;
	}
	public void setProductInfoId(String productInfoId) {
		this.productInfoId = productInfoId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCooperName() {
		return cooperName;
	}
	public void setCooperName(String cooperName) {
		this.cooperName = cooperName;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public int getOndemandCount() {
		return ondemandCount;
	}
	public void setOndemandCount(int ondemandCount) {
		this.ondemandCount = ondemandCount;
	}
	public double getOndemandFee() {
		return ondemandFee;
	}
	public void setOndemandFee(double ondemandFee) {
		this.ondemandFee = ondemandFee;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public double getOrderFee() {
		return orderFee;
	}
	public void setOrderFee(double orderFee) {
		this.orderFee = orderFee;
	}
	public double getOndemandTotal() {
		return ondemandTotal;
	}
	public void setOndemandTotal(double ondemandTotal) {
		this.ondemandTotal = ondemandTotal;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
}
