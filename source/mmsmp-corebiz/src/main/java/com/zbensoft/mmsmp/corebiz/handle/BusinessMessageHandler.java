package com.zbensoft.mmsmp.corebiz.handle;

import com.zbensoft.mmsmp.common.ra.common.message.*;
import com.zbensoft.mmsmp.corebiz.handle.impl.MmsBusinessHandlerImpl;
import com.zbensoft.mmsmp.corebiz.handle.impl.ProxyPayBusinessHandlerImpl;
import com.zbensoft.mmsmp.corebiz.handle.impl.SendNotifyMessageHandlerimpl;
import com.zbensoft.mmsmp.corebiz.handle.impl.SmsBusinessHandlerImpl;
import com.zbensoft.mmsmp.corebiz.handle.impl.UniBusinessHandlerImpl;
import com.zbensoft.mmsmp.corebiz.message.OrderRelationMessage;
import com.zbensoft.mmsmp.log.COREBIZ_LOG;

import org.apache.log4j.Logger;

public class BusinessMessageHandler implements IMessageHandler {
	static final Logger logger = Logger.getLogger(BusinessMessageHandler.class);

	SmsBusinessHandlerImpl smsHandler;
	MmsBusinessHandlerImpl mmsHandler;
	UniBusinessHandlerImpl uniHandler;
	SendNotifyMessageHandlerimpl senHanderler;
	ProxyPayBusinessHandlerImpl ppayHandler;

	public void doHandler(Object message) {
		if (message == null) {
			COREBIZ_LOG.INFO("warnning handle message is empty");
			return;
		}

		AbstractMessage msg = (AbstractMessage) message;

		if (msg.getGlobalMessageid() == null) {
			COREBIZ_LOG.INFO("warnning one message gmsgid[" + msg.getClass().getName() + "] is null");
			return;
		}

		if ((message instanceof ProxyPayMessage)) {
			this.ppayHandler.doHandler(msg);

		} else if (((message instanceof MO_MMDeliverSPMessage)) || ((message instanceof MT_SPMMHttpMessage))
				|| ((message instanceof MT_ReportMessage)) || ((message instanceof MO_ReportMessage))) {

			this.mmsHandler.doHandler(msg);

		} else if ((msg instanceof MO_SMMessage)) {

			this.smsHandler.doHandler(msg);

		} else if ((msg instanceof OrderRelationMessage)) {

			this.uniHandler.doHandler(msg);

		} else if ((msg instanceof CheckResponse)) {
			CheckResponse response = (CheckResponse) msg;

			if (response.getCRequest() == null) {
				COREBIZ_LOG.INFO("warnning vacAgent reply request is null");
				return;
			}
			if (response.getCRequest().getGlobalMessageid() == null) {
				COREBIZ_LOG.INFO("warnning vacAgent reply gmsgid is null");
				return;
			}

			if (response.getCRequest().getGlobalMessageid().toUpperCase().trim().startsWith("MMS")) {
				this.mmsHandler.doHandler(msg);
			} else if (response.getCRequest().getGlobalMessageid().toUpperCase().trim().startsWith("SMS")) {
				this.smsHandler.doHandler(msg);
			} else if (response.getCRequest().getGlobalMessageid().toUpperCase().trim().startsWith("UNI")) {
				this.uniHandler.doHandler(msg);
			} else if (response.getCRequest().getGlobalMessageid().toUpperCase().trim().startsWith("PPS")) {
				this.ppayHandler.doHandler(msg);
			} else {
				COREBIZ_LOG.INFO("warnning vacAgent reply gmsgid[" + msg.getGlobalMessageid() + "] value error");
			}
		} else if ((msg instanceof OrderRelationUpdateNotifyResponse)) {
			OrderRelationUpdateNotifyResponse response = (OrderRelationUpdateNotifyResponse) msg;

			if (response.getOrderRequest() == null) {
				COREBIZ_LOG.INFO("warnning spAgent reply request is null");
				return;
			}
			if (response.getOrderRequest().getGlobalMessageid() == null) {
				COREBIZ_LOG.INFO("warnning spAgent reply gmsgid is null");
				return;
			}

			if (response.getOrderRequest().getGlobalMessageid().toUpperCase().trim().startsWith("MMS")) {
				this.mmsHandler.doHandler(msg);
			} else if (response.getOrderRequest().getGlobalMessageid().toUpperCase().trim().startsWith("SMS")) {
				this.smsHandler.doHandler(msg);
			} else if (response.getOrderRequest().getGlobalMessageid().toUpperCase().trim().startsWith("UNI")) {
				this.uniHandler.doHandler(msg);
			} else {
				COREBIZ_LOG.INFO("warnning spAgent reply gmsgid[" + msg.getGlobalMessageid() + "] value error");
			}
		} else if ((msg instanceof WOCheckResponse)) {
			WOCheckResponse response = (WOCheckResponse) msg;

			if (response.getWoRequest() == null) {
				COREBIZ_LOG.INFO("warnning woAgent reply request is null");
				return;
			}
			if (response.getWoRequest().getGlobalMessageid() == null) {
				COREBIZ_LOG.INFO("warnning woAgent reply gmsgid is null");
				return;
			}

			if (response.getWoRequest().getGlobalMessageid().toUpperCase().trim().startsWith("UNI")) {
				this.uniHandler.doHandler(msg);
			} else if (response.getWoRequest().getGlobalMessageid().toUpperCase().trim().startsWith("MMS")) {
				this.mmsHandler.doHandler(msg);
			} else {
				COREBIZ_LOG.INFO("warnning woAgent reply gmsgid[" + response.getWoRequest().getGlobalMessageid()
						+ "] value error");
			}

		} else if ((msg instanceof SendNotificationMessage)) {
			this.senHanderler.doHandler(msg);
		}
	}

	public void setSmsHandler(SmsBusinessHandlerImpl smsHandler) {
		this.smsHandler = smsHandler;
	}

	public void setMmsHandler(MmsBusinessHandlerImpl mmsHandler) {
		this.mmsHandler = mmsHandler;
	}

	public void setUniHandler(UniBusinessHandlerImpl uniHandler) {
		this.uniHandler = uniHandler;
	}

	public void setSenHanderler(SendNotifyMessageHandlerimpl senHanderler) {
		this.senHanderler = senHanderler;
	}

	public void setPpayHandler(ProxyPayBusinessHandlerImpl ppayHandler) {
		this.ppayHandler = ppayHandler;
	}
}
