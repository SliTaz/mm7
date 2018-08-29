/*    */ package com.zbensoft.mmsmp.common.ra.common.pool;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class SimpleObjectsPool
/*    */ {
/* 16 */   private Set<Object> pool = new HashSet();
/* 17 */   private int poolSize = 100;
/* 18 */   private int size = 0;
/* 19 */   private List<Object> objList = new LinkedList();
/*    */ 
/*    */   public SimpleObjectsPool()
/*    */   {
/*    */   }
/*    */ 
/*    */   public SimpleObjectsPool(int size)
/*    */   {
/* 29 */     setPoolSize(size);
/*    */   }
/*    */ 
/*    */   public void setPoolSize(int newSize)
/*    */   {
/* 35 */     this.poolSize = newSize;
/*    */   }
/*    */ 
/*    */   public void clear()
/*    */   {
/* 40 */     this.size = 0;
/* 41 */     this.pool.clear();
/* 42 */     this.objList.clear();
/*    */   }
/*    */ 
/*    */   public void addObj(Object obj)
/*    */   {
/* 48 */     if (this.size >= this.poolSize)
/*    */     {
/* 50 */       this.pool.remove(this.objList.get(0));
/* 51 */       this.objList.remove(0);
/* 52 */       this.size -= 1;
/*    */     }
/* 54 */     this.pool.add(obj);
/* 55 */     this.objList.add(obj);
/* 56 */     this.size += 1;
/*    */   }
/*    */ 
/*    */   public boolean exists(Object obj)
/*    */   {
/* 66 */     return this.pool.contains(obj);
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.pool.SimpleObjectsPool
 * JD-Core Version:    0.6.0
 */