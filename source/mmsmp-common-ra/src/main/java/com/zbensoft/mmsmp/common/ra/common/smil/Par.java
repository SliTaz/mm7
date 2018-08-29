/*     */ package com.zbensoft.mmsmp.common.ra.common.smil;
/*     */ 
/*     */ public class Par
/*     */ {
/*   5 */   private String dur = null;
/*   6 */   private Image image = null;
/*   7 */   private Text text = null;
/*   8 */   private Audio audio = null;
/*     */ 
/*  10 */   private Video video = null;
/*     */ 
/*     */   public Par(String dur, Image image, Text text, Audio audio)
/*     */   {
/*  21 */     this.dur = dur;
/*  22 */     this.image = image;
/*  23 */     this.text = text;
/*  24 */     this.audio = audio;
/*     */   }
/*     */ 
/*     */   public Par(String dur, Image image, Text text, Audio audio, Video video)
/*     */   {
/*  29 */     this.dur = dur;
/*  30 */     this.image = image;
/*  31 */     this.text = text;
/*  32 */     this.audio = audio;
/*  33 */     this.video = video;
/*     */   }
/*     */ 
/*     */   public Par(String dur, Text text, Image image, Audio audio, Video video)
/*     */   {
/*  38 */     this.dur = dur;
/*     */ 
/*  40 */     this.text = text;
/*  41 */     this.image = image;
/*  42 */     this.audio = audio;
/*  43 */     this.video = video;
/*     */   }
/*     */ 
/*     */   public Audio getAutio()
/*     */   {
/*  52 */     return this.audio;
/*     */   }
/*     */ 
/*     */   public Video getVideo() {
/*  56 */     return this.video;
/*     */   }
/*     */ 
/*     */   public void setVideo(Video video) {
/*  60 */     this.video = video;
/*     */   }
/*     */ 
/*     */   public void setAutio(Audio audio)
/*     */   {
/*  69 */     this.audio = audio;
/*     */   }
/*     */ 
/*     */   public String getDur()
/*     */   {
/*  78 */     return this.dur;
/*     */   }
/*     */ 
/*     */   public void setDur(String dur)
/*     */   {
/*  87 */     this.dur = dur;
/*     */   }
/*     */ 
/*     */   public Image getImage()
/*     */   {
/*  96 */     return this.image;
/*     */   }
/*     */ 
/*     */   public void setImage(Image image)
/*     */   {
/* 105 */     this.image = image;
/*     */   }
/*     */ 
/*     */   public Text getText()
/*     */   {
/* 114 */     return this.text;
/*     */   }
/*     */ 
/*     */   public void setText(Text text)
/*     */   {
/* 123 */     this.text = text;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.smil.Par
 * JD-Core Version:    0.6.0
 */