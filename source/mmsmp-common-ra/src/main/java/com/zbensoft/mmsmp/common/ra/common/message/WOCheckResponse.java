/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class WOCheckResponse extends AbstractMessage
/*    */   implements Serializable
/*    */ {
/*    */   private String messageid;
/*    */   private int reqType;
/*    */   private String returnStr;
/*    */   private WOCheckRequest woRequest;
/*    */ 
/*    */   public String getMessageid()
/*    */   {
/* 13 */     return this.messageid;
/*    */   }
/*    */   public void setMessageid(String messageid) {
/* 16 */     this.messageid = messageid;
/*    */   }
/*    */   public int getReqType() {
/* 19 */     return this.reqType;
/*    */   }
/*    */   public void setReqType(int reqType) {
/* 22 */     this.reqType = reqType;
/*    */   }
/*    */   public String getReturnStr() {
/* 25 */     return this.returnStr;
/*    */   }
/*    */   public void setReturnStr(String returnStr) {
/* 28 */     this.returnStr = returnStr;
/*    */   }
/*    */   public WOCheckRequest getWoRequest() {
/* 31 */     return this.woRequest;
/*    */   }
/*    */   public void setWoRequest(WOCheckRequest woRequest) {
/* 34 */     this.woRequest = woRequest;
/*    */   }
/*    */ 
/*    */   public void decodeString(String message) throws DecodeMessageException
/*    */   {
/*    */   }
/*    */ 
/*    */   public String encodeString() {
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   public MessageType getMessageType() {
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */   public int getServiceId() {
/* 50 */     return 0;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.WOCheckResponse
 * JD-Core Version:    0.6.0
 */