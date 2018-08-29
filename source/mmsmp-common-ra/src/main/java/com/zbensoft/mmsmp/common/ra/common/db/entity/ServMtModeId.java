/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ServMtModeId
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 9106323523224834454L;
/*    */   private Integer servuniqueid;
/*    */   private Integer indexno;
/*    */ 
/*    */   public ServMtModeId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ServMtModeId(Integer servuniqueid, Integer indexno)
/*    */   {
/* 31 */     this.servuniqueid = servuniqueid;
/* 32 */     this.indexno = indexno;
/*    */   }
/*    */ 
/*    */   public Integer getServuniqueid()
/*    */   {
/* 39 */     return this.servuniqueid;
/*    */   }
/*    */ 
/*    */   public void setServuniqueid(Integer servuniqueid) {
/* 43 */     this.servuniqueid = servuniqueid;
/*    */   }
/*    */ 
/*    */   public Integer getIndexno() {
/* 47 */     return this.indexno;
/*    */   }
/*    */ 
/*    */   public void setIndexno(Integer indexno) {
/* 51 */     this.indexno = indexno;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other)
/*    */   {
/* 58 */     if (this == other) return true;
/* 59 */     if (other == null) return false;
/* 60 */     if (!(other instanceof ServMtModeId)) return false;
/* 61 */     ServMtModeId castOther = (ServMtModeId)other;
/*    */ 
/* 64 */     return ((getServuniqueid() == castOther.getServuniqueid()) || ((getServuniqueid() != null) && (castOther.getServuniqueid() != null) && (getServuniqueid().equals(castOther.getServuniqueid())))) && (
/* 64 */       (getIndexno() == castOther.getIndexno()) || ((getIndexno() != null) && (castOther.getIndexno() != null) && (getIndexno().equals(castOther.getIndexno()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 68 */     int result = 17;
/*    */ 
/* 70 */     result = 37 * result + (getServuniqueid() == null ? 0 : getServuniqueid().hashCode());
/* 71 */     result = 37 * result + (getIndexno() == null ? 0 : getIndexno().hashCode());
/* 72 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServMtModeId
 * JD-Core Version:    0.6.0
 */