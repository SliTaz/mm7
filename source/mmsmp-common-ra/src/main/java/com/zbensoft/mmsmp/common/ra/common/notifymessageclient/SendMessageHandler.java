package com.zbensoft.mmsmp.common.ra.common.notifymessageclient;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.IClientHandler;

public class SendMessageHandler implements IClientHandler {
	private static final Log logger = LogFactory.getLog(SendMessageHandler.class);
	private NotifyMessageClientTcpImpl notifyMessageClientTcpImpl;

	public void onConnect(String arg0, int arg1) {
		logger.info("connected");
	}

	public void onDisconnect() {
		logger.info("onDisconnect");
		this.notifyMessageClientTcpImpl.connected = false;
	}

	public void onReceiveMsg(byte[] arg0) {
	}

	public void onReceiveMsg(Serializable arg0) {
	}

	public void onSendedMsg(byte[] arg0) {
	}

	public void onSendedMsg(Serializable arg0) {
		logger.debug("message sended");
	}

	public int slice(byte[] arg0) {
		return 0;
	}

	public SendMessageHandler(NotifyMessageClientTcpImpl notifyMessageClientTcpImpl) {
		this.notifyMessageClientTcpImpl = notifyMessageClientTcpImpl;
	}
}
