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
/*     */ public class VasTreeDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(VasTreeDAO.class);
/*     */   public static final String PARENTID = "parentid";
/*     */   public static final String ISLEAF = "isleaf";
/*     */   public static final String NAME = "name";
/*     */   public static final String DESCRIPTION = "description";
/*     */   public static final String CREATEDATE = "createdate";
/*     */   public static final String UPDATEDATE = "updatedate";
/*     */ 
/*     */   public void save(VasTree transientInstance)
/*     */   {
/*  29 */     log.debug("saving VasTree instance");
/*     */     try {
/*  31 */       getSession().save(transientInstance);
/*  32 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  34 */       log.error("save failed", re);
/*  35 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(VasTree persistentInstance) {
/*  40 */     log.debug("deleting VasTree instance");
/*     */     try {
/*  42 */       getSession().delete(persistentInstance);
/*  43 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  45 */       log.error("delete failed", re);
/*  46 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public VasTree findById(String id) {
/*  51 */     log.debug("getting VasTree instance with id: " + id);
/*     */     try {
/*  53 */       VasTree instance = (VasTree)getSession()
/*  54 */         .get("com.aceway.common.db.entity.VasTree", id);
/*  55 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  57 */       log.error("get failed", re);
/*  58 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(VasTree instance)
/*     */   {
/*  64 */     log.debug("finding VasTree instance by example");
/*     */     try {
/*  66 */       List results = getSession()
/*  67 */         .createCriteria("com.aceway.common.db.entity.VasTree")
/*  68 */         .add(Example.create(instance))
/*  69 */         .list();
/*  70 */       log.debug("find by example successful, result size: " + results.size());
/*  71 */       return results;
/*     */     } catch (RuntimeException re) {
/*  73 */       log.error("find by example failed", re);
/*  74 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  79 */     log.debug("finding VasTree instance with property: " + propertyName + 
/*  80 */       ", value: " + value);
/*     */     try {
/*  82 */       String queryString = "from VasTree as model where model." + 
/*  83 */         propertyName + "= ?";
/*  84 */       Query queryObject = getSession().createQuery(queryString);
/*  85 */       queryObject.setParameter(0, value);
/*  86 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  88 */       log.error("find by property name failed", re);
/*  89 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByParentid(Object parentid)
/*     */   {
/*  94 */     return findByProperty("parentid", parentid);
/*     */   }
/*     */ 
/*     */   public List findByIsleaf(Object isleaf) {
/*  98 */     return findByProperty("isleaf", isleaf);
/*     */   }
/*     */ 
/*     */   public List findByName(Object name) {
/* 102 */     return findByProperty("name", name);
/*     */   }
/*     */ 
/*     */   public List findByDescription(Object description) {
/* 106 */     return findByProperty("description", description);
/*     */   }
/*     */ 
/*     */   public List findByCreatedate(Object createdate) {
/* 110 */     return findByProperty("createdate", createdate);
/*     */   }
/*     */ 
/*     */   public List findByUpdatedate(Object updatedate) {
/* 114 */     return findByProperty("updatedate", updatedate);
/*     */   }
/*     */ 
/*     */   public VasTree merge(VasTree detachedInstance) {
/* 118 */     log.debug("merging VasTree instance");
/*     */     try {
/* 120 */       VasTree result = (VasTree)getSession()
/* 121 */         .merge(detachedInstance);
/* 122 */       log.debug("merge successful");
/* 123 */       return result;
/*     */     } catch (RuntimeException re) {
/* 125 */       log.error("merge failed", re);
/* 126 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(VasTree instance)
/*     */   {
/* 131 */     log.debug("attaching dirty VasTree instance");
/*     */     try {
/* 133 */       getSession().saveOrUpdate(instance);
/* 134 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 136 */       log.error("attach failed", re);
/* 137 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(VasTree instance) {
/* 142 */     log.debug("attaching clean VasTree instance");
/*     */     try {
/* 144 */       getSession().lock(instance, LockMode.NONE);
/* 145 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 147 */       log.error("attach failed", re);
/* 148 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VasTreeDAO
 * JD-Core Version:    0.6.0
 */