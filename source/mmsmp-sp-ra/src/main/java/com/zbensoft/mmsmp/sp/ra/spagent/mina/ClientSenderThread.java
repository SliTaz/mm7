package com.zbensoft.mmsmp.sp.ra.spagent.mina;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.MessageQueue;
import java.util.Queue;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

public class ClientSenderThread implements Runnable {
	private static final Logger logger = Logger.getLogger(ClientSenderThread.class);
	public static IoSession session;
	private MessageQueue<AbstractMessage> mtQuence = SPMessageQuene.getInstance().getMtQuence();

	public void run() {
		while (true)
			if (MinaDataStructure.getInstance().getQueue().size() >= 4500) {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException localInterruptedException) {
				}
			} else {
				AbstractMessage mtmessage = null;
				try {
					mtmessage = (AbstractMessage) this.mtQuence.poll();

					if ((mtmessage != null) && (session.isConnected())) {
						logger.info("begin mtmessage to corebiz");
						session.write(mtmessage);
						logger.info("end mtmessage to corebiz");
					} else if (mtmessage == null) {
						Thread.sleep(1000L);
					} else if (!session.isConnected()) {
						logger.info("spAgent not connected with corebiz");
					}
				} catch (Exception e) {
					logger.error("get mtmessage from mtquene failed", e);
				}
			}
	}
}
