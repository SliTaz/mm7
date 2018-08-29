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
/*     */ public class ContentRelationDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(ContentRelationDAO.class);
/*     */   public static final String CONTENTID = "contentid";
/*     */   public static final String CHILDCONTENTID = "childcontentid";
/*     */   public static final String ALLOTRATIO = "allotratio";
/*     */ 
/*     */   public void save(ContentRelation transientInstance)
/*     */   {
/*  26 */     log.debug("saving ContentRelation instance");
/*     */     try {
/*  28 */       getSession().save(transientInstance);
/*  29 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  31 */       log.error("save failed", re);
/*  32 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(ContentRelation persistentInstance) {
/*  37 */     log.debug("deleting ContentRelation instance");
/*     */     try {
/*  39 */       getSession().delete(persistentInstance);
/*  40 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  42 */       log.error("delete failed", re);
/*  43 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ContentRelation findById(Integer id) {
/*  48 */     log.debug("getting ContentRelation instance with id: " + id);
/*     */     try {
/*  50 */       ContentRelation instance = (ContentRelation)getSession()
/*  51 */         .get("com.aceway.common.db.entity.ContentRelation", id);
/*  52 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  54 */       log.error("get failed", re);
/*  55 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(ContentRelation instance)
/*     */   {
/*  61 */     log.debug("finding ContentRelation instance by example");
/*     */     try {
/*  63 */       List results = getSession()
/*  64 */         .createCriteria("com.aceway.common.db.entity.ContentRelation")
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
/*  76 */     log.debug("finding ContentRelation instance with property: " + propertyName + 
/*  77 */       ", value: " + value);
/*     */     try {
/*  79 */       String queryString = "from ContentRelation as model where model." + 
/*  80 */         propertyName + "= ?";
/*  81 */       Query queryObject = getSession().createQuery(queryString);
/*  82 */       queryObject.setParameter(0, value);
/*  83 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  85 */       log.error("find by property name failed", re);
/*  86 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByContentid(Object contentid)
/*     */   {
/*  91 */     return findByProperty("contentid", contentid);
/*     */   }
/*     */ 
/*     */   public List findByChildcontentid(Object childcontentid) {
/*  95 */     return findByProperty("childcontentid", childcontentid);
/*     */   }
/*     */ 
/*     */   public List findByAllotratio(Object allotratio) {
/*  99 */     return findByProperty("allotratio", allotratio);
/*     */   }
/*     */ 
/*     */   public ContentRelation merge(ContentRelation detachedInstance) {
/* 103 */     log.debug("merging ContentRelation instance");
/*     */     try {
/* 105 */       ContentRelation result = (ContentRelation)getSession()
/* 106 */         .merge(detachedInstance);
/* 107 */       log.debug("merge successful");
/* 108 */       return result;
/*     */     } catch (RuntimeException re) {
/* 110 */       log.error("merge failed", re);
/* 111 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(ContentRelation instance)
/*     */   {
/* 116 */     log.debug("attaching dirty ContentRelation instance");
/*     */     try {
/* 118 */       getSession().saveOrUpdate(instance);
/* 119 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 121 */       log.error("attach failed", re);
/* 122 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(ContentRelation instance) {
/* 127 */     log.debug("attaching clean ContentRelation instance");
/*     */     try {
/* 129 */       getSession().lock(instance, LockMode.NONE);
/* 130 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 132 */       log.error("attach failed", re);
/* 133 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentRelationDAO
 * JD-Core Version:    0.6.0
 */