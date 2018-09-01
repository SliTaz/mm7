 package com.zbensoft.mmsmp.common.ra.common.message;

 import com.zbensoft.mmsmp.common.ra.common.queue.messagehandler.AbstractMessageHandler;
 import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.IServerHandler;
 import org.apache.log4j.Logger;

 import java.io.Serializable;
 import java.util.HashMap;
 import java.util.Map;

 public class ReceiveMMSHTTPMessageHandler
   implements IServerHandler
 {
   private static Logger logger = Logger.getLogger(ReceiveMMSHTTPMessageHandler.class);
   private MessageQueue queue;
   private Map<Class<?>, AbstractMessageHandler> messageHandlerMap = new HashMap();
 
   public ReceiveMMSHTTPMessageHandler(MessageQueue queue)
   {
     this.queue = queue;
   }
 
   public void onConnect(int arg0, String arg1, int arg2)
   {
   }
 
   public void onDisconnect(int arg0, Object arg1)
   {
   }
 
   public void onReceiveMsg(int arg0, Object arg1, byte[] arg2)
   {
   }
 
   public void onReceiveMsg(int arg0, Object arg1, Serializable arg2)
   {
     if ((arg2 instanceof AbstractMessage)) {
       AbstractMessage message = (AbstractMessage)arg2;
       if ((message instanceof MT_MMHttpMessage)) {
         logger.info("receive a MT_MMHttpMessage message");
       }
 
       if (message.getPriority() == MessageQueue.PRIORITY_COMMON_SMS)
         this.queue.addMessageNoBlock(message, MessageQueue.PRIORITY_COMMON_SMS);
     }
   }
 
   public void onSendedMsg(int arg0, Object arg1, byte[] arg2)
   {
   }
 
   public void onSendedMsg(int arg0, Object arg1, Serializable arg2)
   {
   }
 
   public int slice(int arg0, byte[] arg1)
   {
     return 0;
   }
 
   public MessageQueue getQueue()
   {
     return this.queue;
   }
 
   public void setQueue(MessageQueue queue)
   {
     this.queue = queue;
   }
 }
