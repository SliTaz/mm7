/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ContentRelation
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6004287543172932015L;
/*    */   private Integer relationid;
/*    */   private Integer contentid;
/*    */   private Integer childcontentid;
/*    */   private Double allotratio;
/*    */ 
/*    */   public ContentRelation()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ContentRelation(Integer contentid, Integer childcontentid, Double allotratio)
/*    */   {
/* 33 */     this.contentid = contentid;
/* 34 */     this.childcontentid = childcontentid;
/* 35 */     this.allotratio = allotratio;
/*    */   }
/*    */ 
/*    */   public Integer getRelationid()
/*    */   {
/* 42 */     return this.relationid;
/*    */   }
/*    */ 
/*    */   public void setRelationid(Integer relationid) {
/* 46 */     this.relationid = relationid;
/*    */   }
/*    */ 
/*    */   public Integer getContentid() {
/* 50 */     return this.contentid;
/*    */   }
/*    */ 
/*    */   public void setContentid(Integer contentid) {
/* 54 */     this.contentid = contentid;
/*    */   }
/*    */ 
/*    */   public Integer getChildcontentid() {
/* 58 */     return this.childcontentid;
/*    */   }
/*    */ 
/*    */   public void setChildcontentid(Integer childcontentid) {
/* 62 */     this.childcontentid = childcontentid;
/*    */   }
/*    */ 
/*    */   public Double getAllotratio() {
/* 66 */     return this.allotratio;
/*    */   }
/*    */ 
/*    */   public void setAllotratio(Double allotratio) {
/* 70 */     this.allotratio = allotratio;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentRelation
 * JD-Core Version:    0.6.0
 */