/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ServMtMode
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1211765969285037196L;
/*     */   private ServMtModeId id;
/*     */   private String mtstarttime1;
/*     */   private String mtendtime1;
/*     */   private String mtstarttime2;
/*     */   private String mtendtime2;
/*     */   private String mtstarttime3;
/*     */   private String mtendtime3;
/*     */   private String mtstarttime4;
/*     */   private String mtendtime4;
/*     */   private String mtstarttime5;
/*     */   private String mtendtime5;
/*     */   private String mttype;
/*     */ 
/*     */   public String getMtendtime4()
/*     */   {
/*  38 */     return this.mtendtime4;
/*     */   }
/*     */ 
/*     */   public void setMtendtime4(String mtendtime4)
/*     */   {
/*  43 */     this.mtendtime4 = mtendtime4;
/*     */   }
/*     */ 
/*     */   public String getMtendtime5()
/*     */   {
/*  48 */     return this.mtendtime5;
/*     */   }
/*     */ 
/*     */   public void setMtendtime5(String mtendtime5)
/*     */   {
/*  53 */     this.mtendtime5 = mtendtime5;
/*     */   }
/*     */ 
/*     */   public String getMtstarttime4()
/*     */   {
/*  58 */     return this.mtstarttime4;
/*     */   }
/*     */ 
/*     */   public void setMtstarttime4(String mtstarttime4)
/*     */   {
/*  63 */     this.mtstarttime4 = mtstarttime4;
/*     */   }
/*     */ 
/*     */   public String getMtstarttime5()
/*     */   {
/*  68 */     return this.mtstarttime5;
/*     */   }
/*     */ 
/*     */   public void setMtstarttime5(String mtstarttime5)
/*     */   {
/*  73 */     this.mtstarttime5 = mtstarttime5;
/*     */   }
/*     */ 
/*     */   public String getMttype()
/*     */   {
/*  78 */     return this.mttype;
/*     */   }
/*     */ 
/*     */   public void setMttype(String mttype)
/*     */   {
/*  83 */     this.mttype = mttype;
/*     */   }
/*     */ 
/*     */   public ServMtMode()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ServMtMode(String mtstarttime1, String mtendtime1, String mtstarttime2, String mtendtime2, String mtstarttime3, String mtendtime3)
/*     */   {
/*  94 */     this.mtstarttime1 = mtstarttime1;
/*  95 */     this.mtendtime1 = mtendtime1;
/*  96 */     this.mtstarttime2 = mtstarttime2;
/*  97 */     this.mtendtime2 = mtendtime2;
/*  98 */     this.mtstarttime3 = mtstarttime3;
/*  99 */     this.mtendtime3 = mtendtime3;
/*     */   }
/*     */ 
/*     */   public ServMtModeId getId()
/*     */   {
/* 106 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(ServMtModeId id) {
/* 110 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getMtstarttime1() {
/* 114 */     return this.mtstarttime1;
/*     */   }
/*     */ 
/*     */   public void setMtstarttime1(String mtstarttime1) {
/* 118 */     this.mtstarttime1 = mtstarttime1;
/*     */   }
/*     */ 
/*     */   public String getMtendtime1() {
/* 122 */     return this.mtendtime1;
/*     */   }
/*     */ 
/*     */   public void setMtendtime1(String mtendtime1) {
/* 126 */     this.mtendtime1 = mtendtime1;
/*     */   }
/*     */ 
/*     */   public String getMtstarttime2() {
/* 130 */     return this.mtstarttime2;
/*     */   }
/*     */ 
/*     */   public void setMtstarttime2(String mtstarttime2) {
/* 134 */     this.mtstarttime2 = mtstarttime2;
/*     */   }
/*     */ 
/*     */   public String getMtendtime2() {
/* 138 */     return this.mtendtime2;
/*     */   }
/*     */ 
/*     */   public void setMtendtime2(String mtendtime2) {
/* 142 */     this.mtendtime2 = mtendtime2;
/*     */   }
/*     */ 
/*     */   public String getMtstarttime3() {
/* 146 */     return this.mtstarttime3;
/*     */   }
/*     */ 
/*     */   public void setMtstarttime3(String mtstarttime3) {
/* 150 */     this.mtstarttime3 = mtstarttime3;
/*     */   }
/*     */ 
/*     */   public String getMtendtime3() {
/* 154 */     return this.mtendtime3;
/*     */   }
/*     */ 
/*     */   public void setMtendtime3(String mtendtime3) {
/* 158 */     this.mtendtime3 = mtendtime3;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 171 */     String TAB = "    ";
/*     */ 
/* 173 */     StringBuffer retValue = new StringBuffer();
/*     */ 
/* 175 */     retValue.append("ServMtMode ( ")
/* 176 */       .append(super.toString()).append("    ")
/* 177 */       .append("id = ").append(this.id).append("    ")
/* 178 */       .append("mtstarttime1 = ").append(this.mtstarttime1).append("    ")
/* 179 */       .append("mtendtime1 = ").append(this.mtendtime1).append("    ")
/* 180 */       .append("mtstarttime2 = ").append(this.mtstarttime2).append("    ")
/* 181 */       .append("mtendtime2 = ").append(this.mtendtime2).append("    ")
/* 182 */       .append("mtstarttime3 = ").append(this.mtstarttime3).append("    ")
/* 183 */       .append("mtendtime3 = ").append(this.mtendtime3).append("    ")
/* 184 */       .append(" )");
/*     */ 
/* 186 */     return retValue.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServMtMode
 * JD-Core Version:    0.6.0
 */