 package com.zbensoft.mmsmp.corebiz.util;
 
 import java.util.Collection;
 import java.util.concurrent.LinkedBlockingQueue;
 import java.util.concurrent.TimeUnit;
 import java.util.concurrent.atomic.AtomicInteger;
 
 public class MyLinkedBlockingQueue<E>
   extends LinkedBlockingQueue<E>
 {
   private static final long serialVersionUID = 5534425457011377199L;
   private final AtomicInteger putCount = new AtomicInteger(0);
   private final AtomicInteger pollCount = new AtomicInteger(0);
   
   private final AtomicInteger lastSecondPutNum = new AtomicInteger(0);
   private final AtomicInteger lastSecondPollNum = new AtomicInteger(0);
   
   public MyLinkedBlockingQueue(int capacity) {
     super(capacity);
     
     new Thread(new Runnable()
     {
       public void run() {
         for (;;) {
           try {
             Thread.sleep(1000L);
           }
           catch (InterruptedException localInterruptedException) {}
           
           MyLinkedBlockingQueue.this.lastSecondPutNum.set(MyLinkedBlockingQueue.this.putCount.getAndSet(0));
           MyLinkedBlockingQueue.this.lastSecondPollNum.set(MyLinkedBlockingQueue.this.pollCount.getAndSet(0));
         }
       }
     })
     
 
 
 
 
 
 
 
 
 
 
 
       .start();
   }
   
   public int getLastSecondPutNum() {
     return this.lastSecondPutNum.get();
   }
   
   public int getLastSecondPollNum() {
     return this.lastSecondPollNum.get();
   }
   
   public void put(E e) throws InterruptedException
   {
     super.put(e);
     this.putCount.incrementAndGet();
   }
   
 
   public boolean offer(E e)
   {
     boolean r = super.offer(e);
     if (r) {
       this.putCount.incrementAndGet();
     }
     return r;
   }
   
   public E take()
     throws InterruptedException
   {
     E e = super.take();
     this.pollCount.incrementAndGet();
     return e;
   }
   
 
   public boolean add(E e)
   {
     boolean r = super.add(e);
     if (r) {
       this.putCount.incrementAndGet();
     }
     return r;
   }
   
   public E poll() {
     E e = super.poll();
     if (e != null) {
       this.pollCount.incrementAndGet();
     }
     return e;
   }
   
   public int drainTo(Collection<? super E> c)
   {
     int r = super.drainTo(c);
     this.pollCount.getAndAdd(r);
     return r;
   }
   
   public boolean offer(E e, long timeout, TimeUnit unit)
     throws InterruptedException
   {
     boolean r = super.offer(e, timeout, unit);
     if (r) {
       this.putCount.incrementAndGet();
     }
     return r;
   }
   
   public E poll(long timeout, TimeUnit unit)
     throws InterruptedException
   {
     E e = super.poll();
     if (e != null) {
       this.pollCount.incrementAndGet();
     }
     return e;
   }
   
 
   public int drainTo(Collection<? super E> c, int maxElements)
   {
     int r = super.drainTo(c, maxElements);
     this.pollCount.getAndAdd(r);
     return r;
   }
   
 
 
   public boolean addAll(Collection<? extends E> c)
   {
     boolean r = super.addAll(c);
     if (r) {
       this.putCount.addAndGet(c.size());
     }
     return r;
   }
 }


