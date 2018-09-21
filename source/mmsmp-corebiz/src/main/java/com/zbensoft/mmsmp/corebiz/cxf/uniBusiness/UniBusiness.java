package com.zbensoft.mmsmp.corebiz.cxf.uniBusiness;



import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.OrderRelationRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.ProductManageRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.Response;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.ReverseUnsubscribeManageRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.SendVerifyCodeRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.ServiceCapabilityManageRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.ServiceManageRequest;



@WebService
public abstract interface UniBusiness {
	@WebMethod
	@WebResult(name = "serviceCapabilityManageReturn", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com")
	public abstract Response serviceCapabilityManage(@WebParam(name = "request", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com") ServiceCapabilityManageRequest paramServiceCapabilityManageRequest);

	@WebMethod
	@WebResult(name = "orderRelationManageReturn", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com")
	public abstract Response orderRelationManage(@WebParam(name = "request3", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com") OrderRelationRequest paramOrderRelationRequest);

	@WebMethod
	@WebResult(name = "produceManageReturn", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com")
	public abstract Response produceManage(@WebParam(name = "request2", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com") ProductManageRequest paramProductManageRequest);

	@WebMethod
	@WebResult(name = "serviceManageReturn", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com")
	public abstract Response serviceManage(@WebParam(name = "request1", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com") ServiceManageRequest paramServiceManageRequest);

	@WebMethod
	@WebResult(name = "reverseUnsubscribeManageReturn", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com")
	public abstract Response reverseUnsubscribeManage(@WebParam(name = "request4", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com") ReverseUnsubscribeManageRequest paramReverseUnsubscribeManageRequest);

	@WebMethod
	@WebResult(name = "sendVerifyCode", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com")
	public abstract Response sendVerifyCode(@WebParam(name = "request5", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com") SendVerifyCodeRequest sendVerifyCodeRequest);

}
