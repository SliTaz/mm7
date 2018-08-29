/*     */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="Response", propOrder={"desc", "returnCode", "codeType"})
/*     */ public class Response
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String desc;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String returnCode;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected int codeType;
/*     */ 
/*     */   public int getCodeType()
/*     */   {
/*  52 */     return this.codeType;
/*     */   }
/*     */ 
/*     */   public void setCodeType(int codeType)
/*     */   {
/*  59 */     this.codeType = codeType;
/*     */   }
/*     */ 
/*     */   public String getDesc()
/*     */   {
/*  71 */     return this.desc;
/*     */   }
/*     */ 
/*     */   public void setDesc(String value)
/*     */   {
/*  83 */     this.desc = value;
/*     */   }
/*     */ 
/*     */   public String getReturnCode()
/*     */   {
/*  95 */     return this.returnCode;
/*     */   }
/*     */ 
/*     */   public void setReturnCode(String value)
/*     */   {
/* 107 */     this.returnCode = value;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.unibusiness.Response
 * JD-Core Version:    0.6.0
 */