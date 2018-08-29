/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Sysapp
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4262470963480660787L;
/*     */   private String strappname;
/*     */   private Integer iid;
/*     */   private String strapptype;
/*     */   private String strvendorname;
/*     */   private String strappversion;
/*     */   private String strappserial;
/*     */   private String strappagentipaddress;
/*     */   private String strapprunstartedtime;
/*     */   private Integer shcurrentrunstatus;
/*     */   private String caretasks;
/*     */   private Integer strappagentport;
/*     */   private String communitystring;
/*     */ 
/*     */   public Sysapp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Sysapp(String strappname, Integer iid, String strapptype)
/*     */   {
/*  40 */     this.strappname = strappname;
/*  41 */     this.iid = iid;
/*  42 */     this.strapptype = strapptype;
/*     */   }
/*     */ 
/*     */   public Sysapp(String strappname, Integer iid, String strapptype, String strvendorname, String strappversion, String strappserial, String strappagentipaddress, String strapprunstartedtime, Integer shcurrentrunstatus, String caretasks, Integer strappagentport, String communitystring)
/*     */   {
/*  47 */     this.strappname = strappname;
/*  48 */     this.iid = iid;
/*  49 */     this.strapptype = strapptype;
/*  50 */     this.strvendorname = strvendorname;
/*  51 */     this.strappversion = strappversion;
/*  52 */     this.strappserial = strappserial;
/*  53 */     this.strappagentipaddress = strappagentipaddress;
/*  54 */     this.strapprunstartedtime = strapprunstartedtime;
/*  55 */     this.shcurrentrunstatus = shcurrentrunstatus;
/*  56 */     this.caretasks = caretasks;
/*  57 */     this.strappagentport = strappagentport;
/*  58 */     this.communitystring = communitystring;
/*     */   }
/*     */ 
/*     */   public String getStrappname()
/*     */   {
/*  65 */     return this.strappname;
/*     */   }
/*     */ 
/*     */   public void setStrappname(String strappname) {
/*  69 */     this.strappname = strappname;
/*     */   }
/*     */ 
/*     */   public Integer getIid() {
/*  73 */     return this.iid;
/*     */   }
/*     */ 
/*     */   public void setIid(Integer iid) {
/*  77 */     this.iid = iid;
/*     */   }
/*     */ 
/*     */   public String getStrapptype() {
/*  81 */     return this.strapptype;
/*     */   }
/*     */ 
/*     */   public void setStrapptype(String strapptype) {
/*  85 */     this.strapptype = strapptype;
/*     */   }
/*     */ 
/*     */   public String getStrvendorname() {
/*  89 */     return this.strvendorname;
/*     */   }
/*     */ 
/*     */   public void setStrvendorname(String strvendorname) {
/*  93 */     this.strvendorname = strvendorname;
/*     */   }
/*     */ 
/*     */   public String getStrappversion() {
/*  97 */     return this.strappversion;
/*     */   }
/*     */ 
/*     */   public void setStrappversion(String strappversion) {
/* 101 */     this.strappversion = strappversion;
/*     */   }
/*     */ 
/*     */   public String getStrappserial() {
/* 105 */     return this.strappserial;
/*     */   }
/*     */ 
/*     */   public void setStrappserial(String strappserial) {
/* 109 */     this.strappserial = strappserial;
/*     */   }
/*     */ 
/*     */   public String getStrappagentipaddress() {
/* 113 */     return this.strappagentipaddress;
/*     */   }
/*     */ 
/*     */   public void setStrappagentipaddress(String strappagentipaddress) {
/* 117 */     this.strappagentipaddress = strappagentipaddress;
/*     */   }
/*     */ 
/*     */   public String getStrapprunstartedtime() {
/* 121 */     return this.strapprunstartedtime;
/*     */   }
/*     */ 
/*     */   public void setStrapprunstartedtime(String strapprunstartedtime) {
/* 125 */     this.strapprunstartedtime = strapprunstartedtime;
/*     */   }
/*     */ 
/*     */   public Integer getShcurrentrunstatus() {
/* 129 */     return this.shcurrentrunstatus;
/*     */   }
/*     */ 
/*     */   public void setShcurrentrunstatus(Integer shcurrentrunstatus) {
/* 133 */     this.shcurrentrunstatus = shcurrentrunstatus;
/*     */   }
/*     */ 
/*     */   public String getCaretasks() {
/* 137 */     return this.caretasks;
/*     */   }
/*     */ 
/*     */   public void setCaretasks(String caretasks) {
/* 141 */     this.caretasks = caretasks;
/*     */   }
/*     */ 
/*     */   public Integer getStrappagentport() {
/* 145 */     return this.strappagentport;
/*     */   }
/*     */ 
/*     */   public void setStrappagentport(Integer strappagentport) {
/* 149 */     this.strappagentport = strappagentport;
/*     */   }
/*     */ 
/*     */   public String getCommunitystring() {
/* 153 */     return this.communitystring;
/*     */   }
/*     */ 
/*     */   public void setCommunitystring(String communitystring) {
/* 157 */     this.communitystring = communitystring;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.Sysapp
 * JD-Core Version:    0.6.0
 */