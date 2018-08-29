 package com.zbensoft.mmsmp.common.ra.common.queue;
 
 import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.IClientHandler;
 
 public class SendMessageHandler
   implements IClientHandler
 {
   private static final Log logger = LogFactory.getLog(SendMessageHandler.class);
   private MessageQueueClientTcpImpl messageQueueClientTcpImpl;
 
   public void onConnect(String arg0, int arg1)
   {
     logger.info("connected");
   }
 
   public void onDisconnect()
   {
     logger.info("onDisconnect");
     this.messageQueueClientTcpImpl.connected = false;
   }
 
   public void onReceiveMsg(byte[] arg0)
   {
   }
 
   public void onReceiveMsg(Serializable arg0)
   {
   }
 
   public void onSendedMsg(byte[] arg0)
   {
   }
 
   public void onSendedMsg(Serializable arg0)
   {
     logger.debug("message sended");
   }
 
   public int slice(byte[] arg0)
   {
     return 0;
   }
 
   public SendMessageHandler(MessageQueueClientTcpImpl messageQueueClientTcpImpl)
   {
     this.messageQueueClientTcpImpl = messageQueueClientTcpImpl;
   }
 }
