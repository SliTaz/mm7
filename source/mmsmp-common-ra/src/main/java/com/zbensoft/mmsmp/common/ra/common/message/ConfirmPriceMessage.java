/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ 
/*    */ public class ConfirmPriceMessage extends AbstractMessage
/*    */ {
/*    */   private String serviceCode;
/*    */   private String strPhone;
/*    */   private String messageId;
/*    */   private String recipient;
/*    */   private String transactionID;
/*    */ 
/*    */   public void decodeString(String message)
/*    */     throws DecodeMessageException
/*    */   {
/*    */   }
/*    */ 
/*    */   public String encodeString()
/*    */   {
/* 22 */     return null;
/*    */   }
/*    */ 
/*    */   public MessageType getMessageType()
/*    */   {
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */   public int getServiceId()
/*    */   {
/* 32 */     return 0;
/*    */   }
/*    */ 
/*    */   public String getServiceCode() {
/* 36 */     return this.serviceCode;
/*    */   }
/*    */ 
/*    */   public void setServiceCode(String serviceCode) {
/* 40 */     this.serviceCode = serviceCode;
/*    */   }
/*    */ 
/*    */   public String getStrPhone() {
/* 44 */     return this.strPhone;
/*    */   }
/*    */ 
/*    */   public void setStrPhone(String strPhone) {
/* 48 */     this.strPhone = strPhone;
/*    */   }
/*    */ 
/*    */   public String getMessageId() {
/* 52 */     return this.messageId;
/*    */   }
/*    */ 
/*    */   public void setMessageId(String messageId) {
/* 56 */     this.messageId = messageId;
/*    */   }
/*    */ 
/*    */   public String getRecipient() {
/* 60 */     return this.recipient;
/*    */   }
/*    */ 
/*    */   public void setRecipient(String recipient) {
/* 64 */     this.recipient = recipient;
/*    */   }
/*    */ 
/*    */   public String getTransactionID() {
/* 68 */     return this.transactionID;
/*    */   }
/*    */ 
/*    */   public void setTransactionID(String transactionID) {
/* 72 */     this.transactionID = transactionID;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.ConfirmPriceMessage
 * JD-Core Version:    0.6.0
 */