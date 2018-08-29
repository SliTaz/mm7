/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ContentTree
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3268344226981839163L;
/*     */   private String id;
/*     */   private String parentid;
/*     */   private String isleaf;
/*     */   private String name;
/*     */   private String description;
/*     */   private String createdate;
/*     */   private String updatedate;
/*     */   private String alias;
/*     */ 
/*     */   public ContentTree()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ContentTree(String parentid, String isleaf)
/*     */   {
/*  36 */     this.parentid = parentid;
/*  37 */     this.isleaf = isleaf;
/*     */   }
/*     */ 
/*     */   public ContentTree(String parentid, String isleaf, String name, String description, String createdate, String updatedate, String alias)
/*     */   {
/*  42 */     this.parentid = parentid;
/*  43 */     this.isleaf = isleaf;
/*  44 */     this.name = name;
/*  45 */     this.description = description;
/*  46 */     this.createdate = createdate;
/*  47 */     this.updatedate = updatedate;
/*  48 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  55 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  59 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getParentid() {
/*  63 */     return this.parentid;
/*     */   }
/*     */ 
/*     */   public void setParentid(String parentid) {
/*  67 */     this.parentid = parentid;
/*     */   }
/*     */ 
/*     */   public String getIsleaf() {
/*  71 */     return this.isleaf;
/*     */   }
/*     */ 
/*     */   public void setIsleaf(String isleaf) {
/*  75 */     this.isleaf = isleaf;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  79 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  83 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  87 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description) {
/*  91 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public String getCreatedate() {
/*  95 */     return this.createdate;
/*     */   }
/*     */ 
/*     */   public void setCreatedate(String createdate) {
/*  99 */     this.createdate = createdate;
/*     */   }
/*     */ 
/*     */   public String getUpdatedate() {
/* 103 */     return this.updatedate;
/*     */   }
/*     */ 
/*     */   public void setUpdatedate(String updatedate) {
/* 107 */     this.updatedate = updatedate;
/*     */   }
/*     */ 
/*     */   public String getAlias() {
/* 111 */     return this.alias;
/*     */   }
/*     */ 
/*     */   public void setAlias(String alias) {
/* 115 */     this.alias = alias;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentTree
 * JD-Core Version:    0.6.0
 */