package com.zbensoft.mmsmp.common.log;

import ch.qos.logback.core.rolling.RollingFileAppender;

public class MinuteLogAppender<E> extends RollingFileAppender<E> {
	private static long start = System.currentTimeMillis(); // minutes
//	private int rollOverTimeInMinutes = 1;
	private int min = 10;
	@Override
	public void rollover() {
		long currentTime = System.currentTimeMillis();
		int maxIntervalSinceLastLoggingInMillis = getMin() * 60 * 1000;

		if ((currentTime - start) >= maxIntervalSinceLastLoggingInMillis) {
			super.rollover();
			start = System.currentTimeMillis();
		}
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	
}
