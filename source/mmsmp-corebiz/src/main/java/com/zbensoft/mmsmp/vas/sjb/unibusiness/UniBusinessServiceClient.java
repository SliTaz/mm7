 package com.zbensoft.mmsmp.vas.sjb.unibusiness;
 
 import java.net.MalformedURLException;
 import java.util.Collection;
 import java.util.HashMap;
 import javax.xml.namespace.QName;
 import org.codehaus.xfire.XFire;
 import org.codehaus.xfire.XFireFactory;
 import org.codehaus.xfire.XFireRuntimeException;
 import org.codehaus.xfire.annotations.AnnotationServiceFactory;
 import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
 import org.codehaus.xfire.client.Client;
 import org.codehaus.xfire.client.XFireProxyFactory;
 import org.codehaus.xfire.service.Endpoint;
 import org.codehaus.xfire.service.Service;
 import org.codehaus.xfire.soap.Soap11Binding;
 import org.codehaus.xfire.transport.TransportManager;
 
 public class UniBusinessServiceClient
 {
	    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
	    private HashMap endpoints = new HashMap();
	    private Service service0;
	    
	    public UniBusinessServiceClient() {
	      create0();
	      Endpoint UniBusinessServiceEP = this.service0.addEndpoint(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessService"), new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessServiceSoapBinding"), "http://localhost:8080/axis/services/UniBusinessService");
	      this.endpoints.put(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessService"), UniBusinessServiceEP);
	      Endpoint UniBusinessLocalEndpointEP = this.service0.addEndpoint(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalEndpoint"), new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalBinding"), "xfire.local://UniBusinessService");
	      this.endpoints.put(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalEndpoint"), UniBusinessLocalEndpointEP);
	    }
	    
	    public Object getEndpoint(Endpoint endpoint) {
	      try {
	        return proxyFactory.create(endpoint.getBinding(), endpoint.getUrl());
	      } catch (MalformedURLException e) {
	        throw new XFireRuntimeException("Invalid URL", e);
	      }
	    }
	    
	    public Object getEndpoint(QName name) {
	      Endpoint endpoint = (Endpoint)this.endpoints.get(name);
	      if (endpoint == null) {
	        throw new IllegalStateException("No such endpoint!");
	      }
	      return getEndpoint(endpoint);
	    }
	    
	    public Collection getEndpoints() {
	      return this.endpoints.values();
	    }
	    
	    private void create0() {
	      TransportManager tm = XFireFactory.newInstance().getXFire().getTransportManager();
	      HashMap props = new HashMap();
	      props.put("annotations.allow.interface", Boolean.valueOf(true));
	      AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new org.codehaus.xfire.aegis.AegisBindingProvider(new org.codehaus.xfire.jaxb2.JaxbTypeRegistry()));
	      asf.setBindingCreationEnabled(false);
	      this.service0 = asf.create(UniBusiness.class, props);
	      
	      Soap11Binding localSoap11Binding = asf.createSoap11Binding(this.service0, new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalBinding"), "urn:xfire:transport:local");
	      
	  
	      localSoap11Binding = asf.createSoap11Binding(this.service0, new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessServiceSoapBinding"), "http://schemas.xmlsoap.org/soap/http");
	    }
	    
	    public UniBusiness getUniBusinessService()
	    {
	      return (UniBusiness)getEndpoint(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessService"));
	    }
	    
	    public UniBusiness getUniBusinessService(String url) {
	      UniBusiness var = getUniBusinessService();
	      Client.getInstance(var).setUrl(url);
	      return var;
	    }
	    
	    public UniBusiness getUniBusinessLocalEndpoint() {
	      return (UniBusiness)getEndpoint(new QName("http://unibusiness.sjb.vas.aceway.com", "UniBusinessLocalEndpoint"));
	    }
	    
	    public UniBusiness getUniBusinessLocalEndpoint(String url) {
	      UniBusiness var = getUniBusinessLocalEndpoint();
	      Client.getInstance(var).setUrl(url);
	      return var;
	    }
	  }





