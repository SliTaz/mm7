/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CpInfoTemp
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5422283347542236829L;
/*     */   private Long cpid;
/*     */   private String cpname;
/*     */   private String cpaddress;
/*     */   private String artificialperson;
/*     */   private String bankname;
/*     */   private String bankaccount;
/*     */   private String website;
/*     */   private String fax;
/*     */   private String businesslinkman;
/*     */   private String businesslinkmantel;
/*     */   private String businesslinkmanmobile;
/*     */   private String businesslinkmanemail;
/*     */   private String clientlinkman;
/*     */   private String clientlinkmantel;
/*     */   private String clientlinkmanmobile;
/*     */   private String clientlinkmanemail;
/*     */   private String regemail;
/*     */   private String loginno;
/*     */   private String status;
/*     */   private String deleteapply;
/*     */   private String cpcode;
/*     */ 
/*     */   public CpInfoTemp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CpInfoTemp(String cpname, String regemail, String status, String deleteapply, String cpcode)
/*     */   {
/*  49 */     this.cpname = cpname;
/*  50 */     this.regemail = regemail;
/*  51 */     this.status = status;
/*  52 */     this.deleteapply = deleteapply;
/*  53 */     this.cpcode = cpcode;
/*     */   }
/*     */ 
/*     */   public CpInfoTemp(String cpname, String cpaddress, String artificialperson, String bankname, String bankaccount, String website, String fax, String businesslinkman, String businesslinkmantel, String businesslinkmanmobile, String businesslinkmanemail, String clientlinkman, String clientlinkmantel, String clientlinkmanmobile, String clientlinkmanemail, String regemail, String loginno, String status, String deleteapply, String cpcode)
/*     */   {
/*  58 */     this.cpname = cpname;
/*  59 */     this.cpaddress = cpaddress;
/*  60 */     this.artificialperson = artificialperson;
/*  61 */     this.bankname = bankname;
/*  62 */     this.bankaccount = bankaccount;
/*  63 */     this.website = website;
/*  64 */     this.fax = fax;
/*  65 */     this.businesslinkman = businesslinkman;
/*  66 */     this.businesslinkmantel = businesslinkmantel;
/*  67 */     this.businesslinkmanmobile = businesslinkmanmobile;
/*  68 */     this.businesslinkmanemail = businesslinkmanemail;
/*  69 */     this.clientlinkman = clientlinkman;
/*  70 */     this.clientlinkmantel = clientlinkmantel;
/*  71 */     this.clientlinkmanmobile = clientlinkmanmobile;
/*  72 */     this.clientlinkmanemail = clientlinkmanemail;
/*  73 */     this.regemail = regemail;
/*  74 */     this.loginno = loginno;
/*  75 */     this.status = status;
/*  76 */     this.deleteapply = deleteapply;
/*  77 */     this.cpcode = cpcode;
/*     */   }
/*     */ 
/*     */   public Long getCpid()
/*     */   {
/*  84 */     return this.cpid;
/*     */   }
/*     */ 
/*     */   public void setCpid(Long cpid) {
/*  88 */     this.cpid = cpid;
/*     */   }
/*     */ 
/*     */   public String getCpname() {
/*  92 */     return this.cpname;
/*     */   }
/*     */ 
/*     */   public void setCpname(String cpname) {
/*  96 */     this.cpname = cpname;
/*     */   }
/*     */ 
/*     */   public String getCpaddress() {
/* 100 */     return this.cpaddress;
/*     */   }
/*     */ 
/*     */   public void setCpaddress(String cpaddress) {
/* 104 */     this.cpaddress = cpaddress;
/*     */   }
/*     */ 
/*     */   public String getArtificialperson() {
/* 108 */     return this.artificialperson;
/*     */   }
/*     */ 
/*     */   public void setArtificialperson(String artificialperson) {
/* 112 */     this.artificialperson = artificialperson;
/*     */   }
/*     */ 
/*     */   public String getBankname() {
/* 116 */     return this.bankname;
/*     */   }
/*     */ 
/*     */   public void setBankname(String bankname) {
/* 120 */     this.bankname = bankname;
/*     */   }
/*     */ 
/*     */   public String getBankaccount() {
/* 124 */     return this.bankaccount;
/*     */   }
/*     */ 
/*     */   public void setBankaccount(String bankaccount) {
/* 128 */     this.bankaccount = bankaccount;
/*     */   }
/*     */ 
/*     */   public String getWebsite() {
/* 132 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(String website) {
/* 136 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public String getFax() {
/* 140 */     return this.fax;
/*     */   }
/*     */ 
/*     */   public void setFax(String fax) {
/* 144 */     this.fax = fax;
/*     */   }
/*     */ 
/*     */   public String getBusinesslinkman() {
/* 148 */     return this.businesslinkman;
/*     */   }
/*     */ 
/*     */   public void setBusinesslinkman(String businesslinkman) {
/* 152 */     this.businesslinkman = businesslinkman;
/*     */   }
/*     */ 
/*     */   public String getBusinesslinkmantel() {
/* 156 */     return this.businesslinkmantel;
/*     */   }
/*     */ 
/*     */   public void setBusinesslinkmantel(String businesslinkmantel) {
/* 160 */     this.businesslinkmantel = businesslinkmantel;
/*     */   }
/*     */ 
/*     */   public String getBusinesslinkmanmobile() {
/* 164 */     return this.businesslinkmanmobile;
/*     */   }
/*     */ 
/*     */   public void setBusinesslinkmanmobile(String businesslinkmanmobile) {
/* 168 */     this.businesslinkmanmobile = businesslinkmanmobile;
/*     */   }
/*     */ 
/*     */   public String getBusinesslinkmanemail() {
/* 172 */     return this.businesslinkmanemail;
/*     */   }
/*     */ 
/*     */   public void setBusinesslinkmanemail(String businesslinkmanemail) {
/* 176 */     this.businesslinkmanemail = businesslinkmanemail;
/*     */   }
/*     */ 
/*     */   public String getClientlinkman() {
/* 180 */     return this.clientlinkman;
/*     */   }
/*     */ 
/*     */   public void setClientlinkman(String clientlinkman) {
/* 184 */     this.clientlinkman = clientlinkman;
/*     */   }
/*     */ 
/*     */   public String getClientlinkmantel() {
/* 188 */     return this.clientlinkmantel;
/*     */   }
/*     */ 
/*     */   public void setClientlinkmantel(String clientlinkmantel) {
/* 192 */     this.clientlinkmantel = clientlinkmantel;
/*     */   }
/*     */ 
/*     */   public String getClientlinkmanmobile() {
/* 196 */     return this.clientlinkmanmobile;
/*     */   }
/*     */ 
/*     */   public void setClientlinkmanmobile(String clientlinkmanmobile) {
/* 200 */     this.clientlinkmanmobile = clientlinkmanmobile;
/*     */   }
/*     */ 
/*     */   public String getClientlinkmanemail() {
/* 204 */     return this.clientlinkmanemail;
/*     */   }
/*     */ 
/*     */   public void setClientlinkmanemail(String clientlinkmanemail) {
/* 208 */     this.clientlinkmanemail = clientlinkmanemail;
/*     */   }
/*     */ 
/*     */   public String getRegemail() {
/* 212 */     return this.regemail;
/*     */   }
/*     */ 
/*     */   public void setRegemail(String regemail) {
/* 216 */     this.regemail = regemail;
/*     */   }
/*     */ 
/*     */   public String getLoginno() {
/* 220 */     return this.loginno;
/*     */   }
/*     */ 
/*     */   public void setLoginno(String loginno) {
/* 224 */     this.loginno = loginno;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 228 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 232 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getDeleteapply() {
/* 236 */     return this.deleteapply;
/*     */   }
/*     */ 
/*     */   public void setDeleteapply(String deleteapply) {
/* 240 */     this.deleteapply = deleteapply;
/*     */   }
/*     */ 
/*     */   public String getCpcode() {
/* 244 */     return this.cpcode;
/*     */   }
/*     */ 
/*     */   public void setCpcode(String cpcode) {
/* 248 */     this.cpcode = cpcode;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.CpInfoTemp
 * JD-Core Version:    0.6.0
 */