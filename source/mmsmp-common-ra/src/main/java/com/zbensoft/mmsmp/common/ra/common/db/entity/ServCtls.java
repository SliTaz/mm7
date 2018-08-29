/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ServCtls
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8541903354456536995L;
/*     */   private Integer servuniqueid;
/*     */   private String ctlappname;
/*     */   private Integer threadnum;
/*     */   private String mqappname;
/*     */   private String bakmqappname;
/*     */   private String paylayxappname;
/*     */ 
/*     */   public ServCtls()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ServCtls(Integer servuniqueid, String ctlappname, Integer threadnum, String mqappname)
/*     */   {
/*  34 */     this.servuniqueid = servuniqueid;
/*  35 */     this.ctlappname = ctlappname;
/*  36 */     this.threadnum = threadnum;
/*  37 */     this.mqappname = mqappname;
/*     */   }
/*     */ 
/*     */   public ServCtls(Integer servuniqueid, String ctlappname, Integer threadnum, String mqappname, String bakmqappname, String paylayxappname)
/*     */   {
/*  42 */     this.servuniqueid = servuniqueid;
/*  43 */     this.ctlappname = ctlappname;
/*  44 */     this.threadnum = threadnum;
/*  45 */     this.mqappname = mqappname;
/*  46 */     this.bakmqappname = bakmqappname;
/*  47 */     this.paylayxappname = paylayxappname;
/*     */   }
/*     */   public String toString() {
/*  50 */     StringBuilder sb = new StringBuilder();
/*  51 */     sb.append("servuniqueid" + this.servuniqueid);
/*  52 */     sb.append("ctlappname" + this.ctlappname);
/*  53 */     sb.append("mqappname" + this.mqappname);
/*  54 */     sb.append("bakmqappname" + this.bakmqappname);
/*  55 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public Integer getServuniqueid()
/*     */   {
/*  62 */     return this.servuniqueid;
/*     */   }
/*     */ 
/*     */   public void setServuniqueid(Integer servuniqueid) {
/*  66 */     this.servuniqueid = servuniqueid;
/*     */   }
/*     */ 
/*     */   public String getCtlappname() {
/*  70 */     return this.ctlappname;
/*     */   }
/*     */ 
/*     */   public void setCtlappname(String ctlappname) {
/*  74 */     this.ctlappname = ctlappname;
/*     */   }
/*     */ 
/*     */   public Integer getThreadnum() {
/*  78 */     return this.threadnum;
/*     */   }
/*     */ 
/*     */   public void setThreadnum(Integer threadnum) {
/*  82 */     this.threadnum = threadnum;
/*     */   }
/*     */ 
/*     */   public String getMqappname() {
/*  86 */     return this.mqappname;
/*     */   }
/*     */ 
/*     */   public void setMqappname(String mqappname) {
/*  90 */     this.mqappname = mqappname;
/*     */   }
/*     */ 
/*     */   public String getBakmqappname() {
/*  94 */     return this.bakmqappname;
/*     */   }
/*     */ 
/*     */   public void setBakmqappname(String bakmqappname) {
/*  98 */     this.bakmqappname = bakmqappname;
/*     */   }
/*     */ 
/*     */   public String getPaylayxappname() {
/* 102 */     return this.paylayxappname;
/*     */   }
/*     */ 
/*     */   public void setPaylayxappname(String paylayxappname) {
/* 106 */     this.paylayxappname = paylayxappname;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServCtls
 * JD-Core Version:    0.6.0
 */