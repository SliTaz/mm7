package com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductManageRequest", propOrder = { "operate", "productID", "productType", "spID" })
public class ProductManageRequest extends CommonRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2985857863562863118L;

	protected int operate;

	@XmlElement(required = true, nillable = true)
	protected String productID;

	@XmlElement(required = true, nillable = true)
	protected String productType;

	@XmlElement(required = true, nillable = true)
	protected String spID;

	public int getOperate() {
		return this.operate;
	}

	public void setOperate(int value) {
		this.operate = value;
	}

	public String getProductID() {
		return this.productID;
	}

	public void setProductID(String value) {
		this.productID = value;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String value) {
		this.productType = value;
	}

	public String getSpID() {
		return this.spID;
	}

	public void setSpID(String value) {
		this.spID = value;
	}
}
