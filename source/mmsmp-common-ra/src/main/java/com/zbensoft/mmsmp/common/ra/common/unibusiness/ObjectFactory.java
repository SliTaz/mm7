/*     */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  27 */   private static final QName _ReverseUnsubscribeManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "reverseUnsubscribeManageReturn");
/*  28 */   private static final QName _Request2_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request2");
/*  29 */   private static final QName _ProduceManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "produceManageReturn");
/*  30 */   private static final QName _Request1_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request1");
/*  31 */   private static final QName _ServiceCapabilityManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "serviceCapabilityManageReturn");
/*  32 */   private static final QName _Request4_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request4");
/*  33 */   private static final QName _Request3_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request3");
/*  34 */   private static final QName _OrderRelationManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "orderRelationManageReturn");
/*  35 */   private static final QName _Request_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "request");
/*  36 */   private static final QName _ServiceManageReturn_QNAME = new QName("http://unibusiness.sjb.vas.aceway.com", "serviceManageReturn");
/*     */ 
/*     */   public ServiceManageRequest createServiceManageRequest()
/*     */   {
/*  50 */     return new ServiceManageRequest();
/*     */   }
/*     */ 
/*     */   public ProductManageRequest createProductManageRequest()
/*     */   {
/*  58 */     return new ProductManageRequest();
/*     */   }
/*     */ 
/*     */   public Response createResponse()
/*     */   {
/*  66 */     return new Response();
/*     */   }
/*     */ 
/*     */   public OrderRelationRequest createOrderRelationRequest()
/*     */   {
/*  74 */     return new OrderRelationRequest();
/*     */   }
/*     */ 
/*     */   public ServiceCapabilityManageRequest createServiceCapabilityManageRequest()
/*     */   {
/*  82 */     return new ServiceCapabilityManageRequest();
/*     */   }
/*     */ 
/*     */   public CommonRequest createCommonRequest()
/*     */   {
/*  90 */     return new CommonRequest();
/*     */   }
/*     */ 
/*     */   public ReverseUnsubscribeManageRequest createReverseUnsubscribeManageRequest()
/*     */   {
/*  98 */     return new ReverseUnsubscribeManageRequest();
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="reverseUnsubscribeManageReturn")
/*     */   public JAXBElement<Response> createReverseUnsubscribeManageReturn(Response value)
/*     */   {
/* 107 */     return new JAXBElement(_ReverseUnsubscribeManageReturn_QNAME, Response.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request2")
/*     */   public JAXBElement<ProductManageRequest> createRequest2(ProductManageRequest value)
/*     */   {
/* 116 */     return new JAXBElement(_Request2_QNAME, ProductManageRequest.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="produceManageReturn")
/*     */   public JAXBElement<Response> createProduceManageReturn(Response value)
/*     */   {
/* 125 */     return new JAXBElement(_ProduceManageReturn_QNAME, Response.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request1")
/*     */   public JAXBElement<ServiceManageRequest> createRequest1(ServiceManageRequest value)
/*     */   {
/* 134 */     return new JAXBElement(_Request1_QNAME, ServiceManageRequest.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="serviceCapabilityManageReturn")
/*     */   public JAXBElement<Response> createServiceCapabilityManageReturn(Response value)
/*     */   {
/* 143 */     return new JAXBElement(_ServiceCapabilityManageReturn_QNAME, Response.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request4")
/*     */   public JAXBElement<ReverseUnsubscribeManageRequest> createRequest4(ReverseUnsubscribeManageRequest value)
/*     */   {
/* 152 */     return new JAXBElement(_Request4_QNAME, ReverseUnsubscribeManageRequest.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request3")
/*     */   public JAXBElement<OrderRelationRequest> createRequest3(OrderRelationRequest value)
/*     */   {
/* 161 */     return new JAXBElement(_Request3_QNAME, OrderRelationRequest.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="orderRelationManageReturn")
/*     */   public JAXBElement<Response> createOrderRelationManageReturn(Response value)
/*     */   {
/* 170 */     return new JAXBElement(_OrderRelationManageReturn_QNAME, Response.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="request")
/*     */   public JAXBElement<ServiceCapabilityManageRequest> createRequest(ServiceCapabilityManageRequest value)
/*     */   {
/* 179 */     return new JAXBElement(_Request_QNAME, ServiceCapabilityManageRequest.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://unibusiness.sjb.vas.aceway.com", name="serviceManageReturn")
/*     */   public JAXBElement<Response> createServiceManageReturn(Response value)
/*     */   {
/* 188 */     return new JAXBElement(_ServiceManageReturn_QNAME, Response.class, null, value);
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.unibusiness.ObjectFactory
 * JD-Core Version:    0.6.0
 */