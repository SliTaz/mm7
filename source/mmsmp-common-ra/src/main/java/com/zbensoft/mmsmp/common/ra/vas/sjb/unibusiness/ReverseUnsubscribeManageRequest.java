package com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReverseUnsubscribeManageRequest", propOrder = { "phone", "userType", "productType", "productID" })
public class ReverseUnsubscribeManageRequest extends CommonRequest {

	@XmlElement(required = true, nillable = true)
	protected String phone;

	@XmlElement(required = true, type = Integer.class, nillable = true)
	protected Integer userType;

	@XmlElement(required = true, type = Integer.class, nillable = true)
	protected Integer productType;

	@XmlElement(required = true, nillable = true)
	protected String productID;

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String value) {
		this.phone = value;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer value) {
		this.userType = value;
	}

	public Integer getProductType() {
		return this.productType;
	}

	public void setProductType(Integer value) {
		this.productType = value;
	}

	public String getProductID() {
		return this.productID;
	}

	public void setProductID(String value) {
		this.productID = value;
	}
}
