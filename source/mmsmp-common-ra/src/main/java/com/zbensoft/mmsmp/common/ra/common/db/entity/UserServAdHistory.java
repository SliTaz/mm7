/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class UserServAdHistory
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8695208814657357093L;
/*     */   private String mttranid;
/*     */   private String reqid;
/*     */   private String cellphonenumber;
/*     */   private String chargeparty;
/*     */   private Integer servuniqueid;
/*     */   private Integer contentid;
/*     */   private Date mttime;
/*     */   private String status;
/*     */   private String mtkind;
/*     */   private Date statustime;
/*     */   private String mtservtype;
/*     */   private Integer mtrelationid;
/*     */ 
/*     */   public UserServAdHistory()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UserServAdHistory(String reqid, String cellphonenumber, Integer servuniqueid, Integer contentid)
/*     */   {
/*  41 */     this.reqid = reqid;
/*  42 */     this.cellphonenumber = cellphonenumber;
/*  43 */     this.servuniqueid = servuniqueid;
/*  44 */     this.contentid = contentid;
/*     */   }
/*     */ 
/*     */   public UserServAdHistory(String reqid, String cellphonenumber, String chargeparty, Integer servuniqueid, Integer contentid, Date mttime, String status, String mtkind, Date statustime, String mtservtype, Integer mtrelationid)
/*     */   {
/*  49 */     this.reqid = reqid;
/*  50 */     this.cellphonenumber = cellphonenumber;
/*  51 */     this.chargeparty = chargeparty;
/*  52 */     this.servuniqueid = servuniqueid;
/*  53 */     this.contentid = contentid;
/*  54 */     this.mttime = mttime;
/*  55 */     this.status = status;
/*  56 */     this.mtkind = mtkind;
/*  57 */     this.statustime = statustime;
/*  58 */     this.mtservtype = mtservtype;
/*  59 */     this.mtrelationid = mtrelationid;
/*     */   }
/*     */ 
/*     */   public String getMttranid()
/*     */   {
/*  66 */     return this.mttranid;
/*     */   }
/*     */ 
/*     */   public void setMttranid(String mttranid) {
/*  70 */     this.mttranid = mttranid;
/*     */   }
/*     */ 
/*     */   public String getReqid() {
/*  74 */     return this.reqid;
/*     */   }
/*     */ 
/*     */   public void setReqid(String reqid) {
/*  78 */     this.reqid = reqid;
/*     */   }
/*     */ 
/*     */   public String getCellphonenumber() {
/*  82 */     return this.cellphonenumber;
/*     */   }
/*     */ 
/*     */   public void setCellphonenumber(String cellphonenumber) {
/*  86 */     this.cellphonenumber = cellphonenumber;
/*     */   }
/*     */ 
/*     */   public String getChargeparty() {
/*  90 */     return this.chargeparty;
/*     */   }
/*     */ 
/*     */   public void setChargeparty(String chargeparty) {
/*  94 */     this.chargeparty = chargeparty;
/*     */   }
/*     */ 
/*     */   public Integer getServuniqueid() {
/*  98 */     return this.servuniqueid;
/*     */   }
/*     */ 
/*     */   public void setServuniqueid(Integer servuniqueid) {
/* 102 */     this.servuniqueid = servuniqueid;
/*     */   }
/*     */ 
/*     */   public Integer getContentid() {
/* 106 */     return this.contentid;
/*     */   }
/*     */ 
/*     */   public void setContentid(Integer contentid) {
/* 110 */     this.contentid = contentid;
/*     */   }
/*     */ 
/*     */   public Date getMttime() {
/* 114 */     return this.mttime;
/*     */   }
/*     */ 
/*     */   public void setMttime(Date mttime) {
/* 118 */     this.mttime = mttime;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 122 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 126 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getMtkind() {
/* 130 */     return this.mtkind;
/*     */   }
/*     */ 
/*     */   public void setMtkind(String mtkind) {
/* 134 */     this.mtkind = mtkind;
/*     */   }
/*     */ 
/*     */   public Date getStatustime() {
/* 138 */     return this.statustime;
/*     */   }
/*     */ 
/*     */   public void setStatustime(Date statustime) {
/* 142 */     this.statustime = statustime;
/*     */   }
/*     */ 
/*     */   public String getMtservtype() {
/* 146 */     return this.mtservtype;
/*     */   }
/*     */ 
/*     */   public void setMtservtype(String mtservtype) {
/* 150 */     this.mtservtype = mtservtype;
/*     */   }
/*     */ 
/*     */   public Integer getMtrelationid() {
/* 154 */     return this.mtrelationid;
/*     */   }
/*     */ 
/*     */   public void setMtrelationid(Integer mtrelationid) {
/* 158 */     this.mtrelationid = mtrelationid;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserServAdHistory
 * JD-Core Version:    0.6.0
 */