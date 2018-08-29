/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VasTree
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5035160832827096792L;
/*     */   private String id;
/*     */   private String parentid;
/*     */   private String isleaf;
/*     */   private String name;
/*     */   private String description;
/*     */   private String createdate;
/*     */   private String updatedate;
/*     */ 
/*     */   public VasTree()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VasTree(String parentid, String isleaf)
/*     */   {
/*  35 */     this.parentid = parentid;
/*  36 */     this.isleaf = isleaf;
/*     */   }
/*     */ 
/*     */   public VasTree(String parentid, String isleaf, String name, String description, String createdate, String updatedate)
/*     */   {
/*  41 */     this.parentid = parentid;
/*  42 */     this.isleaf = isleaf;
/*  43 */     this.name = name;
/*  44 */     this.description = description;
/*  45 */     this.createdate = createdate;
/*  46 */     this.updatedate = updatedate;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  53 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  57 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getParentid() {
/*  61 */     return this.parentid;
/*     */   }
/*     */ 
/*     */   public void setParentid(String parentid) {
/*  65 */     this.parentid = parentid;
/*     */   }
/*     */ 
/*     */   public String getIsleaf() {
/*  69 */     return this.isleaf;
/*     */   }
/*     */ 
/*     */   public void setIsleaf(String isleaf) {
/*  73 */     this.isleaf = isleaf;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  77 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  81 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/*  85 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description) {
/*  89 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public String getCreatedate() {
/*  93 */     return this.createdate;
/*     */   }
/*     */ 
/*     */   public void setCreatedate(String createdate) {
/*  97 */     this.createdate = createdate;
/*     */   }
/*     */ 
/*     */   public String getUpdatedate() {
/* 101 */     return this.updatedate;
/*     */   }
/*     */ 
/*     */   public void setUpdatedate(String updatedate) {
/* 105 */     this.updatedate = updatedate;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VasTree
 * JD-Core Version:    0.6.0
 */