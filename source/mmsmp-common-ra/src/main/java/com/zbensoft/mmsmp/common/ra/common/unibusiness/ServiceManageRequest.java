/*     */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ServiceManageRequest", propOrder={"operate", "serviceID", "spID"})
/*     */ public class ServiceManageRequest extends CommonRequest
/*     */ {
/*     */   protected int operate;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String serviceID;
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
/*     */   public String getServiceID()
/*     */   {
/*  72 */     return this.serviceID;
/*     */   }
/*     */ 
/*     */   public void setServiceID(String value)
/*     */   {
/*  84 */     this.serviceID = value;
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
 * Qualified Name:     com.aceway.common.unibusiness.ServiceManageRequest
 * JD-Core Version:    0.6.0
 */