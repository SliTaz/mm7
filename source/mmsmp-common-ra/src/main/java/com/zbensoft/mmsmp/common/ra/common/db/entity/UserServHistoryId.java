/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class UserServHistoryId
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4778474096568512261L;
/*    */   private String cellphonenumber;
/*    */   private Integer servuniqueid;
/*    */   private Integer contentid;
/*    */ 
/*    */   public UserServHistoryId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UserServHistoryId(String cellphonenumber, Integer servuniqueid, Integer contentid)
/*    */   {
/* 32 */     this.cellphonenumber = cellphonenumber;
/* 33 */     this.servuniqueid = servuniqueid;
/* 34 */     this.contentid = contentid;
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
/*    */   public Integer getServuniqueid() {
/* 49 */     return this.servuniqueid;
/*    */   }
/*    */ 
/*    */   public void setServuniqueid(Integer servuniqueid) {
/* 53 */     this.servuniqueid = servuniqueid;
/*    */   }
/*    */ 
/*    */   public Integer getContentid() {
/* 57 */     return this.contentid;
/*    */   }
/*    */ 
/*    */   public void setContentid(Integer contentid) {
/* 61 */     this.contentid = contentid;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other)
/*    */   {
/* 68 */     if (this == other) return true;
/* 69 */     if (other == null) return false;
/* 70 */     if (!(other instanceof UserServHistoryId)) return false;
/* 71 */     UserServHistoryId castOther = (UserServHistoryId)other;
/*    */ 
/* 75 */     return ((getCellphonenumber() == castOther.getCellphonenumber()) || ((getCellphonenumber() != null) && (castOther.getCellphonenumber() != null) && (getCellphonenumber().equals(castOther.getCellphonenumber())))) && 
/* 74 */       ((getServuniqueid() == castOther.getServuniqueid()) || ((getServuniqueid() != null) && (castOther.getServuniqueid() != null) && (getServuniqueid().equals(castOther.getServuniqueid())))) && (
/* 75 */       (getContentid() == castOther.getContentid()) || ((getContentid() != null) && (castOther.getContentid() != null) && (getContentid().equals(castOther.getContentid()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 79 */     int result = 17;
/*    */ 
/* 81 */     result = 37 * result + (getCellphonenumber() == null ? 0 : getCellphonenumber().hashCode());
/* 82 */     result = 37 * result + (getServuniqueid() == null ? 0 : getServuniqueid().hashCode());
/* 83 */     result = 37 * result + (getContentid() == null ? 0 : getContentid().hashCode());
/* 84 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserServHistoryId
 * JD-Core Version:    0.6.0
 */