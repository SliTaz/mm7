package com.zbensoft.mmsmp.common.mutliThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MultiThread extends Thread {
	private static final Logger log = LoggerFactory.getLogger(MultiThread.class);
	private boolean isRun = true;
	private int sleepTime;
	private long activeTime = 0;
	private int index;

	public boolean isStopThread() {
		return !isRun;
	}

	public MultiThread(String name) {
		super(name);
	}

	public void stopThread() {
		isRun = false;
	}

	@Override
	public void run() {
		while (isRun) {
			try {
				activeTime = System.currentTimeMillis();
				boolean isNotSleep = process();
				if (!isNotSleep) {
					if (sleepTime > 0) {
						Thread.sleep(sleepTime);
					}
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

	/**
	 * 活动时间，用于计算是否还处于活动状态
	 * 
	 * @return
	 */
	public long getActiveTime() {
		return activeTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public abstract boolean process();
}
