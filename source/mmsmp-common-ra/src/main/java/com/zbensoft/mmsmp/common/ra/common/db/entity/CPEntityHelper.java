/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.hibernate.HibernateException;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ 
/*     */ public class CPEntityHelper
/*     */ {
/*  19 */   private static final Logger logger = Logger.getLogger(CPEntityHelper.class);
/*     */ 
/*     */   public static LinkedList getAllCP() {
/*  22 */     Session session = Entity.getSession();
/*  23 */     if (session == null) {
/*  24 */       return null;
/*     */     }
/*  26 */     LinkedList cpList = null;
/*     */     try {
/*  28 */       String queryString = "select CpInfo from CpInfo as CpInfo";
/*     */ 
/*  30 */       Query query = session.createQuery(queryString);
/*  31 */       cpList = new LinkedList();
/*  32 */       for (Iterator it = query.iterate(); it.hasNext(); ) {
/*  33 */         CpInfo cp = (CpInfo)it.next();
/*  34 */         cpList.add(cp);
/*     */       }
/*     */     }
/*     */     catch (HibernateException localHibernateException) {
/*     */     }
/*     */     try {
/*  40 */       session.close();
/*     */     }
/*     */     catch (HibernateException localHibernateException1) {
/*     */     }
/*  44 */     return cpList;
/*     */   }
/*     */ 
/*     */   public static CpInfo getCpInfoByID(int id) {
/*  48 */     Session session = Entity.getSession();
/*  49 */     if (session == null) {
/*  50 */       return null;
/*     */     }
/*  52 */     CpInfo cp = null;
/*     */     try {
/*  54 */       String queryString = "select CpInfo from CpInfo as CpInfo where CpInfo.id=" + id;
/*  55 */       Query query = session.createQuery(queryString);
/*  56 */       Iterator it = query.iterate(); if (it.hasNext())
/*  57 */         cp = (CpInfo)it.next();
/*     */     }
/*     */     catch (HibernateException localHibernateException)
/*     */     {
/*     */     }
/*     */     try
/*     */     {
/*  64 */       session.close();
/*     */     }
/*     */     catch (HibernateException localHibernateException1) {
/*     */     }
/*  68 */     return cp;
/*     */   }
/*     */ 
/*     */   public static void addCpInfo(CpInfo cpInfo) {
/*  72 */     Session session = Entity.getSession();
/*  73 */     if (session == null) {
/*  74 */       logger.error("no session found!");
/*  75 */       return;
/*     */     }
/*     */     try {
/*  78 */       session.save(cpInfo);
/*     */     } catch (HibernateException e) {
/*  80 */       logger.error(e);
/*     */     }
/*     */     try {
/*  83 */       Entity.Commit(session);
/*  84 */       session.close();
/*     */     } catch (HibernateException e) {
/*  86 */       logger.error(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void deleteCpInfo(CpInfo cpInfo) {
/*  91 */     Session session = Entity.getSession();
/*  92 */     if (session == null)
/*  93 */       return;
/*     */     try
/*     */     {
/*  96 */       if (cpInfo != null) {
/*  97 */         session.delete(cpInfo);
/*     */       }
/*  99 */       Entity.Commit(session);
/*     */     }
/*     */     catch (HibernateException localHibernateException) {
/*     */     }
/*     */     try {
/* 104 */       session.close();
/*     */     }
/*     */     catch (HibernateException localHibernateException1) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updateCpInfo(CpInfo cp) {
/* 111 */     Session session = Entity.getSession();
/* 112 */     if (session == null)
/* 113 */       return;
/*     */     try
/*     */     {
/* 116 */       session.update(cp);
/* 117 */       Entity.Commit(session);
/*     */     }
/*     */     catch (HibernateException localHibernateException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static LinkedList getAllTempCP() {
/* 124 */     Session session = Entity.getSession();
/* 125 */     if (session == null) {
/* 126 */       return null;
/*     */     }
/* 128 */     LinkedList cpList = null;
/*     */     try {
/* 130 */       String queryString = "select CpInfoTemp from CpInfoTemp as CpInfoTemp";
/*     */ 
/* 132 */       Query query = session.createQuery(queryString);
/* 133 */       cpList = new LinkedList();
/* 134 */       for (Iterator it = query.iterate(); it.hasNext(); ) {
/* 135 */         CpInfoTemp cp = (CpInfoTemp)it.next();
/* 136 */         cpList.add(cp);
/*     */       }
/*     */     }
/*     */     catch (HibernateException localHibernateException) {
/*     */     }
/*     */     try {
/* 142 */       session.close();
/*     */     }
/*     */     catch (HibernateException localHibernateException1) {
/*     */     }
/* 146 */     return cpList;
/*     */   }
/*     */ 
/*     */   public static CpInfoTemp getCpInfoTempByID(int id) {
/* 150 */     Session session = Entity.getSession();
/* 151 */     if (session == null) {
/* 152 */       return null;
/*     */     }
/* 154 */     CpInfoTemp cp = null;
/*     */     try {
/* 156 */       String queryString = "select CpInfoTemp from CpInfoTemp as CpInfoTemp where CpInfoTemp.id=" + id;
/* 157 */       Query query = session.createQuery(queryString);
/* 158 */       Iterator it = query.iterate(); if (it.hasNext())
/* 159 */         cp = (CpInfoTemp)it.next();
/*     */     }
/*     */     catch (HibernateException localHibernateException)
/*     */     {
/*     */     }
/*     */     try
/*     */     {
/* 166 */       session.close();
/*     */     }
/*     */     catch (HibernateException localHibernateException1) {
/*     */     }
/* 170 */     return cp;
/*     */   }
/*     */ 
/*     */   public static void addCpInfoTemp(CpInfoTemp cpInfo) {
/* 174 */     Session session = Entity.getSession();
/* 175 */     if (session == null)
/* 176 */       return;
/*     */     try
/*     */     {
/* 179 */       session.save(cpInfo);
/*     */     }
/*     */     catch (HibernateException localHibernateException) {
/*     */     }
/*     */     try {
/* 184 */       session.close();
/* 185 */       Entity.Commit(session);
/*     */     }
/*     */     catch (HibernateException localHibernateException1) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void deleteCpInfoTemp(CpInfoTemp cpInfo) {
/* 192 */     Session session = Entity.getSession();
/* 193 */     if (session == null)
/* 194 */       return;
/*     */     try
/*     */     {
/* 197 */       if (cpInfo != null) {
/* 198 */         session.delete(cpInfo);
/*     */       }
/* 200 */       Entity.Commit(session);
/*     */     }
/*     */     catch (HibernateException localHibernateException) {
/*     */     }
/*     */     try {
/* 205 */       session.close();
/*     */     }
/*     */     catch (HibernateException localHibernateException1) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updateCpInfoTemp(CpInfoTemp cp) {
/* 212 */     Session session = Entity.getSession();
/* 213 */     if (session == null)
/* 214 */       return;
/*     */     try
/*     */     {
/* 217 */       session.update(cp);
/* 218 */       Entity.Commit(session);
/*     */     }
/*     */     catch (HibernateException localHibernateException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 225 */     CpInfo cpInfo = new CpInfo();
/*     */ 
/* 227 */     cpInfo.setStatus("1");
/*     */ 
/* 229 */     addCpInfo(cpInfo);
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.CPEntityHelper
 * JD-Core Version:    0.6.0
 */