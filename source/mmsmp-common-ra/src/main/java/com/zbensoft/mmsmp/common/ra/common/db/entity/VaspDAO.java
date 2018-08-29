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
/*     */ public class VaspDAO extends BaseHibernateDAO
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(VaspDAO.class);
/*     */   public static final String VASPID = "vaspid";
/*     */   public static final String VASPNAME = "vaspname";
/*     */   public static final String BUSINESSPHONE = "businessphone";
/*     */   public static final String CONTACTPERSON = "contactperson";
/*     */   public static final String FAX = "fax";
/*     */   public static final String EMAILADDRESS = "emailaddress";
/*     */   public static final String WEBADDRESS = "webaddress";
/*     */   public static final String OFFICEADDRESS = "officeaddress";
/*     */   public static final String CREATEDATE = "createdate";
/*     */   public static final String PROVINCE = "province";
/*     */   public static final String STATUS = "status";
/*     */   public static final String UPDATEDATE = "updatedate";
/*     */   public static final String VASPDESC = "vaspdesc";
/*     */ 
/*     */   public void save(Vasp transientInstance)
/*     */   {
/*  36 */     log.debug("saving Vasp instance");
/*     */     try {
/*  38 */       getSession().save(transientInstance);
/*  39 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  41 */       log.error("save failed", re);
/*  42 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(Vasp persistentInstance) {
/*  47 */     log.debug("deleting Vasp instance");
/*     */     try {
/*  49 */       getSession().delete(persistentInstance);
/*  50 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  52 */       log.error("delete failed", re);
/*  53 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vasp findById(Integer id) {
/*  58 */     log.debug("getting Vasp instance with id: " + id);
/*     */     try {
/*  60 */       Vasp instance = (Vasp)getSession()
/*  61 */         .get("com.aceway.common.db.entity.Vasp", id);
/*  62 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  64 */       log.error("get failed", re);
/*  65 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List<Vasp> findAll()
/*     */   {
/*     */     try
/*     */     {
/*  73 */       String queryString = "from Vasp as model ";
/*  74 */       Query queryObject = getSession().createQuery(queryString);
/*     */ 
/*  76 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/*  78 */       log.error("find by property name failed", re);
/*  79 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(Vasp instance)
/*     */   {
/*  84 */     log.debug("finding Vasp instance by example");
/*     */     try {
/*  86 */       List results = getSession()
/*  87 */         .createCriteria("com.aceway.common.db.entity.Vasp")
/*  88 */         .add(Example.create(instance))
/*  89 */         .list();
/*  90 */       log.debug("find by example successful, result size: " + results.size());
/*  91 */       return results;
/*     */     } catch (RuntimeException re) {
/*  93 */       log.error("find by example failed", re);
/*  94 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  99 */     log.debug("finding Vasp instance with property: " + propertyName + 
/* 100 */       ", value: " + value);
/*     */     try {
/* 102 */       String queryString = "from Vasp as model where model." + 
/* 103 */         propertyName + "= ?";
/* 104 */       Query queryObject = getSession().createQuery(queryString);
/* 105 */       queryObject.setParameter(0, value);
/* 106 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 108 */       log.error("find by property name failed", re);
/* 109 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByVaspid(Object vaspid)
/*     */   {
/* 114 */     return findByProperty("vaspid", vaspid);
/*     */   }
/*     */ 
/*     */   public List findByVaspname(Object vaspname) {
/* 118 */     return findByProperty("vaspname", vaspname);
/*     */   }
/*     */ 
/*     */   public List findByBusinessphone(Object businessphone) {
/* 122 */     return findByProperty("businessphone", businessphone);
/*     */   }
/*     */ 
/*     */   public List findByContactperson(Object contactperson) {
/* 126 */     return findByProperty("contactperson", contactperson);
/*     */   }
/*     */ 
/*     */   public List findByFax(Object fax) {
/* 130 */     return findByProperty("fax", fax);
/*     */   }
/*     */ 
/*     */   public List findByEmailaddress(Object emailaddress) {
/* 134 */     return findByProperty("emailaddress", emailaddress);
/*     */   }
/*     */ 
/*     */   public List findByWebaddress(Object webaddress) {
/* 138 */     return findByProperty("webaddress", webaddress);
/*     */   }
/*     */ 
/*     */   public List findByOfficeaddress(Object officeaddress) {
/* 142 */     return findByProperty("officeaddress", officeaddress);
/*     */   }
/*     */ 
/*     */   public List findByCreatedate(Object createdate) {
/* 146 */     return findByProperty("createdate", createdate);
/*     */   }
/*     */ 
/*     */   public List findByProvince(Object province) {
/* 150 */     return findByProperty("province", province);
/*     */   }
/*     */ 
/*     */   public List findByStatus(Object status) {
/* 154 */     return findByProperty("status", status);
/*     */   }
/*     */ 
/*     */   public List findByUpdatedate(Object updatedate) {
/* 158 */     return findByProperty("updatedate", updatedate);
/*     */   }
/*     */ 
/*     */   public List findByVaspdesc(Object vaspdesc) {
/* 162 */     return findByProperty("vaspdesc", vaspdesc);
/*     */   }
/*     */ 
/*     */   public Vasp merge(Vasp detachedInstance) {
/* 166 */     log.debug("merging Vasp instance");
/*     */     try {
/* 168 */       Vasp result = (Vasp)getSession()
/* 169 */         .merge(detachedInstance);
/* 170 */       log.debug("merge successful");
/* 171 */       return result;
/*     */     } catch (RuntimeException re) {
/* 173 */       log.error("merge failed", re);
/* 174 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(Vasp instance)
/*     */   {
/* 179 */     log.debug("attaching dirty Vasp instance");
/*     */     try {
/* 181 */       getSession().saveOrUpdate(instance);
/* 182 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 184 */       log.error("attach failed", re);
/* 185 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(Vasp instance) {
/* 190 */     log.debug("attaching clean Vasp instance");
/*     */     try {
/* 192 */       getSession().lock(instance, LockMode.NONE);
/* 193 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 195 */       log.error("attach failed", re);
/* 196 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VaspDAO
 * JD-Core Version:    0.6.0
 */