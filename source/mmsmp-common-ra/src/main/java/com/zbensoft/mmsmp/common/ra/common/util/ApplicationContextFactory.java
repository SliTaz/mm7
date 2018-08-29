/*    */ package com.zbensoft.mmsmp.common.ra.common.util;
/*    */ 
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class ApplicationContextFactory
/*    */ {
/*    */   private static ClassPathXmlApplicationContext ctx;
/*    */ 
/*    */   public static ApplicationContext getApplicationContext()
/*    */   {
/* 19 */     if (ctx == null) {
/* 20 */       ctx = new ClassPathXmlApplicationContext(
/* 21 */         new String[] { "classpath*:spring/*.xml" });
/*    */     }
/*    */ 
/* 24 */     return ctx;
/*    */   }
/*    */ 
/*    */   public static Object getBean(String name)
/*    */   {
/* 29 */     return getApplicationContext().getBean(name);
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.util.ApplicationContextFactory
 * JD-Core Version:    0.6.0
 */