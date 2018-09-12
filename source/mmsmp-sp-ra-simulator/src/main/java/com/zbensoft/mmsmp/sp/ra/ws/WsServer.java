package com.zbensoft.mmsmp.sp.ra.ws;

import java.io.IOException;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.sp.ra.utils.Utils;
import com.zbensoft.mmsmp.sp.ra.ws.req.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.sp.ra.ws.rsp.OrderRelationUpdateNotifyResponse;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WsServer {
	private static final Logger logger = Logger.getLogger(WsServer.class);

	public OrderRelationUpdateNotifyResponse orderRelationUpdateNotify(
			OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest) throws RemoteException {
		logger.info("in orderRelationUpdateNotify method...");
		logger.fatal("\r\nRecordSequenceId:" + orderRelationUpdateNotifyRequest.getRecordSequenceId() + "\r\nUserIdType:"
				+ orderRelationUpdateNotifyRequest.getUserIdType() + "\r\nUserId:"
				+ orderRelationUpdateNotifyRequest.getUserId() + "\r\nServiceType:"
				+ orderRelationUpdateNotifyRequest.getServiceType() + "\r\nSpId:"
				+ orderRelationUpdateNotifyRequest.getSpId() + "\r\nProductId:"
				+ orderRelationUpdateNotifyRequest.getProductId() + "\r\nUpdateType:"
				+ orderRelationUpdateNotifyRequest.getUpdateType() + "\r\nUpdateTime:"
				+ orderRelationUpdateNotifyRequest.getUpdateTime() + "\r\nUpdateDesc:"
				+ orderRelationUpdateNotifyRequest.getUpdateDesc() + "\r\nLinkId:"
				+ orderRelationUpdateNotifyRequest.getLinkId() + "\r\nContent:"
				+ orderRelationUpdateNotifyRequest.getContent() + "\r\nEffectiveDate:"
				+ orderRelationUpdateNotifyRequest.getEffectiveDate() + "\r\nExpireDate:"
				+ orderRelationUpdateNotifyRequest.getExpireDate() + "\r\nExpireDate:"
				+ orderRelationUpdateNotifyRequest.getTime_stamp() + "\r\nEncodeStr:"
				+ orderRelationUpdateNotifyRequest.getEncodeStr());
		
		int updateType=orderRelationUpdateNotifyRequest.getUpdateType();
		logger.info("updateType:"+updateType+"");
		if(updateType==Utils.ORDER_INT){
			logger.info("need send http");
			
			//发送http start
			String resultUrl=Utils.SPReceiveMTMMS_url;//"http://localhost:9095/SPReceiveMTMMS";
			String ServiceCode="pro10002";//deliver.SPNumber;
			String LinkedID=orderRelationUpdateNotifyRequest.getLinkId();
			
			String json ="";
			json=json+"<?xml version=\"1.0\" encoding=\"GB2312\"?>";
			json=json+"<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/ \">";
			json=json+"<env:Header>";
			json=json+"<mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0 \" env:mustUnderstand=\"1\">1234567890</mm7:TransactionID>";
			json=json+"</env:Header>";
			json=json+"<env:Body>";
			json=json+"<SubmitReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0 \">";
			json=json+"<MM7Version>6.3.0</MM7Version>";
			json=json+"<SenderIdentification>";
			json=json+"<VASPID>111</VASPID>";
			json=json+"<VASID>4321</VASID>";
			json=json+"<SenderAddress>18910229166</SenderAddress>";
			json=json+"</SenderIdentification>";
			json=json+"<Recipients>";
			json=json+"<To>";
			json=json+"<Number>8613312345678</Number>";
			json=json+"</To>";
			json=json+"</Recipients>";
			json=json+"<ServiceCode>"+ServiceCode+"</ServiceCode>";
			json=json+"<LinkedID>"+LinkedID+"</LinkedID>";
			json=json+"<DeliveryReport>true</DeliveryReport>";
			json=json+"<ReadReply>true</ReadReply>";
			json=json+"<Subject>mmsSubject</Subject>";
			json=json+"<ChargedParty>Recipient</ChargedParty>";
			json=json+"<ChargedPartyID>8613312345678</ChargedPartyID>";
			json=json+"</SubmitReq>";
			json=json+"</env:Body>";
			json=json+"</env:Envelope>";
			
			String result = null;
			OkHttpClient httpClient = new OkHttpClient();
			RequestBody requestBody = RequestBody.create(MediaType.parse("text/xml"), json);
			Request request = new Request.Builder().url(resultUrl).post(requestBody).build();
			try {
				Response response = httpClient.newCall(request).execute();
				result = response.body().string();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("sended http "+resultUrl+" method");
			//发送http end
		}else{
			logger.info("not need send http");
		}
		

		OrderRelationUpdateNotifyResponse response = new OrderRelationUpdateNotifyResponse();
		response.setRecordSequenceId(orderRelationUpdateNotifyRequest.getRecordSequenceId());
		response.setResultCode(0);
		return response;
	}
}
