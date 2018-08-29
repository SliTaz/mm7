/*     */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ReverseUnsubscribeManageRequest", propOrder={"phone", "userType", "productType", "productID"})
/*     */ public class ReverseUnsubscribeManageRequest extends CommonRequest
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String phone;
/*     */ 
/*     */   @XmlElement(required=true, type=Integer.class, nillable=true)
/*     */   protected Integer userType;
/*     */ 
/*     */   @XmlElement(required=true, type=Integer.class, nillable=true)
/*     */   protected Integer productType;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String productID;
/*     */ 
/*     */   public String getPhone()
/*     */   {
/*  61 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String value)
/*     */   {
/*  73 */     this.phone = value;
/*     */   }
/*     */ 
/*     */   public Integer getUserType()
/*     */   {
/*  85 */     return this.userType;
/*     */   }
/*     */ 
/*     */   public void setUserType(Integer value)
/*     */   {
/*  97 */     this.userType = value;
/*     */   }
/*     */ 
/*     */   public Integer getProductType()
/*     */   {
/* 109 */     return this.productType;
/*     */   }
/*     */ 
/*     */   public void setProductType(Integer value)
/*     */   {
/* 121 */     this.productType = value;
/*     */   }
/*     */ 
/*     */   public String getProductID()
/*     */   {
/* 133 */     return this.productID;
/*     */   }
/*     */ 
/*     */   public void setProductID(String value)
/*     */   {
/* 145 */     this.productID = value;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.unibusiness.ReverseUnsubscribeManageRequest
 * JD-Core Version:    0.6.0
 */