/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.hibernate.LockMode;
/*     */ import org.springframework.orm.hibernate3.HibernateTemplate;
/*     */ import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/*     */ 
/*     */ public class VasserviceDAO extends HibernateDaoSupport
/*     */ {
/*  16 */   private static final Log log = LogFactory.getLog(VasserviceDAO.class);
/*     */   public static final String VASPID = "vaspid";
/*     */   public static final String VASID = "vasid";
/*     */   public static final String SERVICECODE = "servicecode";
/*     */   public static final String SERVICENAME = "servicename";
/*     */   public static final String SERVICETYPE = "servicetype";
/*     */   public static final String FEETYPE = "feetype";
/*     */   public static final String STATUS = "status";
/*     */   public static final String RUNSTATUS = "runstatus";
/*     */   public static final String CREATEDATE = "createdate";
/*     */   public static final String UPDATEDATE = "updatedate";
/*     */   public static final String SERVICEDESC = "servicedesc";
/*     */   public static final String ORDERFEE = "orderfee";
/*     */   public static final String ONDEMANDCODE = "ondemandcode";
/*     */   public static final String ONDEMANDFEE = "ondemandfee";
/*     */   public static final String ONDEMANDDESC = "ondemanddesc";
/*     */   public static final String FEEDESC = "feedesc";
/*     */   public static final String MTCONTENTMODE = "mtcontentmode";
/*     */   public static final String MTMODE = "mtmode";
/*     */   public static final String DEFAULTFLAG = "defaultflag";
/*     */   public static final String SMSKEY = "smskey";
/*     */   public static final String TREEID = "treeid";
/*     */   public static final String ORDERCODE = "ordercode";
/*     */   public static final String CANCELORDERCODE = "cancelordercode";
/*     */   public static final String VIEWPIC = "viewpic";
/*     */   public static final String SMALLPIC = "smallpic";
/*     */   public static final String FEATURESTRNUM = "featurestrnum";
/*     */ 
/*     */   public void saveOrUpdate(Vasservice transientInstance)
/*     */   {
/*  49 */     log.debug("saving Vasservice instance");
/*     */     try
/*     */     {
/*  52 */       getHibernateTemplate().saveOrUpdate(transientInstance);
/*  53 */       log.debug("save successful");
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  57 */       log.error("save failed", re);
/*  58 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(Vasservice persistentInstance)
/*     */   {
/*  68 */     log.debug("deleting Vasservice instance");
/*     */     try
/*     */     {
/*  71 */       getHibernateTemplate().delete(persistentInstance);
/*  72 */       log.debug("delete successful");
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  76 */       log.error("delete failed", re);
/*  77 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vasservice findById(Integer id)
/*     */   {
/*  88 */     log.debug("getting Vasservice instance with id: " + id);
/*     */     try
/*     */     {
/*  91 */       Vasservice instance = (Vasservice)getHibernateTemplate().get("com.aceway.common.db.entity.Vasservice", id);
/*  92 */       return instance;
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  96 */       log.error("get failed", re);
/*  97 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(Vasservice instance)
/*     */   {
/* 108 */     log.debug("finding Vasservice instance by example");
/*     */     try {
/* 110 */       List results = getHibernateTemplate().findByExample(instance);
/*     */ 
/* 112 */       if (log.isDebugEnabled()) {
/* 113 */         log.debug("find by example successful, result size: " + results.size());
/*     */       }
/* 115 */       return results;
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 119 */       log.error("find by example failed", re);
/* 120 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<Vasservice> findAll()
/*     */   {
/*     */     try
/*     */     {
/* 127 */       String queryString = "from Vasservice as model ";
/* 128 */       List list = getHibernateTemplate().find(queryString);
/* 129 */       return list;
/*     */     } catch (RuntimeException re) {
/* 131 */       log.error("find by property name failed", re);
/* 132 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<Vasservice> findNormalStatus()
/*     */   {
/*     */     try
/*     */     {
/* 139 */       String queryString = "from Vasservice as model where model.status ='2' and model.dealStatus = 0 or model.dealStatus =1";
/* 140 */       List list = getHibernateTemplate().find(queryString);
/* 141 */       return list;
/*     */     } catch (RuntimeException re) {
/* 143 */       log.error("find by property name failed", re);
/* 144 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 149 */     log.debug("finding Vasservice instance with property: " + propertyName + 
/* 150 */       ", value: " + value);
/*     */     try {
/* 152 */       String queryString = "from Vasservice as model where model." + 
/* 153 */         propertyName + "= ?";
/* 154 */       List list = getHibernateTemplate().find(queryString, value);
/*     */ 
/* 156 */       return list;
/*     */     } catch (RuntimeException re) {
/* 158 */       log.error("find by property name failed", re);
/* 159 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByVaspid(Object vaspid)
/*     */   {
/* 164 */     return findByProperty("vaspid", vaspid);
/*     */   }
/*     */ 
/*     */   public List findByVasid(Object vasid) {
/* 168 */     return findByProperty("vasid", vasid);
/*     */   }
/*     */ 
/*     */   public List findByServicecode(Object servicecode) {
/* 172 */     return findByProperty("servicecode", servicecode);
/*     */   }
/*     */ 
/*     */   public List findByServicename(Object servicename) {
/* 176 */     return findByProperty("servicename", servicename);
/*     */   }
/*     */ 
/*     */   public List findByServicetype(Object servicetype) {
/* 180 */     return findByProperty("servicetype", servicetype);
/*     */   }
/*     */ 
/*     */   public List findByFeetype(Object feetype) {
/* 184 */     return findByProperty("feetype", feetype);
/*     */   }
/*     */ 
/*     */   public List findByStatus(Object status) {
/* 188 */     return findByProperty("status", status);
/*     */   }
/*     */ 
/*     */   public List findByRunstatus(Object runstatus) {
/* 192 */     return findByProperty("runstatus", runstatus);
/*     */   }
/*     */ 
/*     */   public List findByCreatedate(Object createdate) {
/* 196 */     return findByProperty("createdate", createdate);
/*     */   }
/*     */ 
/*     */   public List findByUpdatedate(Object updatedate) {
/* 200 */     return findByProperty("updatedate", updatedate);
/*     */   }
/*     */ 
/*     */   public List findByServicedesc(Object servicedesc) {
/* 204 */     return findByProperty("servicedesc", servicedesc);
/*     */   }
/*     */ 
/*     */   public List findByOrderfee(Object orderfee) {
/* 208 */     return findByProperty("orderfee", orderfee);
/*     */   }
/*     */ 
/*     */   public List findByOndemandfee(Object ondemandfee) {
/* 212 */     return findByProperty("ondemandfee", ondemandfee);
/*     */   }
/*     */ 
/*     */   public List findByOndemanddesc(Object ondemanddesc) {
/* 216 */     return findByProperty("ondemanddesc", ondemanddesc);
/*     */   }
/*     */ 
/*     */   public List findByFeedesc(Object feedesc) {
/* 220 */     return findByProperty("feedesc", feedesc);
/*     */   }
/*     */ 
/*     */   public List findByMtcontentmode(Object mtcontentmode) {
/* 224 */     return findByProperty("mtcontentmode", mtcontentmode);
/*     */   }
/*     */ 
/*     */   public List findByMtmode(Object mtmode) {
/* 228 */     return findByProperty("mtmode", mtmode);
/*     */   }
/*     */ 
/*     */   public List findByDefaultflag(Object defaultflag) {
/* 232 */     return findByProperty("defaultflag", defaultflag);
/*     */   }
/*     */ 
/*     */   public List findBySmskey(Object smskey) {
/* 236 */     return findByProperty("smskey", smskey);
/*     */   }
/*     */ 
/*     */   public List findByTreeid(Object treeid) {
/* 240 */     return findByProperty("treeid", treeid);
/*     */   }
/*     */ 
/*     */   public List findByOrdercode(Object ordercode) {
/* 244 */     return findByProperty("ordercode", ordercode);
/*     */   }
/*     */ 
/*     */   public List findByCancelordercode(Object cancelordercode) {
/* 248 */     return findByProperty("cancelordercode", cancelordercode);
/*     */   }
/*     */ 
/*     */   public List findByViewpic(Object viewpic) {
/* 252 */     return findByProperty("viewpic", viewpic);
/*     */   }
/*     */ 
/*     */   public List findBySmallpic(Object smallpic) {
/* 256 */     return findByProperty("smallpic", smallpic);
/*     */   }
/*     */ 
/*     */   public List findByFeaturestrnum(Object featurestrnum) {
/* 260 */     return findByProperty("featurestrnum", featurestrnum);
/*     */   }
/*     */ 
/*     */   public List findByOndemandCode(Object ondemandcode)
/*     */   {
/* 270 */     return findByProperty("ondemandcode", ondemandcode);
/*     */   }
/*     */ 
/*     */   public Vasservice merge(Vasservice detachedInstance) {
/* 274 */     log.debug("merging Vasservice instance");
/*     */     try {
/* 276 */       Vasservice result = (Vasservice)getHibernateTemplate()
/* 277 */         .merge(detachedInstance);
/* 278 */       log.debug("merge successful");
/* 279 */       return result;
/*     */     } catch (RuntimeException re) {
/* 281 */       log.error("merge failed", re);
/* 282 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(Vasservice instance)
/*     */   {
/* 291 */     log.debug("attaching dirty Vasservice instance");
/*     */     try {
/* 293 */       getHibernateTemplate().saveOrUpdate(instance);
/* 294 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 296 */       log.error("attach failed", re);
/* 297 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(Vasservice instance)
/*     */   {
/* 306 */     log.debug("attaching clean Vasservice instance");
/*     */     try {
/* 308 */       getHibernateTemplate().lock(instance, LockMode.NONE);
/* 309 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 311 */       log.error("attach failed", re);
/* 312 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VasserviceDAO
 * JD-Core Version:    0.6.0
 */