 package com.zbensoft.mmsmp.vas.sjb.common;
 
 import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
 import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_MEMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_WapPushMessage;
 import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
 import com.zbensoft.mmsmp.vas.sjb.send.SendRouter;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 
 
 
 
 
 
 
 
 
 public class DealMessageThread
   implements Runnable
 {
   private static final Log logger = LogFactory.getLog(DealMessageThread.class);
   public static long mmsInterval = ConfigUtil.getInstance().getCorebizConfig().getMmsInterval();
   public static long mmeInterval = ConfigUtil.getInstance().getCorebizConfig().getMmeInterval();
   public static long smsInterval = ConfigUtil.getInstance().getCorebizConfig().getSmsInterval();
   public static long wapInterval = ConfigUtil.getInstance().getCorebizConfig().getWapInterval();
   
 
   private Router router;
   
   private MessageQueue queue;
   
   private int priority;
   
 
   public DealMessageThread(Router router, MessageQueue queue, int priority)
   {
     this.router = router;
     this.queue = queue;
     this.priority = priority;
   }
   
 
   public void run()
   {
     for (;;)
     {
       AbstractMessage message = null;
       try {
         if ((this.router instanceof SendRouter)) {
           if (this.priority == MessageQueue.PRIORITY_COMMON) {
             logger.info("!!starting to get next message from common_priority queue");
           } else if (this.priority == MessageQueue.PRIORITY_VIP) {
             logger.info("!!startring to get next message from vip_priority queue");
           }
         }
         message = this.queue.getMessage(Integer.valueOf(this.priority));
         if (message != null){
        	 break;
         }
         Thread.sleep(1000L);
       }
       catch (Exception ex) {
         try {
           Thread.sleep(1000L);
         }
         catch (Exception e) {
           logger.error(e.getMessage());
         } 
         continue;
       }
       try{
         this.router.doRouter(message);
       } catch (Exception e) {
         logger.error(e);
       }
       
       if ((this.router instanceof SendRouter)) {
         try {
           if ((message instanceof MT_MMMessage)) {
             Thread.sleep(mmsInterval);
           } else if ((message instanceof MT_MEMessage)) {
             Thread.sleep(mmeInterval);
           } else if ((message instanceof MT_SMMessage)) {
             Thread.sleep(smsInterval);
           } else if ((message instanceof MT_WapPushMessage)) {
             Thread.sleep(wapInterval);
           }
         } catch (Exception e) {
           logger.error(e.getMessage());
         }
       }
     }
   }
 }





