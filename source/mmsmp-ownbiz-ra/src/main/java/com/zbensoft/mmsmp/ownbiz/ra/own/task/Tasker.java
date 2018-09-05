

package com.zbensoft.mmsmp.ownbiz.ra.own.task;

import com.zbensoft.mmsmp.ownbiz.ra.own.job.Job;

public class Tasker extends Thread {
    static final long EXCEPTION_SLEEPTIME = 1000L;
    long repeatInterval;
    long startDelay;
    boolean isRun;
    Job job;

    public Tasker() {
    }

    public void run() {
        this.isRun = true;
        await(this.startDelay <= 50L ? 50L : this.startDelay);

        while(this.isRun) {
            try {
                this.job.doHandler();
                await(this.repeatInterval <= 50L ? 50L : this.repeatInterval);
            } catch (Exception var2) {
                await(1000L);
            }
        }

    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public static void await(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception var3) {
            ;
        }

    }
}
