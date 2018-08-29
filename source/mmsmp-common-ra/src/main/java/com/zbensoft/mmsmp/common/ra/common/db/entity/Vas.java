/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Vas
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1388520428575064811L;
/*     */   private Integer uniqueid;
/*     */   private String vaspid;
/*     */   private String vasid;
/*     */   private String vassmsid;
/*     */   private String vasname;
/*     */   private String vasuri;
/*     */   private String vasipaddress;
/*     */   private String aousername;
/*     */   private String aopassword;
/*     */   private String aoauthenflag;
/*     */   private String atusername;
/*     */   private String atpassword;
/*     */   private String atauthenflag;
/*     */   private String vasallowedflag;
/*     */   private Integer maxconnlimit;
/*     */   private Integer maxtrafficlimit;
/*     */   private String createdate;
/*     */   private Integer controlpriority;
/*     */   private String provincecode;
/*     */   private String citycode;
/*     */   private String protocol;
/*     */   private String capacityId;
/*     */   private String status;
/*     */   private String vasIsmpId;
/*     */   private String serviceId;
/*     */   private Integer ifServiceIndependent;
/*     */   private VasReserveInfo vasReserveInfo;
/*     */ 
/*     */   public VasReserveInfo getVasReserveInfo()
/*     */   {
/*  51 */     return this.vasReserveInfo;
/*     */   }
/*     */ 
/*     */   public void setVasReserveInfo(VasReserveInfo vasReserveInfo) {
/*  55 */     this.vasReserveInfo = vasReserveInfo;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/*  59 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/*  63 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Vas()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Vas(String vaspid, String vasid, String vasname, String aoauthenflag, String atauthenflag, String vasallowedflag)
/*     */   {
/*  72 */     this.vaspid = vaspid;
/*  73 */     this.vasid = vasid;
/*  74 */     this.vasname = vasname;
/*  75 */     this.aoauthenflag = aoauthenflag;
/*  76 */     this.atauthenflag = atauthenflag;
/*  77 */     this.vasallowedflag = vasallowedflag;
/*     */   }
/*     */ 
/*     */   public Vas(String vaspid, String vasid, String vasname, String vasuri, String vasipaddress, String aousername, String aopassword, String aoauthenflag, String atusername, String atpassword, String atauthenflag, String vasallowedflag, Integer maxconnlimit, Integer maxtrafficlimit, String createdate, Integer controlpriority, String provincecode, String citycode, String protocol)
/*     */   {
/*  82 */     this.vaspid = vaspid;
/*  83 */     this.vasid = vasid;
/*  84 */     this.vasname = vasname;
/*  85 */     this.vasuri = vasuri;
/*  86 */     this.vasipaddress = vasipaddress;
/*  87 */     this.aousername = aousername;
/*  88 */     this.aopassword = aopassword;
/*  89 */     this.aoauthenflag = aoauthenflag;
/*  90 */     this.atusername = atusername;
/*  91 */     this.atpassword = atpassword;
/*  92 */     this.atauthenflag = atauthenflag;
/*  93 */     this.vasallowedflag = vasallowedflag;
/*  94 */     this.maxconnlimit = maxconnlimit;
/*  95 */     this.maxtrafficlimit = maxtrafficlimit;
/*  96 */     this.createdate = createdate;
/*  97 */     this.controlpriority = controlpriority;
/*  98 */     this.provincecode = provincecode;
/*  99 */     this.citycode = citycode;
/* 100 */     this.protocol = protocol;
/*     */   }
/*     */ 
/*     */   public Integer getUniqueid()
/*     */   {
/* 107 */     return this.uniqueid;
/*     */   }
/*     */ 
/*     */   public void setUniqueid(Integer uniqueid) {
/* 111 */     this.uniqueid = uniqueid;
/*     */   }
/*     */ 
/*     */   public String getVaspid() {
/* 115 */     return this.vaspid;
/*     */   }
/*     */ 
/*     */   public void setVaspid(String vaspid) {
/* 119 */     this.vaspid = vaspid;
/*     */   }
/*     */ 
/*     */   public String getVasid() {
/* 123 */     return this.vasid;
/*     */   }
/*     */ 
/*     */   public void setVasid(String vasid) {
/* 127 */     this.vasid = vasid;
/*     */   }
/*     */ 
/*     */   public String getVasname() {
/* 131 */     return this.vasname;
/*     */   }
/*     */ 
/*     */   public void setVasname(String vasname) {
/* 135 */     this.vasname = vasname;
/*     */   }
/*     */ 
/*     */   public String getVasuri() {
/* 139 */     return this.vasuri;
/*     */   }
/*     */ 
/*     */   public void setVasuri(String vasuri) {
/* 143 */     this.vasuri = vasuri;
/*     */   }
/*     */ 
/*     */   public String getVasipaddress() {
/* 147 */     return this.vasipaddress;
/*     */   }
/*     */ 
/*     */   public void setVasipaddress(String vasipaddress) {
/* 151 */     this.vasipaddress = vasipaddress;
/*     */   }
/*     */ 
/*     */   public String getAousername() {
/* 155 */     return this.aousername;
/*     */   }
/*     */ 
/*     */   public void setAousername(String aousername) {
/* 159 */     this.aousername = aousername;
/*     */   }
/*     */ 
/*     */   public String getAopassword() {
/* 163 */     return this.aopassword;
/*     */   }
/*     */ 
/*     */   public void setAopassword(String aopassword) {
/* 167 */     this.aopassword = aopassword;
/*     */   }
/*     */ 
/*     */   public String getAoauthenflag() {
/* 171 */     return this.aoauthenflag;
/*     */   }
/*     */ 
/*     */   public void setAoauthenflag(String aoauthenflag) {
/* 175 */     this.aoauthenflag = aoauthenflag;
/*     */   }
/*     */ 
/*     */   public String getAtusername() {
/* 179 */     return this.atusername;
/*     */   }
/*     */ 
/*     */   public void setAtusername(String atusername) {
/* 183 */     this.atusername = atusername;
/*     */   }
/*     */ 
/*     */   public String getAtpassword() {
/* 187 */     return this.atpassword;
/*     */   }
/*     */ 
/*     */   public void setAtpassword(String atpassword) {
/* 191 */     this.atpassword = atpassword;
/*     */   }
/*     */ 
/*     */   public String getAtauthenflag() {
/* 195 */     return this.atauthenflag;
/*     */   }
/*     */ 
/*     */   public void setAtauthenflag(String atauthenflag) {
/* 199 */     this.atauthenflag = atauthenflag;
/*     */   }
/*     */ 
/*     */   public String getVasallowedflag() {
/* 203 */     return this.vasallowedflag;
/*     */   }
/*     */ 
/*     */   public void setVasallowedflag(String vasallowedflag) {
/* 207 */     this.vasallowedflag = vasallowedflag;
/*     */   }
/*     */ 
/*     */   public Integer getMaxconnlimit() {
/* 211 */     return this.maxconnlimit;
/*     */   }
/*     */ 
/*     */   public void setMaxconnlimit(Integer maxconnlimit) {
/* 215 */     this.maxconnlimit = maxconnlimit;
/*     */   }
/*     */ 
/*     */   public Integer getMaxtrafficlimit() {
/* 219 */     return this.maxtrafficlimit;
/*     */   }
/*     */ 
/*     */   public void setMaxtrafficlimit(Integer maxtrafficlimit) {
/* 223 */     this.maxtrafficlimit = maxtrafficlimit;
/*     */   }
/*     */ 
/*     */   public String getCreatedate() {
/* 227 */     return this.createdate;
/*     */   }
/*     */ 
/*     */   public void setCreatedate(String createdate) {
/* 231 */     this.createdate = createdate;
/*     */   }
/*     */ 
/*     */   public Integer getControlpriority() {
/* 235 */     return this.controlpriority;
/*     */   }
/*     */ 
/*     */   public void setControlpriority(Integer controlpriority) {
/* 239 */     this.controlpriority = controlpriority;
/*     */   }
/*     */ 
/*     */   public String getProvincecode() {
/* 243 */     return this.provincecode;
/*     */   }
/*     */ 
/*     */   public void setProvincecode(String provincecode) {
/* 247 */     this.provincecode = provincecode;
/*     */   }
/*     */ 
/*     */   public String getCitycode() {
/* 251 */     return this.citycode;
/*     */   }
/*     */ 
/*     */   public void setCitycode(String citycode) {
/* 255 */     this.citycode = citycode;
/*     */   }
/*     */ 
/*     */   public String getProtocol() {
/* 259 */     return this.protocol;
/*     */   }
/*     */ 
/*     */   public void setProtocol(String protocol) {
/* 263 */     this.protocol = protocol;
/*     */   }
/*     */ 
/*     */   public String getVassmsid() {
/* 267 */     return this.vassmsid;
/*     */   }
/*     */ 
/*     */   public void setVassmsid(String vassmsid) {
/* 271 */     this.vassmsid = vassmsid;
/*     */   }
/*     */ 
/*     */   public String getCapacityId() {
/* 275 */     return this.capacityId;
/*     */   }
/*     */ 
/*     */   public void setCapacityId(String capacityId) {
/* 279 */     this.capacityId = capacityId;
/*     */   }
/*     */ 
/*     */   public String getVasIsmpId() {
/* 283 */     return this.vasIsmpId;
/*     */   }
/*     */ 
/*     */   public void setVasIsmpId(String vasIsmpId) {
/* 287 */     this.vasIsmpId = vasIsmpId;
/*     */   }
/*     */ 
/*     */   public String getServiceId() {
/* 291 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public void setServiceId(String serviceId) {
/* 295 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public Integer getIfServiceIndependent() {
/* 299 */     return this.ifServiceIndependent;
/*     */   }
/*     */ 
/*     */   public void setIfServiceIndependent(Integer ifServiceIndependent) {
/* 303 */     this.ifServiceIndependent = ifServiceIndependent;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.Vas
 * JD-Core Version:    0.6.0
 */