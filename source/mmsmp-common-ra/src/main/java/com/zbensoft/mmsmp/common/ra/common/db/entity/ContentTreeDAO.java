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
/*     */ public class ContentTreeDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(ContentTreeDAO.class);
/*     */   public static final String PARENTID = "parentid";
/*     */   public static final String ISLEAF = "isleaf";
/*     */   public static final String NAME = "name";
/*     */   public static final String DESCRIPTION = "description";
/*     */   public static final String CREATEDATE = "createdate";
/*     */   public static final String UPDATEDATE = "updatedate";
/*     */   public static final String ALIAS = "alias";
/*     */ 
/*     */   public void save(ContentTree transientInstance)
/*     */   {
/*  30 */     log.debug("saving ContentTree instance");
/*     */     try {
/*  32 */       getSession().save(transientInstance);
/*  33 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  35 */       log.error("save failed", re);
/*  36 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(ContentTree persistentInstance) {
/*  41 */     log.debug("deleting ContentTree instance");
/*     */     try {
/*  43 */       getSession().delete(persistentInstance);
/*  44 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  46 */       log.error("delete failed", re);
/*  47 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ContentTree findById(String id) {
/*  52 */     log.debug("getting ContentTree instance with id: " + id);
/*     */     try {
/*  54 */       ContentTree instance = (ContentTree)getSession()
/*  55 */         .get("com.aceway.common.db.entity.ContentTree", id);
/*  56 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  58 */       log.error("get failed", re);
/*  59 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findAll()
/*     */   {
/*     */     try
/*     */     {
/*  66 */       String queryString = "from ContentTree as model";
/*  67 */       Query queryObject = getSession().createQuery(queryString);
/*  68 */       return queryObject.list();
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  72 */       log.error("find by property name failed", re);
/*  73 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(ContentTree instance)
/*     */   {
/*  78 */     log.debug("finding ContentTree instance by example");
/*     */     try {
/*  80 */       List results = getSession()
/*  81 */         .createCriteria("com.aceway.common.db.entity.ContentTree")
/*  82 */         .add(Example.create(instance))
/*  83 */         .list();
/*  84 */       log.debug("find by example successful, result size: " + results.size());
/*  85 */       return results;
/*     */     } catch (RuntimeException re) {
/*  87 */       log.error("find by example failed", re);
/*  88 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  93 */     log.debug("finding ContentTree instance with property: " + propertyName + 
/*  94 */       ", value: " + value);
/*     */     try {
/*  96 */       String queryString = "from ContentTree as model where model." + 
/*  97 */         propertyName + "= ?";
/*  98 */       Query queryObject = getSession().createQuery(queryString);
/*  99 */       queryObject.setParameter(0, value);
/* 100 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 102 */       log.error("find by property name failed", re);
/* 103 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByParentid(Object parentid)
/*     */   {
/* 108 */     return findByProperty("parentid", parentid);
/*     */   }
/*     */ 
/*     */   public List findByIsleaf(Object isleaf) {
/* 112 */     return findByProperty("isleaf", isleaf);
/*     */   }
/*     */ 
/*     */   public List findByName(Object name) {
/* 116 */     return findByProperty("name", name);
/*     */   }
/*     */ 
/*     */   public List findByDescription(Object description) {
/* 120 */     return findByProperty("description", description);
/*     */   }
/*     */ 
/*     */   public List findByCreatedate(Object createdate) {
/* 124 */     return findByProperty("createdate", createdate);
/*     */   }
/*     */ 
/*     */   public List findByUpdatedate(Object updatedate) {
/* 128 */     return findByProperty("updatedate", updatedate);
/*     */   }
/*     */ 
/*     */   public List findByAlias(Object alias) {
/* 132 */     return findByProperty("alias", alias);
/*     */   }
/*     */ 
/*     */   public ContentTree merge(ContentTree detachedInstance) {
/* 136 */     log.debug("merging ContentTree instance");
/*     */     try {
/* 138 */       ContentTree result = (ContentTree)getSession()
/* 139 */         .merge(detachedInstance);
/* 140 */       log.debug("merge successful");
/* 141 */       return result;
/*     */     } catch (RuntimeException re) {
/* 143 */       log.error("merge failed", re);
/* 144 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(ContentTree instance)
/*     */   {
/* 149 */     log.debug("attaching dirty ContentTree instance");
/*     */     try {
/* 151 */       getSession().saveOrUpdate(instance);
/* 152 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 154 */       log.error("attach failed", re);
/* 155 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(ContentTree instance) {
/* 160 */     log.debug("attaching clean ContentTree instance");
/*     */     try {
/* 162 */       getSession().lock(instance, LockMode.NONE);
/* 163 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 165 */       log.error("attach failed", re);
/* 166 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentTreeDAO
 * JD-Core Version:    0.6.0
 */