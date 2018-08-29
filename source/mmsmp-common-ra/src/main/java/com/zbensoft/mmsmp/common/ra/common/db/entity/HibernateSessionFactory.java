/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.hibernate.HibernateException;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.SessionFactory;
/*     */ import org.hibernate.cfg.Configuration;
/*     */ 
/*     */ public class HibernateSessionFactory
/*     */ {
/*  18 */   private static final Logger log = Logger.getLogger(HibernateSessionFactory.class);
/*     */ 
/*  28 */   private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
/*  29 */   private static final ThreadLocal<Session> threadLocal = new ThreadLocal();
/*  30 */   private static Configuration configuration = new Configuration();
/*  31 */   private static SessionFactory sessionFactory = null;
/*  32 */   private static String configFile = CONFIG_FILE_LOCATION;
/*     */ 
/*     */   public static Session getSession()
/*     */     throws HibernateException
/*     */   {
/*  47 */     Session session = (Session)threadLocal.get();
/*     */ 
/*  49 */     if ((session == null) || (!session.isOpen())) {
/*  50 */       if (sessionFactory == null) {
/*  51 */         rebuildSessionFactory();
/*     */       }
/*  53 */       session = sessionFactory != null ? sessionFactory.openSession() : null;
/*  54 */       threadLocal.set(session);
/*     */     }
/*  56 */     session.clear();
/*  57 */     return session;
/*     */   }
/*     */ 
/*     */   public static void rebuildSessionFactory()
/*     */   {
/*     */     try
/*     */     {
/*  68 */       configuration.configure(configFile);
/*  69 */       log.debug(configuration.buildSessionFactory().getClass().getName());
/*  70 */       sessionFactory = configuration.buildSessionFactory();
/*     */     } catch (Exception e) {
/*  72 */       System.err.println("%%%% Error Creating SessionFactory %%%%");
/*  73 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeSession()
/*     */     throws HibernateException
/*     */   {
/*  84 */     Session session = (Session)threadLocal.get();
/*  85 */     threadLocal.set(null);
/*     */ 
/*  87 */     if (session != null)
/*  88 */       session.close();
/*     */   }
/*     */ 
/*     */   public static SessionFactory getSessionFactory()
/*     */   {
/*  97 */     return sessionFactory;
/*     */   }
/*     */ 
/*     */   public static void setConfigFile(String configFile)
/*     */   {
/* 108 */     configFile = configFile;
/* 109 */     sessionFactory = null;
/*     */   }
/*     */ 
/*     */   public static Configuration getConfiguration()
/*     */   {
/* 117 */     return configuration;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.HibernateSessionFactory
 * JD-Core Version:    0.6.0
 */