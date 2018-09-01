 package com.zbensoft.mmsmp.common.ra.common.message;

public class MT_MMHttpMessage extends AbstractMessage
 {
   private static final long serialVersionUID = 1L;
   private String ContentType;
   private String Content_Transfer_Encoding;
   private String Authorization;
   private String SOAPAction;
   private String MM7APIVersion;
   private String Mime_Version;
   private String response;
   private byte[] contents = new byte[4096];
   private String mmsmcUrl;
   private String servicecodes;
   private String sp_productid;
   private String messageid;
//   private HttpServletRequest request;
   private String reqId;
   private String mtSerType;
   private String msgType;
 
   public String getMmsmcUrl()
   {
     return this.mmsmcUrl;
   }
 
   public void setMmsmcUrl(String mmsmcUrl) {
     this.mmsmcUrl = mmsmcUrl;
   }
 
   public byte[] getContents() {
     return this.contents;
   }
 
   public void setContents(byte[] contents) {
     this.contents = contents;
   }
 
   public void decodeString(String message)
     throws DecodeMessageException
   {
   }
 
   public String encodeString()
   {
     return null;
   }
 
   public MessageType getMessageType()
   {
     return null;
   }
 
   public int getServiceId()
   {
     return 0;
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
 
   public String getResponse()
   {
     return this.response;
   }
 
   public void setResponse(String response) {
     this.response = response;
   }
 
   public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append("Authorization\t").append(this.Authorization).append("\r\n")
       .append("Content_Transfer_Encoding\t").append(this.Content_Transfer_Encoding).append("\r\n")
       .append("ContentType\t").append(this.ContentType).append("\r\n")
       .append("SOAPAction\t").append(this.SOAPAction).append("\r\n")
       .append("MM7APIVersion\t").append(this.MM7APIVersion).append("\r\n")
       .append("Mime_Version\t").append(this.Mime_Version).append("\r\n")
       .append("response\t").append(this.response).append("\r\n")
       .append("mmsmcUrl\t").append(this.mmsmcUrl).append("\r\n")
       .append("contents:\r\n").append(new String(this.contents));
     return sb.toString();
   }
 
   public String getServicecodes()
   {
     return this.servicecodes;
   }
 
   public void setServicecodes(String servicecodes) {
     this.servicecodes = servicecodes;
   }
 
   public String getSp_productid() {
     return this.sp_productid;
   }
 
   public void setSp_productid(String spProductid) {
     this.sp_productid = spProductid;
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
 
   public String getReqId() {
     return this.reqId;
   }
 
   public void setReqId(String reqId) {
     this.reqId = reqId;
   }
 
   public String getMtSerType() {
     return this.mtSerType;
   }
 
   public void setMtSerType(String mtSerType) {
     this.mtSerType = mtSerType;
   }
 
   public String getMsgType() {
     return this.msgType;
   }
 
   public void setMsgType(String msgType) {
     this.msgType = msgType;
   }
 }
