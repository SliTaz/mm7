package com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness.define;

public class ProductManageRequest extends CommonRequest {
	public static int Operate_Normal = 0;
	public static int Operate_Apply = 1;
	public static int Operate_Pause = 2;
	public static int Operate_PreCancel = 3;
	public static int Operate_Cancel = 4;
	private String spID;
	private String productID;
	private String productType;
	private int operate;

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getOperate() {
		return this.operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}

	public String getProductID() {
		return this.productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getSpID() {
		return this.spID;
	}

	public void setSpID(String spID) {
		this.spID = spID;
	}
}
