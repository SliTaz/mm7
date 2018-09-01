package com.zbensoft.mmsmp.sp.ra.spagent.mina;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.MessageQueue;
import com.zbensoft.mmsmp.sp.ra.spagent.router.MORouter;
import org.apache.log4j.Logger;

public class ServerSenderThread implements Runnable {
	private static final Logger logger = Logger.getLogger(ServerSenderThread.class);
	private SPMessageQuene messageQuene;
	private MORouter moRouter = new MORouter();

	public ServerSenderThread(SPMessageQuene instance) {
		this.messageQuene = instance;
	}

	public void run() {
		while (true) {
			AbstractMessage momessage = null;
			try {
				momessage = (AbstractMessage) this.messageQuene.getMoQuence().poll();

				if (momessage == null) {
					Thread.sleep(1000L);
				} else {
					logger.info("begin deal moMessage");
					this.moRouter.doRouter(momessage);
					logger.info("end deal moMessage");
				}
			} catch (Exception e) {
				logger.error("get momessage from moquence failed", e);
			}
		}
	}
}
