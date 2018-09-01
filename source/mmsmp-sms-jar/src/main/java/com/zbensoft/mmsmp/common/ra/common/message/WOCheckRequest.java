/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ 
/*    */ import java.io.Serializable;

/*    */
/*    */ public class WOCheckRequest extends AbstractMessage
/*    */   implements Serializable
/*    */ {
/*    */   private String userPhone;
/*    */   private String sp_productID;
/*    */   private String servicename;
/*    */   private int fee;
/*    */   private String feeType;
/*    */   private int peroid;
/*    */   private int reqType;
/*    */ 
/*    */   public String getUserPhone()
/*    */   {
/* 17 */     return this.userPhone;
/*    */   }
/*    */ 
/*    */   public void setUserPhone(String userPhone) {
/* 21 */     this.userPhone = userPhone;
/*    */   }
/*    */ 
/*    */   public String getSp_productID() {
/* 25 */     return this.sp_productID;
/*    */   }
/*    */ 
/*    */   public void setSp_productID(String sp_productID) {
/* 29 */     this.sp_productID = sp_productID;
/*    */   }
/*    */ 
/*    */   public String getServicename() {
/* 33 */     return this.servicename;
/*    */   }
/*    */ 
/*    */   public void setServicename(String servicename) {
/* 37 */     this.servicename = servicename;
/*    */   }
/*    */ 
/*    */   public int getFee() {
/* 41 */     return this.fee;
/*    */   }
/*    */ 
/*    */   public void setFee(int fee) {
/* 45 */     this.fee = fee;
/*    */   }
/*    */ 
/*    */   public String getFeeType() {
/* 49 */     return this.feeType;
/*    */   }
/*    */ 
/*    */   public void setFeeType(String feeType) {
/* 53 */     this.feeType = feeType;
/*    */   }
/*    */ 
/*    */   public int getPeroid() {
/* 57 */     return this.peroid;
/*    */   }
/*    */ 
/*    */   public void setPeroid(int peroid) {
/* 61 */     this.peroid = peroid;
/*    */   }
/*    */ 
/*    */   public int getReqType() {
/* 65 */     return this.reqType;
/*    */   }
/*    */ 
/*    */   public void setReqType(int reqType) {
/* 69 */     this.reqType = reqType;
/*    */   }
/*    */ 
/*    */   public void decodeString(String message)
/*    */     throws DecodeMessageException
/*    */   {
/*    */   }
/*    */ 
/*    */   public String encodeString()
/*    */   {
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */   public MessageType getMessageType()
/*    */   {
/* 84 */     return null;
/*    */   }
/*    */ 
/*    */   public int getServiceId()
/*    */   {
/* 89 */     return 0;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.WOCheckRequest
 * JD-Core Version:    0.6.0
 */