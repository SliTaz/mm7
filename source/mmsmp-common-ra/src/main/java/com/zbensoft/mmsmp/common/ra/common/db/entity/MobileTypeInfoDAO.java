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
/*     */ public class MobileTypeInfoDAO extends BaseHibernateDAO
/*     */ {
/*  23 */   private static final Log log = LogFactory.getLog(MobileTypeInfoDAO.class);
/*     */   public static final String VENDERNAME = "vendername";
/*     */   public static final String MOBILETYPENAME = "mobiletypename";
/*     */   public static final String PICTYPE = "pictype";
/*     */   public static final String AUDIOTYPE = "audiotype";
/*     */   public static final String POLYPHONICNUM = "polyphonicnum";
/*     */   public static final String VIDEOTYPE = "videotype";
/*     */   public static final String CHARSET = "charset";
/*     */   public static final String SCREENCOLOR = "screencolor";
/*     */   public static final String SMILSPT = "smilspt";
/*     */   public static final String WAPSPT = "wapspt";
/*     */   public static final String MMSSPT = "mmsspt";
/*     */   public static final String WAPPUSHSPT = "wappushspt";
/*     */   public static final String MMSMAXVAL = "mmsmaxval";
/*     */   public static final String UANAME = "uaname";
/*     */   public static final String SCREENSIZE = "screensize";
/*     */ 
/*     */   public void save(MobileTypeInfo transientInstance)
/*     */   {
/*  42 */     log.debug("saving MobileTypeInfo instance");
/*     */     try {
/*  44 */       getSession().save(transientInstance);
/*  45 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  47 */       log.error("save failed", re);
/*  48 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(MobileTypeInfo persistentInstance) {
/*  53 */     log.debug("deleting MobileTypeInfo instance");
/*     */     try {
/*  55 */       getSession().delete(persistentInstance);
/*  56 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  58 */       log.error("delete failed", re);
/*  59 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public MobileTypeInfo findById(Integer id) {
/*  64 */     log.debug("getting MobileTypeInfo instance with id: " + id);
/*     */     try {
/*  66 */       MobileTypeInfo instance = (MobileTypeInfo)getSession().get(
/*  67 */         "com.aceway.common.db.entity.MobileTypeInfo", id);
/*  68 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  70 */       log.error("get failed", re);
/*  71 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(MobileTypeInfo instance)
/*     */   {
/*  76 */     log.debug("finding MobileTypeInfo instance by example");
/*     */     try {
/*  78 */       List results = getSession().createCriteria(
/*  79 */         "com.aceway.common.db.entity.MobileTypeInfo").add(
/*  80 */         Example.create(instance)).list();
/*  81 */       log.debug("find by example successful, result size: " + 
/*  82 */         results.size());
/*  83 */       return results;
/*     */     } catch (RuntimeException re) {
/*  85 */       log.error("find by example failed", re);
/*  86 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  91 */     log.debug("finding MobileTypeInfo instance with property: " + 
/*  92 */       propertyName + ", value: " + value);
/*     */     try {
/*  94 */       String queryString = "from MobileTypeInfo as model where model." + 
/*  95 */         propertyName + "= ?";
/*  96 */       Query queryObject = getSession().createQuery(queryString);
/*  97 */       queryObject.setParameter(0, value);
/*  98 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 100 */       log.error("find by property name failed", re);
/* 101 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByVendername(Object vendername)
/*     */   {
/* 106 */     return findByProperty("vendername", vendername);
/*     */   }
/*     */ 
/*     */   public List findByMobiletypename(Object mobiletypename) {
/* 110 */     return findByProperty("mobiletypename", mobiletypename);
/*     */   }
/*     */ 
/*     */   public List findByPictype(Object pictype) {
/* 114 */     return findByProperty("pictype", pictype);
/*     */   }
/*     */ 
/*     */   public List findByAudiotype(Object audiotype) {
/* 118 */     return findByProperty("audiotype", audiotype);
/*     */   }
/*     */ 
/*     */   public List findByPolyphonicnum(Object polyphonicnum) {
/* 122 */     return findByProperty("polyphonicnum", polyphonicnum);
/*     */   }
/*     */ 
/*     */   public List findByVideotype(Object videotype) {
/* 126 */     return findByProperty("videotype", videotype);
/*     */   }
/*     */ 
/*     */   public List findByCharset(Object charset) {
/* 130 */     return findByProperty("charset", charset);
/*     */   }
/*     */ 
/*     */   public List findByScreencolor(Object screencolor) {
/* 134 */     return findByProperty("screencolor", screencolor);
/*     */   }
/*     */ 
/*     */   public List findBySmilspt(Object smilspt) {
/* 138 */     return findByProperty("smilspt", smilspt);
/*     */   }
/*     */ 
/*     */   public List findByWapspt(Object wapspt) {
/* 142 */     return findByProperty("wapspt", wapspt);
/*     */   }
/*     */ 
/*     */   public List findByMmsspt(Object mmsspt) {
/* 146 */     return findByProperty("mmsspt", mmsspt);
/*     */   }
/*     */ 
/*     */   public List findByWappushspt(Object wappushspt) {
/* 150 */     return findByProperty("wappushspt", wappushspt);
/*     */   }
/*     */ 
/*     */   public List findByMmsmaxval(Object mmsmaxval) {
/* 154 */     return findByProperty("mmsmaxval", mmsmaxval);
/*     */   }
/*     */ 
/*     */   public List findByUaname(Object uaname) {
/* 158 */     return findByProperty("uaname", uaname);
/*     */   }
/*     */ 
/*     */   public List findByScreensize(Object screensize) {
/* 162 */     return findByProperty("screensize", screensize);
/*     */   }
/*     */ 
/*     */   public List findAll() {
/* 166 */     log.debug("finding all MobileTypeInfo instances");
/*     */     try {
/* 168 */       String queryString = "from MobileTypeInfo";
/* 169 */       Query queryObject = getSession().createQuery(queryString);
/* 170 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 172 */       log.error("find all failed", re);
/* 173 */     }throw re;
/*     */   }
/*     */ 
/*     */   public MobileTypeInfo merge(MobileTypeInfo detachedInstance)
/*     */   {
/* 178 */     log.debug("merging MobileTypeInfo instance");
/*     */     try {
/* 180 */       MobileTypeInfo result = (MobileTypeInfo)getSession().merge(
/* 181 */         detachedInstance);
/* 182 */       log.debug("merge successful");
/* 183 */       return result;
/*     */     } catch (RuntimeException re) {
/* 185 */       log.error("merge failed", re);
/* 186 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(MobileTypeInfo instance)
/*     */   {
/* 191 */     log.debug("attaching dirty MobileTypeInfo instance");
/*     */     try {
/* 193 */       getSession().saveOrUpdate(instance);
/* 194 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 196 */       log.error("attach failed", re);
/* 197 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(MobileTypeInfo instance) {
/* 202 */     log.debug("attaching clean MobileTypeInfo instance");
/*     */     try {
/* 204 */       getSession().lock(instance, LockMode.NONE);
/* 205 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 207 */       log.error("attach failed", re);
/* 208 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.MobileTypeInfoDAO
 * JD-Core Version:    0.6.0
 */