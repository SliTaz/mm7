package com.zbensoft.mmsmp.sp.ra.spagent.mina;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {
	private static final long serialVersionUID = 5534425457011377199L;
	private final AtomicInteger putCount = new AtomicInteger(0);
	private final AtomicInteger pollCount = new AtomicInteger(0);

	private final AtomicInteger lastSecondPutNum = new AtomicInteger(0);
	private final AtomicInteger lastSecondPollNum = new AtomicInteger(0);

	public MyLinkedBlockingQueue(int capacity) {
		super(capacity);

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					MyLinkedBlockingQueue.this.lastSecondPutNum.set(MyLinkedBlockingQueue.this.putCount.getAndSet(0));
					MyLinkedBlockingQueue.this.lastSecondPollNum.set(MyLinkedBlockingQueue.this.pollCount.getAndSet(0));
				}
			}
		}).start();
	}

	public int getLastSecondPutNum() {
		return this.lastSecondPutNum.get();
	}

	public int getLastSecondPollNum() {
		return this.lastSecondPollNum.get();
	}

	public void put(E e) throws InterruptedException {
		super.put(e);
		this.putCount.incrementAndGet();
	}

	public E poll() {
		Object e = super.poll();
		if (e != null) {
			this.pollCount.incrementAndGet();
		}
		return (E) e;
	}
}
