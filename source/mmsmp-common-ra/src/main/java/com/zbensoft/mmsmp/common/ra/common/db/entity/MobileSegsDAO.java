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
/*     */ public class MobileSegsDAO extends BaseHibernateDAO
/*     */ {
/*  13 */   private static final Log log = LogFactory.getLog(MobileSegsDAO.class);
/*     */   public static final String PROVINCECODE = "provincecode";
/*     */   public static final String STARTNO = "startno";
/*     */   public static final String ENDNO = "endno";
/*     */   public static final String DESCRIPTION = "description";
/*     */ 
/*     */   public void save(MobileSegs transientInstance)
/*     */   {
/*  24 */     log.debug("saving MobileSegs instance");
/*     */     try {
/*  26 */       getSession().save(transientInstance);
/*  27 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  29 */       log.error("save failed", re);
/*  30 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(MobileSegs persistentInstance) {
/*  35 */     log.debug("deleting MobileSegs instance");
/*     */     try {
/*  37 */       getSession().delete(persistentInstance);
/*  38 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  40 */       log.error("delete failed", re);
/*  41 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public MobileSegs findById(Integer id) {
/*  46 */     log.debug("getting MobileSegs instance with id: " + id);
/*     */     try {
/*  48 */       MobileSegs instance = (MobileSegs)getSession()
/*  49 */         .get("com.aceway.common.db.entity.MobileSegs", id);
/*  50 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  52 */       log.error("get failed", re);
/*  53 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(MobileSegs instance)
/*     */   {
/*  59 */     log.debug("finding MobileSegs instance by example");
/*     */     try {
/*  61 */       List results = getSession()
/*  62 */         .createCriteria("com.aceway.common.db.entity.MobileSegs")
/*  63 */         .add(Example.create(instance))
/*  64 */         .list();
/*  65 */       log.debug("find by example successful, result size: " + results.size());
/*  66 */       return results;
/*     */     } catch (RuntimeException re) {
/*  68 */       log.error("find by example failed", re);
/*  69 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  74 */     log.debug("finding MobileSegs instance with property: " + propertyName + 
/*  75 */       ", value: " + value);
/*     */     try {
/*  77 */       String queryString = "from MobileSegs as model where model." + 
/*  78 */         propertyName + "= ?";
/*  79 */       Query queryObject = getSession().createQuery(queryString);
/*  80 */       queryObject.setParameter(0, value);
/*  81 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  83 */       log.error("find by property name failed", re);
/*  84 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProvincecode(Object provincecode)
/*     */   {
/*  90 */     return findByProperty("provincecode", provincecode);
/*     */   }
/*     */ 
/*     */   public List findByStartno(Object startno)
/*     */   {
/*  96 */     return findByProperty("startno", startno);
/*     */   }
/*     */ 
/*     */   public List findByEndno(Object endno)
/*     */   {
/* 101 */     return findByProperty("endno", endno);
/*     */   }
/*     */ 
/*     */   public List findByDescription(Object description) {
/* 105 */     return findByProperty("description", description);
/*     */   }
/*     */ 
/*     */   public List findAll()
/*     */   {
/* 110 */     log.debug("finding all MobileSegs instances");
/*     */     try {
/* 112 */       String queryString = "from MobileSegs";
/* 113 */       Query queryObject = getSession().createQuery(queryString);
/* 114 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 116 */       log.error("find all failed", re);
/* 117 */     }throw re;
/*     */   }
/*     */ 
/*     */   public MobileSegs merge(MobileSegs detachedInstance)
/*     */   {
/* 122 */     log.debug("merging MobileSegs instance");
/*     */     try {
/* 124 */       MobileSegs result = (MobileSegs)getSession()
/* 125 */         .merge(detachedInstance);
/* 126 */       log.debug("merge successful");
/* 127 */       return result;
/*     */     } catch (RuntimeException re) {
/* 129 */       log.error("merge failed", re);
/* 130 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(MobileSegs instance)
/*     */   {
/* 135 */     log.debug("attaching dirty MobileSegs instance");
/*     */     try {
/* 137 */       getSession().saveOrUpdate(instance);
/* 138 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 140 */       log.error("attach failed", re);
/* 141 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(MobileSegs instance) {
/* 146 */     log.debug("attaching clean MobileSegs instance");
/*     */     try {
/* 148 */       getSession().lock(instance, LockMode.NONE);
/* 149 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 151 */       log.error("attach failed", re);
/* 152 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.MobileSegsDAO
 * JD-Core Version:    0.6.0
 */