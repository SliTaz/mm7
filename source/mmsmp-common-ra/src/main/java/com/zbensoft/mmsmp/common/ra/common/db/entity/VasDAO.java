/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.hibernate.LockMode;
/*     */ import org.springframework.orm.hibernate3.HibernateTemplate;
/*     */ import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/*     */ 
/*     */ public class VasDAO extends HibernateDaoSupport
/*     */ {
/*  17 */   private static final Log log = LogFactory.getLog(VasDAO.class);
/*     */   public static final String VASPID = "vaspid";
/*     */   public static final String VASID = "vasid";
/*     */   public static final String VASNAME = "vasname";
/*     */   public static final String VASURI = "vasuri";
/*     */   public static final String VASIPADDRESS = "vasipaddress";
/*     */   public static final String AOUSERNAME = "aousername";
/*     */   public static final String AOPASSWORD = "aopassword";
/*     */   public static final String AOAUTHENFLAG = "aoauthenflag";
/*     */   public static final String ATUSERNAME = "atusername";
/*     */   public static final String ATPASSWORD = "atpassword";
/*     */   public static final String ATAUTHENFLAG = "atauthenflag";
/*     */   public static final String VASALLOWEDFLAG = "vasallowedflag";
/*     */   public static final String MAXCONNLIMIT = "maxconnlimit";
/*     */   public static final String MAXTRAFFICLIMIT = "maxtrafficlimit";
/*     */   public static final String CREATEDATE = "createdate";
/*     */   public static final String CONTROLPRIORITY = "controlpriority";
/*     */   public static final String PROVINCECODE = "provincecode";
/*     */   public static final String CITYCODE = "citycode";
/*     */   public static final String PROTOCOL = "protocol";
/*     */ 
/*     */   public void save(Vas transientInstance)
/*     */   {
/*  42 */     log.debug("saving Vas instance");
/*     */     try {
/*  44 */       getHibernateTemplate().save(transientInstance);
/*  45 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  47 */       log.error("save failed", re);
/*  48 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(Vas persistentInstance) {
/*  53 */     log.debug("deleting Vas instance");
/*     */     try {
/*  55 */       getHibernateTemplate().delete(persistentInstance);
/*  56 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  58 */       log.error("delete failed", re);
/*  59 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<Vas> findAll()
/*     */   {
/*     */     try {
/*  66 */       String queryString = "from Vas as model ";
/*  67 */       List list = getHibernateTemplate().find(queryString);
/*  68 */       return list;
/*     */     } catch (RuntimeException re) {
/*  70 */       log.error("find by property name failed", re);
/*  71 */     }throw re;
/*     */   }
/*     */ 
/*     */   public Vas findById(Integer id)
/*     */   {
/*  76 */     log.debug("getting Vas instance with id: " + id);
/*     */     try {
/*  78 */       Vas instance = (Vas)getHibernateTemplate()
/*  79 */         .get("com.aceway.common.db.entity.Vas", id);
/*  80 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  82 */       log.error("get failed", re);
/*  83 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(Vas instance)
/*     */   {
/*  89 */     log.debug("finding Vas instance by example");
/*     */     try {
/*  91 */       List results = getHibernateTemplate().findByExample(instance);
/*     */ 
/*  93 */       log.debug("find by example successful, result size: " + results.size());
/*  94 */       return results;
/*     */     } catch (RuntimeException re) {
/*  96 */       log.error("find by example failed", re);
/*  97 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/* 102 */     log.debug("finding Vas instance with property: " + propertyName + 
/* 103 */       ", value: " + value);
/*     */     try {
/* 105 */       String queryString = "from Vas as model where model." + 
/* 106 */         propertyName + "= ?";
/* 107 */       List list = getHibernateTemplate().find(queryString, value);
/*     */ 
/* 109 */       return list;
/*     */     } catch (RuntimeException re) {
/* 111 */       log.error("find by property name failed", re);
/* 112 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByVaspid(Object vaspid)
/*     */   {
/* 117 */     return findByProperty("vaspid", vaspid);
/*     */   }
/*     */ 
/*     */   public List findByVasid(Object vasid) {
/* 121 */     return findByProperty("vasid", vasid);
/*     */   }
/*     */ 
/*     */   public List findByVasname(Object vasname) {
/* 125 */     return findByProperty("vasname", vasname);
/*     */   }
/*     */ 
/*     */   public List findByVasuri(Object vasuri) {
/* 129 */     return findByProperty("vasuri", vasuri);
/*     */   }
/*     */ 
/*     */   public List findByVasipaddress(Object vasipaddress) {
/* 133 */     return findByProperty("vasipaddress", vasipaddress);
/*     */   }
/*     */ 
/*     */   public List findByAousername(Object aousername) {
/* 137 */     return findByProperty("aousername", aousername);
/*     */   }
/*     */ 
/*     */   public List findByAopassword(Object aopassword) {
/* 141 */     return findByProperty("aopassword", aopassword);
/*     */   }
/*     */ 
/*     */   public List findByAoauthenflag(Object aoauthenflag) {
/* 145 */     return findByProperty("aoauthenflag", aoauthenflag);
/*     */   }
/*     */ 
/*     */   public List findByAtusername(Object atusername) {
/* 149 */     return findByProperty("atusername", atusername);
/*     */   }
/*     */ 
/*     */   public List findByAtpassword(Object atpassword) {
/* 153 */     return findByProperty("atpassword", atpassword);
/*     */   }
/*     */ 
/*     */   public List findByAtauthenflag(Object atauthenflag) {
/* 157 */     return findByProperty("atauthenflag", atauthenflag);
/*     */   }
/*     */ 
/*     */   public List findByVasallowedflag(Object vasallowedflag) {
/* 161 */     return findByProperty("vasallowedflag", vasallowedflag);
/*     */   }
/*     */ 
/*     */   public List findByMaxconnlimit(Object maxconnlimit) {
/* 165 */     return findByProperty("maxconnlimit", maxconnlimit);
/*     */   }
/*     */ 
/*     */   public List findByMaxtrafficlimit(Object maxtrafficlimit) {
/* 169 */     return findByProperty("maxtrafficlimit", maxtrafficlimit);
/*     */   }
/*     */ 
/*     */   public List findByCreatedate(Object createdate) {
/* 173 */     return findByProperty("createdate", createdate);
/*     */   }
/*     */ 
/*     */   public List findByControlpriority(Object controlpriority) {
/* 177 */     return findByProperty("controlpriority", controlpriority);
/*     */   }
/*     */ 
/*     */   public List findByProvincecode(Object provincecode) {
/* 181 */     return findByProperty("provincecode", provincecode);
/*     */   }
/*     */ 
/*     */   public List findByCitycode(Object citycode) {
/* 185 */     return findByProperty("citycode", citycode);
/*     */   }
/*     */ 
/*     */   public List findByProtocol(Object protocol) {
/* 189 */     return findByProperty("protocol", protocol);
/*     */   }
/*     */ 
/*     */   public Vas merge(Vas detachedInstance) {
/* 193 */     log.debug("merging Vas instance");
/*     */     try {
/* 195 */       Vas result = (Vas)getHibernateTemplate()
/* 196 */         .merge(detachedInstance);
/* 197 */       log.debug("merge successful");
/* 198 */       return result;
/*     */     } catch (RuntimeException re) {
/* 200 */       log.error("merge failed", re);
/* 201 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(Vas instance)
/*     */   {
/* 206 */     log.debug("attaching dirty Vas instance");
/*     */     try {
/* 208 */       getHibernateTemplate().saveOrUpdate(instance);
/* 209 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 211 */       log.error("attach failed", re);
/* 212 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(Vas instance) {
/* 217 */     log.debug("attaching clean Vas instance");
/*     */     try {
/* 219 */       getHibernateTemplate().lock(instance, LockMode.NONE);
/* 220 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 222 */       log.error("attach failed", re);
/* 223 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VasDAO
 * JD-Core Version:    0.6.0
 */