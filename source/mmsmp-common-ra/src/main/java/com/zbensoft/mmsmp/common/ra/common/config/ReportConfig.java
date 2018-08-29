/*    */ package com.zbensoft.mmsmp.common.ra.common.config;
/*    */ 
/*    */ import com.aceway.common.app.IFileModifyListener;
/*    */ 
/*    */ public class ReportConfig
/*    */ {
/*  9 */   private static ReportConfig instance = new ReportConfig();
/* 10 */   private boolean initFlag = false;
/* 11 */   private CommonConfig mmsmsConfig = null;
/*    */ 
/*    */   public static ReportConfig getInstance()
/*    */   {
/* 28 */     return instance;
/*    */   }
/*    */ 
/*    */   private void init()
/*    */   {
/* 37 */     if (!this.initFlag)
/*    */     {
/* 39 */       this.mmsmsConfig = CommonConfig.getInstance("mmsms.properties");
/* 40 */       this.initFlag = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getProperty(String name)
/*    */   {
/* 51 */     if (!this.initFlag) {
/* 52 */       init();
/*    */     }
/* 54 */     return this.mmsmsConfig.getProperty(name);
/*    */   }
/*    */ 
/*    */   public void addFileModifyListener(IFileModifyListener listener)
/*    */   {
/* 63 */     this.mmsmsConfig.addFileModifyListener(listener);
/*    */   }
/*    */ 
/*    */   public String getTaskName(int index)
/*    */   {
/* 73 */     return getProperty("TaskName" + index);
/*    */   }
/*    */ 
/*    */   public String getStartStatTime()
/*    */   {
/* 82 */     return getProperty("StartStatTime");
/*    */   }
/*    */ 
/*    */   public int getTaskNums()
/*    */   {
/* 91 */     return Integer.parseInt(getProperty("TaskNums"));
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.ReportConfig
 * JD-Core Version:    0.6.0
 */