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
/*     */ public class UserWhitelistDAO extends BaseHibernateDAO
/*     */ {
/*  18 */   private static final Log log = LogFactory.getLog(UserWhitelistDAO.class);
/*     */   public static final String ADDCAUSE = "addcause";
/*     */   public static final String ADDPERSON = "addperson";
/*     */ 
/*     */   public void save(UserWhitelist transientInstance)
/*     */   {
/*  26 */     log.debug("saving UserWhitelist instance");
/*     */     try {
/*  28 */       getSession().save(transientInstance);
/*  29 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  31 */       log.error("save failed", re);
/*  32 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(UserWhitelist persistentInstance) {
/*  37 */     log.debug("deleting UserWhitelist instance");
/*     */     try {
/*  39 */       getSession().delete(persistentInstance);
/*  40 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  42 */       log.error("delete failed", re);
/*  43 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserWhitelist findById(String id) {
/*  48 */     log.debug("getting UserWhitelist instance with id: " + id);
/*     */     try {
/*  50 */       UserWhitelist instance = (UserWhitelist)getSession()
/*  51 */         .get("com.aceway.common.db.entity.UserWhitelist", id);
/*  52 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  54 */       log.error("get failed", re);
/*  55 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(UserWhitelist instance)
/*     */   {
/*  61 */     log.debug("finding UserWhitelist instance by example");
/*     */     try {
/*  63 */       List results = getSession()
/*  64 */         .createCriteria("com.aceway.common.db.entity.UserWhitelist")
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
/*  76 */     log.debug("finding UserWhitelist instance with property: " + propertyName + 
/*  77 */       ", value: " + value);
/*     */     try {
/*  79 */       String queryString = "from UserWhitelist as model where model." + 
/*  80 */         propertyName + "= ?";
/*  81 */       Query queryObject = getSession().createQuery(queryString);
/*  82 */       queryObject.setParameter(0, value);
/*  83 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  85 */       log.error("find by property name failed", re);
/*  86 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByAddcause(Object addcause)
/*     */   {
/*  91 */     return findByProperty("addcause", addcause);
/*     */   }
/*     */ 
/*     */   public List findByAddperson(Object addperson) {
/*  95 */     return findByProperty("addperson", addperson);
/*     */   }
/*     */ 
/*     */   public UserWhitelist merge(UserWhitelist detachedInstance) {
/*  99 */     log.debug("merging UserWhitelist instance");
/*     */     try {
/* 101 */       UserWhitelist result = (UserWhitelist)getSession()
/* 102 */         .merge(detachedInstance);
/* 103 */       log.debug("merge successful");
/* 104 */       return result;
/*     */     } catch (RuntimeException re) {
/* 106 */       log.error("merge failed", re);
/* 107 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(UserWhitelist instance)
/*     */   {
/* 112 */     log.debug("attaching dirty UserWhitelist instance");
/*     */     try {
/* 114 */       getSession().saveOrUpdate(instance);
/* 115 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 117 */       log.error("attach failed", re);
/* 118 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(UserWhitelist instance) {
/* 123 */     log.debug("attaching clean UserWhitelist instance");
/*     */     try {
/* 125 */       getSession().lock(instance, LockMode.NONE);
/* 126 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 128 */       log.error("attach failed", re);
/* 129 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserWhitelistDAO
 * JD-Core Version:    0.6.0
 */