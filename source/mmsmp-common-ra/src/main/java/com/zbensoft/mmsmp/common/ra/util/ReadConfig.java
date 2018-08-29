/*     */ package com.zbensoft.mmsmp.common.ra.util;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.FtpScanClient;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;

/*     */ import java.io.PrintStream;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class ReadConfig
/*     */ {
/*  11 */   private static Properties _properties = null;
/*     */ 
/*     */   public static String getServer()
/*     */   {
/*  37 */     return ConfigUtil.getInstance().getFtpScanClient().getFtpServer();
/*     */   }
/*     */ 
/*     */   public static String getUser()
/*     */   {
/*  45 */     return ConfigUtil.getInstance().getFtpScanClient().getFtpUser();
/*     */   }
/*     */ 
/*     */   public static String getLocalPath()
/*     */   {
/*  53 */     return ConfigUtil.getInstance().getFtpScanClient().getLocalPath();
/*     */   }
/*     */ 
/*     */   public static String getPasswd()
/*     */   {
/*  61 */     return ConfigUtil.getInstance().getFtpScanClient().getFtpPasswd();
/*     */   }
/*     */ 
/*     */   public static String getInterval()
/*     */   {
/*  69 */     return ConfigUtil.getInstance().getFtpScanClient().getFtpScanInterval();
/*     */   }
/*     */ 
/*     */   public static String getFtpPath()
/*     */   {
/*  77 */     return ConfigUtil.getInstance().getFtpScanClient().getFtpPath();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 101 */     System.out.println(ConfigUtil.getInstance().getFtpScanClient().getFtpPath());
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.util.ReadConfig
 * JD-Core Version:    0.6.0
 */