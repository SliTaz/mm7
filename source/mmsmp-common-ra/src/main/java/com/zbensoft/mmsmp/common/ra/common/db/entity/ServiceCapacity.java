/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ServiceCapacity
/*    */   implements Serializable
/*    */ {
/*    */   private String uniqueid;
/*    */   private String name;
/*    */   private String capacity;
/*    */   private String status;
/*    */   private String spId;
/*    */   private String capacityId;
/*    */ 
/*    */   public String getCapacityId()
/*    */   {
/* 25 */     return this.capacityId;
/*    */   }
/*    */ 
/*    */   public void setCapacityId(String capacityId) {
/* 29 */     this.capacityId = capacityId;
/*    */   }
/*    */ 
/*    */   public ServiceCapacity()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ServiceCapacity(String name, String capacity, String status)
/*    */   {
/* 38 */     this.name = name;
/* 39 */     this.capacity = capacity;
/* 40 */     this.status = status;
/*    */   }
/*    */ 
/*    */   public ServiceCapacity(String name, String capacity, String status, String spId)
/*    */   {
/* 45 */     this.name = name;
/* 46 */     this.capacity = capacity;
/* 47 */     this.status = status;
/* 48 */     this.spId = spId;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 55 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 59 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getCapacity() {
/* 63 */     return this.capacity;
/*    */   }
/*    */ 
/*    */   public void setCapacity(String capacity) {
/* 67 */     this.capacity = capacity;
/*    */   }
/*    */ 
/*    */   public String getStatus() {
/* 71 */     return this.status;
/*    */   }
/*    */ 
/*    */   public void setStatus(String status) {
/* 75 */     this.status = status;
/*    */   }
/*    */ 
/*    */   public String getSpId() {
/* 79 */     return this.spId;
/*    */   }
/*    */ 
/*    */   public void setSpId(String spId) {
/* 83 */     this.spId = spId;
/*    */   }
/*    */ 
/*    */   public String getUniqueid() {
/* 87 */     return this.uniqueid;
/*    */   }
/*    */ 
/*    */   public void setUniqueid(String uniqueid) {
/* 91 */     this.uniqueid = uniqueid;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServiceCapacity
 * JD-Core Version:    0.6.0
 */