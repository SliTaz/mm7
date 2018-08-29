/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class UserTestlist
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 6601870696744967454L;
/*    */   private String cellphonenumber;
/*    */   private Integer vasuniqueid;
/*    */   private Date adddate;
/*    */   private String addcause;
/*    */   private String addperson;
/*    */ 
/*    */   public UserTestlist()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UserTestlist(Integer vasuniqueid, Date adddate)
/*    */   {
/* 34 */     this.vasuniqueid = vasuniqueid;
/* 35 */     this.adddate = adddate;
/*    */   }
/*    */ 
/*    */   public UserTestlist(Integer vasuniqueid, Date adddate, String addcause, String addperson)
/*    */   {
/* 40 */     this.vasuniqueid = vasuniqueid;
/* 41 */     this.adddate = adddate;
/* 42 */     this.addcause = addcause;
/* 43 */     this.addperson = addperson;
/*    */   }
/*    */ 
/*    */   public String getCellphonenumber()
/*    */   {
/* 50 */     return this.cellphonenumber;
/*    */   }
/*    */ 
/*    */   public void setCellphonenumber(String cellphonenumber) {
/* 54 */     this.cellphonenumber = cellphonenumber;
/*    */   }
/*    */ 
/*    */   public Integer getVasuniqueid() {
/* 58 */     return this.vasuniqueid;
/*    */   }
/*    */ 
/*    */   public void setVasuniqueid(Integer vasuniqueid) {
/* 62 */     this.vasuniqueid = vasuniqueid;
/*    */   }
/*    */ 
/*    */   public Date getAdddate() {
/* 66 */     return this.adddate;
/*    */   }
/*    */ 
/*    */   public void setAdddate(Date adddate) {
/* 70 */     this.adddate = adddate;
/*    */   }
/*    */ 
/*    */   public String getAddcause() {
/* 74 */     return this.addcause;
/*    */   }
/*    */ 
/*    */   public void setAddcause(String addcause) {
/* 78 */     this.addcause = addcause;
/*    */   }
/*    */ 
/*    */   public String getAddperson() {
/* 82 */     return this.addperson;
/*    */   }
/*    */ 
/*    */   public void setAddperson(String addperson) {
/* 86 */     this.addperson = addperson;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserTestlist
 * JD-Core Version:    0.6.0
 */