/*     */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ProductManageRequest", propOrder={"operate", "productID", "productType", "spID"})
/*     */ public class ProductManageRequest extends CommonRequest
/*     */ {
/*     */   protected int operate;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String productID;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String productType;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String spID;
/*     */ 
/*     */   public int getOperate()
/*     */   {
/*  56 */     return this.operate;
/*     */   }
/*     */ 
/*     */   public void setOperate(int value)
/*     */   {
/*  64 */     this.operate = value;
/*     */   }
/*     */ 
/*     */   public String getProductID()
/*     */   {
/*  76 */     return this.productID;
/*     */   }
/*     */ 
/*     */   public void setProductID(String value)
/*     */   {
/*  88 */     this.productID = value;
/*     */   }
/*     */ 
/*     */   public String getProductType()
/*     */   {
/* 100 */     return this.productType;
/*     */   }
/*     */ 
/*     */   public void setProductType(String value)
/*     */   {
/* 112 */     this.productType = value;
/*     */   }
/*     */ 
/*     */   public String getSpID()
/*     */   {
/* 124 */     return this.spID;
/*     */   }
/*     */ 
/*     */   public void setSpID(String value)
/*     */   {
/* 136 */     this.spID = value;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.unibusiness.ProductManageRequest
 * JD-Core Version:    0.6.0
 */