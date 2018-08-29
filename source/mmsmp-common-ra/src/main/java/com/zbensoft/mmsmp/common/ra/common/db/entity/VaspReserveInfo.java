/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VaspReserveInfo
/*     */   implements Serializable
/*     */ {
/*     */   private String recordSequenceId;
/*     */   private Integer spPsedoFlag;
/*     */   private String spid;
/*     */   private String spCity;
/*     */   private Integer isTrust;
/*     */   private String spOrderUrl;
/*     */   private String orderKey;
/*     */   private Integer synOrderFunc;
/*     */   private String effDate;
/*     */   private String expdate;
/*     */   private String reserve1;
/*     */   private String spExtendNum;
/*     */   private String reportmessageurl;
/*     */ 
/*     */   public VaspReserveInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VaspReserveInfo(String recordSequenceId, String spid)
/*     */   {
/*  35 */     this.recordSequenceId = recordSequenceId;
/*  36 */     this.spid = spid;
/*     */   }
/*     */ 
/*     */   public VaspReserveInfo(String recordSequenceId, Integer spPsedoFlag, String spid, String spCity, Integer isTrust, String spOrderUrl, String orderKey, Integer synOrderFunc, String effDate, String expdate, String reserve1, String spExtendNum, String reportmessageurl)
/*     */   {
/*  42 */     this.recordSequenceId = recordSequenceId;
/*  43 */     this.spPsedoFlag = spPsedoFlag;
/*  44 */     this.spid = spid;
/*  45 */     this.spCity = spCity;
/*  46 */     this.isTrust = isTrust;
/*  47 */     this.spOrderUrl = spOrderUrl;
/*  48 */     this.orderKey = orderKey;
/*  49 */     this.synOrderFunc = synOrderFunc;
/*  50 */     this.effDate = effDate;
/*  51 */     this.expdate = expdate;
/*  52 */     this.reserve1 = reserve1;
/*  53 */     this.spExtendNum = spExtendNum;
/*  54 */     this.reportmessageurl = reportmessageurl;
/*     */   }
/*     */ 
/*     */   public String getRecordSequenceId()
/*     */   {
/*  60 */     return this.recordSequenceId;
/*     */   }
/*     */ 
/*     */   public void setRecordSequenceId(String recordSequenceId) {
/*  64 */     this.recordSequenceId = recordSequenceId;
/*     */   }
/*     */ 
/*     */   public Integer getSpPsedoFlag() {
/*  68 */     return this.spPsedoFlag;
/*     */   }
/*     */ 
/*     */   public void setSpPsedoFlag(Integer spPsedoFlag) {
/*  72 */     this.spPsedoFlag = spPsedoFlag;
/*     */   }
/*     */ 
/*     */   public String getSpid() {
/*  76 */     return this.spid;
/*     */   }
/*     */ 
/*     */   public void setSpid(String spid) {
/*  80 */     this.spid = spid;
/*     */   }
/*     */ 
/*     */   public String getSpCity() {
/*  84 */     return this.spCity;
/*     */   }
/*     */ 
/*     */   public void setSpCity(String spCity) {
/*  88 */     this.spCity = spCity;
/*     */   }
/*     */ 
/*     */   public Integer getIsTrust() {
/*  92 */     return this.isTrust;
/*     */   }
/*     */ 
/*     */   public void setIsTrust(Integer isTrust) {
/*  96 */     this.isTrust = isTrust;
/*     */   }
/*     */ 
/*     */   public String getSpOrderUrl() {
/* 100 */     return this.spOrderUrl;
/*     */   }
/*     */ 
/*     */   public void setSpOrderUrl(String spOrderUrl) {
/* 104 */     this.spOrderUrl = spOrderUrl;
/*     */   }
/*     */ 
/*     */   public String getOrderKey() {
/* 108 */     return this.orderKey;
/*     */   }
/*     */ 
/*     */   public void setOrderKey(String orderKey) {
/* 112 */     this.orderKey = orderKey;
/*     */   }
/*     */ 
/*     */   public Integer getSynOrderFunc() {
/* 116 */     return this.synOrderFunc;
/*     */   }
/*     */ 
/*     */   public void setSynOrderFunc(Integer synOrderFunc) {
/* 120 */     this.synOrderFunc = synOrderFunc;
/*     */   }
/*     */ 
/*     */   public String getEffDate() {
/* 124 */     return this.effDate;
/*     */   }
/*     */ 
/*     */   public void setEffDate(String effDate) {
/* 128 */     this.effDate = effDate;
/*     */   }
/*     */ 
/*     */   public String getExpdate() {
/* 132 */     return this.expdate;
/*     */   }
/*     */ 
/*     */   public void setExpdate(String expdate) {
/* 136 */     this.expdate = expdate;
/*     */   }
/*     */ 
/*     */   public String getReserve1() {
/* 140 */     return this.reserve1;
/*     */   }
/*     */ 
/*     */   public void setReserve1(String reserve1) {
/* 144 */     this.reserve1 = reserve1;
/*     */   }
/*     */ 
/*     */   public String getSpExtendNum() {
/* 148 */     return this.spExtendNum;
/*     */   }
/*     */ 
/*     */   public void setSpExtendNum(String spExtendNum) {
/* 152 */     this.spExtendNum = spExtendNum;
/*     */   }
/*     */ 
/*     */   public String getReportmessageurl() {
/* 156 */     return this.reportmessageurl;
/*     */   }
/*     */ 
/*     */   public void setReportmessageurl(String reportmessageurl) {
/* 160 */     this.reportmessageurl = reportmessageurl;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other) {
/* 164 */     if (this == other)
/* 165 */       return true;
/* 166 */     if (other == null)
/* 167 */       return false;
/* 168 */     if (!(other instanceof VaspReserveInfo))
/* 169 */       return false;
/* 170 */     VaspReserveInfo castOther = (VaspReserveInfo)other;
/*     */ 
/* 197 */     return ((getRecordSequenceId() == castOther.getRecordSequenceId()) || ((getRecordSequenceId() != null) && 
/* 173 */       (castOther.getRecordSequenceId() != null) && (getRecordSequenceId().equals(castOther.getRecordSequenceId())))) && 
/* 174 */       ((getSpPsedoFlag() == castOther.getSpPsedoFlag()) || ((getSpPsedoFlag() != null) && (castOther.getSpPsedoFlag() != null) && 
/* 175 */       (getSpPsedoFlag().equals(castOther.getSpPsedoFlag())))) && 
/* 176 */       ((getSpid() == castOther.getSpid()) || ((getSpid() != null) && (castOther.getSpid() != null) && 
/* 177 */       (getSpid()
/* 177 */       .equals(castOther.getSpid())))) && 
/* 178 */       ((getSpCity() == castOther.getSpCity()) || ((getSpCity() != null) && (castOther.getSpCity() != null) && 
/* 179 */       (getSpCity()
/* 179 */       .equals(castOther.getSpCity())))) && 
/* 180 */       ((getIsTrust() == castOther.getIsTrust()) || ((getIsTrust() != null) && (castOther.getIsTrust() != null) && 
/* 181 */       (getIsTrust()
/* 181 */       .equals(castOther.getIsTrust())))) && 
/* 182 */       ((getSpOrderUrl() == castOther.getSpOrderUrl()) || ((getSpOrderUrl() != null) && (castOther.getSpOrderUrl() != null) && 
/* 183 */       (getSpOrderUrl().equals(castOther.getSpOrderUrl())))) && 
/* 184 */       ((getOrderKey() == castOther.getOrderKey()) || ((getOrderKey() != null) && (castOther.getOrderKey() != null) && 
/* 185 */       (getOrderKey().equals(castOther.getOrderKey())))) && 
/* 186 */       ((getSynOrderFunc() == castOther.getSynOrderFunc()) || ((getSynOrderFunc() != null) && (castOther.getSynOrderFunc() != null) && 
/* 187 */       (getSynOrderFunc().equals(castOther.getSynOrderFunc())))) && 
/* 188 */       ((getEffDate() == castOther.getEffDate()) || ((getEffDate() != null) && (castOther.getEffDate() != null) && 
/* 189 */       (getEffDate()
/* 189 */       .equals(castOther.getEffDate())))) && 
/* 190 */       ((getExpdate() == castOther.getExpdate()) || ((getExpdate() != null) && (castOther.getExpdate() != null) && 
/* 191 */       (getExpdate()
/* 191 */       .equals(castOther.getExpdate())))) && 
/* 192 */       ((getReserve1() == castOther.getReserve1()) || ((getReserve1() != null) && (castOther.getReserve1() != null) && 
/* 193 */       (getReserve1().equals(castOther.getReserve1())))) && 
/* 194 */       ((getSpExtendNum() == castOther.getSpExtendNum()) || ((getSpExtendNum() != null) && (castOther.getSpExtendNum() != null) && 
/* 195 */       (getSpExtendNum().equals(castOther.getSpExtendNum())))) && (
/* 196 */       (getReportmessageurl() == castOther.getReportmessageurl()) || ((getReportmessageurl() != null) && 
/* 197 */       (castOther.getReportmessageurl() != null) && (getReportmessageurl().equals(castOther.getReportmessageurl()))));
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 201 */     int result = 17;
/*     */ 
/* 203 */     result = 37 * result + (getRecordSequenceId() == null ? 0 : getRecordSequenceId().hashCode());
/* 204 */     result = 37 * result + (getSpPsedoFlag() == null ? 0 : getSpPsedoFlag().hashCode());
/* 205 */     result = 37 * result + (getSpid() == null ? 0 : getSpid().hashCode());
/* 206 */     result = 37 * result + (getSpCity() == null ? 0 : getSpCity().hashCode());
/* 207 */     result = 37 * result + (getIsTrust() == null ? 0 : getIsTrust().hashCode());
/* 208 */     result = 37 * result + (getSpOrderUrl() == null ? 0 : getSpOrderUrl().hashCode());
/* 209 */     result = 37 * result + (getOrderKey() == null ? 0 : getOrderKey().hashCode());
/* 210 */     result = 37 * result + (getSynOrderFunc() == null ? 0 : getSynOrderFunc().hashCode());
/* 211 */     result = 37 * result + (getEffDate() == null ? 0 : getEffDate().hashCode());
/* 212 */     result = 37 * result + (getExpdate() == null ? 0 : getExpdate().hashCode());
/* 213 */     result = 37 * result + (getReserve1() == null ? 0 : getReserve1().hashCode());
/* 214 */     result = 37 * result + (getSpExtendNum() == null ? 0 : getSpExtendNum().hashCode());
/* 215 */     result = 37 * result + (getReportmessageurl() == null ? 0 : getReportmessageurl().hashCode());
/* 216 */     return result;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VaspReserveInfo
 * JD-Core Version:    0.6.0
 */