/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class FlowFiles
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7078603419849918229L;
/*     */   private Integer flowid;
/*     */   private String flowname;
/*     */   private String flowcode;
/*     */   private String author;
/*     */   private Date createdate;
/*     */   private String status;
/*     */   private String flowfile;
/*     */   private String flowdesc;
/*     */ 
/*     */   public FlowFiles()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FlowFiles(String flowname, String flowcode, String status, String flowfile)
/*     */   {
/*  45 */     this.flowname = flowname;
/*  46 */     this.flowcode = flowcode;
/*  47 */     this.status = status;
/*  48 */     this.flowfile = flowfile;
/*     */   }
/*     */ 
/*     */   public FlowFiles(String flowname, String flowcode, String author, Date createdate, String status, String flowfile, String flowdesc)
/*     */   {
/*  64 */     this.flowname = flowname;
/*  65 */     this.flowcode = flowcode;
/*  66 */     this.author = author;
/*  67 */     this.createdate = createdate;
/*  68 */     this.status = status;
/*  69 */     this.flowfile = flowfile;
/*  70 */     this.flowdesc = flowdesc;
/*     */   }
/*     */ 
/*     */   public Integer getFlowid()
/*     */   {
/*  75 */     return this.flowid;
/*     */   }
/*     */ 
/*     */   public void setFlowid(Integer flowid) {
/*  79 */     this.flowid = flowid;
/*     */   }
/*     */ 
/*     */   public String getFlowname() {
/*  83 */     return this.flowname;
/*     */   }
/*     */ 
/*     */   public void setFlowname(String flowname) {
/*  87 */     this.flowname = flowname;
/*     */   }
/*     */ 
/*     */   public String getFlowcode() {
/*  91 */     return this.flowcode;
/*     */   }
/*     */ 
/*     */   public void setFlowcode(String flowcode) {
/*  95 */     this.flowcode = flowcode;
/*     */   }
/*     */ 
/*     */   public String getAuthor() {
/*  99 */     return this.author;
/*     */   }
/*     */ 
/*     */   public void setAuthor(String author) {
/* 103 */     this.author = author;
/*     */   }
/*     */ 
/*     */   public Date getCreatedate() {
/* 107 */     return this.createdate;
/*     */   }
/*     */ 
/*     */   public void setCreatedate(Date createdate) {
/* 111 */     this.createdate = createdate;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 115 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 119 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getFlowfile() {
/* 123 */     return this.flowfile;
/*     */   }
/*     */ 
/*     */   public void setFlowfile(String flowfile) {
/* 127 */     this.flowfile = flowfile;
/*     */   }
/*     */ 
/*     */   public String getFlowdesc() {
/* 131 */     return this.flowdesc;
/*     */   }
/*     */ 
/*     */   public void setFlowdesc(String flowdesc) {
/* 135 */     this.flowdesc = flowdesc;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.FlowFiles
 * JD-Core Version:    0.6.0
 */