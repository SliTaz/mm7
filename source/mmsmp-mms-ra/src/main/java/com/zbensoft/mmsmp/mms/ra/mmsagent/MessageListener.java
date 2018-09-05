package com.zbensoft.mmsmp.mms.ra.mmsagent;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageListener {
	int number;
	LinkedBlockingQueue queue;
	MessageDispather dispather;
	ExecutorService execor;

	public void start() {
		System.out.println("MessageListener start;queue:"+queue);
		sleeping(200);
		this.execor = Executors.newFixedThreadPool(this.number);

		for (int i = 0; i < this.number; i++) {
			Thread thread = new Thread(new MessageConsumer());
			this.execor.execute(thread);
			sleeping(50);
		}
	}

	public void stop() {
		this.execor.shutdown();
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumber() {
		return this.number;
	}

	public void setQueue(LinkedBlockingQueue queue) {
		this.queue = queue;
	}

	public void setDispather(MessageDispather dispather) {
		this.dispather = dispather;
	}

	public void sleeping(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception localException) {
		}
	}

	class MessageConsumer implements Runnable {
		MessageConsumer() {
		}

		public void run() {
			while (true)
				try {
					Object message = MessageListener.this.queue.take();

					if (message == null) {
						MessageListener.this.sleeping(100);
					} else {
						MessageListener.this.dispather.doDispatch((AbstractMessage) message);
					}
				} catch (Exception ex) {
					MessageListener.this.sleeping(100);
				}
		}
	}
}
