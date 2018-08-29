/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import com.cmcc.mm7.vasp.common.MMContent;
/*     */ import com.cmcc.mm7.vasp.common.SOAPDecoder;
/*     */ import com.cmcc.mm7.vasp.message.MM7DeliverReq;
/*     */ import com.cmcc.mm7.vasp.message.MM7RSReq;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class MO_MMDeliverSPMessage extends AbstractMessage
/*     */   implements Serializable
/*     */ {
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
/*     */   private String messageid;
/*  39 */   private Map<String, String> requestHeads = new HashMap();
/*     */   private byte[] contentByte;
/*     */   private String needConfirm;
/*     */   private String spid;
/*     */   private String servicesId;
/*     */   private String productId;
/*     */   private int checkType;
/*     */ 
/*     */   public String getNeedConfirm()
/*     */   {
/*  50 */     return this.needConfirm;
/*     */   }
/*     */   public void setNeedConfirm(String needConfirm) {
/*  53 */     this.needConfirm = needConfirm;
/*     */   }
/*     */ 
/*     */   public String getServicesId() {
/*  57 */     return this.servicesId;
/*     */   }
/*     */   public void setServicesId(String servicesId) {
/*  60 */     this.servicesId = servicesId;
/*     */   }
/*     */   public String getSpid() {
/*  63 */     return this.spid;
/*     */   }
/*     */   public void setSpid(String spid) {
/*  66 */     this.spid = spid;
/*     */   }
/*     */ 
/*     */   public String getProductId() {
/*  70 */     return this.productId;
/*     */   }
/*     */   public void setProductId(String productId) {
/*  73 */     this.productId = productId;
/*     */   }
/*     */   public int getCheckType() {
/*  76 */     return this.checkType;
/*     */   }
/*     */   public void setCheckType(int checkType) {
/*  79 */     this.checkType = checkType;
/*     */   }
/*     */   public String getSendurl() {
/*  82 */     return this.sendurl;
/*     */   }
/*     */   public void setSendurl(String sendurl) {
/*  85 */     this.sendurl = sendurl;
/*     */   }
/*     */   public String getMessageid() {
/*  88 */     return this.messageid;
/*     */   }
/*     */   public void setMessageid(String messageid) {
/*  91 */     this.messageid = messageid;
/*     */   }
/*     */   public Map<String, String> getRequestHeads() {
/*  94 */     return this.requestHeads;
/*     */   }
/*     */   public void setRequestHeads(Map<String, String> requestHeads) {
/*  97 */     this.requestHeads = requestHeads;
/*     */   }
/*     */ 
/*     */   public byte[] getContentByte() {
/* 101 */     return this.contentByte;
/*     */   }
/*     */   public void setContentByte(byte[] contentByte) {
/* 104 */     this.contentByte = contentByte;
/*     */   }
/*     */ 
/*     */   public void decodeString(String paramString) throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString() {
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId() {
/* 120 */     return 0;
/*     */   }
/*     */ 
/*     */   public MM7DeliverReq getMM7DeliverReq()
/*     */   {
/* 125 */     ByteArrayOutputStream baos = null;
/*     */     try
/*     */     {
/* 129 */       baos = new ByteArrayOutputStream();
/* 130 */       baos.write(this.contentByte);
/* 131 */       SOAPDecoder soapDecoder = new SOAPDecoder();
/* 132 */       soapDecoder.setMessage(baos);
/* 133 */       soapDecoder.decodeMessage();
/* 134 */       MM7RSReq rsReq = soapDecoder.getMessage();
/* 135 */       MM7DeliverReq localMM7DeliverReq = (MM7DeliverReq)rsReq;
/*     */       return localMM7DeliverReq;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */       return null;
/*     */     }
/*     */     finally {
/*     */       try {
/* 143 */         baos.close(); } catch (Exception localException3) {
/*     */       }
/* 144 */     }throw localObject;
/*     */   }
/*     */ 
/*     */   public String getHead(String key)
/*     */   {
/* 149 */     return (String)this.requestHeads.get(key.toLowerCase());
/*     */   }
/*     */   public List getBcclist() {
/* 152 */     return this.bcclist;
/*     */   }
/*     */   public void setBcclist(List bcclist) {
/* 155 */     this.bcclist = bcclist;
/*     */   }
/*     */   public List getCclist() {
/* 158 */     return this.cclist;
/*     */   }
/*     */   public void setCclist(List cclist) {
/* 161 */     this.cclist = cclist;
/*     */   }
/*     */   public MMContent getContent() {
/* 164 */     return this.Content;
/*     */   }
/*     */   public void setContent(MMContent content) {
/* 167 */     this.Content = content;
/*     */   }
/*     */   public String getLinkedID() {
/* 170 */     return this.LinkedID;
/*     */   }
/*     */   public void setLinkedID(String linkedID) {
/* 173 */     this.LinkedID = linkedID;
/*     */   }
/*     */   public String getMM7Version() {
/* 176 */     return this.MM7Version;
/*     */   }
/*     */   public void setMM7Version(String version) {
/* 179 */     this.MM7Version = version;
/*     */   }
/*     */   public String getMMSRelayServerID() {
/* 182 */     return this.MMSRelayServerID;
/*     */   }
/*     */   public void setMMSRelayServerID(String relayServerID) {
/* 185 */     this.MMSRelayServerID = relayServerID;
/*     */   }
/*     */   public String getReplyChargingID() {
/* 188 */     return this.ReplyChargingID;
/*     */   }
/*     */   public void setReplyChargingID(String replyChargingID) {
/* 191 */     this.ReplyChargingID = replyChargingID;
/*     */   }
/*     */   public String getSender() {
/* 194 */     return this.Sender;
/*     */   }
/*     */   public void setSender(String sender) {
/* 197 */     this.Sender = sender;
/*     */   }
/*     */   public String getSubject() {
/* 200 */     return this.Subject;
/*     */   }
/*     */   public void setSubject(String subject) {
/* 203 */     this.Subject = subject;
/*     */   }
/*     */   public Date getTimeStamp() {
/* 206 */     return this.TimeStamp;
/*     */   }
/*     */   public void setTimeStamp(Date timeStamp) {
/* 209 */     this.TimeStamp = timeStamp;
/*     */   }
/*     */   public List getTo() {
/* 212 */     return this.To;
/*     */   }
/*     */   public void setTo(List to) {
/* 215 */     this.To = to;
/*     */   }
/*     */   public String getTransactionID() {
/* 218 */     return this.TransactionID;
/*     */   }
/*     */   public void setTransactionID(String transactionID) {
/* 221 */     this.TransactionID = transactionID;
/*     */   }
/*     */   public String getContentType() {
/* 224 */     return this.contentType;
/*     */   }
/*     */   public void setContentType(String contentType) {
/* 227 */     this.contentType = contentType;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MO_MMDeliverSPMessage
 * JD-Core Version:    0.6.0
 */