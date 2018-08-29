/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.LockMode;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.criterion.Example;
/*     */ 
/*     */ public class UserServHistoryDAO extends BaseHibernateDAO
/*     */ {
/*  19 */   private static final Log log = LogFactory.getLog(UserServHistoryDAO.class);
/*     */   public static final String CHARGEPARTY = "chargeparty";
/*     */ 
/*     */   public void save(UserServHistory transientInstance)
/*     */   {
/*  26 */     log.debug("saving UserServHistory instance");
/*     */     try {
/*  28 */       getSession().save(transientInstance);
/*  29 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  31 */       log.error("save failed", re);
/*  32 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(UserServHistory persistentInstance) {
/*  37 */     log.debug("deleting UserServHistory instance");
/*     */     try {
/*  39 */       getSession().delete(persistentInstance);
/*  40 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  42 */       log.error("delete failed", re);
/*  43 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserServHistory findById(UserServHistoryId id) {
/*  48 */     log.debug("getting UserServHistory instance with id: " + id);
/*     */     try {
/*  50 */       UserServHistory instance = (UserServHistory)getSession()
/*  51 */         .get("com.aceway.common.db.entity.UserServHistory", id);
/*  52 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  54 */       log.error("get failed", re);
/*  55 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(UserServHistory instance)
/*     */   {
/*  61 */     log.debug("finding UserServHistory instance by example");
/*     */     try {
/*  63 */       List results = getSession()
/*  64 */         .createCriteria("com.aceway.common.db.entity.UserServHistory")
/*  65 */         .add(Example.create(instance))
/*  66 */         .list();
/*  67 */       log.debug("find by example successful, result size: " + results.size());
/*  68 */       return results;
/*     */     } catch (RuntimeException re) {
/*  70 */       log.error("find by example failed", re);
/*  71 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  76 */     log.debug("finding UserServHistory instance with property: " + propertyName + 
/*  77 */       ", value: " + value);
/*     */     try {
/*  79 */       String queryString = "from UserServHistory as model where model." + 
/*  80 */         propertyName + "= ?";
/*  81 */       Query queryObject = getSession().createQuery(queryString);
/*  82 */       queryObject.setParameter(0, value);
/*  83 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  85 */       log.error("find by property name failed", re);
/*  86 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByChargeparty(Object chargeparty)
/*     */   {
/*  91 */     return findByProperty("chargeparty", chargeparty);
/*     */   }
/*     */ 
/*     */   public UserServHistory merge(UserServHistory detachedInstance) {
/*  95 */     log.debug("merging UserServHistory instance");
/*     */     try {
/*  97 */       UserServHistory result = (UserServHistory)getSession()
/*  98 */         .merge(detachedInstance);
/*  99 */       log.debug("merge successful");
/* 100 */       return result;
/*     */     } catch (RuntimeException re) {
/* 102 */       log.error("merge failed", re);
/* 103 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(UserServHistory instance)
/*     */   {
/* 108 */     log.debug("attaching dirty UserServHistory instance");
/*     */     try {
/* 110 */       getSession().saveOrUpdate(instance);
/* 111 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 113 */       log.error("attach failed", re);
/* 114 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(UserServHistory instance) {
/* 119 */     log.debug("attaching clean UserServHistory instance");
/*     */     try {
/* 121 */       getSession().lock(instance, LockMode.NONE);
/* 122 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 124 */       log.error("attach failed", re);
/* 125 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserServHistoryDAO
 * JD-Core Version:    0.6.0
 */