package com.zbensoft.mmsmp.common.ra.MM7.servlet;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.AdminConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.message.ConfirmPriceMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueueClientInf;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueueClientTcpImpl;
import com.zbensoft.mmsmp.common.ra.utils.MM7DAO;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7DeliverReq;
import com.cmcc.mm7.vasp.message.MM7DeliverRes;
import com.cmcc.mm7.vasp.message.MM7DeliveryReportReq;
import com.cmcc.mm7.vasp.message.MM7DeliveryReportRes;
import com.cmcc.mm7.vasp.message.MM7VASPRes;
import com.cmcc.mm7.vasp.service.MM7ReceiveServlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ReceiveServlet extends MM7ReceiveServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReceiveServlet.class);
	private ReportHandler handle;
	private HttpRequest request;
	private MessageQueue MO_Queue;
	private MM7DAO mm7dao;

	public ReceiveServlet(MessageQueue queue) {
		this.MO_Queue = queue;
	}

	public void init() throws ServletException {
		super.init();
		this.handle = new ReportHandler();
		this.Config = new MM7Config("mm7Config");
	}

	protected void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException {
		logger.info("Content-Type:\t\t" + req.getHeader("Content-Type"));
		this.request = new HttpRequest(req);
		super.service(this.request, rsp);
	}

	public MM7VASPRes doDeliveryReport(MM7DeliveryReportReq request) {
		logger.info("=====>recevice a doDeliveryReport messsage==============");

		String phone = request.getRecipient();
		String str_phone = phone;
		if (phone.startsWith("+86"))
			str_phone = phone.substring(3);
		else if (phone.startsWith("86"))
			str_phone = phone.substring(2);

		MO_ReportMessage mtReport = new MO_ReportMessage();
		mtReport.setCorrelator(request.getMessageID() + str_phone);
		mtReport.setStatus(request.getMMStatus());
		mtReport.setSourceType(1);

		this.MO_Queue.addMessage(mtReport, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
		logger.info("MO_Queue size is:" + this.MO_Queue.getSize(MessageQueue.PRIORITY_COMMON));

		ConfirmPrice(request);

		MM7DeliveryReportRes mm7DeliveryReportRes = new MM7DeliveryReportRes();
		mm7DeliveryReportRes.setStatusCode(1000);
		mm7DeliveryReportRes.setTransactionID(request.getTransactionID());
		return mm7DeliveryReportRes;
	}

	private void ConfirmPrice(MM7DeliveryReportReq request) {
		logger.info("=====>start ConfirmPrice==============");
		String phone = request.getRecipient();
		String str_phone = phone;
		if (phone.startsWith("+86"))
			str_phone = phone.substring(3);
		else if (phone.startsWith("86"))
			str_phone = phone.substring(2);

		ConfirmPriceMessage cpmessage = new ConfirmPriceMessage();
		cpmessage.setMessageId(request.getMessageID());
		cpmessage.setRecipient(request.getRecipient());
		cpmessage.setTransactionID(request.getTransactionID());
		cpmessage.setStrPhone(str_phone);
		cpmessage.setOperatorsType(3);
		this.MO_Queue.addMessage(cpmessage, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
		logger.info("MO_Queue size is :" + this.MO_Queue.getSize(MessageQueue.PRIORITY_COMMON));
		logger.info("=====>end ConfirmPrice==============");
	}

	public MM7VASPRes doDeliver(MM7DeliverReq req) {
		StringBuilder sb = new StringBuilder("\r\nReceive Deliver Message..............");
		sb.append("\r\n").append("To:\t\t").append(req.getTo().get(0)).append("\r\n");
		sb.append("sender:\t\t").append(req.getSender()).append("\r\n");
		sb.append("Subject:\t\t").append(req.getSubject()).append("\r\n").append("Content Charset:\t\t").append(req.getContent().getCharset()).append("\r\n").append("TransactionID:\t\t").append(req.getTransactionID())
				.append("\r\n").append("Content-Type:\t\t").append(req.getContentType());

		logger.info(sb.toString());

		MO_MMDeliverMessage me = new MO_MMDeliverMessage();
		me.setRequest(this.request);
		me.setBcclist(req.getBcc());
		me.setCclist(req.getCc());
		me.setContent(req.getContent());
		me.setLinkedID(req.getLinkedID());
		me.setMM7Version(req.getMM7Version());
		me.setMMSRelayServerID(req.getMMSRelayServerID());
		me.setPriority(req.getPriority());
		me.setReplyChargingID(req.getReplyChargingID());
		me.setSender(req.getSender());
		me.setSubject(req.getSubject());
		me.setTimeStamp(req.getTimeStamp());
		me.setTo(req.getTo());
		me.setTransactionID(req.getTransactionID());
		me.setContentType(req.getContentType());

		this.MO_Queue.addMessage(me, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
		logger.info("MO_Queue size is:" + this.MO_Queue.getSize(MessageQueue.PRIORITY_COMMON));

		MM7DeliverRes mm7DeliverRes = new MM7DeliverRes();
		mm7DeliverRes.setServiceCode("服务代码");
		mm7DeliverRes.setStatusCode(1000);
		mm7DeliverRes.setStatusText("所用状态文本说明");
		return mm7DeliverRes;
	}

	private void notify_corebiz(MO_ReportMessage moSmmessage) {
		try {
			String host = ConfigUtil.getInstance().getAdminConfig().getNotifyMessageIP();
			int port = ConfigUtil.getInstance().getCorebizConfig().getMoQueueListenPort();
			MessageQueueClientInf messageQueueClientInf = new MessageQueueClientTcpImpl(host, port);
			messageQueueClientInf.send(moSmmessage);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public void setMm7dao(MM7DAO mm7dao) {
		this.mm7dao = mm7dao;
	}
}
