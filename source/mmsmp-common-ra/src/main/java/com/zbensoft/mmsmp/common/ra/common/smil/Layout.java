/*    */ package com.zbensoft.mmsmp.common.ra.common.smil;
/*    */ 
/*    */ public class Layout
/*    */ {
/*  7 */   private RootLayout rootLayout = null;
/*  8 */   private Region imageRegion = null;
/*  9 */   private Region textRegion = null;
/*    */ 
/* 11 */   private Region soundRegion = null;
/* 12 */   private Region videoRegion = null;
/*    */ 
/*    */   public Layout(RootLayout rootLayout, Region image, Region text)
/*    */   {
/* 23 */     this.rootLayout = rootLayout;
/* 24 */     this.imageRegion = image;
/* 25 */     this.textRegion = text;
/*    */   }
/*    */ 
/*    */   public Layout(RootLayout rootLayout, Region image, Region text, Region sound, Region video)
/*    */   {
/* 30 */     this.rootLayout = rootLayout;
/* 31 */     this.imageRegion = image;
/* 32 */     this.textRegion = text;
/* 33 */     this.soundRegion = sound;
/* 34 */     this.videoRegion = video;
/*    */   }
/*    */ 
/*    */   public Region getImageRegion()
/*    */   {
/* 43 */     return this.imageRegion;
/*    */   }
/*    */ 
/*    */   public void setImageRegion(Region imageRegion)
/*    */   {
/* 52 */     this.imageRegion = imageRegion;
/*    */   }
/*    */ 
/*    */   public RootLayout getRootLayout()
/*    */   {
/* 57 */     return this.rootLayout;
/*    */   }
/*    */ 
/*    */   public void setRootLayout(RootLayout rootLayout)
/*    */   {
/* 66 */     this.rootLayout = rootLayout;
/*    */   }
/*    */ 
/*    */   public Region getTextRegion()
/*    */   {
/* 71 */     return this.textRegion;
/*    */   }
/*    */ 
/*    */   public void setTextRegion(Region textRegion)
/*    */   {
/* 80 */     this.textRegion = textRegion;
/*    */   }
/*    */ 
/*    */   public Region getSoundRegion() {
/* 84 */     return this.soundRegion;
/*    */   }
/*    */ 
/*    */   public void setSoundRegion(Region soundRegion) {
/* 88 */     this.soundRegion = soundRegion;
/*    */   }
/*    */ 
/*    */   public Region getVideoRegion() {
/* 92 */     return this.videoRegion;
/*    */   }
/*    */ 
/*    */   public void setVideoRegion(Region videoRegion) {
/* 96 */     this.videoRegion = videoRegion;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.smil.Layout
 * JD-Core Version:    0.6.0
 */