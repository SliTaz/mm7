/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class UserWhitelist
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1673599009210435073L;
/*    */   private String cellphonenumber;
/*    */   private Date adddate;
/*    */   private String addcause;
/*    */   private String addperson;
/*    */ 
/*    */   public UserWhitelist()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UserWhitelist(Date adddate)
/*    */   {
/* 33 */     this.adddate = adddate;
/*    */   }
/*    */ 
/*    */   public UserWhitelist(Date adddate, String addcause, String addperson)
/*    */   {
/* 38 */     this.adddate = adddate;
/* 39 */     this.addcause = addcause;
/* 40 */     this.addperson = addperson;
/*    */   }
/*    */ 
/*    */   public String getCellphonenumber()
/*    */   {
/* 47 */     return this.cellphonenumber;
/*    */   }
/*    */ 
/*    */   public void setCellphonenumber(String cellphonenumber) {
/* 51 */     this.cellphonenumber = cellphonenumber;
/*    */   }
/*    */ 
/*    */   public Date getAdddate() {
/* 55 */     return this.adddate;
/*    */   }
/*    */ 
/*    */   public void setAdddate(Date adddate) {
/* 59 */     this.adddate = adddate;
/*    */   }
/*    */ 
/*    */   public String getAddcause() {
/* 63 */     return this.addcause;
/*    */   }
/*    */ 
/*    */   public void setAddcause(String addcause) {
/* 67 */     this.addcause = addcause;
/*    */   }
/*    */ 
/*    */   public String getAddperson() {
/* 71 */     return this.addperson;
/*    */   }
/*    */ 
/*    */   public void setAddperson(String addperson) {
/* 75 */     this.addperson = addperson;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserWhitelist
 * JD-Core Version:    0.6.0
 */