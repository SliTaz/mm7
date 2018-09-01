package com.zbensoft.mmsmp.common.mutliThread;

import java.util.HashMap;
import java.util.Map;

class MultiThreadMessage extends Thread {
	private String key;
	private Class<?> clazz;
	private int threadCount = 1;
	private int maxThreadCount = 1;
	private int sleepTime = 2;
	private Map<Integer, MultiThread> map = new HashMap<Integer, MultiThread>();

	public MultiThreadMessage(String key, Class<?> clazz) {

		this.key = key;
		this.clazz = clazz;
	}

	public MultiThreadMessage(String key, Class<?> clazz, int threadCount, int maxThreadCount, int sleepTime) {
		this(key, clazz);
		this.threadCount = threadCount;
		this.maxThreadCount = maxThreadCount;
		this.sleepTime = sleepTime;
	}

	public Map<Integer, MultiThread> getMap() {
		return map;
	}

	public void setMap(Map<Integer, MultiThread> map) {
		this.map = map;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

}
