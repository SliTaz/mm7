/*    */ package com.zbensoft.mmsmp.common.ra.common.mina.handler;
/*    */ import org.apache.mina.core.service.IoHandlerAdapter;
/*    */ import org.apache.mina.core.session.IdleStatus;
/*    */ import org.apache.mina.core.session.IoSession;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
/*    */ 
/*    */ public class MessageQueueClientHandler extends IoHandlerAdapter
/*    */ {
/*    */   private AbstractMessage message;
/*    */ 
/*    */   public MessageQueueClientHandler(AbstractMessage msg)
/*    */   {
/* 23 */     this.message = msg;
/*    */   }
/*    */ 
/*    */   public void exceptionCaught(IoSession session, Throwable e)
/*    */     throws Exception
/*    */   {
/* 32 */     session.close(true);
/*    */   }
/*    */ 
/*    */   public void messageReceived(IoSession arg0, Object arg1)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void messageSent(IoSession arg0, Object arg1)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void sessionClosed(IoSession session)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void sessionCreated(IoSession session)
/*    */     throws Exception
/*    */   {
/* 63 */     session.write(this.message);
/*    */   }
/*    */ 
/*    */   public void sessionIdle(IoSession arg0, IdleStatus arg1)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void sessionOpened(IoSession arg0)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mina.handler.MessageQueueClientHandler
 * JD-Core Version:    0.6.0
 */