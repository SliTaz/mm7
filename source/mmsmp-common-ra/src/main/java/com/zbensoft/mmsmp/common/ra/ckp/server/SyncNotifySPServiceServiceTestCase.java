package com.zbensoft.mmsmp.common.ra.ckp.server;

import com.zbensoft.mmsmp.common.ra.vacNew.util.Utility;
import java.net.URL;
import javax.xml.rpc.Service;
//import javax.xml.rpc.ServiceException;
import javax.xml.rpc.ServiceFactory;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class SyncNotifySPServiceServiceTestCase extends TestCase {
	public SyncNotifySPServiceServiceTestCase(String name) {
		super(name);
	}

	public void testSyncNotifySPWSDL() throws Exception {
		ServiceFactory serviceFactory = ServiceFactory.newInstance();
		URL url = new URL(new SyncNotifySPServiceServiceLocator().getSyncNotifySPAddress() + "?WSDL");
		Service service = serviceFactory.createService(url, new SyncNotifySPServiceServiceLocator().getServiceName());
		assertTrue(service != null);
	}

	public void test1SyncNotifySPOrderRelationUpdateNotify() throws Exception {
		SyncNotifySPSoapBindingStub binding;
		try {
			binding = (SyncNotifySPSoapBindingStub) new SyncNotifySPServiceServiceLocator().getSyncNotifySP();
		} catch (Exception jre) {
//			SyncNotifySPSoapBindingStub binding;
//			if (jre.getLinkedCause() != null)
//				jre.getLinkedCause().printStackTrace();
			throw new AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
		}
		
		assertNotNull("binding is null", binding);

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
	}
}
