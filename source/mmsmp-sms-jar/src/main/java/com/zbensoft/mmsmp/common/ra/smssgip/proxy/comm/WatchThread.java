package com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm;

public abstract class WatchThread extends Thread {
    private boolean alive = true;
    private String state;
    public static final ThreadGroup tg = new ThreadGroup("watch-thread");

    public WatchThread(String name) {
        super(tg, name);
        this.setDaemon(true);
    }

    public void kill() {
        this.alive = false;
    }

    public final void run() {
        for(; this.alive; yield()) {
            try {
                this.task();
            } catch (Exception var2) {
                var2.printStackTrace();
            } catch (Throwable var3) {
                var3.printStackTrace();
            }
        }

    }

    protected void setState1(String newState) {
        this.state = newState;
    }

    public String getState1() {
        return this.state;
    }

    protected abstract void task();
}
