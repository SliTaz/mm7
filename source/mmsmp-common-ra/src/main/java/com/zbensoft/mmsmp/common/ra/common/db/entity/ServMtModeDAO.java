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
/*     */ public class ServMtModeDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(ServMtModeDAO.class);
/*     */   public static final String MTSTARTTIME1 = "mtstarttime1";
/*     */   public static final String MTENDTIME1 = "mtendtime1";
/*     */   public static final String MTSTARTTIME2 = "mtstarttime2";
/*     */   public static final String MTENDTIME2 = "mtendtime2";
/*     */   public static final String MTSTARTTIME3 = "mtstarttime3";
/*     */   public static final String MTENDTIME3 = "mtendtime3";
/*     */ 
/*     */   public void save(ServMtMode transientInstance)
/*     */   {
/*  30 */     log.debug("saving ServMtMode instance");
/*     */     try
/*     */     {
/*  34 */       getSession().save(transientInstance);
/*  35 */       log.debug("save successful");
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  39 */       log.error("save failed", re);
/*  40 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(ServMtMode persistentInstance)
/*     */   {
/*  49 */     log.debug("deleting ServMtMode instance");
/*     */     try {
/*  51 */       getSession().delete(persistentInstance);
/*  52 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  54 */       log.error("delete failed", re);
/*  55 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ServMtMode findById(ServMtModeId id)
/*     */   {
/*  65 */     log.debug("getting ServMtMode instance with id: " + id);
/*     */     try {
/*  67 */       ServMtMode instance = (ServMtMode)getSession()
/*  68 */         .get("com.aceway.common.db.entity.ServMtMode", id);
/*  69 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  71 */       log.error("get failed", re);
/*  72 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(ServMtMode instance)
/*     */   {
/*  82 */     log.debug("finding ServMtMode instance by example");
/*     */     try {
/*  84 */       List results = getSession()
/*  85 */         .createCriteria("com.aceway.common.db.entity.ServMtMode")
/*  86 */         .add(Example.create(instance))
/*  87 */         .list();
/*  88 */       log.debug("find by example successful, result size: " + results.size());
/*  89 */       return results;
/*     */     } catch (RuntimeException re) {
/*  91 */       log.error("find by example failed", re);
/*  92 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<ServMtMode> findAll()
/*     */   {
/*     */     try
/*     */     {
/*  99 */       String queryString = "from ServMtMode as model";
/* 100 */       Query queryObject = getSession().createQuery(queryString);
/*     */ 
/* 102 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 104 */       log.error("find by property name failed", re);
/* 105 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 110 */     log.debug("finding ServMtMode instance with property: " + propertyName + 
/* 111 */       ", value: " + value);
/*     */     try {
/* 113 */       String queryString = "from ServMtMode as model where model." + 
/* 114 */         propertyName + "= ?";
/* 115 */       Query queryObject = getSession().createQuery(queryString);
/* 116 */       queryObject.setParameter(0, value);
/* 117 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 119 */       log.error("find by property name failed", re);
/* 120 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByMtstarttime1(Object mtstarttime1)
/*     */   {
/* 125 */     return findByProperty("mtstarttime1", mtstarttime1);
/*     */   }
/*     */ 
/*     */   public List findByMtendtime1(Object mtendtime1) {
/* 129 */     return findByProperty("mtendtime1", mtendtime1);
/*     */   }
/*     */ 
/*     */   public List findByMtstarttime2(Object mtstarttime2) {
/* 133 */     return findByProperty("mtstarttime2", mtstarttime2);
/*     */   }
/*     */ 
/*     */   public List findByMtendtime2(Object mtendtime2) {
/* 137 */     return findByProperty("mtendtime2", mtendtime2);
/*     */   }
/*     */ 
/*     */   public List findByMtstarttime3(Object mtstarttime3) {
/* 141 */     return findByProperty("mtstarttime3", mtstarttime3);
/*     */   }
/*     */ 
/*     */   public List findByMtendtime3(Object mtendtime3) {
/* 145 */     return findByProperty("mtendtime3", mtendtime3);
/*     */   }
/*     */ 
/*     */   public ServMtMode merge(ServMtMode detachedInstance) {
/* 149 */     log.debug("merging ServMtMode instance");
/*     */     try {
/* 151 */       ServMtMode result = (ServMtMode)getSession()
/* 152 */         .merge(detachedInstance);
/* 153 */       log.debug("merge successful");
/* 154 */       return result;
/*     */     } catch (RuntimeException re) {
/* 156 */       log.error("merge failed", re);
/* 157 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(ServMtMode instance)
/*     */   {
/* 166 */     log.debug("attaching dirty ServMtMode instance");
/*     */     try {
/* 168 */       getSession().saveOrUpdate(instance);
/* 169 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 171 */       log.error("attach failed", re);
/* 172 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(ServMtMode instance) {
/* 177 */     log.debug("attaching clean ServMtMode instance");
/*     */     try {
/* 179 */       getSession().lock(instance, LockMode.NONE);
/* 180 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 182 */       log.error("attach failed", re);
/* 183 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServMtModeDAO
 * JD-Core Version:    0.6.0
 */