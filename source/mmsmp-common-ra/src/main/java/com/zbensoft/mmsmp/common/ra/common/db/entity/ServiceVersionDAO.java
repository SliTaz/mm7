/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class ServiceVersionDAO extends BaseHibernateDAO
/*    */ {
/* 17 */   private static final Log log = LogFactory.getLog(ServiceVersionDAO.class);
/*    */ 
/*    */   public List<ServiceVersion> findAll() {
/*    */     try {
/* 21 */       Query query = getSession().createQuery("from ServiceVersion");
/* 22 */       return query.list();
/*    */     } catch (RuntimeException e) {
/* 24 */       log.error("", e);
/* 25 */     }throw e;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ServiceVersionDAO
 * JD-Core Version:    0.6.0
 */