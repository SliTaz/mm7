package com.zbensoft.mmsmp.common.ra.wo.order;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public class UserOrderResponse {
	private String flag;
	private String detail;
	private String recordsn;
	private String service_code;
	private String svcnum;
	private String sys_sequence;
	private String resp_date;
	private String pay_money;
	private String pay_type;
	private String sp_OrderId;

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getRecordsn() {
		return this.recordsn;
	}

	public void setRecordsn(String recordsn) {
		this.recordsn = recordsn;
	}

	public String getService_code() {
		return this.service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getSvcnum() {
		return this.svcnum;
	}

	public void setSvcnum(String svcnum) {
		this.svcnum = svcnum;
	}

	public String getSys_sequence() {
		return this.sys_sequence;
	}

	public void setSys_sequence(String sys_sequence) {
		this.sys_sequence = sys_sequence;
	}

	public String getResp_date() {
		return this.resp_date;
	}

	public void setResp_date(String resp_date) {
		this.resp_date = resp_date;
	}

	public String getPay_money() {
		return this.pay_money;
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}

	public String getPay_type() {
		return this.pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getSp_OrderId() {
		return this.sp_OrderId;
	}

	public void setSp_OrderId(String sp_OrderId) {
		this.sp_OrderId = sp_OrderId;
	}

	public static UserOrderResponse UserOrderResponseForXml(String xmlstr) {
		UserOrderResponse re = new UserOrderResponse();
		try {
			Document document = DocumentHelper.parseText(xmlstr.trim());

			Node node = document.selectSingleNode("/interface-result/head/flag");
			if (node != null)
				re.flag = node.getStringValue().trim();
			else {
				re.flag = "";
			}
			node = document.selectSingleNode("/interface-result/head/detail");
			if (node != null)
				re.detail = node.getStringValue().trim();
			else {
				re.detail = "";
			}
			node = document.selectSingleNode("/interface-result/head/recordsn");
			if (node != null)
				re.recordsn = node.getStringValue().trim();
			else {
				re.recordsn = "";
			}
			node = document.selectSingleNode("/interface-result/head/service_code");
			if (node != null)
				re.service_code = node.getStringValue().trim();
			else {
				re.service_code = "";
			}
			node = document.selectSingleNode("/interface-result/head/svcnum");
			if (node != null)
				re.svcnum = node.getStringValue().trim();
			else {
				re.svcnum = "";
			}
			node = document.selectSingleNode("/interface-result/head/sys_sequence");
			if (node != null)
				re.sys_sequence = node.getStringValue().trim();
			else {
				re.sys_sequence = "";
			}
			node = document.selectSingleNode("/interface-result/head/resp_date");
			if (node != null)
				re.resp_date = node.getStringValue().trim();
			else {
				re.resp_date = "";
			}
			node = document.selectSingleNode("/interface-result/content/pay_money");
			if (node != null)
				re.pay_money = node.getStringValue().trim();
			else {
				re.pay_money = "";
			}
			node = document.selectSingleNode("/interface-result/content/pay_type");
			if (node != null)
				re.pay_type = node.getStringValue().trim();
			else {
				re.pay_type = "";
			}
			node = document.selectSingleNode("/interface-result/content/sp_OrderId");
			if (node != null)
				re.sp_OrderId = node.getStringValue().trim();
			else
				re.sp_OrderId = "";
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return re;
	}
}
