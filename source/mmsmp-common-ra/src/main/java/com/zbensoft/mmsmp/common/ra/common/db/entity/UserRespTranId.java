/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class UserRespTranId
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7074284252256391114L;
/*    */   private Integer serviceuniqueid;
/*    */   private String cellphonenumber;
/*    */ 
/*    */   public UserRespTranId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UserRespTranId(Integer serviceuniqueid, String cellphonenumber)
/*    */   {
/* 31 */     this.serviceuniqueid = serviceuniqueid;
/* 32 */     this.cellphonenumber = cellphonenumber;
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
/*    */   public String getCellphonenumber() {
/* 47 */     return this.cellphonenumber;
/*    */   }
/*    */ 
/*    */   public void setCellphonenumber(String cellphonenumber) {
/* 51 */     this.cellphonenumber = cellphonenumber;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other)
/*    */   {
/* 58 */     if (this == other) return true;
/* 59 */     if (other == null) return false;
/* 60 */     if (!(other instanceof UserRespTranId)) return false;
/* 61 */     UserRespTranId castOther = (UserRespTranId)other;
/*    */ 
/* 64 */     return ((getServiceuniqueid() == castOther.getServiceuniqueid()) || ((getServiceuniqueid() != null) && (castOther.getServiceuniqueid() != null) && (getServiceuniqueid().equals(castOther.getServiceuniqueid())))) && (
/* 64 */       (getCellphonenumber() == castOther.getCellphonenumber()) || ((getCellphonenumber() != null) && (castOther.getCellphonenumber() != null) && (getCellphonenumber().equals(castOther.getCellphonenumber()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 68 */     int result = 17;
/*    */ 
/* 70 */     result = 37 * result + (getServiceuniqueid() == null ? 0 : getServiceuniqueid().hashCode());
/* 71 */     result = 37 * result + (getCellphonenumber() == null ? 0 : getCellphonenumber().hashCode());
/* 72 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserRespTranId
 * JD-Core Version:    0.6.0
 */