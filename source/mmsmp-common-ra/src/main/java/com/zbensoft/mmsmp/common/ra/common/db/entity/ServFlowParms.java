/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ServFlowParms
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4322344634835160391L;
/*    */   private ServFlowParmsId id;
/*    */   private String parmvalue;
/*    */ 
/*    */   public ServFlowParms()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ServFlowParms(String parmvalue)
/*    */   {
/* 31 */     this.parmvalue = parmvalue;
/*    */   }
/*    */ 
/*    */   public ServFlowParmsId getId()
/*    */   {
/* 38 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(ServFlowParmsId id) {
/* 42 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getParmvalue() {
/* 46 */     return this.parmvalue;
/*    */   }
/*    */ 
/*    */   public void setParmvalue(String parmvalue) {
/* 50 */     this.parmvalue = parmvalue;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServFlowParms
 * JD-Core Version:    0.6.0
 */