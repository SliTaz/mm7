/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CpInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1636226613199980279L;
/*     */   private String cpid;
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
/*     */   private String loginno;
/*     */   private String status;
/*     */   private String deleteapply;
/*     */   private String regemail;
/*     */   private String cpcode;
/*     */   private String rejectdesc;
/*     */   private String techlinkman;
/*     */   private String techlinkmantel;
/*     */   private String techlinkmanmobile;
/*     */   private String techlinkmanemail;
/*     */   private String financelinkman;
/*     */   private String financelinkmantel;
/*     */   private String financelinkmanmobile;
/*     */   private String financelinkmanemail;
/*     */ 
/*     */   public CpInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CpInfo(String cpname, String status, String deleteapply, String regemail)
/*     */   {
/*  58 */     this.cpname = cpname;
/*  59 */     this.status = status;
/*  60 */     this.deleteapply = deleteapply;
/*  61 */     this.regemail = regemail;
/*     */   }
/*     */ 
/*     */   public CpInfo(String cpname, String cpaddress, String artificialperson, String bankname, String bankaccount, String website, String fax, String businesslinkman, String businesslinkmantel, String businesslinkmanmobile, String businesslinkmanemail, String clientlinkman, String clientlinkmantel, String clientlinkmanmobile, String clientlinkmanemail, String loginno, String status, String deleteapply, String regemail, String cpcode, String rejectdesc, String techlinkman, String techlinkmantel, String techlinkmanmobile, String techlinkmanemail, String financelinkman, String financelinkmantel, String financelinkmanmobile, String financelinkmanemail)
/*     */   {
/*  66 */     this.cpname = cpname;
/*  67 */     this.cpaddress = cpaddress;
/*  68 */     this.artificialperson = artificialperson;
/*  69 */     this.bankname = bankname;
/*  70 */     this.bankaccount = bankaccount;
/*  71 */     this.website = website;
/*  72 */     this.fax = fax;
/*  73 */     this.businesslinkman = businesslinkman;
/*  74 */     this.businesslinkmantel = businesslinkmantel;
/*  75 */     this.businesslinkmanmobile = businesslinkmanmobile;
/*  76 */     this.businesslinkmanemail = businesslinkmanemail;
/*  77 */     this.clientlinkman = clientlinkman;
/*  78 */     this.clientlinkmantel = clientlinkmantel;
/*  79 */     this.clientlinkmanmobile = clientlinkmanmobile;
/*  80 */     this.clientlinkmanemail = clientlinkmanemail;
/*  81 */     this.loginno = loginno;
/*  82 */     this.status = status;
/*  83 */     this.deleteapply = deleteapply;
/*  84 */     this.regemail = regemail;
/*  85 */     this.cpcode = cpcode;
/*  86 */     this.rejectdesc = rejectdesc;
/*  87 */     this.techlinkman = techlinkman;
/*  88 */     this.techlinkmantel = techlinkmantel;
/*  89 */     this.techlinkmanmobile = techlinkmanmobile;
/*  90 */     this.techlinkmanemail = techlinkmanemail;
/*  91 */     this.financelinkman = financelinkman;
/*  92 */     this.financelinkmantel = financelinkmantel;
/*  93 */     this.financelinkmanmobile = financelinkmanmobile;
/*  94 */     this.financelinkmanemail = financelinkmanemail;
/*     */   }
/*     */ 
/*     */   public String getCpid()
/*     */   {
/* 101 */     return this.cpid;
/*     */   }
/*     */ 
/*     */   public void setCpid(String cpid) {
/* 105 */     this.cpid = cpid;
/*     */   }
/*     */ 
/*     */   public String getCpname() {
/* 109 */     return this.cpname;
/*     */   }
/*     */ 
/*     */   public void setCpname(String cpname) {
/* 113 */     this.cpname = cpname;
/*     */   }
/*     */ 
/*     */   public String getCpaddress() {
/* 117 */     return this.cpaddress;
/*     */   }
/*     */ 
/*     */   public void setCpaddress(String cpaddress) {
/* 121 */     this.cpaddress = cpaddress;
/*     */   }
/*     */ 
/*     */   public String getArtificialperson() {
/* 125 */     return this.artificialperson;
/*     */   }
/*     */ 
/*     */   public void setArtificialperson(String artificialperson) {
/* 129 */     this.artificialperson = artificialperson;
/*     */   }
/*     */ 
/*     */   public String getBankname() {
/* 133 */     return this.bankname;
/*     */   }
/*     */ 
/*     */   public void setBankname(String bankname) {
/* 137 */     this.bankname = bankname;
/*     */   }
/*     */ 
/*     */   public String getBankaccount() {
/* 141 */     return this.bankaccount;
/*     */   }
/*     */ 
/*     */   public void setBankaccount(String bankaccount) {
/* 145 */     this.bankaccount = bankaccount;
/*     */   }
/*     */ 
/*     */   public String getWebsite() {
/* 149 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(String website) {
/* 153 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public String getFax() {
/* 157 */     return this.fax;
/*     */   }
/*     */ 
/*     */   public void setFax(String fax) {
/* 161 */     this.fax = fax;
/*     */   }
/*     */ 
/*     */   public String getBusinesslinkman() {
/* 165 */     return this.businesslinkman;
/*     */   }
/*     */ 
/*     */   public void setBusinesslinkman(String businesslinkman) {
/* 169 */     this.businesslinkman = businesslinkman;
/*     */   }
/*     */ 
/*     */   public String getBusinesslinkmantel() {
/* 173 */     return this.businesslinkmantel;
/*     */   }
/*     */ 
/*     */   public void setBusinesslinkmantel(String businesslinkmantel) {
/* 177 */     this.businesslinkmantel = businesslinkmantel;
/*     */   }
/*     */ 
/*     */   public String getBusinesslinkmanmobile() {
/* 181 */     return this.businesslinkmanmobile;
/*     */   }
/*     */ 
/*     */   public void setBusinesslinkmanmobile(String businesslinkmanmobile) {
/* 185 */     this.businesslinkmanmobile = businesslinkmanmobile;
/*     */   }
/*     */ 
/*     */   public String getBusinesslinkmanemail() {
/* 189 */     return this.businesslinkmanemail;
/*     */   }
/*     */ 
/*     */   public void setBusinesslinkmanemail(String businesslinkmanemail) {
/* 193 */     this.businesslinkmanemail = businesslinkmanemail;
/*     */   }
/*     */ 
/*     */   public String getClientlinkman() {
/* 197 */     return this.clientlinkman;
/*     */   }
/*     */ 
/*     */   public void setClientlinkman(String clientlinkman) {
/* 201 */     this.clientlinkman = clientlinkman;
/*     */   }
/*     */ 
/*     */   public String getClientlinkmantel() {
/* 205 */     return this.clientlinkmantel;
/*     */   }
/*     */ 
/*     */   public void setClientlinkmantel(String clientlinkmantel) {
/* 209 */     this.clientlinkmantel = clientlinkmantel;
/*     */   }
/*     */ 
/*     */   public String getClientlinkmanmobile() {
/* 213 */     return this.clientlinkmanmobile;
/*     */   }
/*     */ 
/*     */   public void setClientlinkmanmobile(String clientlinkmanmobile) {
/* 217 */     this.clientlinkmanmobile = clientlinkmanmobile;
/*     */   }
/*     */ 
/*     */   public String getClientlinkmanemail() {
/* 221 */     return this.clientlinkmanemail;
/*     */   }
/*     */ 
/*     */   public void setClientlinkmanemail(String clientlinkmanemail) {
/* 225 */     this.clientlinkmanemail = clientlinkmanemail;
/*     */   }
/*     */ 
/*     */   public String getLoginno() {
/* 229 */     return this.loginno;
/*     */   }
/*     */ 
/*     */   public void setLoginno(String loginno) {
/* 233 */     this.loginno = loginno;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 237 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 241 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getDeleteapply() {
/* 245 */     return this.deleteapply;
/*     */   }
/*     */ 
/*     */   public void setDeleteapply(String deleteapply) {
/* 249 */     this.deleteapply = deleteapply;
/*     */   }
/*     */ 
/*     */   public String getRegemail() {
/* 253 */     return this.regemail;
/*     */   }
/*     */ 
/*     */   public void setRegemail(String regemail) {
/* 257 */     this.regemail = regemail;
/*     */   }
/*     */ 
/*     */   public String getCpcode() {
/* 261 */     return this.cpcode;
/*     */   }
/*     */ 
/*     */   public void setCpcode(String cpcode) {
/* 265 */     this.cpcode = cpcode;
/*     */   }
/*     */ 
/*     */   public String getRejectdesc() {
/* 269 */     return this.rejectdesc;
/*     */   }
/*     */ 
/*     */   public void setRejectdesc(String rejectdesc) {
/* 273 */     this.rejectdesc = rejectdesc;
/*     */   }
/*     */ 
/*     */   public String getTechlinkman() {
/* 277 */     return this.techlinkman;
/*     */   }
/*     */ 
/*     */   public void setTechlinkman(String techlinkman) {
/* 281 */     this.techlinkman = techlinkman;
/*     */   }
/*     */ 
/*     */   public String getTechlinkmantel() {
/* 285 */     return this.techlinkmantel;
/*     */   }
/*     */ 
/*     */   public void setTechlinkmantel(String techlinkmantel) {
/* 289 */     this.techlinkmantel = techlinkmantel;
/*     */   }
/*     */ 
/*     */   public String getTechlinkmanmobile() {
/* 293 */     return this.techlinkmanmobile;
/*     */   }
/*     */ 
/*     */   public void setTechlinkmanmobile(String techlinkmanmobile) {
/* 297 */     this.techlinkmanmobile = techlinkmanmobile;
/*     */   }
/*     */ 
/*     */   public String getTechlinkmanemail() {
/* 301 */     return this.techlinkmanemail;
/*     */   }
/*     */ 
/*     */   public void setTechlinkmanemail(String techlinkmanemail) {
/* 305 */     this.techlinkmanemail = techlinkmanemail;
/*     */   }
/*     */ 
/*     */   public String getFinancelinkman() {
/* 309 */     return this.financelinkman;
/*     */   }
/*     */ 
/*     */   public void setFinancelinkman(String financelinkman) {
/* 313 */     this.financelinkman = financelinkman;
/*     */   }
/*     */ 
/*     */   public String getFinancelinkmantel() {
/* 317 */     return this.financelinkmantel;
/*     */   }
/*     */ 
/*     */   public void setFinancelinkmantel(String financelinkmantel) {
/* 321 */     this.financelinkmantel = financelinkmantel;
/*     */   }
/*     */ 
/*     */   public String getFinancelinkmanmobile() {
/* 325 */     return this.financelinkmanmobile;
/*     */   }
/*     */ 
/*     */   public void setFinancelinkmanmobile(String financelinkmanmobile) {
/* 329 */     this.financelinkmanmobile = financelinkmanmobile;
/*     */   }
/*     */ 
/*     */   public String getFinancelinkmanemail() {
/* 333 */     return this.financelinkmanemail;
/*     */   }
/*     */ 
/*     */   public void setFinancelinkmanemail(String financelinkmanemail) {
/* 337 */     this.financelinkmanemail = financelinkmanemail;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.CpInfo
 * JD-Core Version:    0.6.0
 */