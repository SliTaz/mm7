/*    */ package com.zbensoft.mmsmp.common.ra.common.pool;
/*    */ 
/*    */ public class KeyPos
/*    */ {
/* 15 */   public static Integer[] pos = new Integer[500];
/* 16 */   private static int size = 500;
/*    */ 
/*    */   static
/*    */   {
/* 20 */     for (int i = 0; i < size; i++)
/* 21 */       pos[i] = new Integer(i);
/*    */   }
/*    */ 
/*    */   public static void setSize(int newSize)
/*    */   {
/* 28 */     if (size > newSize)
/*    */     {
/* 30 */       return;
/*    */     }
/* 32 */     size = newSize;
/*    */ 
/* 34 */     pos = new Integer[newSize];
/* 35 */     for (int i = 0; i < size; i++)
/*    */     {
/* 37 */       pos[i] = new Integer(i);
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.pool.KeyPos
 * JD-Core Version:    0.6.0
 */