/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ServFlowRelation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8028075772319623395L;
/*     */   private ServFlowRelationId id;
/*     */   private String opcodepattern;
/*     */   private String smspattern;
/*     */   private String description;
/*     */   private String mms;
/*     */   private String sms;
/*     */   private String wwwondemand;
/*     */   private String userorder;
/*     */   private String usercancelorder;
/*     */   private String wwwuserorderuse;
/*     */   private String web;
/*     */   private String wap;
/*     */   private String customer;
/*     */ 
/*     */   public String getCustomer()
/*     */   {
/*  37 */     return this.customer;
/*     */   }
/*     */ 
/*     */   public void setCustomer(String customer) {
/*  41 */     this.customer = customer;
/*     */   }
/*     */ 
/*     */   public String getWap() {
/*  45 */     return this.wap;
/*     */   }
/*     */ 
/*     */   public void setWap(String wap) {
/*  49 */     this.wap = wap;
/*     */   }
/*     */ 
/*     */   public String getWeb() {
/*  53 */     return this.web;
/*     */   }
/*     */ 
/*     */   public void setWeb(String web) {
/*  57 */     this.web = web;
/*     */   }
/*     */ 
/*     */   public ServFlowRelation()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ServFlowRelation(ServFlowRelationId id)
/*     */   {
/*  66 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public ServFlowRelation(ServFlowRelationId id, String opcodepattern, String smspattern, String description, String mms, String sms, String wwwondemand, String userorder, String usercancelorder, String wwwuserorderuse)
/*     */   {
/*  71 */     this.id = id;
/*  72 */     this.opcodepattern = opcodepattern;
/*  73 */     this.smspattern = smspattern;
/*  74 */     this.description = description;
/*  75 */     this.mms = mms;
/*  76 */     this.sms = sms;
/*  77 */     this.wwwondemand = wwwondemand;
/*  78 */     this.userorder = userorder;
/*  79 */     this.usercancelorder = usercancelorder;
/*  80 */     this.wwwuserorderuse = wwwuserorderuse;
/*     */   }
/*     */ 
/*     */   public ServFlowRelationId getId()
/*     */   {
/*  87 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(ServFlowRelationId id) {
/*  91 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getOpcodepattern() {
/*  95 */     return this.opcodepattern;
/*     */   }
/*     */ 
/*     */   public void setOpcodepattern(String opcodepattern) {
/*  99 */     this.opcodepattern = opcodepattern;
/*     */   }
/*     */ 
/*     */   public String getSmspattern() {
/* 103 */     return this.smspattern;
/*     */   }
/*     */ 
/*     */   public void setSmspattern(String smspattern) {
/* 107 */     this.smspattern = smspattern;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/* 111 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description) {
/* 115 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public String getMms() {
/* 119 */     return this.mms;
/*     */   }
/*     */ 
/*     */   public void setMms(String mms) {
/* 123 */     this.mms = mms;
/*     */   }
/*     */ 
/*     */   public String getSms() {
/* 127 */     return this.sms;
/*     */   }
/*     */ 
/*     */   public void setSms(String sms) {
/* 131 */     this.sms = sms;
/*     */   }
/*     */ 
/*     */   public String getWwwondemand() {
/* 135 */     return this.wwwondemand;
/*     */   }
/*     */ 
/*     */   public void setWwwondemand(String wwwondemand) {
/* 139 */     this.wwwondemand = wwwondemand;
/*     */   }
/*     */ 
/*     */   public String getUserorder() {
/* 143 */     return this.userorder;
/*     */   }
/*     */ 
/*     */   public void setUserorder(String userorder) {
/* 147 */     this.userorder = userorder;
/*     */   }
/*     */ 
/*     */   public String getUsercancelorder() {
/* 151 */     return this.usercancelorder;
/*     */   }
/*     */ 
/*     */   public void setUsercancelorder(String usercancelorder) {
/* 155 */     this.usercancelorder = usercancelorder;
/*     */   }
/*     */ 
/*     */   public String getWwwuserorderuse() {
/* 159 */     return this.wwwuserorderuse;
/*     */   }
/*     */ 
/*     */   public void setWwwuserorderuse(String wwwuserorderuse) {
/* 163 */     this.wwwuserorderuse = wwwuserorderuse;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServFlowRelation
 * JD-Core Version:    0.6.0
 */