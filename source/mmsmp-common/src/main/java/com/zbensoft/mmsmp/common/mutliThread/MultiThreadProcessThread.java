package com.zbensoft.mmsmp.common.mutliThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiThreadProcessThread extends Thread {
	private static Logger log = LoggerFactory.getLogger(MultiThreadProcessThread.class);

	public MultiThreadProcessThread() {
		super("MultiThreadProcessThread");
	}

	@Override
	public void run() {
		while (true) {
			try {
				MultiThreadManage.getInstance().doThread();
				Thread.sleep(1000 * 60);
			} catch (Exception e) {
				log.error("MultiThreadProcessThread exception", e);
			}
		}
	}

}
