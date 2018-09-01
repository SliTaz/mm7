/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ 
/*    */ public class DecodeMessageException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -4349642737314277005L;
/*    */ 
/*    */   public DecodeMessageException(String errorMessage)
/*    */   {
/* 15 */     super(errorMessage);
/*    */   }
/*    */ 
/*    */   public DecodeMessageException(String errorMessage, Exception e)
/*    */   {
/* 25 */     super(errorMessage, e);
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.DecodeMessageException
 * JD-Core Version:    0.6.0
 */