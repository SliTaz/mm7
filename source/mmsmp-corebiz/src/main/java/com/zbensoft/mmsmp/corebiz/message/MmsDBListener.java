 package com.zbensoft.mmsmp.corebiz.message;
 
 import com.zbensoft.mmsmp.corebiz.dao.SmsDAO;
 import java.util.ArrayList;
 import java.util.concurrent.ArrayBlockingQueue;
 import java.util.concurrent.BlockingQueue;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import org.apache.log4j.Logger;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.ApplicationListener;
 
 
 public class MmsDBListener
   implements ApplicationListener
 {
   static final Logger logger = Logger.getLogger(MmsDBListener.class);
   
   BlockingQueue<MmsHistoryMessage>[] queues;
   
   ExecutorService execor;
   SmsDAO smsDAO;
   String info;
   String name;
   int idle = 50;
   int number = 1;
   int queueSize = 10000;
   
   boolean debug = false;
   
   public MmsDBListener()
   {
     String value = System.getProperty("debug");
     if ((value != null) && (value.equalsIgnoreCase("true"))) {
       this.debug = true;
     }
   }
   
   public void put(MmsHistoryMessage message)
   {
     try
     {
       String gmsid = message.getGlobalMessageId();
       int group = gmsid.charAt(gmsid.length() - 1);
       int index = group % this.number;
       
       this.queues[index].put(message);
     }
     catch (InterruptedException e)
     {
       e.printStackTrace();
     }
   }
   
   public void start()
   {
     sleeping(2000);
     
     this.number = (this.number < 1 ? 1 : this.number);
     this.queueSize = (this.queueSize < 10000 ? 10000 : this.queueSize);
     this.queues = new ArrayBlockingQueue[this.number];
     this.execor = Executors.newFixedThreadPool(this.number);
     
     for (int i = 0; i < this.number; i++)
     {
       this.queues[i] = new ArrayBlockingQueue(this.queueSize);
       Thread thread = new Thread(new MessageConsumer(this.queues[i]));
       this.name = (this.info + " thread[" + i + "]");
       thread.setName(this.name);
       this.execor.execute(thread);
       
       logger.info(this.info + " listener thread[" + i + "] startup");
       sleeping(this.idle);
     }
     if (this.debug) {
       Thread monitor = new Thread(new Runnable()
       {
         public void run()
         {
           for (;;) {
             int sum = 0;
             
             if (MmsDBListener.this.queues != null) { BlockingQueue[] arrayOfBlockingQueue;
               int j = (arrayOfBlockingQueue = MmsDBListener.this.queues).length; for (int i = 0; i < j; i++) { BlockingQueue<MmsHistoryMessage> q = arrayOfBlockingQueue[i];
                 sum += q.size();
               }
             }
             
             MmsDBListener.logger.error("MmsDBListener queues size:" + sum);
             try {
               Thread.sleep(1000L);
             }
             catch (InterruptedException localInterruptedException) {}
           }
         }
       });
       monitor.start();
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
           if (this.queue.size() > 0)
           {
             ArrayList list = new ArrayList();
             this.queue.drainTo(list, 100);
             MmsDBListener.this.smsDAO.batchInsertMTRecords(list);
           }
           else
           {
             MmsDBListener.this.sleeping(MmsDBListener.this.idle);
           }
         }
       } catch (Exception ex) {
         MmsDBListener.logger.error(MmsDBListener.this.name + " listener exception:" + ex.getMessage());
         ex.printStackTrace();
         MmsDBListener.this.sleeping(MmsDBListener.this.idle * 3);
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
   
 
   public int getQueueSize()
   {
     int len = 0;
     
     for (int i = 0; i < this.queues.length; i++)
     {
       len += this.queues[i].size();
     }
     
     return len;
   }
   
   public int getQueueCapacity()
   {
     int len = 0;
     
     for (int i = 0; i < this.queues.length; i++)
     {
       len += this.queues[i].remainingCapacity();
     }
     
     return len;
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
   
   public void setQueueSize(int queueSize)
   {
     this.queueSize = queueSize;
   }
   
   public void onApplicationEvent(ApplicationEvent arg0) {}
 }





