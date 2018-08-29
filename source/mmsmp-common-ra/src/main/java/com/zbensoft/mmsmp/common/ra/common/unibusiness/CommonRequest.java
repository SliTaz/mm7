/*     */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="CommonRequest", propOrder={"channel", "operator", "streamingNum"})
/*     */ public class CommonRequest
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String channel;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String operator;
/*     */ 
/*     */   @XmlElement(required=true, nillable=true)
/*     */   protected String streamingNum;
/*     */ 
/*     */   public String getChannel()
/*     */   {
/*  58 */     return this.channel;
/*     */   }
/*     */ 
/*     */   public void setChannel(String value)
/*     */   {
/*  70 */     this.channel = value;
/*     */   }
/*     */ 
/*     */   public String getOperator()
/*     */   {
/*  82 */     return this.operator;
/*     */   }
/*     */ 
/*     */   public void setOperator(String value)
/*     */   {
/*  94 */     this.operator = value;
/*     */   }
/*     */ 
/*     */   public String getStreamingNum()
/*     */   {
/* 106 */     return this.streamingNum;
/*     */   }
/*     */ 
/*     */   public void setStreamingNum(String value)
/*     */   {
/* 118 */     this.streamingNum = value;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.unibusiness.CommonRequest
 * JD-Core Version:    0.6.0
 */