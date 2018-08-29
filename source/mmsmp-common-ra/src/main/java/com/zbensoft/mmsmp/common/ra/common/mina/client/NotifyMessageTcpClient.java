/*     */ package com.zbensoft.mmsmp.common.ra.common.mina.client;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.mina.TcpClient;
import com.zbensoft.mmsmp.common.ra.common.mina.handler.NotifyMessageClientHandler;

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
/*     */ public class NotifyMessageTcpClient
/*     */   implements TcpClient
/*     */ {
/*  34 */   private static Logger logger = Logger.getLogger(NotifyMessageTcpClient.class);
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
/*  51 */     this.host = this.host;
/*  52 */     this.port = port;
/*  53 */     SocketAddress address = new InetSocketAddress(this.host, this.port);
/*     */ 
/*  55 */     this.connector = new NioSocketConnector();
/*     */ 
/*  57 */     this.connector.setConnectTimeoutMillis(30000L);
/*     */ 
/*  59 */     this.connector.setHandler(new NotifyMessageClientHandler((AbstractMessage)msg));
/*     */ 
/*  61 */     DefaultIoFilterChainBuilder chain = this.connector.getFilterChain();
/*     */ 
/*  63 */     chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
/*     */ 
/*  65 */     chain.addLast("logger", new LoggingFilter());
/*     */ 
/*  67 */     this.result = this.connector.connect(address);
/*     */ 
/*  69 */     if (this.result.isConnected()) {
/*  70 */       return 0;
/*     */     }
/*  72 */     return 1;
/*     */   }
/*     */ 
/*     */   public void disConnect()
/*     */   {
/*  83 */     this.lock.lock();
/*     */     try {
/*  85 */       if (this.result.isConnected()) {
/*  86 */         this.session = this.result.getSession();
/*  87 */         this.session.write("QUIT");
/*  88 */         this.session.close(true);
/*     */       }
/*     */     } catch (Exception e) {
/*  91 */       e.printStackTrace();
/*     */     } finally {
/*  93 */       this.lock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reConnect()
/*     */   {
/*     */     try
/*     */     {
/* 104 */       if ((this.session == null) || (!this.session.isConnected())) {
/* 105 */         if (connect(this.host, this.port) == 0)
/* 106 */           this.connected = true;
/*     */         else {
/* 108 */           logger.error("connect to server error!");
/*     */         }
/*     */       }
/*     */ 
/* 112 */       this.connectedCondition.signalAll();
/*     */     } catch (Exception e) {
/* 114 */       logger.error("reConnect is error!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public int connect(String ip, int port)
/*     */   {
/* 123 */     return 0;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mina.smsclient.NotifyMessageTcpClient
 * JD-Core Version:    0.6.0
 */