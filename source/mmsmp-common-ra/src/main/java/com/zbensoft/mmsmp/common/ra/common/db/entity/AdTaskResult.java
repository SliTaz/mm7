/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class AdTaskResult
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5736900239087290887L;
/*     */   private Integer taskid;
/*     */   private Date runstarttime;
/*     */   private Date runendtime;
/*     */   private Integer submitsum;
/*     */   private Integer validsubmitsum;
/*     */   private Integer successsum;
/*     */   private Integer unknownsum;
/*     */   private Integer failuresum;
/*     */   private Integer canceledsum;
/*     */   private String resultdesc;
/*     */ 
/*     */   public AdTaskResult()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AdTaskResult(Date runstarttime, Date runendtime, Integer submitsum, Integer validsubmitsum, Integer successsum, Integer unknownsum, Integer failuresum, Integer canceledsum, String resultdesc)
/*     */   {
/*  40 */     this.runstarttime = runstarttime;
/*  41 */     this.runendtime = runendtime;
/*  42 */     this.submitsum = submitsum;
/*  43 */     this.validsubmitsum = validsubmitsum;
/*  44 */     this.successsum = successsum;
/*  45 */     this.unknownsum = unknownsum;
/*  46 */     this.failuresum = failuresum;
/*  47 */     this.canceledsum = canceledsum;
/*  48 */     this.resultdesc = resultdesc;
/*     */   }
/*     */ 
/*     */   public Integer getTaskid()
/*     */   {
/*  55 */     return this.taskid;
/*     */   }
/*     */ 
/*     */   public void setTaskid(Integer taskid) {
/*  59 */     this.taskid = taskid;
/*     */   }
/*     */ 
/*     */   public Date getRunstarttime() {
/*  63 */     return this.runstarttime;
/*     */   }
/*     */ 
/*     */   public void setRunstarttime(Date runstarttime) {
/*  67 */     this.runstarttime = runstarttime;
/*     */   }
/*     */ 
/*     */   public Date getRunendtime() {
/*  71 */     return this.runendtime;
/*     */   }
/*     */ 
/*     */   public void setRunendtime(Date runendtime) {
/*  75 */     this.runendtime = runendtime;
/*     */   }
/*     */ 
/*     */   public Integer getSubmitsum() {
/*  79 */     return this.submitsum;
/*     */   }
/*     */ 
/*     */   public void setSubmitsum(Integer submitsum) {
/*  83 */     this.submitsum = submitsum;
/*     */   }
/*     */ 
/*     */   public Integer getValidsubmitsum() {
/*  87 */     return this.validsubmitsum;
/*     */   }
/*     */ 
/*     */   public void setValidsubmitsum(Integer validsubmitsum) {
/*  91 */     this.validsubmitsum = validsubmitsum;
/*     */   }
/*     */ 
/*     */   public Integer getSuccesssum() {
/*  95 */     return this.successsum;
/*     */   }
/*     */ 
/*     */   public void setSuccesssum(Integer successsum) {
/*  99 */     this.successsum = successsum;
/*     */   }
/*     */ 
/*     */   public Integer getUnknownsum() {
/* 103 */     return this.unknownsum;
/*     */   }
/*     */ 
/*     */   public void setUnknownsum(Integer unknownsum) {
/* 107 */     this.unknownsum = unknownsum;
/*     */   }
/*     */ 
/*     */   public Integer getFailuresum() {
/* 111 */     return this.failuresum;
/*     */   }
/*     */ 
/*     */   public void setFailuresum(Integer failuresum) {
/* 115 */     this.failuresum = failuresum;
/*     */   }
/*     */ 
/*     */   public Integer getCanceledsum() {
/* 119 */     return this.canceledsum;
/*     */   }
/*     */ 
/*     */   public void setCanceledsum(Integer canceledsum) {
/* 123 */     this.canceledsum = canceledsum;
/*     */   }
/*     */ 
/*     */   public String getResultdesc() {
/* 127 */     return this.resultdesc;
/*     */   }
/*     */ 
/*     */   public void setResultdesc(String resultdesc) {
/* 131 */     this.resultdesc = resultdesc;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.AdTaskResult
 * JD-Core Version:    0.6.0
 */