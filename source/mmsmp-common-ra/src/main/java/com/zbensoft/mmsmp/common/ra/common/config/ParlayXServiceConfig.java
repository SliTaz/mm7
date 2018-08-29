/*    */ package com.zbensoft.mmsmp.common.ra.common.config;
/*    */ 
/*    */ import com.aceway.common.app.IFileModifyListener;
/*    */ 
/*    */ public class ParlayXServiceConfig
/*    */ {
/*  7 */   private static ParlayXServiceConfig instance = new ParlayXServiceConfig();
/*  8 */   private boolean initFlag = false;
/*    */   private CommonConfig parlayxServiceConfig;
/*    */ 
/*    */   public static ParlayXServiceConfig getInstance()
/*    */   {
/* 14 */     return instance;
/*    */   }
/*    */ 
/*    */   private void init()
/*    */   {
/* 19 */     if (!this.initFlag) {
/* 20 */       this.parlayxServiceConfig = CommonConfig.getInstance("parlayXService.properties");
/* 21 */       this.initFlag = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getProperty(String name) {
/* 25 */     if (!this.initFlag) {
/* 26 */       init();
/*    */     }
/* 28 */     return this.parlayxServiceConfig.getProperty(name);
/*    */   }
/*    */   public void addFileModifyListener(IFileModifyListener listener) {
/* 31 */     this.parlayxServiceConfig.addFileModifyListener(listener);
/*    */   }
/*    */   public String getSubSystemId() {
/* 34 */     return getProperty("SubSystemId");
/*    */   }
/*    */ 
/*    */   public int getPxsmsWrapperPort()
/*    */   {
/* 41 */     return Integer.parseInt(getProperty("pxsms.WrapperPort").trim());
/*    */   }
/*    */ 
/*    */   public int getPxmmsWrapperPort()
/*    */   {
/* 48 */     return Integer.parseInt(getProperty("pxmms.WrapperPort").trim());
/*    */   }
/*    */ 
/*    */   public int getVasIdLength()
/*    */   {
/* 55 */     return Integer.parseInt(getProperty("vasid.length").trim());
/*    */   }
/*    */   public int getSmsVasIdLength() {
/* 58 */     return Integer.parseInt(getProperty("sms.vasid.length").trim());
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.ParlayXServiceConfig
 * JD-Core Version:    0.6.0
 */