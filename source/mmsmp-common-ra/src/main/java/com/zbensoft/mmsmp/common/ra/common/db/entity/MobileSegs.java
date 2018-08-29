/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class MobileSegs
/*    */   implements Serializable
/*    */ {
/*    */   private Integer segid;
/*    */   private String cityid;
/*    */   private String startno;
/*    */   private String endno;
/*    */   private String description;
/*    */ 
/*    */   public Integer getSegid()
/*    */   {
/* 33 */     return this.segid;
/*    */   }
/*    */ 
/*    */   public void setSegid(Integer segid) {
/* 37 */     this.segid = segid;
/*    */   }
/*    */ 
/*    */   public String getCityid() {
/* 41 */     return this.cityid;
/*    */   }
/*    */ 
/*    */   public void setCityid(String cityid) {
/* 45 */     this.cityid = cityid;
/*    */   }
/*    */   public String getStartno() {
/* 48 */     return this.startno;
/*    */   }
/*    */ 
/*    */   public void setStartno(String startno) {
/* 52 */     this.startno = startno;
/*    */   }
/*    */ 
/*    */   public String getEndno() {
/* 56 */     return this.endno;
/*    */   }
/*    */ 
/*    */   public void setEndno(String endno) {
/* 60 */     this.endno = endno;
/*    */   }
/*    */ 
/*    */   public String getDescription() {
/* 64 */     return this.description;
/*    */   }
/*    */ 
/*    */   public void setDescription(String description) {
/* 68 */     this.description = description;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.MobileSegs
 * JD-Core Version:    0.6.0
 */