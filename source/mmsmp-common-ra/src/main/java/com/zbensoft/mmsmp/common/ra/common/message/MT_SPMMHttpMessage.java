/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class MT_SPMMHttpMessage extends AbstractMessage
/*     */   implements Serializable
/*     */ {
/*     */   private HttpServletRequest request;
/*     */   private String messageid;
/*     */   private String ContentType;
/*     */   private String Content_Transfer_Encoding;
/*     */   private String Authorization;
/*     */   private String SOAPAction;
/*     */   private String MM7APIVersion;
/*     */   private String Mime_Version;
/*     */   private byte[] contentbyte;
/*     */ 
/*     */   public byte[] getContentbyte()
/*     */   {
/*  24 */     return this.contentbyte;
/*     */   }
/*     */ 
/*     */   public void setContentbyte(byte[] contentbyte) {
/*  28 */     this.contentbyte = contentbyte;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/*  38 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/*  43 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  48 */     return 0;
/*     */   }
/*     */ 
/*     */   public HttpServletRequest getRequest() {
/*  52 */     return this.request;
/*     */   }
/*     */ 
/*     */   public void setRequest(HttpServletRequest request) {
/*  56 */     this.request = request;
/*     */   }
/*     */ 
/*     */   public String getMessageid() {
/*  60 */     return this.messageid;
/*     */   }
/*     */ 
/*     */   public void setMessageid(String messageid) {
/*  64 */     this.messageid = messageid;
/*     */   }
/*     */ 
/*     */   public String getContentType() {
/*  68 */     return this.ContentType;
/*     */   }
/*     */ 
/*     */   public void setContentType(String contentType) {
/*  72 */     this.ContentType = contentType;
/*     */   }
/*     */ 
/*     */   public String getContent_Transfer_Encoding() {
/*  76 */     return this.Content_Transfer_Encoding;
/*     */   }
/*     */ 
/*     */   public void setContent_Transfer_Encoding(String contentTransferEncoding) {
/*  80 */     this.Content_Transfer_Encoding = contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   public String getAuthorization() {
/*  84 */     return this.Authorization;
/*     */   }
/*     */ 
/*     */   public void setAuthorization(String authorization) {
/*  88 */     this.Authorization = authorization;
/*     */   }
/*     */ 
/*     */   public String getSOAPAction() {
/*  92 */     return this.SOAPAction;
/*     */   }
/*     */ 
/*     */   public void setSOAPAction(String sOAPAction) {
/*  96 */     this.SOAPAction = sOAPAction;
/*     */   }
/*     */ 
/*     */   public String getMM7APIVersion() {
/* 100 */     return this.MM7APIVersion;
/*     */   }
/*     */ 
/*     */   public void setMM7APIVersion(String mM7APIVersion) {
/* 104 */     this.MM7APIVersion = mM7APIVersion;
/*     */   }
/*     */ 
/*     */   public String getMime_Version() {
/* 108 */     return this.Mime_Version;
/*     */   }
/*     */ 
/*     */   public void setMime_Version(String mimeVersion) {
/* 112 */     this.Mime_Version = mimeVersion;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MT_SPMMHttpMessage
 * JD-Core Version:    0.6.0
 */