package com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SMSSeverThread extends WatchThread {
    private ServerSocket serversocket;
    private SocketEventListener listener;
    private String ip;
    private int port;
    private int status;
    private static final int ON = 0;
    private static final int OFF = 1;

    public SMSSeverThread(String ip, int port, SocketEventListener lis) {
        super("socket-listing");
        this.status = 1;
        this.ip = ip;
        this.port = port;
        this.listener = lis;
        this.setDaemon(false);
        this.start();
    }

    public SMSSeverThread(int port, SocketEventListener lis) {
        this((String)null, port, lis);
    }

    public synchronized void beginListen() throws IOException {
        if (this.status != 0) {
            try {
                this.serversocket = new ServerSocket();
                if (this.ip != null) {
                    this.serversocket.bind(new InetSocketAddress(this.ip, this.port));
                } else {
                    this.serversocket.bind(new InetSocketAddress(this.port));
                }

                this.status = 0;
                this.notifyAll();
            } catch (IOException var2) {
                throw var2;
            }
        }
    }

    public synchronized void stopListen() {
        if (this.status == 0) {
            try {
                if (this.serversocket != null) {
                    this.status = 1;
                    this.serversocket.close();
                    this.serversocket = null;
                }
            } catch (IOException var2) {
                ;
            }
        }

    }

    protected void task() {
        try {
            if (this.status == 0) {
                Socket incoming = this.serversocket.accept();
                this.listener.onConnect(incoming);
            } else {
                synchronized(this) {
                    this.wait(10000L);
                }
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
}