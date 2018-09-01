package com.zbensoft.mmsmp.common.ra.wo.order;

public class Head {
	private String service_code;
	private String svcnum;
	private String id_type;
	private String spno;
	private String recordsn;
	private String reqdate;
	private String wopriv;
	private String ChkValue;

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

	public String getId_type() {
		return this.id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getSpno() {
		return this.spno;
	}

	public void setSpno(String spno) {
		this.spno = spno;
	}

	public String getRecordsn() {
		return this.recordsn;
	}

	public void setRecordsn(String recordsn) {
		this.recordsn = recordsn;
	}

	public String getReqdate() {
		return this.reqdate;
	}

	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}

	public String getWopriv() {
		return this.wopriv;
	}

	public void setWopriv(String wopriv) {
		this.wopriv = wopriv;
	}

	public String getChkValue() {
		return this.ChkValue;
	}

	public void setChkValue(String chkValue) {
		this.ChkValue = chkValue;
	}
}
