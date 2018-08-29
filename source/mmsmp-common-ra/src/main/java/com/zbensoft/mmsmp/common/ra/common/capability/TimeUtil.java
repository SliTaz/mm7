/*    */ package com.zbensoft.mmsmp.common.ra.common.capability;
/*    */ 
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class TimeUtil
/*    */ {
/*    */   public static void printTaskTime(Log log, String task, long begintime)
/*    */   {
/* 15 */     long dur = System.currentTimeMillis() - begintime;
/*    */ 
/* 17 */     if (dur == 0L) {
/* 18 */       return;
/*    */     }
/* 20 */     if (dur < 10L)
/*    */     {
/* 22 */       if (log.isDebugEnabled())
/* 23 */         log.debug(task + ": " + begintime + " - " + System.currentTimeMillis() + ": " + dur + "ms");
/*    */     }
/* 25 */     else if (dur < 800L)
/*    */     {
/* 27 */       if (log.isInfoEnabled())
/* 28 */         log.info(task + ": " + begintime + " - " + System.currentTimeMillis() + ": " + dur + "ms");
/*    */     }
/* 30 */     else if (log.isWarnEnabled())
/* 31 */       log.warn(task + ": " + begintime + " - " + System.currentTimeMillis() + ": " + dur + "ms");
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.capability.TimeUtil
 * JD-Core Version:    0.6.0
 */