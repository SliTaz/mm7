 package com.zbensoft.mmsmp.vas.sjb.messagehandler;
 
 import com.zbensoft.mmsmp.common.ra.common.message.PauseNotificationMessage;
 import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
 import com.zbensoft.mmsmp.common.ra.common.queue.messagehandler.AbstractMessageHandler;
 
 
 
 
 
 
 
 
 
 public class PauseNotificationMessageHandler
   extends AbstractMessageHandler
 {
   private MessageQueue queue;
   
   public boolean handleable(Object notifyMessage)
   {
     if ((notifyMessage instanceof PauseNotificationMessage)) {
       return true;
     }
     return false;
   }
   
   public void handleMessage(Object notifyMessage)
   {
     PauseNotificationMessage message = (PauseNotificationMessage)notifyMessage;
     
     int contentId = message.getContentId();
     
     this.queue.removeMessage(Integer.valueOf(contentId));
   }
   
 
   public MessageQueue getQueue()
   {
     return this.queue;
   }
   
   public void setQueue(MessageQueue queue) {
     this.queue = queue;
   }
 }





