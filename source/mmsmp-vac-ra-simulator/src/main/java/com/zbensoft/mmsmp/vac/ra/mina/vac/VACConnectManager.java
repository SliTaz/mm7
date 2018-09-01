package com.zbensoft.mmsmp.vac.ra.mina.vac;

import org.apache.log4j.Logger;

public class VACConnectManager extends Thread {
	private static final Logger logger = Logger.getLogger(VACConnectManager.class.getName());
	private String threadname;
	private VACClient client = null;

	public VACConnectManager(String threadame) {
		this.threadname = threadame;
	}

	private VACClient getClientInstance() {
		if (this.client == null) {
			this.client = new VACClient();
		}
		return this.client;
	}

	public void run() {
		try {
			while (true) {
				int connected = getClientInstance().run(this.threadname);
				if (connected != 0)
					break;
				try {
					logger.info("warn : vacClient reconnect failed and wait 5 seconds to try again");
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			logger.info("connect successfuly");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
