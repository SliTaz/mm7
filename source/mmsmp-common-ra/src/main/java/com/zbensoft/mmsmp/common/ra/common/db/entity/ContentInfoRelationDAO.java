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
/*     */ public class ContentInfoRelationDAO extends BaseHibernateDAO
/*     */ {
/*  24 */   private static final Log log = LogFactory.getLog(ContentInfoRelationDAO.class);
/*     */   public static final String FIZESIZE = "fizesize";
/*     */   public static final String CONTENTPATH = "contentpath";
/*     */ 
/*     */   public void save(ContentInfoRelation transientInstance)
/*     */   {
/*  30 */     log.debug("saving ContentInfoRelation instance");
/*     */     try {
/*  32 */       getSession().save(transientInstance);
/*  33 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  35 */       log.error("save failed", re);
/*  36 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(ContentInfoRelation persistentInstance) {
/*  41 */     log.debug("deleting ContentInfoRelation instance");
/*     */     try {
/*  43 */       getSession().delete(persistentInstance);
/*  44 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  46 */       log.error("delete failed", re);
/*  47 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ContentInfoRelation findById(ContentInfoRelationId id) {
/*  52 */     log.debug("getting ContentInfoRelation instance with id: " + id);
/*     */     try {
/*  54 */       ContentInfoRelation instance = (ContentInfoRelation)getSession()
/*  55 */         .get("com.aceway.common.db.entity.ContentInfoRelation", id);
/*  56 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  58 */       log.error("get failed", re);
/*  59 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(ContentInfoRelation instance)
/*     */   {
/*  64 */     log.debug("finding ContentInfoRelation instance by example");
/*     */     try {
/*  66 */       List results = getSession().createCriteria(
/*  67 */         "com.aceway.common.db.entity.ContentInfoRelation").add(
/*  68 */         Example.create(instance)).list();
/*  69 */       log.debug("find by example successful, result size: " + 
/*  70 */         results.size());
/*  71 */       return results;
/*     */     } catch (RuntimeException re) {
/*  73 */       log.error("find by example failed", re);
/*  74 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  79 */     log.debug("finding ContentInfoRelation instance with property: " + 
/*  80 */       propertyName + ", value: " + value);
/*     */     try {
/*  82 */       String queryString = "from ContentInfoRelation as model where model." + 
/*  83 */         propertyName + "= ?";
/*  84 */       Query queryObject = getSession().createQuery(queryString);
/*  85 */       queryObject.setParameter(0, value);
/*  86 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  88 */       log.error("find by property name failed", re);
/*  89 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByFizesize(Object fizesize)
/*     */   {
/*  94 */     return findByProperty("fizesize", fizesize);
/*     */   }
/*     */ 
/*     */   public List findByContentpath(Object contentpath) {
/*  98 */     return findByProperty("contentpath", contentpath);
/*     */   }
/*     */ 
/*     */   public List findAll() {
/* 102 */     log.debug("finding all ContentInfoRelation instances");
/*     */     try {
/* 104 */       String queryString = "from ContentInfoRelation";
/* 105 */       Query queryObject = getSession().createQuery(queryString);
/* 106 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 108 */       log.error("find all failed", re);
/* 109 */     }throw re;
/*     */   }
/*     */ 
/*     */   public ContentInfoRelation merge(ContentInfoRelation detachedInstance)
/*     */   {
/* 114 */     log.debug("merging ContentInfoRelation instance");
/*     */     try {
/* 116 */       ContentInfoRelation result = (ContentInfoRelation)getSession()
/* 117 */         .merge(detachedInstance);
/* 118 */       log.debug("merge successful");
/* 119 */       return result;
/*     */     } catch (RuntimeException re) {
/* 121 */       log.error("merge failed", re);
/* 122 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(ContentInfoRelation instance)
/*     */   {
/* 127 */     log.debug("attaching dirty ContentInfoRelation instance");
/*     */     try {
/* 129 */       getSession().saveOrUpdate(instance);
/* 130 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 132 */       log.error("attach failed", re);
/* 133 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(ContentInfoRelation instance) {
/* 138 */     log.debug("attaching clean ContentInfoRelation instance");
/*     */     try {
/* 140 */       getSession().lock(instance, LockMode.NONE);
/* 141 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 143 */       log.error("attach failed", re);
/* 144 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentInfoRelationDAO
 * JD-Core Version:    0.6.0
 */