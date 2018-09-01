package com.zbensoft.mmsmp.common.ra.ckp.server;

import com.zbensoft.mmsmp.common.ra.vacNew.util.Utility;
import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import org.apache.log4j.Logger;

public class OrderRelationClient {
	private static final Logger logger = Logger.getLogger(OrderRelationClient.class);

	public static int notifySp(String url, String sequenceId, String content, String effectiveDate, String encodeStr, String expiredDate, String linkId, String serviceType, String productId, String spId,
			String timeStamp, String updateTime, int updateType, String userId, int userIdType) {
		OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
		orderRelationUpdateNotifyRequest.setRecordSequenceId(sequenceId);
		orderRelationUpdateNotifyRequest.setContent(content);
		orderRelationUpdateNotifyRequest.setEffectiveDate(effectiveDate);
		orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt(encodeStr));
		orderRelationUpdateNotifyRequest.setExpireDate(expiredDate);
		orderRelationUpdateNotifyRequest.setLinkId(linkId);
		orderRelationUpdateNotifyRequest.setServiceType(serviceType);
		orderRelationUpdateNotifyRequest.setProductId(productId);
		orderRelationUpdateNotifyRequest.setSpId(spId);
		orderRelationUpdateNotifyRequest.setTime_stamp(timeStamp);
		orderRelationUpdateNotifyRequest.setUpdateDesc("");
		orderRelationUpdateNotifyRequest.setUpdateTime(updateTime);
		orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(updateType));
		orderRelationUpdateNotifyRequest.setUserId(userId);
		orderRelationUpdateNotifyRequest.setUserIdType(Integer.valueOf(userIdType));
		OrderRelationUpdateNotifyResponse res = null;
		SyncNotifySPSoapBindingStub binding = null;
		try {
			binding = (SyncNotifySPSoapBindingStub) new SyncNotifySPServiceServiceLocator().getSyncNotifySP(url);
			binding.setTimeout(5000);
			try {
				res = binding.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
			} catch (RemoteException e) {
				logger.error("binding error ..  ", e);
			}
		} catch (ServiceException e) {
			logger.error("service error :  ", e);
		}
		logger.info("=====>notify sp successfully :  " + res.getResultCode());
		return res.getResultCode();
	}

	/** @deprecated */
	public static OrderRelationUpdateNotifyResponse notifySp(String url, OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest) {
		OrderRelationUpdateNotifyResponse res = null;
		SyncNotifySPSoapBindingStub binding = null;
		try {
			binding = (SyncNotifySPSoapBindingStub) new SyncNotifySPServiceServiceLocator().getSyncNotifySP(url);
			binding.setTimeout(5000);
			try {
				res = binding.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
			} catch (RemoteException e) {
				logger.error("binding error ..  ", e);
			}
		} catch (ServiceException e) {
			logger.error("service error :  ", e);
		}
		logger.info("=====>notify sp successfully :  " + res.getResultCode());
		return res;
	}

	public static void main(String[] args) {
		String url = "http://220.194.63.139:8080/axis/services/SyncNotifySP_bjlt";

		OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
		orderRelationUpdateNotifyRequest.setRecordSequenceId("123456789123456789");
		orderRelationUpdateNotifyRequest.setContent("content");
		orderRelationUpdateNotifyRequest.setEffectiveDate("2010101010");
		orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("2011212121212"));
		orderRelationUpdateNotifyRequest.setExpireDate("3010101010");
		orderRelationUpdateNotifyRequest.setLinkId("12121212120123456789");
		orderRelationUpdateNotifyRequest.setServiceType("3");
		orderRelationUpdateNotifyRequest.setProductId("8802");
		orderRelationUpdateNotifyRequest.setSpId("3");
		orderRelationUpdateNotifyRequest.setTime_stamp("1212121212");
		orderRelationUpdateNotifyRequest.setUpdateDesc("");
		orderRelationUpdateNotifyRequest.setUpdateTime("1212121212");
		orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(3));
		orderRelationUpdateNotifyRequest.setUserId("15811313734");
		orderRelationUpdateNotifyRequest.setUserIdType(Integer.valueOf(1));

		OrderRelationUpdateNotifyResponse res = notifySp(url, orderRelationUpdateNotifyRequest);
		if (res != null) {
			logger.info("result : " + res.getResultCode());
		}

		logger.info("notify finished ....");
	}
}
