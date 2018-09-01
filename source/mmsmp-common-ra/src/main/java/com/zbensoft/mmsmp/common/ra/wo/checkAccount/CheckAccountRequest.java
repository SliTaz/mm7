package com.zbensoft.mmsmp.common.ra.wo.checkAccount;

public class CheckAccountRequest {
	private String SPNO;
	private String REQDATE;
	private String RDPWD;
	private String SIGN;

	public String getSPNO() {
		return this.SPNO;
	}

	public void setSPNO(String spno) {
		this.SPNO = spno;
	}

	public String getREQDATE() {
		return this.REQDATE;
	}

	public void setREQDATE(String reqdate) {
		this.REQDATE = reqdate;
	}

	public String getRDPWD() {
		return this.RDPWD;
	}

	public void setRDPWD(String rdpwd) {
		this.RDPWD = rdpwd;
	}

	public String getSIGN() {
		return this.SIGN;
	}

	public void setSIGN(String sign) {
		this.SIGN = sign;
	}
}
