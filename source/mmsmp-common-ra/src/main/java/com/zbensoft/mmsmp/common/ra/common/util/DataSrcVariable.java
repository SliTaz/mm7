/*     */ package com.zbensoft.mmsmp.common.ra.common.util;
/*     */ 
/*     */ public class DataSrcVariable
/*     */ {
/*   5 */   private String mDataSrcName = null;
/*   6 */   private String mDriverClassName = null;
/*   7 */   private String mDBUrl = null;
/*   8 */   private String mDBUserName = null;
/*   9 */   private String mDBPassWord = null;
/*  10 */   private int mDataSrcMaxActive = 5;
/*  11 */   private long mDataSrcMaxWait = 5000L;
/*     */ 
/*     */   public int getMDataSrcMaxActive()
/*     */   {
/*  27 */     return this.mDataSrcMaxActive;
/*     */   }
/*     */ 
/*     */   public void setMDataSrcMaxActive(int dataSrcMaxActive)
/*     */   {
/*  35 */     this.mDataSrcMaxActive = dataSrcMaxActive;
/*     */   }
/*     */ 
/*     */   public long getMDataSrcMaxWait()
/*     */   {
/*  43 */     return this.mDataSrcMaxWait;
/*     */   }
/*     */ 
/*     */   public void setMDataSrcMaxWait(long dataSrcMaxWait)
/*     */   {
/*  51 */     this.mDataSrcMaxWait = dataSrcMaxWait;
/*     */   }
/*     */ 
/*     */   public String getMDataSrcName()
/*     */   {
/*  59 */     return this.mDataSrcName;
/*     */   }
/*     */ 
/*     */   public void setMDataSrcName(String dataSrcName)
/*     */   {
/*  67 */     this.mDataSrcName = dataSrcName;
/*     */   }
/*     */ 
/*     */   public String getMDBPassWord()
/*     */   {
/*  75 */     return this.mDBPassWord;
/*     */   }
/*     */ 
/*     */   public void setMDBPassWord(String passWord)
/*     */   {
/*  83 */     this.mDBPassWord = passWord;
/*     */   }
/*     */ 
/*     */   public String getMDBUrl()
/*     */   {
/*  91 */     return this.mDBUrl;
/*     */   }
/*     */ 
/*     */   public void setMDBUrl(String url)
/*     */   {
/*  99 */     this.mDBUrl = url;
/*     */   }
/*     */ 
/*     */   public String getMDBUserName()
/*     */   {
/* 107 */     return this.mDBUserName;
/*     */   }
/*     */ 
/*     */   public void setMDBUserName(String userName)
/*     */   {
/* 115 */     this.mDBUserName = userName;
/*     */   }
/*     */ 
/*     */   public String getMDriverClassName()
/*     */   {
/* 123 */     return this.mDriverClassName;
/*     */   }
/*     */ 
/*     */   public void setMDriverClassName(String driverClassName)
/*     */   {
/* 131 */     this.mDriverClassName = driverClassName;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.util.DataSrcVariable
 * JD-Core Version:    0.6.0
 */