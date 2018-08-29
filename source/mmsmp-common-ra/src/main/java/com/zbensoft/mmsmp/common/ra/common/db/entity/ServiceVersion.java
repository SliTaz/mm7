/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class ServiceVersion
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private Integer serviceId;
/*    */   private String name;
/*    */   private String cod;
/*    */   private String description;
/*    */   private Date createTime;
/*    */ 
/*    */   public Date getCreateTime()
/*    */   {
/* 23 */     return this.createTime;
/*    */   }
/*    */ 
/*    */   public void setCreateTime(Date createTime) {
/* 27 */     this.createTime = createTime;
/*    */   }
/*    */ 
/*    */   public String getCod() {
/* 31 */     return this.cod;
/*    */   }
/*    */ 
/*    */   public void setCod(String cod) {
/* 35 */     this.cod = cod;
/*    */   }
/*    */ 
/*    */   public Integer getId() {
/* 39 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(Integer id) {
/* 43 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 47 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 51 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public Integer getServiceId() {
/* 55 */     return this.serviceId;
/*    */   }
/*    */ 
/*    */   public void setServiceId(Integer serviceId) {
/* 59 */     this.serviceId = serviceId;
/*    */   }
/*    */ 
/*    */   public String getDescription() {
/* 63 */     return this.description;
/*    */   }
/*    */ 
/*    */   public void setDescription(String description) {
/* 67 */     this.description = description;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServiceVersion
 * JD-Core Version:    0.6.0
 */