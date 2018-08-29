/*    */ package com.zbensoft.mmsmp.common.ra.common.mina.handler;
/*    */ import org.apache.mina.core.service.IoHandlerAdapter;
/*    */ import org.apache.mina.core.session.IoSession;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
/*    */ 
/*    */ public class NotifyMessageClientHandler extends IoHandlerAdapter
/*    */ {
/*    */   private AbstractMessage message;
/*    */ 
/*    */   public NotifyMessageClientHandler(AbstractMessage msg)
/*    */   {
/* 21 */     this.message = msg;
/*    */   }
/*    */ 
/*    */   public void sessionOpened(IoSession session) throws Exception
/*    */   {
/* 26 */     session.write(this.message);
/*    */   }
/*    */ 
/*    */   public void messageSent(IoSession session, Object message)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void messageReceived(IoSession session, Object message)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mina.handler.NotifyMessageClientHandler
 * JD-Core Version:    0.6.0
 */