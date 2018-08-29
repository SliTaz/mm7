/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ContentInfoRelation
/*    */   implements Serializable
/*    */ {
/*    */   private Integer relationId;
/*    */   private Integer contentid;
/*    */   private Integer filesize;
/*    */   private String contentpath;
/*    */   private String mediasize;
/*    */   private String provincecode;
/*    */ 
/*    */   public ContentInfoRelation()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ContentInfoRelation(Integer filesize, String contentpath, String mediasize)
/*    */   {
/* 33 */     this.filesize = filesize;
/* 34 */     this.contentpath = contentpath;
/* 35 */     this.mediasize = mediasize;
/*    */   }
/*    */ 
/*    */   public Integer getContentid()
/*    */   {
/* 41 */     return this.contentid;
/*    */   }
/*    */ 
/*    */   public void setContentid(Integer contentid) {
/* 45 */     this.contentid = contentid;
/*    */   }
/*    */ 
/*    */   public Integer getFilesize() {
/* 49 */     return this.filesize;
/*    */   }
/*    */ 
/*    */   public void setFilesize(Integer filesize) {
/* 53 */     this.filesize = filesize;
/*    */   }
/*    */ 
/*    */   public String getContentpath() {
/* 57 */     return this.contentpath;
/*    */   }
/*    */ 
/*    */   public void setContentpath(String contentpath) {
/* 61 */     this.contentpath = contentpath;
/*    */   }
/*    */ 
/*    */   public String getMediasize() {
/* 65 */     return this.mediasize;
/*    */   }
/*    */ 
/*    */   public void setMediasize(String mediasize) {
/* 69 */     this.mediasize = mediasize;
/*    */   }
/*    */ 
/*    */   public String getProvincecode() {
/* 73 */     return this.provincecode;
/*    */   }
/*    */ 
/*    */   public void setProvincecode(String provincecode) {
/* 77 */     this.provincecode = provincecode;
/*    */   }
/*    */ 
/*    */   public Integer getRelationId() {
/* 81 */     return this.relationId;
/*    */   }
/*    */ 
/*    */   public void setRelationId(Integer relationId) {
/* 85 */     this.relationId = relationId;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentInfoRelation
 * JD-Core Version:    0.6.0
 */