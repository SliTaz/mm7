/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ContentTreeItemsId
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -1289444936427510524L;
/*    */   private String treeid;
/*    */   private Integer contentid;
/*    */ 
/*    */   public ContentTreeItemsId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ContentTreeItemsId(String treeid, Integer contentid)
/*    */   {
/* 31 */     this.treeid = treeid;
/* 32 */     this.contentid = contentid;
/*    */   }
/*    */ 
/*    */   public String getTreeid()
/*    */   {
/* 39 */     return this.treeid;
/*    */   }
/*    */ 
/*    */   public void setTreeid(String treeid) {
/* 43 */     this.treeid = treeid;
/*    */   }
/*    */ 
/*    */   public Integer getContentid() {
/* 47 */     return this.contentid;
/*    */   }
/*    */ 
/*    */   public void setContentid(Integer contentid) {
/* 51 */     this.contentid = contentid;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other)
/*    */   {
/* 58 */     if (this == other) return true;
/* 59 */     if (other == null) return false;
/* 60 */     if (!(other instanceof ContentTreeItemsId)) return false;
/* 61 */     ContentTreeItemsId castOther = (ContentTreeItemsId)other;
/*    */ 
/* 64 */     return ((getTreeid() == castOther.getTreeid()) || ((getTreeid() != null) && (castOther.getTreeid() != null) && (getTreeid().equals(castOther.getTreeid())))) && (
/* 64 */       (getContentid() == castOther.getContentid()) || ((getContentid() != null) && (castOther.getContentid() != null) && (getContentid().equals(castOther.getContentid()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 68 */     int result = 17;
/*    */ 
/* 70 */     result = 37 * result + (getTreeid() == null ? 0 : getTreeid().hashCode());
/* 71 */     result = 37 * result + (getContentid() == null ? 0 : getContentid().hashCode());
/* 72 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentTreeItemsId
 * JD-Core Version:    0.6.0
 */