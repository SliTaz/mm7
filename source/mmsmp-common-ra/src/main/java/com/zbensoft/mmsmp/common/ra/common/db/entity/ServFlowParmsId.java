/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ServFlowParmsId
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6271790693212746832L;
/*    */   private Integer serviceuniqueid;
/*    */   private Integer flowid;
/*    */   private String parmname;
/*    */ 
/*    */   public ServFlowParmsId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ServFlowParmsId(Integer serviceuniqueid, Integer flowid, String parmname)
/*    */   {
/* 32 */     this.serviceuniqueid = serviceuniqueid;
/* 33 */     this.flowid = flowid;
/* 34 */     this.parmname = parmname;
/*    */   }
/*    */ 
/*    */   public Integer getServiceuniqueid()
/*    */   {
/* 41 */     return this.serviceuniqueid;
/*    */   }
/*    */ 
/*    */   public void setServiceuniqueid(Integer serviceuniqueid) {
/* 45 */     this.serviceuniqueid = serviceuniqueid;
/*    */   }
/*    */ 
/*    */   public Integer getFlowid() {
/* 49 */     return this.flowid;
/*    */   }
/*    */ 
/*    */   public void setFlowid(Integer flowid) {
/* 53 */     this.flowid = flowid;
/*    */   }
/*    */ 
/*    */   public String getParmname() {
/* 57 */     return this.parmname;
/*    */   }
/*    */ 
/*    */   public void setParmname(String parmname) {
/* 61 */     this.parmname = parmname;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other)
/*    */   {
/* 68 */     if (this == other) return true;
/* 69 */     if (other == null) return false;
/* 70 */     if (!(other instanceof ServFlowParmsId)) return false;
/* 71 */     ServFlowParmsId castOther = (ServFlowParmsId)other;
/*    */ 
/* 75 */     return ((getServiceuniqueid() == castOther.getServiceuniqueid()) || ((getServiceuniqueid() != null) && (castOther.getServiceuniqueid() != null) && (getServiceuniqueid().equals(castOther.getServiceuniqueid())))) && 
/* 74 */       ((getFlowid() == castOther.getFlowid()) || ((getFlowid() != null) && (castOther.getFlowid() != null) && (getFlowid().equals(castOther.getFlowid())))) && (
/* 75 */       (getParmname() == castOther.getParmname()) || ((getParmname() != null) && (castOther.getParmname() != null) && (getParmname().equals(castOther.getParmname()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 79 */     int result = 17;
/*    */ 
/* 81 */     result = 37 * result + (getServiceuniqueid() == null ? 0 : getServiceuniqueid().hashCode());
/* 82 */     result = 37 * result + (getFlowid() == null ? 0 : getFlowid().hashCode());
/* 83 */     result = 37 * result + (getParmname() == null ? 0 : getParmname().hashCode());
/* 84 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServFlowParmsId
 * JD-Core Version:    0.6.0
 */