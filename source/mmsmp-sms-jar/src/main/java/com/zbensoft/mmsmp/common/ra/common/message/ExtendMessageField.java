/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ 
/*    */ import java.io.Serializable;

/*    */
/*    */ public class ExtendMessageField
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -1392119910011998693L;
/*    */   private String name;
/* 15 */   public static final ExtendMessageField FEETYPE = new ExtendMessageField("feeType");
/* 16 */   public static final ExtendMessageField MT_RELATION_ID = new ExtendMessageField("mtRelationId");
/* 17 */   public static final ExtendMessageField MT_SERV_TYPE = new ExtendMessageField("mtServType");
/* 18 */   public static final ExtendMessageField SERVICEID = new ExtendMessageField("serviceId");
/* 19 */   public static final ExtendMessageField CONTENTID = new ExtendMessageField("contentId");
/* 20 */   public static final ExtendMessageField SERVICE_DESC = new ExtendMessageField("serviceDesc");
/*    */ 
/*    */   private ExtendMessageField(String name)
/*    */   {
/* 12 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public static ExtendMessageField parseString(String name)
/*    */   {
/* 27 */     if (name.equals("feeType"))
/* 28 */       return FEETYPE;
/* 29 */     if (name.equals("mtRelationId"))
/* 30 */       return MT_RELATION_ID;
/* 31 */     if (name.equals("mtServType"))
/* 32 */       return MT_SERV_TYPE;
/* 33 */     if (name.equals("serviceId"))
/* 34 */       return SERVICEID;
/* 35 */     if (name.equals("contentId"))
/* 36 */       return CONTENTID;
/* 37 */     if (name.equals("serviceDesc")) {
/* 38 */       return SERVICE_DESC;
/*    */     }
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 45 */     return this.name;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.ExtendMessageField
 * JD-Core Version:    0.6.0
 */