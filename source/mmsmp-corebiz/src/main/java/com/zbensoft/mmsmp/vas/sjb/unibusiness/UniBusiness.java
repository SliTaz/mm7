package com.zbensoft.mmsmp.vas.sjb.unibusiness;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;


@WebService(name="UniBusiness", targetNamespace="http://unibusiness.sjb.vas.aceway.com")
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle=SOAPBinding.ParameterStyle.BARE)
public abstract interface UniBusiness
{
  @WebMethod(operationName="serviceCapabilityManage", action="")
  @WebResult(name="serviceCapabilityManageReturn", targetNamespace="http://unibusiness.sjb.vas.aceway.com")
  public abstract Response serviceCapabilityManage(@WebParam(name="request", targetNamespace="http://unibusiness.sjb.vas.aceway.com") ServiceCapabilityManageRequest paramServiceCapabilityManageRequest);
  
  @WebMethod(operationName="orderRelationManage", action="")
  @WebResult(name="orderRelationManageReturn", targetNamespace="http://unibusiness.sjb.vas.aceway.com")
  public abstract Response orderRelationManage(@WebParam(name="request3", targetNamespace="http://unibusiness.sjb.vas.aceway.com") OrderRelationRequest paramOrderRelationRequest);
  
  @WebMethod(operationName="produceManage", action="")
  @WebResult(name="produceManageReturn", targetNamespace="http://unibusiness.sjb.vas.aceway.com")
  public abstract Response produceManage(@WebParam(name="request2", targetNamespace="http://unibusiness.sjb.vas.aceway.com") ProductManageRequest paramProductManageRequest);
  
  @WebMethod(operationName="serviceManage", action="")
  @WebResult(name="serviceManageReturn", targetNamespace="http://unibusiness.sjb.vas.aceway.com")
  public abstract Response serviceManage(@WebParam(name="request1", targetNamespace="http://unibusiness.sjb.vas.aceway.com") ServiceManageRequest paramServiceManageRequest);
  
  @WebMethod(operationName="reverseUnsubscribeManage", action="")
  @WebResult(name="reverseUnsubscribeManageReturn", targetNamespace="http://unibusiness.sjb.vas.aceway.com")
  public abstract Response reverseUnsubscribeManage(@WebParam(name="request4", targetNamespace="http://unibusiness.sjb.vas.aceway.com") ReverseUnsubscribeManageRequest paramReverseUnsubscribeManageRequest);
}





