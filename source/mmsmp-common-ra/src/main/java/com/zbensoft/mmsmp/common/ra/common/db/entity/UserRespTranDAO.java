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
/*     */ public class UserRespTranDAO extends BaseHibernateDAO
/*     */ {
/*  19 */   private static final Log log = LogFactory.getLog(UserRespTranDAO.class);
/*     */   public static final String WAITMESSAGETYPE = "waitmessagetype";
/*     */   public static final String FLOWID = "flowid";
/*     */   public static final String CURRENTFLOWNODE = "currentflownode";
/*     */   public static final String EXECUTENODESLIST = "executenodeslist";
/*     */   public static final String CONTEXTOBJFILE = "contextobjfile";
/*     */   public static final String SMSPATTERN = "smspattern";
/*     */   public static final String OPCODEPATTERN = "opcodepattern";
/*     */ 
/*     */   public void save(UserRespTran transientInstance)
/*     */   {
/*  36 */     log.debug("saving UserRespTran instance");
/*     */     try
/*     */     {
/*  39 */       getSession().save(transientInstance);
/*  40 */       log.debug("save successful");
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  44 */       log.error("save failed", re);
/*  45 */       getSession().evict(transientInstance);
/*  46 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(UserRespTran persistentInstance)
/*     */   {
/*  56 */     log.debug("deleting UserRespTran instance");
/*     */     try
/*     */     {
/*  59 */       getSession().delete(persistentInstance);
/*  60 */       log.debug("delete successful");
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/*  64 */       log.error("delete failed", re);
/*  65 */       getSession().evict(persistentInstance);
/*  66 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserRespTran findById(UserRespTranId id)
/*     */   {
/*  76 */     log.debug("getting UserRespTran instance with id: " + id);
/*     */     try {
/*  78 */       UserRespTran instance = (UserRespTran)getSession()
/*  79 */         .get("com.aceway.common.db.entity.UserRespTran", id);
/*  80 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  82 */       log.error("get failed", re);
/*  83 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(UserRespTran instance)
/*     */   {
/*  94 */     log.debug("finding UserRespTran instance by example");
/*     */     try
/*     */     {
/*  98 */       List results = getSession().createCriteria("com.aceway.common.db.entity.UserRespTran").add(Example.create(instance)).list();
/*     */ 
/* 100 */       if (log.isDebugEnabled()) {
/* 101 */         log.debug("find by example successful, result size: " + results.size());
/*     */       }
/* 103 */       return results;
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 107 */       log.error("find by example failed", re);
/* 108 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 120 */     if (log.isDebugEnabled()) {
/* 121 */       log.debug("finding UserRespTran instance with property: " + propertyName + ", value: " + value);
/*     */     }
/*     */     try
/*     */     {
/* 125 */       String queryString = "from UserRespTran as model where model." + propertyName + "= ?";
/* 126 */       Query queryObject = getSession().createQuery(queryString);
/* 127 */       queryObject.setParameter(0, value);
/* 128 */       return queryObject.list();
/*     */     }
/*     */     catch (RuntimeException re)
/*     */     {
/* 132 */       log.error("find by property name failed", re);
/* 133 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByWaitmessagetype(Object waitmessagetype)
/*     */   {
/* 143 */     return findByProperty("waitmessagetype", waitmessagetype);
/*     */   }
/*     */ 
/*     */   public List findByFlowid(Object flowid)
/*     */   {
/* 152 */     return findByProperty("flowid", flowid);
/*     */   }
/*     */ 
/*     */   public List findByCurrentflownode(Object currentflownode)
/*     */   {
/* 161 */     return findByProperty("currentflownode", currentflownode);
/*     */   }
/*     */ 
/*     */   public List findByExecutenodeslist(Object executenodeslist)
/*     */   {
/* 170 */     return findByProperty("executenodeslist", executenodeslist);
/*     */   }
/*     */ 
/*     */   public List findByContextobjfile(Object contextobjfile) {
/* 174 */     return findByProperty("contextobjfile", contextobjfile);
/*     */   }
/*     */ 
/*     */   public List findBySmspattern(Object smspattern) {
/* 178 */     return findByProperty("smspattern", smspattern);
/*     */   }
/*     */ 
/*     */   public List findByOpcodepattern(Object opcodepattern) {
/* 182 */     return findByProperty("opcodepattern", opcodepattern);
/*     */   }
/*     */ 
/*     */   public UserRespTran merge(UserRespTran detachedInstance) {
/* 186 */     log.debug("merging UserRespTran instance");
/*     */     try {
/* 188 */       UserRespTran result = (UserRespTran)getSession()
/* 189 */         .merge(detachedInstance);
/* 190 */       log.debug("merge successful");
/* 191 */       return result;
/*     */     } catch (RuntimeException re) {
/* 193 */       log.error("merge failed", re);
/* 194 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(UserRespTran instance)
/*     */   {
/* 199 */     log.debug("attaching dirty UserRespTran instance");
/*     */     try {
/* 201 */       getSession().saveOrUpdate(instance);
/* 202 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 204 */       log.error("attach failed", re);
/* 205 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(UserRespTran instance) {
/* 210 */     log.debug("attaching clean UserRespTran instance");
/*     */     try {
/* 212 */       getSession().lock(instance, LockMode.NONE);
/* 213 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 215 */       log.error("attach failed", re);
/* 216 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserRespTranDAO
 * JD-Core Version:    0.6.0
 */