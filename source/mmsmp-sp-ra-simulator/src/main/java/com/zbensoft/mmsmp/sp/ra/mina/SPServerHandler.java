package com.zbensoft.mmsmp.sp.ra.mina;

import com.zbensoft.mmsmp.sp.ra.utils.Deliver;
import com.zbensoft.mmsmp.sp.ra.utils.Utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;

public class SPServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(SPServerHandler.class);

	public SPServerHandler() {
		
	}

	public void messageReceived(IoSession session, Object message) {
		System.out.println("into SPServerHandler messageReceived;message:"+message);
		IoBuffer buf = (IoBuffer) message;
		ByteBuffer bb = buf.buf();

		Deliver deliver = new Deliver();
		deliver.unserialize(bb.duplicate());
		
		System.out.println("deliver.MessageContent:"+deliver.MessageContent+";deliver.SPNumber:"+deliver.SPNumber+";deliver.Reserve:"+deliver.Reserve+";deliver.UserNumber:"+deliver.UserNumber);
		
		String MessageContent=deliver.MessageContent;
		
		String resultUrl=Utils.SPReceiveMTMMS_url;//"http://localhost:9095/SPReceiveMTMMS";
		String ServiceCode="pro10001";//deliver.SPNumber;
		if(MessageContent.equals("dianbo001")){
			ServiceCode="pro10001";//点播
		}
		if(MessageContent.equals("order001")){
			ServiceCode="pro10002";//订购
		}
		
		//根据接入号和指令内容得到spProductId
		String vasId=deliver.SPNumber;
		String command=MessageContent;
		ServiceCode=HttpRequestHelper.getSpProductIdsForSPsimulator(vasId, command);
		System.out.println("ServiceCode:"+ServiceCode);
		
		String LinkedID=deliver.Reserve;
		String UserNumber=deliver.UserNumber;
		
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
		json=json+"<Number>"+UserNumber+"</Number>";
		json=json+"</To>";
		json=json+"</Recipients>";
		json=json+"<ServiceCode>"+ServiceCode+"</ServiceCode>";
		json=json+"<LinkedID>"+LinkedID+"</LinkedID>";
		json=json+"<DeliveryReport>true</DeliveryReport>";
		json=json+"<ReadReply>true</ReadReply>";
		json=json+"<Subject>mmsSubject</Subject>";
		json=json+"<ChargedParty>Recipient</ChargedParty>";
		json=json+"<ChargedPartyID>"+UserNumber+"</ChargedPartyID>";
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
		
	}
	
	public static void main(String[] args) {
		new SPServerHandler().messageReceived(null,null);
	}

}
