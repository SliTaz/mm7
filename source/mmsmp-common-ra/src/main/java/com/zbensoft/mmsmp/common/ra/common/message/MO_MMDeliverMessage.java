/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import com.cmcc.mm7.vasp.common.MMContent;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class MO_MMDeliverMessage extends AbstractMessage
/*     */   implements Serializable
/*     */ {
/*     */   private HttpServletRequest request;
/*     */   private List bcclist;
/*     */   private List cclist;
/*     */   private MMContent Content;
/*     */   private String LinkedID;
/*     */   private String MM7Version;
/*     */   private String MMSRelayServerID;
/*     */   private String ReplyChargingID;
/*     */   private String Sender;
/*     */   private String Subject;
/*     */   private Date TimeStamp;
/*     */   private List To;
/*     */   private String TransactionID;
/*     */   private String contentType;
/*     */   private String sendurl;
/*     */ 
/*     */   public String getSendurl()
/*     */   {
/*  32 */     return this.sendurl;
/*     */   }
/*     */ 
/*     */   public void setSendurl(String sendurl) {
/*  36 */     this.sendurl = sendurl;
/*     */   }
/*     */ 
/*     */   public String getContentType() {
/*  40 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   public void setContentType(String contentType) {
/*  44 */     this.contentType = contentType;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/*  59 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  64 */     return 0;
/*     */   }
/*     */ 
/*     */   public List getBcclist() {
/*  68 */     return this.bcclist;
/*     */   }
/*     */ 
/*     */   public void setBcclist(List bcclist) {
/*  72 */     this.bcclist = bcclist;
/*     */   }
/*     */ 
/*     */   public List getCclist() {
/*  76 */     return this.cclist;
/*     */   }
/*     */ 
/*     */   public void setCclist(List cclist) {
/*  80 */     this.cclist = cclist;
/*     */   }
/*     */ 
/*     */   public MMContent getContent() {
/*  84 */     return this.Content;
/*     */   }
/*     */ 
/*     */   public void setContent(MMContent content) {
/*  88 */     this.Content = content;
/*     */   }
/*     */ 
/*     */   public String getLinkedID() {
/*  92 */     return this.LinkedID;
/*     */   }
/*     */ 
/*     */   public void setLinkedID(String linkedID) {
/*  96 */     this.LinkedID = linkedID;
/*     */   }
/*     */ 
/*     */   public String getMM7Version() {
/* 100 */     return this.MM7Version;
/*     */   }
/*     */ 
/*     */   public void setMM7Version(String mM7Version) {
/* 104 */     this.MM7Version = mM7Version;
/*     */   }
/*     */ 
/*     */   public String getMMSRelayServerID() {
/* 108 */     return this.MMSRelayServerID;
/*     */   }
/*     */ 
/*     */   public void setMMSRelayServerID(String mMSRelayServerID) {
/* 112 */     this.MMSRelayServerID = mMSRelayServerID;
/*     */   }
/*     */ 
/*     */   public String getReplyChargingID()
/*     */   {
/* 118 */     return this.ReplyChargingID;
/*     */   }
/*     */ 
/*     */   public void setReplyChargingID(String replyChargingID) {
/* 122 */     this.ReplyChargingID = replyChargingID;
/*     */   }
/*     */ 
/*     */   public String getSender() {
/* 126 */     return this.Sender;
/*     */   }
/*     */ 
/*     */   public void setSender(String sender) {
/* 130 */     this.Sender = sender;
/*     */   }
/*     */ 
/*     */   public String getSubject() {
/* 134 */     return this.Subject;
/*     */   }
/*     */ 
/*     */   public void setSubject(String subject) {
/* 138 */     this.Subject = subject;
/*     */   }
/*     */ 
/*     */   public Date getTimeStamp() {
/* 142 */     return this.TimeStamp;
/*     */   }
/*     */ 
/*     */   public void setTimeStamp(Date timeStamp) {
/* 146 */     this.TimeStamp = timeStamp;
/*     */   }
/*     */ 
/*     */   public List getTo() {
/* 150 */     return this.To;
/*     */   }
/*     */ 
/*     */   public void setTo(List to) {
/* 154 */     this.To = to;
/*     */   }
/*     */ 
/*     */   public String getTransactionID() {
/* 158 */     return this.TransactionID;
/*     */   }
/*     */ 
/*     */   public void setTransactionID(String transactionID) {
/* 162 */     this.TransactionID = transactionID;
/*     */   }
/*     */ 
/*     */   public HttpServletRequest getRequest() {
/* 166 */     return this.request;
/*     */   }
/*     */ 
/*     */   public void setRequest(HttpServletRequest request) {
/* 170 */     this.request = request;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MO_MMDeliverMessage
 * JD-Core Version:    0.6.0
 */