package com.zbensoft.mmsmp.common.ra.aaa.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public class CheckResponse {
	private static final Logger logger = Logger.getLogger(CheckResponse.class);
	private String Result_Code;
	private String NeedConfirm;
	private String LinkID;
	private String ReturnMessage;
	private String SP_ProductID;
	private String SPEC_ProductID;
	private String ProductID;
	private String src_SequenceNumber;
	private String serverResult_code;
	private String serverMessage;
	private String spOrderId;

	public String getSpOrderId() {
		return this.spOrderId;
	}

	public void setSpOrderId(String spOrderId) {
		this.spOrderId = spOrderId;
	}

	public String getServerMessage() {
		return this.serverMessage;
	}

	public void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}

	public static CheckResponse CheckResponseForXml(String xmlstr) {
		CheckResponse re = new CheckResponse();
		try {
			Document document = DocumentHelper.parseText(xmlstr.trim());

			Node node = document.selectSingleNode("//LinkID");
			if (node != null)
				re.LinkID = node.getStringValue();
			else {
				re.LinkID = "";
			}

			node = document.selectSingleNode("//NeedConfirm");
			if (node != null)
				re.NeedConfirm = node.getStringValue();
			else {
				re.NeedConfirm = "";
			}
			node = document.selectSingleNode("//ProductID");
			if (node != null)
				re.ProductID = node.getStringValue();
			else {
				re.ProductID = "";
			}
			node = document.selectSingleNode("//Result_Code");
			if (node != null)
				re.Result_Code = node.getStringValue();
			else {
				re.Result_Code = "";
			}
			node = document.selectSingleNode("//ReturnMessage");
			if (node != null)
				re.ReturnMessage = node.getStringValue();
			else {
				re.ReturnMessage = "";
			}
			node = document.selectSingleNode("//SP_ProductID");
			if (node != null)
				re.SP_ProductID = node.getStringValue();
			else {
				re.SP_ProductID = "";
			}
			node = document.selectSingleNode("//SPEC_ProductID");
			if (node != null)
				re.SPEC_ProductID = node.getStringValue();
			else {
				re.SPEC_ProductID = "";
			}
			node = document.selectSingleNode("//src_SequenceNumber");
			if (node != null)
				re.src_SequenceNumber = node.getStringValue();
			else {
				re.src_SequenceNumber = "";
			}
			node = document.selectSingleNode("//serverResult_code");
			if (node != null)
				re.serverResult_code = node.getStringValue();
			else {
				re.serverResult_code = "";
			}
			node = document.selectSingleNode("//serverMessage");
			if (node != null)
				re.serverMessage = node.getStringValue();
			else
				re.serverMessage = "";
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return re;
	}

	public String getServerResult_code() {
		return this.serverResult_code;
	}

	public void setServerResult_code(String serverResultCode) {
		this.serverResult_code = serverResultCode;
	}

	public String getResult_Code() {
		return this.Result_Code;
	}

	public void setResult_Code(String resultCode) {
		this.Result_Code = resultCode;
	}

	public String getNeedConfirm() {
		return this.NeedConfirm;
	}

	public void setNeedConfirm(String needConfirm) {
		this.NeedConfirm = needConfirm;
	}

	public String getLinkID() {
		return this.LinkID;
	}

	public void setLinkID(String linkID) {
		this.LinkID = linkID;
	}

	public String getReturnMessage() {
		return this.ReturnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.ReturnMessage = returnMessage;
	}

	public String getSP_ProductID() {
		return this.SP_ProductID;
	}

	public void setSP_ProductID(String sPProductID) {
		this.SP_ProductID = sPProductID;
	}

	public String getSPEC_ProductID() {
		return this.SPEC_ProductID;
	}

	public void setSPEC_ProductID(String sPECProductID) {
		this.SPEC_ProductID = sPECProductID;
	}

	public String getProductID() {
		return this.ProductID;
	}

	public void setProductID(String productID) {
		this.ProductID = productID;
	}

	public String getSrc_SequenceNumber() {
		return this.src_SequenceNumber;
	}

	public void setSrc_SequenceNumber(String srcSequenceNumber) {
		this.src_SequenceNumber = srcSequenceNumber;
	}

	public static void main(String[] args) {
	}
}
