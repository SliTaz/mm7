 package com.zbensoft.mmsmp.corebiz.service.mina;
 
 import java.net.InetSocketAddress;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Set;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.ApplicationListener;
 import org.springframework.context.event.ContextClosedEvent;
 import org.springframework.context.event.ContextRefreshedEvent;
 
 
 
 
 
 
 public class MinaServerListener implements ApplicationListener
 {
   final Log logger = LogFactory.getLog(getClass().getName());
   
   Map<String, NioSocketAcceptor> nioSocketAcceptors;
   
   public void onApplicationEvent(ApplicationEvent event)
   {
     try
     {
       Iterator<String> it = this.nioSocketAcceptors.keySet().iterator();
       
       while (it.hasNext())
       {
         String key = (String)it.next();
         NioSocketAcceptor socket = (NioSocketAcceptor)this.nioSocketAcceptors.get(key);
         
         if ((event instanceof ContextRefreshedEvent))
         {
           this.logger.info(key + " mina server started at port " + socket.getDefaultLocalAddress().getPort());
         }
         else if ((event instanceof ContextClosedEvent))
         {
           this.logger.info(key + " mina server stopped at port " + socket.getDefaultLocalAddress().getPort());
         }
       }
     }
     catch (Exception ex)
     {
       this.logger.error(ex);
     }
   }
   
   public void setNioSocketAcceptors(Map<String, NioSocketAcceptor> nioSocketAcceptors)
   {
     this.nioSocketAcceptors = nioSocketAcceptors;
   }
 }





