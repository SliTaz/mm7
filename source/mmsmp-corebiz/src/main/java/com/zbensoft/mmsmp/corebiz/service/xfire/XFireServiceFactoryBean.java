 package com.zbensoft.mmsmp.corebiz.service.xfire;
 
 import org.codehaus.xfire.service.Service;
 import org.codehaus.xfire.service.binding.ObjectServiceFactory;
 import org.codehaus.xfire.service.invoker.BeanInvoker;
 import org.springframework.beans.factory.FactoryBean;
 
 
 public class XFireServiceFactoryBean
   implements FactoryBean
 {
   private Class serviceClass;
   private Object target;
   private Service service;
   
   public Object getObject()
     throws Exception
   {
     if (this.service == null)
     {
       this.service = this.ssf.create(this.serviceClass);
       this.service.setInvoker(new BeanInvoker(this.target));
     }
     return this.service;
   }
   
   public Class getObjectType() {
     return Service.class;
   }
   
   public boolean isSingleton() {
     return true;
   }
   
   public Class getServiceClass() {
     return this.serviceClass;
   }
   
   public void setServiceClass(Class serviceClass) {
     this.serviceClass = serviceClass;
   }
   
   public Object getTarget() {
     return this.target;
   }
   
   public void setTarget(Object target) {
     this.target = target;
   }
   
   public Service getService() {
     return this.service;
   }
   
   public void setService(Service service) {
     this.service = service;
   }
   
 
 
 
 
   private final ObjectServiceFactory ssf = new ObjectServiceFactory();
 }


