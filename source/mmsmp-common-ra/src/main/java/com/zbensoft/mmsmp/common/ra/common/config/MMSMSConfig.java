/*     */ package com.zbensoft.mmsmp.common.ra.common.config;
/*     */ 
/*     */ import com.aceway.common.app.IFileModifyListener;
/*     */ 
/*     */ public class MMSMSConfig
/*     */ {
/*   7 */   private static MMSMSConfig instance = new MMSMSConfig();
/*   8 */   private boolean initFlag = false;
/*     */   private CommonConfig mmsmsConfig;
/*     */ 
/*     */   public static MMSMSConfig getInstance()
/*     */   {
/*  15 */     return instance;
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  21 */     if (!this.initFlag)
/*     */     {
/*  23 */       this.mmsmsConfig = CommonConfig.getInstance("mmsms.properties");
/*  24 */       this.initFlag = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getProperty(String name) {
/*  29 */     if (!this.initFlag) {
/*  30 */       init();
/*     */     }
/*     */ 
/*  33 */     return this.mmsmsConfig.getProperty(name);
/*     */   }
/*     */ 
/*     */   public void addFileModifyListener(IFileModifyListener listener) {
/*  37 */     this.mmsmsConfig.addFileModifyListener(listener);
/*     */   }
/*     */ 
/*     */   public String getFlowFilePath()
/*     */   {
/*  42 */     return getProperty("flowFilePath");
/*     */   }
/*     */ 
/*     */   public String getContentFilePath()
/*     */   {
/*  47 */     return getProperty("contentFilePath");
/*     */   }
/*     */ 
/*     */   public String getContentViewPicFilePath()
/*     */   {
/*  52 */     return getProperty("contentViewPicFilePath");
/*     */   }
/*     */ 
/*     */   public String getTempContentFilePath()
/*     */   {
/*  57 */     return getProperty("tempContentFilePath");
/*     */   }
/*     */ 
/*     */   public String getADPhonesFilePath()
/*     */   {
/*  62 */     return getProperty("ADPhonesFilePath");
/*     */   }
/*     */ 
/*     */   public String getDBUrl()
/*     */   {
/*  67 */     return getProperty("db.url");
/*     */   }
/*     */ 
/*     */   public String getDBUserName()
/*     */   {
/*  72 */     return getProperty("db.user");
/*     */   }
/*     */ 
/*     */   public String getDBUserPass()
/*     */   {
/*  77 */     return getProperty("db.password");
/*     */   }
/*     */ 
/*     */   public String getDataSrcName()
/*     */   {
/*  86 */     return getProperty("DataSrcName");
/*     */   }
/*     */ 
/*     */   public String getDriverClassName()
/*     */   {
/*  95 */     return getProperty("DriverClassName");
/*     */   }
/*     */ 
/*     */   public int getDataSrcMaxActive()
/*     */   {
/* 104 */     String maxActive = getProperty("DataSrcMaxActive");
/* 105 */     if (maxActive != null) {
/* 106 */       return Integer.parseInt(getProperty("DataSrcMaxActive"));
/*     */     }
/* 108 */     return 10;
/*     */   }
/*     */ 
/*     */   public long getDataSrcMaxWait()
/*     */   {
/* 118 */     String maxWaitTime = getProperty("DataSrcMaxWait");
/* 119 */     if (maxWaitTime != null) {
/* 120 */       return Long.parseLong(getProperty("DataSrcMaxWait"));
/*     */     }
/* 122 */     return 1000L;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.MMSMSConfig
 * JD-Core Version:    0.6.0
 */