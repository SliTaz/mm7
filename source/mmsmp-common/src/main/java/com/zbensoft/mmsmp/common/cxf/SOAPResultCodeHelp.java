package com.zbensoft.mmsmp.common.cxf;

import java.util.ArrayList;
import java.util.List;

public class SOAPResultCodeHelp {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private String code;
	private String key;
	private String reason;
	private String reason_es;
	private List<SOAPResultCodeHelp> codeList = new ArrayList<SOAPResultCodeHelp>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason_es() {
		return reason_es;
	}

	public void setReason_es(String reason_es) {
		this.reason_es = reason_es;
	}

	public List<SOAPResultCodeHelp> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<SOAPResultCodeHelp> codeList) {
		this.codeList = codeList;
	}

}
