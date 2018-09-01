/*    */ package com.zbensoft.mmsmp.common.ra.common.db.cache;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;

import com.zbensoft.mmsmp.common.ra.common.db.entity.ServCtls;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ServCtlsDAO;
/*    */ 
/*    */ public class ServCtlsCache
/*    */ {
/* 14 */   private static ServCtlsCache instance = null;
/* 15 */   private volatile List<ServCtls> cacheList = new ArrayList();
/* 16 */   private volatile Map<Integer, ServCtls> cacheMap = new HashMap();
/*    */ 
/*    */   public static ServCtlsCache getInstance()
/*    */   {
/* 24 */     if (instance == null) {
/* 25 */       synchronized (ServCtlsCache.class.getName()) {
/* 26 */         if (instance == null) {
/* 27 */           instance = new ServCtlsCache();
/*    */         }
/*    */       }
/*    */     }
/* 31 */     return instance;
/*    */   }
/*    */ 
/*    */   private ServCtlsCache()
/*    */   {
/* 37 */     refreshCache();
/*    */   }
/*    */ 
/*    */   public void refreshCache()
/*    */   {
/* 42 */     ServCtlsDAO dao = new ServCtlsDAO();
/* 43 */     List<ServCtls> servCtlsList = dao.findAll();
/* 44 */     this.cacheList = servCtlsList;
/* 45 */     for (ServCtls servCtls : servCtlsList)
/* 46 */       this.cacheMap.put(servCtls.getServuniqueid(), servCtls);
/*    */   }
/*    */ 
/*    */   public List<ServCtls> getServCtlsList()
/*    */   {
/* 52 */     return this.cacheList;
/*    */   }
/*    */   public ServCtls getServCtls(int serviceId) {
/* 55 */     return (ServCtls)this.cacheMap.get(new Integer(serviceId));
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.cache.ServCtlsCache
 * JD-Core Version:    0.6.0
 */