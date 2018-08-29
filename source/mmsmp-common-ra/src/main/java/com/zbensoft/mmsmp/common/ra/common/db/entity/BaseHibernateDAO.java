/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class BaseHibernateDAO
/*    */   implements IBaseHibernateDAO
/*    */ {
/*    */   public Session getSession()
/*    */   {
/* 13 */     return HibernateSessionFactory.getSession();
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.BaseHibernateDAO
 * JD-Core Version:    0.6.0
 */