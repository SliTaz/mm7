package com.zbensoft.mmsmp.corebiz.handle;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.corebiz.cache.DataCache;
import com.zbensoft.mmsmp.corebiz.service.mina.MinaClientProxy;
import org.apache.log4j.Logger;

public class SPDispatchMessageHandler extends DispatchMessageHandler implements IMessageHandler {
	static final Logger logger = Logger.getLogger(SPDispatchMessageHandler.class);

	MinaClientProxy ownerClientProxy;
	DataCache dataCache;

	public void doHandler(Object message) throws Exception {
		AbstractMessage msg = (AbstractMessage) message;

		String TAKE_ACC_NUMBER = this.dataCache.getSysParams("TAKE_ACC_NUMBER");

		if ((msg instanceof MO_ReportMessage)) {

			MO_ReportMessage report = (MO_ReportMessage) msg;
			if ((report.getReportUrl() != null) && (this.dataCache.getOwnSpReportUrl(report.getReportUrl()) != null)) {
				logger.info(
						"send one owner mmsmt report notify message[globalMessageid:" + msg.getGlobalMessageid() + "]");
				this.ownerClientProxy.send(msg);
			} else {
				super.doHandler(message);
			}

		} else if ((msg instanceof OrderRelationUpdateNotifyRequest)) {
			OrderRelationUpdateNotifyRequest order = (OrderRelationUpdateNotifyRequest) msg;
			if ((order.getSpId() != null) && (this.dataCache.getOwnSpInfoBySpId(order.getSpId()) != null)) {
				logger.info("send one owner order relation notify message[globalMessageid:" + msg.getGlobalMessageid()
						+ "]");

				this.ownerClientProxy.send(msg);
			} else {
				super.doHandler(message);
			}

		} else if ((msg instanceof MO_SMMessage)) {
			MO_SMMessage mosms = (MO_SMMessage) msg;
			if (this.dataCache.getOwnSpInfoBySpId(mosms.getVaspId()) != null) {
				logger.info("send one owner MO_SMMessage message[globalMessageid:" + msg.getGlobalMessageid() + "]");

				this.ownerClientProxy.send(msg);
			} else {
				super.doHandler(message);
			}

		} else {
			super.doHandler(message);
		}
	}

	public void setOwnerClientProxy(MinaClientProxy ownerClientProxy) {
		this.ownerClientProxy = ownerClientProxy;
	}

	public void setDataCache(DataCache dataCache) {
		this.dataCache = dataCache;
	}
}
