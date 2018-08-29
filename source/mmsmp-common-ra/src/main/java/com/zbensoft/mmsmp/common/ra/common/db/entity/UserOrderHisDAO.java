/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.LockMode;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.criterion.Example;
/*     */ 
/*     */ public class UserOrderHisDAO extends BaseHibernateDAO
/*     */ {
/*  21 */   private static final Log log = LogFactory.getLog(UserOrderHisDAO.class);
/*     */   public static final String CELLPHONENUMBER = "cellphonenumber";
/*     */   public static final String CHARGEPARTY = "chargeparty";
/*     */   public static final String SERVICEUNIQUEID = "serviceuniqueid";
/*     */   public static final String ORDERMETHOD = "ordermethod";
/*     */   public static final String FEETYPE = "feetype";
/*     */   public static final String FEE = "fee";
/*     */   public static final String CANCELORDERMETHOD = "cancelordermethod";
/*     */   public static final String CONTENTID = "contentid";
/*     */   public static final String FEATURESTR = "featurestr";
/*     */ 
/*     */   public void save(UserOrderHis transientInstance)
/*     */   {
/*  36 */     log.info("UserOrderHis:" + transientInstance.toString());
/*     */ 
/*  38 */     log.info("saving UserOrderHis instance");
/*     */ 
/*  49 */     Session session = getSession();
/*     */     try {
/*  51 */       session.save(transientInstance);
/*  52 */       session.flush();
/*  53 */       session.evict(transientInstance);
/*  54 */       log.info("save successful");
/*     */     } catch (RuntimeException re) {
/*  56 */       log.error("save failed", re);
/*  57 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(UserOrderHis persistentInstance) {
/*  62 */     log.debug("deleting UserOrderHis instance");
/*     */     try {
/*  64 */       getSession().delete(persistentInstance);
/*  65 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  67 */       log.error("delete failed", re);
/*  68 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserOrderHis findById(Integer id) {
/*  73 */     log.debug("getting UserOrderHis instance with id: " + id);
/*  74 */     Session session = getSession();
/*     */     try {
/*  76 */       UserOrderHis instance = (UserOrderHis)session
/*  77 */         .get("com.aceway.common.db.entity.UserOrderHis", id);
/*  78 */       session.flush();
/*  79 */       session.evict(instance);
/*  80 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  82 */       log.error("get failed", re);
/*  83 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(UserOrderHis instance)
/*     */   {
/*  89 */     log.debug("finding UserOrderHis instance by example");
/*     */     try {
/*  91 */       List results = getSession()
/*  92 */         .createCriteria("com.aceway.common.db.entity.UserOrderHis")
/*  93 */         .add(Example.create(instance))
/*  94 */         .list();
/*  95 */       log.debug("find by example successful, result size: " + results.size());
/*  96 */       return results;
/*     */     } catch (RuntimeException re) {
/*  98 */       log.error("find by example failed", re);
/*  99 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 104 */     log.debug("finding UserOrderHis instance with property: " + propertyName + 
/* 105 */       ", value: " + value);
/*     */     try {
/* 107 */       String queryString = "from UserOrderHis as model where model." + 
/* 108 */         propertyName + "= ?";
/* 109 */       Query queryObject = getSession().createQuery(queryString);
/* 110 */       queryObject.setParameter(0, value);
/* 111 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 113 */       log.error("find by property name failed", re);
/* 114 */     }throw re;
/*     */   }
/*     */ 
/*     */   public UserOrderHis findOrderHis(String userPhone, Integer servId, Date orderDate)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       String queryString = "from UserOrderHis as model where model.cellphonenumber=? and serviceuniqueid=? and orderdate=?";
/* 122 */       Query queryObject = getSession().createQuery(queryString);
/* 123 */       queryObject.setParameter(0, userPhone);
/* 124 */       queryObject.setParameter(1, servId);
/* 125 */       queryObject.setParameter(2, orderDate);
/* 126 */       List result = queryObject.list();
/* 127 */       if ((result == null) || (result.size() == 0)) {
/* 128 */         return null;
/*     */       }
/* 130 */       return (UserOrderHis)result.get(0);
/*     */     }
/*     */     catch (RuntimeException re) {
/* 133 */       log.error("find by property name failed", re);
/* 134 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByCellphonenumber(Object cellphonenumber)
/*     */   {
/* 139 */     return findByProperty("cellphonenumber", cellphonenumber);
/*     */   }
/*     */ 
/*     */   public List findByChargeparty(Object chargeparty) {
/* 143 */     return findByProperty("chargeparty", chargeparty);
/*     */   }
/*     */ 
/*     */   public List findByServiceuniqueid(Object serviceuniqueid) {
/* 147 */     return findByProperty("serviceuniqueid", serviceuniqueid);
/*     */   }
/*     */ 
/*     */   public List findByOrdermethod(Object ordermethod) {
/* 151 */     return findByProperty("ordermethod", ordermethod);
/*     */   }
/*     */ 
/*     */   public List findByFeetype(Object feetype) {
/* 155 */     return findByProperty("feetype", feetype);
/*     */   }
/*     */ 
/*     */   public List findByFee(Object fee) {
/* 159 */     return findByProperty("fee", fee);
/*     */   }
/*     */ 
/*     */   public List findByCancelordermethod(Object cancelordermethod) {
/* 163 */     return findByProperty("cancelordermethod", cancelordermethod);
/*     */   }
/*     */ 
/*     */   public List findByContentid(Object contentid) {
/* 167 */     return findByProperty("contentid", contentid);
/*     */   }
/*     */ 
/*     */   public List findByFeaturestr(Object featurestr) {
/* 171 */     return findByProperty("featurestr", featurestr);
/*     */   }
/*     */ 
/*     */   public UserOrderHis merge(UserOrderHis detachedInstance) {
/* 175 */     log.debug("merging UserOrderHis instance");
/*     */     try {
/* 177 */       UserOrderHis result = (UserOrderHis)getSession()
/* 178 */         .merge(detachedInstance);
/* 179 */       log.debug("merge successful");
/* 180 */       return result;
/*     */     } catch (RuntimeException re) {
/* 182 */       log.error("merge failed", re);
/* 183 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(UserOrderHis instance)
/*     */   {
/* 188 */     log.debug("attaching dirty UserOrderHis instance");
/*     */     try {
/* 190 */       getSession().saveOrUpdate(instance);
/* 191 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 193 */       log.error("attach failed", re);
/* 194 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(UserOrderHis instance) {
/* 199 */     log.debug("attaching clean UserOrderHis instance");
/*     */     try {
/* 201 */       getSession().lock(instance, LockMode.NONE);
/* 202 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 204 */       log.error("attach failed", re);
/* 205 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserOrderHisDAO
 * JD-Core Version:    0.6.0
 */