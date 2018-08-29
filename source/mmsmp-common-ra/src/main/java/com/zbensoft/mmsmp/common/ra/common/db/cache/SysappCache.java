/*    */ package com.zbensoft.mmsmp.common.ra.common.db.cache;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;

import com.zbensoft.mmsmp.common.ra.common.db.entity.Sysapp;
import com.zbensoft.mmsmp.common.ra.common.db.entity.SysappDAO;
/*    */ 
/*    */ public class SysappCache
/*    */ {
/* 12 */   private static SysappCache instance = new SysappCache();
/*    */ 
/* 14 */   private Map<String, Sysapp> cache = new HashMap();
/*    */ 
/*    */   public static SysappCache getInstance()
/*    */   {
/* 22 */     return instance;
/*    */   }
/*    */ 
/*    */   public SysappCache()
/*    */   {
/* 28 */     refreshCache();
/*    */   }
/*    */ 
/*    */   public void refreshCache()
/*    */   {
/* 37 */     Map cache = new HashMap();
/*    */ 
/* 39 */     SysappDAO dao = new SysappDAO();
/* 40 */     List sysAppList = dao.findAll();
/* 41 */     Sysapp sysapp = null;
/*    */ 
/* 43 */     for (int i = 0; i < sysAppList.size(); i++)
/*    */     {
/* 45 */       sysapp = (Sysapp)sysAppList.get(i);
/* 46 */       cache.put(sysapp.getStrappname(), sysapp);
/*    */     }
/*    */ 
/* 49 */     this.cache = cache;
/*    */   }
/*    */ 
/*    */   public Sysapp getApp(String name)
/*    */   {
/* 59 */     if (name == null) {
/* 60 */       return null;
/*    */     }
/* 62 */     return (Sysapp)this.cache.get(name);
/*    */   }
/*    */ 
/*    */   public Map<String, Sysapp> getSysappMap()
/*    */   {
/* 71 */     return this.cache;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.cache.SysappCache
 * JD-Core Version:    0.6.0
 */