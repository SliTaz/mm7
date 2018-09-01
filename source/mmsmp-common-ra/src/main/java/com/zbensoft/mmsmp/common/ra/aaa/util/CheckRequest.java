package com.zbensoft.mmsmp.common.ra.aaa.util;

public class CheckRequest {
	private String user_number;
	private String sp_product_id;
	private String sp_id;
	private String service_id;
	private String src_SequenceNumber;
	private String ServiceType;
	private String linkid;

	public CheckRequest() {
	}

	public CheckRequest(String user_number, String sp_product_id, String sp_id, String service_id, String src_SequenceNumber, String ServiceType, String linkid) {
		this.user_number = user_number;
		this.sp_id = sp_id;
		this.sp_product_id = sp_product_id;
		this.service_id = service_id;
		this.src_SequenceNumber = src_SequenceNumber;
		this.ServiceType = ServiceType;
		this.linkid = linkid;
	}

	public String getService_id() {
		return this.service_id;
	}

	public void setService_id(String serviceId) {
		this.service_id = serviceId;
	}

	public String getLinkID() {
		return this.linkid;
	}

	public void setLinkID(String linkID) {
		this.linkid = linkID;
	}

	public String getServiceType() {
		return this.ServiceType;
	}

	public void setServiceType(String serviceType) {
		this.ServiceType = serviceType;
	}

	public String getUser_number() {
		return this.user_number;
	}

	public void setUser_number(String userNumber) {
		this.user_number = userNumber;
	}

	public String getSp_product_id() {
		return this.sp_product_id;
	}

	public void setSp_product_id(String spProductId) {
		this.sp_product_id = spProductId;
	}

	public String getSp_id() {
		return this.sp_id;
	}

	public void setSp_id(String spId) {
		this.sp_id = spId;
	}

	public String getSrc_SequenceNumber() {
		return this.src_SequenceNumber;
	}

	public void setSrc_SequenceNumber(String srcSequenceNumber) {
		this.src_SequenceNumber = srcSequenceNumber;
	}
}
