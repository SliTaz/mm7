package com.zbensoft.mmsmp.common.ra.wo.quitOrder;

public class QuitUserOrderRequest {
	private String sequence_id;
	private String userid;
	private String userName;
	private String mobile;
	private String res_id;
	private String Order_type;

	public String getSequence_id() {
		return this.sequence_id;
	}

	public void setSequence_id(String sequence_id) {
		this.sequence_id = sequence_id;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRes_id() {
		return this.res_id;
	}

	public void setRes_id(String res_id) {
		this.res_id = res_id;
	}

	public String getOrder_type() {
		return this.Order_type;
	}

	public void setOrder_type(String order_type) {
		this.Order_type = order_type;
	}
}
