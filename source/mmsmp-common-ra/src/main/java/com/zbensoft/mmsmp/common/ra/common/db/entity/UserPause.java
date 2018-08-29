/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class UserPause
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private Date createtime;
/*    */   private Integer serviceid;
/*    */   private String status;
/*    */   private Integer interval;
/*    */   private String tellphonenumber;
/*    */ 
/*    */   public Integer getId()
/*    */   {
/* 17 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 20 */     this.id = id;
/*    */   }
/*    */   public Integer getInterval() {
/* 23 */     return this.interval;
/*    */   }
/*    */   public void setInterval(Integer interval) {
/* 26 */     this.interval = interval;
/*    */   }
/*    */ 
/*    */   public Integer getServiceid() {
/* 30 */     return this.serviceid;
/*    */   }
/*    */   public void setServiceid(Integer serviceid) {
/* 33 */     this.serviceid = serviceid;
/*    */   }
/*    */   public String getStatus() {
/* 36 */     return this.status;
/*    */   }
/*    */   public void setStatus(String status) {
/* 39 */     this.status = status;
/*    */   }
/*    */   public String getTellphonenumber() {
/* 42 */     return this.tellphonenumber;
/*    */   }
/*    */   public void setTellphonenumber(String tellphonenumber) {
/* 45 */     this.tellphonenumber = tellphonenumber;
/*    */   }
/*    */ 
/*    */   public Date getCreatetime() {
/* 49 */     return this.createtime;
/*    */   }
/*    */ 
/*    */   public void setCreatetime(Date createtime) {
/* 53 */     this.createtime = createtime;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserPause
 * JD-Core Version:    0.6.0
 */