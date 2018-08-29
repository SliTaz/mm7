/*    */ package com.zbensoft.mmsmp.common.ra.util;
/*    */ 
/*    */ public class FileProcessor
/*    */ {
/*  6 */   private static String Tifpath = ReadConfig.getFtpPath();
/*    */ 
/*    */   public static String getFileName(String filepath)
/*    */   {
/* 10 */     int a = filepath.lastIndexOf("/");
/* 11 */     String filename = filepath.substring(a + 1, filepath.length());
/* 12 */     return filename;
/*    */   }
/*    */ 
/*    */   public static String getDestpath(String orgname)
/*    */   {
/* 17 */     return Tifpath + orgname + "/";
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.util.FileProcessor
 * JD-Core Version:    0.6.0
 */