package com.zbensoft.mmsmp.mms.ra.mmsagent;

import com.zbensoft.mmsmp.common.ra.MM7.servlet.HttpRequest;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7DeliverReq;
import com.cmcc.mm7.vasp.message.MM7DeliverRes;
import com.cmcc.mm7.vasp.message.MM7DeliveryReportReq;
import com.cmcc.mm7.vasp.message.MM7DeliveryReportRes;
import com.cmcc.mm7.vasp.message.MM7VASPRes;
import com.cmcc.mm7.vasp.service.MM7ReceiveServlet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ReceiveServlet extends MM7ReceiveServlet {
	static final Logger logger = Logger.getLogger(ReceiveServlet.class);
	HttpRequest mo_req;
	MessageRouter messageRouter;

	public void init() throws ServletException {
		this.Config = new MM7Config("mm7Config");
		//this.messageRouter = ((MessageRouter) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("messageRouter"));
		this.messageRouter = ApplicationListener.getMessageRouter();
	}

	protected void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException {
		this.mo_req = new HttpRequest(req);
		super.service(this.mo_req, rsp);
	}

	public MM7VASPRes doDeliver(MM7DeliverReq req) {
		List tos = req.getTo();
		String to = (tos != null) && (tos.size() > 0) ? (String) tos.get(0) : "";
		String gmsgid = MO_MMDeliverSPMessage.generateUUID("MMSMO");

		StringBuilder sb = new StringBuilder("mmsagent<- mmsc one mmsmo message");
		sb.append("\r\n");
		sb.append("[");
		sb.append(" gmsgid:").append(gmsgid);
		sb.append(" To:").append(to);
		sb.append(" sender: ").append(req.getSender());
		sb.append(" LinkedID: ").append(req.getLinkedID());
		sb.append(" Content Charset: ").append(req.getContent().getCharset());
		sb.append(" TransactionID: ").append(req.getTransactionID());
		sb.append(" Content-Type: ").append(req.getContentType());
		sb.append("]");

		logger.info(sb.toString());

		Map reqHeads = new HashMap();
		ByteArrayOutputStream baos = null;
		ServletInputStream bio = null;
		byte[] aByte = new byte[1024];
		int readLen = 0;
		byte[] contentBytes = (byte[]) null;
		try {
			Enumeration eHeads = this.mo_req.getHeaderNames();
			while (eHeads.hasMoreElements()) {
				String key = (String) eHeads.nextElement();
				String val = this.mo_req.getHeader(key);
				reqHeads.put(key, val);
			}

			baos = new ByteArrayOutputStream();
			bio = this.mo_req.getInputStream();

			baos.write(req.getContentType().getBytes());
			baos.write("\r\n".getBytes());
			while ((readLen = bio.read(aByte)) != -1) {
				baos.write(aByte, 0, readLen);
			}

			contentBytes = baos.toByteArray();
		} catch (Exception ex) {
			logger.error(sb.toString(), ex);
			try {
				baos.close();
			} catch (Exception localException1) {
			}
			try {
				bio.close();
			} catch (Exception localException2) {
			}
		} finally {
			try {
				baos.close();
			} catch (Exception localException3) {
			}
			try {
				bio.close();
			} catch (Exception localException4) {
			}
		}
		MO_MMDeliverSPMessage me = new MO_MMDeliverSPMessage();

		me.setGlobalMessageid(gmsgid);
		me.setContent(null);
		me.setBcclist(req.getBcc());
		me.setCclist(req.getCc());
		me.setLinkedID(req.getLinkedID());
		me.setMMSRelayServerID(req.getMMSRelayServerID());
		me.setPriority(req.getPriority());
		me.setReplyChargingID(req.getReplyChargingID());
		me.setSender(req.getSender());
		me.setSubject(req.getSubject());
		me.setTimeStamp(req.getTimeStamp());
		me.setTo(req.getTo());

		me.setTransactionID(req.getTransactionID());
		me.setContentType(req.getContentType());
		me.setMM7Version(req.getMM7Version());

		me.setRequestHeads(reqHeads);
		me.setContentByte(contentBytes);

		this.messageRouter.doRoute(me);

		MM7DeliverRes mm7DeliverRes = new MM7DeliverRes();
		mm7DeliverRes.setServiceCode("服务代码");
		mm7DeliverRes.setStatusCode(1000);
		mm7DeliverRes.setStatusText("所用状态文本说明");
		mm7DeliverRes.setTransactionID(req.getTransactionID());
		return mm7DeliverRes;
	}

	public MM7VASPRes doDeliveryReport(MM7DeliveryReportReq request) {
		String gmsgid = MO_ReportMessage.generateUUID("MMSMR");

		StringBuilder sb = new StringBuilder("mmsagent<- mmsc one mmsmr message");
		sb.append("\r\n");
		sb.append("[");
		sb.append(" gmsgid:").append(gmsgid);
		sb.append(" receipts: ").append(request.getRecipient());
		sb.append(" sender: ").append(request.getSender());
		sb.append(" messageid: ").append(request.getMessageID());
		sb.append(" statusText: ").append(request.getStatusText());
		sb.append("]");

		logger.info(sb.toString());

		String str_phone = request.getRecipient();

		if (request.getRecipient().startsWith("+86"))
			str_phone = request.getRecipient().substring(3);
		else if (request.getRecipient().startsWith("86"))
			str_phone = request.getRecipient().substring(2);

		MO_ReportMessage mr = new MO_ReportMessage();
		mr.setGlobalMessageid(gmsgid);
		mr.setCorrelator(request.getMessageID());
		mr.setStatus(request.getStatusText());
		mr.setSourceType(1);
		mr.setMessageId(request.getMessageID());
		mr.setRecipient(request.getRecipient());
		mr.setTransactionID(request.getTransactionID());
		mr.setStrPhone(str_phone);
		mr.setOperatorsType(3);

		this.messageRouter.doRoute(mr);

		MM7DeliveryReportRes mm7DeliveryReportRes = new MM7DeliveryReportRes();
		mm7DeliveryReportRes.setStatusCode(1000);
		mm7DeliveryReportRes.setTransactionID(request.getTransactionID());

		return mm7DeliveryReportRes;
	}
}
