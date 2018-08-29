/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import org.hibernate.HibernateException;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.SessionFactory;
/*     */ import org.hibernate.Transaction;
/*     */ import org.hibernate.cfg.Configuration;
/*     */ 
/*     */ public class Entity
/*     */ {
/*     */   private static SessionFactory sessionFactory;
/*     */   private static Configuration configuration;
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  27 */       configuration = 
/*  28 */         new Configuration()
/*  29 */         .addClass(CpInfo.class)
/*  30 */         .addClass(CpInfoTemp.class);
/*  31 */       sessionFactory = configuration.buildSessionFactory();
/*     */     }
/*     */     catch (HibernateException e)
/*     */     {
/*  35 */       configuration = null;
/*  36 */       sessionFactory = null;
/*  37 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static SessionFactory getSessionFactory() {
/*  42 */     return sessionFactory;
/*     */   }
/*     */ 
/*     */   public static Configuration getConfiguration() {
/*  46 */     return configuration;
/*     */   }
/*     */ 
/*     */   public static Session getSession() {
/*  50 */     if (sessionFactory == null)
/*  51 */       return null;
/*     */     try
/*     */     {
/*  54 */       return sessionFactory.openSession();
/*     */     }
/*     */     catch (HibernateException e) {
/*  57 */       e.printStackTrace();
/*  58 */     }return null;
/*     */   }
/*     */ 
/*     */   public static boolean Commit(Session session)
/*     */   {
/*     */     try
/*     */     {
/*  66 */       session.flush();
/*  67 */       session.connection().commit();
/*  68 */       session.close();
/*     */     }
/*     */     catch (HibernateException e)
/*     */     {
/*  72 */       e.printStackTrace();
/*  73 */       return false;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*  77 */       e.printStackTrace();
/*  78 */       return false;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  82 */       e.printStackTrace();
/*  83 */       return false;
/*     */     }
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   public Transaction BeginTransaction(Session session) throws HibernateException
/*     */   {
/*  90 */     session = sessionFactory.openSession();
/*  91 */     return session.beginTransaction();
/*     */   }
/*     */ 
/*     */   public void EndTransaction(Session session, Transaction transaction, boolean commit)
/*     */     throws HibernateException
/*     */   {
/*  99 */     if (commit) {
/* 100 */       transaction.commit();
/*     */     }
/*     */     else {
/* 103 */       transaction.rollback();
/*     */     }
/* 105 */     session.close();
/*     */   }
/*     */ 
/*     */   public static String reformatString(String str) {
/* 109 */     if (str == null) {
/* 110 */       return "";
/*     */     }
/* 112 */     return str;
/*     */   }
/*     */ 
/*     */   public static String decodeString(String str) {
/* 116 */     if (str == null)
/* 117 */       return "";
/*     */     try
/*     */     {
/* 120 */       return str;
/*     */     }
/*     */     catch (Exception e) {
/* 123 */       e.printStackTrace();
/* 124 */     }return "";
/*     */   }
/*     */ 
/*     */   public static String encodeString(String str)
/*     */   {
/* 129 */     if (str == null)
/* 130 */       return "";
/*     */     try
/*     */     {
/* 133 */       return str;
/*     */     }
/*     */     catch (Exception e) {
/* 136 */       e.printStackTrace();
/* 137 */     }return "";
/*     */   }
/*     */ 
/*     */   public static String decodeObject(Object obj)
/*     */   {
/* 142 */     if (obj == null) {
/* 143 */       return "";
/*     */     }
/* 145 */     return obj.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.Entity
 * JD-Core Version:    0.6.0
 */