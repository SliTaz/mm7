/*     */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ServiceCapabilityManageRequest", propOrder={"operate", "serviceCapabilityID", "spID"})
/*     */ public class ServiceCapabilityManageRequest extends CommonRequest
/*     */ {
/*     */   protected int operate;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String serviceCapabilityID;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String spID;
/*     */ 
/*     */   public int getOperate()
/*     */   {
/*  52 */     return this.operate;
/*     */   }
/*     */ 
/*     */   public void setOperate(int value)
/*     */   {
/*  60 */     this.operate = value;
/*     */   }
/*     */ 
/*     */   public String getServiceCapabilityID()
/*     */   {
/*  72 */     return this.serviceCapabilityID;
/*     */   }
/*     */ 
/*     */   public void setServiceCapabilityID(String value)
/*     */   {
/*  84 */     this.serviceCapabilityID = value;
/*     */   }
/*     */ 
/*     */   public String getSpID()
/*     */   {
/*  96 */     return this.spID;
/*     */   }
/*     */ 
/*     */   public void setSpID(String value)
/*     */   {
/* 108 */     this.spID = value;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.unibusiness.ServiceCapabilityManageRequest
 * JD-Core Version:    0.6.0
 */