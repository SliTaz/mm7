/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class UserOrder
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5129961608946565112L;
/*     */   private UserOrderId id;
/*     */   private String chargeparty;
/*     */   private Date orderdate;
/*     */   private String ordermethod;
/*     */   private String feetype;
/*     */   private Double fee;
/*     */   private Integer orderhisid;
/*     */   private String featurestr;
/*     */   private Integer status;
/*     */   private Integer serviceuniqueid;
/*     */   private String version;
/*     */   private String nodisturbtime;
/*     */   private Integer priority;
/*     */   private String orderDate1;
/*     */   private String userarea;
/*     */ 
/*     */   public String getOrderDate1()
/*     */   {
/*  40 */     return this.orderDate1;
/*     */   }
/*     */ 
/*     */   public void setOrderDate1(String orderDate1) {
/*  44 */     this.orderDate1 = orderDate1;
/*     */   }
/*     */ 
/*     */   public Integer getPriority() {
/*  48 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority) {
/*  52 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public UserOrder()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UserOrder(UserOrderId id, String chargeparty, Date orderdate, String ordermethod)
/*     */   {
/*  62 */     this.id = id;
/*  63 */     this.chargeparty = chargeparty;
/*  64 */     this.orderdate = orderdate;
/*  65 */     this.ordermethod = ordermethod;
/*     */   }
/*     */ 
/*     */   public UserOrder(UserOrderId id, String chargeparty, Date orderdate, String ordermethod, String feetype, Double fee, Integer orderhisid)
/*     */   {
/*  71 */     this.id = id;
/*  72 */     this.chargeparty = chargeparty;
/*  73 */     this.orderdate = orderdate;
/*  74 */     this.ordermethod = ordermethod;
/*  75 */     this.feetype = feetype;
/*  76 */     this.fee = fee;
/*  77 */     this.orderhisid = orderhisid;
/*     */   }
/*     */ 
/*     */   public UserOrderId getId()
/*     */   {
/*  83 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(UserOrderId id) {
/*  87 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getChargeparty() {
/*  91 */     return this.chargeparty;
/*     */   }
/*     */ 
/*     */   public void setChargeparty(String chargeparty) {
/*  95 */     this.chargeparty = chargeparty;
/*     */   }
/*     */ 
/*     */   public Date getOrderdate() {
/*  99 */     return this.orderdate;
/*     */   }
/*     */ 
/*     */   public void setOrderdate(Date orderdate) {
/* 103 */     this.orderdate = orderdate;
/*     */   }
/*     */ 
/*     */   public String getOrdermethod() {
/* 107 */     return this.ordermethod;
/*     */   }
/*     */ 
/*     */   public void setOrdermethod(String ordermethod) {
/* 111 */     this.ordermethod = ordermethod;
/*     */   }
/*     */ 
/*     */   public String getFeetype() {
/* 115 */     return this.feetype;
/*     */   }
/*     */ 
/*     */   public void setFeetype(String feetype) {
/* 119 */     this.feetype = feetype;
/*     */   }
/*     */ 
/*     */   public Double getFee() {
/* 123 */     return this.fee;
/*     */   }
/*     */ 
/*     */   public void setFee(Double fee) {
/* 127 */     this.fee = fee;
/*     */   }
/*     */ 
/*     */   public Integer getOrderhisid() {
/* 131 */     return this.orderhisid;
/*     */   }
/*     */ 
/*     */   public void setOrderhisid(Integer orderhisid) {
/* 135 */     this.orderhisid = orderhisid;
/*     */   }
/*     */ 
/*     */   public String getFeaturestr() {
/* 139 */     return this.featurestr;
/*     */   }
/*     */ 
/*     */   public void setFeaturestr(String featurestr) {
/* 143 */     this.featurestr = featurestr;
/*     */   }
/*     */ 
/*     */   public Integer getStatus() {
/* 147 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Integer status) {
/* 151 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/* 155 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version) {
/* 159 */     this.version = version;
/*     */   }
/*     */ 
/*     */   public String getNodisturbtime() {
/* 163 */     return this.nodisturbtime;
/*     */   }
/*     */ 
/*     */   public void setNodisturbtime(String nodisturbtime) {
/* 167 */     this.nodisturbtime = nodisturbtime;
/*     */   }
/*     */ 
/*     */   public Integer getServiceuniqueid() {
/* 171 */     return this.serviceuniqueid;
/*     */   }
/*     */ 
/*     */   public void setServiceuniqueid(Integer serviceuniqueid) {
/* 175 */     this.serviceuniqueid = serviceuniqueid;
/*     */   }
/*     */ 
/*     */   public String getUserarea() {
/* 179 */     return this.userarea;
/*     */   }
/*     */ 
/*     */   public void setUserarea(String userarea) {
/* 183 */     this.userarea = userarea;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserOrder
 * JD-Core Version:    0.6.0
 */