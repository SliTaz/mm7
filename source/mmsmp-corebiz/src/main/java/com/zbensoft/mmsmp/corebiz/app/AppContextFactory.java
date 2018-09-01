 package com.zbensoft.mmsmp.corebiz.app;
 
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.support.ClassPathXmlApplicationContext;
 
 
 public class AppContextFactory
 {
   static ClassPathXmlApplicationContext ctx;
   
   public static ApplicationContext getApplicationContext()
   {
     if (ctx == null)
     {
       ctx = new ClassPathXmlApplicationContext(new String[] { "classpath:/applicationContext.xml" });
     }
     
     return ctx;
   }
   
   public static Object getBean(String name)
   {
     return getApplicationContext().getBean(name);
   }
 }





