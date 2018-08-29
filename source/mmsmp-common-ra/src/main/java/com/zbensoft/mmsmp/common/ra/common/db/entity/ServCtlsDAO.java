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
/*     */ public class ServCtlsDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(ServCtlsDAO.class);
/*     */   public static final String CTLAPPNAME = "ctlappname";
/*     */   public static final String THREADNUM = "threadnum";
/*     */   public static final String MQAPPNAME = "mqappname";
/*     */   public static final String BAKMQAPPNAME = "bakmqappname";
/*     */   public static final String PAYLAYXAPPNAME = "paylayxappname";
/*     */ 
/*     */   public void save(ServCtls transientInstance)
/*     */   {
/*  28 */     log.debug("saving ServCtls instance");
/*     */     try {
/*  30 */       getSession().save(transientInstance);
/*  31 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  33 */       log.error("save failed", re);
/*  34 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(ServCtls persistentInstance) {
/*  39 */     log.debug("deleting ServCtls instance");
/*     */     try {
/*  41 */       getSession().delete(persistentInstance);
/*  42 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  44 */       log.error("delete failed", re);
/*  45 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ServCtls findById(Integer id) {
/*  50 */     log.debug("getting ServCtls instance with id: " + id);
/*     */     try {
/*  52 */       ServCtls instance = (ServCtls)getSession()
/*  53 */         .get("com.aceway.common.db.entity.ServCtls", id);
/*  54 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  56 */       log.error("get failed", re);
/*  57 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(ServCtls instance)
/*     */   {
/*  63 */     log.debug("finding ServCtls instance by example");
/*     */     try {
/*  65 */       List results = getSession()
/*  66 */         .createCriteria("com.aceway.common.db.entity.ServCtls")
/*  67 */         .add(Example.create(instance))
/*  68 */         .list();
/*  69 */       log.debug("find by example successful, result size: " + results.size());
/*  70 */       return results;
/*     */     } catch (RuntimeException re) {
/*  72 */       log.error("find by example failed", re);
/*  73 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findAll()
/*     */   {
/*     */     try {
/*  79 */       String queryString = "from ServCtls as model ";
/*  80 */       Query queryObject = getSession().createQuery(queryString);
/*  81 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  83 */       log.error("find by property name failed", re);
/*  84 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  89 */     log.debug("finding ServCtls instance with property: " + propertyName + 
/*  90 */       ", value: " + value);
/*     */     try {
/*  92 */       String queryString = "from ServCtls as model where model." + 
/*  93 */         propertyName + "= ?";
/*  94 */       Query queryObject = getSession().createQuery(queryString);
/*  95 */       queryObject.setParameter(0, value);
/*  96 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  98 */       log.error("find by property name failed", re);
/*  99 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByCtlappname(Object ctlappname)
/*     */   {
/* 104 */     return findByProperty("ctlappname", ctlappname);
/*     */   }
/*     */ 
/*     */   public List findByThreadnum(Object threadnum) {
/* 108 */     return findByProperty("threadnum", threadnum);
/*     */   }
/*     */ 
/*     */   public List findByMqappname(Object mqappname) {
/* 112 */     return findByProperty("mqappname", mqappname);
/*     */   }
/*     */ 
/*     */   public List findByBakmqappname(Object bakmqappname) {
/* 116 */     return findByProperty("bakmqappname", bakmqappname);
/*     */   }
/*     */ 
/*     */   public List findByPaylayxappname(Object paylayxappname) {
/* 120 */     return findByProperty("paylayxappname", paylayxappname);
/*     */   }
/*     */ 
/*     */   public ServCtls merge(ServCtls detachedInstance) {
/* 124 */     log.debug("merging ServCtls instance");
/*     */     try {
/* 126 */       ServCtls result = (ServCtls)getSession()
/* 127 */         .merge(detachedInstance);
/* 128 */       log.debug("merge successful");
/* 129 */       return result;
/*     */     } catch (RuntimeException re) {
/* 131 */       log.error("merge failed", re);
/* 132 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(ServCtls instance)
/*     */   {
/* 137 */     log.debug("attaching dirty ServCtls instance");
/*     */     try {
/* 139 */       getSession().saveOrUpdate(instance);
/* 140 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 142 */       log.error("attach failed", re);
/* 143 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(ServCtls instance) {
/* 148 */     log.debug("attaching clean ServCtls instance");
/*     */     try {
/* 150 */       getSession().lock(instance, LockMode.NONE);
/* 151 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 153 */       log.error("attach failed", re);
/* 154 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServCtlsDAO
 * JD-Core Version:    0.6.0
 */