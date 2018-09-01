 package com.zbensoft.mmsmp.vas.sjb.unibusiness;
 
 import javax.xml.bind.JAXBElement;
 import javax.xml.bind.annotation.XmlElementDecl;
 import javax.xml.bind.annotation.XmlRegistry;
 import javax.xml.namespace.QName;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @XmlRegistry
 public class ObjectFactory
 {
	    private static final QName _ReverseUnsubscribeManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "reverseUnsubscribeManageReturn");
	    private static final QName _Request2_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request2");
	    private static final QName _ProduceManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "produceManageReturn");
	    private static final QName _Request1_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request1");
	    private static final QName _ServiceCapabilityManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "serviceCapabilityManageReturn");
	    private static final QName _Request4_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request4");
	    private static final QName _Request3_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request3");
	    private static final QName _OrderRelationManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "orderRelationManageReturn");
	    private static final QName _Request_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request");
	    private static final QName _ServiceManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "serviceManageReturn");
   
 
 
 
 
 
 
 
 
 
 
   public ServiceManageRequest createServiceManageRequest()
   {
     return new ServiceManageRequest();
   }
   
 
 
 
   public ProductManageRequest createProductManageRequest()
   {
     return new ProductManageRequest();
   }
   
 
 
 
   public Response createResponse()
   {
     return new Response();
   }
   
 
 
 
   public OrderRelationRequest createOrderRelationRequest()
   {
     return new OrderRelationRequest();
   }
   
 
 
 
   public ServiceCapabilityManageRequest createServiceCapabilityManageRequest()
   {
     return new ServiceCapabilityManageRequest();
   }
   
 
 
 
   public CommonRequest createCommonRequest()
   {
     return new CommonRequest();
   }
   
 
 
 
   public ReverseUnsubscribeManageRequest createReverseUnsubscribeManageRequest()
   {
     return new ReverseUnsubscribeManageRequest();
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="reverseUnsubscribeManageReturn")
   public JAXBElement<Response> createReverseUnsubscribeManageReturn(Response value)
   {
     return new JAXBElement(_ReverseUnsubscribeManageReturn_QNAME, Response.class, null, value);
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request2")
   public JAXBElement<ProductManageRequest> createRequest2(ProductManageRequest value)
   {
     return new JAXBElement(_Request2_QNAME, ProductManageRequest.class, null, value);
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="produceManageReturn")
   public JAXBElement<Response> createProduceManageReturn(Response value)
   {
     return new JAXBElement(_ProduceManageReturn_QNAME, Response.class, null, value);
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request1")
   public JAXBElement<ServiceManageRequest> createRequest1(ServiceManageRequest value)
   {
     return new JAXBElement(_Request1_QNAME, ServiceManageRequest.class, null, value);
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="serviceCapabilityManageReturn")
   public JAXBElement<Response> createServiceCapabilityManageReturn(Response value)
   {
     return new JAXBElement(_ServiceCapabilityManageReturn_QNAME, Response.class, null, value);
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request4")
   public JAXBElement<ReverseUnsubscribeManageRequest> createRequest4(ReverseUnsubscribeManageRequest value)
   {
     return new JAXBElement(_Request4_QNAME, ReverseUnsubscribeManageRequest.class, null, value);
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request3")
   public JAXBElement<OrderRelationRequest> createRequest3(OrderRelationRequest value)
   {
     return new JAXBElement(_Request3_QNAME, OrderRelationRequest.class, null, value);
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="orderRelationManageReturn")
   public JAXBElement<Response> createOrderRelationManageReturn(Response value)
   {
     return new JAXBElement(_OrderRelationManageReturn_QNAME, Response.class, null, value);
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request")
   public JAXBElement<ServiceCapabilityManageRequest> createRequest(ServiceCapabilityManageRequest value)
   {
     return new JAXBElement(_Request_QNAME, ServiceCapabilityManageRequest.class, null, value);
   }
   
 
 
 
   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="serviceManageReturn")
   public JAXBElement<Response> createServiceManageReturn(Response value)
   {
     return new JAXBElement(_ServiceManageReturn_QNAME, Response.class, null, value);
   }
 }





