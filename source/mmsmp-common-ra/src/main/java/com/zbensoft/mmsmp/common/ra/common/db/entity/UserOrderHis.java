/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class UserOrderHis
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3380345970483434975L;
/*     */   private Integer uniqueid;
/*     */   private String cellphonenumber;
/*     */   private String chargeparty;
/*     */   private Integer serviceuniqueid;
/*     */   private Date orderdate;
/*     */   private Date cancelorderdate;
/*     */   private String ordermethod;
/*     */   private String feetype;
/*     */   private Double fee;
/*     */   private String cancelordermethod;
/*     */   private String province;
/*     */   private String servicename;
/*     */   private int fee1;
/*     */   private String cpName;
/*     */   private String status;
/*     */ 
/*     */   public String getStatus()
/*     */   {
/*  40 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status)
/*     */   {
/*  45 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getCpName()
/*     */   {
/*  50 */     return this.cpName;
/*     */   }
/*     */ 
/*     */   public void setCpName(String cpName)
/*     */   {
/*  55 */     this.cpName = cpName;
/*     */   }
/*     */ 
/*     */   public int getFee1()
/*     */   {
/*  60 */     return this.fee1;
/*     */   }
/*     */ 
/*     */   public void setFee1(int fee1)
/*     */   {
/*  65 */     this.fee1 = fee1;
/*     */   }
/*     */ 
/*     */   public String getServicename()
/*     */   {
/*  70 */     return this.servicename;
/*     */   }
/*     */ 
/*     */   public void setServicename(String servicename)
/*     */   {
/*  75 */     this.servicename = servicename;
/*     */   }
/*     */ 
/*     */   public String getProvince()
/*     */   {
/*  80 */     return this.province;
/*     */   }
/*     */ 
/*     */   public void setProvince(String province)
/*     */   {
/*  85 */     this.province = province;
/*     */   }
/*     */ 
/*     */   public UserOrderHis()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UserOrderHis(String cellphonenumber, String chargeparty, Integer serviceuniqueid, Date orderdate, Date cancelorderdate, String ordermethod, String feetype, Double fee, String cancelordermethod)
/*     */   {
/*  96 */     this.cellphonenumber = cellphonenumber;
/*  97 */     this.chargeparty = chargeparty;
/*  98 */     this.serviceuniqueid = serviceuniqueid;
/*  99 */     this.orderdate = orderdate;
/* 100 */     this.cancelorderdate = cancelorderdate;
/* 101 */     this.ordermethod = ordermethod;
/* 102 */     this.feetype = feetype;
/* 103 */     this.fee = fee;
/* 104 */     this.cancelordermethod = cancelordermethod;
/*     */   }
/*     */ 
/*     */   public Integer getUniqueid()
/*     */   {
/* 111 */     return this.uniqueid;
/*     */   }
/*     */ 
/*     */   public void setUniqueid(Integer uniqueid) {
/* 115 */     this.uniqueid = uniqueid;
/*     */   }
/*     */ 
/*     */   public String getCellphonenumber() {
/* 119 */     return this.cellphonenumber;
/*     */   }
/*     */ 
/*     */   public void setCellphonenumber(String cellphonenumber) {
/* 123 */     this.cellphonenumber = cellphonenumber;
/*     */   }
/*     */ 
/*     */   public String getChargeparty() {
/* 127 */     return this.chargeparty;
/*     */   }
/*     */ 
/*     */   public void setChargeparty(String chargeparty) {
/* 131 */     this.chargeparty = chargeparty;
/*     */   }
/*     */ 
/*     */   public Integer getServiceuniqueid() {
/* 135 */     return this.serviceuniqueid;
/*     */   }
/*     */ 
/*     */   public void setServiceuniqueid(Integer serviceuniqueid) {
/* 139 */     this.serviceuniqueid = serviceuniqueid;
/*     */   }
/*     */ 
/*     */   public Date getOrderdate() {
/* 143 */     return this.orderdate;
/*     */   }
/*     */ 
/*     */   public void setOrderdate(Date orderdate) {
/* 147 */     this.orderdate = orderdate;
/*     */   }
/*     */ 
/*     */   public Date getCancelorderdate() {
/* 151 */     return this.cancelorderdate;
/*     */   }
/*     */ 
/*     */   public void setCancelorderdate(Date cancelorderdate) {
/* 155 */     this.cancelorderdate = cancelorderdate;
/*     */   }
/*     */ 
/*     */   public String getOrdermethod() {
/* 159 */     return this.ordermethod;
/*     */   }
/*     */ 
/*     */   public void setOrdermethod(String ordermethod) {
/* 163 */     this.ordermethod = ordermethod;
/*     */   }
/*     */ 
/*     */   public String getFeetype() {
/* 167 */     return this.feetype;
/*     */   }
/*     */ 
/*     */   public void setFeetype(String feetype) {
/* 171 */     this.feetype = feetype;
/*     */   }
/*     */ 
/*     */   public Double getFee() {
/* 175 */     return this.fee;
/*     */   }
/*     */ 
/*     */   public void setFee(Double fee) {
/* 179 */     this.fee = fee;
/*     */   }
/*     */ 
/*     */   public String getCancelordermethod() {
/* 183 */     return this.cancelordermethod;
/*     */   }
/*     */ 
/*     */   public void setCancelordermethod(String cancelordermethod) {
/* 187 */     this.cancelordermethod = cancelordermethod;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserOrderHis
 * JD-Core Version:    0.6.0
 */