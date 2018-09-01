package com.zbensoft.mmsmp.mms.ra.mmsagent;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
import org.apache.log4j.Logger;

public class MessageDispather {
	static final Logger logger = Logger.getLogger(MessageDispather.class);
	MinaClientProxy minaClientProxy;
	Mm7ClientProxy mm7ClientProxy;

	public void doDispatch(AbstractMessage message) {
		try {
			if ((message instanceof MO_MMDeliverSPMessage)) {
				this.minaClientProxy.deliver(message);
			} else if ((message instanceof MO_ReportMessage)) {
				this.minaClientProxy.deliver(message);
			} else if ((message instanceof MT_MMMessage)) {
				this.mm7ClientProxy.submit((MT_MMMessage) message);
			} else if ((message instanceof MT_MMHttpSPMessage)) {
				this.mm7ClientProxy.submit((MT_MMHttpSPMessage) message);
			} else if ((message instanceof MT_ReportMessage)) {
				this.minaClientProxy.deliver(message);
			}
		} catch (Exception ex) {
			logger.error("message dispatch", ex);
		}
	}

	public void setMinaClientProxy(MinaClientProxy minaClientProxy) {
		this.minaClientProxy = minaClientProxy;
	}

	public void setMm7ClientProxy(Mm7ClientProxy mm7ClientProxy) {
		this.mm7ClientProxy = mm7ClientProxy;
	}
}
