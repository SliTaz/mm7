/*    */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AcewayMMSInfo
/*    */ {
/*  6 */   private Map<String, String> headers = new HashMap();
/*    */ 
/*    */   public void setHeader(String key, String value) {
/*  9 */     this.headers.put(key, value);
/*    */   }
/*    */ 
/*    */   public String getHeader(String key) {
/* 13 */     return (String)this.headers.get(key);
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.AcewayMMSInfo
 * JD-Core Version:    0.6.0
 */