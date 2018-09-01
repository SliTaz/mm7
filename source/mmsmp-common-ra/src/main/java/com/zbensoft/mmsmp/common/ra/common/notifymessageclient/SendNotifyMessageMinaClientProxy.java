 package com.zbensoft.mmsmp.common.ra.common.notifymessageclient;
 import java.io.Serializable;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;

 
 public class SendNotifyMessageMinaClientProxy
 {
   static final Logger logger = Logger.getLogger(SendNotifyMessageMinaClientProxy.class);
   private String host;
   private int port;
 
   public SendNotifyMessageMinaClientProxy(String host, int port)
   {
     this.host = host;
     this.port = port;
   }
 
   public int send(Serializable message) throws Exception
   {
     NioSocketConnector connector = null;
     int result = 1;
     try
     {
       connector = new NioSocketConnector();
       connector.setConnectTimeout(30);
       connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
       connector.setHandler(new ClientHandler());
       ConnectFuture future = connector.connect(new InetSocketAddress(this.host, this.port));
       future.addListener(new IoFutureListener<ConnectFuture>() {
           public void operationComplete(ConnectFuture future) {
               if (future.isConnected())
               {
                 IoSession session = future.getSession();
                 session.write(message);
                 session.close();
     
                 SendNotifyMessageMinaClientProxy.logger.info("SendNotifyMessageMinaClientProxy send message:\r\n" + message.toString());
               }
               else
               {
                 SendNotifyMessageMinaClientProxy.logger.info("SendNotifyMessageMinaClientProxy send message failed:\r\n" + message);
               }
           }
       });
//       future.addListener(new IoFutureListener(message)
//       {
//         public void operationComplete(ConnectFuture future)
//         {
//           if (future.isConnected())
//           {
//             IoSession session = future.getSession();
//             session.write(this.val$message);
//             session.close();
// 
//             SendNotifyMessageMinaClientProxy.logger.info("SendNotifyMessageMinaClientProxy send message:\r\n" + this.val$message.toString());
//           }
//           else
//           {
//             SendNotifyMessageMinaClientProxy.logger.info("SendNotifyMessageMinaClientProxy send message failed:\r\n" + this.val$message);
//           }
//         }
//       });
       result = 0;
     }
     catch (Exception ex)
     {
       ex.printStackTrace();
     }
     finally {
       connector.dispose();
     }
     return result;
   }
 
   public String getHost()
   {
     return this.host;
   }
 
   public void setHost(String host) {
     this.host = host;
   }
 
   public int getPort() {
     return this.port;
   }
 
   public void setPort(int port) {
     this.port = port;
   }
   public static void main(String[] agrs) {
     SendNotificationMessage notifyMsg = new SendNotificationMessage();
     notifyMsg.setGlobalMessageTime(System.currentTimeMillis());
     notifyMsg.setGlobalMessageid(SendNotificationMessage.generateUUID("admin-sms"));
     notifyMsg.setPhoneNumber(new String[] { "13521725997" });
     notifyMsg.setSendContent("短信内容短信内容");
     SendNotifyMessageMinaClientProxy notifyMsgClient = new SendNotifyMessageMinaClientProxy("127.0.0.1", 8000);
     try {
       int a = notifyMsgClient.send(notifyMsg);
       System.out.println(a);
     }
     catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   static class ClientHandler extends IoHandlerAdapter
   {
     public void sessionOpened(IoSession session)
       throws Exception
     {
       session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
     }
 
     public void sessionIdle(IoSession session, IdleStatus status)
       throws Exception
     {
       if (status == IdleStatus.READER_IDLE)
       {
         session.close(true);
       }
     }
 
     public void exceptionCaught(IoSession session, Throwable cause)
       throws Exception
     {
       session.close();
     }
   }
 }
