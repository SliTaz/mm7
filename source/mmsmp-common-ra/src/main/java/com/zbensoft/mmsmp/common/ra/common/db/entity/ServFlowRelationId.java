/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ServFlowRelationId
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5136144616800338748L;
/*    */   private Integer serviceuniqueid;
/*    */   private Integer flowid;
/*    */ 
/*    */   public ServFlowRelationId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ServFlowRelationId(Integer serviceuniqueid, Integer flowid)
/*    */   {
/* 31 */     this.serviceuniqueid = serviceuniqueid;
/* 32 */     this.flowid = flowid;
/*    */   }
/*    */ 
/*    */   public Integer getServiceuniqueid()
/*    */   {
/* 39 */     return this.serviceuniqueid;
/*    */   }
/*    */ 
/*    */   public void setServiceuniqueid(Integer serviceuniqueid) {
/* 43 */     this.serviceuniqueid = serviceuniqueid;
/*    */   }
/*    */ 
/*    */   public Integer getFlowid() {
/* 47 */     return this.flowid;
/*    */   }
/*    */ 
/*    */   public void setFlowid(Integer flowid) {
/* 51 */     this.flowid = flowid;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other)
/*    */   {
/* 58 */     if (this == other) return true;
/* 59 */     if (other == null) return false;
/* 60 */     if (!(other instanceof ServFlowRelationId)) return false;
/* 61 */     ServFlowRelationId castOther = (ServFlowRelationId)other;
/*    */ 
/* 64 */     return ((getServiceuniqueid() == castOther.getServiceuniqueid()) || ((getServiceuniqueid() != null) && (castOther.getServiceuniqueid() != null) && (getServiceuniqueid().equals(castOther.getServiceuniqueid())))) && (
/* 64 */       (getFlowid() == castOther.getFlowid()) || ((getFlowid() != null) && (castOther.getFlowid() != null) && (getFlowid().equals(castOther.getFlowid()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 68 */     int result = 17;
/*    */ 
/* 70 */     result = 37 * result + (getServiceuniqueid() == null ? 0 : getServiceuniqueid().hashCode());
/* 71 */     result = 37 * result + (getFlowid() == null ? 0 : getFlowid().hashCode());
/* 72 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServFlowRelationId
 * JD-Core Version:    0.6.0
 */