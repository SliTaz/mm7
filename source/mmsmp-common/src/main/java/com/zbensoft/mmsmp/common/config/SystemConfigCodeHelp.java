package com.zbensoft.mmsmp.common.config;

import java.util.ArrayList;
import java.util.List;

/**
 * CodeHelp
 * 
 * @author 谢强
 * @version 1.0.0
 * 
 */
public class SystemConfigCodeHelp {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String nameOne;
	private String nameTwo;
	private String nameThree;
	private String defalut;
	private List<SystemConfigCodeHelp> codeList = new ArrayList<SystemConfigCodeHelp>();

	public String getNameThree() {
		return nameThree;
	}

	public void setNameThree(String nameThree) {
		this.nameThree = nameThree;
	}

	public String getDefalut() {
		return defalut;
	}

	public void setDefalut(String defalut) {
		this.defalut = defalut;
	}

	public List<SystemConfigCodeHelp> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<SystemConfigCodeHelp> codeList) {
		this.codeList = codeList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNameOne() {
		return nameOne;
	}

	public void setNameOne(String nameOne) {
		this.nameOne = nameOne;
	}

	public String getNameTwo() {
		return nameTwo;
	}

	public void setNameTwo(String nameTwo) {
		this.nameTwo = nameTwo;
	}

}
