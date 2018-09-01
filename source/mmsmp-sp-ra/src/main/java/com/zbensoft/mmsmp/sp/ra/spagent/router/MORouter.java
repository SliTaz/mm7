package com.zbensoft.mmsmp.sp.ra.spagent.router;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.sp.ra.spagent.message.MessageProcessor;
import org.apache.log4j.Logger;

public class MORouter {
	private static final Logger logger = Logger.getLogger(MORouter.class);

	public void doRouter(AbstractMessage momessage) {
		if (momessage == null) {
			return;
		}
		logger.info("the message is :" + momessage.getClass());
		try {
			if ((momessage instanceof MO_SMMessage)) {
				MO_SMMessage mosms = (MO_SMMessage) momessage;
				logger.info("begin dealMoSMSMsg");
				MessageProcessor.dealMOSMSMsg(mosms);
			} else if ((momessage instanceof MO_ReportMessage)) {
				MO_ReportMessage morpt = (MO_ReportMessage) momessage;
				logger.info("begin dealMORptMsg");
				MessageProcessor.dealMORptMsg(morpt);
			} else if ((momessage instanceof OrderRelationUpdateNotifyRequest)) {
				OrderRelationUpdateNotifyRequest order = (OrderRelationUpdateNotifyRequest) momessage;
				logger.info("begin dealOrderRelation");
				MessageProcessor.dealOrderRelation(order);
			} else if ((momessage instanceof MO_MMDeliverSPMessage)) {
				MO_MMDeliverSPMessage req = (MO_MMDeliverSPMessage) momessage;
				logger.info("begin dealMOMMSMsg");
				MessageProcessor.dealMOMMSMsg(req);
			}
		} catch (Exception e) {
			logger.error("moMessage doRouter is error", e);
		}
	}
}
