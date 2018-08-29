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
/*     */ public class FlowParmsDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(FlowParmsDAO.class);
/*     */   public static final String PARMDESC = "parmdesc";
/*     */   public static final String DATATYPE = "datatype";
/*     */   public static final String PARMDEFAULTVALUE = "parmdefaultvalue";
/*     */   public static final String ISORIGINAL = "isoriginal";
/*     */ 
/*     */   public void save(FlowParms transientInstance)
/*     */   {
/*  31 */     log.debug("saving FlowParms instance");
/*     */     try
/*     */     {
/*  35 */       getSession().save(transientInstance);
/*  36 */       log.debug("save successful");
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  40 */       log.error("save failed", re);
/*  41 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(FlowParms persistentInstance)
/*     */   {
/*  50 */     log.debug("deleting FlowParms instance");
/*     */     try
/*     */     {
/*  53 */       getSession().delete(persistentInstance);
/*  54 */       log.debug("delete successful");
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  58 */       log.error("delete failed", re);
/*  59 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public FlowParms findById(FlowParmsId id)
/*     */   {
/*  70 */     if (log.isDebugEnabled()) {
/*  71 */       log.debug("getting FlowParms instance with id: " + id);
/*     */     }
/*     */     try
/*     */     {
/*  75 */       FlowParms instance = (FlowParms)getSession().get("com.aceway.common.db.entity.FlowParms", id);
/*  76 */       return instance;
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  80 */       log.error("get failed", re);
/*  81 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(FlowParms instance)
/*     */   {
/*  92 */     log.debug("finding FlowParms instance by example");
/*     */     try
/*     */     {
/*  96 */       List results = getSession()
/*  97 */         .createCriteria("com.aceway.common.db.entity.FlowParms").add(Example.create(instance))
/*  98 */         .list();
/*  99 */       log.debug("find by example successful, result size: " + results.size());
/* 100 */       return results;
/*     */     } catch (RuntimeException re) {
/* 102 */       log.error("find by example failed", re);
/* 103 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 116 */     if (log.isDebugEnabled()) {
/* 117 */       log.debug("finding FlowParms instance with property: " + propertyName + ", value: " + value);
/*     */     }
/*     */     try
/*     */     {
/* 121 */       String queryString = "from FlowParms as model where model." + propertyName + "= ?";
/* 122 */       Query queryObject = getSession().createQuery(queryString);
/* 123 */       queryObject.setParameter(0, value);
/*     */ 
/* 125 */       return queryObject.list();
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 129 */       log.error("find by property name failed", re);
/* 130 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByFlowId(Object value)
/*     */   {
/*     */     try
/*     */     {
/* 139 */       String queryString = "from FlowParms as model where model.id.flowid= ?";
/* 140 */       Query queryObject = getSession().createQuery(queryString);
/* 141 */       queryObject.setParameter(0, value);
/*     */ 
/* 143 */       return queryObject.list();
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 147 */       log.error("find by property name failed", re);
/* 148 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<FlowParms> findAll()
/*     */   {
/*     */     try
/*     */     {
/* 160 */       String queryString = "from FlowParms as model";
/* 161 */       Query queryObject = getSession().createQuery(queryString);
/*     */ 
/* 163 */       return queryObject.list();
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 167 */       log.error("find by property name failed", re);
/* 168 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByParmdesc(Object parmdesc)
/*     */   {
/* 178 */     return findByProperty("parmdesc", parmdesc);
/*     */   }
/*     */ 
/*     */   public List findByDatatype(Object datatype)
/*     */   {
/* 187 */     return findByProperty("datatype", datatype);
/*     */   }
/*     */ 
/*     */   public List findByParmdefaultvalue(Object parmdefaultvalue)
/*     */   {
/* 196 */     return findByProperty("parmdefaultvalue", parmdefaultvalue);
/*     */   }
/*     */ 
/*     */   public List findByIsoriginal(Object isOriginal)
/*     */   {
/* 205 */     return findByProperty("isoriginal", isOriginal);
/*     */   }
/*     */ 
/*     */   public FlowParms merge(FlowParms detachedInstance)
/*     */   {
/* 214 */     log.debug("merging FlowParms instance");
/*     */     try
/*     */     {
/* 217 */       FlowParms result = (FlowParms)getSession().merge(detachedInstance);
/* 218 */       log.debug("merge successful");
/* 219 */       return result;
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 223 */       log.error("merge failed", re);
/* 224 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(FlowParms instance)
/*     */   {
/* 233 */     log.debug("attaching dirty FlowParms instance");
/*     */     try {
/* 235 */       getSession().saveOrUpdate(instance);
/* 236 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 238 */       log.error("attach failed", re);
/* 239 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(FlowParms instance) {
/* 244 */     log.debug("attaching clean FlowParms instance");
/*     */     try {
/* 246 */       getSession().lock(instance, LockMode.NONE);
/* 247 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 249 */       log.error("attach failed", re);
/* 250 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.FlowParmsDAO
 * JD-Core Version:    0.6.0
 */