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
/*     */ public class CpInfoDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(CpInfoDAO.class);
/*     */   public static final String CPNAME = "cpname";
/*     */   public static final String CPADDRESS = "cpaddress";
/*     */   public static final String ARTIFICIALPERSON = "artificialperson";
/*     */   public static final String BANKNAME = "bankname";
/*     */   public static final String BANKACCOUNT = "bankaccount";
/*     */   public static final String WEBSITE = "website";
/*     */   public static final String FAX = "fax";
/*     */   public static final String BUSINESSLINKMAN = "businesslinkman";
/*     */   public static final String BUSINESSLINKMANTEL = "businesslinkmantel";
/*     */   public static final String BUSINESSLINKMANMOBILE = "businesslinkmanmobile";
/*     */   public static final String BUSINESSLINKMANEMAIL = "businesslinkmanemail";
/*     */   public static final String CLIENTLINKMAN = "clientlinkman";
/*     */   public static final String CLIENTLINKMANTEL = "clientlinkmantel";
/*     */   public static final String CLIENTLINKMANMOBILE = "clientlinkmanmobile";
/*     */   public static final String CLIENTLINKMANEMAIL = "clientlinkmanemail";
/*     */   public static final String LOGINNO = "loginno";
/*     */   public static final String STATUS = "status";
/*     */   public static final String DELETEAPPLY = "deleteapply";
/*     */   public static final String REGEMAIL = "regemail";
/*     */   public static final String CPCODE = "cpcode";
/*     */   public static final String REJECTDESC = "rejectdesc";
/*     */   public static final String TECHLINKMAN = "techlinkman";
/*     */   public static final String TECHLINKMANTEL = "techlinkmantel";
/*     */   public static final String TECHLINKMANMOBILE = "techlinkmanmobile";
/*     */   public static final String TECHLINKMANEMAIL = "techlinkmanemail";
/*     */   public static final String FINANCELINKMAN = "financelinkman";
/*     */   public static final String FINANCELINKMANTEL = "financelinkmantel";
/*     */   public static final String FINANCELINKMANMOBILE = "financelinkmanmobile";
/*     */   public static final String FINANCELINKMANEMAIL = "financelinkmanemail";
/*     */ 
/*     */   public void save(CpInfo transientInstance)
/*     */   {
/*  52 */     log.debug("saving CpInfo instance");
/*     */     try {
/*  54 */       getSession().save(transientInstance);
/*  55 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  57 */       log.error("save failed", re);
/*  58 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(CpInfo persistentInstance) {
/*  63 */     log.debug("deleting CpInfo instance");
/*     */     try {
/*  65 */       getSession().delete(persistentInstance);
/*  66 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  68 */       log.error("delete failed", re);
/*  69 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public CpInfo findById(Integer id) {
/*  74 */     log.debug("getting CpInfo instance with id: " + id);
/*     */     try {
/*  76 */       CpInfo instance = (CpInfo)getSession()
/*  77 */         .get("com.aceway.common.db.entity.CpInfo", id);
/*  78 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  80 */       log.error("get failed", re);
/*  81 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(CpInfo instance)
/*     */   {
/*  87 */     log.debug("finding CpInfo instance by example");
/*     */     try {
/*  89 */       List results = getSession()
/*  90 */         .createCriteria("com.aceway.common.db.entity.CpInfo")
/*  91 */         .add(Example.create(instance))
/*  92 */         .list();
/*  93 */       log.debug("find by example successful, result size: " + results.size());
/*  94 */       return results;
/*     */     } catch (RuntimeException re) {
/*  96 */       log.error("find by example failed", re);
/*  97 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 102 */     log.debug("finding CpInfo instance with property: " + propertyName + 
/* 103 */       ", value: " + value);
/*     */     try {
/* 105 */       String queryString = "from CpInfo as model where model." + 
/* 106 */         propertyName + "= ?";
/* 107 */       Query queryObject = getSession().createQuery(queryString);
/* 108 */       queryObject.setParameter(0, value);
/* 109 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 111 */       log.error("find by property name failed", re);
/* 112 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByCpname(Object cpname)
/*     */   {
/* 117 */     return findByProperty("cpname", cpname);
/*     */   }
/*     */ 
/*     */   public List findByCpaddress(Object cpaddress) {
/* 121 */     return findByProperty("cpaddress", cpaddress);
/*     */   }
/*     */ 
/*     */   public List findByArtificialperson(Object artificialperson) {
/* 125 */     return findByProperty("artificialperson", artificialperson);
/*     */   }
/*     */ 
/*     */   public List findByBankname(Object bankname) {
/* 129 */     return findByProperty("bankname", bankname);
/*     */   }
/*     */ 
/*     */   public List findByBankaccount(Object bankaccount) {
/* 133 */     return findByProperty("bankaccount", bankaccount);
/*     */   }
/*     */ 
/*     */   public List findByWebsite(Object website) {
/* 137 */     return findByProperty("website", website);
/*     */   }
/*     */ 
/*     */   public List findByFax(Object fax) {
/* 141 */     return findByProperty("fax", fax);
/*     */   }
/*     */ 
/*     */   public List findByBusinesslinkman(Object businesslinkman) {
/* 145 */     return findByProperty("businesslinkman", businesslinkman);
/*     */   }
/*     */ 
/*     */   public List findByBusinesslinkmantel(Object businesslinkmantel) {
/* 149 */     return findByProperty("businesslinkmantel", businesslinkmantel);
/*     */   }
/*     */ 
/*     */   public List findByBusinesslinkmanmobile(Object businesslinkmanmobile) {
/* 153 */     return findByProperty("businesslinkmanmobile", businesslinkmanmobile);
/*     */   }
/*     */ 
/*     */   public List findByBusinesslinkmanemail(Object businesslinkmanemail) {
/* 157 */     return findByProperty("businesslinkmanemail", businesslinkmanemail);
/*     */   }
/*     */ 
/*     */   public List findByClientlinkman(Object clientlinkman) {
/* 161 */     return findByProperty("clientlinkman", clientlinkman);
/*     */   }
/*     */ 
/*     */   public List findByClientlinkmantel(Object clientlinkmantel) {
/* 165 */     return findByProperty("clientlinkmantel", clientlinkmantel);
/*     */   }
/*     */ 
/*     */   public List findByClientlinkmanmobile(Object clientlinkmanmobile) {
/* 169 */     return findByProperty("clientlinkmanmobile", clientlinkmanmobile);
/*     */   }
/*     */ 
/*     */   public List findByClientlinkmanemail(Object clientlinkmanemail) {
/* 173 */     return findByProperty("clientlinkmanemail", clientlinkmanemail);
/*     */   }
/*     */ 
/*     */   public List findByLoginno(Object loginno) {
/* 177 */     return findByProperty("loginno", loginno);
/*     */   }
/*     */ 
/*     */   public List findByStatus(Object status) {
/* 181 */     return findByProperty("status", status);
/*     */   }
/*     */ 
/*     */   public List findByDeleteapply(Object deleteapply) {
/* 185 */     return findByProperty("deleteapply", deleteapply);
/*     */   }
/*     */ 
/*     */   public List findByRegemail(Object regemail) {
/* 189 */     return findByProperty("regemail", regemail);
/*     */   }
/*     */ 
/*     */   public List findByCpcode(Object cpcode) {
/* 193 */     return findByProperty("cpcode", cpcode);
/*     */   }
/*     */ 
/*     */   public List findByRejectdesc(Object rejectdesc) {
/* 197 */     return findByProperty("rejectdesc", rejectdesc);
/*     */   }
/*     */ 
/*     */   public List findByTechlinkman(Object techlinkman) {
/* 201 */     return findByProperty("techlinkman", techlinkman);
/*     */   }
/*     */ 
/*     */   public List findByTechlinkmantel(Object techlinkmantel) {
/* 205 */     return findByProperty("techlinkmantel", techlinkmantel);
/*     */   }
/*     */ 
/*     */   public List findByTechlinkmanmobile(Object techlinkmanmobile) {
/* 209 */     return findByProperty("techlinkmanmobile", techlinkmanmobile);
/*     */   }
/*     */ 
/*     */   public List findByTechlinkmanemail(Object techlinkmanemail) {
/* 213 */     return findByProperty("techlinkmanemail", techlinkmanemail);
/*     */   }
/*     */ 
/*     */   public List findByFinancelinkman(Object financelinkman) {
/* 217 */     return findByProperty("financelinkman", financelinkman);
/*     */   }
/*     */ 
/*     */   public List findByFinancelinkmantel(Object financelinkmantel) {
/* 221 */     return findByProperty("financelinkmantel", financelinkmantel);
/*     */   }
/*     */ 
/*     */   public List findByFinancelinkmanmobile(Object financelinkmanmobile) {
/* 225 */     return findByProperty("financelinkmanmobile", financelinkmanmobile);
/*     */   }
/*     */ 
/*     */   public List findByFinancelinkmanemail(Object financelinkmanemail) {
/* 229 */     return findByProperty("financelinkmanemail", financelinkmanemail);
/*     */   }
/*     */ 
/*     */   public CpInfo merge(CpInfo detachedInstance) {
/* 233 */     log.debug("merging CpInfo instance");
/*     */     try {
/* 235 */       CpInfo result = (CpInfo)getSession()
/* 236 */         .merge(detachedInstance);
/* 237 */       log.debug("merge successful");
/* 238 */       return result;
/*     */     } catch (RuntimeException re) {
/* 240 */       log.error("merge failed", re);
/* 241 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(CpInfo instance)
/*     */   {
/* 246 */     log.debug("attaching dirty CpInfo instance");
/*     */     try {
/* 248 */       getSession().saveOrUpdate(instance);
/* 249 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 251 */       log.error("attach failed", re);
/* 252 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(CpInfo instance) {
/* 257 */     log.debug("attaching clean CpInfo instance");
/*     */     try {
/* 259 */       getSession().lock(instance, LockMode.NONE);
/* 260 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 262 */       log.error("attach failed", re);
/* 263 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.CpInfoDAO
 * JD-Core Version:    0.6.0
 */