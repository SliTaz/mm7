package com.zbensoft.mmsmp.sp.ra.spagent.mina;

import com.zbensoft.mmsmp.sp.ra.spagent.listener.ThreadListener;
import org.apache.log4j.Logger;

public class ReConnector extends Thread {
	private static final Logger logger = Logger.getLogger(ReConnector.class);

	public void run() {
		ThreadListener listener = new ThreadListener();
		while (true) {
			int connected = listener.startMinaClient();
			if (connected != 0)
				break;
			try {
				logger.error("reconnect failed and wait 5 seconds to try again");
				Thread.sleep(5000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

		logger.info("connect successfuly");
	}
}
