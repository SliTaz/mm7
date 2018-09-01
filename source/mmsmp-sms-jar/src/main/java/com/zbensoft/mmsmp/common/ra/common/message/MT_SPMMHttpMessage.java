package com.zbensoft.mmsmp.common.ra.common.message;

import java.io.Serializable;

public class MT_SPMMHttpMessage extends AbstractMessage implements Serializable {
//	private HttpServletRequest request;
	private String messageid;
	private String ContentType;
	private String Content_Transfer_Encoding;
	private String Authorization;
	private String SOAPAction;
	private String MM7APIVersion;
	private String Mime_Version;
	private byte[] contentbyte;

	public byte[] getContentbyte() {
		return this.contentbyte;
	}

	public void setContentbyte(byte[] contentbyte) {
		this.contentbyte = contentbyte;
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

	// public HttpServletRequest getRequest() {
	// return this.request;
	// }
	//
	// public void setRequest(HttpServletRequest request) {
	// this.request = request;
	// }

	public String getMessageid() {
		return this.messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getContentType() {
		return this.ContentType;
	}

	public void setContentType(String contentType) {
		this.ContentType = contentType;
	}

	public String getContent_Transfer_Encoding() {
		return this.Content_Transfer_Encoding;
	}

	public void setContent_Transfer_Encoding(String contentTransferEncoding) {
		this.Content_Transfer_Encoding = contentTransferEncoding;
	}

	public String getAuthorization() {
		return this.Authorization;
	}

	public void setAuthorization(String authorization) {
		this.Authorization = authorization;
	}

	public String getSOAPAction() {
		return this.SOAPAction;
	}

	public void setSOAPAction(String sOAPAction) {
		this.SOAPAction = sOAPAction;
	}

	public String getMM7APIVersion() {
		return this.MM7APIVersion;
	}

	public void setMM7APIVersion(String mM7APIVersion) {
		this.MM7APIVersion = mM7APIVersion;
	}

	public String getMime_Version() {
		return this.Mime_Version;
	}

	public void setMime_Version(String mimeVersion) {
		this.Mime_Version = mimeVersion;
	}
}
