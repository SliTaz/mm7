/*     */ package com.zbensoft.mmsmp.common.ra.common.config;
/*     */ 
/*     */ import com.aceway.common.app.IFileModifyListener;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class DispatcherConfig
/*     */ {
/*  11 */   private static final Log logger = LogFactory.getLog(DispatcherConfig.class);
/*  12 */   private static DispatcherConfig mInstance = new DispatcherConfig();
/*  13 */   private boolean initFlag = false;
/*  14 */   private CommonConfig mDispatcherConfig = null;
/*     */ 
/*     */   public static DispatcherConfig getInstance()
/*     */   {
/*  31 */     return mInstance;
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  37 */     if (!this.initFlag)
/*  38 */       this.mDispatcherConfig = CommonConfig.getInstance("dispatcher.properties");
/*  39 */     logger.info("mDispatcherConfig = null :" + this.mDispatcherConfig);
/*     */   }
/*     */ 
/*     */   public String getProperty(String name)
/*     */   {
/*  49 */     if (!this.initFlag)
/*  50 */       init();
/*  51 */     logger.info("Para name: " + name);
/*  52 */     return this.mDispatcherConfig.getProperty(name);
/*     */   }
/*     */ 
/*     */   public void addFileModifyListener(IFileModifyListener listener)
/*     */   {
/*  61 */     this.mDispatcherConfig.addFileModifyListener(listener);
/*     */   }
/*     */ 
/*     */   public int getPort()
/*     */   {
/*  70 */     return Integer.parseInt(getProperty("Port"));
/*     */   }
/*     */ 
/*     */   public int getHandlerNum()
/*     */   {
/*  79 */     return Integer.parseInt(getProperty("HandleNumber"));
/*     */   }
/*     */ 
/*     */   public boolean toStartBundle()
/*     */   {
/*  88 */     return Boolean.parseBoolean(getProperty("StartBundle"));
/*     */   }
/*     */ 
/*     */   public int getWrapperPort()
/*     */   {
/*  97 */     return Integer.parseInt(getProperty("WrapperPort"));
/*     */   }
/*     */ 
/*     */   public String getSubSystemId()
/*     */   {
/* 106 */     return getProperty("SubSystemId");
/*     */   }
/*     */ 
/*     */   public String getDispatchServerIP()
/*     */   {
/* 115 */     return getProperty("DispatchServerIP");
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.DispatcherConfig
 * JD-Core Version:    0.6.0
 */