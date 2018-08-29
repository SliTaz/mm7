/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class FlowParms
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8240739726340861489L;
/*    */   private FlowParmsId id;
/*    */   private String parmdesc;
/*    */   private String datatype;
/*    */   private String parmdefaultvalue;
/*    */   private String isoriginal;
/*    */ 
/*    */   public FlowParms()
/*    */   {
/*    */   }
/*    */ 
/*    */   public FlowParms(FlowParmsId id, String datatype)
/*    */   {
/* 33 */     this.id = id;
/* 34 */     this.datatype = datatype;
/*    */   }
/*    */ 
/*    */   public FlowParms(FlowParmsId id, String parmdesc, String datatype, String parmdefaultvalue, String isoriginal)
/*    */   {
/* 39 */     this.id = id;
/* 40 */     this.parmdesc = parmdesc;
/* 41 */     this.datatype = datatype;
/* 42 */     this.parmdefaultvalue = parmdefaultvalue;
/* 43 */     this.isoriginal = isoriginal;
/*    */   }
/*    */ 
/*    */   public FlowParmsId getId()
/*    */   {
/* 50 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(FlowParmsId id) {
/* 54 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getParmdesc() {
/* 58 */     return this.parmdesc;
/*    */   }
/*    */ 
/*    */   public void setParmdesc(String parmdesc) {
/* 62 */     this.parmdesc = parmdesc;
/*    */   }
/*    */ 
/*    */   public String getDatatype() {
/* 66 */     return this.datatype;
/*    */   }
/*    */ 
/*    */   public void setDatatype(String datatype) {
/* 70 */     this.datatype = datatype;
/*    */   }
/*    */ 
/*    */   public String getParmdefaultvalue() {
/* 74 */     return this.parmdefaultvalue;
/*    */   }
/*    */ 
/*    */   public void setParmdefaultvalue(String parmdefaultvalue) {
/* 78 */     this.parmdefaultvalue = parmdefaultvalue;
/*    */   }
/*    */ 
/*    */   public String getIsoriginal() {
/* 82 */     return this.isoriginal;
/*    */   }
/*    */ 
/*    */   public void setIsoriginal(String isoriginal) {
/* 86 */     this.isoriginal = isoriginal;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.FlowParms
 * JD-Core Version:    0.6.0
 */