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
/*     */ public class SysParametersDAO extends BaseHibernateDAO
/*     */ {
/*  23 */   private static final Log log = LogFactory.getLog(SysParametersDAO.class);
/*     */   public static final String VALUE = "value";
/*     */   public static final String DESCRIPTION = "description";
/*     */ 
/*     */   public void save(SysParameters transientInstance)
/*     */   {
/*  29 */     log.debug("saving SysParameters instance");
/*     */     try {
/*  31 */       getSession().save(transientInstance);
/*  32 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  34 */       log.error("save failed", re);
/*  35 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(SysParameters persistentInstance) {
/*  40 */     log.debug("deleting SysParameters instance");
/*     */     try {
/*  42 */       getSession().delete(persistentInstance);
/*  43 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  45 */       log.error("delete failed", re);
/*  46 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public SysParameters findById(String id) {
/*  51 */     log.debug("getting SysParameters instance with id: " + id);
/*     */     try {
/*  53 */       SysParameters instance = (SysParameters)getSession().get(
/*  54 */         "com.aceway.common.db.test.SysParameters", id);
/*  55 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  57 */       log.error("get failed", re);
/*  58 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(SysParameters instance)
/*     */   {
/*  63 */     log.debug("finding SysParameters instance by example");
/*     */     try {
/*  65 */       List results = getSession().createCriteria(
/*  66 */         "com.aceway.common.db.test.SysParameters").add(
/*  67 */         Example.create(instance)).list();
/*  68 */       log.debug("find by example successful, result size: " + 
/*  69 */         results.size());
/*  70 */       return results;
/*     */     } catch (RuntimeException re) {
/*  72 */       log.error("find by example failed", re);
/*  73 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  78 */     log.debug("finding SysParameters instance with property: " + 
/*  79 */       propertyName + ", value: " + value);
/*     */     try {
/*  81 */       String queryString = "from SysParameters as model where model." + 
/*  82 */         propertyName + "= ?";
/*  83 */       Query queryObject = getSession().createQuery(queryString);
/*  84 */       queryObject.setParameter(0, value);
/*  85 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  87 */       log.error("find by property name failed", re);
/*  88 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByValue(Object value)
/*     */   {
/*  93 */     return findByProperty("value", value);
/*     */   }
/*     */ 
/*     */   public List findByDescription(Object description) {
/*  97 */     return findByProperty("description", description);
/*     */   }
/*     */ 
/*     */   public List findAll() {
/* 101 */     log.debug("finding all SysParameters instances");
/*     */     try {
/* 103 */       String queryString = "from SysParameters";
/* 104 */       Query queryObject = getSession().createQuery(queryString);
/* 105 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 107 */       log.error("find all failed", re);
/* 108 */     }throw re;
/*     */   }
/*     */ 
/*     */   public SysParameters merge(SysParameters detachedInstance)
/*     */   {
/* 113 */     log.debug("merging SysParameters instance");
/*     */     try {
/* 115 */       SysParameters result = (SysParameters)getSession().merge(
/* 116 */         detachedInstance);
/* 117 */       log.debug("merge successful");
/* 118 */       return result;
/*     */     } catch (RuntimeException re) {
/* 120 */       log.error("merge failed", re);
/* 121 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(SysParameters instance)
/*     */   {
/* 126 */     log.debug("attaching dirty SysParameters instance");
/*     */     try {
/* 128 */       getSession().saveOrUpdate(instance);
/* 129 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 131 */       log.error("attach failed", re);
/* 132 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(SysParameters instance) {
/* 137 */     log.debug("attaching clean SysParameters instance");
/*     */     try {
/* 139 */       getSession().lock(instance, LockMode.NONE);
/* 140 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 142 */       log.error("attach failed", re);
/* 143 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.SysParametersDAO
 * JD-Core Version:    0.6.0
 */