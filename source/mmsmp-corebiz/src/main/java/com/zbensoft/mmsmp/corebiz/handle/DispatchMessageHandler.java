package com.zbensoft.mmsmp.corebiz.handle;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.corebiz.service.mina.MinaClientProxy;
import com.zbensoft.mmsmp.log.COREBIZ_LOG;

import org.apache.log4j.Logger;

public class DispatchMessageHandler implements IMessageHandler {
	static final Logger logger = Logger.getLogger(DispatchMessageHandler.class);

	MinaClientProxy minaClientProxy;

	public void doHandler(Object message) throws Exception {
		try {
			this.minaClientProxy.send((AbstractMessage) message);
		} catch (Exception ex) {
			COREBIZ_LOG.ERROR("",ex);
			throw ex;
		}
	}

	public void setMinaClientProxy(MinaClientProxy minaClientProxy) {
		this.minaClientProxy = minaClientProxy;
	}
}
