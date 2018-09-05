package com.zbensoft.mmsmp.sp.ra.spagent.message;

import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.MessageQueue;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.SPMessageQuene;
import com.zbensoft.mmsmp.sp.ra.spagent.sp.mina.MessageClient;
import com.zbensoft.mmsmp.sp.ra.spagent.sp.momms.MoMMSToSpSender;
import com.zbensoft.mmsmp.sp.ra.spagent.sp.report.ReportToSpSender;
import com.zbensoft.mmsmp.sp.ra.spagent.sp.ws.OrderRelationClient;
import com.zbensoft.mmsmp.sp.ra.spagent.utils.Deliver;
import com.zbensoft.mmsmp.sp.ra.spagent.utils.HttpRequest;
import com.zbensoft.mmsmp.sp.ra.spagent.utils.OrderRelationCopy;
import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;

public class MessageProcessor {
	private static final Logger logger = Logger.getLogger(MessageProcessor.class);

	public static void main(String[] args) {
		MO_SMMessage mosms = new MO_SMMessage();
		mosms.setSendAddress("1312222222");
		mosms.setVasId("1065556500101");
		mosms.setSmsText("#123");
		mosms.setLinkId("123456");
		mosms.setNotirySPURL("127.0.0.1:39095");//发给sp的模拟器。sp的模拟器的ip和端口号

		mosms.setSequence_Number_1(Integer.valueOf(111));
		mosms.setSequence_Number_2(Integer.valueOf(222));
		mosms.setSequence_Number_3(Integer.valueOf(333));
		mosms.setMessageCoding((byte) 15);
		mosms.setTP_pid((byte) 5);
		mosms.setTP_udhi((byte) 6);
		try {
			mosms.setContentLength(mosms.getSmsText().getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		dealMOSMSMsg(mosms);
	}

	public static void dealMOSMSMsg(MO_SMMessage mosms) {
		String url = mosms.getNotirySPURL();
		logger.info(" send demand sms to sp , the url is : " + url);
		if ((url != null) && (!"".equals(url))) {
			String host = url.split(":")[0];
			String port = url.split(":")[1];
			String userphone = mosms.getSendAddress();
			if (!userphone.startsWith("86")) {
				userphone = "86" + userphone;
			}

			Deliver de = new Deliver();
			try {
				de.Sequence_Number_1 = mosms.getSequence_Number_1();
				de.Sequence_Number_2 = mosms.getSequence_Number_2();
				de.Sequence_Number_3 = mosms.getSequence_Number_3();
				de.MessageCoding = mosms.getMessageCoding();
				de.MessageContent = mosms.getSmsText();
				de.Reserve = mosms.getLinkId();
				de.SPNumber = mosms.getVasId();
				de.TP_pid = mosms.getTP_pid();
				de.TP_udhi = mosms.getTP_udhi();
				de.UserNumber = userphone;
				de.ContentLength = mosms.getContentLength();

				MessageClient client = new MessageClient(host, Integer.parseInt(port));
				client.send(de);
			} catch (Exception e) {
				logger.error(e);
			}

			logger.info("=====>deliver msg: " + de);
		} else {
			logger.info("=====>error! sp ipaddress or port is empty");
		}
	}

	public static void dealMORptMsg(MO_ReportMessage morpt) {
		try {
			ReportToSpSender sender = new ReportToSpSender();
			sender.sendReportToSp(morpt);
		} catch (Exception e) {
			logger.error("dealMoRptMsg is error", e);
		}
		logger.info("send mo report to sp ");
	}

	public static void dealOrderRelation(com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest order) {
		String notifyurl = order.getNotifySPURL();
		com.zbensoft.mmsmp.sp.ra.spagent.sp.ws.OrderRelationUpdateNotifyRequest orderRequest = new com.zbensoft.mmsmp.sp.ra.spagent.sp.ws.OrderRelationUpdateNotifyRequest();
		OrderRelationCopy.copyRequest(order, orderRequest);
		com.zbensoft.mmsmp.sp.ra.spagent.sp.ws.OrderRelationUpdateNotifyResponse orderResponse = OrderRelationClient
				.notifySp(orderRequest, notifyurl);

		com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyResponse res = new com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyResponse();
		OrderRelationCopy.copyResponse(orderResponse, res);
		res.setOrderRequest(order);
		res.setGlobalMessageid(order.getGlobalMessageid());
		try {
			SPMessageQuene.getInstance().getMtQuence().put(res);
		} catch (Exception e) {
			logger.info("put OrderRelationUpdateNotifyResponse to mtquence error ", e);
		}
	}

	public static void dealMOMMSMsg(MO_MMDeliverSPMessage req) {
		try {
			byte[] contentbyte = req.getContentByte();
			HttpRequest request = new HttpRequest();
			request.setB(contentbyte);
			request.setContentType(req.getContentType());
			request.setLinkId(req.getLinkedID());
			request.setSendurl(req.getSendurl());

			String momms = "sendurl:" + req.getSendurl() + "\r\n linkid:" + req.getLinkedID();
			logger.info("the mo mms \r\n" + momms);

			MoMMSToSpSender mmsSender = new MoMMSToSpSender(request);
			mmsSender.send();
			logger.info("send mo mms to sp ");
		} catch (Exception e) {
			logger.error("dealMoMMsMsg is error", e);
		}
	}
}
