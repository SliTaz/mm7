/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ServContRelation
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8271023252585030926L;
/*    */   private Integer relationid;
/*    */   private Integer serviceuniqueid;
/*    */   private String itemtype;
/*    */   private Integer itemid;
/*    */   private String contenttreeid;
/*    */ 
/*    */   public ServContRelation()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ServContRelation(Integer relationid, Integer serviceuniqueid, Integer itemid)
/*    */   {
/* 33 */     this.relationid = relationid;
/* 34 */     this.serviceuniqueid = serviceuniqueid;
/* 35 */     this.itemid = itemid;
/*    */   }
/*    */ 
/*    */   public ServContRelation(Integer relationid, Integer serviceuniqueid, String itemtype, Integer itemid, String contenttreeid)
/*    */   {
/* 40 */     this.relationid = relationid;
/* 41 */     this.serviceuniqueid = serviceuniqueid;
/* 42 */     this.itemtype = itemtype;
/* 43 */     this.itemid = itemid;
/* 44 */     this.contenttreeid = contenttreeid;
/*    */   }
/*    */ 
/*    */   public Integer getRelationid()
/*    */   {
/* 51 */     return this.relationid;
/*    */   }
/*    */ 
/*    */   public void setRelationid(Integer relationid) {
/* 55 */     this.relationid = relationid;
/*    */   }
/*    */ 
/*    */   public Integer getServiceuniqueid() {
/* 59 */     return this.serviceuniqueid;
/*    */   }
/*    */ 
/*    */   public void setServiceuniqueid(Integer serviceuniqueid) {
/* 63 */     this.serviceuniqueid = serviceuniqueid;
/*    */   }
/*    */ 
/*    */   public String getItemtype() {
/* 67 */     return this.itemtype;
/*    */   }
/*    */ 
/*    */   public void setItemtype(String itemtype) {
/* 71 */     this.itemtype = itemtype;
/*    */   }
/*    */ 
/*    */   public Integer getItemid() {
/* 75 */     return this.itemid;
/*    */   }
/*    */ 
/*    */   public void setItemid(Integer itemid) {
/* 79 */     this.itemid = itemid;
/*    */   }
/*    */ 
/*    */   public String getContenttreeid() {
/* 83 */     return this.contenttreeid;
/*    */   }
/*    */ 
/*    */   public void setContenttreeid(String contenttreeid) {
/* 87 */     this.contenttreeid = contenttreeid;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServContRelation
 * JD-Core Version:    0.6.0
 */