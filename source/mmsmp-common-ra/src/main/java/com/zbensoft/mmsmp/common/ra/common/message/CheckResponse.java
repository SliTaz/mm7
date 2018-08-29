/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CheckResponse extends AbstractMessage
/*     */   implements Serializable
/*     */ {
/*  26 */   private static final Logger logger = Logger.getLogger(CheckResponse.class);
/*     */   private String Result_Code;
/*     */   private String NeedConfirm;
/*     */   private String LinkID;
/*     */   private String ReturnMessage;
/*     */   private String SP_ProductID;
/*     */   private String SPEC_ProductID;
/*     */   private String ProductID;
/*     */   private String src_SequenceNumber;
/*     */   private String serverResult_code;
/*     */   private String serverMessage;
/*     */   private String spOrderId;
/*     */   private Integer messageid;
/*     */   private CheckRequest cRequest;
/*     */ 
/*     */   public CheckRequest getCRequest()
/*     */   {
/*  46 */     return this.cRequest;
/*     */   }
/*     */ 
/*     */   public void setCRequest(CheckRequest request)
/*     */   {
/*  51 */     this.cRequest = request;
/*     */   }
/*     */ 
/*     */   public Integer getMessageid()
/*     */   {
/*  56 */     return this.messageid;
/*     */   }
/*     */ 
/*     */   public void setMessageid(Integer messageid)
/*     */   {
/*  61 */     this.messageid = messageid;
/*     */   }
/*     */ 
/*     */   public String getSpOrderId()
/*     */   {
/*  66 */     return this.spOrderId;
/*     */   }
/*     */ 
/*     */   public void setSpOrderId(String spOrderId)
/*     */   {
/*  71 */     this.spOrderId = spOrderId;
/*     */   }
/*     */ 
/*     */   public String getServerMessage()
/*     */   {
/*  76 */     return this.serverMessage;
/*     */   }
/*     */ 
/*     */   public void setServerMessage(String serverMessage)
/*     */   {
/*  81 */     this.serverMessage = serverMessage;
/*     */   }
/*     */ 
/*     */   public String getServerResult_code()
/*     */   {
/* 162 */     return this.serverResult_code;
/*     */   }
/*     */ 
/*     */   public void setServerResult_code(String serverResultCode)
/*     */   {
/* 167 */     this.serverResult_code = serverResultCode;
/*     */   }
/*     */ 
/*     */   public String getResult_Code()
/*     */   {
/* 172 */     return this.Result_Code;
/*     */   }
/*     */   public void setResult_Code(String resultCode) {
/* 175 */     this.Result_Code = resultCode;
/*     */   }
/*     */   public String getNeedConfirm() {
/* 178 */     return this.NeedConfirm;
/*     */   }
/*     */   public void setNeedConfirm(String needConfirm) {
/* 181 */     this.NeedConfirm = needConfirm;
/*     */   }
/*     */   public String getLinkID() {
/* 184 */     return this.LinkID;
/*     */   }
/*     */   public void setLinkID(String linkID) {
/* 187 */     this.LinkID = linkID;
/*     */   }
/*     */   public String getReturnMessage() {
/* 190 */     return this.ReturnMessage;
/*     */   }
/*     */   public void setReturnMessage(String returnMessage) {
/* 193 */     this.ReturnMessage = returnMessage;
/*     */   }
/*     */   public String getSP_ProductID() {
/* 196 */     return this.SP_ProductID;
/*     */   }
/*     */   public void setSP_ProductID(String sPProductID) {
/* 199 */     this.SP_ProductID = sPProductID;
/*     */   }
/*     */   public String getSPEC_ProductID() {
/* 202 */     return this.SPEC_ProductID;
/*     */   }
/*     */   public void setSPEC_ProductID(String sPECProductID) {
/* 205 */     this.SPEC_ProductID = sPECProductID;
/*     */   }
/*     */   public String getProductID() {
/* 208 */     return this.ProductID;
/*     */   }
/*     */   public void setProductID(String productID) {
/* 211 */     this.ProductID = productID;
/*     */   }
/*     */   public String getSrc_SequenceNumber() {
/* 214 */     return this.src_SequenceNumber;
/*     */   }
/*     */   public void setSrc_SequenceNumber(String srcSequenceNumber) {
/* 217 */     this.src_SequenceNumber = srcSequenceNumber;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 234 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 240 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/* 246 */     return 0;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.CheckResponse
 * JD-Core Version:    0.6.0
 */