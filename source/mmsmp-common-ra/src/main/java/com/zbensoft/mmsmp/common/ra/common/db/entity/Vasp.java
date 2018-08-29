/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Vasp
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8694290780202910680L;
/*     */   private Integer uniqueid;
/*     */   private String vaspid;
/*     */   private String vaspname;
/*     */   private String businessphone;
/*     */   private String contactperson;
/*     */   private String fax;
/*     */   private String emailaddress;
/*     */   private String webaddress;
/*     */   private String officeaddress;
/*     */   private String createdate;
/*     */   private String province;
/*     */   private String status;
/*     */   private String updatedate;
/*     */   private String vaspdesc;
/*     */   private VaspReserveInfo vaspReserveInfo;
/*     */ 
/*     */   public VaspReserveInfo getVaspReserveInfo()
/*     */   {
/*  39 */     return this.vaspReserveInfo;
/*     */   }
/*     */ 
/*     */   public void setVaspReserveInfo(VaspReserveInfo vaspReserveInfo) {
/*  43 */     this.vaspReserveInfo = vaspReserveInfo;
/*     */   }
/*     */ 
/*     */   public Vasp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Vasp(String vaspid, String vaspname, String status)
/*     */   {
/*  52 */     this.vaspid = vaspid;
/*  53 */     this.vaspname = vaspname;
/*  54 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Vasp(String vaspid, String vaspname, String businessphone, String contactperson, String fax, String emailaddress, String webaddress, String officeaddress, String createdate, String province, String status, String updatedate, String vaspdesc)
/*     */   {
/*  59 */     this.vaspid = vaspid;
/*  60 */     this.vaspname = vaspname;
/*  61 */     this.businessphone = businessphone;
/*  62 */     this.contactperson = contactperson;
/*  63 */     this.fax = fax;
/*  64 */     this.emailaddress = emailaddress;
/*  65 */     this.webaddress = webaddress;
/*  66 */     this.officeaddress = officeaddress;
/*  67 */     this.createdate = createdate;
/*  68 */     this.province = province;
/*  69 */     this.status = status;
/*  70 */     this.updatedate = updatedate;
/*  71 */     this.vaspdesc = vaspdesc;
/*     */   }
/*     */ 
/*     */   public Integer getUniqueid()
/*     */   {
/*  78 */     return this.uniqueid;
/*     */   }
/*     */ 
/*     */   public void setUniqueid(Integer uniqueid) {
/*  82 */     this.uniqueid = uniqueid;
/*     */   }
/*     */ 
/*     */   public String getVaspid() {
/*  86 */     return this.vaspid;
/*     */   }
/*     */ 
/*     */   public void setVaspid(String vaspid) {
/*  90 */     this.vaspid = vaspid;
/*     */   }
/*     */ 
/*     */   public String getVaspname() {
/*  94 */     return this.vaspname;
/*     */   }
/*     */ 
/*     */   public void setVaspname(String vaspname) {
/*  98 */     this.vaspname = vaspname;
/*     */   }
/*     */ 
/*     */   public String getBusinessphone() {
/* 102 */     return this.businessphone;
/*     */   }
/*     */ 
/*     */   public void setBusinessphone(String businessphone) {
/* 106 */     this.businessphone = businessphone;
/*     */   }
/*     */ 
/*     */   public String getContactperson() {
/* 110 */     return this.contactperson;
/*     */   }
/*     */ 
/*     */   public void setContactperson(String contactperson) {
/* 114 */     this.contactperson = contactperson;
/*     */   }
/*     */ 
/*     */   public String getFax() {
/* 118 */     return this.fax;
/*     */   }
/*     */ 
/*     */   public void setFax(String fax) {
/* 122 */     this.fax = fax;
/*     */   }
/*     */ 
/*     */   public String getEmailaddress() {
/* 126 */     return this.emailaddress;
/*     */   }
/*     */ 
/*     */   public void setEmailaddress(String emailaddress) {
/* 130 */     this.emailaddress = emailaddress;
/*     */   }
/*     */ 
/*     */   public String getWebaddress() {
/* 134 */     return this.webaddress;
/*     */   }
/*     */ 
/*     */   public void setWebaddress(String webaddress) {
/* 138 */     this.webaddress = webaddress;
/*     */   }
/*     */ 
/*     */   public String getOfficeaddress() {
/* 142 */     return this.officeaddress;
/*     */   }
/*     */ 
/*     */   public void setOfficeaddress(String officeaddress) {
/* 146 */     this.officeaddress = officeaddress;
/*     */   }
/*     */ 
/*     */   public String getCreatedate() {
/* 150 */     return this.createdate;
/*     */   }
/*     */ 
/*     */   public void setCreatedate(String createdate) {
/* 154 */     this.createdate = createdate;
/*     */   }
/*     */ 
/*     */   public String getProvince() {
/* 158 */     return this.province;
/*     */   }
/*     */ 
/*     */   public void setProvince(String province) {
/* 162 */     this.province = province;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 166 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 170 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getUpdatedate() {
/* 174 */     return this.updatedate;
/*     */   }
/*     */ 
/*     */   public void setUpdatedate(String updatedate) {
/* 178 */     this.updatedate = updatedate;
/*     */   }
/*     */ 
/*     */   public String getVaspdesc() {
/* 182 */     return this.vaspdesc;
/*     */   }
/*     */ 
/*     */   public void setVaspdesc(String vaspdesc) {
/* 186 */     this.vaspdesc = vaspdesc;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.Vasp
 * JD-Core Version:    0.6.0
 */