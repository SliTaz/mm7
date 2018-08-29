/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class VasTreeItemsId
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1964903982188301066L;
/*    */   private String treeid;
/*    */   private String vaspid;
/*    */   private String vasid;
/*    */ 
/*    */   public VasTreeItemsId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public VasTreeItemsId(String treeid, String vaspid, String vasid)
/*    */   {
/* 32 */     this.treeid = treeid;
/* 33 */     this.vaspid = vaspid;
/* 34 */     this.vasid = vasid;
/*    */   }
/*    */ 
/*    */   public String getTreeid()
/*    */   {
/* 41 */     return this.treeid;
/*    */   }
/*    */ 
/*    */   public void setTreeid(String treeid) {
/* 45 */     this.treeid = treeid;
/*    */   }
/*    */ 
/*    */   public String getVaspid() {
/* 49 */     return this.vaspid;
/*    */   }
/*    */ 
/*    */   public void setVaspid(String vaspid) {
/* 53 */     this.vaspid = vaspid;
/*    */   }
/*    */ 
/*    */   public String getVasid() {
/* 57 */     return this.vasid;
/*    */   }
/*    */ 
/*    */   public void setVasid(String vasid) {
/* 61 */     this.vasid = vasid;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other)
/*    */   {
/* 68 */     if (this == other) return true;
/* 69 */     if (other == null) return false;
/* 70 */     if (!(other instanceof VasTreeItemsId)) return false;
/* 71 */     VasTreeItemsId castOther = (VasTreeItemsId)other;
/*    */ 
/* 75 */     return ((getTreeid() == castOther.getTreeid()) || ((getTreeid() != null) && (castOther.getTreeid() != null) && (getTreeid().equals(castOther.getTreeid())))) && 
/* 74 */       ((getVaspid() == castOther.getVaspid()) || ((getVaspid() != null) && (castOther.getVaspid() != null) && (getVaspid().equals(castOther.getVaspid())))) && (
/* 75 */       (getVasid() == castOther.getVasid()) || ((getVasid() != null) && (castOther.getVasid() != null) && (getVasid().equals(castOther.getVasid()))));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 79 */     int result = 17;
/*    */ 
/* 81 */     result = 37 * result + (getTreeid() == null ? 0 : getTreeid().hashCode());
/* 82 */     result = 37 * result + (getVaspid() == null ? 0 : getVaspid().hashCode());
/* 83 */     result = 37 * result + (getVasid() == null ? 0 : getVasid().hashCode());
/* 84 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VasTreeItemsId
 * JD-Core Version:    0.6.0
 */