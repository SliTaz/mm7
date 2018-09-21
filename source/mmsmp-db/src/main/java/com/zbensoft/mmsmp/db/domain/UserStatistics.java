package com.zbensoft.mmsmp.db.domain;

public class UserStatistics {
	private String time;
	private String companyCode;
	private String spInfoId;
	private int sendCount; //发送总条数
	private int orderCount; //订购总数
	private int onDemandCount; //点播数
	private double spTotalCost; //sp总费用
	private double orderFee; // 订购费率
	private double onDemandFee; //点播费率
	private String monthStart; //开始月份
	private String monthEnd; 	//结束月份
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getSpInfoId() {
		return spInfoId;
	}
	public void setSpInfoId(String spInfoId) {
		this.spInfoId = spInfoId;
	}
	public int getSendCount() {
		return sendCount;
	}
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public int getOnDemandCount() {
		return onDemandCount;
	}
	public void setOnDemandCount(int onDemandCount) {
		this.onDemandCount = onDemandCount;
	}
	public double getSpTotalCost() {
		return spTotalCost;
	}
	public void setSpTotalCost(double spTotalCost) {
		this.spTotalCost = spTotalCost;
	}
	public double getOrderFee() {
		return orderFee;
	}
	public void setOrderFee(double orderFee) {
		this.orderFee = orderFee;
	}
	public double getOnDemandFee() {
		return onDemandFee;
	}
	public void setOnDemandFee(double onDemandFee) {
		this.onDemandFee = onDemandFee;
	}
	public String getMonthStart() {
		return monthStart;
	}
	public void setMonthStart(String monthStart) {
		this.monthStart = monthStart;
	}
	public String getMonthEnd() {
		return monthEnd;
	}
	public void setMonthEnd(String monthEnd) {
		this.monthEnd = monthEnd;
	}
}
