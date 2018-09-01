package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.threadPool;

public class WorkerThread extends Thread {
    protected final Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    public void run() {
        while(true) {
            try {
                Request request = this.channel.takeRequest();
                request.execute();
            } catch (Exception var2) {
                var2.printStackTrace();
            } catch (Throwable var3) {
                var3.printStackTrace();
            }
        }
    }
}
