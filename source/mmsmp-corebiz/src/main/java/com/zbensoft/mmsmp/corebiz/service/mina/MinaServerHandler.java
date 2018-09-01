 package com.zbensoft.mmsmp.corebiz.service.mina;
 
 import com.zbensoft.mmsmp.corebiz.route.IMessageRouter;
 import java.io.Serializable;
 import org.apache.log4j.Logger;
 import org.apache.mina.core.service.IoHandlerAdapter;
 import org.apache.mina.core.session.IdleStatus;
 import org.apache.mina.core.session.IoSession;
 import org.apache.mina.core.session.IoSessionConfig;
 import org.apache.mina.transport.socket.SocketSessionConfig;
 
 
 
 
 
 
 
 
 public class MinaServerHandler extends IoHandlerAdapter
 {
   static final Logger logger = Logger.getLogger(MinaServerHandler.class);
   
   IMessageRouter messageRouter;
   String idleString = "";
   
   public void sessionCreated(IoSession session)
     throws Exception
   {
     SocketSessionConfig cfg = (SocketSessionConfig)session.getConfig();
     cfg.setKeepAlive(true);
     cfg.setSoLinger(0);
   }
   
   public void messageReceived(IoSession session, Object message)
     throws Exception
   {
     this.messageRouter.doRouter((Serializable)message);
   }
   
   public void sessionOpened(IoSession session)
     throws Exception
   {
     session.getConfig().setIdleTime(IdleStatus.READER_IDLE, 25);
     session.getConfig().setIdleTime(IdleStatus.WRITER_IDLE, 25);
   }
   
   public void sessionIdle(IoSession session, IdleStatus status)
     throws Exception
   {
     session.write("corebiz-> mina client echo idle message");
   }
   
   public void exceptionCaught(IoSession session, Throwable cause)
     throws Exception
   {
     logger.error("Unexpected exception:", cause);
     session.close(true);
   }
   
   public void setMessageRouter(IMessageRouter messageRouter) {
     this.messageRouter = messageRouter;
   }
 }





