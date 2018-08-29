/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class MT_MMHttpMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String ContentType;
/*     */   private String Content_Transfer_Encoding;
/*     */   private String Authorization;
/*     */   private String SOAPAction;
/*     */   private String MM7APIVersion;
/*     */   private String Mime_Version;
/*     */   private String response;
/*  19 */   private byte[] contents = new byte[4096];
/*     */   private String mmsmcUrl;
/*     */   private String servicecodes;
/*     */   private String sp_productid;
/*     */   private String messageid;
/*     */   private HttpServletRequest request;
/*     */   private String reqId;
/*     */   private String mtSerType;
/*     */   private String msgType;
/*     */ 
/*     */   public String getMmsmcUrl()
/*     */   {
/*  31 */     return this.mmsmcUrl;
/*     */   }
/*     */ 
/*     */   public void setMmsmcUrl(String mmsmcUrl) {
/*  35 */     this.mmsmcUrl = mmsmcUrl;
/*     */   }
/*     */ 
/*     */   public byte[] getContents() {
/*  39 */     return this.contents;
/*     */   }
/*     */ 
/*     */   public void setContents(byte[] contents) {
/*  43 */     this.contents = contents;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/*  53 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  63 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getContentType() {
/*  67 */     return this.ContentType;
/*     */   }
/*     */ 
/*     */   public void setContentType(String contentType) {
/*  71 */     this.ContentType = contentType;
/*     */   }
/*     */ 
/*     */   public String getContent_Transfer_Encoding() {
/*  75 */     return this.Content_Transfer_Encoding;
/*     */   }
/*     */ 
/*     */   public void setContent_Transfer_Encoding(String contentTransferEncoding) {
/*  79 */     this.Content_Transfer_Encoding = contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   public String getAuthorization() {
/*  83 */     return this.Authorization;
/*     */   }
/*     */ 
/*     */   public void setAuthorization(String authorization) {
/*  87 */     this.Authorization = authorization;
/*     */   }
/*     */ 
/*     */   public String getSOAPAction() {
/*  91 */     return this.SOAPAction;
/*     */   }
/*     */ 
/*     */   public void setSOAPAction(String sOAPAction) {
/*  95 */     this.SOAPAction = sOAPAction;
/*     */   }
/*     */ 
/*     */   public String getMM7APIVersion() {
/*  99 */     return this.MM7APIVersion;
/*     */   }
/*     */ 
/*     */   public void setMM7APIVersion(String mM7APIVersion) {
/* 103 */     this.MM7APIVersion = mM7APIVersion;
/*     */   }
/*     */ 
/*     */   public String getMime_Version() {
/* 107 */     return this.Mime_Version;
/*     */   }
/*     */ 
/*     */   public void setMime_Version(String mimeVersion) {
/* 111 */     this.Mime_Version = mimeVersion;
/*     */   }
/*     */ 
/*     */   public String getResponse()
/*     */   {
/* 116 */     return this.response;
/*     */   }
/*     */ 
/*     */   public void setResponse(String response) {
/* 120 */     this.response = response;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 124 */     StringBuilder sb = new StringBuilder();
/* 125 */     sb.append("Authorization\t").append(this.Authorization).append("\r\n")
/* 126 */       .append("Content_Transfer_Encoding\t").append(this.Content_Transfer_Encoding).append("\r\n")
/* 127 */       .append("ContentType\t").append(this.ContentType).append("\r\n")
/* 128 */       .append("SOAPAction\t").append(this.SOAPAction).append("\r\n")
/* 129 */       .append("MM7APIVersion\t").append(this.MM7APIVersion).append("\r\n")
/* 130 */       .append("Mime_Version\t").append(this.Mime_Version).append("\r\n")
/* 131 */       .append("response\t").append(this.response).append("\r\n")
/* 132 */       .append("mmsmcUrl\t").append(this.mmsmcUrl).append("\r\n")
/* 133 */       .append("contents:\r\n").append(new String(this.contents));
/* 134 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String getServicecodes()
/*     */   {
/* 140 */     return this.servicecodes;
/*     */   }
/*     */ 
/*     */   public void setServicecodes(String servicecodes) {
/* 144 */     this.servicecodes = servicecodes;
/*     */   }
/*     */ 
/*     */   public String getSp_productid() {
/* 148 */     return this.sp_productid;
/*     */   }
/*     */ 
/*     */   public void setSp_productid(String spProductid) {
/* 152 */     this.sp_productid = spProductid;
/*     */   }
/*     */ 
/*     */   public HttpServletRequest getRequest() {
/* 156 */     return this.request;
/*     */   }
/*     */ 
/*     */   public void setRequest(HttpServletRequest request) {
/* 160 */     this.request = request;
/*     */   }
/*     */ 
/*     */   public String getMessageid() {
/* 164 */     return this.messageid;
/*     */   }
/*     */ 
/*     */   public void setMessageid(String messageid) {
/* 168 */     this.messageid = messageid;
/*     */   }
/*     */ 
/*     */   public String getReqId() {
/* 172 */     return this.reqId;
/*     */   }
/*     */ 
/*     */   public void setReqId(String reqId) {
/* 176 */     this.reqId = reqId;
/*     */   }
/*     */ 
/*     */   public String getMtSerType() {
/* 180 */     return this.mtSerType;
/*     */   }
/*     */ 
/*     */   public void setMtSerType(String mtSerType) {
/* 184 */     this.mtSerType = mtSerType;
/*     */   }
/*     */ 
/*     */   public String getMsgType() {
/* 188 */     return this.msgType;
/*     */   }
/*     */ 
/*     */   public void setMsgType(String msgType) {
/* 192 */     this.msgType = msgType;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MT_MMHttpMessage
 * JD-Core Version:    0.6.0
 */