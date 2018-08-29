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
/*     */ public class AdTasksDAO extends BaseHibernateDAO
/*     */ {
/*  19 */   private static final Log log = LogFactory.getLog(AdTasksDAO.class);
/*     */   public static final String TASKNAME = "taskname";
/*     */   public static final String TASKDESC = "taskdesc";
/*     */   public static final String ADTYPE = "adtype";
/*     */   public static final String STARTDAY = "startday";
/*     */   public static final String ENDDAY = "endday";
/*     */   public static final String STARTTIME = "starttime";
/*     */   public static final String ENDTIME = "endtime";
/*     */   public static final String ADTEXT = "adtext";
/*     */   public static final String PUSHURL = "pushurl";
/*     */   public static final String CONTENTID = "contentid";
/*     */   public static final String PHONESFILE = "phonesfile";
/*     */   public static final String STATUS = "status";
/*     */   public static final String PRIORITY = "priority";
/*     */   public static final String CPID = "cpid";
/*     */   public static final String SENDER = "sender";
/*     */   public static final String CHARGEPARTY = "chargeparty";
/*     */   public static final String SERVICEUNIQUEID = "serviceuniqueid";
/*     */ 
/*     */   public void save(AdTasks transientInstance)
/*     */   {
/*  42 */     log.debug("saving AdTasks instance");
/*     */     try {
/*  44 */       getSession().save(transientInstance);
/*  45 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  47 */       log.error("save failed", re);
/*  48 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(AdTasks persistentInstance) {
/*  53 */     log.debug("deleting AdTasks instance");
/*     */     try {
/*  55 */       getSession().delete(persistentInstance);
/*  56 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  58 */       log.error("delete failed", re);
/*  59 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public AdTasks findById(Integer id) {
/*  64 */     log.debug("getting AdTasks instance with id: " + id);
/*     */     try {
/*  66 */       AdTasks instance = (AdTasks)getSession()
/*  67 */         .get("com.aceway.common.db.entity.AdTasks", id);
/*  68 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  70 */       log.error("get failed", re);
/*  71 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(AdTasks instance)
/*     */   {
/*  77 */     log.debug("finding AdTasks instance by example");
/*     */     try {
/*  79 */       List results = getSession()
/*  80 */         .createCriteria("com.aceway.common.db.entity.AdTasks")
/*  81 */         .add(Example.create(instance))
/*  82 */         .list();
/*  83 */       log.debug("find by example successful, result size: " + results.size());
/*  84 */       return results;
/*     */     } catch (RuntimeException re) {
/*  86 */       log.error("find by example failed", re);
/*  87 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  92 */     log.debug("finding AdTasks instance with property: " + propertyName + 
/*  93 */       ", value: " + value);
/*     */     try {
/*  95 */       String queryString = "from AdTasks as model where model." + 
/*  96 */         propertyName + "= ?";
/*  97 */       Query queryObject = getSession().createQuery(queryString);
/*  98 */       queryObject.setParameter(0, value);
/*  99 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 101 */       log.error("find by property name failed", re);
/* 102 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByTaskname(Object taskname)
/*     */   {
/* 107 */     return findByProperty("taskname", taskname);
/*     */   }
/*     */ 
/*     */   public List findByTaskdesc(Object taskdesc) {
/* 111 */     return findByProperty("taskdesc", taskdesc);
/*     */   }
/*     */ 
/*     */   public List findByAdtype(Object adtype) {
/* 115 */     return findByProperty("adtype", adtype);
/*     */   }
/*     */ 
/*     */   public List findByStartday(Object startday) {
/* 119 */     return findByProperty("startday", startday);
/*     */   }
/*     */ 
/*     */   public List findByEndday(Object endday) {
/* 123 */     return findByProperty("endday", endday);
/*     */   }
/*     */ 
/*     */   public List findByStarttime(Object starttime) {
/* 127 */     return findByProperty("starttime", starttime);
/*     */   }
/*     */ 
/*     */   public List findByEndtime(Object endtime) {
/* 131 */     return findByProperty("endtime", endtime);
/*     */   }
/*     */ 
/*     */   public List findByAdtext(Object adtext) {
/* 135 */     return findByProperty("adtext", adtext);
/*     */   }
/*     */ 
/*     */   public List findByPushurl(Object pushurl) {
/* 139 */     return findByProperty("pushurl", pushurl);
/*     */   }
/*     */ 
/*     */   public List findByContentid(Object contentid) {
/* 143 */     return findByProperty("contentid", contentid);
/*     */   }
/*     */ 
/*     */   public List findByPhonesfile(Object phonesfile) {
/* 147 */     return findByProperty("phonesfile", phonesfile);
/*     */   }
/*     */ 
/*     */   public List findByStatus(Object status) {
/* 151 */     return findByProperty("status", status);
/*     */   }
/*     */ 
/*     */   public List findByPriority(Object priority) {
/* 155 */     return findByProperty("priority", priority);
/*     */   }
/*     */ 
/*     */   public List findByCpid(Object cpid) {
/* 159 */     return findByProperty("cpid", cpid);
/*     */   }
/*     */ 
/*     */   public List findBySender(Object sender) {
/* 163 */     return findByProperty("sender", sender);
/*     */   }
/*     */ 
/*     */   public List findByChargeparty(Object chargeparty) {
/* 167 */     return findByProperty("chargeparty", chargeparty);
/*     */   }
/*     */ 
/*     */   public List findByServiceuniqueid(Object serviceuniqueid) {
/* 171 */     return findByProperty("serviceuniqueid", serviceuniqueid);
/*     */   }
/*     */ 
/*     */   public AdTasks merge(AdTasks detachedInstance) {
/* 175 */     log.debug("merging AdTasks instance");
/*     */     try {
/* 177 */       AdTasks result = (AdTasks)getSession()
/* 178 */         .merge(detachedInstance);
/* 179 */       log.debug("merge successful");
/* 180 */       return result;
/*     */     } catch (RuntimeException re) {
/* 182 */       log.error("merge failed", re);
/* 183 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(AdTasks instance)
/*     */   {
/* 188 */     log.debug("attaching dirty AdTasks instance");
/*     */     try {
/* 190 */       getSession().saveOrUpdate(instance);
/* 191 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 193 */       log.error("attach failed", re);
/* 194 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(AdTasks instance) {
/* 199 */     log.debug("attaching clean AdTasks instance");
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
 * Qualified Name:     com.aceway.common.db.entity.AdTasksDAO
 * JD-Core Version:    0.6.0
 */