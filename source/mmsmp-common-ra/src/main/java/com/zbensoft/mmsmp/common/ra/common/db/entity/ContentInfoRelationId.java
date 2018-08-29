/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ContentInfoRelationId
/*    */   implements Serializable
/*    */ {
/*    */   private Integer contentid;
/*    */   private Integer mediaorderno;
/*    */   private Integer mediaspecid;
/*    */ 
/*    */   public ContentInfoRelationId()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ContentInfoRelationId(Integer contentid, Integer mediaorderno, Integer mediaspecid)
/*    */   {
/* 26 */     this.contentid = contentid;
/* 27 */     this.mediaorderno = mediaorderno;
/* 28 */     this.mediaspecid = mediaspecid;
/*    */   }
/*    */ 
/*    */   public Integer getContentid()
/*    */   {
/* 34 */     return this.contentid;
/*    */   }
/*    */ 
/*    */   public void setContentid(Integer contentid) {
/* 38 */     this.contentid = contentid;
/*    */   }
/*    */ 
/*    */   public Integer getMediaorderno() {
/* 42 */     return this.mediaorderno;
/*    */   }
/*    */ 
/*    */   public void setMediaorderno(Integer mediaorderno) {
/* 46 */     this.mediaorderno = mediaorderno;
/*    */   }
/*    */ 
/*    */   public Integer getMediaspecid() {
/* 50 */     return this.mediaspecid;
/*    */   }
/*    */ 
/*    */   public void setMediaspecid(Integer mediaspecid) {
/* 54 */     this.mediaspecid = mediaspecid;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object other) {
/* 58 */     if (this == other)
/* 59 */       return true;
/* 60 */     if (other == null)
/* 61 */       return false;
/* 62 */     if (!(other instanceof ContentInfoRelationId))
/* 63 */       return false;
/* 64 */     ContentInfoRelationId castOther = (ContentInfoRelationId)other;
/*    */ 
/* 66 */     if (getContentid() != castOther.getContentid()) { if (
/* 67 */         getContentid() != null) if ((castOther.getContentid() == null) || 
/* 69 */           (!getContentid().equals(castOther.getContentid())));
/* 70 */     } else if (getMediaorderno() != castOther.getMediaorderno()) { if (
/* 71 */         getMediaorderno() != null) if ((castOther.getMediaorderno() == null) || 
/* 73 */           (!getMediaorderno().equals(castOther.getMediaorderno())));
/* 74 */     } else if (getMediaspecid() != castOther.getMediaspecid()) { if (
/* 75 */         getMediaspecid() != null) if ((castOther.getMediaspecid() == null) || 
/* 77 */           (!getMediaspecid().equals(castOther.getMediaspecid()))); } else return true;
/* 66 */     return 
/* 77 */       false;
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 81 */     int result = 17;
/*    */ 
/* 83 */     result = 37 * result + (
/* 84 */       getContentid() == null ? 0 : getContentid().hashCode());
/* 85 */     result = 37 * 
/* 86 */       result + (
/* 87 */       getMediaorderno() == null ? 0 : 
/* 88 */       getMediaorderno().hashCode());
/* 89 */     result = 37 * 
/* 90 */       result + (
/* 91 */       getMediaspecid() == null ? 0 : 
/* 92 */       getMediaspecid().hashCode());
/* 93 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentInfoRelationId
 * JD-Core Version:    0.6.0
 */