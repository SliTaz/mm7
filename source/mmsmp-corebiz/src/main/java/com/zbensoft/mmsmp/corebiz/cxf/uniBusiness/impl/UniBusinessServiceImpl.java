package com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
import com.zbensoft.mmsmp.common.ra.common.message.WOCheckResponse;
import com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness.Constants;
import com.zbensoft.mmsmp.config.SpringBeanUtil;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.UniBusiness;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.OrderRelationRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.ProductManageRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.Response;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.ReverseUnsubscribeManageRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.SendVerifyCodeRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.ServiceCapabilityManageRequest;
import com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo.ServiceManageRequest;
import com.zbensoft.mmsmp.corebiz.message.OrderRelationMessage;
import com.zbensoft.mmsmp.corebiz.route.ReceiveRouter;
import com.zbensoft.mmsmp.corebiz.util.StringUtil;
import com.zbensoft.mmsmp.log.COREBIZ_LOG;

@WebService(serviceName = "UniBusinessService", targetNamespace = "http://uniBusiness.cxf.corebiz.mmsmp.zbensoft.com", endpointInterface = "com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.UniBusiness")
public class UniBusinessServiceImpl implements UniBusiness {
	private static Logger logger = Logger.getLogger(UniBusinessServiceImpl.class);
	
	@Resource(name="receiveMessageRouter")
	ReceiveRouter receiveRouter;

	public void setReceiveRouter(ReceiveRouter receiveRouter) {
		this.receiveRouter = receiveRouter;
	}

	public Response orderRelationManage(OrderRelationRequest request) {
		COREBIZ_LOG.INFO("received  orderRelationManage request : " + request.toString());
		Response res = new Response();
		res.setDesc("业务已受理");
		res.setCodeType(Constants.ResponseCodeType_MMSMP);
		res.setReturnCode(Constants.ResponseCode_Sucess);

		if ((request.getUserPhone() == null) || (request.getUserPhone().trim().length() < 11)) {
			res.setCodeType(Constants.ResponseCodeType_MMSMP);
			res.setReturnCode(Constants.ResponseCode_UserPhoneError);
			res.setDesc("用户号码不合法");
			return res;
		}
		request.setUserPhone(StringUtil.getPhone11(request.getUserPhone()));

		String channel = request.getChannel();
		String globalMessageid = "";

		int intChannel = Integer.parseInt(channel);
		OrderRelationMessage OrderRelationMessage = new OrderRelationMessage();
		switch (intChannel) {
		case 1:
			globalMessageid = OrderRelationMessage.generateUUID("UNI-WEB");
			break;

		case 4:
			globalMessageid = OrderRelationMessage.generateUUID("UNI-WEB");
			break;

		case 2:
			globalMessageid = OrderRelationMessage.generateUUID("UNI-WAP");
			break;

		case 3:
		case 6:
			globalMessageid = OrderRelationMessage.generateUUID("UNI-IPHONE");
			break;
		case 5:
		default:
			res.setCodeType(Constants.ResponseCodeType_MMSMP);
			res.setReturnCode(Constants.ResponseCode_ChannelTypeError);
			res.setDesc("未知渠道");
			return res;
		}

		OrderRelationMessage.setOrderRelationRequest(request);
		OrderRelationMessage.setGlobalMessageid(globalMessageid);

		this.receiveRouter.doRouter(OrderRelationMessage);
		COREBIZ_LOG.INFO(
				"OrderRelationMessage doRouter  [channel:" + channel + " globalMessageid:" + globalMessageid + "]");
		return res;
	}

	public Response produceManage(ProductManageRequest request) {
		COREBIZ_LOG.INFO("=======admin管理门户 内容暂停恢复消息通知corbize =========" + request.getProductID());

		String[] str = request.getProductID().split("[|]");
		System.out.println("sp_productID=======" + str[6]);

		if (request.getProductID().startsWith("HF")) {
			COREBIZ_LOG.INFO("恢复Map key=======ZT" + str[6]);
			((Map) SpringBeanUtil.getBean("commonDataMemory")).remove("ZT" + str[6]);
		} else {
			WOCheckResponse wc = new WOCheckResponse();
			wc.setMessageid(request.getProductID());
			COREBIZ_LOG.INFO("暂停Map key=======ZT" + str[6]);
			((Map) SpringBeanUtil.getBean("commonDataMemory")).put("ZT" + str[6], wc);
		}

		Response res = new Response();
		res.setDesc("业务已受理");
		res.setReturnCode("0");
		return res;
	}

	public Response reverseUnsubscribeManage(ReverseUnsubscribeManageRequest arg0) {
		return null;
	}

	public Response serviceCapabilityManage(ServiceCapabilityManageRequest arg0) {
		return null;
	}

	public Response serviceManage(ServiceManageRequest arg0) {
		return null;
	}

	@Override
	public Response sendVerifyCode(SendVerifyCodeRequest request) {
		
		COREBIZ_LOG.INFO("received  sendVerifyCode request : " + request.toString());
		Response res = new Response();
		res.setDesc("业务已受理");
		res.setCodeType(Constants.ResponseCodeType_MMSMP);
		res.setReturnCode(Constants.ResponseCode_Sucess);
		if ((request.getPhone() == null) || (request.getPhone().trim().length() < 11)) {
			res.setCodeType(Constants.ResponseCodeType_MMSMP);
			res.setReturnCode(Constants.ResponseCode_UserPhoneError);
			res.setDesc("用户号码不合法");
			return res;
		}
		request.setPhone(StringUtil.getPhone11(request.getPhone()));
		
		if(StringUtils.isEmpty(request.getVerifyCode())){
			res.setCodeType(Constants.ResponseCodeType_MMSMP);
			res.setReturnCode(Constants.ResponseCode_VerifyCodeError);
			res.setDesc("验证码为空");
			return res;
		}
		
		
		SendNotificationMessage sendNMsg = new SendNotificationMessage();
		sendNMsg.setPhoneNumber(new String[] {request.getPhone()});
		sendNMsg.setSendContent("【请勿转发】您的验证码为："+request.getVerifyCode());
		this.receiveRouter.doRouter(sendNMsg);
		COREBIZ_LOG.INFO("MT_SMMessage doRouter  [phone:" + request.getPhone() + " code:" + request.getVerifyCode() + "]");
		
		return  res;
	}
}
