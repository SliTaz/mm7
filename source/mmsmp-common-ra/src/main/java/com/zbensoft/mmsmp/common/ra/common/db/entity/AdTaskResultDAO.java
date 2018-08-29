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
/*     */ public class AdTaskResultDAO extends BaseHibernateDAO
/*     */ {
/*  19 */   private static final Log log = LogFactory.getLog(AdTaskResultDAO.class);
/*     */   public static final String SUBMITSUM = "submitsum";
/*     */   public static final String VALIDSUBMITSUM = "validsubmitsum";
/*     */   public static final String SUCCESSSUM = "successsum";
/*     */   public static final String UNKNOWNSUM = "unknownsum";
/*     */   public static final String FAILURESUM = "failuresum";
/*     */   public static final String CANCELEDSUM = "canceledsum";
/*     */   public static final String RESULTDESC = "resultdesc";
/*     */ 
/*     */   public void save(AdTaskResult transientInstance)
/*     */   {
/*  32 */     log.debug("saving AdTaskResult instance");
/*     */     try {
/*  34 */       getSession().save(transientInstance);
/*  35 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  37 */       log.error("save failed", re);
/*  38 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(AdTaskResult persistentInstance) {
/*  43 */     log.debug("deleting AdTaskResult instance");
/*     */     try {
/*  45 */       getSession().delete(persistentInstance);
/*  46 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  48 */       log.error("delete failed", re);
/*  49 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public AdTaskResult findById(Integer id) {
/*  54 */     log.debug("getting AdTaskResult instance with id: " + id);
/*     */     try {
/*  56 */       AdTaskResult instance = (AdTaskResult)getSession()
/*  57 */         .get("com.aceway.common.db.entity.AdTaskResult", id);
/*  58 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  60 */       log.error("get failed", re);
/*  61 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(AdTaskResult instance)
/*     */   {
/*  67 */     log.debug("finding AdTaskResult instance by example");
/*     */     try {
/*  69 */       List results = getSession()
/*  70 */         .createCriteria("com.aceway.common.db.entity.AdTaskResult")
/*  71 */         .add(Example.create(instance))
/*  72 */         .list();
/*  73 */       log.debug("find by example successful, result size: " + results.size());
/*  74 */       return results;
/*     */     } catch (RuntimeException re) {
/*  76 */       log.error("find by example failed", re);
/*  77 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  82 */     log.debug("finding AdTaskResult instance with property: " + propertyName + 
/*  83 */       ", value: " + value);
/*     */     try {
/*  85 */       String queryString = "from AdTaskResult as model where model." + 
/*  86 */         propertyName + "= ?";
/*  87 */       Query queryObject = getSession().createQuery(queryString);
/*  88 */       queryObject.setParameter(0, value);
/*  89 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  91 */       log.error("find by property name failed", re);
/*  92 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findBySubmitsum(Object submitsum)
/*     */   {
/*  97 */     return findByProperty("submitsum", submitsum);
/*     */   }
/*     */ 
/*     */   public List findByValidsubmitsum(Object validsubmitsum) {
/* 101 */     return findByProperty("validsubmitsum", validsubmitsum);
/*     */   }
/*     */ 
/*     */   public List findBySuccesssum(Object successsum) {
/* 105 */     return findByProperty("successsum", successsum);
/*     */   }
/*     */ 
/*     */   public List findByUnknownsum(Object unknownsum) {
/* 109 */     return findByProperty("unknownsum", unknownsum);
/*     */   }
/*     */ 
/*     */   public List findByFailuresum(Object failuresum) {
/* 113 */     return findByProperty("failuresum", failuresum);
/*     */   }
/*     */ 
/*     */   public List findByCanceledsum(Object canceledsum) {
/* 117 */     return findByProperty("canceledsum", canceledsum);
/*     */   }
/*     */ 
/*     */   public List findByResultdesc(Object resultdesc) {
/* 121 */     return findByProperty("resultdesc", resultdesc);
/*     */   }
/*     */ 
/*     */   public AdTaskResult merge(AdTaskResult detachedInstance) {
/* 125 */     log.debug("merging AdTaskResult instance");
/*     */     try {
/* 127 */       AdTaskResult result = (AdTaskResult)getSession()
/* 128 */         .merge(detachedInstance);
/* 129 */       log.debug("merge successful");
/* 130 */       return result;
/*     */     } catch (RuntimeException re) {
/* 132 */       log.error("merge failed", re);
/* 133 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(AdTaskResult instance)
/*     */   {
/* 138 */     log.debug("attaching dirty AdTaskResult instance");
/*     */     try {
/* 140 */       getSession().saveOrUpdate(instance);
/* 141 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 143 */       log.error("attach failed", re);
/* 144 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(AdTaskResult instance) {
/* 149 */     log.debug("attaching clean AdTaskResult instance");
/*     */     try {
/* 151 */       getSession().lock(instance, LockMode.NONE);
/* 152 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 154 */       log.error("attach failed", re);
/* 155 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.AdTaskResultDAO
 * JD-Core Version:    0.6.0
 */