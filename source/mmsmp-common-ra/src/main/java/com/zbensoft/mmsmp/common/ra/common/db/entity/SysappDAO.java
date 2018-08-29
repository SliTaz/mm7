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
/*     */ public class SysappDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(SysappDAO.class);
/*     */   public static final String IID = "iid";
/*     */   public static final String STRAPPTYPE = "strapptype";
/*     */   public static final String STRVENDORNAME = "strvendorname";
/*     */   public static final String STRAPPVERSION = "strappversion";
/*     */   public static final String STRAPPSERIAL = "strappserial";
/*     */   public static final String STRAPPAGENTIPADDRESS = "strappagentipaddress";
/*     */   public static final String STRAPPRUNSTARTEDTIME = "strapprunstartedtime";
/*     */   public static final String SHCURRENTRUNSTATUS = "shcurrentrunstatus";
/*     */   public static final String CARETASKS = "caretasks";
/*     */   public static final String STRAPPAGENTPORT = "strappagentport";
/*     */   public static final String COMMUNITYSTRING = "communitystring";
/*     */ 
/*     */   public void save(Sysapp transientInstance)
/*     */   {
/*  34 */     log.debug("saving Sysapp instance");
/*     */     try {
/*  36 */       getSession().save(transientInstance);
/*  37 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  39 */       log.error("save failed", re);
/*  40 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(Sysapp persistentInstance) {
/*  45 */     log.debug("deleting Sysapp instance");
/*     */     try {
/*  47 */       getSession().delete(persistentInstance);
/*  48 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  50 */       log.error("delete failed", re);
/*  51 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Sysapp findById(String id) {
/*  56 */     log.debug("getting Sysapp instance with id: " + id);
/*     */     try {
/*  58 */       Sysapp instance = (Sysapp)getSession()
/*  59 */         .get("com.aceway.common.db.entity.Sysapp", id);
/*  60 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  62 */       log.error("get failed", re);
/*  63 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(Sysapp instance)
/*     */   {
/*  69 */     log.debug("finding Sysapp instance by example");
/*     */     try {
/*  71 */       List results = getSession()
/*  72 */         .createCriteria("com.aceway.common.db.entity.Sysapp")
/*  73 */         .add(Example.create(instance))
/*  74 */         .list();
/*  75 */       log.debug("find by example successful, result size: " + results.size());
/*  76 */       return results;
/*     */     } catch (RuntimeException re) {
/*  78 */       log.error("find by example failed", re);
/*  79 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  84 */     log.debug("finding Sysapp instance with property: " + propertyName + 
/*  85 */       ", value: " + value);
/*     */     try {
/*  87 */       String queryString = "from Sysapp as model where model." + 
/*  88 */         propertyName + "= ?";
/*  89 */       Query queryObject = getSession().createQuery(queryString);
/*  90 */       queryObject.setParameter(0, value);
/*  91 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  93 */       log.error("find by property name failed", re);
/*  94 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findAll()
/*     */   {
/*     */     try
/*     */     {
/* 101 */       String queryString = "from Sysapp as model";
/* 102 */       Query queryObject = getSession().createQuery(queryString);
/*     */ 
/* 104 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 106 */       log.error("find by property name failed", re);
/* 107 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByIid(Object iid)
/*     */   {
/* 112 */     return findByProperty("iid", iid);
/*     */   }
/*     */ 
/*     */   public List findByStrapptype(Object strapptype) {
/* 116 */     return findByProperty("strapptype", strapptype);
/*     */   }
/*     */ 
/*     */   public List findByStrvendorname(Object strvendorname) {
/* 120 */     return findByProperty("strvendorname", strvendorname);
/*     */   }
/*     */ 
/*     */   public List findByStrappversion(Object strappversion) {
/* 124 */     return findByProperty("strappversion", strappversion);
/*     */   }
/*     */ 
/*     */   public List findByStrappserial(Object strappserial) {
/* 128 */     return findByProperty("strappserial", strappserial);
/*     */   }
/*     */ 
/*     */   public List findByStrappagentipaddress(Object strappagentipaddress) {
/* 132 */     return findByProperty("strappagentipaddress", strappagentipaddress);
/*     */   }
/*     */ 
/*     */   public List findByStrapprunstartedtime(Object strapprunstartedtime) {
/* 136 */     return findByProperty("strapprunstartedtime", strapprunstartedtime);
/*     */   }
/*     */ 
/*     */   public List findByShcurrentrunstatus(Object shcurrentrunstatus) {
/* 140 */     return findByProperty("shcurrentrunstatus", shcurrentrunstatus);
/*     */   }
/*     */ 
/*     */   public List findByCaretasks(Object caretasks) {
/* 144 */     return findByProperty("caretasks", caretasks);
/*     */   }
/*     */ 
/*     */   public List findByStrappagentport(Object strappagentport) {
/* 148 */     return findByProperty("strappagentport", strappagentport);
/*     */   }
/*     */ 
/*     */   public List findByCommunitystring(Object communitystring) {
/* 152 */     return findByProperty("communitystring", communitystring);
/*     */   }
/*     */ 
/*     */   public Sysapp merge(Sysapp detachedInstance) {
/* 156 */     log.debug("merging Sysapp instance");
/*     */     try {
/* 158 */       Sysapp result = (Sysapp)getSession()
/* 159 */         .merge(detachedInstance);
/* 160 */       log.debug("merge successful");
/* 161 */       return result;
/*     */     } catch (RuntimeException re) {
/* 163 */       log.error("merge failed", re);
/* 164 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(Sysapp instance)
/*     */   {
/* 169 */     log.debug("attaching dirty Sysapp instance");
/*     */     try {
/* 171 */       getSession().saveOrUpdate(instance);
/* 172 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 174 */       log.error("attach failed", re);
/* 175 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(Sysapp instance) {
/* 180 */     log.debug("attaching clean Sysapp instance");
/*     */     try {
/* 182 */       getSession().lock(instance, LockMode.NONE);
/* 183 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 185 */       log.error("attach failed", re);
/* 186 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.SysappDAO
 * JD-Core Version:    0.6.0
 */