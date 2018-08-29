package com.zbensoft.mmsmp.sms.ra.mina.listener;

import org.apache.log4j.Logger;

public class ConnectManager extends Thread {
    private static final Logger logger = Logger.getLogger(ConnectManager.class);

    public ConnectManager() {
    }

    public void run() {
        ThreadListener listener = new ThreadListener();

        while(true) {
            int connected = listener.startClient();
            if (connected != 0) {
                logger.info("connect successfuly");
                return;
            }

            try {
                logger.info("reconnect failed and wait 5 seconds to try again");
                Thread.sleep(5000L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }
        }
    }
}
