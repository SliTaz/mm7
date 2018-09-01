package com.zbensoft.mmsmp.common.ra.wo.order;

public class UserOrderRequest extends Head {
	private String pay_type;
	private String pay_money;
	private String res_id;
	private String res_name;
	private String pay_desc;
	private String res_url;
	private String Order_type;

	public String getPay_type() {
		return this.pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getPay_money() {
		return this.pay_money;
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}

	public String getRes_id() {
		return this.res_id;
	}

	public void setRes_id(String res_id) {
		this.res_id = res_id;
	}

	public String getRes_name() {
		return this.res_name;
	}

	public void setRes_name(String res_name) {
		this.res_name = res_name;
	}

	public String getPay_desc() {
		return this.pay_desc;
	}

	public void setPay_desc(String pay_desc) {
		this.pay_desc = pay_desc;
	}

	public String getRes_url() {
		return this.res_url;
	}

	public void setRes_url(String res_url) {
		this.res_url = res_url;
	}

	public String getOrder_type() {
		return this.Order_type;
	}

	public void setOrder_type(String order_type) {
		this.Order_type = order_type;
	}
}
