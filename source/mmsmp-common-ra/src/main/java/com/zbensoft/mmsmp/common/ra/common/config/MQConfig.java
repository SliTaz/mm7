/*    */ package com.zbensoft.mmsmp.common.ra.common.config;
/*    */ 
/*    */ import com.aceway.common.app.IFileModifyListener;
/*    */ 
/*    */ public class MQConfig
/*    */ {
/*  8 */   private static MQConfig instance = new MQConfig();
/*  9 */   private boolean initFlag = false;
/*    */   private CommonConfig mqConfig;
/*    */ 
/*    */   public static MQConfig getInstance()
/*    */   {
/* 25 */     return instance;
/*    */   }
/*    */ 
/*    */   private void init()
/*    */   {
/* 32 */     if (!this.initFlag)
/* 33 */       this.mqConfig = CommonConfig.getInstance("mq.properties");
/*    */   }
/*    */ 
/*    */   public String getProperty(String name)
/*    */   {
/* 42 */     if (!this.initFlag) {
/* 43 */       init();
/*    */     }
/*    */ 
/* 46 */     return this.mqConfig.getProperty(name);
/*    */   }
/*    */ 
/*    */   public void addFileModifyListener(IFileModifyListener listener)
/*    */   {
/* 54 */     this.mqConfig.addFileModifyListener(listener);
/*    */   }
/*    */ 
/*    */   public String getSubSystemId()
/*    */   {
/* 63 */     return getProperty("SubSystemId");
/*    */   }
/*    */ 
/*    */   public int getWrapperPort()
/*    */   {
/* 72 */     return Integer.parseInt(getProperty("WrapperPort"));
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.MQConfig
 * JD-Core Version:    0.6.0
 */