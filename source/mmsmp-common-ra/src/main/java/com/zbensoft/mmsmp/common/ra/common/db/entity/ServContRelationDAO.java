/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.LockMode;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.criterion.Example;
/*     */ 
/*     */ public class ServContRelationDAO extends BaseHibernateDAO
/*     */ {
/*  21 */   private static final Log log = LogFactory.getLog(ServContRelationDAO.class);
/*     */   public static final String SERVICEUNIQUEID = "serviceuniqueid";
/*     */   public static final String ITEMTYPE = "itemtype";
/*     */   public static final String ITEMID = "itemid";
/*     */   public static final String CONTENTTREEID = "contenttreeid";
/*     */   public static final String FEATURESTR = "featurestr";
/*     */ 
/*     */   public void save(ServContRelation transientInstance)
/*     */   {
/*  35 */     log.debug("saving ServContRelation instance");
/*     */     try {
/*  37 */       getSession().save(transientInstance);
/*  38 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  40 */       log.error("save failed", re);
/*  41 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(ServContRelation persistentInstance)
/*     */   {
/*  50 */     log.debug("deleting ServContRelation instance");
/*     */     try {
/*  52 */       getSession().delete(persistentInstance);
/*  53 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  55 */       log.error("delete failed", re);
/*  56 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ServContRelation findById(Integer id)
/*     */   {
/*  66 */     log.debug("getting ServContRelation instance with id: " + id);
/*     */     try {
/*  68 */       ServContRelation instance = (ServContRelation)getSession()
/*  69 */         .get("com.aceway.common.db.entity.ServContRelation", id);
/*  70 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  72 */       log.error("get failed", re);
/*  73 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(ServContRelation instance)
/*     */   {
/*  83 */     log.debug("finding ServContRelation instance by example");
/*     */     try {
/*  85 */       List results = getSession()
/*  86 */         .createCriteria("com.aceway.common.db.entity.ServContRelation")
/*  87 */         .add(Example.create(instance))
/*  88 */         .list();
/*  89 */       log.debug("find by example successful, result size: " + results.size());
/*  90 */       return results;
/*     */     } catch (RuntimeException re) {
/*  92 */       log.error("find by example failed", re);
/*  93 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<ServContRelation> findAll()
/*     */   {
/*     */     try
/*     */     {
/* 103 */       String queryString = "from ServContRelation as model ";
/* 104 */       Query queryObject = getSession().createQuery(queryString);
/* 105 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 107 */       log.error("find by property name failed", re);
/* 108 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 120 */     if (log.isDebugEnabled()) {
/* 121 */       log.debug("finding ServContRelation instance with property: " + propertyName + ", value: " + value);
/*     */     }
/*     */     try
/*     */     {
/* 125 */       String queryString = "from ServContRelation as model where model." + propertyName + "= ?";
/* 126 */       Query queryObject = getSession().createQuery(queryString);
/* 127 */       queryObject.setParameter(0, value);
/*     */ 
/* 129 */       return queryObject.list();
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 133 */       log.error("find by property name failed", re);
/* 134 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyNameA, Object valueA, String propertyNameB, Object valueB)
/*     */   {
/* 148 */     if (log.isDebugEnabled()) {
/* 149 */       log.debug("finding ServContRelation instance with property: " + propertyNameA + ", value: " + valueA);
/*     */     }
/*     */     try
/*     */     {
/* 153 */       String queryString = "from ServContRelation as modelA, ContentInfo as modelB where modelA." + propertyNameA + 
/* 154 */         "= ? and modelA.itemtype = '2' and modelB." + propertyNameB + "= ? and " + 
/* 155 */         "(modelA.contenttreeid = modelB.treeid)";
/*     */ 
/* 157 */       Query queryObject = getSession().createQuery(queryString);
/*     */ 
/* 159 */       queryObject.setParameter(0, valueA);
/* 160 */       queryObject.setParameter(1, valueB);
/* 161 */       log.info("query sql: " + queryObject.getQueryString());
/* 162 */       log.info("value 1: " + valueA + " value 2: " + valueB);
/* 163 */       List result = queryObject.list();
/*     */ 
/* 165 */       if (result.size() > 0) {
/* 166 */         return result;
/*     */       }
/*     */ 
/* 169 */       queryString = "from ServContRelation as modelA, ContentInfo as modelB where modelA." + propertyNameA + 
/* 170 */         "= ? and modelA.itemtype = '1' and modelB." + propertyNameB + "= ? and " + 
/* 171 */         "(modelA.itemid = modelB.contentid)";
/*     */ 
/* 173 */       queryObject = getSession().createQuery(queryString);
/*     */ 
/* 175 */       queryObject.setParameter(0, valueA);
/* 176 */       queryObject.setParameter(1, valueB);
/*     */ 
/* 178 */       List secondResult = queryObject.list();
/*     */ 
/* 182 */       return secondResult;
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 186 */       log.error("find by property name failed", re);
/* 187 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<ContentInfo> findContentInfo(String propertyNameA, Object valueA)
/*     */   {
/* 199 */     List contentInfoList = new ArrayList();
/* 200 */     Object[] obj = (Object[])null;
/* 201 */     Iterator iter = null;
/*     */ 
/* 203 */     String queryString = "from ServContRelation as modelA, ContentInfo as modelB where modelA." + propertyNameA + 
/* 204 */       "= ? and ((modelA.itemtype = '2' and modelA.contenttreeid = modelB.treeid) or (modelA.itemtype = '1' and modelA.itemid = modelB.contentid))";
/*     */     try
/*     */     {
/* 208 */       Query queryObject = getSession().createQuery(queryString);
/*     */ 
/* 210 */       queryObject.setParameter(0, valueA);
/*     */ 
/* 212 */       return queryObject.list();
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 244 */       log.error("find content failed: ", re);
/* 245 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByServiceuniqueid(Object serviceuniqueid)
/*     */   {
/* 256 */     return findByProperty("serviceuniqueid", serviceuniqueid);
/*     */   }
/*     */ 
/*     */   public List findByItemtype(Object itemtype)
/*     */   {
/* 266 */     return findByProperty("itemtype", itemtype);
/*     */   }
/*     */ 
/*     */   public List findByItemid(Object itemid)
/*     */   {
/* 276 */     return findByProperty("itemid", itemid);
/*     */   }
/*     */ 
/*     */   public List findByContenttreeid(Object contenttreeid)
/*     */   {
/* 286 */     return findByProperty("contenttreeid", contenttreeid);
/*     */   }
/*     */ 
/*     */   public List findByServiceIdPlusItemType(Object serviceuniqueid, Object featureStr)
/*     */   {
/* 298 */     if (featureStr.toString().length() > 10) return null;
/* 299 */     return findByProperty("serviceuniqueid", serviceuniqueid, "featurestr", featureStr);
/*     */   }
/*     */ 
/*     */   public List<ContentInfo> findContentInfoByServiceId(Object serviceuniqueid)
/*     */   {
/* 309 */     return findContentInfo("serviceuniqueid", serviceuniqueid);
/*     */   }
/*     */ 
/*     */   public ServContRelation merge(ServContRelation detachedInstance)
/*     */   {
/* 318 */     log.debug("merging ServContRelation instance");
/*     */     try {
/* 320 */       ServContRelation result = (ServContRelation)getSession()
/* 321 */         .merge(detachedInstance);
/* 322 */       log.debug("merge successful");
/* 323 */       return result;
/*     */     } catch (RuntimeException re) {
/* 325 */       log.error("merge failed", re);
/* 326 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(ServContRelation instance)
/*     */   {
/* 331 */     log.debug("attaching dirty ServContRelation instance");
/*     */     try {
/* 333 */       getSession().saveOrUpdate(instance);
/* 334 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 336 */       log.error("attach failed", re);
/* 337 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(ServContRelation instance) {
/* 342 */     log.debug("attaching clean ServContRelation instance");
/*     */     try {
/* 344 */       getSession().lock(instance, LockMode.NONE);
/* 345 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 347 */       log.error("attach failed", re);
/* 348 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServContRelationDAO
 * JD-Core Version:    0.6.0
 */