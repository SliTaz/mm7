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
/*     */ public class FlowFilesDAO extends BaseHibernateDAO
/*     */ {
/*  18 */   private static final Log log = LogFactory.getLog(FlowFilesDAO.class);
/*     */   public static final String FLOWNAME = "flowname";
/*     */   public static final String FLOWCODE = "flowcode";
/*     */   public static final String AUTHOR = "author";
/*     */   public static final String STATUS = "status";
/*     */   public static final String FLOWFILE = "flowfile";
/*     */   public static final String FLOWDESC = "flowdesc";
/*     */ 
/*     */   public void save(FlowFiles transientInstance)
/*     */   {
/*  33 */     log.debug("saving FlowFiles instance");
/*     */     try {
/*  35 */       getSession().save(transientInstance);
/*  36 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  38 */       log.error("save failed", re);
/*  39 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(FlowFiles persistentInstance)
/*     */   {
/*  48 */     log.debug("deleting FlowFiles instance");
/*     */     try {
/*  50 */       getSession().delete(persistentInstance);
/*  51 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  53 */       log.error("delete failed", re);
/*  54 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public FlowFiles findById(Integer id)
/*     */   {
/*  65 */     log.debug("getting FlowFiles instance with id: " + id);
/*     */     try {
/*  67 */       FlowFiles instance = (FlowFiles)getSession().get("com.aceway.common.db.entity.FlowFiles", id);
/*  68 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  70 */       log.error("get failed", re);
/*  71 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(FlowFiles instance)
/*     */   {
/*  82 */     log.debug("finding FlowFiles instance by example");
/*     */     try
/*     */     {
/*  86 */       List results = getSession()
/*  87 */         .createCriteria("com.aceway.common.db.entity.FlowFiles").add(Example.create(instance)).list();
/*     */ 
/*  89 */       if (log.isDebugEnabled()) {
/*  90 */         log.debug("Find by example successful, result size: " + results.size());
/*     */       }
/*  92 */       return results;
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  96 */       log.error("find by example failed", re);
/*  97 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<FlowFiles> findAll()
/*     */   {
/*     */     try
/*     */     {
/* 104 */       String queryString = "from FlowFiles as model";
/* 105 */       Query queryObject = getSession().createQuery(queryString);
/* 106 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 108 */       log.error("find by property name failed", re);
/* 109 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 120 */     log.debug("finding FlowFiles instance with property: " + propertyName + 
/* 121 */       ", value: " + value);
/*     */     try {
/* 123 */       String queryString = "from FlowFiles as model where model." + 
/* 124 */         propertyName + "= ?";
/* 125 */       Query queryObject = getSession().createQuery(queryString);
/* 126 */       queryObject.setParameter(0, value);
/* 127 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 129 */       log.error("find by property name failed", re);
/* 130 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByFlowname(Object flowname)
/*     */   {
/* 135 */     return findByProperty("flowname", flowname);
/*     */   }
/*     */ 
/*     */   public List findByFlowcode(Object flowcode) {
/* 139 */     return findByProperty("flowcode", flowcode);
/*     */   }
/*     */ 
/*     */   public List findByAuthor(Object author) {
/* 143 */     return findByProperty("author", author);
/*     */   }
/*     */ 
/*     */   public List findByStatus(Object status) {
/* 147 */     return findByProperty("status", status);
/*     */   }
/*     */ 
/*     */   public List findByFlowfile(Object flowfile) {
/* 151 */     return findByProperty("flowfile", flowfile);
/*     */   }
/*     */ 
/*     */   public List findByFlowdesc(Object flowdesc) {
/* 155 */     return findByProperty("flowdesc", flowdesc);
/*     */   }
/*     */ 
/*     */   public FlowFiles merge(FlowFiles detachedInstance) {
/* 159 */     log.debug("merging FlowFiles instance");
/*     */     try {
/* 161 */       FlowFiles result = (FlowFiles)getSession()
/* 162 */         .merge(detachedInstance);
/* 163 */       log.debug("merge successful");
/* 164 */       return result;
/*     */     } catch (RuntimeException re) {
/* 166 */       log.error("merge failed", re);
/* 167 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(FlowFiles instance)
/*     */   {
/* 176 */     log.debug("attaching dirty FlowFiles instance");
/*     */     try {
/* 178 */       getSession().saveOrUpdate(instance);
/* 179 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 181 */       log.error("attach failed", re);
/* 182 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(FlowFiles instance)
/*     */   {
/* 191 */     log.debug("attaching clean FlowFiles instance");
/*     */     try {
/* 193 */       getSession().lock(instance, LockMode.NONE);
/* 194 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 196 */       log.error("attach failed", re);
/* 197 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.FlowFilesDAO
 * JD-Core Version:    0.6.0
 */