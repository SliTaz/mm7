package com.zbensoft.mmsmp.common.ra.ckp.server;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.CommonConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.OrderRelationRequest;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.Response;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.UniBusiness;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.UniBusinessServiceClient;
import com.zbensoft.mmsmp.common.ra.vacNew.util.Utility;
import java.io.PrintStream;
import java.rmi.RemoteException;
//import javax.xml.rpc.ServiceException;
import junit.framework.AssertionFailedError;
import org.apache.log4j.Logger;

public class SyncNotifySPSoapBindingImpl implements SyncNotifySPService {
	private static final Logger logger = Logger.getLogger(SyncNotifySPSoapBindingImpl.class);
	private SyncNotifySPDao syncNotifySPDao;

	public OrderRelationUpdateNotifyResponse orderRelationUpdateNotify(OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest) throws RemoteException {
		System.out.println("in orderRelationUpdateNotify method...");
		logger.info("流水号: " + orderRelationUpdateNotifyRequest.getRecordSequenceId());
		logger.info("用户ID类型：" + orderRelationUpdateNotifyRequest.getUserIdType());
		logger.info("用户手机号码或伪码: " + orderRelationUpdateNotifyRequest.getUserId());
		logger.info("业务类型: " + orderRelationUpdateNotifyRequest.getServiceType());
		logger.info("SP标识: " + orderRelationUpdateNotifyRequest.getSpId());
		logger.info("产品标识: " + orderRelationUpdateNotifyRequest.getProductId());
		logger.info("更新操作的类型: " + orderRelationUpdateNotifyRequest.getUpdateType());
		logger.info("更新时间: " + orderRelationUpdateNotifyRequest.getUpdateTime());
		logger.info("更新操作的详细描述，默认为空: " + orderRelationUpdateNotifyRequest.getUpdateDesc());
		logger.info("LinkId: " + orderRelationUpdateNotifyRequest.getLinkId());
		logger.info("内容: " + orderRelationUpdateNotifyRequest.getContent());
		logger.info("订购关系生效时间: " + orderRelationUpdateNotifyRequest.getEffectiveDate());
		logger.info("订购关系失效时间： " + orderRelationUpdateNotifyRequest.getExpireDate());
		logger.info("时间戳由VAC生成: " + orderRelationUpdateNotifyRequest.getTime_stamp());
		logger.info("EncodeStr: " + orderRelationUpdateNotifyRequest.getEncodeStr());

		OrderRelationRequest orderRelationRequest = new OrderRelationRequest();

		orderRelationRequest.setProductID(orderRelationUpdateNotifyRequest.getProductId());
		if ((orderRelationUpdateNotifyRequest.getProductId() != null) && (orderRelationUpdateNotifyRequest.getProductId().trim().length() > 0))
			orderRelationRequest.setOrderType(1);
		else {
			orderRelationRequest.setOrderType(2);
		}
		int status = orderRelationUpdateNotifyRequest.getUpdateType().intValue();
		if (status == 1)
			status--;
		else if (status == 2) {
			status++;
		}

		orderRelationRequest.setStatus(status);
		orderRelationRequest.setUserPhone(orderRelationUpdateNotifyRequest.getUserId());
		orderRelationRequest.setUserType(0);
		orderRelationRequest.setStreamingNum(orderRelationUpdateNotifyRequest.getRecordSequenceId());
		orderRelationRequest.setChannel("1");
		OrderRelationUpdateNotifyResponse response = new OrderRelationUpdateNotifyResponse();
		try {
			UniBusinessServiceClient client = new UniBusinessServiceClient();
			logger.info(ConfigUtil.getInstance().getCommonConfig().getUniBusinessUrl());
			UniBusiness uniBusiness = client.getUniBusinessService(ConfigUtil.getInstance().getCommonConfig().getUniBusinessUrl());
			Response myResponse = uniBusiness.orderRelationManage(orderRelationRequest);

			response.setRecordSequenceId(orderRelationUpdateNotifyRequest.getRecordSequenceId());
			response.setResultCode(Integer.parseInt(myResponse.getReturnCode()));
			logger.info("myResponse.getReturnCode() is : " + Integer.parseInt(myResponse.getReturnCode()));

			dispatchMessage(orderRelationUpdateNotifyRequest);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return response;
	}

	private void dispatchMessage(OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest) throws RemoteException {
		String spid = orderRelationUpdateNotifyRequest.getSpId();
		String url = this.syncNotifySPDao.getSpOrderUrl(spid);
		if ((url != null) && (!"".equals(url))) {
			SyncNotifySPSoapBindingStub binding;
			try {
				binding = (SyncNotifySPSoapBindingStub) new SyncNotifySPServiceServiceLocator().getSyncNotifySP(url);
			} catch (Exception jre) {
//				SyncNotifySPSoapBindingStub binding;
//				if (jre.getLinkedCause() != null)
//					jre.getLinkedCause().printStackTrace();
				throw new AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
			}
//			SyncNotifySPSoapBindingStub binding;
			OrderRelationUpdateNotifyResponse value = binding.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
			logger.info("通知sp返回resultCode为：" + value.getResultCode());
		} else {
			logger.info("sp 订购 url为空！");
		}
	}

	public void setSyncNotifySPDao(SyncNotifySPDao syncNotifySPDao) {
		this.syncNotifySPDao = syncNotifySPDao;
	}

	public static void main(String[] args) {
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

		SyncNotifySPSoapBindingImpl bindingImpl = new SyncNotifySPSoapBindingImpl();
		try {
			bindingImpl.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
