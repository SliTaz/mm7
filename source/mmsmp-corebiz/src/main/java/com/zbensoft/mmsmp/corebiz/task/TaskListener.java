 package com.zbensoft.mmsmp.corebiz.task;
 
 import org.apache.log4j.Logger;
 import org.quartz.Scheduler;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.ApplicationListener;
 import org.springframework.context.event.ContextClosedEvent;
 import org.springframework.context.event.ContextRefreshedEvent;

import com.zbensoft.mmsmp.log.COREBIZ_LOG;
 
 
 
 
 
 
 
 
 
 public class TaskListener
   implements ApplicationListener
 {
   static final Logger logger = Logger.getLogger(TaskListener.class);
   
   Scheduler scheduler;
   
   public void onApplicationEvent(ApplicationEvent event)
   {
     try
     {
       if ((event instanceof ContextRefreshedEvent))
       {
        COREBIZ_LOG.INFO("task scheduler started");
       }
       else if ((event instanceof ContextClosedEvent))
       {
         this.scheduler.shutdown();
        COREBIZ_LOG.INFO("task scheduler stopped");
       }
     }
     catch (Exception ex)
     {
       COREBIZ_LOG.ERROR("",ex);
     }
   }
   
   public void setScheduler(Scheduler scheduler) {
     this.scheduler = scheduler;
   }
 }





