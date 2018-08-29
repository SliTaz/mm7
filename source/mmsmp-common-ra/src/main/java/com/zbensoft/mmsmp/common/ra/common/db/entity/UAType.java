/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class UAType
/*     */ {
/*     */   private int typeId;
/*     */   private String uaTypeName;
/*     */   private String adaptFlag;
/*     */   private String imageResolution;
/*     */   private String imageType;
/*     */   private String musicType;
/*     */   private String uaTypeDesc;
/*  24 */   private Set mobileTypeInfo = new HashSet();
/*     */   private String maxImageResolution;
/*     */   private String minImageResolution;
/*     */   private Date createDate;
/*     */   private Date updateDate;
/*     */ 
/*     */   public Date getCreateDate()
/*     */   {
/*  33 */     return this.createDate;
/*     */   }
/*     */ 
/*     */   public void setCreateDate(Date createDate)
/*     */   {
/*  39 */     this.createDate = createDate;
/*     */   }
/*     */ 
/*     */   public Date getUpdateDate()
/*     */   {
/*  45 */     return this.updateDate;
/*     */   }
/*     */ 
/*     */   public void setUpdateDate(Date updateDate)
/*     */   {
/*  51 */     this.updateDate = updateDate;
/*     */   }
/*     */ 
/*     */   public String getMaxImageResolution()
/*     */   {
/*  57 */     return this.maxImageResolution;
/*     */   }
/*     */ 
/*     */   public void setMaxImageResolution(String maxImageResolution)
/*     */   {
/*  63 */     this.maxImageResolution = maxImageResolution;
/*     */   }
/*     */ 
/*     */   public String getMinImageResolution()
/*     */   {
/*  69 */     return this.minImageResolution;
/*     */   }
/*     */ 
/*     */   public void setMinImageResolution(String minImageResolution)
/*     */   {
/*  75 */     this.minImageResolution = minImageResolution;
/*     */   }
/*     */ 
/*     */   public int getTypeId()
/*     */   {
/*  81 */     return this.typeId;
/*     */   }
/*     */ 
/*     */   public void setTypeId(int typeId)
/*     */   {
/*  87 */     this.typeId = typeId;
/*     */   }
/*     */ 
/*     */   public String getUaTypeName()
/*     */   {
/*  93 */     return this.uaTypeName;
/*     */   }
/*     */ 
/*     */   public void setUaTypeName(String uaTypeName)
/*     */   {
/*  99 */     this.uaTypeName = uaTypeName;
/*     */   }
/*     */ 
/*     */   public String getAdaptFlag()
/*     */   {
/* 105 */     return this.adaptFlag;
/*     */   }
/*     */ 
/*     */   public void setAdaptFlag(String adaptFlag)
/*     */   {
/* 111 */     this.adaptFlag = adaptFlag;
/*     */   }
/*     */ 
/*     */   public String getImageResolution()
/*     */   {
/* 117 */     return this.imageResolution;
/*     */   }
/*     */ 
/*     */   public void setImageResolution(String imageResolution)
/*     */   {
/* 123 */     this.imageResolution = imageResolution;
/*     */   }
/*     */ 
/*     */   public String getImageType()
/*     */   {
/* 129 */     return this.imageType;
/*     */   }
/*     */ 
/*     */   public void setImageType(String imageType)
/*     */   {
/* 135 */     this.imageType = imageType;
/*     */   }
/*     */ 
/*     */   public String getMusicType()
/*     */   {
/* 141 */     return this.musicType;
/*     */   }
/*     */ 
/*     */   public void setMusicType(String musicType)
/*     */   {
/* 147 */     this.musicType = musicType;
/*     */   }
/*     */ 
/*     */   public String getUaTypeDesc()
/*     */   {
/* 153 */     return this.uaTypeDesc;
/*     */   }
/*     */ 
/*     */   public void setUaTypeDesc(String uaTypeDesc)
/*     */   {
/* 159 */     this.uaTypeDesc = uaTypeDesc;
/*     */   }
/*     */ 
/*     */   public Set getMobileTypeInfo()
/*     */   {
/* 165 */     return this.mobileTypeInfo;
/*     */   }
/*     */ 
/*     */   public void setMobileTypeInfo(Set mobileTypeInfo)
/*     */   {
/* 171 */     this.mobileTypeInfo = mobileTypeInfo;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UAType
 * JD-Core Version:    0.6.0
 */