 package com.zbensoft.mmsmp.corebiz.util;
 
 import com.leansoft.bigqueue.BigQueueImpl;
 import com.leansoft.bigqueue.IBigQueue;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.Closeable;
 import java.io.IOException;
 import java.io.ObjectInputStream;
 import java.io.ObjectOutputStream;
 import java.util.concurrent.atomic.AtomicInteger;
 
 
 public class MessageQueue
 {
   IBigQueue bigQueue = null;
   
   private final AtomicInteger putCount = new AtomicInteger(0);
   private final AtomicInteger pollCount = new AtomicInteger(0);
   
   private final AtomicInteger lastSecondPutNum = new AtomicInteger(0);
   private final AtomicInteger lastSecondPollNum = new AtomicInteger(0);
   
   public MessageQueue(String dataPath, String queueName) {
     try {
       this.bigQueue = new BigQueueImpl(dataPath, queueName);
       
       new Thread(new Runnable()
       {
         public void run() {
           for (;;) {
             try {
               Thread.sleep(1000L);
             }
             catch (InterruptedException localInterruptedException) {}
             
             MessageQueue.this.lastSecondPutNum.set(MessageQueue.this.putCount.getAndSet(0));
             MessageQueue.this.lastSecondPollNum.set(MessageQueue.this.pollCount.getAndSet(0));
           }
         }
       }, queueName + "_monitor_thread").start();
       
       new Thread(new Runnable()
       {
         public void run() {
           for (;;) {
             try {
               Thread.sleep(30000L);
             }
             catch (InterruptedException localInterruptedException) {}
             if (MessageQueue.this.bigQueue != null) {
               try {
                 MessageQueue.this.bigQueue.gc();
               } catch (IOException e) {
                 e.printStackTrace();
               }
             }
           }
         }
       }, queueName + "_gc_thread").start();
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
   
   public int getLastSecondPutNum() {
     return this.lastSecondPutNum.get();
   }
   
   public int getLastSecondPollNum() {
     return this.lastSecondPollNum.get();
   }
   
   public void put(Object item) throws IOException
   {
     this.bigQueue.enqueue(toBytes(item));
     this.putCount.incrementAndGet();
   }
   
 
 
   public void offer(Object item) throws IOException { put(item); }
   
   public void clear() {
     byte[] data;
     do {
       data = (byte[])null;
       try {
         data = this.bigQueue.dequeue();
       }
       catch (IOException localIOException) {}
     } while (data != null);
   }
   
 
   public Object poll()
     throws ClassNotFoundException, IOException
   {
     byte[] bytes = this.bigQueue.dequeue();
     if (bytes == null) {
       return null;
     }
     Object e = toObject(bytes);
     this.pollCount.incrementAndGet();
     return e;
   }
   
   public int size()
   {
     return (int)this.bigQueue.size();
   }
   
   public Object peek() throws ClassNotFoundException, IOException {
     return toObject(this.bigQueue.peek());
   }
   
   private byte[] toBytes(Object item) throws IOException {
     ByteArrayOutputStream baos = null;
     ObjectOutputStream oos = null;
     try {
       baos = new ByteArrayOutputStream();
       oos = new ObjectOutputStream(baos);
       
       oos.writeObject(item);
       oos.flush();
       return baos.toByteArray();
     } finally {
       closeResource(baos);
       closeResource(oos);
     }
   }
   
   private Object toObject(byte[] content)
     throws IOException, ClassNotFoundException
   {
     ByteArrayInputStream bais = null;
     ObjectInputStream ois = null;
     try {
       bais = new ByteArrayInputStream(content);
       ois = new ObjectInputStream(bais);
       
       return ois.readObject();
     } finally {
       closeResource(bais);
       closeResource(ois);
     }
   }
   
   private void closeResource(Closeable c) throws IOException {
     if (c != null) {
       c.close();
     }
   }
 }


