/*   */ package com.zbensoft.mmsmp.common.ra.common.util;
/*   */ 
/*   */ public class MobileValidator
/*   */ {
/*   */   public static boolean isValidMMSReceiver(String number)
/*   */   {
/* 5 */     return (number != null) && (number.length() == 11) && (number.startsWith("13"));
/*   */   }
/*   */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.util.MobileValidator
 * JD-Core Version:    0.6.0
 */