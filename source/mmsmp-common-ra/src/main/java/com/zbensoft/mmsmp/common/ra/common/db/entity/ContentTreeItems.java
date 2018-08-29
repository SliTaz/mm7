/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ContentTreeItems
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 402589594915277424L;
/*    */   private ContentTreeItemsId id;
/*    */ 
/*    */   public ContentTreeItemsId getId()
/*    */   {
/* 33 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(ContentTreeItemsId id) {
/* 37 */     this.id = id;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentTreeItems
 * JD-Core Version:    0.6.0
 */