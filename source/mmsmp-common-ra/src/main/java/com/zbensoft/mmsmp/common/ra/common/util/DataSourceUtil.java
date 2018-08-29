/*     */ package com.zbensoft.mmsmp.common.ra.common.util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.commons.dbcp.BasicDataSource;
/*     */ import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.config.MMSMSConfig;
/*     */ 
/*     */ public class DataSourceUtil
/*     */ {
/*  16 */   private static final Logger logger = Logger.getLogger(DataSourceUtil.class);
/*     */   private static final String mDataSrcName = "jdbc/mms";
/*  19 */   private static final String mDriverClassName = MMSMSConfig.getInstance().getDriverClassName();
/*  20 */   private static final String mDBUrl = MMSMSConfig.getInstance().getDBUrl();
/*  21 */   private static final String mDBUserName = MMSMSConfig.getInstance().getDBUserName();
/*  22 */   private static final String mDBPassWord = MMSMSConfig.getInstance().getDBUserPass();
/*  23 */   private static final int mDataSrcMaxActive = MMSMSConfig.getInstance().getDataSrcMaxActive();
/*  24 */   private static final long mDataSrcMaxWait = MMSMSConfig.getInstance().getDataSrcMaxWait();
/*     */ 
/*  26 */   private static Map<String, DataSource> mDataSourceMap = new HashMap();
/*     */ 
/*     */   public static Connection getConnection()
/*     */     throws SQLException
/*     */   {
/*  36 */     if (mDataSourceMap.get("jdbc/mms") == null)
/*     */     {
/*  38 */       BasicDataSource dataSrc = new BasicDataSource();
/*  39 */       dataSrc.setDriverClassName(mDriverClassName);
/*  40 */       dataSrc.setUsername(mDBUserName);
/*  41 */       dataSrc.setPassword(mDBPassWord);
/*  42 */       dataSrc.setUrl(mDBUrl);
/*  43 */       dataSrc.setMaxActive(mDataSrcMaxActive);
/*  44 */       dataSrc.setMaxIdle(5);
/*  45 */       dataSrc.setInitialSize(5);
/*  46 */       dataSrc.setMaxWait(mDataSrcMaxWait);
/*  47 */       dataSrc.setTestOnBorrow(true);
/*  48 */       dataSrc.setTestWhileIdle(true);
/*  49 */       dataSrc.setTestOnReturn(true);
/*  50 */       mDataSourceMap.put("jdbc/mms", dataSrc);
/*     */     }
/*     */ 
/*  53 */     return ((DataSource)mDataSourceMap.get("jdbc/mms")).getConnection();
/*     */   }
/*     */ 
/*     */   public static void printDataSourceStats(DataSource ds)
/*     */     throws SQLException
/*     */   {
/*  63 */     BasicDataSource bds = (BasicDataSource)ds;
/*  64 */     logger.debug("NumActive: " + bds.getNumActive());
/*  65 */     logger.debug("NumIdle: " + bds.getNumIdle());
/*     */   }
/*     */ 
/*     */   public static void shutdownDataSource(DataSource ds)
/*     */     throws SQLException
/*     */   {
/*  75 */     BasicDataSource bds = (BasicDataSource)ds;
/*  76 */     bds.close();
/*     */   }
/*     */ 
/*     */   public static void closeConnection(Connection conn)
/*     */   {
/*  85 */     if (conn != null)
/*     */       try
/*     */       {
/*  88 */         conn.close();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/*  92 */         logger.error("", se);
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void closeConnection(Connection conn, PreparedStatement preStmt)
/*     */   {
/* 103 */     if (preStmt != null)
/*     */     {
/*     */       try
/*     */       {
/* 107 */         preStmt.close();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/* 111 */         logger.error("", se);
/*     */       }
/*     */     }
/*     */ 
/* 115 */     if (conn != null)
/*     */     {
/*     */       try
/*     */       {
/* 119 */         conn.close();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/* 123 */         logger.error("", se);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeStatement(PreparedStatement preStmt, ResultSet result)
/*     */   {
/* 130 */     if (result != null) {
/*     */       try
/*     */       {
/* 133 */         result.close();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/* 137 */         logger.error("", se);
/*     */       }
/*     */     }
/* 140 */     if (preStmt != null)
/*     */     {
/*     */       try
/*     */       {
/* 144 */         preStmt.close();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/* 148 */         logger.error("", se);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeConnection(Connection conn, PreparedStatement preStmt, ResultSet result)
/*     */   {
/* 155 */     if (result != null) {
/*     */       try
/*     */       {
/* 158 */         result.close();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/* 162 */         logger.error("", se);
/*     */       }
/*     */     }
/* 165 */     if (preStmt != null)
/*     */     {
/*     */       try
/*     */       {
/* 169 */         preStmt.close();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/* 173 */         logger.error("", se);
/*     */       }
/*     */     }
/*     */ 
/* 177 */     if (conn != null)
/*     */     {
/*     */       try
/*     */       {
/* 181 */         conn.close();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/* 185 */         logger.error("", se);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void rollback(Connection conn)
/*     */   {
/* 197 */     if (conn != null)
/*     */       try
/*     */       {
/* 200 */         conn.rollback();
/*     */       }
/*     */       catch (SQLException ex)
/*     */       {
/* 204 */         logger.error(ex);
/*     */       }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.util.DataSourceUtil
 * JD-Core Version:    0.6.0
 */