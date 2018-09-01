 package com.zbensoft.mmsmp.corebiz.route;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.Set;
 import java.util.concurrent.BlockingQueue;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 public class BusinessRouter
   implements IMessageRouter
 {
   static final Logger logger = Logger.getLogger(BusinessRouter.class);
   
 
   ConcurrentHashMap<String, Counter> countMap;
   
   ConcurrentHashMap<String, BlockingQueue> policyMap;
   
   boolean debug = false;
   
   public BusinessRouter()
   {
     String value = System.getProperty("debug");
     if ((value != null) && (value.equalsIgnoreCase("true"))) {
       this.debug = true;
     }
   }
   
   public void doRouter(Serializable message)
   {
     try
     {
       ((BlockingQueue)this.policyMap.get(message.getClass().getName())).put(message);
       if (this.debug) {
         ((Counter)this.countMap.get(message.getClass().getName())).oneStep();
       }
     }
     catch (Exception ex)
     {
       logger.error(ex);
     }
   }
   
   public void setPolicyMap(ConcurrentHashMap<String, BlockingQueue> policyMap)
   {
     this.policyMap = policyMap;
     
     if (this.debug) {
       this.countMap = new ConcurrentHashMap();
       
 
       Iterator<String> it = this.policyMap.keySet().iterator();
       while (it.hasNext()) {
         String key = (String)it.next();
         this.countMap.put(key, new Counter());
       }
       
 
       Thread monitor = new Thread(new Runnable()
       {
         public void run()
         {
           for (;;) {
             Iterator<String> it = BusinessRouter.this.policyMap.keySet().iterator();
             ArrayList<BlockingQueue> qList = new ArrayList();
             ArrayList<ArrayList<String>> sbList = new ArrayList();
             while (it.hasNext()) {
               String key = (String)it.next();
               BlockingQueue q = (BlockingQueue)BusinessRouter.this.policyMap.get(key);
               if (!qList.contains(q)) {
                 qList.add(q);
                 ArrayList<String> clist = new ArrayList();
                 clist.add(key);
                 sbList.add(clist);
               } else {
                 int index = qList.indexOf(q);
                 ((ArrayList)sbList.get(index)).add(key);
               }
             }
             
 
 
 
             for (int i = 0; i < qList.size(); i++) {
               BlockingQueue tq = (BlockingQueue)qList.get(i);
               BusinessRouter.logger.warn(i + " queue size:" + tq.size());
               ArrayList<String> tc = (ArrayList)sbList.get(i);
               for (String c : tc) {
                 BusinessRouter.logger.warn("    " + c + " SUM:" + ((BusinessRouter.Counter)BusinessRouter.this.countMap.get(c)).getNumber());
               }
             }
             try
             {
               Thread.sleep(60000L);
             }
             catch (Exception localException) {}
           }
         }
       });
       monitor.setDaemon(true);
       monitor.start();
     }
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
   class Counter
   {
     long number = 0L;
     
     Counter() {}
     
     synchronized void oneStep() {
       this.number += 1L;
     }
     
     synchronized long getNumber()
     {
       return this.number;
     }
   }
 }





