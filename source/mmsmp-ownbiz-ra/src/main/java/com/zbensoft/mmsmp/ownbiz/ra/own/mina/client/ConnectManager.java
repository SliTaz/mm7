

package com.zbensoft.mmsmp.ownbiz.ra.own.mina.client;

import com.zbensoft.mmsmp.ownbiz.ra.own.listener.MinaServerListener;
import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;

public class ConnectManager extends Thread {
    private static final Logger _log = Logger.getLogger(ConnectManager.class);
    private static final String CLASS_NAME = "ConnectManager ";
    private static final int SLEEP_SECOND = 10;

    public ConnectManager() {
    }

    public void run() {
        _log.info(String.format("%s连接管理器线程启动.....", "ConnectManager "));
        MinaServerListener listener = new MinaServerListener();

        while(true) {
            boolean var2 = false;

            int iConnected;
            try {
                iConnected = listener.startClient();
            } catch (RuntimeIoException var4) {
                iConnected = 0;
            }

            if (iConnected != 0) {
                _log.info(String.format("%s连接管理器连接成功，线程终止.", "ConnectManager "));
                return;
            }

            try {
                _log.info(String.format("%s重新连接失败，等待%d秒钟，再次尝试.", "ConnectManager ", 10));
                Thread.sleep(10000L);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }
        }
    }
}
