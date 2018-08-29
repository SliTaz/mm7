/*     */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="OrderRelationRequest", propOrder={"oldProduceID", "orderType", "productID", "spCode", "status", "userPhone", "chargeparty", "userType", "aaaURL", "serviceId", "feeType", "woInfo", "serviceName", "fee", "peroid"})
/*     */ public class OrderRelationRequest extends CommonRequest
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String oldProduceID;
/*     */   protected int orderType;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String productID;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String spCode;
/*     */   protected int status;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String userPhone;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String chargeparty;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected int userType;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String aaaURL;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String serviceId;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected int feeType;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String woInfo;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String serviceName;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected int fee;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected int peroid;
/*     */ 
/*     */   public int getPeroid()
/*     */   {
/*  90 */     return this.peroid;
/*     */   }
/*     */ 
/*     */   public void setPeroid(int peroid) {
/*  94 */     this.peroid = peroid;
/*     */   }
/*     */ 
/*     */   public String getServiceName() {
/*  98 */     return this.serviceName;
/*     */   }
/*     */ 
/*     */   public void setServiceName(String serviceName) {
/* 102 */     this.serviceName = serviceName;
/*     */   }
/*     */ 
/*     */   public int getFee() {
/* 106 */     return this.fee;
/*     */   }
/*     */ 
/*     */   public void setFee(int fee) {
/* 110 */     this.fee = fee;
/*     */   }
/*     */ 
/*     */   public String getWoInfo() {
/* 114 */     return this.woInfo;
/*     */   }
/*     */ 
/*     */   public void setWoInfo(String woInfo) {
/* 118 */     this.woInfo = woInfo;
/*     */   }
/*     */ 
/*     */   public String getAaaURL() {
/* 122 */     return this.aaaURL;
/*     */   }
/*     */ 
/*     */   public void setAaaURL(String aaaURL) {
/* 126 */     this.aaaURL = aaaURL;
/*     */   }
/*     */ 
/*     */   public String getServiceId()
/*     */   {
/* 132 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public void setServiceId(String serviceId) {
/* 136 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public int getFeeType() {
/* 140 */     return this.feeType;
/*     */   }
/*     */ 
/*     */   public void setFeeType(int feeType) {
/* 144 */     this.feeType = feeType;
/*     */   }
/*     */ 
/*     */   public String getOldProduceID()
/*     */   {
/* 156 */     return this.oldProduceID;
/*     */   }
/*     */ 
/*     */   public void setOldProduceID(String value)
/*     */   {
/* 168 */     this.oldProduceID = value;
/*     */   }
/*     */ 
/*     */   public int getOrderType()
/*     */   {
/* 176 */     return this.orderType;
/*     */   }
/*     */ 
/*     */   public void setOrderType(int value)
/*     */   {
/* 184 */     this.orderType = value;
/*     */   }
/*     */ 
/*     */   public String getProductID()
/*     */   {
/* 196 */     return this.productID;
/*     */   }
/*     */ 
/*     */   public void setProductID(String value)
/*     */   {
/* 208 */     this.productID = value;
/*     */   }
/*     */ 
/*     */   public String getSpCode()
/*     */   {
/* 220 */     return this.spCode;
/*     */   }
/*     */ 
/*     */   public void setSpCode(String value)
/*     */   {
/* 232 */     this.spCode = value;
/*     */   }
/*     */ 
/*     */   public int getStatus()
/*     */   {
/* 240 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(int value)
/*     */   {
/* 248 */     this.status = value;
/*     */   }
/*     */ 
/*     */   public String getUserPhone()
/*     */   {
/* 260 */     return this.userPhone;
/*     */   }
/*     */ 
/*     */   public void setUserPhone(String value)
/*     */   {
/* 272 */     this.userPhone = value;
/*     */   }
/*     */ 
/*     */   public String getChargeparty() {
/* 276 */     return this.chargeparty;
/*     */   }
/*     */ 
/*     */   public void setChargeparty(String chargeparty) {
/* 280 */     this.chargeparty = chargeparty;
/*     */   }
/*     */ 
/*     */   public int getUserType()
/*     */   {
/* 288 */     return this.userType;
/*     */   }
/*     */ 
/*     */   public void setUserType(int value)
/*     */   {
/* 296 */     this.userType = value;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 301 */     StringBuffer sb = new StringBuffer(super.toString());
/* 302 */     sb.append(" [");
/* 303 */     sb.append(" oldProduceID=").append(this.oldProduceID);
/* 304 */     sb.append(" orderType=").append(this.orderType);
/* 305 */     sb.append(" productID=").append(this.productID);
/* 306 */     sb.append(" spCode=").append(this.spCode);
/* 307 */     sb.append(" status=").append(this.status);
/* 308 */     sb.append(" userPhone=").append(this.userPhone);
/* 309 */     sb.append(" chargeparty=").append(this.chargeparty);
/* 310 */     sb.append(" peroid=").append(this.peroid);
/* 311 */     sb.append(" userType=").append(this.userType);
/* 312 */     sb.append(" ]");
/* 313 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.unibusiness.OrderRelationRequest
 * JD-Core Version:    0.6.0
 */