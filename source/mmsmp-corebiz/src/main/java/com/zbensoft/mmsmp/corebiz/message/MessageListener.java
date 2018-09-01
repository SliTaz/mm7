 package com.zbensoft.mmsmp.corebiz.message;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.corebiz.handle.IMessageHandler;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import java.util.concurrent.LinkedBlockingQueue;
 import java.util.concurrent.ThreadFactory;
 import java.util.concurrent.atomic.AtomicInteger;
 import org.apache.log4j.Logger;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.ApplicationListener;
 import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
 
 
 
 public class MessageListener implements ApplicationListener
 {
   static final Logger logger = Logger.getLogger(MessageListener.class);
   
   String info;
   
   String name;
   
   int number;
   
   int idle;
   
   LinkedBlockingQueue<Object> queue;
   
   IMessageHandler handler;
   
   ThreadFactory factory;
   ExecutorService execor;
   ThreadPoolTaskExecutor handleHelper;
   private Thread consumerThread;
   
   public void onApplicationEvent(ApplicationEvent event) {}
   
   public void start()
   {
     this.info = (this.info == null ? MessageListener.class.getSimpleName() : this.info);
     this.factory = new MessageThreadFactory(this.info);
     this.execor = Executors.newFixedThreadPool(this.number, this.factory);
     
     this.name = (this.info + " thread");
     
     this.consumerThread = new Thread(new MessageConsumer());
     this.consumerThread.setName("MessageListener-Consumer " + this.info);
     this.consumerThread.start();
   }
   
 
   public void stop()
   {
     this.execor.shutdown();
   }
   
   static class MessageThreadFactory implements ThreadFactory
   {
     static final AtomicInteger poolNumber = new AtomicInteger(1);
     
     final AtomicInteger threadNumber = new AtomicInteger(1);
     final ThreadGroup group;
     final String namePrefix;
     final String nameSuffix;
     
     MessageThreadFactory(String threadSuffix) {
       SecurityManager s = System.getSecurityManager();
       this.group = (s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup());
       
       this.namePrefix = ("corebiz pool-[" + poolNumber.getAndIncrement() + "]" + "-thread-");
       this.nameSuffix = threadSuffix;
     }
     
     public Thread newThread(Runnable r) {
       Thread t = new Thread(this.group, r, this.namePrefix + "[" + this.nameSuffix + "]" + "[" + this.threadNumber.getAndIncrement() + "]", 0L);
       if (t.isDaemon())
         t.setDaemon(false);
       if (t.getPriority() != 5)
         t.setPriority(5);
       return t;
     }
   }
   
   class MessageConsumer implements Runnable
   {
     MessageConsumer() {}
     
     public void run() {
       try {
         for (;;) {
           Object message = MessageListener.this.queue.take();
           
           if (message != null) {
             MessageListener.MessageHandler mh = new MessageListener.MessageHandler(MessageListener.this);
             mh.setMessage((AbstractMessage)message);
             MessageListener.this.execor.execute(mh);
           }
         }
       } catch (Exception ex) { MessageListener.logger.error(MessageListener.this.name + " listener exception:" + ex.getMessage());
         ex.printStackTrace();
       }
     }
   }
   
   class MessageHandler implements Runnable
   {
     AbstractMessage message;
     MessageListener messageListener;
     MessageHandler(MessageListener messageListener) {
    	 this.messageListener=messageListener;
     }
     
     public void setMessage(AbstractMessage message) {
       this.message = message;
     }
     
     public void run()
     {
       try {
    	   messageListener.handler.doHandler(this.message);
       }
       catch (Exception e) {
         try {
           if (this.message != null) {
        	   messageListener.logger.error("ChargetNumber : " + this.message.getChargetNumber() + "ServiceId : \t" + this.message.getServiceId() + "GlobalMessageid : \t" + this.message.getGlobalMessageid() + "\t" + e.getMessage());
           } else {
        	   messageListener.logger.error("message is null" + e.getMessage());
           }
         }
         catch (Exception e1) {
        	 messageListener.logger.error("exception: \t" + e1.getMessage());
         }
       }
     }
   }
   
   public void sleeping(int time)
   {
     try {
       Thread.sleep(time);
     } catch (Exception localException) {}
   }
   
   public void setNumber(int number) { this.number = number; }
   
   public void setIdle(int idle)
   {
     this.idle = idle;
   }
   
   public void setQueue(LinkedBlockingQueue<Object> queue) {
     this.queue = queue;
   }
   
   public void setHandler(IMessageHandler handler) {
     this.handler = handler;
   }
   
   public int getNumber() {
     return this.number;
   }
   
   public void setInfo(String info) {
     this.info = info;
   }
   
   public void setHandleHelper(ThreadPoolTaskExecutor handleHelper) {
     this.handleHelper = handleHelper;
   }
 }





