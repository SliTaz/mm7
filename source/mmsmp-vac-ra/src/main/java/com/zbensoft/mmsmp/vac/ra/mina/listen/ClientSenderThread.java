package com.zbensoft.mmsmp.vac.ra.mina.listen;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.zbensoft.mmsmp.common.ra.common.message.CheckResponse;
import com.zbensoft.mmsmp.vac.ra.log.PROCESS_LOG;

public class ClientSenderThread implements Runnable {
	private static final Logger logger = Logger.getLogger(ClientSenderThread.class);
	public static IoSession session;
	private LinkedBlockingQueue<CheckResponse> responseQuence;

	public ClientSenderThread(CheckMessageQuence messageQuence) {
		this.responseQuence = messageQuence.getResponseQuence();
	}

	public void run() {
		while (true) {
			CheckResponse cResponse = null;
			try {
				cResponse = (CheckResponse) this.responseQuence.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if ((cResponse != null) && (session.isConnected())) {
				session.write(cResponse);
				PROCESS_LOG.INFO("client corebiz " + "send:" + cResponse.toString());
			}
			logger.info("After send to corebiz, the quence size ==" + this.responseQuence.size());
		}
	}

}
