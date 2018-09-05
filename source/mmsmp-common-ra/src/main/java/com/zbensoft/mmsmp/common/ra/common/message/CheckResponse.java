package com.zbensoft.mmsmp.common.ra.common.message;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class CheckResponse extends AbstractMessage implements Serializable {
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
	private Integer messageid;
	private CheckRequest cRequest;

	public CheckRequest getCRequest() {
		return this.cRequest;
	}

	public void setCRequest(CheckRequest request) {
		this.cRequest = request;
	}

	public Integer getMessageid() {
		return this.messageid;
	}

	public void setMessageid(Integer messageid) {
		this.messageid = messageid;
	}

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

	public void decodeString(String message) throws DecodeMessageException {
	}

	public String encodeString() {
		return null;
	}

	public MessageType getMessageType() {
		return null;
	}

	public int getServiceId() {
		return 0;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append("Result_Code").append("=").append(Result_Code).append(",");
		sb.append("NeedConfirm").append("=").append(NeedConfirm).append(",");
		sb.append("LinkID").append("=").append(LinkID).append(",");
		sb.append("ReturnMessage").append("=").append(ReturnMessage).append(",");
		sb.append("SP_ProductID").append("=").append(SP_ProductID).append(",");
		sb.append("SPEC_ProductID").append("=").append(SPEC_ProductID).append(",");
		sb.append("ProductID").append("=").append(ProductID).append(",");
		sb.append("src_SequenceNumber").append("=").append(src_SequenceNumber).append(",");
		sb.append("serverResult_code").append("=").append(serverResult_code).append(",");
		sb.append("serverMessage").append("=").append(serverMessage).append(",");
		sb.append("spOrderId").append("=").append(spOrderId).append(",");
		sb.append("messageid").append("=").append(messageid).append(",");
		sb.append("cRequest").append("=").append(cRequest).append(",");
		return sb.toString();
	}
}
