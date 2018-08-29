/*    */ package com.zbensoft.mmsmp.common.ra.common.mina.handler;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.mina.core.service.IoHandlerAdapter;
/*    */ import org.apache.mina.core.session.IdleStatus;
/*    */ import org.apache.mina.core.session.IoSession;
/*    */ 
/*    */ public class MessageQueueServerHandler extends IoHandlerAdapter
/*    */ {
/*    */   public void exceptionCaught(IoSession session, Throwable e)
/*    */   {
/* 24 */     System.out.println(e.getMessage());
/* 25 */     session.close(true);
/*    */   }
/*    */ 
/*    */   public void messageReceived(IoSession session, Object message)
/*    */     throws Exception
/*    */   {
/* 34 */     session.write("服务器收到消息" + message.getClass());
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
/* 50 */     session.close(true);
/*    */   }
/*    */ 
/*    */   public void sessionCreated(IoSession session)
/*    */     throws Exception
/*    */   {
/* 58 */     session.write("当前接入：" + session.getRemoteAddress());
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
 * Qualified Name:     com.aceway.common.mina.handler.MessageQueueServerHandler
 * JD-Core Version:    0.6.0
 */