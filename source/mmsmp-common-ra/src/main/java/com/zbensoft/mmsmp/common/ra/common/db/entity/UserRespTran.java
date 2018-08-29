/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class UserRespTran
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5247360808338620913L;
/*     */   private UserRespTranId id;
/*     */   private String waitmessagetype;
/*     */   private Integer flowid;
/*     */   private String currentflownode;
/*     */   private String executenodeslist;
/*     */   private String contextobjfile;
/*     */   private Date expiredtime;
/*     */   private String smspattern;
/*     */   private String opcodepattern;
/*     */ 
/*     */   public UserRespTran()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UserRespTran(UserRespTranId id)
/*     */   {
/*  38 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public UserRespTran(UserRespTranId id, String waitmessagetype, Integer flowid, String currentflownode, String executenodeslist, String contextobjfile, Date expiredtime, String smspattern, String opcodepattern)
/*     */   {
/*  43 */     this.id = id;
/*  44 */     this.waitmessagetype = waitmessagetype;
/*  45 */     this.flowid = flowid;
/*  46 */     this.currentflownode = currentflownode;
/*  47 */     this.executenodeslist = executenodeslist;
/*  48 */     this.contextobjfile = contextobjfile;
/*  49 */     this.expiredtime = expiredtime;
/*  50 */     this.smspattern = smspattern;
/*  51 */     this.opcodepattern = opcodepattern;
/*     */   }
/*     */ 
/*     */   public UserRespTranId getId()
/*     */   {
/*  58 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(UserRespTranId id) {
/*  62 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getWaitmessagetype() {
/*  66 */     return this.waitmessagetype;
/*     */   }
/*     */ 
/*     */   public void setWaitmessagetype(String waitmessagetype) {
/*  70 */     this.waitmessagetype = waitmessagetype;
/*     */   }
/*     */ 
/*     */   public Integer getFlowid() {
/*  74 */     return this.flowid;
/*     */   }
/*     */ 
/*     */   public void setFlowid(Integer flowid) {
/*  78 */     this.flowid = flowid;
/*     */   }
/*     */ 
/*     */   public String getCurrentflownode() {
/*  82 */     return this.currentflownode;
/*     */   }
/*     */ 
/*     */   public void setCurrentflownode(String currentflownode) {
/*  86 */     this.currentflownode = currentflownode;
/*     */   }
/*     */ 
/*     */   public String getExecutenodeslist() {
/*  90 */     return this.executenodeslist;
/*     */   }
/*     */ 
/*     */   public void setExecutenodeslist(String executenodeslist) {
/*  94 */     this.executenodeslist = executenodeslist;
/*     */   }
/*     */ 
/*     */   public String getContextobjfile() {
/*  98 */     return this.contextobjfile;
/*     */   }
/*     */ 
/*     */   public void setContextobjfile(String contextobjfile) {
/* 102 */     this.contextobjfile = contextobjfile;
/*     */   }
/*     */ 
/*     */   public Date getExpiredtime() {
/* 106 */     return this.expiredtime;
/*     */   }
/*     */ 
/*     */   public void setExpiredtime(Date expiredtime) {
/* 110 */     this.expiredtime = expiredtime;
/*     */   }
/*     */ 
/*     */   public String getSmspattern() {
/* 114 */     return this.smspattern;
/*     */   }
/*     */ 
/*     */   public void setSmspattern(String smspattern) {
/* 118 */     this.smspattern = smspattern;
/*     */   }
/*     */ 
/*     */   public String getOpcodepattern() {
/* 122 */     return this.opcodepattern;
/*     */   }
/*     */ 
/*     */   public void setOpcodepattern(String opcodepattern) {
/* 126 */     this.opcodepattern = opcodepattern;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserRespTran
 * JD-Core Version:    0.6.0
 */