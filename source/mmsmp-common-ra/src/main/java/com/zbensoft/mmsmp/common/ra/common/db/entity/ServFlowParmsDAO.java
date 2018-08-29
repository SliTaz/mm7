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
/*     */ public class ServFlowParmsDAO extends BaseHibernateDAO
/*     */ {
/*  19 */   private static final Log log = LogFactory.getLog(ServFlowParmsDAO.class);
/*     */   public static final String PARMVALUE = "parmvalue";
/*     */ 
/*     */   public void save(ServFlowParms transientInstance)
/*     */   {
/*  29 */     log.debug("saving ServFlowParms instance");
/*     */     try {
/*  31 */       getSession().save(transientInstance);
/*  32 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  34 */       log.error("save failed", re);
/*  35 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(ServFlowParms persistentInstance)
/*     */   {
/*  44 */     log.debug("deleting ServFlowParms instance");
/*     */     try {
/*  46 */       getSession().delete(persistentInstance);
/*  47 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  49 */       log.error("delete failed", re);
/*  50 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ServFlowParms findById(ServFlowParmsId id)
/*     */   {
/*  60 */     log.debug("getting ServFlowParms instance with id: " + id);
/*     */     try {
/*  62 */       ServFlowParms instance = (ServFlowParms)getSession()
/*  63 */         .get("com.aceway.common.db.entity.ServFlowParms", id);
/*  64 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  66 */       log.error("get failed", re);
/*  67 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(ServFlowParms instance)
/*     */   {
/*  77 */     log.debug("finding ServFlowParms instance by example");
/*     */     try {
/*  79 */       List results = getSession()
/*  80 */         .createCriteria("com.aceway.common.db.entity.ServFlowParms")
/*  81 */         .add(Example.create(instance))
/*  82 */         .list();
/*  83 */       log.debug("find by example successful, result size: " + results.size());
/*  84 */       return results;
/*     */     } catch (RuntimeException re) {
/*  86 */       log.error("find by example failed", re);
/*  87 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<ServFlowParms> findAll()
/*     */   {
/*     */     try
/*     */     {
/*  94 */       String queryString = "from ServFlowParms as model";
/*  95 */       Query queryObject = getSession().createQuery(queryString);
/*  96 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  98 */       log.error("find by property name failed", re);
/*  99 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 110 */     log.debug("finding ServFlowParms instance with property: " + propertyName + 
/* 111 */       ", value: " + value);
/*     */     try {
/* 113 */       String queryString = "from ServFlowParms as model where model." + 
/* 114 */         propertyName + "= ?";
/* 115 */       Query queryObject = getSession().createQuery(queryString);
/* 116 */       queryObject.setParameter(0, value);
/* 117 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 119 */       log.error("find by property name failed", re);
/* 120 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByParmvalue(Object parmvalue)
/*     */   {
/* 130 */     return findByProperty("parmvalue", parmvalue);
/*     */   }
/*     */ 
/*     */   public ServFlowParms merge(ServFlowParms detachedInstance) {
/* 134 */     log.debug("merging ServFlowParms instance");
/*     */     try {
/* 136 */       ServFlowParms result = (ServFlowParms)getSession()
/* 137 */         .merge(detachedInstance);
/* 138 */       log.debug("merge successful");
/* 139 */       return result;
/*     */     } catch (RuntimeException re) {
/* 141 */       log.error("merge failed", re);
/* 142 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(ServFlowParms instance)
/*     */   {
/* 147 */     log.debug("attaching dirty ServFlowParms instance");
/*     */     try {
/* 149 */       getSession().saveOrUpdate(instance);
/* 150 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 152 */       log.error("attach failed", re);
/* 153 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(ServFlowParms instance) {
/* 158 */     log.debug("attaching clean ServFlowParms instance");
/*     */     try {
/* 160 */       getSession().lock(instance, LockMode.NONE);
/* 161 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 163 */       log.error("attach failed", re);
/* 164 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServFlowParmsDAO
 * JD-Core Version:    0.6.0
 */