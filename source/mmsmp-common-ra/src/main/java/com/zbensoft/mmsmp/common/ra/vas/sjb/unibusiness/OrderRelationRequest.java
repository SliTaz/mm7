package com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderRelationRequest", propOrder = { "oldProduceID", "orderType", "productID", "spCode", "status", "userPhone", "userType", "aaaURL", "serviceId", "feeType", "woInfo" })
public class OrderRelationRequest extends CommonRequest {

	@XmlElement(required = true, nillable = true)
	protected String oldProduceID;
	protected int orderType;

	@XmlElement(required = true, nillable = true)
	protected String productID;

	@XmlElement(required = true, nillable = true)
	protected String spCode;
	protected int status;

	@XmlElement(required = true, nillable = true)
	protected String userPhone;

	@XmlElement(required = true, nillable = true)
	protected int userType;

	@XmlElement(required = true, nillable = true)
	protected String aaaURL;

	@XmlElement(required = true, nillable = true)
	protected String serviceId;

	@XmlElement(required = true, nillable = true)
	protected int feeType;

	@XmlElement(required = true, nillable = true)
	protected String woInfo;

	public String getWoInfo() {
		return this.woInfo;
	}

	public void setWoInfo(String woInfo) {
		this.woInfo = woInfo;
	}

	public String getAaaURL() {
		return this.aaaURL;
	}

	public void setAaaURL(String aaaURL) {
		this.aaaURL = aaaURL;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public int getFeeType() {
		return this.feeType;
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}

	public String getOldProduceID() {
		return this.oldProduceID;
	}

	public void setOldProduceID(String value) {
		this.oldProduceID = value;
	}

	public int getOrderType() {
		return this.orderType;
	}

	public void setOrderType(int value) {
		this.orderType = value;
	}

	public String getProductID() {
		return this.productID;
	}

	public void setProductID(String value) {
		this.productID = value;
	}

	public String getSpCode() {
		return this.spCode;
	}

	public void setSpCode(String value) {
		this.spCode = value;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int value) {
		this.status = value;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String value) {
		this.userPhone = value;
	}

	public int getUserType() {
		return this.userType;
	}

	public void setUserType(int value) {
		this.userType = value;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" oldProduceID=").append(this.oldProduceID);
		sb.append(" orderType=").append(this.orderType);
		sb.append(" productID=").append(this.productID);
		sb.append(" spCode=").append(this.spCode);
		sb.append(" status=").append(this.status);
		sb.append(" userPhone=").append(this.userPhone);
		sb.append(" userType=").append(this.userType);
		sb.append(" ]");
		return sb.toString();
	}
}
