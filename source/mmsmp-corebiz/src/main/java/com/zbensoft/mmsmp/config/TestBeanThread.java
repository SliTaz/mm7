package com.zbensoft.mmsmp.config;

import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.corebiz.cache.DataCache;
import com.zbensoft.mmsmp.corebiz.handle.impl.SmsBusinessHandlerImpl;
import com.zbensoft.mmsmp.corebiz.route.BusinessRouter;
import com.zbensoft.mmsmp.corebiz.route.IMessageRouter;

public class TestBeanThread extends Thread {
	public TestBeanThread(){
	}
	
	@Override
	public void run() {
		try {
			
			while (true) {
				DataCache dataCache = SpringBeanUtil.getBean(DataCache.class);
//				dataCache.init();
				dataCache.refreshSysParmas();
				dataCache.refreshProductInfo();
				Thread.sleep(30000);
//				IMessageRouter messageRouter = SpringBeanUtil.getBean(BusinessRouter.class);
//				SmsBusinessHandlerImpl smsBusinessHandlerImpl = SpringBeanUtil.getBean(SmsBusinessHandlerImpl.class);
//				System.out.println("Send Test info to VAC");
//				MO_SMMessage mosms=new MO_SMMessage();
//				mosms.setGlobalMessageid("SMS_100091239881239991");
//				mosms.setServiceCode("11#22#33#44#55");
//				mosms.setVaspId("test123123123");
//				CheckRequest dianborequest = new CheckRequest("SMS_mosms.getSendAddress()", "optype.getProduct_id()",
//						"optype.getSpid()", null, null, "4", null);
//				dianborequest.setGlobalMessageid(mosms.getGlobalMessageid());
//				smsBusinessHandlerImpl.getDataMap().put(dianborequest.getGlobalMessageid(), mosms);
//				messageRouter.doRouter(dianborequest);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
