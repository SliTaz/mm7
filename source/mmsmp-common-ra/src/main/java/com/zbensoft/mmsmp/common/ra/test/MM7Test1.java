package com.zbensoft.mmsmp.common.ra.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import com.cmcc.mm7.vasp.common.MMConstants;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7DeliveryReportReq;
import com.cmcc.mm7.vasp.message.MM7RSRes;
import com.cmcc.mm7.vasp.message.MM7SubmitReq;
import com.cmcc.mm7.vasp.message.MM7SubmitRes;
import com.cmcc.mm7.vasp.service.MM7Sender;

public class MM7Test1 extends Thread{

	public void run(){
		while (true){
			String mm7ConfigName = "mm7Config.xml";
		    String mm7ConnConfigName = "ConnConfig.xml";
		    
		    MM7Config mm7Config = new MM7Config(mm7ConfigName);
		    mm7Config.setConnConfigName(mm7ConnConfigName);
		    
		    MM7Sender mm7Sender = null;
		    
			try {
				mm7Sender = new MM7Sender(mm7Config);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("sender:=  " + mm7Sender);
			
//			MM7DeliveryReportReq request=new MM7DeliveryReportReq();
//			request.setMessageID("1111");
//			request.setSender("2222");
//			request.setRecipient("33333");
//			mm7Sender.send(request);
			
			MM7SubmitReq submit = new MM7SubmitReq();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			
			submit.setTransactionID("1000000");
			submit.addTo("13980000035");
			submit.setVASID("1729");
			submit.setVASPID("810003");
			submit.setServiceCode("101");
			submit.setSenderAddress("1729101");
			submit.setSubject("MMS测试");
			submit.setChargedPartyID("13980000035");

			submit.setChargedParty((byte) 4);
			submit.setChargedPartyID("13980000035");
			submit.setDeliveryReport(true);
			
			
			MMContent content = new MMContent();
		    content.setContentType(MMConstants.ContentType.MULTIPART_RELATED);
		    content.setContentID("mm7Test");
		    
		    String ss = "这是一个测试MMS";
		    byte[] bb = ss.getBytes();
			InputStream input = new ByteArrayInputStream(bb);
			MMContent sub2 = MMContent.createFromStream(input);
			sub2.setContentID("2.txt");
			sub2.setContentType(MMConstants.ContentType.TEXT);
			
		    content.addSubContent(sub2);
		    
		    submit.setContent(content);
		    
		    MM7RSRes rsRes = null;
		    rsRes = mm7Sender.send(submit);
			
			if ((rsRes instanceof MM7SubmitRes)) {
				MM7SubmitRes submitRes = (MM7SubmitRes) rsRes;
				System.out.println(rsRes.getTransactionID());
				System.out.println("after!!submitRes.statuscode=" + rsRes.getStatusCode() + ";submitRes.statusText="
						+ rsRes.getStatusText());
				

			} else {
				System.out.println("不正确消息！rsRes.statuscode=" + rsRes.getStatusCode() + ";rsRes.statusText="
						+ rsRes.getStatusText());
			}
			
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			break;
		}
	}
	
	
	public static void main(String[] args) {
		new MM7Test1().run();
	}
}
