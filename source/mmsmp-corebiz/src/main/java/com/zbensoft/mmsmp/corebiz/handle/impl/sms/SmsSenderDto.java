package com.zbensoft.mmsmp.corebiz.handle.impl.sms;

import java.util.ArrayList;
import java.util.List;

public class SmsSenderDto {
	private String sp_productid; // SP产品编号
	private String serviceId; // 产品编号
	private String vasid; // 接入号
	private String ordercode; // 订购指令
	private String cancelordercode; // 取消订购指令
	private String ondemandcode; // 点播指令
	private String fee;
	private String servicename; // 产品名称
	private String vaspid; // 企业代码
	private int cpid;
	private Integer uniqueid;
	private int type;
	private String vaspname = ""; // 企业名称
	private String businessphone = ""; // 业务联系电话
	private List<SmsSenderDto> products = new ArrayList();

	public Integer getUniqueid() {
		return this.uniqueid;
	}

	public void setUniqueid(Integer uniqueid) {
		this.uniqueid = uniqueid;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getServicename() {
		return this.servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getSp_productid() {
		return this.sp_productid;
	}

	public void setSp_productid(String sp_productid) {
		this.sp_productid = sp_productid;
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

	public List<SmsSenderDto> getProducts() {
		return this.products;
	}

	public void setProducts(List<SmsSenderDto> products) {
		this.products = products;
	}

	public String getVaspname() {
		return this.vaspname;
	}

	public void setVaspname(String vaspname) {
		this.vaspname = vaspname;
	}

	public String getBusinessphone() {
		return this.businessphone;
	}

	public void setBusinessphone(String businessphone) {
		this.businessphone = businessphone;
	}
}
