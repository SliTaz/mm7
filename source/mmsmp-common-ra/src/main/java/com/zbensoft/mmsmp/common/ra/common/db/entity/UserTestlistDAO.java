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
/*     */ public class UserTestlistDAO extends BaseHibernateDAO
/*     */ {
/*  18 */   private static final Log log = LogFactory.getLog(UserTestlistDAO.class);
/*     */   public static final String VASUNIQUEID = "vasuniqueid";
/*     */   public static final String ADDCAUSE = "addcause";
/*     */   public static final String ADDPERSON = "addperson";
/*     */ 
/*     */   public void save(UserTestlist transientInstance)
/*     */   {
/*  27 */     log.debug("saving UserTestlist instance");
/*     */     try {
/*  29 */       getSession().save(transientInstance);
/*  30 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  32 */       log.error("save failed", re);
/*  33 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(UserTestlist persistentInstance) {
/*  38 */     log.debug("deleting UserTestlist instance");
/*     */     try {
/*  40 */       getSession().delete(persistentInstance);
/*  41 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  43 */       log.error("delete failed", re);
/*  44 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserTestlist findById(String id) {
/*  49 */     log.debug("getting UserTestlist instance with id: " + id);
/*     */     try {
/*  51 */       UserTestlist instance = (UserTestlist)getSession()
/*  52 */         .get("com.aceway.common.db.entity.UserTestlist", id);
/*  53 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  55 */       log.error("get failed", re);
/*  56 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(UserTestlist instance)
/*     */   {
/*  62 */     log.debug("finding UserTestlist instance by example");
/*     */     try {
/*  64 */       List results = getSession()
/*  65 */         .createCriteria("com.aceway.common.db.entity.UserTestlist")
/*  66 */         .add(Example.create(instance))
/*  67 */         .list();
/*  68 */       log.debug("find by example successful, result size: " + results.size());
/*  69 */       return results;
/*     */     } catch (RuntimeException re) {
/*  71 */       log.error("find by example failed", re);
/*  72 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  77 */     log.debug("finding UserTestlist instance with property: " + propertyName + 
/*  78 */       ", value: " + value);
/*     */     try {
/*  80 */       String queryString = "from UserTestlist as model where model." + 
/*  81 */         propertyName + "= ?";
/*  82 */       Query queryObject = getSession().createQuery(queryString);
/*  83 */       queryObject.setParameter(0, value);
/*  84 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  86 */       log.error("find by property name failed", re);
/*  87 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByVasuniqueid(Object vasuniqueid)
/*     */   {
/*  92 */     return findByProperty("vasuniqueid", vasuniqueid);
/*     */   }
/*     */ 
/*     */   public List findByAddcause(Object addcause) {
/*  96 */     return findByProperty("addcause", addcause);
/*     */   }
/*     */ 
/*     */   public List findByAddperson(Object addperson) {
/* 100 */     return findByProperty("addperson", addperson);
/*     */   }
/*     */ 
/*     */   public UserTestlist merge(UserTestlist detachedInstance) {
/* 104 */     log.debug("merging UserTestlist instance");
/*     */     try {
/* 106 */       UserTestlist result = (UserTestlist)getSession()
/* 107 */         .merge(detachedInstance);
/* 108 */       log.debug("merge successful");
/* 109 */       return result;
/*     */     } catch (RuntimeException re) {
/* 111 */       log.error("merge failed", re);
/* 112 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(UserTestlist instance)
/*     */   {
/* 117 */     log.debug("attaching dirty UserTestlist instance");
/*     */     try {
/* 119 */       getSession().saveOrUpdate(instance);
/* 120 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 122 */       log.error("attach failed", re);
/* 123 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(UserTestlist instance) {
/* 128 */     log.debug("attaching clean UserTestlist instance");
/*     */     try {
/* 130 */       getSession().lock(instance, LockMode.NONE);
/* 131 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 133 */       log.error("attach failed", re);
/* 134 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserTestlistDAO
 * JD-Core Version:    0.6.0
 */