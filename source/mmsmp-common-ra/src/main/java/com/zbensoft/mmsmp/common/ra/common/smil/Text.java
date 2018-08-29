/*    */ package com.zbensoft.mmsmp.common.ra.common.smil;
/*    */ 
/*    */ public class Text
/*    */   implements IMMSMediaContent
/*    */ {
/*    */   private String src;
/*    */   private String region;
/*    */   private String alt;
/*    */   private String begin;
/*    */   private String end;
/*    */ 
/*    */   public Text(String src, String region)
/*    */   {
/* 11 */     this.src = src;
/* 12 */     this.region = region;
/*    */   }
/*    */ 
/*    */   public String getAlt() {
/* 16 */     return this.alt;
/*    */   }
/*    */   public void setAlt(String alt) {
/* 19 */     this.alt = alt;
/*    */   }
/*    */   public String getBegin() {
/* 22 */     return this.begin;
/*    */   }
/*    */   public void setBegin(String begin) {
/* 25 */     this.begin = begin;
/*    */   }
/*    */   public String getEnd() {
/* 28 */     return this.end;
/*    */   }
/*    */   public void setEnd(String end) {
/* 31 */     this.end = end;
/*    */   }
/*    */   public String getRegion() {
/* 34 */     return this.region;
/*    */   }
/*    */   public void setRegion(String region) {
/* 37 */     this.region = region;
/*    */   }
/*    */   public String getSrc() {
/* 40 */     return this.src;
/*    */   }
/*    */   public void setSrc(String src) {
/* 43 */     this.src = src;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.smil.Text
 * JD-Core Version:    0.6.0
 */