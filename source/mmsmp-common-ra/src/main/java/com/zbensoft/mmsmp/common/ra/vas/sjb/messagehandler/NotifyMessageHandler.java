package com.zbensoft.mmsmp.common.ra.vas.sjb.messagehandler;

import com.zbensoft.mmsmp.common.ra.common.queue.messagehandler.AbstractMessageHandler;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.IServerHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class NotifyMessageHandler implements IServerHandler {
	private static Logger logger = Logger.getLogger(NotifyMessageHandler.class);

	private Map<Class<?>, AbstractMessageHandler> messageHandlerMap = new HashMap();

	public void registerMessageHandler(Class<?> clazz, AbstractMessageHandler obj) {
		this.messageHandlerMap.put(clazz, obj);
	}

	public void onConnect(int arg0, String arg1, int arg2) {
		logger.info("connected");
	}

	public void onDisconnect(int arg0, Object arg1) {
		logger.info("Disconnected");
	}

	public void onReceiveMsg(int arg0, Object arg1, byte[] arg2) {
	}

	public void onReceiveMsg(int arg0, Object arg1, Serializable arg2) {
		logger.info("current NotifyMessageHandler onReceiveMsg  receive Msg");
		logger.debug("connectId = " + arg0);
		logger.debug("arg2 = " + arg2.getClass());

		AbstractMessageHandler messageHandler = (AbstractMessageHandler) this.messageHandlerMap.get(arg2.getClass());

		if (messageHandler == null) {
			logger.error("can't find handler to process this type message! please register firstly!");
			throw new RuntimeException("can't find handler to process this type message! please register firstly!");
		}

		messageHandler.handleMessage(arg2);
	}

	public void onSendedMsg(int arg0, Object arg1, byte[] arg2) {
	}

	public void onSendedMsg(int arg0, Object arg1, Serializable arg2) {
	}

	public int slice(int arg0, byte[] arg1) {
		return 0;
	}
}
