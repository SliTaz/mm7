/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class UserOrderOrUnoderMessage extends AbstractMessage
/*    */ {
/*    */   private String CELLPHONENUMBER;
/*    */   private String CHARGEPARTY;
/*    */   private String SERVICEUNIQUEID;
/*    */   private Date ORDERDATE;
/*    */   private Date CANCELORDERDATE;
/*    */   private String ORDERMETHOD;
/*    */   private String FEETYPE;
/*    */   private int FEE;
/*    */   private String ORDERHISID;
/*    */   private int STATUS;
/*    */   private int PRIORITY;
/*    */ 
/*    */   public void decodeString(String message)
/*    */     throws DecodeMessageException
/*    */   {
/*    */   }
/*    */ 
/*    */   public String encodeString()
/*    */   {
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */   public MessageType getMessageType()
/*    */   {
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   public int getServiceId()
/*    */   {
/* 47 */     return 0;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.UserOrderOrUnoderMessage
 * JD-Core Version:    0.6.0
 */