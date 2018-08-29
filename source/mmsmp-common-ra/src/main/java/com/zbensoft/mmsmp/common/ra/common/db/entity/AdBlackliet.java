/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class AdBlackliet
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7894191920926908933L;
/*    */   private String cellphonenumber;
/*    */   private String adtype;
/*    */   private String description;
/*    */ 
/*    */   public AdBlackliet()
/*    */   {
/*    */   }
/*    */ 
/*    */   public AdBlackliet(String adtype)
/*    */   {
/* 31 */     this.adtype = adtype;
/*    */   }
/*    */ 
/*    */   public AdBlackliet(String adtype, String description)
/*    */   {
/* 36 */     this.adtype = adtype;
/* 37 */     this.description = description;
/*    */   }
/*    */ 
/*    */   public String getCellphonenumber()
/*    */   {
/* 44 */     return this.cellphonenumber;
/*    */   }
/*    */ 
/*    */   public void setCellphonenumber(String cellphonenumber) {
/* 48 */     this.cellphonenumber = cellphonenumber;
/*    */   }
/*    */ 
/*    */   public String getAdtype() {
/* 52 */     return this.adtype;
/*    */   }
/*    */ 
/*    */   public void setAdtype(String adtype) {
/* 56 */     this.adtype = adtype;
/*    */   }
/*    */ 
/*    */   public String getDescription() {
/* 60 */     return this.description;
/*    */   }
/*    */ 
/*    */   public void setDescription(String description) {
/* 64 */     this.description = description;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.AdBlackliet
 * JD-Core Version:    0.6.0
 */