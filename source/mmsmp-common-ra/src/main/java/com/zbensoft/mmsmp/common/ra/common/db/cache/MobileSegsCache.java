/*    */ package com.zbensoft.mmsmp.common.ra.common.db.cache;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;

import com.zbensoft.mmsmp.common.ra.common.db.entity.MobileSegs;
import com.zbensoft.mmsmp.common.ra.common.db.entity.MobileSegsDAO;
/*    */ 
/*    */ public class MobileSegsCache
/*    */ {
/* 10 */   private static MobileSegsCache instance = null;
/* 11 */   private volatile Map<String, String> segsCacheMap = new HashMap();
/*    */ 
/*    */   public static MobileSegsCache getInstance()
/*    */   {
/* 15 */     if (instance == null) {
/* 16 */       synchronized (ServCtlsCache.class.getName()) {
/* 17 */         if (instance == null) {
/* 18 */           instance = new MobileSegsCache();
/*    */         }
/*    */       }
/*    */     }
/* 22 */     return instance;
/*    */   }
/*    */ 
/*    */   private MobileSegsCache()
/*    */   {
/* 27 */     refreshCache();
/*    */   }
/*    */ 
/*    */   public void refreshCache()
/*    */   {
/* 32 */     MobileSegsDAO dao = new MobileSegsDAO();
/* 33 */     List segsList = dao.findAll();
/* 34 */     for (MobileSegs segs : segsList)
/* 35 */       this.segsCacheMap.put(segs.getStartno(), segs.getCityid());
/*    */   }
/*    */ 
/*    */   public Map<String, String> getSegsCacheMap()
/*    */   {
/* 41 */     return this.segsCacheMap;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.cache.MobileSegsCache
 * JD-Core Version:    0.6.0
 */