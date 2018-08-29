/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SendNotificationMessage extends AbstractMessage
/*     */   implements Serializable
/*     */ {
/*     */   private String[] phoneNumber;
/*     */   private String serviceCode;
/*     */   private int contentId;
/*     */   private String[] status;
/*     */   private int sendType;
/*     */   private String mtType;
/*     */   private String[] province;
/*     */   private int serviceType;
/*     */   private String linkId;
/*     */   private String sendContent;
/*     */ 
/*     */   public String[] getPhoneNumber()
/*     */   {
/*  50 */     return this.phoneNumber;
/*     */   }
/*     */ 
/*     */   public void setPhoneNumber(String[] phoneNumber) {
/*  54 */     this.phoneNumber = phoneNumber;
/*     */   }
/*     */ 
/*     */   public int getContentId() {
/*  58 */     return this.contentId;
/*     */   }
/*     */ 
/*     */   public void setContentId(int contentId) {
/*  62 */     this.contentId = contentId;
/*     */   }
/*     */ 
/*     */   public String[] getStatus() {
/*  66 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String[] status) {
/*  70 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public int getSendType() {
/*  74 */     return this.sendType;
/*     */   }
/*     */ 
/*     */   public void setSendType(int sendType) {
/*  78 */     this.sendType = sendType;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/*  82 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/*  86 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String[] getProvince() {
/*  90 */     return this.province;
/*     */   }
/*     */ 
/*     */   public void setProvince(String[] province) {
/*  94 */     this.province = province;
/*     */   }
/*     */ 
/*     */   public String getMtType() {
/*  98 */     return this.mtType;
/*     */   }
/*     */ 
/*     */   public void setMtType(String mtType) {
/* 102 */     this.mtType = mtType;
/*     */   }
/*     */ 
/*     */   public int getServiceType() {
/* 106 */     return this.serviceType;
/*     */   }
/*     */ 
/*     */   public void setServiceType(int serviceType) {
/* 110 */     this.serviceType = serviceType;
/*     */   }
/*     */ 
/*     */   public String getLinkId() {
/* 114 */     return this.linkId;
/*     */   }
/*     */ 
/*     */   public void setLinkId(String linkId) {
/* 118 */     this.linkId = linkId;
/*     */   }
/*     */ 
/*     */   public String getSendContent() {
/* 122 */     return this.sendContent;
/*     */   }
/*     */ 
/*     */   public void setSendContent(String sendContent) {
/* 126 */     this.sendContent = sendContent;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/* 146 */     return 0;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.SendNotificationMessage
 * JD-Core Version:    0.6.0
 */