/*     */ package com.zbensoft.mmsmp.common.ra.common.smil;
/*     */ 
/*     */ public class Region
/*     */ {
/*   5 */   private String id = null;
/*   6 */   private String left = null;
/*   7 */   private String top = null;
/*   8 */   private String width = null;
/*   9 */   private String height = null;
/*  10 */   private String fit = null;
/*     */ 
/*     */   public Region(String id, String left, String top, String width, String height)
/*     */   {
/*  22 */     this.id = id;
/*  23 */     this.left = left;
/*  24 */     this.top = top;
/*  25 */     this.width = width;
/*  26 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public String getFit()
/*     */   {
/*  34 */     return this.fit;
/*     */   }
/*     */ 
/*     */   public void setFit(String fit)
/*     */   {
/*  42 */     this.fit = fit;
/*     */   }
/*     */ 
/*     */   public String getHeight()
/*     */   {
/*  50 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setHeight(String height)
/*     */   {
/*  58 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  66 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id)
/*     */   {
/*  74 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getLeft()
/*     */   {
/*  79 */     return this.left;
/*     */   }
/*     */ 
/*     */   public void setLeft(String left)
/*     */   {
/*  88 */     this.left = left;
/*     */   }
/*     */ 
/*     */   public String getTop()
/*     */   {
/*  93 */     return this.top;
/*     */   }
/*     */ 
/*     */   public void setTop(String top)
/*     */   {
/*  98 */     this.top = top;
/*     */   }
/*     */ 
/*     */   public String getWidth()
/*     */   {
/* 103 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void setWidth(String width)
/*     */   {
/* 108 */     this.width = width;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.smil.Region
 * JD-Core Version:    0.6.0
 */