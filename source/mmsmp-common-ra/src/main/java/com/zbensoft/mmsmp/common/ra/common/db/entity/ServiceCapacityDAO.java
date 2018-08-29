/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/*     */ 
/*     */ public class ServiceCapacityDAO extends HibernateDaoSupport
/*     */ {
/*     */   public void updateAllByCapacity(String id, String status)
/*     */   {
/*  12 */     Query query = getSession().createQuery("update ServiceCapacity set status=:status where capacityId=:id");
/*  13 */     query.setString("status", status);
/*  14 */     query.setString("id", id);
/*  15 */     query.executeUpdate();
/*  16 */     query = getSession().createQuery("update Vas set status=:status where capacityId in (select uniqueid from ServiceCapacity where capacityId=:id)");
/*  17 */     query.setString("status", status);
/*  18 */     query.setString("id", id);
/*  19 */     query.executeUpdate();
/*  20 */     String oldStatus = "";
/*  21 */     switch (status.charAt(0)) {
/*     */     case '0':
/*  23 */       oldStatus = "2";
/*  24 */       break;
/*     */     case '2':
/*  26 */       oldStatus = "3";
/*  27 */       break;
/*     */     case '3':
/*  29 */       oldStatus = "5";
/*     */     case '1':
/*     */     }
/*     */ 
/*  33 */     query = getSession().createQuery("update Vasservice set status=:status where vasid in (select vasid from Vas where capacityId in (select uniqueid from ServiceCapacity where capacityId=:id) )");
/*  34 */     query.setString("status", oldStatus);
/*  35 */     query.setString("id", id);
/*  36 */     query.executeUpdate();
/*  37 */     if ("4".equals(status)) {
/*  38 */       query = getSession().createQuery("delete from UserOrder where serviceuniqueid in (select uniqueid from Vasservice where vasid in (select vasid from Vas where capacityId in (select uniqueid from ServiceCapacity where capacityId=:id)))");
/*  39 */       query.setString("id", id);
/*  40 */       query.executeUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateAllByVas(String id, String status)
/*     */   {
/*  47 */     String oldStatus = "";
/*  48 */     switch (status.charAt(0)) {
/*     */     case '0':
/*  50 */       oldStatus = "2";
/*  51 */       break;
/*     */     case '2':
/*  53 */       oldStatus = "3";
/*  54 */       break;
/*     */     case '3':
/*  56 */       oldStatus = "5";
/*     */     case '1':
/*     */     }
/*  59 */     Query query = getSession().createQuery("update Vas set status=:status where vasIsmpId=:id");
/*  60 */     query.setString("status", oldStatus);
/*  61 */     query.setString("id", id);
/*  62 */     query.executeUpdate();
/*     */ 
/*  64 */     query = getSession().createQuery("update Vasservice set status=:status where vasid in (select vasid from Vas where vasIsmpId=:id )");
/*  65 */     query.setString("status", oldStatus);
/*  66 */     query.setString("id", id);
/*  67 */     query.executeUpdate();
/*  68 */     if ("4".equals(status)) {
/*  69 */       query = getSession().createQuery("delete from UserOrder where serviceuniqueid in (select uniqueid from Vasservice where vasid in (select vasid from Vas where vasIsmpId=:id ))");
/*  70 */       query.setString("id", id);
/*  71 */       query.executeUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateAllByVasService(Integer id, String status)
/*     */   {
/*  80 */     String oldStatus = "";
/*  81 */     switch (status.charAt(0)) {
/*     */     case '0':
/*  83 */       oldStatus = "2";
/*  84 */       break;
/*     */     case '2':
/*  86 */       oldStatus = "3";
/*  87 */       break;
/*     */     case '3':
/*  89 */       oldStatus = "5";
/*     */     case '1':
/*     */     }
/*  92 */     Query query = getSession().createQuery("update Vasservice set status=:status where uniqueid=:id");
/*  93 */     query.setString("status", oldStatus);
/*  94 */     query.setInteger("id", id.intValue());
/*  95 */     query.executeUpdate();
/*  96 */     if ("4".equals(status)) {
/*  97 */       query = getSession().createQuery("delete from UserOrder where serviceuniqueid=:id");
/*  98 */       query.setInteger("id", id.intValue());
/*  99 */       query.executeUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ServiceCapacity findCapacityId(String id)
/*     */   {
/* 108 */     String queryString = "from ServiceCapacity as model where model.capacityId=?";
/* 109 */     Query queryObject = getSession().createQuery(queryString);
/* 110 */     queryObject.setParameter(0, id);
/*     */     try {
/* 112 */       return (ServiceCapacity)queryObject.list().get(0); } catch (Exception e) {
/*     */     }
/* 114 */     return null;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServiceCapacityDAO
 * JD-Core Version:    0.6.0
 */