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
/*     */ public class AdBlacklietDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(AdBlacklietDAO.class);
/*     */   public static final String ADTYPE = "adtype";
/*     */   public static final String DESCRIPTION = "description";
/*     */ 
/*     */   public void save(AdBlackliet transientInstance)
/*     */   {
/*  25 */     log.debug("saving AdBlackliet instance");
/*     */     try {
/*  27 */       getSession().save(transientInstance);
/*  28 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  30 */       log.error("save failed", re);
/*  31 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(AdBlackliet persistentInstance) {
/*  36 */     log.debug("deleting AdBlackliet instance");
/*     */     try {
/*  38 */       getSession().delete(persistentInstance);
/*  39 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  41 */       log.error("delete failed", re);
/*  42 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public AdBlackliet findById(String id) {
/*  47 */     log.debug("getting AdBlackliet instance with id: " + id);
/*     */     try {
/*  49 */       AdBlackliet instance = (AdBlackliet)getSession()
/*  50 */         .get("com.aceway.common.db.entity.AdBlackliet", id);
/*  51 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  53 */       log.error("get failed", re);
/*  54 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(AdBlackliet instance)
/*     */   {
/*  60 */     log.debug("finding AdBlackliet instance by example");
/*     */     try {
/*  62 */       List results = getSession()
/*  63 */         .createCriteria("com.aceway.common.db.entity.AdBlackliet")
/*  64 */         .add(Example.create(instance))
/*  65 */         .list();
/*  66 */       log.debug("find by example successful, result size: " + results.size());
/*  67 */       return results;
/*     */     } catch (RuntimeException re) {
/*  69 */       log.error("find by example failed", re);
/*  70 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  75 */     log.debug("finding AdBlackliet instance with property: " + propertyName + 
/*  76 */       ", value: " + value);
/*     */     try {
/*  78 */       String queryString = "from AdBlackliet as model where model." + 
/*  79 */         propertyName + "= ?";
/*  80 */       Query queryObject = getSession().createQuery(queryString);
/*  81 */       queryObject.setParameter(0, value);
/*  82 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  84 */       log.error("find by property name failed", re);
/*  85 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByAdtype(Object adtype)
/*     */   {
/*  90 */     return findByProperty("adtype", adtype);
/*     */   }
/*     */ 
/*     */   public List findByDescription(Object description) {
/*  94 */     return findByProperty("description", description);
/*     */   }
/*     */ 
/*     */   public AdBlackliet merge(AdBlackliet detachedInstance) {
/*  98 */     log.debug("merging AdBlackliet instance");
/*     */     try {
/* 100 */       AdBlackliet result = (AdBlackliet)getSession()
/* 101 */         .merge(detachedInstance);
/* 102 */       log.debug("merge successful");
/* 103 */       return result;
/*     */     } catch (RuntimeException re) {
/* 105 */       log.error("merge failed", re);
/* 106 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(AdBlackliet instance)
/*     */   {
/* 111 */     log.debug("attaching dirty AdBlackliet instance");
/*     */     try {
/* 113 */       getSession().saveOrUpdate(instance);
/* 114 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 116 */       log.error("attach failed", re);
/* 117 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(AdBlackliet instance) {
/* 122 */     log.debug("attaching clean AdBlackliet instance");
/*     */     try {
/* 124 */       getSession().lock(instance, LockMode.NONE);
/* 125 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 127 */       log.error("attach failed", re);
/* 128 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.AdBlacklietDAO
 * JD-Core Version:    0.6.0
 */