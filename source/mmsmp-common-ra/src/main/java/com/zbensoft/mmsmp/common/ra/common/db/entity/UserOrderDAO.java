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
/*     */ public class UserOrderDAO extends BaseHibernateDAO
/*     */ {
/*  21 */   private static final Log log = LogFactory.getLog(UserOrderDAO.class);
/*     */   public static final String CHARGEPARTY = "chargeparty";
/*     */   public static final String ORDERMETHOD = "ordermethod";
/*     */   public static final String FEETYPE = "feetype";
/*     */   public static final String FEE = "fee";
/*     */   public static final String ORDERHISID = "orderhisid";
/*     */   public static final String FEATURESTR = "featurestr";
/*     */ 
/*     */   public void save(UserOrder transientInstance)
/*     */   {
/*  33 */     log.debug("saving UserOrder instance");
/*  34 */     Session session = getSession();
/*     */     try
/*     */     {
/*  37 */       session.save(transientInstance);
/*  38 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  40 */       log.error("save failed", re);
/*  41 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(UserOrder persistentInstance) {
/*  46 */     log.debug("deleting UserOrder instance");
/*     */     try {
/*  48 */       getSession().delete(persistentInstance);
/*  49 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  51 */       log.error("delete failed", re);
/*  52 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserOrder findById(UserOrderId id) {
/*  57 */     log.debug("getting UserOrder instance with id: " + id);
/*  58 */     Session session = getSession();
/*     */     try {
/*  60 */       UserOrder instance = (UserOrder)session
/*  61 */         .get("com.aceway.common.db.entity.UserOrder", id);
/*  62 */       session.flush();
/*  63 */       session.evict(instance);
/*  64 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  66 */       log.error("get failed", re);
/*  67 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(UserOrder instance)
/*     */   {
/*  73 */     log.debug("finding UserOrder instance by example");
/*     */     try {
/*  75 */       List results = getSession()
/*  76 */         .createCriteria("com.aceway.common.db.entity.UserOrder")
/*  77 */         .add(Example.create(instance))
/*  78 */         .list();
/*  79 */       log.debug("find by example successful, result size: " + results.size());
/*  80 */       return results;
/*     */     } catch (RuntimeException re) {
/*  82 */       log.error("find by example failed", re);
/*  83 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  88 */     log.debug("finding UserOrder instance with property: " + propertyName + 
/*  89 */       ", value: " + value);
/*     */     try {
/*  91 */       String queryString = "from UserOrder as model where model." + 
/*  92 */         propertyName + "= ?";
/*  93 */       Query queryObject = getSession().createQuery(queryString);
/*  94 */       queryObject.setParameter(0, value);
/*  95 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  97 */       log.error("find by property name failed", re);
/*  98 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByChargeparty(Object chargeparty)
/*     */   {
/* 103 */     return findByProperty("chargeparty", chargeparty);
/*     */   }
/*     */ 
/*     */   public List findByOrdermethod(Object ordermethod) {
/* 107 */     return findByProperty("ordermethod", ordermethod);
/*     */   }
/*     */ 
/*     */   public List findByFeetype(Object feetype) {
/* 111 */     return findByProperty("feetype", feetype);
/*     */   }
/*     */ 
/*     */   public List findByFee(Object fee) {
/* 115 */     return findByProperty("fee", fee);
/*     */   }
/*     */ 
/*     */   public List findByOrderhisid(Object orderhisid) {
/* 119 */     return findByProperty("orderhisid", orderhisid);
/*     */   }
/*     */ 
/*     */   public List findByFeaturestr(Object featurestr) {
/* 123 */     return findByProperty("featurestr", featurestr);
/*     */   }
/*     */ 
/*     */   public UserOrder merge(UserOrder detachedInstance) {
/* 127 */     log.debug("merging UserOrder instance");
/*     */     try {
/* 129 */       UserOrder result = (UserOrder)getSession()
/* 130 */         .merge(detachedInstance);
/* 131 */       log.debug("merge successful");
/* 132 */       return result;
/*     */     } catch (RuntimeException re) {
/* 134 */       log.error("merge failed", re);
/* 135 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(UserOrder instance)
/*     */   {
/* 140 */     log.debug("attaching dirty UserOrder instance");
/*     */     try {
/* 142 */       getSession().saveOrUpdate(instance);
/* 143 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 145 */       log.error("attach failed", re);
/* 146 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(UserOrder instance) {
/* 151 */     log.debug("attaching clean UserOrder instance");
/*     */     try {
/* 153 */       getSession().lock(instance, LockMode.NONE);
/* 154 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 156 */       log.error("attach failed", re);
/* 157 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserOrderDAO
 * JD-Core Version:    0.6.0
 */