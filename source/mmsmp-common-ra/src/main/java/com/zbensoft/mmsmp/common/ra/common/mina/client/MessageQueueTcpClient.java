/*     */ package com.zbensoft.mmsmp.common.ra.common.mina.client;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.mina.TcpClient;
import com.zbensoft.mmsmp.common.ra.common.mina.handler.MessageQueueClientHandler;

/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
/*     */ import org.apache.mina.core.future.ConnectFuture;
/*     */ import org.apache.mina.core.service.IoConnector;
/*     */ import org.apache.mina.core.session.IoSession;
/*     */ import org.apache.mina.filter.codec.ProtocolCodecFilter;
/*     */ import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
/*     */ import org.apache.mina.filter.logging.LoggingFilter;
/*     */ import org.apache.mina.transport.socket.nio.NioSocketConnector;
/*     */ 
/*     */ public class MessageQueueTcpClient
/*     */   implements TcpClient
/*     */ {
/*  34 */   private static Logger logger = Logger.getLogger(MessageQueueTcpClient.class);
/*     */   private String host;
/*     */   private int port;
/*  38 */   private Lock lock = new ReentrantLock();
/*  39 */   private Condition connectedCondition = this.lock.newCondition();
/*     */   boolean connected;
/*     */   private ConnectFuture result;
/*     */   private IoSession session;
/*     */   private IoConnector connector;
/*     */ 
/*     */   public int connect(String ip, int port, Object msg)
/*     */   {
/*  51 */     this.host = ip;
/*  52 */     this.port = port;
/*  53 */     SocketAddress address = new InetSocketAddress(this.host, this.port);
/*     */ 
/*  55 */     this.connector = new NioSocketConnector();
/*     */ 
/*  58 */     this.connector.setHandler(new MessageQueueClientHandler((AbstractMessage)msg));
/*     */ 
/*  60 */     DefaultIoFilterChainBuilder chain = this.connector.getFilterChain();
/*     */ 
/*  62 */     chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
/*     */ 
/*  64 */     chain.addLast("logger", new LoggingFilter());
/*     */ 
/*  67 */     this.result = this.connector.connect(address);
/*     */     try {
/*  69 */       Thread.sleep(1000L);
/*     */     } catch (InterruptedException e) {
/*  71 */       e.printStackTrace();
/*     */     }
/*  73 */     if (this.result.isConnected()) {
/*  74 */       return 0;
/*     */     }
/*  76 */     return 1;
/*     */   }
/*     */ 
/*     */   public void disConnect()
/*     */   {
/*  87 */     this.lock.lock();
/*     */     try {
/*  89 */       if (this.result.isConnected()) {
/*  90 */         this.session = this.result.getSession();
/*  91 */         this.session.write("客户端主动断开连接！");
/*  92 */         this.session.close(true);
/*  93 */         this.connector.dispose();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  97 */       e.printStackTrace();
/*     */     } finally {
/*  99 */       this.lock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reConnect()
/*     */   {
/*     */     try
/*     */     {
/* 110 */       if ((this.session == null) || (!this.session.isConnected())) {
/* 111 */         if (connect(this.host, this.port) == 0)
/* 112 */           this.connected = true;
/*     */         else {
/* 114 */           logger.error("connect to server error!");
/*     */         }
/*     */       }
/*     */ 
/* 118 */       this.connectedCondition.signalAll();
/*     */     } catch (Exception e) {
/* 120 */       logger.error("reConnect is error!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public int connect(String ip, int port)
/*     */   {
/* 131 */     return 0;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mina.smsclient.MessageQueueTcpClient
 * JD-Core Version:    0.6.0
 */