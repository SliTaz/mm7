package com.zbensoft.mmsmp.common.ra.common.queue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.mina.TcpClient;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TcpClientImpl;

public class MessageQueueClientTcpImpl implements MessageQueueClientInf {
	private static Logger logger = Logger.getLogger(MessageQueueClientTcpImpl.class);
	private TcpClient tcpClient;
	private String host;
	private int port;
	private Lock lock = new ReentrantLock();
	private Condition connectedCondition = this.lock.newCondition();
	boolean connected;

	public MessageQueueClientTcpImpl(String host, int port) {
		this.host = host;
		this.port = port;

		this.tcpClient = new TcpClientImpl();
		this.tcpClient.setDataHandler(new SendMessageHandler(this), 51200);

		if (this.tcpClient.connect(host, port)) {
			this.connected = true;
		} else
			logger.error("connect to server error!");
	}

	public int send(AbstractMessage message) {
		if (!this.connected) {
			if (this.lock.tryLock()) {
				try {
					reConnect();
				} catch (Exception ex) {
					ex.printStackTrace();
					return 1;
				} finally {
					this.lock.unlock();
				}
			} else {
				try {
					this.connectedCondition.await(10L, TimeUnit.SECONDS);
				} catch (Exception ex) {
					ex.printStackTrace();
					if (!this.connected) {
						return 1;
					}
				}
				if (!this.connected)
					return 1;
			}
		}
		try {
			if (this.tcpClient.send(message) == 0) {
				logger.debug("message sened. message:" + message);
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.tcpClient.disconnect();
		}
		this.tcpClient.disconnect();

		return 1;
	}

	private void reConnect() {
		if (this.tcpClient == null) {
			this.tcpClient = new TcpClientImpl();
			this.tcpClient.setDataHandler(new SendMessageHandler(this), 51200);
		}

		if (this.tcpClient.connect(this.host, this.port)) {
			this.connected = true;
		} else {
			logger.error("connect to server error!");
		}

		this.connectedCondition.signalAll();
	}

	public void stop() {
		this.lock.lock();
		try {
			if (this.tcpClient != null)
				this.tcpClient.disconnect();
		} finally {
			this.lock.unlock();
		}
	}
}
