package com.zbensoft.mmsmp.sp.ra.spagent.test.sp;

import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyResponse;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

public class WsServer {
	private static final Logger logger = Logger.getLogger(WsServer.class);

	public OrderRelationUpdateNotifyResponse orderRelationUpdateNotify(
			OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest) throws RemoteException {
		logger.info("in orderRelationUpdateNotify method...");
		logger.fatal("\r\n流水号: " + orderRelationUpdateNotifyRequest.getRecordSequenceId() + "\r\n用户ID类型："
				+ orderRelationUpdateNotifyRequest.getUserIdType() + "\r\n用户手机号码或伪码: "
				+ orderRelationUpdateNotifyRequest.getUserId() + "\r\n业务类型: "
				+ orderRelationUpdateNotifyRequest.getServiceType() + "\r\nSP标识: "
				+ orderRelationUpdateNotifyRequest.getSpId() + "\r\n产品标识: "
				+ orderRelationUpdateNotifyRequest.getProductId() + "\r\n更新操作的类型: "
				+ orderRelationUpdateNotifyRequest.getUpdateType() + "\r\n更新时间: "
				+ orderRelationUpdateNotifyRequest.getUpdateTime() + "\r\n更新操作的详细描述，默认为空: "
				+ orderRelationUpdateNotifyRequest.getUpdateDesc() + "\r\nLinkId: "
				+ orderRelationUpdateNotifyRequest.getLinkId() + "\r\n内容: "
				+ orderRelationUpdateNotifyRequest.getContent() + "\r\n订购关系生效时间: "
				+ orderRelationUpdateNotifyRequest.getEffectiveDate() + "\r\n订购关系失效时间： "
				+ orderRelationUpdateNotifyRequest.getExpireDate() + "\r\n时间戳由VAC生成: "
				+ orderRelationUpdateNotifyRequest.getTime_stamp() + "\r\nEncodeStr: "
				+ orderRelationUpdateNotifyRequest.getEncodeStr());

		OrderRelationUpdateNotifyResponse response = new OrderRelationUpdateNotifyResponse();
		response.setRecordSequenceId(orderRelationUpdateNotifyRequest.getRecordSequenceId());
		response.setResultCode(0);
		return response;
	}
}
