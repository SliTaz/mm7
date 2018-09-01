 package com.zbensoft.mmsmp.corebiz.util;
 
 import java.io.IOException;
 import java.util.Collection;
 import java.util.concurrent.LinkedBlockingQueue;
 import java.util.concurrent.atomic.AtomicInteger;
 
 public class ProxyQueue
   extends LinkedBlockingQueue
 {
   MessageQueue queue;
   private final AtomicInteger putCount = new AtomicInteger(0);
   private final AtomicInteger pollCount = new AtomicInteger(0);
   
   private final AtomicInteger lastSecondPutNum = new AtomicInteger(0);
   private final AtomicInteger lastSecondPollNum = new AtomicInteger(0);
   
   public ProxyQueue(String dataPath, String queueName) {
     this.queue = new MessageQueue(dataPath, queueName);
     
     new Thread(new Runnable()
     {
       public void run() {
         for (;;) {
           try {
             Thread.sleep(1000L);
           }
           catch (InterruptedException localInterruptedException) {}
           
           ProxyQueue.this.lastSecondPutNum.set(ProxyQueue.this.putCount.getAndSet(0));
           ProxyQueue.this.lastSecondPollNum.set(ProxyQueue.this.pollCount.getAndSet(0));
         }
       }
     }, queueName + "_monitor_thread").start();
   }
   
   public int getLastSecondPutNum() {
     return this.lastSecondPutNum.get();
   }
   
   public int getLastSecondPollNum() {
     return this.lastSecondPollNum.get();
   }
   
 
   public int size()
   {
     return this.queue.size();
   }
   
   public void put(Object e) throws InterruptedException
   {
     try
     {
       this.queue.offer(e);
       this.putCount.incrementAndGet();
     } catch (IOException e1) {
       e1.printStackTrace();
     }
   }
   
   public boolean addAll(Collection c)
   {
     try {
       for (Object e : c) {
         this.queue.offer(e);
         this.putCount.incrementAndGet();
       }
     } catch (IOException e1) {
       e1.printStackTrace();
       return false;
     }
     return true;
   }
   
   public Object take()
     throws InterruptedException
   {
     Object r = null;
     try
     {
       while ((r = this.queue.poll()) == null)
       {
         Thread.sleep(10L);
       }
       this.pollCount.incrementAndGet();
     }
     catch (ClassNotFoundException e) {
       e.printStackTrace();
     }
     catch (IOException e) {
       e.printStackTrace();
     }
     return r;
   }
 }


