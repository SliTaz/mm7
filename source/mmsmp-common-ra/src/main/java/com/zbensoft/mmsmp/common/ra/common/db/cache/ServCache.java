/*     */ package com.zbensoft.mmsmp.common.ra.common.db.cache;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;

import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.db.entity.VasserviceDAO;
/*     */ 
/*     */ public class ServCache
/*     */ {
/*  14 */   private static ServCache instance = new ServCache();
/*     */ 
/*  16 */   private volatile Map<String, Vasservice> cache = new HashMap();
/*  17 */   private volatile Map<Integer, Vasservice> cacheById = new HashMap();
/*     */ 
/*     */   public static ServCache getInstance()
/*     */   {
/*  25 */     return instance;
/*     */   }
/*     */ 
/*     */   private ServCache()
/*     */   {
/*  34 */     refreshCache();
/*     */   }
/*     */ 
/*     */   public void refreshCache()
/*     */   {
/*  43 */     Map cache = new HashMap();
/*  44 */     Map cacheById = new HashMap();
/*     */ 
/*  46 */     VasserviceDAO dao = new VasserviceDAO();
/*  47 */     List servList = dao.findAll();
/*  48 */     Vasservice serv = null;
/*     */ 
/*  50 */     for (int i = 0; i < servList.size(); i++)
/*     */     {
/*  52 */       serv = (Vasservice)servList.get(i);
/*  53 */       cache.put(serv.getVaspid() + serv.getVasid() + serv.getServicecode(), serv);
/*  54 */       cacheById.put(serv.getUniqueid(), serv);
/*     */     }
/*     */ 
/*  57 */     this.cache = cache;
/*  58 */     this.cacheById = cacheById;
/*     */   }
/*     */ 
/*     */   public Vasservice getServ(String vaspId, String vasId, String serviceCode)
/*     */   {
/*  70 */     return (Vasservice)this.cache.get(vaspId + vasId + serviceCode);
/*     */   }
/*     */ 
/*     */   public Vasservice getServ(int servId)
/*     */   {
/*  80 */     Vasservice service = (Vasservice)this.cacheById.get(new Integer(servId));
/*  81 */     if (service == null) {
/*  82 */       VasserviceDAO dao = new VasserviceDAO();
/*  83 */       service = dao.findById(Integer.valueOf(servId));
/*  84 */       this.cacheById.put(Integer.valueOf(servId), service);
/*     */     }
/*     */ 
/*  87 */     return service;
/*     */   }
/*     */ 
/*     */   public Map<String, Vasservice> getServiceMap()
/*     */   {
/*  96 */     return this.cache;
/*     */   }
/*     */ 
/*     */   public Map<Integer, Vasservice> getCacheById()
/*     */   {
/* 101 */     return this.cacheById;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.cache.ServCache
 * JD-Core Version:    0.6.0
 */