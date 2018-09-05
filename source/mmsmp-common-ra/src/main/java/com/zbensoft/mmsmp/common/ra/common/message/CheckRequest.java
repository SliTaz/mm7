package com.zbensoft.mmsmp.common.ra.common.message;

import java.io.Serializable;

public class CheckRequest extends AbstractMessage implements Serializable {
	private String user_number;
	private String sp_product_id;
	private String sp_id;
	private String service_id;
	private String src_SequenceNumber;
	private String ServiceType;
	private String linkid;
	private Integer messageid;
	private long putTime;
	private boolean isResponse;
	private String reqSource;

	public CheckRequest() {
	}

	public CheckRequest(String user_number, String sp_product_id, String sp_id, String service_id, String src_SequenceNumber, String ServiceType, String linkid) {
		this.user_number = user_number;
		this.sp_id = sp_id;
		this.sp_product_id = sp_product_id;
		this.service_id = service_id;
		this.src_SequenceNumber = src_SequenceNumber;
		this.ServiceType = ServiceType;
		this.linkid = linkid;
	}

	public boolean isResponse() {
		return this.isResponse;
	}

	public void setResponse(boolean isResponse) {
		this.isResponse = isResponse;
	}

	public String getService_id() {
		return this.service_id;
	}

	public void setService_id(String serviceId) {
		this.service_id = serviceId;
	}

	public long getPutTime() {
		return this.putTime;
	}

	public void setPutTime(long putTime) {
		this.putTime = putTime;
	}

	public Integer getMessageid() {
		return this.messageid;
	}

	public void setMessageid(Integer messageid) {
		this.messageid = messageid;
	}

	public String getLinkID() {
		return this.linkid;
	}

	public void setLinkID(String linkID) {
		this.linkid = linkID;
	}

	public String getServiceType() {
		return this.ServiceType;
	}

	public void setServiceType(String serviceType) {
		this.ServiceType = serviceType;
	}

	public String getUser_number() {
		return this.user_number;
	}

	public void setUser_number(String userNumber) {
		this.user_number = userNumber;
	}

	public String getSp_product_id() {
		return this.sp_product_id;
	}

	public void setSp_product_id(String spProductId) {
		this.sp_product_id = spProductId;
	}

	public String getSp_id() {
		return this.sp_id;
	}

	public void setSp_id(String spId) {
		this.sp_id = spId;
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
		sb.append("user_number").append("=").append(user_number).append(",");
		sb.append("sp_product_id").append("=").append(sp_product_id).append(",");
		sb.append("sp_id").append("=").append(sp_id).append(",");
		sb.append("service_id").append("=").append(service_id).append(",");
		sb.append("src_SequenceNumber").append("=").append(src_SequenceNumber).append(",");
		sb.append("ServiceType").append("=").append(ServiceType).append(",");
		sb.append("linkid").append("=").append(linkid).append(",");
		sb.append("messageid").append("=").append(messageid).append(",");
		sb.append("putTime").append("=").append(putTime).append(",");
		sb.append("isResponse").append("=").append(isResponse).append(",");
		sb.append("reqSource").append("=").append(reqSource).append(",");
		return sb.toString();
	}
}
