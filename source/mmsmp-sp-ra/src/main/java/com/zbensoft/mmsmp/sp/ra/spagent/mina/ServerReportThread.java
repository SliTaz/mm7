package com.zbensoft.mmsmp.sp.ra.spagent.mina;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.MessageQueue;
import com.zbensoft.mmsmp.sp.ra.spagent.router.MORouter;
import org.apache.log4j.Logger;

public class ServerReportThread implements Runnable {
	private static final Logger logger = Logger.getLogger(ServerReportThread.class);
	private SPMessageQuene messageQuene;
	private MORouter moRouter = new MORouter();

	public ServerReportThread(SPMessageQuene instance) {
		this.messageQuene = instance;
	}

	public void run() {
		while (true) {
			AbstractMessage momessage = null;
			try {
				momessage = (AbstractMessage) this.messageQuene.getReportQuence().poll();

				if (momessage == null) {
					Thread.sleep(1000L);
				} else {
					logger.info("begin deal moReportMessage");
					this.moRouter.doRouter(momessage);
					logger.info("end deal moReportMessage");
				}
			} catch (Exception e) {
				logger.error("get momessage from moquence failed", e);
			}
		}
	}
}
