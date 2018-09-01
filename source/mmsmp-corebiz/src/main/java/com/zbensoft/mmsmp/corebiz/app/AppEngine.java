 package com.zbensoft.mmsmp.corebiz.app;
 
 import org.apache.log4j.Logger;
 import org.springframework.context.ApplicationContext;
 
 public class AppEngine
 {
   static final Logger logger = Logger.getLogger(AppEngine.class);
   
 
 
   public static void main(String[] args)
   {
     AppEngine appEngine = (AppEngine)AppContextFactory.getApplicationContext().getBean("appEngine");
   }
 }





