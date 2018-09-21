 package com.zbensoft.mmsmp.corebiz.service.jetty;
 
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.mortbay.jetty.Connector;
 import org.mortbay.jetty.Server;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.ApplicationListener;
 import org.springframework.context.event.ContextClosedEvent;
 import org.springframework.context.event.ContextRefreshedEvent;

import com.zbensoft.mmsmp.log.COREBIZ_LOG;
 
 
 
 
 
 
 public class JettyServiceListener
   implements ApplicationListener
 {
   final Log logger = LogFactory.getLog(getClass().getName());
   
   Server jettyServer;
   
   public void onApplicationEvent(ApplicationEvent event)
   {
     if ((event instanceof ContextRefreshedEvent))
     {
      COREBIZ_LOG.INFO(" http server started at port " + this.jettyServer.getConnectors()[0].getPort());
     }
     else if ((event instanceof ContextClosedEvent))
     {
      COREBIZ_LOG.INFO(" http server stopped at port " + this.jettyServer.getConnectors()[0].getPort());
     }
   }
   
   public void setJettyServer(Server jettyServer) {
     this.jettyServer = jettyServer;
   }
 }


