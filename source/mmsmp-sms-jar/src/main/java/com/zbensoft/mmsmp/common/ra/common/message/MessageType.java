/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ 
/*    */ public class MessageType
/*    */ {
/*    */   private String messageType;
/* 16 */   public static final MessageType USER_ORDER_MESSAGE = new MessageType("UserOrder.REQ");
/* 17 */   public static final MessageType USER_ORDERUSE_MESSAGE = new MessageType("UserOrderUse.REQ");
/* 18 */   public static final MessageType USER_CANCELORDER_MESSAGE = new MessageType("UserCancelOrder.REQ");
/* 19 */   public static final MessageType USER_ONDEMAND_MESSAGE = new MessageType("UserOnDemand.REQ");
/* 20 */   public static final MessageType MO_MM_MESSAGE = new MessageType("UserSendMM.REQ");
/* 21 */   public static final MessageType MO_SM_MESSAGE = new MessageType("UserSendSM.REQ");
/* 22 */   public static final MessageType MT_MM_MESSAGE = new MessageType("SendMMToUser.REQ");
/* 23 */   public static final MessageType MT_SM_MESSAGE = new MessageType("SendSMToUser.REQ");
/* 24 */   public static final MessageType MT_WAPPUSH_MESSAGE = new MessageType("SendWapPushToUser.REQ");
/* 25 */   public static final MessageType MO_MM_DR_MESSAGE = new MessageType("UserSendMMDR.REQ");
/* 26 */   public static final MessageType MO_REPORT_MESSAGE = new MessageType("MoReport.REQ");
/* 27 */   public static final MessageType MT_REPORT_MESSAGE = new MessageType("MtReport.REQ");
/*    */ 
/*    */   private MessageType(String messageType)
/*    */   {
/* 13 */     this.messageType = messageType;
/*    */   }
/*    */ 
/*    */   public String getMessageType()
/*    */   {
/* 35 */     return this.messageType;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 40 */     return this.messageType;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MessageType
 * JD-Core Version:    0.6.0
 */