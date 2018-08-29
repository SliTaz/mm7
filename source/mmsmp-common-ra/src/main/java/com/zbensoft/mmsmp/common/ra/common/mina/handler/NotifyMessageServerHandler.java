/*    */ package com.zbensoft.mmsmp.common.ra.common.mina.handler;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.mina.core.service.IoHandlerAdapter;
/*    */ import org.apache.mina.core.session.IdleStatus;
/*    */ import org.apache.mina.core.session.IoSession;
/*    */ 
/*    */ public class NotifyMessageServerHandler extends IoHandlerAdapter
/*    */ {
/*    */   public void exceptionCaught(IoSession session, Throwable e)
/*    */     throws Exception
/*    */   {
/* 24 */     System.out.println("异常捕获：" + e.getMessage());
/* 25 */     session.close(true);
/*    */   }
/*    */ 
/*    */   public void messageReceived(IoSession session, Object e)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void messageSent(IoSession session, Object e)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void sessionClosed(IoSession session)
/*    */     throws Exception
/*    */   {
/* 50 */     System.out.println("会话被关闭");
/* 51 */     session.close(true);
/*    */   }
/*    */ 
/*    */   public void sessionCreated(IoSession session)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void sessionIdle(IoSession session, IdleStatus e)
/*    */     throws Exception
/*    */   {
/* 66 */     System.out.println("会话空闲");
/* 67 */     session.close(true);
/*    */   }
/*    */ 
/*    */   public void sessionOpened(IoSession session)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mina.handler.NotifyMessageServerHandler
 * JD-Core Version:    0.6.0
 */