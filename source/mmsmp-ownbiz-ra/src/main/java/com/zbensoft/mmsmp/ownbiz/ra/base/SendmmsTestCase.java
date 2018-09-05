package com.zbensoft.mmsmp.ownbiz.ra.base;

import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7RSRes;
import com.cmcc.mm7.vasp.message.MM7SubmitReq;
import com.cmcc.mm7.vasp.message.MM7SubmitRes;
import com.cmcc.mm7.vasp.service.MM7Sender;
import java.io.PrintStream;

public class SendmmsTestCase {
	public static void main(String[] args) {
		MM7Config mm7Config = new MM7Config("./config.xml");
		mm7Config.setConnConfigName("./ConnConfig.xml");

		MM7SubmitReq submit = new MM7SubmitReq();
		submit.setTransactionID("tid00001");

		submit.addTo("15011113322");
		submit.setChargedPartyID("18601317568");
		submit.setVASID("1065556552201");
		submit.setVASPID("91405");
		submit.setServiceCode("3100211200,3100241207");
		submit.setSenderAddress("1065556500409");
		submit.setDeliveryReport(false);
		submit.setReadReply(false);
		submit.setChargedParty((byte) 1);
		submit.setPriority((byte) 0);
		submit.setLinkedID("11223");
		submit.setSubject("测试18689945367赠送给18601106635)");

		MM7Sender mm7Sender = null;
		try {
			MMContent content = MMContent.createFromString("hello zhangwei");
			submit.setContent(content);

			mm7Sender = new MM7Sender(mm7Config);

			MM7RSRes res = mm7Sender.send(submit);
			if ((res instanceof MM7SubmitRes)) {
				System.out.println("res.statuscode=" + res.getStatusCode() + ";res.statusText=" + res.getStatusText());
				MM7SubmitRes subRes = (MM7SubmitRes) res;
				System.out.println("messageid=" + subRes.getMessageID());
			} else {
				System.out.println("send failure...");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
