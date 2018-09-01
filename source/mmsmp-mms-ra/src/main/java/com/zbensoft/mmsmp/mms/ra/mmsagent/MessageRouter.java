package com.zbensoft.mmsmp.mms.ra.mmsagent;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

public class MessageRouter {
	static final Logger logger = Logger.getLogger(MessageRouter.class);
	ConcurrentHashMap<String, BlockingQueue> policyMap;

	public void doRoute(AbstractMessage message) {
		try {
			((BlockingQueue) this.policyMap.get(message.getClass().getName())).put(message);
		} catch (Exception ex) {
			logger.error("message router", ex);
		}
	}

	public void setPolicyMap(ConcurrentHashMap<String, BlockingQueue> policyMap) {
		this.policyMap = policyMap;
	}
}
