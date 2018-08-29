/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.LockMode;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.SQLQuery;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.criterion.Example;
/*     */ 
/*     */ public class ContentInfoDAO extends BaseHibernateDAO
/*     */ {
/*  12 */   private static final Log log = LogFactory.getLog(ContentInfoDAO.class);
/*     */   public static final String CONTENTCODE = "contentcode";
/*     */   public static final String CONTENTNAME = "contentname";
/*     */   public static final String CONTENTDESC = "contentdesc";
/*     */   public static final String CPID = "cpid";
/*     */   public static final String AUTHORNAME = "authorname";
/*     */   public static final String STATUS = "status";
/*     */   public static final String CONTENTPATH = "contentpath";
/*     */   public static final String CONTENTTYPE = "contenttype";
/*     */   public static final String KEYWORDS = "keywords";
/*     */   public static final String ISREALTIME = "isrealtime";
/*     */   public static final String REALTIMECONTENURL = "realtimecontenurl";
/*     */   public static final String REJECTDESC = "rejectdesc";
/*     */   public static final String TREEID = "treeid";
/*     */   public static final String DELETEAPPLY = "deleteapply";
/*     */   public static final String VIEWPIC = "viewpic";
/*     */   public static final String SINGER = "singer";
/*     */   public static final String FEATURESTR = "featurestr";
/*     */   public static final String ISADAPTER = "isadapter";
/*     */ 
/*     */   public void save(ContentInfo transientInstance)
/*     */   {
/*  34 */     log.debug("saving ContentInfo instance");
/*     */     try {
/*  36 */       getSession().save(transientInstance);
/*  37 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  39 */       log.error("save failed", re);
/*  40 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(ContentInfo persistentInstance) {
/*  45 */     log.debug("deleting ContentInfo instance");
/*     */     try {
/*  47 */       getSession().delete(persistentInstance);
/*  48 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  50 */       log.error("delete failed", re);
/*  51 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ContentInfo findById(Integer id) {
/*  56 */     log.debug("getting ContentInfo instance with id: " + id);
/*     */     try {
/*  58 */       ContentInfo instance = (ContentInfo)getSession().get(
/*  59 */         "com.aceway.common.db.entity.ContentInfo", id);
/*  60 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  62 */       log.error("get failed", re);
/*  63 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByService(int serviceId)
/*     */   {
/*     */     try {
/*  69 */       String queryString = "from ContentInfo as model where model.serviceId=?";
/*  70 */       Query queryObject = getSession().createQuery(queryString);
/*  71 */       queryObject.setInteger(0, serviceId);
/*  72 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  74 */       log.error("find by property name failed", re);
/*  75 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByFeatureStr(int serviceId, String featureStr)
/*     */   {
/*     */     try {
/*  81 */       String queryString = "from ContentInfo as model where model.serviceId=? and model.featurestr=?";
/*  82 */       Query queryObject = getSession().createQuery(queryString);
/*  83 */       queryObject.setInteger(0, serviceId);
/*  84 */       queryObject.setString(2, featureStr);
/*  85 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  87 */       log.error("find by property name failed", re);
/*  88 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByServiceVersion(int serviceId, String versionCode) {
/*     */     try {
/*  93 */       String queryString = "select CONTENTID,a.SERVICEID,CONTENTNAME,CREATEDATE,CONTENTPATH,VALIDSTARTTIME,VALIDENDTIME,FEATURESTR,VERSION_ID from content_info a,vasservice_version b where a.SERVICEID=" + 
/*  95 */         serviceId + " and b.cod=" + versionCode + " and a.VERSION_ID=b.ID";
/*  96 */       Query queryObject = getSession().createSQLQuery(queryString).addEntity(ContentInfo.class);
/*  97 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  99 */       log.error("find by property name failed", re);
/* 100 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(ContentInfo instance)
/*     */   {
/* 105 */     log.debug("finding ContentInfo instance by example");
/*     */     try {
/* 107 */       List results = getSession().createCriteria(
/* 108 */         "com.aceway.common.db.entity.ContentInfo").add(
/* 109 */         Example.create(instance)).list();
/* 110 */       log.debug("find by example successful, result size: " + 
/* 111 */         results.size());
/* 112 */       return results;
/*     */     } catch (RuntimeException re) {
/* 114 */       log.error("find by example failed", re);
/* 115 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 120 */     log.debug("finding ContentInfo instance with property: " + propertyName + 
/* 121 */       ", value: " + value);
/*     */     try {
/* 123 */       String queryString = "from ContentInfo as model where model." + 
/* 124 */         propertyName + "= ?";
/* 125 */       Query queryObject = getSession().createQuery(queryString);
/* 126 */       queryObject.setParameter(0, value);
/* 127 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 129 */       log.error("find by property name failed", re);
/* 130 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByContentcode(Object contentcode)
/*     */   {
/* 135 */     return findByProperty("contentcode", contentcode);
/*     */   }
/*     */ 
/*     */   public List findByContentname(Object contentname) {
/* 139 */     return findByProperty("contentname", contentname);
/*     */   }
/*     */ 
/*     */   public List findByContentdesc(Object contentdesc) {
/* 143 */     return findByProperty("contentdesc", contentdesc);
/*     */   }
/*     */ 
/*     */   public List findByCpid(Object cpid) {
/* 147 */     return findByProperty("cpid", cpid);
/*     */   }
/*     */ 
/*     */   public List findByAuthorname(Object authorname) {
/* 151 */     return findByProperty("authorname", authorname);
/*     */   }
/*     */ 
/*     */   public List findByStatus(Object status) {
/* 155 */     return findByProperty("status", status);
/*     */   }
/*     */ 
/*     */   public List findByContentpath(Object contentpath) {
/* 159 */     return findByProperty("contentpath", contentpath);
/*     */   }
/*     */ 
/*     */   public List findByContenttype(Object contenttype) {
/* 163 */     return findByProperty("contenttype", contenttype);
/*     */   }
/*     */ 
/*     */   public List findByKeywords(Object keywords) {
/* 167 */     return findByProperty("keywords", keywords);
/*     */   }
/*     */ 
/*     */   public List findByIsrealtime(Object isrealtime) {
/* 171 */     return findByProperty("isrealtime", isrealtime);
/*     */   }
/*     */ 
/*     */   public List findByRealtimecontenurl(Object realtimecontenurl) {
/* 175 */     return findByProperty("realtimecontenurl", realtimecontenurl);
/*     */   }
/*     */ 
/*     */   public List findByRejectdesc(Object rejectdesc) {
/* 179 */     return findByProperty("rejectdesc", rejectdesc);
/*     */   }
/*     */ 
/*     */   public List findByTreeid(Object treeid) {
/* 183 */     return findByProperty("treeid", treeid);
/*     */   }
/*     */ 
/*     */   public List findByDeleteapply(Object deleteapply) {
/* 187 */     return findByProperty("deleteapply", deleteapply);
/*     */   }
/*     */ 
/*     */   public List findByViewpic(Object viewpic) {
/* 191 */     return findByProperty("viewpic", viewpic);
/*     */   }
/*     */ 
/*     */   public List findBySinger(Object singer) {
/* 195 */     return findByProperty("singer", singer);
/*     */   }
/*     */ 
/*     */   public List findByFeaturestr(Object featurestr) {
/* 199 */     return findByProperty("featurestr", featurestr);
/*     */   }
/*     */ 
/*     */   public List findByIsadapter(Object isadapter) {
/* 203 */     return findByProperty("isadapter", isadapter);
/*     */   }
/*     */ 
/*     */   public List findAll() {
/* 207 */     log.debug("finding all ContentInfo instances");
/*     */     try {
/* 209 */       String queryString = "from ContentInfo";
/* 210 */       Query queryObject = getSession().createQuery(queryString);
/* 211 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 213 */       log.error("find all failed", re);
/* 214 */     }throw re;
/*     */   }
/*     */ 
/*     */   public ContentInfo merge(ContentInfo detachedInstance)
/*     */   {
/* 219 */     log.debug("merging ContentInfo instance");
/*     */     try {
/* 221 */       ContentInfo result = (ContentInfo)getSession().merge(
/* 222 */         detachedInstance);
/* 223 */       log.debug("merge successful");
/* 224 */       return result;
/*     */     } catch (RuntimeException re) {
/* 226 */       log.error("merge failed", re);
/* 227 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(ContentInfo instance)
/*     */   {
/* 232 */     log.debug("attaching dirty ContentInfo instance");
/*     */     try {
/* 234 */       getSession().saveOrUpdate(instance);
/* 235 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 237 */       log.error("attach failed", re);
/* 238 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(ContentInfo instance) {
/* 243 */     log.debug("attaching clean ContentInfo instance");
/*     */     try {
/* 245 */       getSession().lock(instance, LockMode.NONE);
/* 246 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 248 */       log.error("attach failed", re);
/* 249 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentInfoDAO
 * JD-Core Version:    0.6.0
 */