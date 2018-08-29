/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VasReserveInfo
/*     */   implements Serializable
/*     */ {
/*     */   private String recordsequenceid;
/*     */   private String spname;
/*     */   private String servicetype;
/*     */   private Integer groupservice;
/*     */   private String servicecompose;
/*     */   private Integer servicecredit;
/*     */   private String introurl;
/*     */   private String wapintropic;
/*     */   private String servicecolumn;
/*     */   private String enterurl;
/*     */   private String confirmurl;
/*     */   private String priceurl;
/*     */   private String freeurl;
/*     */   private Integer needconfmback;
/*     */   private Integer checktype;
/*     */   private String effdate;
/*     */   private String expdate;
/*     */   private Integer wapservicetype;
/*     */   private String sporderurl;
/*     */   private Integer synorderfunc;
/*     */   private Integer sppsedoflag;
/*     */   private String serviceid;
/*     */ 
/*     */   public VasReserveInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VasReserveInfo(String recordsequenceid)
/*     */   {
/*  44 */     this.recordsequenceid = recordsequenceid;
/*     */   }
/*     */ 
/*     */   public VasReserveInfo(String recordsequenceid, String spname, String servicetype, Integer groupservice, String servicecompose, Integer servicecredit, String introurl, String wapintropic, String servicecolumn, String enterurl, String confirmurl, String priceurl, String freeurl, Integer needconfmback, Integer checktype, String effdate, String expdate, Integer wapservicetype, String sporderurl, Integer synorderfunc, Integer sppsedoflag, String serviceid)
/*     */   {
/*  52 */     this.recordsequenceid = recordsequenceid;
/*  53 */     this.spname = spname;
/*  54 */     this.servicetype = servicetype;
/*  55 */     this.groupservice = groupservice;
/*  56 */     this.servicecompose = servicecompose;
/*  57 */     this.servicecredit = servicecredit;
/*  58 */     this.introurl = introurl;
/*  59 */     this.wapintropic = wapintropic;
/*  60 */     this.servicecolumn = servicecolumn;
/*  61 */     this.enterurl = enterurl;
/*  62 */     this.confirmurl = confirmurl;
/*  63 */     this.priceurl = priceurl;
/*  64 */     this.freeurl = freeurl;
/*  65 */     this.needconfmback = needconfmback;
/*  66 */     this.checktype = checktype;
/*  67 */     this.effdate = effdate;
/*  68 */     this.expdate = expdate;
/*  69 */     this.wapservicetype = wapservicetype;
/*  70 */     this.sporderurl = sporderurl;
/*  71 */     this.synorderfunc = synorderfunc;
/*  72 */     this.sppsedoflag = sppsedoflag;
/*  73 */     this.serviceid = serviceid;
/*     */   }
/*     */ 
/*     */   public String getRecordsequenceid()
/*     */   {
/*  79 */     return this.recordsequenceid;
/*     */   }
/*     */ 
/*     */   public void setRecordsequenceid(String recordsequenceid) {
/*  83 */     this.recordsequenceid = recordsequenceid;
/*     */   }
/*     */ 
/*     */   public String getSpname() {
/*  87 */     return this.spname;
/*     */   }
/*     */ 
/*     */   public void setSpname(String spname) {
/*  91 */     this.spname = spname;
/*     */   }
/*     */ 
/*     */   public String getServicetype() {
/*  95 */     return this.servicetype;
/*     */   }
/*     */ 
/*     */   public void setServicetype(String servicetype) {
/*  99 */     this.servicetype = servicetype;
/*     */   }
/*     */ 
/*     */   public Integer getGroupservice() {
/* 103 */     return this.groupservice;
/*     */   }
/*     */ 
/*     */   public void setGroupservice(Integer groupservice) {
/* 107 */     this.groupservice = groupservice;
/*     */   }
/*     */ 
/*     */   public String getServicecompose() {
/* 111 */     return this.servicecompose;
/*     */   }
/*     */ 
/*     */   public void setServicecompose(String servicecompose) {
/* 115 */     this.servicecompose = servicecompose;
/*     */   }
/*     */ 
/*     */   public Integer getServicecredit() {
/* 119 */     return this.servicecredit;
/*     */   }
/*     */ 
/*     */   public void setServicecredit(Integer servicecredit) {
/* 123 */     this.servicecredit = servicecredit;
/*     */   }
/*     */ 
/*     */   public String getIntrourl() {
/* 127 */     return this.introurl;
/*     */   }
/*     */ 
/*     */   public void setIntrourl(String introurl) {
/* 131 */     this.introurl = introurl;
/*     */   }
/*     */ 
/*     */   public String getWapintropic() {
/* 135 */     return this.wapintropic;
/*     */   }
/*     */ 
/*     */   public void setWapintropic(String wapintropic) {
/* 139 */     this.wapintropic = wapintropic;
/*     */   }
/*     */ 
/*     */   public String getServicecolumn() {
/* 143 */     return this.servicecolumn;
/*     */   }
/*     */ 
/*     */   public void setServicecolumn(String servicecolumn) {
/* 147 */     this.servicecolumn = servicecolumn;
/*     */   }
/*     */ 
/*     */   public String getEnterurl() {
/* 151 */     return this.enterurl;
/*     */   }
/*     */ 
/*     */   public void setEnterurl(String enterurl) {
/* 155 */     this.enterurl = enterurl;
/*     */   }
/*     */ 
/*     */   public String getConfirmurl() {
/* 159 */     return this.confirmurl;
/*     */   }
/*     */ 
/*     */   public void setConfirmurl(String confirmurl) {
/* 163 */     this.confirmurl = confirmurl;
/*     */   }
/*     */ 
/*     */   public String getPriceurl() {
/* 167 */     return this.priceurl;
/*     */   }
/*     */ 
/*     */   public void setPriceurl(String priceurl) {
/* 171 */     this.priceurl = priceurl;
/*     */   }
/*     */ 
/*     */   public String getFreeurl() {
/* 175 */     return this.freeurl;
/*     */   }
/*     */ 
/*     */   public void setFreeurl(String freeurl) {
/* 179 */     this.freeurl = freeurl;
/*     */   }
/*     */ 
/*     */   public Integer getNeedconfmback() {
/* 183 */     return this.needconfmback;
/*     */   }
/*     */ 
/*     */   public void setNeedconfmback(Integer needconfmback) {
/* 187 */     this.needconfmback = needconfmback;
/*     */   }
/*     */ 
/*     */   public Integer getChecktype() {
/* 191 */     return this.checktype;
/*     */   }
/*     */ 
/*     */   public void setChecktype(Integer checktype) {
/* 195 */     this.checktype = checktype;
/*     */   }
/*     */ 
/*     */   public String getEffdate() {
/* 199 */     return this.effdate;
/*     */   }
/*     */ 
/*     */   public void setEffdate(String effdate) {
/* 203 */     this.effdate = effdate;
/*     */   }
/*     */ 
/*     */   public String getExpdate() {
/* 207 */     return this.expdate;
/*     */   }
/*     */ 
/*     */   public void setExpdate(String expdate) {
/* 211 */     this.expdate = expdate;
/*     */   }
/*     */ 
/*     */   public Integer getWapservicetype() {
/* 215 */     return this.wapservicetype;
/*     */   }
/*     */ 
/*     */   public void setWapservicetype(Integer wapservicetype) {
/* 219 */     this.wapservicetype = wapservicetype;
/*     */   }
/*     */ 
/*     */   public String getSporderurl() {
/* 223 */     return this.sporderurl;
/*     */   }
/*     */ 
/*     */   public void setSporderurl(String sporderurl) {
/* 227 */     this.sporderurl = sporderurl;
/*     */   }
/*     */ 
/*     */   public Integer getSynorderfunc() {
/* 231 */     return this.synorderfunc;
/*     */   }
/*     */ 
/*     */   public void setSynorderfunc(Integer synorderfunc) {
/* 235 */     this.synorderfunc = synorderfunc;
/*     */   }
/*     */ 
/*     */   public Integer getSppsedoflag() {
/* 239 */     return this.sppsedoflag;
/*     */   }
/*     */ 
/*     */   public void setSppsedoflag(Integer sppsedoflag) {
/* 243 */     this.sppsedoflag = sppsedoflag;
/*     */   }
/*     */ 
/*     */   public String getServiceid() {
/* 247 */     return this.serviceid;
/*     */   }
/*     */ 
/*     */   public void setServiceid(String serviceid) {
/* 251 */     this.serviceid = serviceid;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other) {
/* 255 */     if (this == other)
/* 256 */       return true;
/* 257 */     if (other == null)
/* 258 */       return false;
/* 259 */     if (!(other instanceof VasReserveInfo))
/* 260 */       return false;
/* 261 */     VasReserveInfo castOther = (VasReserveInfo)other;
/*     */ 
/* 306 */     return ((getRecordsequenceid() == castOther.getRecordsequenceid()) || ((getRecordsequenceid() != null) && 
/* 264 */       (castOther.getRecordsequenceid() != null) && (getRecordsequenceid().equals(castOther.getRecordsequenceid())))) && 
/* 265 */       ((getSpname() == castOther.getSpname()) || ((getSpname() != null) && (castOther.getSpname() != null) && 
/* 266 */       (getSpname()
/* 266 */       .equals(castOther.getSpname())))) && 
/* 267 */       ((getServicetype() == castOther.getServicetype()) || ((getServicetype() != null) && (castOther.getServicetype() != null) && 
/* 268 */       (getServicetype().equals(castOther.getServicetype())))) && 
/* 269 */       ((getGroupservice() == castOther.getGroupservice()) || ((getGroupservice() != null) && (castOther.getGroupservice() != null) && 
/* 270 */       (getGroupservice().equals(castOther.getGroupservice())))) && 
/* 271 */       ((getServicecompose() == castOther.getServicecompose()) || ((getServicecompose() != null) && (castOther.getServicecompose() != null) && 
/* 272 */       (getServicecompose().equals(castOther.getServicecompose())))) && 
/* 273 */       ((getServicecredit() == castOther.getServicecredit()) || ((getServicecredit() != null) && (castOther.getServicecredit() != null) && 
/* 274 */       (getServicecredit().equals(castOther.getServicecredit())))) && 
/* 275 */       ((getIntrourl() == castOther.getIntrourl()) || ((getIntrourl() != null) && (castOther.getIntrourl() != null) && 
/* 276 */       (getIntrourl().equals(castOther.getIntrourl())))) && 
/* 277 */       ((getWapintropic() == castOther.getWapintropic()) || ((getWapintropic() != null) && (castOther.getWapintropic() != null) && 
/* 278 */       (getWapintropic().equals(castOther.getWapintropic())))) && 
/* 279 */       ((getServicecolumn() == castOther.getServicecolumn()) || ((getServicecolumn() != null) && (castOther.getServicecolumn() != null) && 
/* 280 */       (getServicecolumn().equals(castOther.getServicecolumn())))) && 
/* 281 */       ((getEnterurl() == castOther.getEnterurl()) || ((getEnterurl() != null) && (castOther.getEnterurl() != null) && 
/* 282 */       (getEnterurl().equals(castOther.getEnterurl())))) && 
/* 283 */       ((getConfirmurl() == castOther.getConfirmurl()) || ((getConfirmurl() != null) && (castOther.getConfirmurl() != null) && 
/* 284 */       (getConfirmurl().equals(castOther.getConfirmurl())))) && 
/* 285 */       ((getPriceurl() == castOther.getPriceurl()) || ((getPriceurl() != null) && (castOther.getPriceurl() != null) && 
/* 286 */       (getPriceurl().equals(castOther.getPriceurl())))) && 
/* 287 */       ((getFreeurl() == castOther.getFreeurl()) || ((getFreeurl() != null) && (castOther.getFreeurl() != null) && 
/* 288 */       (getFreeurl()
/* 288 */       .equals(castOther.getFreeurl())))) && 
/* 289 */       ((getNeedconfmback() == castOther.getNeedconfmback()) || ((getNeedconfmback() != null) && (castOther.getNeedconfmback() != null) && 
/* 290 */       (getNeedconfmback().equals(castOther.getNeedconfmback())))) && 
/* 291 */       ((getChecktype() == castOther.getChecktype()) || ((getChecktype() != null) && (castOther.getChecktype() != null) && 
/* 292 */       (getChecktype().equals(castOther.getChecktype())))) && 
/* 293 */       ((getEffdate() == castOther.getEffdate()) || ((getEffdate() != null) && (castOther.getEffdate() != null) && 
/* 294 */       (getEffdate()
/* 294 */       .equals(castOther.getEffdate())))) && 
/* 295 */       ((getExpdate() == castOther.getExpdate()) || ((getExpdate() != null) && (castOther.getExpdate() != null) && 
/* 296 */       (getExpdate()
/* 296 */       .equals(castOther.getExpdate())))) && 
/* 297 */       ((getWapservicetype() == castOther.getWapservicetype()) || ((getWapservicetype() != null) && (castOther.getWapservicetype() != null) && 
/* 298 */       (getWapservicetype().equals(castOther.getWapservicetype())))) && 
/* 299 */       ((getSporderurl() == castOther.getSporderurl()) || ((getSporderurl() != null) && (castOther.getSporderurl() != null) && 
/* 300 */       (getSporderurl().equals(castOther.getSporderurl())))) && 
/* 301 */       ((getSynorderfunc() == castOther.getSynorderfunc()) || ((getSynorderfunc() != null) && (castOther.getSynorderfunc() != null) && 
/* 302 */       (getSynorderfunc().equals(castOther.getSynorderfunc())))) && 
/* 303 */       ((getSppsedoflag() == castOther.getSppsedoflag()) || ((getSppsedoflag() != null) && (castOther.getSppsedoflag() != null) && 
/* 304 */       (getSppsedoflag().equals(castOther.getSppsedoflag())))) && (
/* 305 */       (getServiceid() == castOther.getServiceid()) || ((getServiceid() != null) && (castOther.getServiceid() != null) && 
/* 306 */       (getServiceid().equals(castOther.getServiceid()))));
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 310 */     int result = 17;
/*     */ 
/* 312 */     result = 37 * result + (getRecordsequenceid() == null ? 0 : getRecordsequenceid().hashCode());
/* 313 */     result = 37 * result + (getSpname() == null ? 0 : getSpname().hashCode());
/* 314 */     result = 37 * result + (getServicetype() == null ? 0 : getServicetype().hashCode());
/* 315 */     result = 37 * result + (getGroupservice() == null ? 0 : getGroupservice().hashCode());
/* 316 */     result = 37 * result + (getServicecompose() == null ? 0 : getServicecompose().hashCode());
/* 317 */     result = 37 * result + (getServicecredit() == null ? 0 : getServicecredit().hashCode());
/* 318 */     result = 37 * result + (getIntrourl() == null ? 0 : getIntrourl().hashCode());
/* 319 */     result = 37 * result + (getWapintropic() == null ? 0 : getWapintropic().hashCode());
/* 320 */     result = 37 * result + (getServicecolumn() == null ? 0 : getServicecolumn().hashCode());
/* 321 */     result = 37 * result + (getEnterurl() == null ? 0 : getEnterurl().hashCode());
/* 322 */     result = 37 * result + (getConfirmurl() == null ? 0 : getConfirmurl().hashCode());
/* 323 */     result = 37 * result + (getPriceurl() == null ? 0 : getPriceurl().hashCode());
/* 324 */     result = 37 * result + (getFreeurl() == null ? 0 : getFreeurl().hashCode());
/* 325 */     result = 37 * result + (getNeedconfmback() == null ? 0 : getNeedconfmback().hashCode());
/* 326 */     result = 37 * result + (getChecktype() == null ? 0 : getChecktype().hashCode());
/* 327 */     result = 37 * result + (getEffdate() == null ? 0 : getEffdate().hashCode());
/* 328 */     result = 37 * result + (getExpdate() == null ? 0 : getExpdate().hashCode());
/* 329 */     result = 37 * result + (getWapservicetype() == null ? 0 : getWapservicetype().hashCode());
/* 330 */     result = 37 * result + (getSporderurl() == null ? 0 : getSporderurl().hashCode());
/* 331 */     result = 37 * result + (getSynorderfunc() == null ? 0 : getSynorderfunc().hashCode());
/* 332 */     result = 37 * result + (getSppsedoflag() == null ? 0 : getSppsedoflag().hashCode());
/* 333 */     result = 37 * result + (getServiceid() == null ? 0 : getServiceid().hashCode());
/* 334 */     return result;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VasReserveInfo
 * JD-Core Version:    0.6.0
 */