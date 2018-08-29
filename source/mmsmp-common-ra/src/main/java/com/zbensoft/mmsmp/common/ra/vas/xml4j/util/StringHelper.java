/*    */ package com.zbensoft.mmsmp.common.ra.vas.xml4j.util;
/*    */ 
/*    */ public class StringHelper
/*    */ {
/*    */   public static String capitalizeFirst(String source)
/*    */   {
/*  6 */     if ((source == null) || ("".equals(source)))
/*  7 */       return source;
/*  8 */     StringBuffer result = new StringBuffer();
/*  9 */     result.append(Character.toUpperCase(source.charAt(0)));
/* 10 */     if (source.length() > 1)
/* 11 */       result.append(source.substring(1));
/* 12 */     return result.toString();
/*    */   }
/*    */ 
/*    */   public static String classNameWithoutPackage(String source) {
/* 16 */     if ((source == null) || ("".equals(source)))
/* 17 */       return source;
/* 18 */     int index = source.lastIndexOf(".");
/* 19 */     if (index < 0)
/* 20 */       return source;
/* 21 */     return source.substring(index + 1);
/*    */   }
/*    */ 
/*    */   public static String lowerCaseFirst(String source) {
/* 25 */     if (source == null)
/* 26 */       return null;
/* 27 */     if (source.equals(""))
/* 28 */       return "";
/* 29 */     StringBuffer result = new StringBuffer();
/* 30 */     result.append(Character.toLowerCase(source.charAt(0)));
/* 31 */     if (source.length() > 1)
/* 32 */       result.append(source.substring(1));
/* 33 */     return result.toString();
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.util.StringHelper
 * JD-Core Version:    0.6.0
 */