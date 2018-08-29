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
/*     */ public class ServFlowRelationDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(ServFlowRelationDAO.class);
/*     */   public static final String OPCODEPATTERN = "opcodepattern";
/*     */   public static final String SMSPATTERN = "smspattern";
/*     */   public static final String DESCRIPTION = "description";
/*     */   public static final String MMS = "mms";
/*     */   public static final String SMS = "sms";
/*     */   public static final String WWWONDEMAND = "wwwondemand";
/*     */   public static final String USERORDER = "userorder";
/*     */   public static final String USERCANCELORDER = "usercancelorder";
/*     */   public static final String WWWUSERORDERUSE = "wwwuserorderuse";
/*     */ 
/*     */   public void save(ServFlowRelation transientInstance)
/*     */   {
/*  32 */     log.debug("saving ServFlowRelation instance");
/*     */     try {
/*  34 */       getSession().save(transientInstance);
/*  35 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  37 */       log.error("save failed", re);
/*  38 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(ServFlowRelation persistentInstance) {
/*  43 */     log.debug("deleting ServFlowRelation instance");
/*     */     try {
/*  45 */       getSession().delete(persistentInstance);
/*  46 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  48 */       log.error("delete failed", re);
/*  49 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ServFlowRelation findById(ServFlowRelationId id) {
/*  54 */     log.debug("getting ServFlowRelation instance with id: " + id);
/*     */     try {
/*  56 */       ServFlowRelation instance = (ServFlowRelation)getSession()
/*  57 */         .get("com.aceway.common.db.entity.ServFlowRelation", id);
/*  58 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  60 */       log.error("get failed", re);
/*  61 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(ServFlowRelation instance)
/*     */   {
/*  67 */     log.debug("finding ServFlowRelation instance by example");
/*     */     try {
/*  69 */       List results = getSession()
/*  70 */         .createCriteria("com.aceway.common.db.entity.ServFlowRelation")
/*  71 */         .add(Example.create(instance))
/*  72 */         .list();
/*  73 */       log.debug("find by example successful, result size: " + results.size());
/*  74 */       return results;
/*     */     } catch (RuntimeException re) {
/*  76 */       log.error("find by example failed", re);
/*  77 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<ServFlowRelation> findAll()
/*     */   {
/*     */     try {
/*  83 */       String queryString = "from ServFlowRelation as model";
/*  84 */       Query queryObject = getSession().createQuery(queryString);
/*  85 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  87 */       log.error("find by property name failed", re);
/*  88 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  93 */     log.debug("finding ServFlowRelation instance with property: " + propertyName + 
/*  94 */       ", value: " + value);
/*     */     try {
/*  96 */       String queryString = "from ServFlowRelation as model where model." + 
/*  97 */         propertyName + "= ?";
/*  98 */       Query queryObject = getSession().createQuery(queryString);
/*  99 */       queryObject.setParameter(0, value);
/* 100 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 102 */       log.error("find by property name failed", re);
/* 103 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByOpcodepattern(Object opcodepattern)
/*     */   {
/* 108 */     return findByProperty("opcodepattern", opcodepattern);
/*     */   }
/*     */ 
/*     */   public List findBySmspattern(Object smspattern) {
/* 112 */     return findByProperty("smspattern", smspattern);
/*     */   }
/*     */ 
/*     */   public List findByDescription(Object description) {
/* 116 */     return findByProperty("description", description);
/*     */   }
/*     */ 
/*     */   public List findByMms(Object mms) {
/* 120 */     return findByProperty("mms", mms);
/*     */   }
/*     */ 
/*     */   public List findBySms(Object sms) {
/* 124 */     return findByProperty("sms", sms);
/*     */   }
/*     */ 
/*     */   public List findByWwwondemand(Object wwwondemand) {
/* 128 */     return findByProperty("wwwondemand", wwwondemand);
/*     */   }
/*     */ 
/*     */   public List findByUserorder(Object userorder) {
/* 132 */     return findByProperty("userorder", userorder);
/*     */   }
/*     */ 
/*     */   public List findByUsercancelorder(Object usercancelorder) {
/* 136 */     return findByProperty("usercancelorder", usercancelorder);
/*     */   }
/*     */ 
/*     */   public List findByWwwuserorderuse(Object wwwuserorderuse) {
/* 140 */     return findByProperty("wwwuserorderuse", wwwuserorderuse);
/*     */   }
/*     */ 
/*     */   public ServFlowRelation merge(ServFlowRelation detachedInstance) {
/* 144 */     log.debug("merging ServFlowRelation instance");
/*     */     try {
/* 146 */       ServFlowRelation result = (ServFlowRelation)getSession()
/* 147 */         .merge(detachedInstance);
/* 148 */       log.debug("merge successful");
/* 149 */       return result;
/*     */     } catch (RuntimeException re) {
/* 151 */       log.error("merge failed", re);
/* 152 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(ServFlowRelation instance)
/*     */   {
/* 157 */     log.debug("attaching dirty ServFlowRelation instance");
/*     */     try {
/* 159 */       getSession().saveOrUpdate(instance);
/* 160 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 162 */       log.error("attach failed", re);
/* 163 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(ServFlowRelation instance) {
/* 168 */     log.debug("attaching clean ServFlowRelation instance");
/*     */     try {
/* 170 */       getSession().lock(instance, LockMode.NONE);
/* 171 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 173 */       log.error("attach failed", re);
/* 174 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServFlowRelationDAO
 * JD-Core Version:    0.6.0
 */