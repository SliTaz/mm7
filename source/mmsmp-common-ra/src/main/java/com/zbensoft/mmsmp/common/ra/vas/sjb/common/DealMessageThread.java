package com.zbensoft.mmsmp.common.ra.vas.sjb.common;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MEMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_WapPushMessage;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
import com.zbensoft.mmsmp.common.ra.vas.sjb.send.SendRouter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DealMessageThread implements Runnable {
	private static final Log logger = LogFactory.getLog(DealMessageThread.class);
	public static long mmsInterval = ConfigUtil.getInstance().getCorebizConfig().getMmsInterval();
	public static long mmeInterval = ConfigUtil.getInstance().getCorebizConfig().getMmeInterval();
	public static long smsInterval = ConfigUtil.getInstance().getCorebizConfig().getSmsInterval();
	public static long wapInterval = ConfigUtil.getInstance().getCorebizConfig().getWapInterval();
	private Router router;
	private MessageQueue queue;
	private int priority;

	public DealMessageThread(Router router, MessageQueue queue, int priority) {
		this.router = router;
		this.queue = queue;
		this.priority = priority;
	}

	public void run() {
		while (true) {
			AbstractMessage message = null;
			try {
				message = this.queue.getMessage(Integer.valueOf(this.priority));
				if (message != null) {
					this.router.doRouter(message);
					if ((this.router instanceof SendRouter)) {
						try {
							if ((message instanceof MT_MMMessage))
								Thread.sleep(mmsInterval);
							else if ((message instanceof MT_MEMessage))
								Thread.sleep(mmeInterval);
							else if ((message instanceof MT_SMMessage))
								Thread.sleep(smsInterval);
							else if ((message instanceof MT_WapPushMessage))
								Thread.sleep(wapInterval);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
				} else {
					Thread.sleep(1000L);
				}
			} catch (Exception ex) {
				try {
					Thread.sleep(1000L);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}

		}
	}
}
