/*     */ package com.zbensoft.mmsmp.common.ra.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DBUtil
/*     */ {
/*     */   private static DataSource dataSource;
/*  22 */   private static final Logger log = Logger.getLogger(DBUtil.class);
/*     */ 
/*  24 */   public static Connection getConnection() throws SQLException { return dataSource.getConnection(); }
/*     */ 
/*     */   private static void close(ResultSet rs, Statement stat, Connection conn)
/*     */   {
/*     */     try
/*     */     {
/*  30 */       if (rs != null) rs.close();
/*  31 */       if (stat != null) stat.close();
/*  32 */       if (conn != null) conn.close(); 
/*     */     }
/*     */     catch (SQLException e) {
/*  34 */       System.err.println("close resources error :  " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean executeUpdate(String sql)
/*     */   {
	
	
///*  40 */     Connection conn = null;
///*  41 */     Statement stat = null;
///*  42 */     System.out.println(sql);
///*     */     try {
///*  44 */       conn = getConnection();
///*  45 */       stat = conn.createStatement();
///*  46 */       int result = stat.executeUpdate(sql);
///*     */ 
///*  48 */       int i = result >= 1 ? 1 : 0;
///*     */       return i;
///*     */     } catch (SQLException e) {
///*  50 */       System.out.println("execute Update error " + e.getMessage());
///*     */       return false;
///*     */     } finally {
///*  53 */       close(null, stat, conn);
///*  54 */     }throw localObject;

return true;
/*     */   }
/*     */ 
/*     */   public static boolean checkExist(String sql)
/*     */   {
/*  59 */     Connection conn = null;
/*  60 */     Statement stat = null;
/*     */ 
/*  62 */     ResultSet rs = null;
/*  63 */     log.info("check exist : " + sql);
/*     */     try {
/*  65 */       conn = getConnection();
/*  66 */       stat = conn.createStatement();
/*  67 */       rs = stat.executeQuery(sql);
/*  68 */       if (rs.next())
/*     */         return true;
/*     */     }
/*     */     catch (SQLException e) {
/*  72 */       log.info("check Exist error:   " + e.getMessage());
/*     */       return false;
/*     */     } finally {
/*  75 */       close(rs, stat, conn); } close(rs, stat, conn);
/*     */ 
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   public static ResultSet query(String sql) {
/*  81 */     Connection conn = null;
/*  82 */     Statement stat = null;
/*     */ 
/*  84 */     ResultSet rs = null;
/*  85 */     log.info("query : " + sql);
/*     */     try {
/*  87 */       conn = getConnection();
/*  88 */       stat = conn.createStatement();
/*  89 */       rs = stat.executeQuery(sql);
/*  90 */       return rs;
/*     */     } catch (SQLException e) {
/*  92 */       log.info("query error:   " + e.getMessage());
/*  93 */     }return null;
/*     */   }
/*     */ 
/*     */   public static String queryProvinceCode(String sql)
/*     */   {
///* 102 */     Connection conn = null;
///* 103 */     Statement stat = null;
///*     */ 
///* 105 */     ResultSet rs = null;
///* 106 */     log.info("check exist : " + sql);
///*     */     try {
///* 108 */       conn = getConnection();
///* 109 */       stat = conn.createStatement();
///* 110 */       rs = stat.executeQuery(sql);
///* 111 */       if (rs.next()) {
///* 112 */         String str = rs.getString(1);
///*     */         return str;
///*     */       }
///*     */       return "";
///*     */     } catch (SQLException e) {
///* 116 */       log.info("query error:   " + e.getMessage());
///*     */       return null;
///*     */     } finally {
///* 119 */       close(rs, stat, conn);
///* 120 */     }throw localObject;
return "";
/*     */   }
/*     */ 
/*     */   public int getMaxId()
/*     */   {
/* 126 */     String sql = " select max(userid) from user_info";
/* 127 */     ResultSet rs = query(sql);
/*     */     try {
/* 129 */       if (rs.next())
/* 130 */         return rs.getInt(1) + 1;
/*     */     }
/*     */     catch (SQLException e) {
/* 133 */       e.printStackTrace();
/*     */ 
/* 135 */       close(rs, null, null);
/* 136 */     }return 10000;
/*     */   }
/*     */ 
/*     */   public boolean insertUserInfo(String number) {
/* 140 */     int maxid = getMaxId();
/* 141 */     String sql = "insert into user_info (userid,cellphonenumber,terminaltype,status) values (" + maxid + ",'" + number + "',1,1)";
/* 142 */     log.info("add userInfo:  " + sql);
/* 143 */     boolean flag = executeUpdate(sql);
/* 144 */     return flag;
/*     */   }
/*     */ 
/*     */   public DataSource getDataSource()
/*     */   {
/* 152 */     return dataSource;
/*     */   }
/*     */ 
/*     */   public void setDataSource(DataSource dataSource)
/*     */   {
/* 160 */     dataSource = dataSource;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.util.DBUtil
 * JD-Core Version:    0.6.0
 */