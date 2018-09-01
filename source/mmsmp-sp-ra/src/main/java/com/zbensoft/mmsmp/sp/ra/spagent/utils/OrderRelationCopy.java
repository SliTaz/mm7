package com.zbensoft.mmsmp.sp.ra.spagent.utils;

import org.apache.log4j.Logger;

public class OrderRelationCopy {
	private static final Logger logger = Logger.getLogger(OrderRelationCopy.class);

	public static void copyRequest(com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest order,
			com.zbensoft.mmsmp.sp.ra.spagent.sp.ws.OrderRelationUpdateNotifyRequest orderRequest) {
		orderRequest.setRecordSequenceId(order.getRecordSequenceId());
		orderRequest.setContent(order.getContent());
		orderRequest.setEffectiveDate(order.getEffectiveDate());
		orderRequest.setEncodeStr(order.getEncodeStr());
		orderRequest.setExpireDate(order.getExpireDate());
		orderRequest.setLinkId(order.getLinkId());
		orderRequest.setServiceType(order.getServiceType());
		orderRequest.setProductId(order.getProductId());
		orderRequest.setSpId(order.getSpId());
		orderRequest.setTime_stamp(order.getTime_stamp());
		orderRequest.setUpdateDesc(order.getUpdateDesc());
		orderRequest.setUpdateTime(order.getUpdateTime());
		orderRequest.setUpdateType(order.getUpdateType());
		orderRequest.setUserId(order.getUserId());
		orderRequest.setUserIdType(order.getUserIdType());
	}

	public static void copyResponse(com.zbensoft.mmsmp.sp.ra.spagent.sp.ws.OrderRelationUpdateNotifyResponse response,
			com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyResponse res) {
		try {
			res.setRecordSequenceId(response.getRecordSequenceId());
			res.setResultCode(response.getResultCode());
		} catch (RuntimeException e) {
			logger.info("respone is " + response);
		}
	}
}
