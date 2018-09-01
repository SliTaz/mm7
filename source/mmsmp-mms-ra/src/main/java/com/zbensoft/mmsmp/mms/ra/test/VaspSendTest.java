package com.zbensoft.mmsmp.mms.ra.test;

import com.zbensoft.mmsmp.common.ra.send.util.AceMessageUtil;
import com.zbensoft.mmsmp.common.ra.vas.sjb.receive.DemandTask;
import com.cmcc.mm7.vasp.common.MMConstants;
import com.cmcc.mm7.vasp.common.MMConstants.ContentType;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7RSRes;
import com.cmcc.mm7.vasp.message.MM7SubmitReq;
import com.cmcc.mm7.vasp.message.MM7SubmitRes;
import com.cmcc.mm7.vasp.service.MM7Sender;
import java.io.PrintStream;
import org.apache.log4j.Logger;

public class VaspSendTest {
	private static final Logger log = Logger.getLogger(DemandTask.class);

	private final String mms = "this is a multi-part message in MIME format\r\n\r\n----NextPart_0_2817_24856\r\nContent-Type:text/xml;charset=\"GB2312\"\r\nContent-Transfer-Encoding:8bit\r\nContent-ID:</tnn-200102/mm7-vasp>\r\n<?xml version=\"1.0\" encoding=\"GB2312\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">1</mm7:TransactionID></env:Header><env:Body><SubmitReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>11521</VASPID><VASID>106558888</VASID><SenderAddress>106558888</SenderAddress></SenderIdentification><Recipients><To><Number>18601106635</Number></To></Recipients><ServiceCode>3111005101</ServiceCode><DeliveryReport>true</DeliveryReport><ReadReply>true</ReadReply><Subject>test</Subject><ChargedParty>Recipient</ChargedParty></SubmitReq></env:Body></env:Envelope>\r\n----NextPart_0_2817_24856\r\nContent-Type:multipart/related;start=\"<START>\";type=\"application/smil\";boundary=\"SubPart_7452684322002_77645\"\r\nContent-ID:mm7Test\r\nContent-Transfer-Encoding:8bit\r\n\r\n--SubPart_7452684322002_77645\r\nContent-Type:text/plain;charset=GB2312\r\nContent-Transfer-Encoding:8bit\r\nContent-ID:1.text\r\n\r\nyou received this message right ?\r\n\r\n--SubPart_7452684322002_77645--\r\n----NextPart_0_2817_24856--";

	public static void main(String[] args) {
		VaspSendTest test = new VaspSendTest();

		test.sendMMS(3, "3100202102", "15611021168");
	}

	private void sendMMS(int type, String servicecode, String cellphonenumber) {
		System.out.println("starting to send mms.....");
		MM7Config mm7Config = new MM7Config("./config/config.xml");
		mm7Config.setConnConfigName("./config/config.xml");
		MM7SubmitReq submit = new MM7SubmitReq();
		submit.setTransactionID("1");
		submit.addTo(cellphonenumber);

		submit.setVASID("10655565");
		submit.setVASPID("90537");
		submit.setServiceCode(servicecode);
		submit.setSenderAddress("10655565");

		submit.setDeliveryReport(true);
		submit.setReadReply(true);
		submit.setChargedParty((byte) 1);

		submit.setSubject("test");

		MMContent content = new MMContent();
		switch (type) {
		case 1:
			content.setContentType(MMConstants.ContentType.MULTIPART_RELATED);
			content.setContentID("mm7Test");
			MMContent sub1 = MMContent.createFromFile("/Users/ang/Desktop/temp.eml");
			sub1.setContentType(MMConstants.ContentType.SMIL);
			sub1.setContentID("1.smil");
			content.addSubContent(sub1);
			break;
		case 2:
			content.setContentType(MMConstants.ContentType.MULTIPART_RELATED);
			content.setContentID("mm7Test");
			MMContent sub3 = MMContent.createFromFile("/home/bjlt/data/content/test.jpg");
			sub3.setContentType(MMConstants.ContentType.JPEG);
			sub3.setContentID("1.jpg");
			content.addSubContent(sub3);
			break;
		case 3:
			try {
				content = AceMessageUtil.emlFileToMM7("/Users/ang/Desktop/temp.eml");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		default:
			content.setContentType(MMConstants.ContentType.MULTIPART_RELATED);
			content.setContentID("mm7Test");
			MMContent sub2 = MMContent.createFromString("you received this message right ?");
			sub2.setContentType(MMConstants.ContentType.TEXT);
			sub2.setContentID("1.text");
			content.addSubContent(sub2);
		}

		submit.setContent(content);
		MM7Sender mm7Sender = null;
		try {
			mm7Sender = new MM7Sender(mm7Config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("begfore send ...");
		int i = 0;
		MM7RSRes res = mm7Sender.send(submit);
		if ((res instanceof MM7SubmitRes)) {
			System.out.println("res.statuscode=" + res.getStatusCode() + ";res.statusText=" + res.getStatusText());
			MM7SubmitRes subRes = (MM7SubmitRes) res;
			System.out.println("messageid=" + subRes.getMessageID());
		} else {
			log.info("=============" + res);
			System.out.println("send failure...");
		}
	}
}
