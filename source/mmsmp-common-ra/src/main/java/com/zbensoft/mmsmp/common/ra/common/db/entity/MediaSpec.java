/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class MediaSpec
/*    */   implements Serializable
/*    */ {
/*    */   private Integer mediaspecid;
/*    */   private String mediatype;
/*    */   private String filetype;
/*    */   private String spec;
/*    */   private Integer mediapriority;
/* 21 */   private Set contentInfoRelations = new HashSet(0);
/*    */ 
/*    */   public MediaSpec()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MediaSpec(Integer mediaspecid)
/*    */   {
/* 31 */     this.mediaspecid = mediaspecid;
/*    */   }
/*    */ 
/*    */   public MediaSpec(Integer mediaspecid, String mediatype, String filetype, String spec, Integer mediapriority, Set contentInfoRelations)
/*    */   {
/* 37 */     this.mediaspecid = mediaspecid;
/* 38 */     this.mediatype = mediatype;
/* 39 */     this.filetype = filetype;
/* 40 */     this.spec = spec;
/* 41 */     this.mediapriority = mediapriority;
/* 42 */     this.contentInfoRelations = contentInfoRelations;
/*    */   }
/*    */ 
/*    */   public Integer getMediaspecid()
/*    */   {
/* 48 */     return this.mediaspecid;
/*    */   }
/*    */ 
/*    */   public void setMediaspecid(Integer mediaspecid) {
/* 52 */     this.mediaspecid = mediaspecid;
/*    */   }
/*    */ 
/*    */   public String getMediatype() {
/* 56 */     return this.mediatype;
/*    */   }
/*    */ 
/*    */   public void setMediatype(String mediatype) {
/* 60 */     this.mediatype = mediatype;
/*    */   }
/*    */ 
/*    */   public String getFiletype() {
/* 64 */     return this.filetype;
/*    */   }
/*    */ 
/*    */   public void setFiletype(String filetype) {
/* 68 */     this.filetype = filetype;
/*    */   }
/*    */ 
/*    */   public String getSpec() {
/* 72 */     return this.spec;
/*    */   }
/*    */ 
/*    */   public void setSpec(String spec) {
/* 76 */     this.spec = spec;
/*    */   }
/*    */ 
/*    */   public Integer getMediapriority() {
/* 80 */     return this.mediapriority;
/*    */   }
/*    */ 
/*    */   public void setMediapriority(Integer mediapriority) {
/* 84 */     this.mediapriority = mediapriority;
/*    */   }
/*    */ 
/*    */   public Set getContentInfoRelations() {
/* 88 */     return this.contentInfoRelations;
/*    */   }
/*    */ 
/*    */   public void setContentInfoRelations(Set contentInfoRelations) {
/* 92 */     this.contentInfoRelations = contentInfoRelations;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.MediaSpec
 * JD-Core Version:    0.6.0
 */