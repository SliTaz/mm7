/*    */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*    */ 
/*    */ public class AcewayMMMessage
/*    */ {
/* 15 */   protected boolean fMessageTypeAvailable = false;
/*    */   protected String hMessageType;
/* 18 */   protected boolean fPartsInfoAvailable = false;
/* 19 */   protected String partsInfo = null;
/*    */ 
/*    */   public void reset()
/*    */   {
/* 35 */     this.fMessageTypeAvailable = false;
/* 36 */     this.hMessageType = null;
/* 37 */     this.partsInfo = null;
/*    */   }
/*    */ 
/*    */   public boolean isMessageTypeAvailable()
/*    */   {
/* 46 */     return this.fMessageTypeAvailable;
/*    */   }
/*    */ 
/*    */   public String getMessageType()
/*    */   {
/* 55 */     return this.hMessageType;
/*    */   }
/*    */ 
/*    */   public void setMessageType(String messageType_value)
/*    */   {
/* 64 */     this.fMessageTypeAvailable = true;
/* 65 */     this.hMessageType = messageType_value;
/*    */   }
/*    */ 
/*    */   public boolean isPartsInfoAvailable()
/*    */   {
/* 74 */     return this.fPartsInfoAvailable;
/*    */   }
/*    */ 
/*    */   public String getPartsInfo()
/*    */   {
/* 83 */     return this.partsInfo;
/*    */   }
/*    */ 
/*    */   public void setPartsInfo(String info)
/*    */   {
/* 92 */     this.fPartsInfoAvailable = true;
/* 93 */     this.partsInfo = info;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.AcewayMMMessage
 * JD-Core Version:    0.6.0
 */