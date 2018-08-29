/*    */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import javax.xml.namespace.QName;
/*    */ import org.codehaus.xfire.XFire;
/*    */ import org.codehaus.xfire.XFireFactory;
/*    */ import org.codehaus.xfire.XFireRuntimeException;
/*    */ import org.codehaus.xfire.aegis.AegisBindingProvider;
/*    */ import org.codehaus.xfire.annotations.AnnotationServiceFactory;
/*    */ import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
/*    */ import org.codehaus.xfire.client.Client;
/*    */ import org.codehaus.xfire.client.XFireProxyFactory;
/*    */ import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
/*    */ import org.codehaus.xfire.service.Endpoint;
/*    */ import org.codehaus.xfire.service.Service;
/*    */ import org.codehaus.xfire.soap.Soap11Binding;
/*    */ import org.codehaus.xfire.transport.TransportManager;
/*    */ 
/*    */ public class UniBusinessServiceClient
/*    */ {
/* 21 */   private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
/* 22 */   private HashMap endpoints = new HashMap();
/*    */   private Service service0;
/*    */ 
/*    */   public UniBusinessServiceClient()
/*    */   {
/* 26 */     create0();
/* 27 */     Endpoint UniBusinessServiceEP = this.service0.addEndpoint(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessService"), new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessServiceSoapBinding"), "http://localhost:8080/axis/services/UniBusinessService");
/* 28 */     this.endpoints.put(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessService"), UniBusinessServiceEP);
/* 29 */     Endpoint UniBusinessLocalEndpointEP = this.service0.addEndpoint(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalEndpoint"), new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalBinding"), "xfire.local://UniBusinessService");
/* 30 */     this.endpoints.put(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalEndpoint"), UniBusinessLocalEndpointEP);
/*    */   }
/*    */ 
/*    */   public Object getEndpoint(Endpoint endpoint) {
/*    */     try {
/* 35 */       return proxyFactory.create(endpoint.getBinding(), endpoint.getUrl()); } catch (MalformedURLException e) {
/*    */     }
/* 37 */     throw new XFireRuntimeException("Invalid URL", e);
/*    */   }
/*    */ 
/*    */   public Object getEndpoint(QName name)
/*    */   {
/* 42 */     Endpoint endpoint = (Endpoint)this.endpoints.get(name);
/* 43 */     if (endpoint == null) {
/* 44 */       throw new IllegalStateException("No such endpoint!");
/*    */     }
/* 46 */     return getEndpoint(endpoint);
/*    */   }
/*    */ 
/*    */   public Collection getEndpoints() {
/* 50 */     return this.endpoints.values();
/*    */   }
/*    */ 
/*    */   private void create0() {
/* 54 */     TransportManager tm = XFireFactory.newInstance().getXFire().getTransportManager();
/* 55 */     HashMap props = new HashMap();
/* 56 */     props.put("annotations.allow.interface", Boolean.valueOf(true));
/* 57 */     AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
/* 58 */     asf.setBindingCreationEnabled(false);
/* 59 */     this.service0 = asf.create(UniBusiness.class, props);
/*    */ 
/* 61 */     Soap11Binding localSoap11Binding = asf.createSoap11Binding(this.service0, new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalBinding"), "urn:xfire:transport:local");
/*    */ 
/* 64 */     localSoap11Binding = asf.createSoap11Binding(this.service0, new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessServiceSoapBinding"), "http://schemas.xmlsoap.org/soap/http");
/*    */   }
/*    */ 
/*    */   public UniBusiness getUniBusinessService()
/*    */   {
/* 69 */     return (UniBusiness)getEndpoint(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessService"));
/*    */   }
/*    */ 
/*    */   public UniBusiness getUniBusinessService(String url) {
/* 73 */     UniBusiness var = getUniBusinessService();
/* 74 */     Client.getInstance(var).setUrl(url);
/* 75 */     return var;
/*    */   }
/*    */ 
/*    */   public UniBusiness getUniBusinessLocalEndpoint() {
/* 79 */     return (UniBusiness)getEndpoint(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalEndpoint"));
/*    */   }
/*    */ 
/*    */   public UniBusiness getUniBusinessLocalEndpoint(String url) {
/* 83 */     UniBusiness var = getUniBusinessLocalEndpoint();
/* 84 */     Client.getInstance(var).setUrl(url);
/* 85 */     return var;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.unibusiness.UniBusinessServiceClient
 * JD-Core Version:    0.6.0
 */