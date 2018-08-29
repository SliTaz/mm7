/*    */ package com.zbensoft.mmsmp.common.ra.common.smil;
/*    */ 
/*    */ public class Image
/*    */   implements IMMSMediaContent
/*    */ {
/*  5 */   private String src = null;
/*  6 */   private String region = null;
/*  7 */   private String alt = null;
/*  8 */   private String begin = null;
/*  9 */   private String end = null;
/*    */ 
/*    */   public Image(String src, String region)
/*    */   {
/* 18 */     this.src = src;
/* 19 */     this.region = region;
/*    */   }
/*    */ 
/*    */   public String getAlt()
/*    */   {
/* 27 */     return this.alt;
/*    */   }
/*    */ 
/*    */   public void setAlt(String alt)
/*    */   {
/* 35 */     this.alt = alt;
/*    */   }
/*    */   public String getBegin() {
/* 38 */     return this.begin;
/*    */   }
/*    */   public void setBegin(String begin) {
/* 41 */     this.begin = begin;
/*    */   }
/*    */   public String getEnd() {
/* 44 */     return this.end;
/*    */   }
/*    */   public void setEnd(String end) {
/* 47 */     this.end = end;
/*    */   }
/*    */   public String getRegion() {
/* 50 */     return this.region;
/*    */   }
/*    */   public void setRegion(String region) {
/* 53 */     this.region = region;
/*    */   }
/*    */   public String getSrc() {
/* 56 */     return this.src;
/*    */   }
/*    */ 
/*    */   public void setSrc(String src)
/*    */   {
/* 64 */     this.src = src;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.smil.Image
 * JD-Core Version:    0.6.0
 */