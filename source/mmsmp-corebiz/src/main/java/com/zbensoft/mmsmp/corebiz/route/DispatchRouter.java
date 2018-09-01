 package com.zbensoft.mmsmp.corebiz.route;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.corebiz.service.mina.MinaClientProxy;
 import java.io.Serializable;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 
 public class DispatchRouter
   implements IMessageRouter
 {
   static final Logger logger = Logger.getLogger(DispatchRouter.class);
   
 
 
   ConcurrentHashMap<String, MinaClientProxy> policyMap;
   
 
 
 
   public void doRouter(Serializable message)
   {
     try
     {
       ((MinaClientProxy)this.policyMap.get(message.getClass().getName())).send((AbstractMessage)message);
     }
     catch (Exception ex)
     {
       logger.error(ex);
     }
   }
   
   public void setPolicyMap(ConcurrentHashMap<String, MinaClientProxy> policyMap)
   {
     this.policyMap = policyMap;
   }
 }





