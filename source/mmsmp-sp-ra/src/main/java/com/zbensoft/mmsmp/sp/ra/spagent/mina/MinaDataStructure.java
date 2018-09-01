package com.zbensoft.mmsmp.sp.ra.spagent.mina;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.mina.core.write.WriteRequest;

public class MinaDataStructure {
	private static MinaDataStructure instance = null;

	private Queue<WriteRequest> q = null;

	private ConcurrentHashMap<Object, Object> map = null;

	private MinaDataStructure() {
		this.q = new MyLinkedBlockingQueue(5000);
		this.map = new ConcurrentHashMap(4);
	}

	public static MinaDataStructure getInstance() {
		if (instance == null) {
			instance = new MinaDataStructure();
		}
		return instance;
	}

	public Queue<WriteRequest> getQueue() {
		return this.q;
	}

	public ConcurrentHashMap getMap() {
		return this.map;
	}
}
