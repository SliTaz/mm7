package com.zbensoft.mmsmp.sms.ra.listener;

import com.zbensoft.mmsmp.sms.ra.log.SMS_LOG;

public class ConnectManager extends Thread {
    public ConnectManager() {
    }

    public void run() {
        ThreadListener listener = new ThreadListener();

        while(true) {
            int connected = listener.startClient();
            if (connected != 0) {
                SMS_LOG.INFO("connect successfuly");
                return;
            }

            try {
                SMS_LOG.INFO("reconnect failed and wait 5 seconds to try again");
                Thread.sleep(5000L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }
        }
    }
}
