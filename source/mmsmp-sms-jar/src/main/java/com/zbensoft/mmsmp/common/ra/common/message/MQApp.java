/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ 
/*    */ public class MQApp
/*    */ {
/*  5 */   private String appName = null;
/*  6 */   public static final MQApp ParlayX = new MQApp("ParlayX");
/*  7 */   public static final MQApp Controller = new MQApp("Controller");
/*    */ 
/*    */   private MQApp(String appName)
/*    */   {
/* 15 */     this.appName = appName;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 20 */     return this.appName;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MQApp
 * JD-Core Version:    0.6.0
 */