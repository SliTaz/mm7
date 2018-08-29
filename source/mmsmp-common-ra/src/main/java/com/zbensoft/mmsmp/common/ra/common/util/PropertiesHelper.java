/*    */ package com.zbensoft.mmsmp.common.ra.common.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URL;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class PropertiesHelper
/*    */ {
/* 14 */   private static final File file = new File(PropertiesHelper.class.getClassLoader().getResource("").getPath() + "/app.properties");
/*    */   private static Properties _prop;
/*    */ 
/*    */   static
/*    */   {
/* 74 */     do_reload_config();
/* 75 */     new Thread(new Runnable() {
/*    */       public void run() {
/* 77 */         long modified = PropertiesHelper.file.lastModified();
/*    */         while (true) {
/* 79 */           long last_modified = PropertiesHelper.file.lastModified();
/* 80 */           if (modified != last_modified) {
/* 81 */             System.out.println("reloading configure...");
/* 82 */             PropertiesHelper.do_reload_config();
/* 83 */             modified = last_modified;
/*    */           }
/*    */           try {
/* 86 */             Thread.sleep(5000L);
/*    */           }
/*    */           catch (Exception localException)
/*    */           {
/*    */           }
/*    */         }
/*    */       }
/*    */     }).start();
/*    */   }
/*    */ 
/*    */   public static Integer getVacAaaPort()
/*    */   {
/* 17 */     return getInteger("vac.aaa.port", Integer.valueOf(11111));
/*    */   }
/*    */   public static String getVacAaaIp() {
/* 20 */     return getString("vac.aaa.ip", "127.0.0.1");
/*    */   }
/*    */   public static Integer getVacAaaIdleC() {
/* 23 */     return getInteger("vac.aaa.idle.c", Integer.valueOf(60));
/*    */   }
/*    */   public static Integer getVacAaaIdleT() {
/* 26 */     return getInteger("vac.aaa.idle.t", Integer.valueOf(60));
/*    */   }
/*    */   public static Integer getVacAaaIdleN() {
/* 29 */     return getInteger("vac.aaa.idle.n", Integer.valueOf(3));
/*    */   }
/*    */   public static Long getVacAaaMsgTimeout() {
/* 32 */     return getLong("vac.aaa.msg.timeout", Long.valueOf(10000L));
/*    */   }
/*    */ 
/*    */   public static Long getLong(String key, Long def)
/*    */   {
/* 37 */     String value = _prop.getProperty(key);
/* 38 */     return Long.valueOf(value != null ? Long.parseLong(value) : def.longValue());
/*    */   }
/*    */   public static Integer getInteger(String key, Integer def) {
/* 41 */     String value = _prop.getProperty(key);
/* 42 */     return value != null ? Integer.valueOf(value) : def;
/*    */   }
/*    */   public static String getString(String key, String def) {
/* 45 */     String value = _prop.getProperty(key);
/* 46 */     return value != null ? value : def;
/*    */   }
/*    */   public static Boolean getBoolean(String key, Boolean def) {
/* 49 */     String value = _prop.getProperty(key);
/* 50 */     return value != null ? Boolean.valueOf(value) : def;
/*    */   }
/*    */ 
/*    */   public static void do_reload_config() {
/*    */     try {
/* 55 */       _prop = new Properties();
/* 56 */       _prop.load(new FileInputStream(file));
/* 57 */       show_all();
/*    */     } catch (Exception e) {
/* 59 */       e.printStackTrace(System.err);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void show_all() {
/* 64 */     Enumeration keys = _prop.keys();
/* 65 */     while (keys.hasMoreElements()) {
/* 66 */       Object key = keys.nextElement();
/* 67 */       System.out.println("Reading config " + key + " = " + _prop.getProperty((String)key));
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.util.PropertiesHelper
 * JD-Core Version:    0.6.0
 */