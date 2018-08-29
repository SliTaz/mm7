/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ContentInfoAdapter
/*    */   implements Serializable
/*    */ {
/*    */   private Integer realationID;
/*    */   private Integer contentID;
/*    */   private Integer filesize;
/*    */   private String contentpath;
/*    */   private String mediasize;
/*    */   private Integer adapterTypeID;
/*    */   private Map<Integer, ContentInfoRelation> provinceContentMap;
/*    */ 
/*    */   public ContentInfoAdapter()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ContentInfoAdapter(Integer filesize, String contentpath, String mediasize)
/*    */   {
/* 33 */     this.filesize = filesize;
/* 34 */     this.contentpath = contentpath;
/* 35 */     this.mediasize = mediasize;
/*    */   }
/*    */ 
/*    */   public Integer getFilesize()
/*    */   {
/* 43 */     return this.filesize;
/*    */   }
/*    */ 
/*    */   public void setFilesize(Integer filesize) {
/* 47 */     this.filesize = filesize;
/*    */   }
/*    */ 
/*    */   public String getContentpath() {
/* 51 */     return this.contentpath;
/*    */   }
/*    */ 
/*    */   public void setContentpath(String contentpath) {
/* 55 */     this.contentpath = contentpath;
/*    */   }
/*    */ 
/*    */   public String getMediasize() {
/* 59 */     return this.mediasize;
/*    */   }
/*    */ 
/*    */   public void setMediasize(String mediasize) {
/* 63 */     this.mediasize = mediasize;
/*    */   }
/*    */ 
/*    */   public Integer getAdapterTypeID() {
/* 67 */     return this.adapterTypeID;
/*    */   }
/*    */ 
/*    */   public void setAdapterTypeID(Integer adapterTypeID) {
/* 71 */     this.adapterTypeID = adapterTypeID;
/*    */   }
/*    */ 
/*    */   public Integer getRealationID()
/*    */   {
/* 77 */     return this.realationID;
/*    */   }
/*    */ 
/*    */   public void setRealationID(Integer realationID) {
/* 81 */     this.realationID = realationID;
/*    */   }
/*    */ 
/*    */   public Integer getContentID() {
/* 85 */     return this.contentID;
/*    */   }
/*    */ 
/*    */   public void setContentID(Integer contentID) {
/* 89 */     this.contentID = contentID;
/*    */   }
/*    */ 
/*    */   public Map<Integer, ContentInfoRelation> getProvinceContentMap() {
/* 93 */     return this.provinceContentMap;
/*    */   }
/*    */ 
/*    */   public void setProvinceContentMap(Map<Integer, ContentInfoRelation> provinceContentMap)
/*    */   {
/* 98 */     this.provinceContentMap = provinceContentMap;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentInfoAdapter
 * JD-Core Version:    0.6.0
 */