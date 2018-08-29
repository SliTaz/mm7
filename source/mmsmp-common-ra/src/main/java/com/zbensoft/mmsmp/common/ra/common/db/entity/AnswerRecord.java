/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class AnswerRecord
/*    */ {
/*    */   private int answerId;
/*    */   private int serviceId;
/*    */   private int contentId;
/*    */   private Date beginDate;
/*    */   private Date endDate;
/*    */   private String answer;
/*    */ 
/*    */   public int getAnswerId()
/*    */   {
/* 20 */     return this.answerId;
/*    */   }
/*    */   public void setAnswerId(int answerId) {
/* 23 */     this.answerId = answerId;
/*    */   }
/*    */   public int getServiceId() {
/* 26 */     return this.serviceId;
/*    */   }
/*    */   public void setServiceId(int serviceId) {
/* 29 */     this.serviceId = serviceId;
/*    */   }
/*    */   public int getContentId() {
/* 32 */     return this.contentId;
/*    */   }
/*    */   public void setContentId(int contentId) {
/* 35 */     this.contentId = contentId;
/*    */   }
/*    */   public Date getBeginDate() {
/* 38 */     return this.beginDate;
/*    */   }
/*    */   public void setBeginDate(Date beginDate) {
/* 41 */     this.beginDate = beginDate;
/*    */   }
/*    */   public Date getEndDate() {
/* 44 */     return this.endDate;
/*    */   }
/*    */   public void setEndDate(Date endDate) {
/* 47 */     this.endDate = endDate;
/*    */   }
/*    */   public String getAnswer() {
/* 50 */     return this.answer;
/*    */   }
/*    */   public void setAnswer(String answer) {
/* 53 */     this.answer = answer;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.AnswerRecord
 * JD-Core Version:    0.6.0
 */