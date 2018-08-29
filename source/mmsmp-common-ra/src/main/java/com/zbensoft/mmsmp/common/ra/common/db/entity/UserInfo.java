/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class UserInfo
/*     */   implements Serializable
/*     */ {
/*     */   private Integer userid;
/*     */   private MobileTypeInfo mobileTypeInfo;
/*     */   private String cellphonenumber;
/*     */   private String terminaltype;
/*     */   private String provincecode;
/*     */   private String citycode;
/*     */   private Date createdate;
/*     */   private String status;
/*     */   private String userpwd;
/*     */   private String name;
/*     */   private String nickname;
/*     */   private String gender;
/*     */   private Date birthday;
/*     */   private String profession;
/*     */   private String email;
/*     */   private String address;
/*     */   private String liking;
/*     */   private String postalcode;
/*     */   private String country;
/*     */   private String folk;
/*     */   private String marriage;
/*     */   private String certificatetype;
/*     */   private String certificateno;
/*     */   private String brand;
/*     */   private Integer UaTypeId;
/*     */   private String picSpec;
/*     */   private String province;
/*     */   private String flag;
/*     */   private String deleteFlag;
/*     */ 
/*     */   public String getDeleteFlag()
/*     */   {
/*  48 */     return this.deleteFlag;
/*     */   }
/*     */ 
/*     */   public void setDeleteFlag(String deleteFlag) {
/*  52 */     this.deleteFlag = deleteFlag;
/*     */   }
/*     */ 
/*     */   public String getProvince() {
/*  56 */     return this.province;
/*     */   }
/*     */ 
/*     */   public void setProvince(String province) {
/*  60 */     this.province = province;
/*     */   }
/*     */ 
/*     */   public String getFlag() {
/*  64 */     return this.flag;
/*     */   }
/*     */ 
/*     */   public void setFlag(String flag) {
/*  68 */     this.flag = flag;
/*     */   }
/*     */ 
/*     */   public String getPicSpec() {
/*  72 */     return this.picSpec;
/*     */   }
/*     */ 
/*     */   public void setPicSpec(String picSpec) {
/*  76 */     this.picSpec = picSpec;
/*     */   }
/*     */ 
/*     */   public String getBrand() {
/*  80 */     return this.brand;
/*     */   }
/*     */ 
/*     */   public void setBrand(String brand) {
/*  84 */     this.brand = brand;
/*     */   }
/*     */ 
/*     */   public UserInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UserInfo(Integer userid, String cellphonenumber, String terminaltype, String status)
/*     */   {
/*  94 */     this.userid = userid;
/*  95 */     this.cellphonenumber = cellphonenumber;
/*  96 */     this.terminaltype = terminaltype;
/*  97 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public UserInfo(Integer userid, MobileTypeInfo mobileTypeInfo, String cellphonenumber, String terminaltype, String provincecode, String citycode, Date createdate, String status, String userpwd, String name, String nickname, String gender, Date birthday, String profession, String email, String address, String liking, String postalcode, String country, String folk, String marriage, String certificatetype, String certificateno)
/*     */   {
/* 108 */     this.userid = userid;
/* 109 */     this.mobileTypeInfo = mobileTypeInfo;
/* 110 */     this.cellphonenumber = cellphonenumber;
/* 111 */     this.terminaltype = terminaltype;
/* 112 */     this.provincecode = provincecode;
/* 113 */     this.citycode = citycode;
/* 114 */     this.createdate = createdate;
/* 115 */     this.status = status;
/* 116 */     this.userpwd = userpwd;
/* 117 */     this.name = name;
/* 118 */     this.nickname = nickname;
/* 119 */     this.gender = gender;
/* 120 */     this.birthday = birthday;
/* 121 */     this.profession = profession;
/* 122 */     this.email = email;
/* 123 */     this.address = address;
/* 124 */     this.liking = liking;
/* 125 */     this.postalcode = postalcode;
/* 126 */     this.country = country;
/* 127 */     this.folk = folk;
/* 128 */     this.marriage = marriage;
/* 129 */     this.certificatetype = certificatetype;
/* 130 */     this.certificateno = certificateno;
/*     */   }
/*     */ 
/*     */   public Integer getUserid()
/*     */   {
/* 136 */     return this.userid;
/*     */   }
/*     */ 
/*     */   public void setUserid(Integer userid) {
/* 140 */     this.userid = userid;
/*     */   }
/*     */ 
/*     */   public MobileTypeInfo getMobileTypeInfo() {
/* 144 */     return this.mobileTypeInfo;
/*     */   }
/*     */ 
/*     */   public void setMobileTypeInfo(MobileTypeInfo mobileTypeInfo) {
/* 148 */     this.mobileTypeInfo = mobileTypeInfo;
/*     */   }
/*     */ 
/*     */   public String getCellphonenumber() {
/* 152 */     return this.cellphonenumber;
/*     */   }
/*     */ 
/*     */   public void setCellphonenumber(String cellphonenumber) {
/* 156 */     this.cellphonenumber = cellphonenumber;
/*     */   }
/*     */ 
/*     */   public String getTerminaltype() {
/* 160 */     return this.terminaltype;
/*     */   }
/*     */ 
/*     */   public void setTerminaltype(String terminaltype) {
/* 164 */     this.terminaltype = terminaltype;
/*     */   }
/*     */ 
/*     */   public String getProvincecode() {
/* 168 */     return this.provincecode;
/*     */   }
/*     */ 
/*     */   public void setProvincecode(String provincecode) {
/* 172 */     this.provincecode = provincecode;
/*     */   }
/*     */ 
/*     */   public String getCitycode() {
/* 176 */     return this.citycode;
/*     */   }
/*     */ 
/*     */   public void setCitycode(String citycode) {
/* 180 */     this.citycode = citycode;
/*     */   }
/*     */ 
/*     */   public Date getCreatedate() {
/* 184 */     return this.createdate;
/*     */   }
/*     */ 
/*     */   public void setCreatedate(Date createdate) {
/* 188 */     this.createdate = createdate;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 192 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 196 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getUserpwd() {
/* 200 */     return this.userpwd;
/*     */   }
/*     */ 
/*     */   public void setUserpwd(String userpwd) {
/* 204 */     this.userpwd = userpwd;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 208 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 212 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getNickname() {
/* 216 */     return this.nickname;
/*     */   }
/*     */ 
/*     */   public void setNickname(String nickname) {
/* 220 */     this.nickname = nickname;
/*     */   }
/*     */ 
/*     */   public String getGender() {
/* 224 */     return this.gender;
/*     */   }
/*     */ 
/*     */   public void setGender(String gender) {
/* 228 */     this.gender = gender;
/*     */   }
/*     */ 
/*     */   public Date getBirthday() {
/* 232 */     return this.birthday;
/*     */   }
/*     */ 
/*     */   public void setBirthday(Date birthday) {
/* 236 */     this.birthday = birthday;
/*     */   }
/*     */ 
/*     */   public String getProfession() {
/* 240 */     return this.profession;
/*     */   }
/*     */ 
/*     */   public void setProfession(String profession) {
/* 244 */     this.profession = profession;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/* 248 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/* 252 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getAddress() {
/* 256 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String address) {
/* 260 */     this.address = address;
/*     */   }
/*     */ 
/*     */   public String getLiking() {
/* 264 */     return this.liking;
/*     */   }
/*     */ 
/*     */   public void setLiking(String liking) {
/* 268 */     this.liking = liking;
/*     */   }
/*     */ 
/*     */   public String getPostalcode() {
/* 272 */     return this.postalcode;
/*     */   }
/*     */ 
/*     */   public void setPostalcode(String postalcode) {
/* 276 */     this.postalcode = postalcode;
/*     */   }
/*     */ 
/*     */   public String getCountry() {
/* 280 */     return this.country;
/*     */   }
/*     */ 
/*     */   public void setCountry(String country) {
/* 284 */     this.country = country;
/*     */   }
/*     */ 
/*     */   public String getFolk() {
/* 288 */     return this.folk;
/*     */   }
/*     */ 
/*     */   public void setFolk(String folk) {
/* 292 */     this.folk = folk;
/*     */   }
/*     */ 
/*     */   public String getMarriage() {
/* 296 */     return this.marriage;
/*     */   }
/*     */ 
/*     */   public void setMarriage(String marriage) {
/* 300 */     this.marriage = marriage;
/*     */   }
/*     */ 
/*     */   public String getCertificatetype() {
/* 304 */     return this.certificatetype;
/*     */   }
/*     */ 
/*     */   public void setCertificatetype(String certificatetype) {
/* 308 */     this.certificatetype = certificatetype;
/*     */   }
/*     */ 
/*     */   public String getCertificateno() {
/* 312 */     return this.certificateno;
/*     */   }
/*     */ 
/*     */   public void setCertificateno(String certificateno) {
/* 316 */     this.certificateno = certificateno;
/*     */   }
/*     */ 
/*     */   public Integer getUaTypeId() {
/* 320 */     return this.UaTypeId;
/*     */   }
/*     */ 
/*     */   public void setUaTypeId(Integer uaTypeId) {
/* 324 */     this.UaTypeId = uaTypeId;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserInfo
 * JD-Core Version:    0.6.0
 */