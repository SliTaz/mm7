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
/*     */ public class UserInfoDAO extends BaseHibernateDAO
/*     */ {
/*  24 */   private static final Log log = LogFactory.getLog(UserInfoDAO.class);
/*     */   public static final String CELLPHONENUMBER = "cellphonenumber";
/*     */   public static final String TERMINALTYPE = "terminaltype";
/*     */   public static final String PROVINCECODE = "provincecode";
/*     */   public static final String CITYCODE = "citycode";
/*     */   public static final String STATUS = "status";
/*     */   public static final String USERPWD = "userpwd";
/*     */   public static final String NAME = "name";
/*     */   public static final String NICKNAME = "nickname";
/*     */   public static final String GENDER = "gender";
/*     */   public static final String PROFESSION = "profession";
/*     */   public static final String EMAIL = "email";
/*     */   public static final String ADDRESS = "address";
/*     */   public static final String LIKING = "liking";
/*     */   public static final String POSTALCODE = "postalcode";
/*     */   public static final String COUNTRY = "country";
/*     */   public static final String FOLK = "folk";
/*     */   public static final String MARRIAGE = "marriage";
/*     */   public static final String CERTIFICATETYPE = "certificatetype";
/*     */   public static final String CERTIFICATENO = "certificateno";
/*     */ 
/*     */   public void save(UserInfo transientInstance)
/*     */   {
/*  47 */     log.debug("saving UserInfo instance");
/*     */     try {
/*  49 */       getSession().save(transientInstance);
/*  50 */       log.debug("save successful");
/*     */     } catch (RuntimeException re) {
/*  52 */       log.error("save failed", re);
/*  53 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(UserInfo persistentInstance) {
/*  58 */     log.debug("deleting UserInfo instance");
/*     */     try {
/*  60 */       getSession().delete(persistentInstance);
/*  61 */       log.debug("delete successful");
/*     */     } catch (RuntimeException re) {
/*  63 */       log.error("delete failed", re);
/*  64 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public UserInfo findById(Integer id) {
/*  69 */     log.debug("getting UserInfo instance with id: " + id);
/*     */     try {
/*  71 */       UserInfo instance = (UserInfo)getSession().get(
/*  72 */         "com.aceway.common.db.entity.UserInfo", id);
/*  73 */       return instance;
/*     */     } catch (RuntimeException re) {
/*  75 */       log.error("get failed", re);
/*  76 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByExample(UserInfo instance)
/*     */   {
/*  81 */     log.debug("finding UserInfo instance by example");
/*     */     try {
/*  83 */       List results = getSession().createCriteria(
/*  84 */         "com.aceway.common.db.entity.UserInfo").add(
/*  85 */         Example.create(instance)).list();
/*  86 */       log.debug("find by example successful, result size: " + 
/*  87 */         results.size());
/*  88 */       return results;
/*     */     } catch (RuntimeException re) {
/*  90 */       log.error("find by example failed", re);
/*  91 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByProperty(String propertyName, Object value)
/*     */   {
/*  97 */     log.debug("finding UserInfo instance with property: " + propertyName + 
/*  98 */       ", value: " + value);
/*     */     try {
/* 100 */       String queryString = "from UserInfo as model where model." + 
/* 101 */         propertyName + "= ?";
/* 102 */       Query queryObject = getSession().createQuery(queryString);
/* 103 */       queryObject.setParameter(0, value);
/* 104 */       List list = queryObject.list();
/* 105 */       return list;
/*     */     } catch (RuntimeException re) {
/* 107 */       log.error("find by property name failed", re);
/* 108 */     }throw re;
/*     */   }
/*     */ 
/*     */   public List findByCellphonenumber(Object cellphonenumber)
/*     */   {
/* 113 */     return findByProperty("cellphonenumber", cellphonenumber);
/*     */   }
/*     */ 
/*     */   public List findByTerminaltype(Object terminaltype) {
/* 117 */     return findByProperty("terminaltype", terminaltype);
/*     */   }
/*     */ 
/*     */   public List findByProvincecode(Object provincecode) {
/* 121 */     return findByProperty("provincecode", provincecode);
/*     */   }
/*     */ 
/*     */   public List findByCitycode(Object citycode) {
/* 125 */     return findByProperty("citycode", citycode);
/*     */   }
/*     */ 
/*     */   public List findByStatus(Object status) {
/* 129 */     return findByProperty("status", status);
/*     */   }
/*     */ 
/*     */   public List findByUserpwd(Object userpwd) {
/* 133 */     return findByProperty("userpwd", userpwd);
/*     */   }
/*     */ 
/*     */   public List findByName(Object name) {
/* 137 */     return findByProperty("name", name);
/*     */   }
/*     */ 
/*     */   public List findByNickname(Object nickname) {
/* 141 */     return findByProperty("nickname", nickname);
/*     */   }
/*     */ 
/*     */   public List findByGender(Object gender) {
/* 145 */     return findByProperty("gender", gender);
/*     */   }
/*     */ 
/*     */   public List findByProfession(Object profession) {
/* 149 */     return findByProperty("profession", profession);
/*     */   }
/*     */ 
/*     */   public List findByEmail(Object email) {
/* 153 */     return findByProperty("email", email);
/*     */   }
/*     */ 
/*     */   public List findByAddress(Object address) {
/* 157 */     return findByProperty("address", address);
/*     */   }
/*     */ 
/*     */   public List findByLiking(Object liking) {
/* 161 */     return findByProperty("liking", liking);
/*     */   }
/*     */ 
/*     */   public List findByPostalcode(Object postalcode) {
/* 165 */     return findByProperty("postalcode", postalcode);
/*     */   }
/*     */ 
/*     */   public List findByCountry(Object country) {
/* 169 */     return findByProperty("country", country);
/*     */   }
/*     */ 
/*     */   public List findByFolk(Object folk) {
/* 173 */     return findByProperty("folk", folk);
/*     */   }
/*     */ 
/*     */   public List findByMarriage(Object marriage) {
/* 177 */     return findByProperty("marriage", marriage);
/*     */   }
/*     */ 
/*     */   public List findByCertificatetype(Object certificatetype) {
/* 181 */     return findByProperty("certificatetype", certificatetype);
/*     */   }
/*     */ 
/*     */   public List findByCertificateno(Object certificateno) {
/* 185 */     return findByProperty("certificateno", certificateno);
/*     */   }
/*     */ 
/*     */   public List findAll() {
/* 189 */     log.debug("finding all UserInfo instances");
/*     */     try {
/* 191 */       String queryString = "from UserInfo";
/* 192 */       Query queryObject = getSession().createQuery(queryString);
/* 193 */       return queryObject.list();
/*     */     } catch (RuntimeException re) {
/* 195 */       log.error("find all failed", re);
/* 196 */     }throw re;
/*     */   }
/*     */ 
/*     */   public UserInfo merge(UserInfo detachedInstance)
/*     */   {
/* 201 */     log.debug("merging UserInfo instance");
/*     */     try {
/* 203 */       UserInfo result = (UserInfo)getSession().merge(detachedInstance);
/* 204 */       log.debug("merge successful");
/* 205 */       return result;
/*     */     } catch (RuntimeException re) {
/* 207 */       log.error("merge failed", re);
/* 208 */     }throw re;
/*     */   }
/*     */ 
/*     */   public void attachDirty(UserInfo instance)
/*     */   {
/* 213 */     log.debug("attaching dirty UserInfo instance");
/*     */     try {
/* 215 */       getSession().saveOrUpdate(instance);
/* 216 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 218 */       log.error("attach failed", re);
/* 219 */       throw re;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attachClean(UserInfo instance) {
/* 224 */     log.debug("attaching clean UserInfo instance");
/*     */     try {
/* 226 */       getSession().lock(instance, LockMode.NONE);
/* 227 */       log.debug("attach successful");
/*     */     } catch (RuntimeException re) {
/* 229 */       log.error("attach failed", re);
/* 230 */       throw re;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.UserInfoDAO
 * JD-Core Version:    0.6.0
 */