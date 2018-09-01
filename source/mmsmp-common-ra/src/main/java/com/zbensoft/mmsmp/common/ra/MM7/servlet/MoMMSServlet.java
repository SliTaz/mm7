package com.zbensoft.mmsmp.common.ra.MM7.servlet;

import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverMessage;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7DeliverReq;
import com.cmcc.mm7.vasp.message.MM7DeliverRes;
import com.cmcc.mm7.vasp.message.MM7VASPRes;
import com.cmcc.mm7.vasp.service.MM7ReceiveServlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class MoMMSServlet extends MM7ReceiveServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MoMMSServlet.class);
	private MessageQueue mo_moqueue;
	private HttpServletRequest request;

	public MoMMSServlet(MessageQueue queue) {
		this.mo_moqueue = queue;
	}

	public void init() throws ServletException {
		super.init();
		this.Config = new MM7Config("mm7Config");
	}

	protected void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException {
		this.request = new HttpRequest(req);
		super.service(this.request, rsp);
	}

	public MM7VASPRes doDeliver(MM7DeliverReq req) {
		logger.info("recevice a mm7 deliverrequest message");
		logger.info("send to:" + req.getTo().get(0));
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

		this.mo_moqueue.addMessage(me, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
		logger.info("MO_Queue size is:" + this.mo_moqueue.getSize(MessageQueue.PRIORITY_COMMON));

		MM7DeliverRes mm7DeliverRes = new MM7DeliverRes();
		mm7DeliverRes.setServiceCode("服务代码");
		mm7DeliverRes.setStatusCode(1000);
		mm7DeliverRes.setStatusText("所用状态文本说明");
		return mm7DeliverRes;
	}
}
