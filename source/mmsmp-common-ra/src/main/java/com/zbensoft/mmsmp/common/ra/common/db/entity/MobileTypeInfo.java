/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MobileTypeInfo
/*     */   implements Serializable
/*     */ {
/*     */   private Integer mobiletypeid;
/*     */   private String vendername;
/*     */   private String mobiletypename;
/*     */   private String pictype;
/*     */   private String audiotype;
/*     */   private Integer polyphonicnum;
/*     */   private String videotype;
/*     */   private String charset;
/*     */   private Integer screencolor;
/*     */   private String smilspt;
/*     */   private String wapspt;
/*     */   private String mmsspt;
/*     */   private String wappushspt;
/*     */   private Integer mmsmaxval;
/*     */   private String uaname;
/*     */   private String screensize;
/*     */   private UAType uaType;
/*     */   private String UA_URL;
/*     */   private Integer uaTypeId;
/*     */ 
/*     */   public String getUA_URL()
/*     */   {
/*  54 */     return this.UA_URL;
/*     */   }
/*     */ 
/*     */   public void setUA_URL(String ua_url) {
/*  58 */     this.UA_URL = ua_url;
/*     */   }
/*     */ 
/*     */   public MobileTypeInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MobileTypeInfo(Integer mobiletypeid)
/*     */   {
/*  81 */     this.mobiletypeid = mobiletypeid;
/*     */   }
/*     */ 
/*     */   public MobileTypeInfo(Integer mobiletypeid, String vendername, String mobiletypename, String pictype, String audiotype, Integer polyphonicnum, String videotype, String charset, Integer screencolor, String smilspt, String wapspt, String mmsspt, String wappushspt, Integer mmsmaxval, String uaname, String screensize)
/*     */   {
/*  91 */     this.mobiletypeid = mobiletypeid;
/*  92 */     this.vendername = vendername;
/*  93 */     this.mobiletypename = mobiletypename;
/*  94 */     this.pictype = pictype;
/*  95 */     this.audiotype = audiotype;
/*  96 */     this.polyphonicnum = polyphonicnum;
/*  97 */     this.videotype = videotype;
/*  98 */     this.charset = charset;
/*  99 */     this.screencolor = screencolor;
/* 100 */     this.smilspt = smilspt;
/* 101 */     this.wapspt = wapspt;
/* 102 */     this.mmsspt = mmsspt;
/* 103 */     this.wappushspt = wappushspt;
/* 104 */     this.mmsmaxval = mmsmaxval;
/* 105 */     this.uaname = uaname;
/* 106 */     this.screensize = screensize;
/*     */   }
/*     */ 
/*     */   public Integer getMobiletypeid()
/*     */   {
/* 112 */     return this.mobiletypeid;
/*     */   }
/*     */ 
/*     */   public void setMobiletypeid(Integer mobiletypeid) {
/* 116 */     this.mobiletypeid = mobiletypeid;
/*     */   }
/*     */ 
/*     */   public String getVendername() {
/* 120 */     return this.vendername;
/*     */   }
/*     */ 
/*     */   public void setVendername(String vendername) {
/* 124 */     this.vendername = vendername;
/*     */   }
/*     */ 
/*     */   public String getMobiletypename() {
/* 128 */     return this.mobiletypename;
/*     */   }
/*     */ 
/*     */   public void setMobiletypename(String mobiletypename) {
/* 132 */     this.mobiletypename = mobiletypename;
/*     */   }
/*     */ 
/*     */   public String getPictype() {
/* 136 */     return this.pictype;
/*     */   }
/*     */ 
/*     */   public void setPictype(String pictype) {
/* 140 */     this.pictype = pictype;
/*     */   }
/*     */ 
/*     */   public String getAudiotype() {
/* 144 */     return this.audiotype;
/*     */   }
/*     */ 
/*     */   public void setAudiotype(String audiotype) {
/* 148 */     this.audiotype = audiotype;
/*     */   }
/*     */ 
/*     */   public Integer getPolyphonicnum() {
/* 152 */     return this.polyphonicnum;
/*     */   }
/*     */ 
/*     */   public void setPolyphonicnum(Integer polyphonicnum) {
/* 156 */     this.polyphonicnum = polyphonicnum;
/*     */   }
/*     */ 
/*     */   public String getVideotype() {
/* 160 */     return this.videotype;
/*     */   }
/*     */ 
/*     */   public void setVideotype(String videotype) {
/* 164 */     this.videotype = videotype;
/*     */   }
/*     */ 
/*     */   public String getCharset() {
/* 168 */     return this.charset;
/*     */   }
/*     */ 
/*     */   public void setCharset(String charset) {
/* 172 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */   public Integer getScreencolor() {
/* 176 */     return this.screencolor;
/*     */   }
/*     */ 
/*     */   public void setScreencolor(Integer screencolor) {
/* 180 */     this.screencolor = screencolor;
/*     */   }
/*     */ 
/*     */   public String getSmilspt() {
/* 184 */     return this.smilspt;
/*     */   }
/*     */ 
/*     */   public void setSmilspt(String smilspt) {
/* 188 */     this.smilspt = smilspt;
/*     */   }
/*     */ 
/*     */   public String getWapspt() {
/* 192 */     return this.wapspt;
/*     */   }
/*     */ 
/*     */   public void setWapspt(String wapspt) {
/* 196 */     this.wapspt = wapspt;
/*     */   }
/*     */ 
/*     */   public String getMmsspt() {
/* 200 */     return this.mmsspt;
/*     */   }
/*     */ 
/*     */   public void setMmsspt(String mmsspt) {
/* 204 */     this.mmsspt = mmsspt;
/*     */   }
/*     */ 
/*     */   public String getWappushspt() {
/* 208 */     return this.wappushspt;
/*     */   }
/*     */ 
/*     */   public void setWappushspt(String wappushspt) {
/* 212 */     this.wappushspt = wappushspt;
/*     */   }
/*     */ 
/*     */   public Integer getMmsmaxval() {
/* 216 */     return this.mmsmaxval;
/*     */   }
/*     */ 
/*     */   public void setMmsmaxval(Integer mmsmaxval) {
/* 220 */     this.mmsmaxval = mmsmaxval;
/*     */   }
/*     */ 
/*     */   public String getUaname() {
/* 224 */     return this.uaname;
/*     */   }
/*     */ 
/*     */   public void setUaname(String uaname) {
/* 228 */     this.uaname = uaname;
/*     */   }
/*     */ 
/*     */   public String getScreensize() {
/* 232 */     return this.screensize;
/*     */   }
/*     */ 
/*     */   public void setScreensize(String screensize) {
/* 236 */     this.screensize = screensize;
/*     */   }
/*     */ 
/*     */   public UAType getUaType()
/*     */   {
/* 243 */     return this.uaType;
/*     */   }
/*     */ 
/*     */   public void setUaType(UAType uaType)
/*     */   {
/* 250 */     this.uaType = uaType;
/*     */   }
/*     */ 
/*     */   public Integer getUaTypeId() {
/* 254 */     return this.uaTypeId;
/*     */   }
/*     */ 
/*     */   public void setUaTypeId(Integer uaTypeId) {
/* 258 */     this.uaTypeId = uaTypeId;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.MobileTypeInfo
 * JD-Core Version:    0.6.0
 */