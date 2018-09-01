/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.Serializable;

/*     */
/*     */ public class CheckRequest extends AbstractMessage
/*     */   implements Serializable
/*     */ {
/*     */   private String user_number;
/*     */   private String sp_product_id;
/*     */   private String sp_id;
/*     */   private String service_id;
/*     */   private String src_SequenceNumber;
/*     */   private String ServiceType;
/*     */   private String linkid;
/*     */   private Integer messageid;
/*     */   private long putTime;
/*     */   private boolean isResponse;
/*     */   private String reqSource;
/*     */ 
/*     */   public CheckRequest()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CheckRequest(String user_number, String sp_product_id, String sp_id, String service_id, String src_SequenceNumber, String ServiceType, String linkid)
/*     */   {
/*  45 */     this.user_number = user_number;
/*  46 */     this.sp_id = sp_id;
/*  47 */     this.sp_product_id = sp_product_id;
/*  48 */     this.service_id = service_id;
/*  49 */     this.src_SequenceNumber = src_SequenceNumber;
/*  50 */     this.ServiceType = ServiceType;
/*  51 */     this.linkid = linkid;
/*     */   }
/*     */   public boolean isResponse() {
/*  54 */     return this.isResponse;
/*     */   }
/*     */ 
/*     */   public void setResponse(boolean isResponse) {
/*  58 */     this.isResponse = isResponse;
/*     */   }
/*     */ 
/*     */   public String getService_id() {
/*  62 */     return this.service_id;
/*     */   }
/*     */   public void setService_id(String serviceId) {
/*  65 */     this.service_id = serviceId;
/*     */   }
/*     */ 
/*     */   public long getPutTime()
/*     */   {
/*  89 */     return this.putTime;
/*     */   }
/*     */ 
/*     */   public void setPutTime(long putTime) {
/*  93 */     this.putTime = putTime;
/*     */   }
/*     */ 
/*     */   public Integer getMessageid() {
/*  97 */     return this.messageid;
/*     */   }
/*     */ 
/*     */   public void setMessageid(Integer messageid) {
/* 101 */     this.messageid = messageid;
/*     */   }
/*     */ 
/*     */   public String getLinkID() {
/* 105 */     return this.linkid;
/*     */   }
/*     */ 
/*     */   public void setLinkID(String linkID) {
/* 109 */     this.linkid = linkID;
/*     */   }
/*     */ 
/*     */   public String getServiceType() {
/* 113 */     return this.ServiceType;
/*     */   }
/*     */   public void setServiceType(String serviceType) {
/* 116 */     this.ServiceType = serviceType;
/*     */   }
/*     */   public String getUser_number() {
/* 119 */     return this.user_number;
/*     */   }
/*     */   public void setUser_number(String userNumber) {
/* 122 */     this.user_number = userNumber;
/*     */   }
/*     */   public String getSp_product_id() {
/* 125 */     return this.sp_product_id;
/*     */   }
/*     */   public void setSp_product_id(String spProductId) {
/* 128 */     this.sp_product_id = spProductId;
/*     */   }
/*     */   public String getSp_id() {
/* 131 */     return this.sp_id;
/*     */   }
/*     */   public void setSp_id(String spId) {
/* 134 */     this.sp_id = spId;
/*     */   }
/*     */   public String getSrc_SequenceNumber() {
/* 137 */     return this.src_SequenceNumber;
/*     */   }
/*     */   public void setSrc_SequenceNumber(String srcSequenceNumber) {
/* 140 */     this.src_SequenceNumber = srcSequenceNumber;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 155 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/* 160 */     return 0;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.CheckRequest
 * JD-Core Version:    0.6.0
 */