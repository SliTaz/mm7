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
/*     */ public class MediaSpecDAO extends BaseHibernateDAO
/*     */ {
/*  12 */   private static final Log log = LogFactory.getLog(MediaSpecDAO.class);
/*     */   public static final String MEDIATYPE = "mediatype";
/*     */   public static final String FILETYPE = "filetype";
/*     */   public static final String SPEC = "spec";
/*     */   public static final String MEDIAPRIORITY = "mediapriority";
/*     */ 
/*     */   public void save(MediaSpec transientInstance)
/*     */   {
/*  20 */     log.debug("saving MediaSpec instance");
/*     */     try {
/*  22 */       getSession().save(transientInstance);
/*  23 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  25 */       log.error("save failed", re);
/*  26 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(MediaSpec persistentInstance) {
/*  31 */     log.debug("deleting MediaSpec instance");
/*     */     try {
/*  33 */       getSession().delete(persistentInstance);
/*  34 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  36 */       log.error("delete failed", re);
/*  37 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public MediaSpec findById(Integer id) {
/*  42 */     log.debug("getting MediaSpec instance with id: " + id);
/*     */     try {
/*  44 */       MediaSpec instance = (MediaSpec)getSession().get(
/*  45 */         "com.aceway.common.db.entity.MediaSpec", id);
/*  46 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  48 */       log.error("get failed", re);
/*  49 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(MediaSpec instance)
/*     */   {
/*  54 */     log.debug("finding MediaSpec instance by example");
/*     */     try {
/*  56 */       List results = getSession().createCriteria(
/*  57 */         "com.aceway.common.db.entity.MediaSpec").add(
/*  58 */         Example.create(instance)).list();
/*  59 */       log.debug("find by example successful, result size: " + 
/*  60 */         results.size());
/*  61 */       return results;
/*     */     } catch (RuntimeException re) {
/*  63 */       log.error("find by example failed", re);
/*  64 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  69 */     log.debug("finding MediaSpec instance with property: " + propertyName + 
/*  70 */       ", value: " + value);
/*     */     try {
/*  72 */       String queryString = "from MediaSpec as model where model." + 
/*  73 */         propertyName + "= ?";
/*  74 */       Query queryObject = getSession().createQuery(queryString);
/*  75 */       queryObject.setParameter(0, value);
/*  76 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  78 */       log.error("find by property name failed", re);
/*  79 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByIds(String mediaSpecIds) {
/*  83 */     log.debug("finding all MediaSpec instances");
/*     */     try {
/*  85 */       StringBuffer queryStrBuf = new StringBuffer("from MediaSpec where mediaspecid in(" + mediaSpecIds);
/*  86 */       queryStrBuf.append(")");
/*  87 */       Query queryObject = getSession().createQuery(queryStrBuf.toString());
/*  88 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  90 */       log.error("find all failed", re);
/*  91 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByMediatype(Object mediatype)
/*     */   {
/*  96 */     return findByProperty("mediatype", mediatype);
/*     */   }
/*     */ 
/*     */   public List findByFiletype(Object filetype) {
/* 100 */     return findByProperty("filetype", filetype);
/*     */   }
/*     */ 
/*     */   public List findBySpec(Object spec) {
/* 104 */     return findByProperty("spec", spec);
/*     */   }
/*     */ 
/*     */   public List findByMediapriority(Object mediapriority) {
/* 108 */     return findByProperty("mediapriority", mediapriority);
/*     */   }
/*     */ 
/*     */   public List findAll() {
/* 112 */     log.debug("finding all MediaSpec instances");
/*     */     try {
/* 114 */       String queryString = "from MediaSpec";
/* 115 */       Query queryObject = getSession().createQuery(queryString);
/* 116 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 118 */       log.error("find all failed", re);
/* 119 */     }throw re;
/*     */   }
/*     */ 
/*     */   public MediaSpec merge(MediaSpec detachedInstance)
/*     */   {
/* 124 */     log.debug("merging MediaSpec instance");
/*     */     try {
/* 126 */       MediaSpec result = (MediaSpec)getSession().merge(detachedInstance);
/* 127 */       log.debug("merge successful");
/* 128 */       return result;
/*     */     } catch (RuntimeException re) {
/* 130 */       log.error("merge failed", re);
/* 131 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(MediaSpec instance)
/*     */   {
/* 136 */     log.debug("attaching dirty MediaSpec instance");
/*     */     try {
/* 138 */       getSession().saveOrUpdate(instance);
/* 139 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 141 */       log.error("attach failed", re);
/* 142 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(MediaSpec instance) {
/* 147 */     log.debug("attaching clean MediaSpec instance");
/*     */     try {
/* 149 */       getSession().lock(instance, LockMode.NONE);
/* 150 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 152 */       log.error("attach failed", re);
/* 153 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.MediaSpecDAO
 * JD-Core Version:    0.6.0
 */