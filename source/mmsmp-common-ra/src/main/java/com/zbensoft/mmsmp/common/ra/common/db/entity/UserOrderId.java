/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.lang.builder.ToStringBuilder;
/*    */ 
/*    */ public class UserOrderId
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -5084723922120663101L;
/*    */   private String cellphonenumber;
/*    */   private Integer serviceuniqueid;
/*    */ 
/*    */   public UserOrderId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UserOrderId(String cellphonenumber, Integer serviceuniqueid)
/*    */   {
/* 33 */     this.cellphonenumber = cellphonenumber;
/* 34 */     this.serviceuniqueid = serviceuniqueid;
/*    */   }
/*    */ 
/*    */   public String getCellphonenumber()
/*    */   {
/* 41 */     return this.cellphonenumber;
/*    */   }
/*    */ 
/*    */   public void setCellphonenumber(String cellphonenumber) {
/* 45 */     this.cellphonenumber = cellphonenumber;
/*    */   }
/*    */ 
/*    */   public Integer getServiceuniqueid() {
/* 49 */     return this.serviceuniqueid;
/*    */   }
/*    */ 
/*    */   public void setServiceuniqueid(Integer serviceuniqueid) {
/* 53 */     this.serviceuniqueid = serviceuniqueid;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other)
/*    */   {
/* 60 */     if (this == other) return true;
/* 61 */     if (other == null) return false;
/* 62 */     if (!(other instanceof UserOrderId)) return false;
/* 63 */     UserOrderId castOther = (UserOrderId)other;
/*    */ 
/* 66 */     return ((getCellphonenumber() == castOther.getCellphonenumber()) || ((getCellphonenumber() != null) && (castOther.getCellphonenumber() != null) && (getCellphonenumber().equals(castOther.getCellphonenumber())))) && (
/* 66 */       (getServiceuniqueid() == castOther.getServiceuniqueid()) || ((getServiceuniqueid() != null) && (castOther.getServiceuniqueid() != null) && (getServiceuniqueid().equals(castOther.getServiceuniqueid()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 70 */     int result = 17;
/*    */ 
/* 72 */     result = 37 * result + (getCellphonenumber() == null ? 0 : getCellphonenumber().hashCode());
/* 73 */     result = 37 * result + (getServiceuniqueid() == null ? 0 : getServiceuniqueid().hashCode());
/* 74 */     return result;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 82 */     return new ToStringBuilder(this).append("cellphonenumber", 
/* 83 */       this.cellphonenumber).append("serviceuniqueid", 
/* 84 */       this.serviceuniqueid).toString();
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserOrderId
 * JD-Core Version:    0.6.0
 */