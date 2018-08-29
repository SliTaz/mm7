/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class FlowParmsId
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4810178905477940297L;
/* 18 */   private Integer flowid = null;
/* 19 */   private String parmname = null;
/*    */ 
/*    */   public FlowParmsId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public FlowParmsId(Integer flowid, String parmname)
/*    */   {
/* 40 */     this.flowid = flowid;
/* 41 */     this.parmname = parmname;
/*    */   }
/*    */ 
/*    */   public Integer getFlowid()
/*    */   {
/* 50 */     return this.flowid;
/*    */   }
/*    */ 
/*    */   public void setFlowid(Integer flowId)
/*    */   {
/* 58 */     this.flowid = flowId;
/*    */   }
/*    */ 
/*    */   public String getParmname() {
/* 62 */     return this.parmname;
/*    */   }
/*    */ 
/*    */   public void setParmname(String parmname)
/*    */   {
/* 70 */     this.parmname = parmname;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other) {
/* 74 */     if (this == other) return true;
/* 75 */     if (other == null) return false;
/* 76 */     if (!(other instanceof FlowParmsId)) return false;
/* 77 */     FlowParmsId castOther = (FlowParmsId)other;
/*    */ 
/* 80 */     return ((getFlowid() == castOther.getFlowid()) || ((getFlowid() != null) && (castOther.getFlowid() != null) && (getFlowid().equals(castOther.getFlowid())))) && (
/* 80 */       (getParmname() == castOther.getParmname()) || ((getParmname() != null) && (castOther.getParmname() != null) && (getParmname().equals(castOther.getParmname()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 84 */     int result = 17;
/*    */ 
/* 86 */     result = 37 * result + (getFlowid() == null ? 0 : getFlowid().hashCode());
/* 87 */     result = 37 * result + (getParmname() == null ? 0 : getParmname().hashCode());
/* 88 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.FlowParmsId
 * JD-Core Version:    0.6.0
 */