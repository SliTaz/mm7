package com.zbensoft.mmsmp.ownbiz.ra.base;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.OrderRelationRequest;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.Response;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.UniBusiness;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.UniBusinessServiceClient;


public class BusinessTestCase {
	public static void main(String[] args) {
		OrderRelationRequest request = new OrderRelationRequest();
		request.setProductID("3100201701");
		request.setServiceId("31002017");
		request.setServiceName("彩信博客（测试）");
		request.setSpCode("10000");
		request.setUserPhone("13521725996");
		request.setFee(10000);
		request.setPeroid(2);
		request.setChannel("3");
		request.setAaaURL("");
		request.setStatus(7);

		UniBusinessServiceClient client = new UniBusinessServiceClient();
		UniBusiness businessClient = client.getUniBusinessService("http://172.16.2.30:8002/UniBusiness");

		Response response = businessClient.orderRelationManage(request);
		String result = response.getReturnCode();
		String desc = response.getDesc();
		System.out.println("result==" + result + "  desc==" + desc);
	}
}
