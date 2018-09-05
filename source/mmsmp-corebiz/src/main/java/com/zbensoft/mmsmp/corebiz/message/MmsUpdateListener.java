 package com.zbensoft.mmsmp.corebiz.message;
 
 import com.zbensoft.mmsmp.corebiz.dao.SmsDAO;
 import java.util.Date;
 import java.util.concurrent.ArrayBlockingQueue;
 import java.util.concurrent.BlockingQueue;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import org.apache.log4j.Logger;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.ApplicationListener;
 
 public class MmsUpdateListener
   implements ApplicationListener
 {
   static final Logger logger = Logger.getLogger(MmsUpdateListener.class);
   
   BlockingQueue<MmsHistoryMessage> mmsUpdate = new ArrayBlockingQueue(100000);
   
   ExecutorService execor;
   SmsDAO smsDAO;
   String info;
   String name;
   int idle = 10;
   int number = 1;
   int cacheTime = 60;
   
 
 
   public void put(MmsHistoryMessage message)
   {
     try
     {
       this.mmsUpdate.put(message);
     } catch (InterruptedException e) {
       e.printStackTrace();
     }
   }
   
   public void start()
   {
     sleeping(100);
     
     this.number = (this.number < 1 ? 1 : this.number);
     
     this.execor = Executors.newFixedThreadPool(this.number);
     
     for (int i = 0; i < this.number; i++)
     {
       Thread thread = new Thread(new MessageConsumer(this.mmsUpdate));
       this.name = (this.info + " thread[" + i + "]");
       thread.setName(this.name);
       this.execor.execute(thread);
       
       logger.info(this.info + " listener thread[" + i + "] startup");
       sleeping(this.idle);
     }
   }
   
   public void shutdown()
   {
     this.execor.shutdown();
   }
   
   class MessageConsumer implements Runnable
   {
     private BlockingQueue queue;
     
     public MessageConsumer(BlockingQueue queue)
     {
       this.queue = queue;
     }
     
     public void run()
     {
       try
       {
         for (;;)
         {
           if (this.queue.size() > 0) {
             MmsHistoryMessage mhm = (MmsHistoryMessage)this.queue.poll();
             
             if ((mhm != null) && (System.currentTimeMillis() - mhm.getReceiveDate().getTime() < MmsUpdateListener.this.cacheTime * 60 * 1000))
             {
            	 //TODO updateGatewaySRecord() 
               int result = 1;//MmsUpdateListener.this.smsDAO.updateGatewaySRecord(mhm.getStatus(), mhm.getMessageId(), mhm.getReqId(), mhm.getMmsGreCode());
               if (result <= 0) {
                 MmsUpdateListener.this.mmsUpdate.put(mhm);
                 MmsUpdateListener.logger.info(" update fail ! ");
               } else {
                 MmsUpdateListener.logger.info(" update success ! ");
               }
             }
           }
           else {
             MmsUpdateListener.this.sleeping(MmsUpdateListener.this.idle);
           }
         }
       } catch (Exception ex) { MmsUpdateListener.logger.error(MmsUpdateListener.this.name + " listener exception:" + ex.getMessage());
         ex.printStackTrace();
         MmsUpdateListener.this.sleeping(MmsUpdateListener.this.idle * 3);
       }
     }
   }
   
   public void sleeping(int time)
   {
     try
     {
       Thread.sleep(time);
     }
     catch (Exception localException) {}
   }
   
 
   public void setSmsDAO(SmsDAO smsDAO)
   {
     this.smsDAO = smsDAO;
   }
   
   public void setInfo(String info)
   {
     this.info = info;
   }
   
   public void setName(String name)
   {
     this.name = name;
   }
   
   public void setNumber(int number)
   {
     this.number = number;
   }
   
   public void setIdle(int idle)
   {
     this.idle = idle;
   }
   
 
   public void onApplicationEvent(ApplicationEvent arg0) {}
   
   public void setCacheTime(int cacheTime)
   {
     this.cacheTime = cacheTime;
   }
 }





