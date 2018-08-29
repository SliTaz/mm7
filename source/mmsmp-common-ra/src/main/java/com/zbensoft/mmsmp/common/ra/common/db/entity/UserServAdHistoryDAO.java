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
/*     */ public class UserServAdHistoryDAO extends BaseHibernateDAO
/*     */ {
/*  19 */   private static final Log log = LogFactory.getLog(UserServAdHistoryDAO.class);
/*     */   public static final String REQID = "reqid";
/*     */   public static final String CELLPHONENUMBER = "cellphonenumber";
/*     */   public static final String CHARGEPARTY = "chargeparty";
/*     */   public static final String SERVUNIQUEID = "servuniqueid";
/*     */   public static final String CONTENTID = "contentid";
/*     */   public static final String STATUS = "status";
/*     */   public static final String MTKIND = "mtkind";
/*     */   public static final String MTSERVTYPE = "mtservtype";
/*     */   public static final String MTRELATIONID = "mtrelationid";
/*     */ 
/*     */   public void save(UserServAdHistory transientInstance)
/*     */   {
/*  34 */     log.debug("saving UserServAdHistory instance");
/*     */     try {
/*  36 */       getSession().save(transientInstance);
/*  37 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  39 */       log.error("save failed", re);
/*  40 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(UserServAdHistory persistentInstance) {
/*  45 */     log.debug("deleting UserServAdHistory instance");
/*     */     try {
/*  47 */       getSession().delete(persistentInstance);
/*  48 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  50 */       log.error("delete failed", re);
/*  51 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserServAdHistory findById(String id) {
/*  56 */     log.debug("getting UserServAdHistory instance with id: " + id);
/*     */     try {
/*  58 */       UserServAdHistory instance = (UserServAdHistory)getSession()
/*  59 */         .get("com.aceway.common.db.entity.UserServAdHistory", id);
/*  60 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  62 */       log.error("get failed", re);
/*  63 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(UserServAdHistory instance)
/*     */   {
/*  69 */     log.debug("finding UserServAdHistory instance by example");
/*     */     try {
/*  71 */       List results = getSession()
/*  72 */         .createCriteria("com.aceway.common.db.entity.UserServAdHistory")
/*  73 */         .add(Example.create(instance))
/*  74 */         .list();
/*  75 */       log.debug("find by example successful, result size: " + results.size());
/*  76 */       return results;
/*     */     } catch (RuntimeException re) {
/*  78 */       log.error("find by example failed", re);
/*  79 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  84 */     log.debug("finding UserServAdHistory instance with property: " + propertyName + 
/*  85 */       ", value: " + value);
/*     */     try {
/*  87 */       String queryString = "from UserServAdHistory as model where model." + 
/*  88 */         propertyName + "= ?";
/*  89 */       Query queryObject = getSession().createQuery(queryString);
/*  90 */       queryObject.setParameter(0, value);
/*  91 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  93 */       log.error("find by property name failed", re);
/*  94 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByReqid(Object reqid)
/*     */   {
/*  99 */     return findByProperty("reqid", reqid);
/*     */   }
/*     */ 
/*     */   public List findByCellphonenumber(Object cellphonenumber) {
/* 103 */     return findByProperty("cellphonenumber", cellphonenumber);
/*     */   }
/*     */ 
/*     */   public List findByChargeparty(Object chargeparty) {
/* 107 */     return findByProperty("chargeparty", chargeparty);
/*     */   }
/*     */ 
/*     */   public List findByServuniqueid(Object servuniqueid) {
/* 111 */     return findByProperty("servuniqueid", servuniqueid);
/*     */   }
/*     */ 
/*     */   public List findByContentid(Object contentid) {
/* 115 */     return findByProperty("contentid", contentid);
/*     */   }
/*     */ 
/*     */   public List findByStatus(Object status) {
/* 119 */     return findByProperty("status", status);
/*     */   }
/*     */ 
/*     */   public List findByMtkind(Object mtkind) {
/* 123 */     return findByProperty("mtkind", mtkind);
/*     */   }
/*     */ 
/*     */   public List findByMtservtype(Object mtservtype) {
/* 127 */     return findByProperty("mtservtype", mtservtype);
/*     */   }
/*     */ 
/*     */   public List findByMtrelationid(Object mtrelationid) {
/* 131 */     return findByProperty("mtrelationid", mtrelationid);
/*     */   }
/*     */ 
/*     */   public UserServAdHistory merge(UserServAdHistory detachedInstance) {
/* 135 */     log.debug("merging UserServAdHistory instance");
/*     */     try {
/* 137 */       UserServAdHistory result = (UserServAdHistory)getSession()
/* 138 */         .merge(detachedInstance);
/* 139 */       log.debug("merge successful");
/* 140 */       return result;
/*     */     } catch (RuntimeException re) {
/* 142 */       log.error("merge failed", re);
/* 143 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(UserServAdHistory instance)
/*     */   {
/* 148 */     log.debug("attaching dirty UserServAdHistory instance");
/*     */     try {
/* 150 */       getSession().saveOrUpdate(instance);
/* 151 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 153 */       log.error("attach failed", re);
/* 154 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(UserServAdHistory instance) {
/* 159 */     log.debug("attaching clean UserServAdHistory instance");
/*     */     try {
/* 161 */       getSession().lock(instance, LockMode.NONE);
/* 162 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 164 */       log.error("attach failed", re);
/* 165 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserServAdHistoryDAO
 * JD-Core Version:    0.6.0
 */