package com.zbensoft.mmsmp.common.ra.vas.sjb.data;

public class DemandDto {
	private String spid;
	private String serviceid;
	private String spreporturl;
	private String productId;
	private String needConfirm;

	public String getNeedConfirm() {
		return this.needConfirm;
	}

	public void setNeedConfirm(String needConfirm) {
		this.needConfirm = needConfirm;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSpid() {
		return this.spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getServiceid() {
		return this.serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getSpreporturl() {
		return this.spreporturl;
	}

	public void setSpreporturl(String spreporturl) {
		this.spreporturl = spreporturl;
	}
}
