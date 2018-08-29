/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SysParameters
/*    */   implements Serializable
/*    */ {
/*    */   private String name;
/*    */   private String value;
/*    */   private String description;
/*    */ 
/*    */   public SysParameters()
/*    */   {
/*    */   }
/*    */ 
/*    */   public SysParameters(String name, String value)
/*    */   {
/* 23 */     this.name = name;
/* 24 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public SysParameters(String name, String value, String description)
/*    */   {
/* 29 */     this.name = name;
/* 30 */     this.value = value;
/* 31 */     this.description = description;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 37 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 41 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 45 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value) {
/* 49 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getDescription() {
/* 53 */     return this.description;
/*    */   }
/*    */ 
/*    */   public void setDescription(String description) {
/* 57 */     this.description = description;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.SysParameters
 * JD-Core Version:    0.6.0
 */