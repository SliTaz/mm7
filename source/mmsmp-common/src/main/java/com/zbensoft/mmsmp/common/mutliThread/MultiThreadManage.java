package com.zbensoft.mmsmp.common.mutliThread;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多线程管理
 * 
 * @author xieqiang
 * 
 */
public class MultiThreadManage {
	private static Logger log = LoggerFactory.getLogger(MultiThreadManage.class);
	private static MultiThreadManage instance = null;
	private static final Object uniqueLock = new Object();
	private static final Object objectLock = new Object();
	private Map<String, MultiThreadMessage> map = new HashMap<String, MultiThreadMessage>();

	public static MultiThreadManage getInstance() {
		if (instance == null) {
			synchronized (uniqueLock) {
				instance = new MultiThreadManage();
				MultiThreadProcessThread multiThreadProcessThread = new MultiThreadProcessThread();
				multiThreadProcessThread.start();
			}
		}
		return instance;
	}

	/**
	 * 增加需管理的线程
	 * 
	 * @param clazz
	 *            现场类
	 * @param threadCountKey
	 *            现场数key
	 * @param threadCountDefault
	 *            默认线程数
	 * @param maxThreadCountKey
	 *            最大线程数key
	 * @param maxThreadCountDefault
	 *            默认最大线程数
	 * @param sleepTimeKey
	 *            休息时间key
	 * @param sleepTimeDefault
	 *            默认休息时间
	 * @return
	 */
	public boolean addThread(Class<?> clazz, int threadCount, int maxThreadCount, int sleepTime) {
		synchronized (objectLock) {
			try {
				if (clazz != null && MultiThread.class.isAssignableFrom(clazz)) {
					MultiThreadMessage message = map.get(clazz.getName());
					if (message == null) {
						message = new MultiThreadMessage(clazz.getName(), clazz, threadCount, maxThreadCount,
								sleepTime);
						map.put(message.getKey(), message);
					} else {
						message.setThreadCount(threadCount);
						message.setMaxThreadCount(maxThreadCount);
						message.setSleepTime(sleepTime);
					}
					// add by xieqiang 2016-04-20 系统增加多线程后，不需要等待时间，立即启动线程
					MultiThreadManage.getInstance().doThread();
				}
			} catch (Exception e) {
				log.error("", e);
			}
			return true;
		}
	}

	public boolean addThread(Class<?> clazz) {
		synchronized (objectLock) {
			try {
				if (clazz != null && MultiThread.class.isAssignableFrom(clazz)) {
					MultiThreadMessage message = map.get(clazz.getName());
					if (message == null) {
						message = new MultiThreadMessage(clazz.getName(), clazz);
						map.put(message.getKey(), message);
					} else {
						message.setThreadCount(1);
						message.setMaxThreadCount(1);
						message.setSleepTime(2);
					}
					// add by xieqiang 2016-04-20 系统增加多线程后，不需要等待时间，立即启动线程
					MultiThreadManage.getInstance().doThread();
				}
			} catch (Exception e) {
				log.error("", e);
			}
			return true;
		}
	}

	void doThread() {
		synchronized (objectLock) {
			Set<String> keySet = map.keySet();
			if (keySet != null && map.size() > 0) {
				for (Iterator<String> keys = keySet.iterator(); keys.hasNext();) {
					String key = (String) keys.next();
					MultiThreadMessage message = map.get(key);
					if (message != null) {
						int count = message.getThreadCount();
						int countMax = message.getMaxThreadCount();
						Map<Integer, MultiThread> threadMap = message.getMap();
						for (int i = 0; i < countMax; i++) {
							try {
								if (i < count) {
									MultiThread t = threadMap.get(i);
									if (t == null) {
										Constructor<?> cons = message.getClazz().getConstructor(String.class);
										t = (MultiThread) cons.newInstance(message.getKey() + "-" + i);
										t.setSleepTime(message.getSleepTime());
										t.setIndex(i);
										t.start();
										threadMap.put(i, t);
									} else {
										if (t.isStopThread()) {
											t.start();
										}
									}
								} else {
									MultiThread t = threadMap.get(i);
									if (t != null) {
										t.stopThread();
									}
									threadMap.remove(i);
								}
							} catch (Exception e) {
								log.error("", e);
							}
						}
					}

				}
			}
		}
	}

	/**
	 * 取得没有响应的线程
	 * 
	 * @param time
	 * @return
	 */
	public String getThreadNoAnswerForTime(int time) {
		synchronized (objectLock) {
			StringBuffer sb = new StringBuffer();
			Set<String> keySet = map.keySet();
			if (keySet != null && map.size() > 0) {
				for (Iterator<String> keys = keySet.iterator(); keys.hasNext();) {
					String key = (String) keys.next();
					MultiThreadMessage message = map.get(key);
					if (message != null) {
						int count = message.getThreadCount();
						Map<Integer, MultiThread> threadMap = message.getMap();
						for (int i = 0; i < count; i++) {
							try {
								MultiThread t = threadMap.get(i);
								if (t == null) {
									sb.append(message.getClazz().getName()).append(" index=").append(count)
											.append(" is null,not start.");
								} else {
									if (System.currentTimeMillis() - t.getActiveTime() > time) {
										sb.append(t.getName()).append(" no answer time(sec):")
												.append((System.currentTimeMillis() - t.getActiveTime()) / 1000)
												.append(".");
									}
								}
							} catch (Exception e) {
								log.error("", e);
							}
						}
					}
				}
			}
			return sb.toString();
		}
	}
}
