/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class MT_MMHttpSPMessage extends AbstractMessage
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String messageid;
/*  32 */   private Map<String, String> requestHeads = new HashMap();
/*     */   private byte[] contentByte;
/*     */   private String remoteHost;
/*     */   private String ContentType;
/*     */   private String Content_Transfer_Encoding;
/*     */   private String Authorization;
/*     */   private String SOAPAction;
/*     */   private String MM7APIVersion;
/*     */   private String Mime_Version;
/*     */   private String response;
/*  45 */   private byte[] contents = new byte[4096];
/*     */   private String mmsmcUrl;
/*     */   private String servicecodes;
/*     */   private String sp_productid;
/*     */   private String reqId;
/*     */   private String mtSerType;
/*     */   private String msgType;
/*     */   private String sequence;
/*     */   private String phone;
/*     */   private String spid;
/*     */   private String serviceid;
/*     */   private String vaspid;
/*     */   private String uniqueid;
/*     */   private String sourceGlobalMessageid;
/*     */ 
/*     */   public String getSourceGlobalMessageid()
/*     */   {
/*  64 */     return this.sourceGlobalMessageid;
/*     */   }
/*     */ 
/*     */   public void setSourceGlobalMessageid(String sourceGlobalMessageid) {
/*  68 */     this.sourceGlobalMessageid = sourceGlobalMessageid;
/*     */   }
/*     */ 
/*     */   public String getServiceid() {
/*  72 */     return this.serviceid;
/*     */   }
/*     */ 
/*     */   public void setServiceid(String serviceid) {
/*  76 */     this.serviceid = serviceid;
/*     */   }
/*     */ 
/*     */   public String getVaspid() {
/*  80 */     return this.vaspid;
/*     */   }
/*     */ 
/*     */   public void setVaspid(String vaspid) {
/*  84 */     this.vaspid = vaspid;
/*     */   }
/*     */ 
/*     */   public String getUniqueid() {
/*  88 */     return this.uniqueid;
/*     */   }
/*     */ 
/*     */   public void setUniqueid(String uniqueid) {
/*  92 */     this.uniqueid = uniqueid;
/*     */   }
/*     */ 
/*     */   public String getSpid() {
/*  96 */     return this.spid;
/*     */   }
/*     */ 
/*     */   public void setSpid(String spid) {
/* 100 */     this.spid = spid;
/*     */   }
/*     */ 
/*     */   public String getPhone() {
/* 104 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String phone) {
/* 108 */     this.phone = phone;
/*     */   }
/*     */ 
/*     */   public String getSequence() {
/* 112 */     return this.sequence;
/*     */   }
/*     */ 
/*     */   public void setSequence(String sequence) {
/* 116 */     this.sequence = sequence;
/*     */   }
/*     */ 
/*     */   public String getContentType() {
/* 120 */     return this.ContentType;
/*     */   }
/*     */ 
/*     */   public void setContentType(String contentType) {
/* 124 */     this.ContentType = contentType;
/*     */   }
/*     */ 
/*     */   public String getContent_Transfer_Encoding() {
/* 128 */     return this.Content_Transfer_Encoding;
/*     */   }
/*     */ 
/*     */   public void setContent_Transfer_Encoding(String content_Transfer_Encoding) {
/* 132 */     this.Content_Transfer_Encoding = content_Transfer_Encoding;
/*     */   }
/*     */ 
/*     */   public String getAuthorization() {
/* 136 */     return this.Authorization;
/*     */   }
/*     */ 
/*     */   public void setAuthorization(String authorization) {
/* 140 */     this.Authorization = authorization;
/*     */   }
/*     */ 
/*     */   public String getSOAPAction() {
/* 144 */     return this.SOAPAction;
/*     */   }
/*     */ 
/*     */   public void setSOAPAction(String action) {
/* 148 */     this.SOAPAction = action;
/*     */   }
/*     */ 
/*     */   public String getMM7APIVersion() {
/* 152 */     return this.MM7APIVersion;
/*     */   }
/*     */ 
/*     */   public void setMM7APIVersion(String version) {
/* 156 */     this.MM7APIVersion = version;
/*     */   }
/*     */ 
/*     */   public String getMime_Version() {
/* 160 */     return this.Mime_Version;
/*     */   }
/*     */ 
/*     */   public void setMime_Version(String mime_Version) {
/* 164 */     this.Mime_Version = mime_Version;
/*     */   }
/*     */ 
/*     */   public String getResponse() {
/* 168 */     return this.response;
/*     */   }
/*     */ 
/*     */   public void setResponse(String response) {
/* 172 */     this.response = response;
/*     */   }
/*     */ 
/*     */   public byte[] getContents() {
/* 176 */     return this.contents;
/*     */   }
/*     */ 
/*     */   public void setContents(byte[] contents) {
/* 180 */     this.contents = contents;
/*     */   }
/*     */ 
/*     */   public String getMmsmcUrl() {
/* 184 */     return this.mmsmcUrl;
/*     */   }
/*     */ 
/*     */   public void setMmsmcUrl(String mmsmcUrl) {
/* 188 */     this.mmsmcUrl = mmsmcUrl;
/*     */   }
/*     */ 
/*     */   public String getServicecodes() {
/* 192 */     return this.servicecodes;
/*     */   }
/*     */ 
/*     */   public void setServicecodes(String servicecodes) {
/* 196 */     this.servicecodes = servicecodes;
/*     */   }
/*     */ 
/*     */   public String getSp_productid() {
/* 200 */     return this.sp_productid;
/*     */   }
/*     */ 
/*     */   public void setSp_productid(String sp_productid) {
/* 204 */     this.sp_productid = sp_productid;
/*     */   }
/*     */ 
/*     */   public String getReqId() {
/* 208 */     return this.reqId;
/*     */   }
/*     */ 
/*     */   public void setReqId(String reqId) {
/* 212 */     this.reqId = reqId;
/*     */   }
/*     */ 
/*     */   public String getMtSerType() {
/* 216 */     return this.mtSerType;
/*     */   }
/*     */ 
/*     */   public void setMtSerType(String mtSerType) {
/* 220 */     this.mtSerType = mtSerType;
/*     */   }
/*     */ 
/*     */   public String getMsgType() {
/* 224 */     return this.msgType;
/*     */   }
/*     */ 
/*     */   public void setMsgType(String msgType) {
/* 228 */     this.msgType = msgType;
/*     */   }
/*     */ 
/*     */   public static long getSerialVersionUID() {
/* 232 */     return 1L;
/*     */   }
/*     */ 
/*     */   public String getMessageid() {
/* 236 */     return this.messageid;
/*     */   }
/*     */ 
/*     */   public void setMessageid(String messageid) {
/* 240 */     this.messageid = messageid;
/*     */   }
/*     */ 
/*     */   public SubmitReq getSubmitReq()
/*     */   {
/* 245 */     SubmitReq submitReq = new SubmitReq();
/* 246 */     submitReq.parser(new String(this.contentByte));
/* 247 */     return submitReq;
/*     */   }
/*     */ 
/*     */   public String getHead(String key)
/*     */   {
/* 252 */     return (String)this.requestHeads.get(key.toLowerCase());
/*     */   }
/*     */ 
/*     */   public String getRemoteHost() {
/* 256 */     return this.remoteHost;
/*     */   }
/*     */ 
/*     */   public void setRemoteHost(String remoteHost) {
/* 260 */     this.remoteHost = remoteHost;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getRequestHeads() {
/* 264 */     return this.requestHeads;
/*     */   }
/*     */ 
/*     */   public void setRequestHeads(Map<String, String> requestHeads) {
/* 268 */     this.requestHeads = requestHeads;
/*     */   }
/*     */ 
/*     */   public byte[] getContentByte() {
/* 272 */     return this.contentByte;
/*     */   }
/*     */ 
/*     */   public void setContentByte(byte[] contentByte) {
/* 276 */     this.contentByte = contentByte;
/*     */   }
/*     */ 
/*     */   public void decodeString(String arg0)
/*     */     throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 286 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/* 296 */     return 0;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MT_MMHttpSPMessage
 * JD-Core Version:    0.6.0
 */