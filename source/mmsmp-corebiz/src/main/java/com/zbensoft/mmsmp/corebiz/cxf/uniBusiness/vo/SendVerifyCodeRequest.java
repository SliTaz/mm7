package com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendVerifyCodeRequest", propOrder = { "phone","verifyCode"})
public class SendVerifyCodeRequest extends CommonRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1786138489622341397L;

	@XmlElement(required = true, nillable = true)
	protected String phone;
	
	@XmlElement(required = true, nillable = true)
	protected String verifyCode;


	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String value) {
		this.phone = value;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	
}
