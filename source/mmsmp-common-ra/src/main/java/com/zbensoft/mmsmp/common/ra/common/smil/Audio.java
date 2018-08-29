/*    */ package com.zbensoft.mmsmp.common.ra.common.smil;
/*    */ 
/*    */ public class Audio
/*    */   implements IMMSMediaContent
/*    */ {
/*    */   private String src;
/*    */   private String alt;
/*    */   private String begin;
/*    */   private String end;
/*    */ 
/*    */   public Audio(String src)
/*    */   {
/* 10 */     this.src = src;
/*    */   }
/*    */ 
/*    */   public String getAlt() {
/* 14 */     return this.alt;
/*    */   }
/*    */   public void setAlt(String alt) {
/* 17 */     this.alt = alt;
/*    */   }
/*    */   public String getBegin() {
/* 20 */     return this.begin;
/*    */   }
/*    */   public void setBegin(String begin) {
/* 23 */     this.begin = begin;
/*    */   }
/*    */   public String getEnd() {
/* 26 */     return this.end;
/*    */   }
/*    */   public void setEnd(String end) {
/* 29 */     this.end = end;
/*    */   }
/*    */   public String getSrc() {
/* 32 */     return this.src;
/*    */   }
/*    */   public void setSrc(String src) {
/* 35 */     this.src = src;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.smil.Audio
 * JD-Core Version:    0.6.0
 */