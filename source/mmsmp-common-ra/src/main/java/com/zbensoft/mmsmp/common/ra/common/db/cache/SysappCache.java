/*    */ package com.zbensoft.mmsmp.common.ra.common.db.cache;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;

import com.zbensoft.mmsmp.common.ra.common.db.entity.Sysapp;
 
 public class SysappCache
 {
   private static SysappCache instance = new SysappCache();
 
   private Map<String, Sysapp> cache = new HashMap();
 
   public static SysappCache getInstance()
   {
     return instance;
   }
 
   public SysappCache()
   {
     refreshCache();
   }
 
   public void refreshCache()
   {
     Map cache = new HashMap();
 
//     SysappDAO dao = new SysappDAO();
//     List sysAppList = dao.findAll();
//     Sysapp sysapp = null;
// 
//     for (int i = 0; i < sysAppList.size(); i++)
//     {
//       sysapp = (Sysapp)sysAppList.get(i);
//       cache.put(sysapp.getStrappname(), sysapp);
//     }
// 
     this.cache = cache;
   }
 
   public Sysapp getApp(String name)
   {
     if (name == null) {
       return null;
     }
     return (Sysapp)this.cache.get(name);
   }
 
   public Map<String, Sysapp> getSysappMap()
   {
     return this.cache;
   }
 }
