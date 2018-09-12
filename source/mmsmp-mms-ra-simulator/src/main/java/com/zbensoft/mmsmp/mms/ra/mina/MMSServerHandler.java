package com.zbensoft.mmsmp.mms.ra.mina;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import org.apache.commons.httpclient.HttpHost;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;

public class MMSServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(MMSServerHandler.class);

	public MMSServerHandler() {
		
	}

	public void messageReceived(IoSession session, Object message) {
		System.out.println("into MMSServerHandler messageReceived;message:"+message);
		
//		String resultUrl="http://localhost:9095/SPReceiveMTMMS";
//		
//		
//		String json ="";
//		String result = null;
//		OkHttpClient httpClient = new OkHttpClient();
//		RequestBody requestBody = RequestBody.create(MediaType.parse("text/html"), json);
//		Request request = new Request.Builder().url(resultUrl).post(requestBody).build();
//		try {
//			Response response = httpClient.newCall(request).execute();
//			result = response.body().string();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	public static void main(String[] args) {
		new MMSServerHandler().messageReceived(null,null);
	}

}
