/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ import org.apache.log4j.Logger;

/*    */
/*    */ public class MessageQueueClientJVMImpl
/*    */ {
/* 18 */   private static Logger logger = Logger.getLogger(MessageQueueClientJVMImpl.class);
/*    */   private MessageQueue queue;
/*    */ 
/*    */   public MessageQueueClientJVMImpl(MessageQueue queue)
/*    */   {
/* 26 */     this.queue = queue;
/*    */   }
/*    */ 
/*    */   public int send(AbstractMessage message)
/*    */   {
/* 33 */     if (message.getPriority() == MessageQueue.PRIORITY_LOW) {
/* 34 */       this.queue.addMessage(message, Integer.valueOf(MessageQueue.PRIORITY_LOW));
/*    */     }
/* 36 */     else if (message.getPriority() == MessageQueue.PRIORITY_VIP) {
/* 37 */       this.queue.addMessage(message, Integer.valueOf(MessageQueue.PRIORITY_VIP));
/*    */     }
/*    */     else {
/* 40 */       this.queue.addMessage(message, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
/*    */     }
/* 42 */     logger.debug("message sened. priority: " + message.getPriority() + ". message:" + message);
/* 43 */     return 0;
/*    */   }
/*    */ 
/*    */   public MessageQueue getQueue()
/*    */   {
/* 49 */     return this.queue;
/*    */   }
/*    */ 
/*    */   public void setQueue(MessageQueue queue)
/*    */   {
/* 55 */     this.queue = queue;
/*    */   }
/*    */ 
/*    */   public void stop()
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.queue.MessageQueueClientJVMImpl
 * JD-Core Version:    0.6.0
 */