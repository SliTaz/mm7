/*    */ package com.zbensoft.mmsmp.common.ra.common.mina.server;
import com.zbensoft.mmsmp.common.ra.common.mina.TcpServer;
import com.zbensoft.mmsmp.common.ra.common.mina.handler.MessageQueueServerHandler;

/*    */ import java.net.InetSocketAddress;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
/*    */ import org.apache.mina.filter.codec.ProtocolCodecFilter;
/*    */ import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
/*    */ import org.apache.mina.filter.logging.LoggingFilter;
/*    */ import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
/*    */ 
/*    */ public class MessageQueueTcpServer
/*    */   implements TcpServer
/*    */ {
/* 25 */   private static Logger logger = Logger.getLogger(MessageQueueTcpServer.class);
/*    */ 
/*    */   public int stopListener()
/*    */   {
/* 32 */     return 0;
/*    */   }
/*    */ 
/*    */   public int startListener(String ip, int port)
/*    */   {
/*    */     try
/*    */     {
/* 42 */       NioSocketAcceptor acceptor = new NioSocketAcceptor();
/*    */ 
/* 44 */       DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
/*    */ 
/* 46 */       chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
/*    */ 
/* 48 */       chain.addLast("logger", new LoggingFilter());
/*    */ 
/* 50 */       acceptor.setHandler(new MessageQueueServerHandler());
/*    */ 
/* 52 */       acceptor.bind(new InetSocketAddress(ip, port));
/* 53 */       logger.info("Server now listening on ip:port\t" + ip + ":" + port);
/*    */     } catch (Exception ex) {
/* 55 */       ex.printStackTrace();
/* 56 */       return 1;
/*    */     }
/*    */ 
/* 59 */     return 0;
/*    */   }
/*    */ 
/*    */   public int startListener(int port)
/*    */   {
/*    */     try
/*    */     {
/* 69 */       NioSocketAcceptor acceptor = new NioSocketAcceptor();
/*    */ 
/* 71 */       DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
/*    */ 
/* 73 */       chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
/*    */ 
/* 75 */       chain.addLast("logger", new LoggingFilter());
/*    */ 
/* 77 */       acceptor.setHandler(new MessageQueueServerHandler());
/*    */ 
/* 79 */       acceptor.bind(new InetSocketAddress(port));
/* 80 */       logger.info("Server now listening on ip:port \t127.0.0.1:" + port);
/*    */     } catch (Exception ex) {
/* 82 */       ex.printStackTrace();
/* 83 */       return 1;
/*    */     }
/* 85 */     return 0;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mina.server.MessageQueueTcpServer
 * JD-Core Version:    0.6.0
 */