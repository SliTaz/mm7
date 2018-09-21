package com.zbensoft.mmsmp.sp.ra.spagent.sp.ws;

import com.zbensoft.mmsmp.sp.ra.spagent.utils.Utility;
import java.io.PrintStream;
import javax.xml.rpc.ServiceException;
import junit.framework.AssertionFailedError;

public class Test {
//	static String url = "http://220.194.63.139:8080/axis/services/SyncNotifySP_bjlt";
	static String url = "http://127.0.0.1/mmsmpspsimulator/services/SyncNotifySP";

	public static void main(String[] args) throws Exception {
		SyncNotifySPSoapBindingStub binding;
		try {
			binding = (SyncNotifySPSoapBindingStub) new SyncNotifySPServiceServiceLocator().getSyncNotifySP(url);
		} catch (ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
		}
		
		binding.setTimeout(60000);

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

		OrderRelationUpdateNotifyResponse value = null;
		value = binding.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);

		System.out.println(value.getResultCode());
	}
}
