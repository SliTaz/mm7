/*    */ package com.zbensoft.mmsmp.common.ra.common.smil;
/*    */ 
/*    */ public class Video
/*    */   implements IMMSMediaContent
/*    */ {
/*    */   private String src;
/*    */   private String alt;
/*    */   private String begin;
/*    */   private String end;
/*    */ 
/*    */   public Video(String src)
/*    */   {
/*  9 */     this.src = src;
/*    */   }
/*    */   public String getSrc() {
/* 12 */     return this.src;
/*    */   }
/*    */   public void setSrc(String src) {
/* 15 */     this.src = src;
/*    */   }
/*    */   public String getAlt() {
/* 18 */     return this.alt;
/*    */   }
/*    */   public void setAlt(String alt) {
/* 21 */     this.alt = alt;
/*    */   }
/*    */   public String getBegin() {
/* 24 */     return this.begin;
/*    */   }
/*    */   public void setBegin(String begin) {
/* 27 */     this.begin = begin;
/*    */   }
/*    */   public String getEnd() {
/* 30 */     return this.end;
/*    */   }
/*    */   public void setEnd(String end) {
/* 33 */     this.end = end;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.smil.Video
 * JD-Core Version:    0.6.0
 */