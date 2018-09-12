 package com.zbensoft.mmsmp.corebiz.service.xfire;
 
 import com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness.UniBusiness;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.codehaus.xfire.XFire;
 import org.codehaus.xfire.XFireFactory;
 import org.codehaus.xfire.jaxb2.JaxbServiceFactory;
 import org.codehaus.xfire.server.http.XFireHttpServer;
 import org.codehaus.xfire.service.Service;
 import org.codehaus.xfire.service.ServiceRegistry;
 import org.codehaus.xfire.service.invoker.BeanInvoker;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.ApplicationListener;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class XFireServiceListener
   implements ApplicationListener
 {
   final Log logger = LogFactory.getLog(getClass().getName());
   
   private int port = -1;
   
   private UniBusinessServiceImpl uniBusinessServiceImpl;
   
   public void onApplicationEvent(ApplicationEvent event)
   {
     try
     {
       JaxbServiceFactory serviceFactory = new JaxbServiceFactory();
       
       Service service = serviceFactory.create(UniBusiness.class);
       service.setInvoker(new BeanInvoker(this.uniBusinessServiceImpl));
       
       XFire xfire = XFireFactory.newInstance().getXFire();
       xfire.getServiceRegistry().register(service);
       
       XFireHttpServer server = new XFireHttpServer();
       server.setPort(this.port);
       server.start();
       
       this.logger.info("xfire server startup with " + this.port);
     }
     catch (Exception e)
     {
       this.logger.error("xfire process event " + event + " error", e);
     }
   }
   
 
 
   public void setUniBusinessServiceImpl(UniBusinessServiceImpl uniBusinessServiceImpl)
   {
     this.uniBusinessServiceImpl = uniBusinessServiceImpl;
   }
   
   public int getPort() {
     return this.port;
   }
   
   public void setPort(int port) {
     this.port = port;
   }
 }





