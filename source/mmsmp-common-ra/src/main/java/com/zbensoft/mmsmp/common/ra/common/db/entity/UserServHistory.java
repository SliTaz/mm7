/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class UserServHistory
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5013293177053107868L;
/*    */   private UserServHistoryId id;
/*    */   private String chargeparty;
/*    */   private Date usetime;
/*    */ 
/*    */   public UserServHistory()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UserServHistory(UserServHistoryId id)
/*    */   {
/* 32 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public UserServHistory(UserServHistoryId id, String chargeparty, Date usetime)
/*    */   {
/* 37 */     this.id = id;
/* 38 */     this.chargeparty = chargeparty;
/* 39 */     this.usetime = usetime;
/*    */   }
/*    */ 
/*    */   public UserServHistoryId getId()
/*    */   {
/* 46 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(UserServHistoryId id) {
/* 50 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getChargeparty() {
/* 54 */     return this.chargeparty;
/*    */   }
/*    */ 
/*    */   public void setChargeparty(String chargeparty) {
/* 58 */     this.chargeparty = chargeparty;
/*    */   }
/*    */ 
/*    */   public Date getUsetime() {
/* 62 */     return this.usetime;
/*    */   }
/*    */ 
/*    */   public void setUsetime(Date usetime) {
/* 66 */     this.usetime = usetime;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserServHistory
 * JD-Core Version:    0.6.0
 */