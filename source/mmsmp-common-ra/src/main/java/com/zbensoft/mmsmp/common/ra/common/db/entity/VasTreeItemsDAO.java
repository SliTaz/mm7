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
/*     */ public class VasTreeItemsDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(VasTreeItemsDAO.class);
/*     */ 
/*     */   public void save(VasTreeItems transientInstance)
/*     */   {
/*  23 */     log.debug("saving VasTreeItems instance");
/*     */     try {
/*  25 */       getSession().save(transientInstance);
/*  26 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  28 */       log.error("save failed", re);
/*  29 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(VasTreeItems persistentInstance) {
/*  34 */     log.debug("deleting VasTreeItems instance");
/*     */     try {
/*  36 */       getSession().delete(persistentInstance);
/*  37 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  39 */       log.error("delete failed", re);
/*  40 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public VasTreeItems findById(VasTreeItemsId id) {
/*  45 */     log.debug("getting VasTreeItems instance with id: " + id);
/*     */     try {
/*  47 */       VasTreeItems instance = (VasTreeItems)getSession()
/*  48 */         .get("com.aceway.common.db.entity.VasTreeItems", id);
/*  49 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  51 */       log.error("get failed", re);
/*  52 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(VasTreeItems instance)
/*     */   {
/*  58 */     log.debug("finding VasTreeItems instance by example");
/*     */     try {
/*  60 */       List results = getSession()
/*  61 */         .createCriteria("com.aceway.common.db.entity.VasTreeItems")
/*  62 */         .add(Example.create(instance))
/*  63 */         .list();
/*  64 */       log.debug("find by example successful, result size: " + results.size());
/*  65 */       return results;
/*     */     } catch (RuntimeException re) {
/*  67 */       log.error("find by example failed", re);
/*  68 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  73 */     log.debug("finding VasTreeItems instance with property: " + propertyName + 
/*  74 */       ", value: " + value);
/*     */     try {
/*  76 */       String queryString = "from VasTreeItems as model where model." + 
/*  77 */         propertyName + "= ?";
/*  78 */       Query queryObject = getSession().createQuery(queryString);
/*  79 */       queryObject.setParameter(0, value);
/*  80 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  82 */       log.error("find by property name failed", re);
/*  83 */     }throw re;
/*     */   }
/*     */ 
/*     */   public VasTreeItems merge(VasTreeItems detachedInstance)
/*     */   {
/*  88 */     log.debug("merging VasTreeItems instance");
/*     */     try {
/*  90 */       VasTreeItems result = (VasTreeItems)getSession()
/*  91 */         .merge(detachedInstance);
/*  92 */       log.debug("merge successful");
/*  93 */       return result;
/*     */     } catch (RuntimeException re) {
/*  95 */       log.error("merge failed", re);
/*  96 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(VasTreeItems instance)
/*     */   {
/* 101 */     log.debug("attaching dirty VasTreeItems instance");
/*     */     try {
/* 103 */       getSession().saveOrUpdate(instance);
/* 104 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 106 */       log.error("attach failed", re);
/* 107 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(VasTreeItems instance) {
/* 112 */     log.debug("attaching clean VasTreeItems instance");
/*     */     try {
/* 114 */       getSession().lock(instance, LockMode.NONE);
/* 115 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 117 */       log.error("attach failed", re);
/* 118 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VasTreeItemsDAO
 * JD-Core Version:    0.6.0
 */