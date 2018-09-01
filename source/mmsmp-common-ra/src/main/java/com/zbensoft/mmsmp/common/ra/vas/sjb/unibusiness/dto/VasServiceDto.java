package com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness.dto;

public class VasServiceDto {
	private String sp_productid;
	private String serviceId;
	private String vasid;
	private String ordercode;
	private String cancelordercode;
	private String ondemandcode;
	private String fee;
	private String servicename;
	private String vaspid;
	private int cpid;
	private String sp_notify_ip;
	private String sp_notity_port;
	private String orderRelationurl;

	public String getSp_productid() {
		return this.sp_productid;
	}

	public void setSp_productid(String spProductid) {
		this.sp_productid = spProductid;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getVasid() {
		return this.vasid;
	}

	public void setVasid(String vasid) {
		this.vasid = vasid;
	}

	public String getOrdercode() {
		return this.ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getCancelordercode() {
		return this.cancelordercode;
	}

	public void setCancelordercode(String cancelordercode) {
		this.cancelordercode = cancelordercode;
	}

	public String getOndemandcode() {
		return this.ondemandcode;
	}

	public void setOndemandcode(String ondemandcode) {
		this.ondemandcode = ondemandcode;
	}

	public String getFee() {
		return this.fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getServicename() {
		return this.servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getVaspid() {
		return this.vaspid;
	}

	public void setVaspid(String vaspid) {
		this.vaspid = vaspid;
	}

	public int getCpid() {
		return this.cpid;
	}

	public void setCpid(int cpid) {
		this.cpid = cpid;
	}

	public String getSp_notify_ip() {
		return this.sp_notify_ip;
	}

	public void setSp_notify_ip(String spNotifyIp) {
		this.sp_notify_ip = spNotifyIp;
	}

	public String getSp_notity_port() {
		return this.sp_notity_port;
	}

	public void setSp_notity_port(String spNotityPort) {
		this.sp_notity_port = spNotityPort;
	}

	public String getOrderRelationurl() {
		return this.orderRelationurl;
	}

	public void setOrderRelationurl(String orderRelationurl) {
		this.orderRelationurl = orderRelationurl;
	}
}
